package com.viastub.kao100.db

import android.os.CountDownTimer
import android.os.Parcelable
import android.view.View
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.knziha.plod.dictionary.mdict
import kotlinx.android.parcel.Parcelize
import java.io.File
import java.util.*


//Section 1: Embed a sample dictionary .mdd .mdx
@Parcelize
@Entity
data class DictionaryConfig(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo
    var title: String,
    @ColumnInfo
    var dictFilePath: String? = null,
    @ColumnInfo
    var soundFilePath: String? = null,

    ) : Parcelable {
    @Ignore
    var soundFile: File? = null

    @Ignore
    var mdict: mdict? = null

    fun bindId(id: Int) = this.also { this.id = id }

    fun dictFile(): File? = dictFilePath?.let { File(it) }
}


//Section 2:
@Parcelize
@Entity
data class TeachingBook(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var phase: String?,//TeachingBookPhase
    @ColumnInfo
    var grade: Int = 0,

    @ColumnInfo
    var bookCoverImagePath: String?,
    @ColumnInfo
    var unitIdsString: String? = null

) : Parcelable {
    @Ignore
    var unitsDb: MutableList<TeachingBookUnitSection>? = null

    fun bindId(id: Int) = this.also { this.id = id }
    fun unitIds(): MutableList<Int>? {
        return unitIdsString?.let { it.split(",").filter { it.isNotBlank() }.map { it.toInt() } }
            ?.toMutableList()
    }

    fun coverImage(): File? {
        return bookCoverImagePath?.let { File(it) }
    }

    fun bindUnits(units: MutableList<TeachingBookUnitSection>) =
        this.also {
            unitsDb = units
            this.unitIdsString = units.map { it.id!! }.joinToString(",")
        }
}

@Parcelize
@Entity
data class TeachingBookUnitSection(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var description: String? = null,
    @ColumnInfo
    var unitCoverImagePath: String? = null,

    @ColumnInfo
    var audiosPathsInJson: String? = null, //filenames,collection
    @ColumnInfo
    var pageSnapshotImagePathsInJson: String? = null,//filenames,collection
    @ColumnInfo
    var teachingBookTranslationsIdsInString: String? = null,
    @ColumnInfo
    var bookTeachingPointIdsInString: String? = null, //point ids, collection
    @ColumnInfo
    var bookWordItemIdsInString: String? = null, //word ids, collection


) : Parcelable {
    @Ignore
    var bookTranslationsDb: MutableList<TeachingTranslation>? = null

    @Ignore
    var bookTeachingPointsDb: MutableList<TeachingPoint>? = null

    @Ignore
    var bookWordItemsDb: MutableList<TeachingBookWord>? = null


    fun bindId(id: Int) = this.also { this.id = id }

    fun coverImage(): File? {
        return unitCoverImagePath?.let { File(it) }
    }

    fun pageSnapshotPaths(): MutableList<String>? {
        var typeToken = object : TypeToken<MutableList<String>>() {}.type
        return pageSnapshotImagePathsInJson?.let { Gson().fromJson(it, typeToken) }
    }

    fun audioPaths(): MutableList<String>? {
        var typeToken = object : TypeToken<MutableList<String>>() {}.type
        return audiosPathsInJson?.let { Gson().fromJson(it, typeToken) }
    }

    fun bindTranslations(translations: MutableList<TeachingTranslation>): TeachingBookUnitSection {
        this.bookTranslationsDb = translations
        this.teachingBookTranslationsIdsInString = translations.map { it.id!! }.joinToString(",")
        return this
    }

    fun bindTeachingPoints(points: MutableList<TeachingPoint>): TeachingBookUnitSection {
        this.bookTeachingPointsDb = points
        this.bookTeachingPointIdsInString = points.map { it.id!! }.joinToString(",")
        return this
    }

    fun bindWords(words: MutableList<TeachingBookWord>): TeachingBookUnitSection {
        this.bookWordItemsDb = words
        this.bookWordItemIdsInString = words.map { it.id!! }.joinToString(",")
        return this
    }

    fun bindPageSnapshopPaths(paths: MutableList<String>?) =
        this.also { pageSnapshotImagePathsInJson = paths?.let { Gson().toJson(it) } }

    fun bindAudiosPaths(paths: MutableList<String>?) =
        this.also { audiosPathsInJson = paths?.let { Gson().toJson(it) } }

    fun teachingBookTranslationsIds(): MutableList<Int>? =
        teachingBookTranslationsIdsInString?.let {
            it.split(",").filter { it.isNotBlank() }.map { it.toInt() }
        }?.toMutableList()

    fun bookTeachingPointIds(): MutableList<Int>? =
        bookTeachingPointIdsInString?.let {
            it.split(",").filter { it.isNotBlank() }.map { it.toInt() }
        }?.toMutableList()

    fun bookWordItemIds(): MutableList<Int>? =
        bookWordItemIdsInString?.let {
            it.split(",").filter { it.isNotBlank() }.map { it.toInt() }
        }?.toMutableList()

}


@Entity
data class TeachingPoint(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo
    var point: String? = null,
    @ColumnInfo
    var explained: String? = null,
    @ColumnInfo
    var type: String? = null,//TeachingPointType,
) {
    @Ignore
    var sequence: Int? = null

    fun bindId(id: Int) = this.also { this.id = id }
}

@Entity
data class TeachingTranslation(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo
    var name: String? = null,
    @ColumnInfo
    var text_eng: String?,
    @ColumnInfo
    var text_zh: String?
) {
    @Ignore
    var sequence: Int? = null
    fun bindId(id: Int) = this.also { this.id = id }
}

@Entity
data class TeachingBookWord(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var importance: String?,//WordImportance?,
    @ColumnInfo
    var pronunciation: String?,
    @ColumnInfo
    var grammerType: String?,
    @ColumnInfo
    var meaning: String?
) {
    fun bindId(id: Int) = this.also { this.id = id }
}

enum class TeachingBookPhase {
    PRIMARY_SCHOOL, MIDDLE_SCHOOL, HIGH_SCHOOL
}

enum class TeachingPointType {
    SENTENCE_PATTERN, PHRASES_IDIOMS_COLLOCATIONS, GRAMMER;
}

enum class WordImportance {
    NORMAL, OPTIONAL, IMPORTANT
}


//Section 3: Practice
@Entity
data class PracticeTarget(
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var description: String? = null,
    @ColumnInfo
    var bookIdsString: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @Ignore
    var booksDb: MutableList<PracticeBook>? = null

    fun bindId(id: Int): PracticeTarget = this.also { this.id = id }

    fun bookIds(): MutableList<Int>? =
        bookIdsString?.split(",")?.map { it.toInt() }?.toMutableList()

    fun bindBooksDBToThis(books: MutableList<PracticeBook>?): PracticeTarget {
        booksDb = mutableListOf()
        books?.let { booksDb!!.addAll(it) }
        bookIdsString = books?.map { it.id }?.joinToString(",")
        return this
    }
}

@Parcelize
@Entity
data class PracticeBook(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var description: String? = null,
    @ColumnInfo
    var coverImagePath: String? = null,
    @ColumnInfo
    var unitSectionIdsString: String? = null,//section can be any type of blocks
) : Parcelable {

    fun coverImage(): File? = coverImagePath?.let { File(it) }

    @Ignore
    var unitSectionsDb: MutableList<PracticeSection>? = null
    fun bindId(id: Int): PracticeBook = this.also { this.id = id }

    fun unitSectionIds(): MutableList<Int>? =
        unitSectionIdsString?.split(",")?.map { it.toInt() }?.toMutableList()

    fun bindUnitSectionsDBToThis(sections: MutableList<PracticeSection>?): PracticeBook {
        unitSectionsDb = mutableListOf()
        sections?.let { unitSectionsDb!!.addAll(it) }
        unitSectionIdsString = sections?.map { it.id }?.joinToString(",")
        return this
    }
}


//Core models
@Parcelize
@Entity
data class PracticeSection(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo
    var browseMode: String = BrowseMode.SEQUENCE.name,//SEQUENCE, RANDOM
    @ColumnInfo
    var name: String? = null,
    @ColumnInfo
    var practiceTemplateIds: String? = null,
    @ColumnInfo
    var description: String? = null,
) : Parcelable {
    fun bindId(id: Int): PracticeSection {
        return this.also { this.id = id }
    }

    fun practiceTemplateIds(): MutableList<Int>? {
        return practiceTemplateIds?.split(",")?.map { it.toInt() }?.toMutableList()
    }

    @Ignore
    var templatesDB: MutableList<PracticeTemplate>? = null

    @Ignore
    var mySectionPracticeHistory: MySectionPracticeHistory? = null

    @Ignore
    var displaySeq: Int = 0

    fun bindTemplatesDbToThis(templates: MutableList<PracticeTemplate>?): PracticeSection {
        templatesDB = mutableListOf()
        templates?.let {
            templatesDB!!.addAll(it)
        }
        practiceTemplateIds = templates?.map { it.id }?.joinToString(",")
        return this
    }

    fun totalScores(): Double {
        return templatesDB?.map { it.totalScore }?.sum() ?: 0.0
    }

    fun totalTimeInMinutes(): Double {
        return templatesDB?.map { it.totalTimeInMinutes }?.sum() ?: 0.0
    }


}

@Entity
data class PracticeTemplate(
    @ColumnInfo
    var category: String? = null,
    @ColumnInfo
    var requirement: String? = null,
    @ColumnInfo
    var itemMainText: String? = null,
    @ColumnInfo
    var itemMainAudioPath: String? = null,
    @ColumnInfo
    var hints: String? = null,
    @ColumnInfo
    var keyPoints: String? = null,
    @ColumnInfo
    var layoutQuestionsPerRow: Int = 1,
    @ColumnInfo
    var totalScore: Double = 0.0,
    @ColumnInfo
    var totalTimeInMinutes: Double = 0.0,
    @ColumnInfo
    var practiceQuestionIds: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @Ignore
    var submitted: Boolean = false

    fun practiceQuestions(): MutableList<Int>? {
        return practiceQuestionIds?.split(",")?.map { it.toInt() }?.toMutableList()
    }

    @Ignore
    var questionsDb: MutableList<PracticeQuestion>? = null

    @Ignore
    var countDownTimer: CountDownTimer? = null

    fun bindId(id: Int): PracticeTemplate = this.also { this.id = id }

    fun bindQuestionsDbToThis(questions: MutableList<PracticeQuestion>): PracticeTemplate {
        questionsDb = mutableListOf()
        questionsDb!!.addAll(questions)
        practiceQuestionIds = questions.map { it.id }.joinToString(",")
        return this
    }

    //questionId->MutableList<Answers>
    fun pooledQuestionStandardAnswers(): MutableMap<Int, MutableList<String>> {
        return questionsDb?.map {
            Pair(
                it.id!!,
                (it.optionsDb?.flatMap { it.correctAnswers() ?: mutableListOf() })?.toMutableList()
                    ?: mutableListOf()
            )
        }?.toMap()?.toMutableMap() ?: mutableMapOf()
    }

}

@Entity
data class PracticeQuestion(
    @ColumnInfo
    var type: String = QuestionType.SELECT.name,//QuestionType,//FILL, SELECT
    @ColumnInfo
    var text: String? = null,
    @ColumnInfo
    var answerStandard: String? = null,//display only after submit answers
    @ColumnInfo
    var answerKeyPoints: String? = null,
    @ColumnInfo
    var layoutOptionsPerRow: Int = 1,//can auto adjust based on answer's length 30
    @ColumnInfo
    var requireAnsweredOptionsNo: Int = 1,
    @ColumnInfo
    var practiceAnswerOptionIds: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @Ignore
    var displaySeq: Int = 0

    @Ignore
    var scoreEarned: Double = 0.0

    //Used to hold user's input/selected value
    @Ignore
    var usersAnswers: MutableMap<Int, String> = mutableMapOf()

    @Ignore
    var userAnswersChecks: MutableMap<Int, Boolean?> = mutableMapOf()

    @Ignore
    var optionsDb: MutableList<PracticeAnswerOption>? = null

    @Ignore
    var myQuestionActionDb: MyQuestionAction? = null

    @Ignore
    var myQuestionAnsweredHistoryDb: MyQuestionAnsweredHistory? = null

    fun answerStandardX(): String? {
        return answerStandard ?: optionsDb?.flatMap { it.correctAnswers() ?: mutableSetOf() }
            ?.joinToString(",")
    }

    fun optionPractices(): MutableList<Int>? {
        return practiceAnswerOptionIds?.split(",")?.map { it.toInt() }?.toMutableList()
    }

    fun bindId(id: Int): PracticeQuestion = this.also { this.id = id }

    fun bindOptionsDbToThis(options: MutableList<PracticeAnswerOption>): PracticeQuestion {
        optionsDb = mutableListOf()
        optionsDb!!.addAll(options)
        practiceAnswerOptionIds = options.map { it.id }.joinToString(",")
        return this
    }
}

@Entity
data class PracticeAnswerOption(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo
    var layoutUI: String? = LayoutUI.TEXT_VIEW.name,//AnswerOptionUI?,//EDIT_TEXT,TEXT_VIEW,IMAGE_VIEW
    @ColumnInfo
    var displayText: String? = null,//label value,place holder value,description for image
    @ColumnInfo
    var correctAnswersSplitByPipes: String? = null
) {
    @Ignore
    var layoutUIObject: View? = null

    fun correctAnswers(): MutableList<String>? {
        return correctAnswersSplitByPipes?.let {
            return it.split("|")?.toMutableList()
        }
    }

    fun bindId(id: Int): PracticeAnswerOption = this.also { this.id = id }

    @Ignore
    var displaySeq: Int = 0
}

enum class LayoutUI {
    EDIT_TEXT, TEXT_VIEW, IMAGE_VIEW, CORRECTION
}

enum class BrowseMode {
    SEQUENCE, RANDOM
}

enum class QuestionType {
    FILL, SELECT, CORRECT
}

enum class AnswerOptionUI {
    EDIT_TEXT, TEXT_VIEW, IMAGE_VIEW
}

//Section 4: Tests
@Parcelize
@Entity
data class ExamSimulation(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var province: String?,
    @ColumnInfo
    var city: String?,
    @ColumnInfo
    var testType: String?,
    @ColumnInfo
    var grade: String?,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var tags: String?,
    @ColumnInfo
    var practiceSectionIds: String?

) : Parcelable {
    @Ignore
    var myExamSimuHistory: MyExamSimuHistory? = null

    @Ignore
    var practiceSectionsDb: MutableList<PracticeSection>? = null

    fun practiceSections(): MutableList<Int>? {
        return practiceSectionIds?.split(",")?.map { it.toInt() }?.toMutableList()
    }

    fun tags(): MutableList<String> {
        var data = mutableListOf<String>()
        province?.let { data?.add(it) }
        testType?.let { data?.add(it) }
        grade?.let { data?.add(it) }
        tags?.split(",")?.let { data.addAll(it) }
        return data
    }

    fun totalScores(): Double {
        return practiceSectionsDb?.map { it.totalScores() }?.sum() ?: 0.0
    }

    fun totalTimeInMinutes(): Double {
        return practiceSectionsDb?.map { it.totalTimeInMinutes() }?.sum() ?: 0.0
    }
}


@Entity
data class GlobalConfigKaoFiltersProvince(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var province: String,
    @ColumnInfo
    var types: String,
    @ColumnInfo
    var description: String? = null
) {
    fun types(): MutableList<String> {
        return types.split(",").toMutableList()
    }

}

@Entity
data class GlobalConfigKaoFiltersType(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var type: String,
    @ColumnInfo
    var grades: String,
    @ColumnInfo
    var description: String? = null
) {
    fun grades(): MutableList<String> {
        return grades.split(",").toMutableList()
    }
}

@Entity
data class MyUser(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var officialName: String,
    @ColumnInfo
    var nickName: String? = null,
    @ColumnInfo
    var avtarImagePath: String? = null,
    @ColumnInfo
    var dateAdded: String = Date().toString(),
)

@Parcelize
@Entity
data class MySectionPracticeHistory(
    @ColumnInfo
    var userId: Int,
    @ColumnInfo
    var sectionId: Int,
    @ColumnInfo
    var myFinishedTemplateIdsSortedString: String? = null,

    ) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    fun myFinishedTemplateIds(): SortedSet<Int>? {
        return myFinishedTemplateIdsSortedString?.split(",")?.filter { it.isNotEmpty() }
            ?.map { it.toInt() }?.toSortedSet()
    }

    fun setMyFinishedTemplateIdsSortedString(templateIds: MutableList<Int>?): MySectionPracticeHistory {
        templateIds?.let {
            myFinishedTemplateIdsSortedString = templateIds.joinToString(",")
        }
        return this
    }

}

@Entity
data class MyExamSimuHistory(
    @ColumnInfo
    var userId: Int,
    @ColumnInfo
    var examSimulationId: Int,
    @ColumnInfo
    var isFavorite: Boolean? = null,
    @ColumnInfo
    var note: String? = null,
    @ColumnInfo
    var tags: String? = null,
    @ColumnInfo
    var myScores: Double? = null,
    @ColumnInfo
    var myTotalCorrects: Int? = null,
    @ColumnInfo
    var myTotalWrongs: Int? = null,
    @ColumnInfo
    var myTotalMissing: Int? = null,
    @ColumnInfo
    var dateAdded: String = Date().toString()
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

@Entity
data class MyQuestionAction(
    @ColumnInfo
    var userId: Int,
    @ColumnInfo
    var practiceQuestionId: Int,
    @ColumnInfo
    var isFavorite: Boolean? = null,
    @ColumnInfo
    var note: String? = null,
    @ColumnInfo
    var tags: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}

@Entity
data class MyQuestionAnsweredHistory(
    @ColumnInfo
    var userId: Int,
    @ColumnInfo
    var practiceQuestionId: Int,
    @ColumnInfo
    var answersIntStringMapInJson: String? = null,
    @ColumnInfo
    var answerIsCorrect: Boolean? = null,//正确，错误，没回答
    @ColumnInfo
    var dateAdded: String = Date().toString(),

    @ColumnInfo
    var optionalPracticeTemplateId: Int? = null,
    @ColumnInfo
    var optionalPracticeSectionId: Int? = null,
    @ColumnInfo
    var optionalExamSimulationId: Int? = null,
    @ColumnInfo
    var optionalPracticeBookId: Int? = null,
    @ColumnInfo
    var optionalPracticeTargetId: Int? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    fun getMyAnswers(): MutableMap<Int, String>? {
        return answersIntStringMapInJson?.let {
            var typeToken = object : TypeToken<MutableMap<Int, String>>() {}.type
            return Gson().fromJson(it, typeToken)
        }
    }

    fun setMyAnswersJson(answers: MutableMap<Int, String>?): MyQuestionAnsweredHistory {
        answers?.let {
            this.answersIntStringMapInJson = Gson().toJson(it)
        }
        return this
    }
}



























