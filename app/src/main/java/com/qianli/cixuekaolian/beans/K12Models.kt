package com.stubhub.jdk.Jdk16DemoKotlin.models

import java.io.File

//Core models
data class Block(
        var id:Int,
        var seq:Int,//User can specify sequenceS
        var browseMode:BrowseMode,
        var name:String,
        var score:Double,
        var totalTimeInMinutes:Double,
        var workbooks:MutableList<WorkBook>? //順序
)

data class WorkBook(
        var id: Int,
        var category: String?,
        var requirement: String?,
        var hints: String?,
        var keyPoints: String?,
        var layoutQuestionsPerRow: Int = 1,
        var questions: MutableList<Question>?,

        var totalScore:Double,
        var totalTimeInMinutes: Double
)

data class Question(
        var id: Int,
        var type: QuestionType,//FILL, SELECT
        var text:String?,
        var layoutOptionsPerRow:Int = 1,
        var options:MutableList<AnswerOption>?,
        var requireAnsweredOptionsNo:Int=1,

        //Used to hold user's input/selected value
        var correctAnswers:MutableMap<Int,MutableList<String>>?,
        var usersAnswers:MutableMap<Int,String?>?
)

data class AnswerOption(
        var id:Int,
        var layoutUI: AnswerOptionUI?,//EDIT_TEXT,TEXT_VIEW,IMAGE_VIEW
        var displayText:String?,//label value,place holder value,description for image
)

enum class BrowseMode{
    SEQUENCE,RANDOM
}
enum class QuestionType {
    FILL, SELECT
}
enum class AnswerOptionUI{
    EDIT_TEXT,TEXT_VIEW,IMAGE_VIEW
}


//Section 1: Embed a sample dictionary .mdd .mdx
data class DictionaryConfig(
        var id:Int,
        var title:String,
        var dictFile:File?,
        var soundFile:File?
)

//Section 3: Practice
data class TextBookPractice(
        var id:Int,
        var name:String,
        var description:String?,
        var coverImagePath:String,
        var blocks:MutableList<Block>?
)

data class ExamByTypePractice(
        var id:Int,
        var name:String,
        var description:String?,
        var block:Block?
)

//Section 4: Tests
data class ExamSimulation(
        var id:Int,
        var province:String,
        var testType:String,
        var grade:String,
        var name:String,
        var blocks:MutableList<Block>?,
        var totalDifficultyLevel:Double,
        var totalScore:Double,
        var totalTimeInMinutes:Double,
)

//Section 2:
data class TextBook(
        var id:Int,
        var publishedBy:String,
        var publishedVersion:Int,
        var grade:String,
        var title:String,
        var bookCoverImage:String?,
        var bookUnits:MutableList<BookUnit>?,
        var appendices:MutableList<BookAppendix>
)

data class BookAppendix(
        var id:Int,
        var name:String,
        var description: String?,
        var pageSnapshotImages:MutableList<File>?
)

data class BookUnit(
        var id:Int,
        var name:String,
        var description: String?,
        var unitCoverImage:String?,
        var unitPages:UnitPages?,
        var unitWords:UnitWords?
)

data class UnitWords(
        var id:Int,
        var words:MutableList<BookWord>?,
        var soundFile: File?
)

data class UnitPages(
        var id:Int,
        var pages:MutableList<BookPage>?,
        var soundFile: File?
)

data class BookPage(
        var id:Int,
        var pageNo:Int,
        var pageImage:File?,
        var translations:MutableList<Translation>?,
        var teachingPoints:MutableList<TeachingPoint>?,
        var soundFile: File? //optional
)

data class TeachingPoint(
        var id:Int,
        var type:TeachingPointType,
        var point:String,
        var explained:String?
)

data class Translation(
        var id:Int,
        var text_eng:String?,
        var text_zh:String?
)

data class BookWord(
        var id:Int,
        var name:String,
        var importance:WordImportance?,
        var pronunciation:String?,
        var grammerType:String?,
        var meaning:String?,
        var soundFile: File?//optional
)

enum class TeachingPointType{
    SENTENCE_PATTERN,PHRASES_IDIOMS_COLLOCATIONS,GRAMMER;
}

enum class WordImportance{
    NORMAL,OPTIONAL,IMPORTANT
}



























