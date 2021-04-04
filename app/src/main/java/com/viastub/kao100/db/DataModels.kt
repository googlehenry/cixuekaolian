package com.viastub.kao100.db

import android.os.CountDownTimer
import android.os.Parcelable
import android.view.View
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.File


//Section 1: Embed a sample dictionary .mdd .mdx
@Entity
data class DictionaryConfig(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var title: String,
    @ColumnInfo
    var dictFilePath: String?,
    @ColumnInfo
    var soundFilePath: String?,

    ) {
    @Ignore
    var soundFile: File? = null

    @Ignore
    var dictFile: File? = null
}


//Section 2:
@Entity
data class Book(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var phase: String?,//初中,高中
    @ColumnInfo
    var publishedBy: String,
    @ColumnInfo
    var publishedVersion: Int,
    @ColumnInfo
    var grade: String,
    @ColumnInfo
    var title: String,
    @ColumnInfo
    var type: String,//Textbook,GrammarBook
    @ColumnInfo
    var bookCoverImagePath: String?,

    ) {
    @Ignore
    var bookCoverImage: File? = null

    @Ignore
    var bookUnits: MutableList<BookUnit>? = null

    @Ignore
    var appendices: MutableList<BookAppendix>? = null
}

@Entity
data class BookAppendix(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var description: String?,

    ) {
    @Ignore
    var pageSnapshotImages: MutableList<File>? = null
}

@Entity
data class BookUnit(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var description: String?,
    @ColumnInfo
    var unitCoverImage: String?,


    ) {
    @Ignore
    var unitCover: File? = null

    @Ignore
    var bookUnitPages: BookUnitPages? = null

    @Ignore
    var bookUnitWords: BookUnitWords? = null
}

@Entity
data class BookUnitWords(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var soundFilePath: String?,

    ) {
    @Ignore
    var soundFile: File? = null

    @Ignore
    var words: MutableList<BookWord>? = null
}

@Entity
data class BookUnitPages(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var soundFilePath: String?,

    ) {
    @Ignore
    var soundFile: File? = null

    @Ignore
    var pages: MutableList<BookPage>? = null
}

@Entity
data class BookPage(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var pageNo: Int,
    @ColumnInfo
    var pageImagePath: String?,
    @ColumnInfo
    var soundFilePath: String?,

    ) {
    @Ignore
    var soundFile: File? = null //optional

    @Ignore
    var pageImage: File? = null

    @Ignore
    var bookTranslations: MutableList<BookTranslation>? = null

    @Ignore
    var bookTeachingPoints: MutableList<BookTeachingPoint>? = null
}

@Entity
data class BookTeachingPoint(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var type: String?,//TeachingPointType,
    @ColumnInfo
    var point: String?,
    @ColumnInfo
    var explained: String?
)

@Entity
data class BookTranslation(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var text_eng: String?,
    @ColumnInfo
    var text_zh: String?
)

@Entity
data class BookWord(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var importance: String?,//WordImportance?,
    @ColumnInfo
    var pronunciation: String?,
    @ColumnInfo
    var grammerType: String?,
    @ColumnInfo
    var meaning: String?,
    @ColumnInfo
    var soundFilePath: String?,

    ) {
    @Ignore
    var soundFile: File? = null//optional
}

enum class TeachingPointType {
    SENTENCE_PATTERN, PHRASES_IDIOMS_COLLOCATIONS, GRAMMER;
}

enum class WordImportance {
    NORMAL, OPTIONAL, IMPORTANT
}


//Section 3: Practice
@Entity
data class PracticeBook(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var description: String?,
    @ColumnInfo
    var coverImagePath: String?,

    ) {
    @Ignore
    var coverImage: File? = null

    @Ignore
    var practiceSections: MutableList<PracticeSection>? = null
}


@Entity
data class PracticeTarget(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var description: String?
) {
    @Ignore
    var books: MutableList<PracticeBook>? = null
}

//Core models
@Parcelize
@Entity
data class PracticeSection(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var seq: Int = 1,//User can specify sequenceS
    @ColumnInfo
    var browseMode: String = BrowseMode.SEQUENCE.name,//SEQUENCE, RANDOM
    @ColumnInfo
    var name: String? = null,
    @ColumnInfo
    var score: Double = 0.0,
    @ColumnInfo
    var totalTimeInMinutes: Double = 0.0,

    @ColumnInfo
    var practiceQuestionTemplateIds: String? = null
) : Parcelable {
    fun practiceQuestionTemplates(): MutableList<Int>? {
        return practiceQuestionTemplateIds?.split(",")?.map { it.toInt() }?.toMutableList()
    }

    @Ignore
    var questionTemplatesDB: MutableList<PracticeQuestionTemplate>? = null

    @Ignore
    var displaySeq: Int = 0

    fun bindTemplatesDbToThis(templates: MutableList<PracticeQuestionTemplate>): PracticeSection {
        questionTemplatesDB = questionTemplatesDB ?: mutableListOf()
        questionTemplatesDB!!.addAll(templates)
        practiceQuestionTemplateIds = templates.map { it.id }.joinToString(",")
        score = templates.map { it.totalScore }.sum()
        totalTimeInMinutes = templates.map { it.totalTimeInMinutes }.sum()

        return this
    }

}

@Entity
data class PracticeQuestionTemplate(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
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
    @Ignore
    var submitted: Boolean = false

    fun practiceQuestions(): MutableList<Int>? {
        return practiceQuestionIds?.split(",")?.map { it.toInt() }?.toMutableList()
    }

    @Ignore
    var questionsDb: MutableList<PracticeQuestion>? = null

    @Ignore
    var countDownTimer: CountDownTimer? = null

    fun bindQuestionsDbToThis(questions: MutableList<PracticeQuestion>): PracticeQuestionTemplate {
        questionsDb = questionsDb ?: mutableListOf()
        questionsDb!!.addAll(questions)
        practiceQuestionIds = questions.map { it.id }.joinToString(",")
        return this
    }

}

@Entity
data class PracticeQuestion(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var type: String = QuestionType.SELECT.name,//QuestionType,//FILL, SELECT
    @ColumnInfo
    var text: String?,
    @ColumnInfo
    var answerStandard: String? = null,
    @ColumnInfo
    var answerKeyPoints: String? = null,
    @ColumnInfo
    var layoutOptionsPerRow: Int = 1,
    @ColumnInfo
    var requireAnsweredOptionsNo: Int = 1,
    @ColumnInfo
    var practiceAnswerOptionIds: String? = null
) {
    fun optionPractices(): MutableList<Int>? {
        return practiceAnswerOptionIds?.split(",")?.map { it.toInt() }?.toMutableList()
    }

    //Used to hold user's input/selected value
    @Ignore
    var usersAnswers: MutableMap<Int, String>? = null

    @Ignore
    var optionsDb: MutableList<PracticeAnswerOption>? = null

    @Ignore
    var displaySeq: Int = 0

    fun bindOptionsDbToThis(options: MutableList<PracticeAnswerOption>): PracticeQuestion {
        optionsDb = optionsDb ?: mutableListOf()
        optionsDb!!.addAll(options)
        practiceAnswerOptionIds = options.map { it.id }.joinToString(",")
        return this
    }
}

@Entity
data class PracticeAnswerOption(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var layoutUI: String? = LayoutUI.TEXT_VIEW.name,//AnswerOptionUI?,//EDIT_TEXT,TEXT_VIEW,IMAGE_VIEW
    @ColumnInfo
    var displayText: String? = null,//label value,place holder value,description for image
    @ColumnInfo
    var correctAnswers: String? = null
) {
    @Ignore
    var layoutUIObject: View? = null

    fun correctAnswers(): MutableList<String>? {
        return correctAnswers?.split(",")?.toMutableList()
    }

    @Ignore
    var displaySeq: Int = 0
}

enum class LayoutUI {
    EDIT_TEXT, TEXT_VIEW, IMAGE_VIEW
}

enum class BrowseMode {
    SEQUENCE, RANDOM
}

enum class QuestionType {
    FILL, SELECT
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
    var totalDifficultyLevel: Double? = 0.0,
    @ColumnInfo
    var totalScore: Double? = 0.0,
    @ColumnInfo
    var totalTimeInMinutes: Double? = 0.0,
    @ColumnInfo
    var practiceSectionIds: String?

) : Parcelable {
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






























