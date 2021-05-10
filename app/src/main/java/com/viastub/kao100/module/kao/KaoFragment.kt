package com.viastub.kao100.module.kao

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.TestExamAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.beans.Grade
import com.viastub.kao100.beans.Province
import com.viastub.kao100.beans.TestType
import com.viastub.kao100.db.ExamSimulation
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.http.DownloadUtil
import com.viastub.kao100.http.RemoteAPIDataService
import com.viastub.kao100.utils.Constants
import com.viastub.kao100.utils.VariablesKao
import com.yechaoa.yutilskt.LogUtilKt
import kotlinx.android.synthetic.main.fragment_kao.*

class KaoFragment : BaseFragment(), View.OnClickListener {


    override fun id(): Int {
        return R.layout.fragment_kao
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {

        //https://www.jianshu.com/p/e68a0b5fd383
        recycler_view_test_papers.addItemDecoration(
            DividerItemDecoration(
                mContext,
                DividerItemDecoration.VERTICAL
            )
        )

        doAsync(0,
            {
                val roomDB = RoomDB.get(mContext)
                Pair(
                    roomDB.globalConfigKaoFiltersProvinces().getAll(),
                    roomDB.globalConfigKaoFiltersTypes().getAll()
                )
            },
            {
                val provincesFromTable = it.first
                var typesFromTable = it.second

                var provinces = provincesFromTable.mapIndexed { provinceIdx, province ->
                    var testTypesDb = province.types().map { typeStr ->
                        var typeDB = typesFromTable.find { it.type == typeStr }!!

                        var gradeModels = typeDB?.grades()?.mapIndexed { gradeIdx, gradeDbName ->
                            Grade(gradeIdx + 1, gradeDbName)
                        }.toMutableList()

                        TestType(typeDB.id, typeDB.type, gradeModels)
                    }.toMutableList()

                    Province(provinceIdx + 1, province.province, testTypesDb)
                }.toMutableList()

                applyToUi(provinces)

                //load all
                filterTestPapers(null, null, null)


            }
        )


    }

    private fun updateTestPaperList(exams: MutableList<ExamSimulation>?) {
        var adapter = TestExamAdapter(this)
        adapter.data = exams ?: mutableListOf()
        recycler_view_test_papers.adapter = adapter
        recycler_view_test_papers.adapter!!.notifyDataSetChanged()
        LogUtilKt.i("data:${exams?.size}")
    }

    fun applyToUi(provinces: MutableList<Province>) {
        val datasetProvince: List<String> = provinces.map { it.shortName }
        spin_test_province.attachDataSource(datasetProvince)
        spin_test_province.setOnSpinnerItemSelectedListener { parent, view, position, id ->

            var newProvince = spin_test_province.selectedItem as String
            var oldType = spin_test_type.selectedItem as String
            var oldGrade = spin_test_grade.selectedItem as String

            var typesAvailabeForSelected =
                provinces.find { it.shortName == newProvince }?.testTypes
            typesAvailabeForSelected?.let {
                var typeOptions = it.map { it.shortName }
                spin_test_type.attachDataSource(typeOptions)
                if (typeOptions.contains(oldType)) {
                    spin_test_type.selectedIndex = typeOptions.indexOf(oldType)
                }
                oldType = spin_test_type.selectedItem as String
                typesAvailabeForSelected.find { it.shortName == oldType }?.grades?.let {
                    var gradeOptions = it.map { it.shortName }
                    spin_test_grade.attachDataSource(gradeOptions)
                    if (gradeOptions.contains(oldGrade)) {
                        spin_test_grade.selectedIndex = gradeOptions.indexOf(oldGrade)
                    }
                }

            }


            filterTestPapers(newProvince, oldType, oldGrade)


        }

        val datasetType: List<String> = provinces.first().testTypes?.map { it.shortName }!!
        spin_test_type.attachDataSource(datasetType)
        spin_test_type.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            var oldProvince = provinces[spin_test_province.selectedIndex]
            var oldGrade = spin_test_grade.selectedItem as String
            var newType = spin_test_type.selectedItem as String

            var selectedType = oldProvince.testTypes?.find { it.shortName == newType }
            selectedType?.let {
                var gradeOptions = it.grades!!.map { it.shortName }
                spin_test_grade.attachDataSource(gradeOptions)
                if (gradeOptions.contains(oldGrade)) {
                    spin_test_grade.selectedIndex = gradeOptions.indexOf(oldGrade)
                }
            }

            filterTestPapers(oldProvince.shortName, newType, oldGrade)

        }


        val datasetGrade: List<String> =
            provinces.first().testTypes!!.first().grades!!.map { it.shortName }
        spin_test_grade.attachDataSource(datasetGrade)
        spin_test_grade.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            var province = spin_test_province.selectedItem as String
            var grade = spin_test_grade.selectedItem as String
            var type = spin_test_type.selectedItem as String

            filterTestPapers(province, type, grade)
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

    override fun onClick(v: View?) {
        v?.let {
            var examUI = it.getTag(R.id.paper_holder) as ExamSimulation

            if (!examUI.downloaded) {
                toast("下载中...")
                doAsync(0, {
                    var roomDb = RoomDB.get(mContext)

                    RemoteAPIDataService.apis.getExamById(examUI.id).subscribe { exam ->
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
                                    }
                                    template.itemMainAudioPath =
                                        (VariablesKao.globalApplication.filesDir.absolutePath + it).replace(
                                            "//",
                                            "/"
                                        )
                                }
                                roomDb.practiceTemplate().insert(template)
                            }
                            roomDb.practiceSection().insert(section)
                        }
                        roomDb.examSimulation().insert(exam)
                        LogUtilKt.i("links:$links")
                        if (links.isNotEmpty()) {
                            val url = RemoteAPIDataService.BASE_URL + "client/exam/${exam.id}/files"
                            LogUtilKt.i("url:$url")
                            DownloadUtil.get()
                                .downloadToDataFolder(url,
                                    object : DownloadUtil.OnDownloadListener {
                                        override fun onDownloadSuccess() {
                                            //Mark download completed
                                            exam.downloaded = true
                                            roomDb.examSimulation().insert(exam)
                                            main_downloading_progress.max = 100
                                            main_downloading_progress.secondaryProgress = 0
                                            openExam(exam)
                                        }

                                        override fun onDownloading(
                                            progress: Int,
                                            total: Long
                                        ) {
                                            LogUtilKt.i("process:$progress / $total")
                                            main_downloading_progress.max =
                                                (total % Int.MAX_VALUE).toInt()
                                            main_downloading_progress.secondaryProgress = progress
                                        }

                                        override fun onDownloadFailed() {
                                            toast("下载失败,请重试!")
                                        }

                                    })

                        } else {
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

    override fun refresh() {
        if (recycler_view_test_papers.adapter != null) {
            var province: String? = spin_test_province.selectedItem as String
            var grade: String? = spin_test_grade.selectedItem as String
            var type: String? = spin_test_type.selectedItem as String

            if (province == "全国") {
                province = null
            }
            if (grade == "全部") {
                grade = null
            }
            if (type == "所有") {
                type = null
            }

            filterTestPapers(province, type, grade)
        }
    }

}