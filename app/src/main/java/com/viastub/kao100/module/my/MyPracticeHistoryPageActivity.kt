package com.viastub.kao100.module.my

import android.content.Intent
import android.view.View
import android.widget.RadioGroup
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.viastub.kao100.R
import com.viastub.kao100.adapter.CategoryMapAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.beans.LianContext
import com.viastub.kao100.db.*
import com.viastub.kao100.utils.QuestionSearchMode
import com.viastub.kao100.utils.VariablesKao
import com.viastub.kao100.utils.VariablesLian
import com.viastub.kao100.utils.VariablesMy
import kotlinx.android.synthetic.main.my_ci.header_back
import kotlinx.android.synthetic.main.my_ci.recycler_view_high
import kotlinx.android.synthetic.main.my_ci.searchView
import kotlinx.android.synthetic.main.my_errorbook.*
import kotlinx.android.synthetic.main.my_errorbook.radiogroup_group


class MyPracticeHistoryPageActivity : BaseActivity(), View.OnClickListener {
    override fun id(): Int {
        return R.layout.my_errorbook
    }

    var templateMap: MutableMap<Int, PracticeTemplate> = mutableMapOf()
    var questionMap: MutableMap<Int, MyPracticeHistory> = mutableMapOf()

    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        header_action_refresh.setOnClickListener {
            loadQuestionHistory()
        }

        radiogroup_group.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.radiogroup_questions_all -> VariablesMy.questionSearchMode =
                        QuestionSearchMode.ALL
                    R.id.radiogroup_questions_favorite_only -> VariablesMy.questionSearchMode =
                        QuestionSearchMode.FAVORITE
                    R.id.radiogroup_questions_note_only -> VariablesMy.questionSearchMode =
                        QuestionSearchMode.NOTED
                    R.id.radiogroup_questions_corrects -> VariablesMy.questionSearchMode =
                        QuestionSearchMode.CORRECTED
                    R.id.radiogroup_questions_errors -> VariablesMy.questionSearchMode =
                        QuestionSearchMode.ERRORED
                    R.id.radiogroup_questions_skips -> VariablesMy.questionSearchMode =
                        QuestionSearchMode.SKIPPED
                }

                searchItem(VariablesMy.myCurrentSearchedWord)?.let { updateGrid(it) }
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                VariablesMy.myCurrentSearchedWord = newText
                updateGrid(searchItem(newText))
                return true
            }

        })


        searchView.isSubmitButtonEnabled = false
        searchView.onActionViewExpanded()
        radiogroup_questions_errors.isChecked = true

        loadQuestionHistory()

    }

    private fun loadQuestionHistory() {
        awaitAsync({
            var roomDB = RoomDB.get(applicationContext)
            var questionActions = roomDB.myQuestionAction().getByUser(VariablesKao.currentUserId)

            var questionActionMap = questionActions?.map { it.practiceQuestionId to it }?.toMap()
            var templateIds =
                questionActions?.map { it.practiceTemplateId!! }?.toSet()?.toList() ?: listOf()

            var templates = roomDB.practiceTemplate().getByIds(templateIds.toMutableList())
            templates?.map { it.id!! to it }?.toMap()?.let {
                templateMap = it.toMutableMap()
            }

            var ids = questionActionMap?.keys?.toMutableList() ?: mutableListOf()

            var questions = roomDB.practiceQuestion().getByIds(ids)
            var histories = roomDB.myQuestionAnsweredHistory().getByQuestionIds(ids)
            var historyMap = histories?.map { it.practiceQuestionId to it }?.toMap()

            questions.forEach {
                it.myQuestionActionDb = questionActionMap?.get(it.id)
                it.myQuestionAnsweredHistoryDb = historyMap?.get(it.id)
            }


            var myMap = questions.map {
                it.id!! to
                        MyPracticeHistory(
                            it.myQuestionActionDb?.practiceTemplateId,
                            it.id,
                            it.myQuestionAnsweredHistoryDb?.correctAttemptNo ?: 0,
                            it.myQuestionAnsweredHistoryDb?.wrongAttemptNo ?: 0,
                            it.myQuestionAnsweredHistoryDb?.skippedAttemptNo ?: 0,
                            it.myQuestionActionDb?.isFavorite,
                            it.myQuestionActionDb?.note,
                            it.text,
                            it.answerKeyPoints
                        )
            }.toMap()

            questionMap = myMap.toMutableMap()
        }, {
            updateGrid(searchItem(null))
        })
    }

    private fun searchItem(newText: String?): MutableList<CategoryMap> {
        var finalFiltered: MutableList<CategoryMap> = mutableListOf()


        var templatesAfterFilters = mutableSetOf<Int>()


        var matchedTemplatesCountMap = when (VariablesMy.questionSearchMode) {
            QuestionSearchMode.FAVORITE -> {
                questionMap.values?.filter {
                    it.isFavorite == true
                }.groupBy { it.templateId!! }.toMap()
            }
            QuestionSearchMode.NOTED -> {
                questionMap.values?.filter {
                    it.note?.isNotBlank() == true
                }.groupBy { it.templateId!! }.toMap()
            }
            QuestionSearchMode.CORRECTED -> {
                questionMap.values?.filter {
                    it.correctAttempts > 0
                }.groupBy { it.templateId!! }.toMap()
            }
            QuestionSearchMode.ERRORED -> {
                questionMap.values?.filter {
                    it.wrongAttempts > 0
                }.groupBy { it.templateId!! }.toMap()
            }
            QuestionSearchMode.SKIPPED -> {
                questionMap.values?.filter {
                    it.skippedAttempts > 0
                }.groupBy { it.templateId!! }.toMap()
            }
            else -> questionMap.values?.groupBy { it.templateId!! }.toMap()
        }

        templatesAfterFilters.addAll(matchedTemplatesCountMap.keys)


        var searchInQuestionMapCount: Map<Int?, List<MyPracticeHistory>>? = null

        var searchedTextFilters = newText?.let {
            var searchInQuestionMap = questionMap.values?.filter {
                it.questionKeyPoints?.contains(newText, true) == true ||
                        it.questionMainText?.contains(newText, true) == true ||
                        it.note?.contains(newText, true) == true
            }.groupBy { it.templateId }.toMap()

            var searchInTemplate = templateMap.values?.filter {
                it.keyPoints?.contains(newText, true) == true ||
                        it.itemMainText?.contains(newText, true) == true ||
                        it.requirement?.contains(newText, true) == true
            }.map { it.id }.toSet()

            searchInQuestionMapCount = searchInQuestionMap

            searchInQuestionMap.keys + searchInTemplate
        }

        var categoryMapList = templateMap.filterKeys {
            searchedTextFilters?.contains(it) != false && templatesAfterFilters.contains(it)
        }.let {
            it.values?.groupBy {
                it.category
            }.filterKeys { it != null }.toMap().map {
                CategoryMap(
                    it.key, it.value?.size, it.value?.map {
                        var tsize = matchedTemplatesCountMap.get(it.id)?.size
                        var qsize = searchInQuestionMapCount?.get(it.id)?.size

                        var matchedSize = if (qsize == null) {
                            tsize ?: 0
                        } else {
                            if (tsize != null) Math.min(tsize, qsize) else qsize
                        }

                        matchedSize
                    }.sum(),
                    it.value?.toMutableList()
                )
            }.toMutableList()
        }

        finalFiltered.addAll(categoryMapList)

        return finalFiltered
    }

    private fun updateGrid(categoryData: MutableList<CategoryMap>) {
        var adapter = CategoryMapAdapter(this)
        adapter.data = categoryData

        recycler_view_high.layoutManager = GridLayoutManager(this, 2)
        recycler_view_high.adapter = adapter
    }

    override fun onClick(v: View?) {
        var categoryMap = v?.getTag(R.id.question_holder_root)?.let { it as CategoryMap }!!

        var templates = categoryMap.templates
        templates.forEach { it.submitted = true }
        var dummySection = PracticeSection(-1, BrowseMode.SEQUENCE.name, categoryMap.category)
            .bindTemplatesDbToThis(templates)

        var intent = Intent(this, MyLianPage0ActivityPractice::class.java)
        var secs = arrayListOf<PracticeSection>(dummySection)

        intent.putExtra("context", LianContext(PracticeBook(-1, name = "我的习题"), secs, null, null))

        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        VariablesLian.lianContext = null
        VariablesLian.availableTemplatesMap.clear()
        VariablesLian.availableTemplateIds.clear()
        VariablesLian.currentTemplateIdIdx = -1
    }

}

data class CategoryMap(
    var category: String?,
    var countTemplates: Int = 0,
    var countQuestions: Int = 0,
    var templates: MutableList<PracticeTemplate> = mutableListOf()
)

data class MyPracticeHistory(
    var templateId: Int?,
    var questionId: Int?,
    var correctAttempts: Int = 0,
    var wrongAttempts: Int = 0,
    var skippedAttempts: Int = 0,
    var isFavorite: Boolean? = null,
    var note: String? = null,
    var questionMainText: String? = null,
    var questionKeyPoints: String? = null
)