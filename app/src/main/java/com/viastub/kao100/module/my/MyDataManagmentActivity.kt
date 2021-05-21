package com.viastub.kao100.module.my

import android.widget.Toast
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.RoomDB
import kotlinx.android.synthetic.main.activity_ci_word_setting.header_back
import kotlinx.android.synthetic.main.activity_my_db_data.*


class MyDataManagmentActivity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_my_db_data
    }

    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        db_data_delete.setOnClickListener {
            var roomDB = RoomDB.get(applicationContext)
            if (db_data_cb_exams.isChecked) {
                doAsync {
                    roomDB.examSimulation().getAll()?.forEach {
                        it.practiceSections()?.let {
                            roomDB.practiceSection().getByIds(it)?.forEach {
                                roomDB.practiceSection().delete(it)
                            }
                        }
                    }
                    roomDB.examSimulation().deleteAll()
                }
            }

            if (db_data_cb_excercises.isChecked) {
                doAsync {
                    roomDB.practiceTarget().getAll()?.forEach {
                        it.bookIds()?.forEach {
                            var book = roomDB.practiceBook().getById(it)
                            book?.unitSectionIds()?.let {
                                var units = roomDB.teachingBookUnitSection().getByIds(it)
                                units?.forEach { roomDB.teachingBookUnitSection().delete(it) }
                            }
                            book?.let {
                                roomDB.practiceBook().delete(it)
                            }
                        }
                    }
                    roomDB.practiceTarget().deleteAll()
                }
            }

            if (db_data_cb_textbooks.isChecked) {
                doAsync {
                    roomDB.teachingBook().getAll()?.forEach {
                        it.unitIds()?.let {
                            var units = roomDB.teachingBookUnitSection().getByIds(it)
                            units?.forEach { roomDB.teachingBookUnitSection().delete(it) }
                        }
                    }
                    roomDB.teachingBook().deleteAll()
                }
            }


            if (db_data_cb_my_notes.isChecked) {
                doAsync {
                    roomDB.myCollectedNote().deleteAll()
                }
            }
            if (db_data_cb_my_questions.isChecked) {
                doAsync {
                    roomDB.mySectionPracticeHistory().deleteAll()
                    roomDB.myQuestionAnsweredHistory().deleteAll()
                    roomDB.myQuestionAction().deleteAll()
                }
            }
            if (db_data_cb_my_words.isChecked) {
                doAsync {
                    roomDB.myWordHistory().deleteAll()
                }
            }

            Toast.makeText(this, "已删除选中数据", Toast.LENGTH_SHORT).show()
        }

    }


}