package com.viastub.kao100.module.kao

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.TestPaperAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.beans.Grade
import com.viastub.kao100.beans.Province
import com.viastub.kao100.beans.TestPaper
import com.viastub.kao100.beans.TestType
import com.viastub.kao100.db.ExamSimulation
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.Constants
import kotlinx.android.synthetic.main.fragment_kao.*

class KaoFragment : BaseFragment() {


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
                roomDB.examSimulation().getAll()
            },
            {
                updateTestPaperList(it)
            })


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
            }
        )


    }

    private fun updateTestPaperList(it: List<ExamSimulation>?) {
        var testPapers = it?.map { exam ->
            TestPaper(exam.id, exam.name, exam.tags?.split(",")?.toMutableList())
        }?.toMutableList()

        testPapers?.let {
            var adapter = TestPaperAdapter()
            adapter.data = it
            recycler_view_test_papers.adapter = adapter
        }
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
                )
            },
            {
                updateTestPaperList(it)
            })

    }

}