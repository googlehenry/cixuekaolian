package com.qianli.cixuekaolian.module.kao

import android.os.Bundle
import android.view.View
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.base.BaseFragment
import com.qianli.cixuekaolian.beans.Grade
import com.qianli.cixuekaolian.beans.Province
import com.qianli.cixuekaolian.beans.TestType
import kotlinx.android.synthetic.main.fragment_kao.*

class KaoFragment : BaseFragment() {


    override fun id(): Int {
        return R.layout.fragment_kao
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {

        var provinces = chinaProvinces()

        val datasetProvince: List<String> = provinces.map { it.shortName }
        spin_test_province.attachDataSource(datasetProvince)
        spin_test_province.setOnSpinnerItemSelectedListener { parent, view, position, id ->

            var newProvince = spin_test_province.selectedItem
            var oldType = spin_test_type.selectedItem
            var oldGrade = spin_test_grade.selectedItem

            var typesAvailabeForSelected =
                provinces.find { it.shortName == newProvince }?.testTypes
            typesAvailabeForSelected?.let {
                var typeOptions = it.map { it.shortName }
                spin_test_type.attachDataSource(typeOptions)
                if (typeOptions.contains(oldType)) {
                    spin_test_type.selectedIndex = typeOptions.indexOf(oldType)
                }

                it.first().grades?.let {
                    var gradeOptions = it.map { it.shortName }
                    spin_test_grade.attachDataSource(gradeOptions)
                    if (gradeOptions.contains(oldGrade)) {
                        spin_test_grade.selectedIndex = gradeOptions.indexOf(oldGrade)
                    }
                }


            }


        }

        val datasetType: List<String> = provinces.first().testTypes?.map { it.shortName }!!
        spin_test_type.attachDataSource(datasetType)
        spin_test_type.setOnSpinnerItemSelectedListener { parent, view, position, id ->
            var oldProvince = provinces[spin_test_province.selectedIndex]
            var oldGrade = spin_test_grade.selectedItem
            var newType = spin_test_type.selectedItem

            var selectedType = oldProvince.testTypes?.find { it.shortName == newType }
            selectedType?.let {
                var gradeOptions = it.grades!!.map { it.shortName }
                spin_test_grade.attachDataSource(gradeOptions)
                if (gradeOptions.contains(oldGrade)) {
                    spin_test_grade.selectedIndex = gradeOptions.indexOf(oldGrade)
                }
            }

        }


        val datasetGrade: List<String> =
            provinces.first().testTypes!!.first().grades!!.map { it.shortName }
        spin_test_grade.attachDataSource(datasetGrade)
    }

    private fun chinaProvinces() = mutableListOf(
        province(1, "全国"),
        provinceShanghai(1, "上海"),
        province(1, "新疆"),
        province(1, "青海"),
        province(1, "西藏"),
        province(1, "湖南"),
        province(1, "湖北"),
        province(1, "河南"),
        province(1, "河北"),
        province(1, "陕西"),
        province(1, "内蒙古"),
        province(1, "山东"),
        province(1, "山西"),
        province(1, "北京"),
        province(1, "天津"),
        province(1, "黑龙江"),
        province(1, "吉林"),
        province(1, "辽宁"),
        province(1, "江苏"),
        province(1, "上海"),
        province(1, "浙江"),
        province(1, "山西"),
        province(1, "广西"),
        province(1, "广东"),
        province(1, "云南"),
        province(1, "贵州"),
        province(1, "海南"),
        province(1, "台湾"),
        province(1, "香港"),
        province(1, "澳门"),
    )

    private fun provinceShanghai(id: Int, shortName: String) = Province(
        id, shortName, mutableListOf<TestType>(
            TestType(
                0, "全部测试", five4grades()
            ),
            TestType(
                1, "高考", naCEEGrades()
            ),
            TestType(
                2, "中考", naHEEGrades()
            ),
            TestType(
                3, "期末考试", five4grades()
            ),
            TestType(
                4, "期中考试", five4grades()
            )
        )
    )

    private fun province(id: Int, shortName: String) = Province(
        id, shortName, mutableListOf<TestType>(
            TestType(
                0, "全部测试", six3grades()
            ),
            TestType(
                1, "高考", naCEEGrades()
            ),
            TestType(
                2, "中考", naHEEGrades()
            ),
            TestType(
                3, "期末考试", six3grades()
            ),
            TestType(
                4, "期中考试", six3grades()
            )
        )
    )

//    private fun defaultGrades() = mutableListOf<Grade>(
//        Grade(0, "K12", "不分年级")
//    )

    private fun naCEEGrades() = mutableListOf<Grade>(
        Grade(0, "K12", "不分年级"),
        Grade(0, "K12", "不分年级")
    )

    private fun naHEEGrades() = mutableListOf<Grade>(
        Grade(0, "K12", "不分年级"),
        Grade(0, "K12", "不分年级")
    )

    private fun five4grades() = mutableListOf<Grade>(
        Grade(0, "K12", "所有年级"),
        Grade(1, "小学", "1年级"),
        Grade(2, "小学", "2年级"),
        Grade(3, "小学", "3年级"),
        Grade(4, "小学", "4年级"),
        Grade(5, "小学", "5年级"),
        Grade(6, "小升初", "预备班"),
        Grade(7, "初中", "初1"),
        Grade(8, "初中", "初2"),
        Grade(9, "初中", "初3"),
        Grade(10, "高中", "高1"),
        Grade(11, "高中", "高2"),
        Grade(12, "高中", "高3")
    )


    private fun six3grades() = mutableListOf<Grade>(
        Grade(0, "K12", "所有年级"),
        Grade(1, "小学", "1年级"),
        Grade(2, "小学", "2年级"),
        Grade(3, "小学", "3年级"),
        Grade(4, "小学", "4年级"),
        Grade(5, "小学", "5年级"),
        Grade(6, "小学", "6年级"),
        Grade(7, "初中", "初1"),
        Grade(8, "初中", "初2"),
        Grade(9, "初中", "初3"),
        Grade(10, "高中", "高1"),
        Grade(11, "高中", "高2"),
        Grade(12, "高中", "高3")
    )


}