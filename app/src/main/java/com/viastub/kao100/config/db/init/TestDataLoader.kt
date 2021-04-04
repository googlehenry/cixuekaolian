package com.viastub.kao100.config.db.init

import com.viastub.kao100.db.*

class TestDataLoader : DataLoader {
    override fun load(roomDb: RoomDB): Int {
        var testOpion1 = PracticeAnswerOption(1, "EDIT_TEXT", null, "do good to you")
        var testOpion2 = PracticeAnswerOption(2, "EDIT_TEXT", null, "at")
        var testOpion3 = PracticeAnswerOption(3, "EDIT_TEXT", null, "for")

        var testQuestion1 = PracticeQuestion(
            1,
            "FILL",
            """
                (原句)Eat more fruit and it will do you good.
                (改为)Eat more fruit and it will ____.
            """.trimIndent(),
            "do good to you",
            "do good to sb./do sb. good 对某人有好处(反义短语: do harm to sb./do sb. harm)",
            2,
            1,
            "1"
        )
        var testQuestion2 = PracticeQuestion(
            2,
            "FILL",
            """
                Artificial intelligence (AI) may become extremely good ____ achieving something other than what we really want.
            """.trimIndent(),
            "at",
            "be good at(doing) sth.擅长干某事(近义短语:do well in sth.)",
            2,
            1,
            "2"
        )
        var testQuestion3 = PracticeQuestion(
            3,
            "FILL",
            """
                Of course everyone knows that excercise is good ____ the body. 
            """.trimIndent(),
            "for",
            "be good for sb./sth.对某人/某事有好处(反义短语: be bad for sb./sth.)",
            2,
            1,
            "3"
        )

        var testQuestionTemplate1 = PracticeQuestionTemplate(
            1, "同义句转换", "修改原句为同义句",
            null,
            null,
            hints = null,
            keyPoints = "do good to sb./do sb. good 对某人有好处(反义短语: do harm to sb./do sb. harm)",
            1,
            2.0,
            1.0,
            "1"
        )
        var testQuestionTemplate2 = PracticeQuestionTemplate(
            2, "介词单项填空", "使用适合介词填空(一空一词)",
            null,
            null,
            hints = null,
            keyPoints = """
                be good at(doing) sth.擅长干某事(近义短语:do well in sth.)
                be good for sb./sth.对某人/某事有好处(反义短语: be bad for sb./sth.)
            """.trimIndent(),
            1,
            4.0,
            2.0,
            "2,3"
        )

        var testQuestionSection = PracticeSection(
            1,
            seq = 1,
            browseMode = "SEQUENCE",
            name = "基础训练部分",
            score = 6.0,
            totalTimeInMinutes = 3.0,
            practiceQuestionTemplateIds = "1,2"
        )

        var examSimulation1 = ExamSimulation(
            1,
            province = "四川省",
            city = "广元市",
            testType = "期中考试",
            grade = "高1年级",
            name = "2020-2021学年上学期广元市静安区高一上学期一模试卷",
            tags = "基础训练,XYZ模拟高考",
            totalDifficultyLevel = 3.0,
            totalScore = 2.0,
            totalTimeInMinutes = 1.0,
            practiceSectionIds = "1"
        )


        roomDb.practiceAnswerOption().insert(testOpion1)
        roomDb.practiceAnswerOption().insert(testOpion2)
        roomDb.practiceAnswerOption().insert(testOpion3)
        roomDb.practiceQuestion().insert(testQuestion1)
        roomDb.practiceQuestion().insert(testQuestion2)
        roomDb.practiceQuestion().insert(testQuestion3)
        roomDb.practiceQuestionTemplate().insert(testQuestionTemplate1)
        roomDb.practiceQuestionTemplate().insert(testQuestionTemplate2)
        roomDb.practiceSection().insert(testQuestionSection)
        roomDb.examSimulation().insert(examSimulation1)

        return -1
    }

}