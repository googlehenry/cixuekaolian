package com.viastub.kao100.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
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
@Entity
data class PracticeSection(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var seq: Int,//User can specify sequenceS
    @ColumnInfo
    var browseMode: String,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var score: Double,
    @ColumnInfo
    var totalTimeInMinutes: Double,
) {
    @Ignore
    var practiceQuestionTemplates: MutableList<PracticeQuestionTemplate>? = null
}

@Entity
data class PracticeQuestionTemplate(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var category: String?,
    @ColumnInfo
    var requirement: String?,
    @ColumnInfo
    var hints: String?,
    @ColumnInfo
    var keyPoints: String?,
    @ColumnInfo
    var layoutQuestionsPerRow: Int = 1,
    @ColumnInfo
    var totalScore: Double,
    @ColumnInfo
    var totalTimeInMinutes: Double
) {
    @Ignore
    var practiceQuestions: MutableList<PracticeQuestion>? = null
}

@Entity
data class PracticeQuestion(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var type: String,//QuestionType,//FILL, SELECT
    @ColumnInfo
    var text: String?,
    @ColumnInfo
    var layoutOptionsPerRow: Int = 1,
    @ColumnInfo
    var requireAnsweredOptionsNo: Int = 1,
    @ColumnInfo
    var userAnsersJson: String?,
) {
    @Ignore
    var optionPractices: MutableList<PracticeAnswerOption>? = null

    //Used to hold user's input/selected value
    @Ignore
    var usersAnswers: MutableMap<Int, String?>? = null

    @Ignore
    var correctAnswers: MutableMap<Int, MutableList<String>>? = null
}

@Entity
data class PracticeAnswerOption(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var layoutUI: String?,//AnswerOptionUI?,//EDIT_TEXT,TEXT_VIEW,IMAGE_VIEW
    @ColumnInfo
    var displayText: String?,//label value,place holder value,description for image
)

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
    var name: String?,
    @ColumnInfo
    var totalDifficultyLevel: Double?,
    @ColumnInfo
    var totalScore: Double?,
    @ColumnInfo
    var totalTimeInMinutes: Double?,

    ) {
    @Ignore
    var practiceSections: MutableList<PracticeSection>? = null
}

@Entity
data class GlobalConfig(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo
    var module: String?,
    @ColumnInfo
    var key: String?,
    @ColumnInfo
    var value: String?,
    @ColumnInfo
    var description: String?
)




























