package com.viastub.kao100.config.db.init

import com.viastub.kao100.db.*

class TestDataLoader : DataLoader {
    override fun load(roomDb: RoomDB): Int {
        var testOpion1 = PracticeAnswerOption(
            1, "EDIT_TEXT", null,
            "do good to you"
        )

        var testQuestion1 = PracticeQuestion(
            1,
            "FILL",
            """
                (原句)Eat more fruit and it will do you good.
                (改为)Eat more fruit and it will ____.
            """.trimIndent(),
            2,
            1,
            "1"
        )

        var testQuestionTemplate1 = PracticeQuestionTemplate(
            1, "同义句转换", "修改原句为同义句",
            hints = null,
            keyPoints = "do good to sb./do sb. good 对某人有好处(反义短语: do harm to sb./do sb. harm)",
            1,
            2.0,
            1.0,
            "1"
        )

        var testQuestionSection = PracticeSection(
            1,
            seq = 1,
            browseMode = "SEQUENCE",
            name = "基础训练部分",
            score = 2.0,
            totalTimeInMinutes = 1.0,
            practiceQuestionTemplateIds = "1"
        )

        var examSimulation1 = ExamSimulation(
            1,
            province = "四川省",
            city = "广元市",
            testType = "期中考试",
            grade = "高1年级",
            name = "高一英语第一单元阶段测试",
            tags = "基础训练,XYZ模拟高考",
            totalDifficultyLevel = 3.0,
            totalScore = 2.0,
            totalTimeInMinutes = 1.0,
            practiceSectionIds = "1"
        )


        roomDb.practiceAnswerOption().insert(testOpion1)
        roomDb.practiceQuestion().insert(testQuestion1)
        roomDb.practiceQuestionTemplate().insert(testQuestionTemplate1)
        roomDb.practiceSection().insert(testQuestionSection)
        roomDb.examSimulation().insert(examSimulation1)

        return -1
    }

}