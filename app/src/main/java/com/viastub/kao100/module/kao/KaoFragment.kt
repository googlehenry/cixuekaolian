package com.viastub.kao100.module.kao

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.TestExamAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.db.ConfigGlobal
import com.viastub.kao100.db.ExamSimulation
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.http.DownloadUtil
import com.viastub.kao100.http.RemoteAPIDataService
import com.viastub.kao100.utils.*
import com.viastub.kao100.wigets.DownloadingDialog
import com.yechaoa.yutilskt.LogUtilKt
import kotlinx.android.synthetic.main.fragment_kao.*
import kotlinx.android.synthetic.main.fragment_kao.main_downloading_progress
import kotlinx.android.synthetic.main.fragment_kao.what_if_no_content
import kotlinx.android.synthetic.main.fragment_xue.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KaoFragment : BaseFragment(), View.OnClickListener {

    var downloadingDialog: DownloadingDialog? = null

    override fun id(): Int {
        return R.layout.fragment_kao
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        downloadingDialog = DownloadingDialog(mContext)
        downloadingDialog?.setCanceledOnTouchOutside(false)

        //https://www.jianshu.com/p/e68a0b5fd383
        recycler_view_test_papers.addItemDecoration(
            DividerItemDecoration(
                mContext,
                DividerItemDecoration.VERTICAL
            )
        )

        var configMap = mutableMapOf<String, ConfigGlobal?>()
        doAsync(0,
            {
                val roomDB = RoomDB.get(mContext)
                configMap[ConfigGlobal.key_provinces] =
                    roomDB.configGlobal().getByKey(ConfigGlobal.key_provinces)
                configMap[ConfigGlobal.key_types] =
                    roomDB.configGlobal().getByKey(ConfigGlobal.key_types)
                configMap
            },
            {
                configMap[ConfigGlobal.key_provinces]?.valuesByComma()?.let {
                    spin_test_province.attachDataSource(it)
                    spin_test_province.setOnSpinnerItemSelectedListener { parent, view, position, id ->
                        loadExams()
                    }
                }
                configMap[ConfigGlobal.key_types]?.valuesByComma()?.let {
                    spin_test_type.attachDataSource(it)
                    spin_test_type.setOnSpinnerItemSelectedListener { parent, view, position, id ->
                        loadExams()
                    }
                }

                filterTestPapers(null, null, null)
            }
        )


    }

    private fun updateTestPaperList(exams: MutableList<ExamSimulation>?) {
        var adapter = TestExamAdapter(this)
        adapter.data = exams ?: mutableListOf()
        recycler_view_test_papers.adapter = adapter
        recycler_view_test_papers.adapter!!.notifyDataSetChanged()

        if (exams.isNullOrEmpty()) {
            what_if_no_content.visibility = View.VISIBLE
        } else {
            what_if_no_content.visibility = View.GONE
//            what_if_no_content_gif.visibility = View.GONE
        }

    }


    private fun filterTestPapers(province: String?, type: String?, grade: String?) {
        doAsync(0,
            {
                val roomDB = RoomDB.get(mContext)
                roomDB.examSimulation().testPapersByFilters(
                    province?.replace(Constants.kao_province_all, "%") ?: "%",
                    type?.replace(Constants.kao_testType_all, "%") ?: "%",
                    grade?.replace(Constants.kao_grade_all, "%") ?: "%"
                )?.map {
                    it.myExamSimuHistory =
                        roomDB.myExamSimuHistory()
                            .getByUserIdOfExam(VariablesKao.currentUserId, it.id)
                    it
                }?.toMutableList()
            },
            {
                updateTestPaperList(it)
            })

    }

    var downloadingId: Int? = null
    override fun onClick(v: View?) {
        if (timeExpired) {
            Toast.makeText(mContext, "该功能无法使用,请点击右下角按钮续费.", Toast.LENGTH_SHORT).show()
            return
        }
        v?.let {
            if (downloadingId != null) {
                Toast.makeText(mContext, "正在下载...", Toast.LENGTH_SHORT).show()
                return
            }

            var examUI = it.getTag(R.id.paper_holder) as ExamSimulation
            if (!examUI.downloaded) {
                downloadingId = examUI.id
                downloadingDialog?.show()
//                val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(mContext)
//                dialogBuilder.setTitle("正在刷新列表...")
//                dialogBuilder.setCancelable(false)
//                dialogBuilder.setPositiveButton("确定") { dialog, which ->
//                    dialog.dismiss()
//                }
//                val dialog = dialogBuilder.show()

                doAsync(0, {
                    var roomDb = RoomDB.get(mContext)

                    RemoteAPIDataService.apis.getExamById(examUI.id).onErrorReturn {
                        examUI
                    }
                        .subscribe { exam ->
                            var links = mutableListOf<String>()
                            exam.practiceSectionsDb?.forEach { section ->
                                section.templatesDB?.forEach { template ->
                                    template.questionsDb?.forEach { question ->
                                        question.optionsDb?.forEach {
                                            roomDb.practiceAnswerOption().insert(it)
                                        }
                                        roomDb.practiceQuestion().insert(question)
                                    }
                                    template.itemMainAudioPath?.let {
                                        if (it.isNotBlank()) {
                                            links.add(it)
                                            template.itemMainAudioPath =
                                                (VariablesKao.globalApplication.filesDir.absolutePath + it).replace(
                                                    "//",
                                                    "/"
                                                )
                                        }

                                    }
                                    roomDb.practiceTemplate().insert(template)
                                }
                                roomDb.practiceSection().insert(section)
                            }
                            roomDb.examSimulation().insert(exam)
                            LogUtilKt.i("links:$links")
//                            dialog.dismiss()
                            if (links.isNotEmpty()) {
                                val url =
                                    RemoteAPIDataService.BASE_URL + "client/exam/${exam.id}/files"
                                LogUtilKt.i("url:$url")
                                DownloadUtil.get()
                                    .downloadToDataFolder(url,
                                        object : DownloadUtil.OnDownloadListener {
                                            override fun onDownloadSuccess() {
                                                //Mark download completed
                                                downloadingId = null
                                                exam.downloaded = true
                                                downloadingDialog?.dismiss()
                                                roomDb.examSimulation().insert(exam)
                                                main_downloading_progress.max = 100
                                                main_downloading_progress.secondaryProgress = 0
                                                openExam(exam)
                                                this@KaoFragment.loadExams()
                                                ActivityUtils.showToastFromThread(
                                                    mContext,
                                                    "数据下载完成"
                                                )
                                            }

                                            override fun onDownloading(
                                                progress: Int,
                                                total: Long
                                            ) {
                                                LogUtilKt.i("process:$progress / $total")
                                                main_downloading_progress.max =
                                                    (total % Int.MAX_VALUE).toInt()
                                                main_downloading_progress.secondaryProgress =
                                                    progress
                                            }

                                            override fun onDownloadFailed() {
                                                downloadingId = null
                                                exam.downloaded = false
                                                downloadingDialog?.dismiss()
                                                exam.version -= 1
                                                roomDb.examSimulation().insert(exam)
                                                main_downloading_progress.max = 100
                                                main_downloading_progress.secondaryProgress = 0
                                                ActivityUtils.showToastFromThread(
                                                    mContext,
                                                    "数据下载失败"
                                                )
                                            }

                                        })
                                ActivityUtils.showToastFromThread(mContext, "开始下载文件")
                            } else {
                                downloadingDialog?.dismiss()
                                openExam(exam)
                            }

                        }
                }, {})

            } else {
                openExam(examUI)
            }
        }
    }

    private fun openExam(exam: ExamSimulation) {
        var intent =
            Intent(mContext, KaoExamSummaryActivity::class.java)
        intent.putExtra("exam", exam)
        startActivity(intent)
    }

    var isRefreshingList = false
    override fun refresh(silentMode: Boolean) {
        if (isRefreshingList) {
            Toast.makeText(mContext, "正在刷新请稍等", Toast.LENGTH_SHORT).show()
            return
        }
        isRefreshingList = true

//        what_if_no_content_gif.visibility = View.VISIBLE
//        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(mContext)
//        dialogBuilder.setTitle("正在刷新列表")
//        dialogBuilder.setCancelable(false)
//        dialogBuilder.setPositiveButton("OK") { dialog, which ->
//            dialog.dismiss()
//        }
//        var dialog: AlertDialog? = null
        if (!silentMode) {
//            dialog = dialogBuilder.show()
            downloadingDialog?.show()
        }

        CoroutineScope(Dispatchers.IO).launch {
            var roomDb = RoomDB.get(mContext.applicationContext)
            RemoteAPIDataService.apis.getExams().onErrorReturn {
                mutableListOf<ExamSimulation>()
            }.subscribe {
                var updatedOnlineExams = it?.let {
                    it.filter { onlineExam ->
                        var examDb = roomDb.examSimulation().getById(onlineExam.id)
                        examDb?.let {
                            (onlineExam.version ?: 0) > (it.version ?: 0)
                        } ?: true
                    }.map {
                        it.downloaded = false
                        roomDb.examSimulation().insert(it)
                        it
                    }
                }
//                dialog?.dismiss()
                loadExams()
                downloadingDialog?.dismiss()
                isRefreshingList = false
                if (updatedOnlineExams.isNullOrEmpty()) {
                    ActivityUtils.showToastFromThread(mContext, "数据更新完成")
                } else {
                    ActivityUtils.showToastFromThread(mContext, "列表更新完成")
                }
            }
        }


    }

    private fun loadExams() {
        if (recycler_view_test_papers.adapter != null) {
            var province: String? = spin_test_province.selectedItem as String
            var grade: String? = VariablesMain.selectedGrade
            var type: String? = spin_test_type.selectedItem as String

            if (province == "全国") {
                province = null
            }
            if (grade == "所有年级") {
                grade = null
            }
            if (type == "全部测试") {
                type = null
            }

            filterTestPapers(province, type, grade)
        }
    }

    override fun onResume() {
        super.onResume()
        loadExams()
    }

    override fun gradeChanged() {
        loadExams()
    }

    override fun initPageIfNoData() {
        recycler_view_test_papers.adapter?.let {
            var exams = (it as TestExamAdapter).data
            if (exams.isNullOrEmpty()) {
                refresh(false)
            }
        }
    }

}