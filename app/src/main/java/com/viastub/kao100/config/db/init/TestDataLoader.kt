package com.viastub.kao100.config.db.init

import com.viastub.kao100.db.*


class TestDataLoader : DataLoader {
    override fun load(roomDb: RoomDB): Int {
        loadBasicSection2(roomDb)
        loadBasicSection1(roomDb)
        return -1
    }

    private fun loadBasicSection2(roomDb: RoomDB) {
        var testOpion4 = PracticeAnswerOption(4, LayoutUI.TEXT_VIEW.name, "A. Take your time", null)
        var testOpion5 = PracticeAnswerOption(5, LayoutUI.TEXT_VIEW.name, "B. You're right", null)
        var testOpion6 =
            PracticeAnswerOption(6, LayoutUI.TEXT_VIEW.name, "C. Whatever you say", null)
        var testOpion7 = PracticeAnswerOption(7, LayoutUI.TEXT_VIEW.name, "D. Take it easy", "true")

        var testOpion8 = PracticeAnswerOption(8, LayoutUI.TEXT_VIEW.name, "A. come along", "true")
        var testOpion9 = PracticeAnswerOption(9, LayoutUI.TEXT_VIEW.name, "B. come off", null)
        var testOpion10 = PracticeAnswerOption(10, LayoutUI.TEXT_VIEW.name, "C. come across", null)
        var testOpion11 = PracticeAnswerOption(11, LayoutUI.TEXT_VIEW.name, "D. come through", null)

        var testOpion12 = PracticeAnswerOption(12, LayoutUI.TEXT_VIEW.name, "A. but", "true")
        var testOpion13 = PracticeAnswerOption(13, LayoutUI.TEXT_VIEW.name, "B. and", null)
        var testOpion14 = PracticeAnswerOption(14, LayoutUI.TEXT_VIEW.name, "C. so", null)
        var testOpion15 = PracticeAnswerOption(15, LayoutUI.TEXT_VIEW.name, "D. or", null)

        var testOpion16 = PracticeAnswerOption(16, LayoutUI.TEXT_VIEW.name, "A. what", null)
        var testOpion17 = PracticeAnswerOption(17, LayoutUI.TEXT_VIEW.name, "B. when", null)
        var testOpion18 = PracticeAnswerOption(18, LayoutUI.TEXT_VIEW.name, "C. where", "true")
        var testOpion19 = PracticeAnswerOption(19, LayoutUI.TEXT_VIEW.name, "D. which", null)

        var testOpion20 = PracticeAnswerOption(20, LayoutUI.TEXT_VIEW.name, "A. caught", null)
        var testOpion21 =
            PracticeAnswerOption(21, LayoutUI.TEXT_VIEW.name, "B. to have caught", null)
        var testOpion22 = PracticeAnswerOption(22, LayoutUI.TEXT_VIEW.name, "C. to catch", null)
        var testOpion23 =
            PracticeAnswerOption(23, LayoutUI.TEXT_VIEW.name, "D. having caught", "true")

        var testQuestion4 = PracticeQuestion(
            4, QuestionType.SELECT.name,
            """
                --I'm sorry I made a mistake! 
                --____ Nobody is perfect.    
            """.trimIndent(),
            "D", "考查交际用语。根据后句“人无完人”可知，前一个人犯错误了，应叫他take it easy（放松）。",
            1, 1, "4,5,6,7"
        )
        var testQuestion5 = PracticeQuestion(
            5, QuestionType.SELECT.name,
            """
                Would you like to ____ with us to the film tonight?
            """.trimIndent(),
            "A", "考查动词短语辨析。根据句意，与我们一道去看电影，故选A。come along with…与…一道。",
            1, 1, "8,9,10,11"
        )
        var testQuestion6 = PracticeQuestion(
            6, QuestionType.SELECT.name,
            """
                I was glad to meet Jenny again, ____ I didn't want to spend all day with her.
            """.trimIndent(),
            "A", "考查并列连词。根据句意：再次见到Jenny我很高兴，但我不想整天都和她一起度过。",
            1, 1, "12,13,14,15"
        )
        var testQuestion7 = PracticeQuestion(
            7, QuestionType.SELECT.name,
            """
                When I arrived, Bryan took me to see the house ____ I would be staying.
            """.trimIndent(),
            "C", "考查定语从句。定语从句中stay为不及物动词，故不缺主干成分，用关系副词；先行词为house，指地点，故用关系副词where。",
            1, 1, "16,17,18,19"
        )
        var testQuestion8 = PracticeQuestion(
            8,
            QuestionType.SELECT.name,
            """
                I got to the office earlier that day, ____ the 7:30 train from Paddington
            """.trimIndent(),
            "D",
            "考查非谓语动词。根据句意，因为我赶上了7:30的车，所以那天我更早地到了办公室，可知赶车发生在到办公室之前，且与主语I之间为主动关系，故使用现在分词完成体表主动完成。",
            1,
            1,
            "20,21,22,23"
        )

        var testQuestionTemplate3 = PracticeQuestionTemplate(
            3, "单项选择", "选择正确的选项填空",
            null,
            null,
            hints = null,
            keyPoints = """
                1.【答案】D 
                  【解析】考查交际用语。根据后句“人无完人”可知，前一个人犯错误了，应叫他take it easy（放松）。
                2.【答案】A 
                  【解析】考查动词短语辨析。根据句意，与我们一道去看电影，故选A。come along with…与…一道。
                3.【答案】A 
                  【解析】考查并列连词。根据句意：再次见到Jenny我很高兴，但我不想整天都和她一起度过。
                4.【答案】C 
                  【解析】考查定语从句。定语从句中stay为不及物动词，故不缺主干成分，用关系副词；先行词为house，指地点，故用关系副词where。
                5.【答案】D 
                  【解析】考查非谓语动词。根据句意，因为我赶上了7:30的车，所以那天我更早地到了办公室，可知赶车发生在到办公室之前，且与主语I之间为主动关系，故使用现在分词完成体表主动完成。
            """.trimIndent(),
            1,
            10.0,
            5.0,
            "4,5,6,7,8"
        )

        var testQuestionSection2 = PracticeSection(
            2,
            seq = 1,
            browseMode = "SEQUENCE",
            name = "单项选择",
            score = 10.0,
            totalTimeInMinutes = 5.0,
            practiceQuestionTemplateIds = "3"
        )


        roomDb.practiceAnswerOption().insert(testOpion4)
        roomDb.practiceAnswerOption().insert(testOpion5)
        roomDb.practiceAnswerOption().insert(testOpion6)
        roomDb.practiceAnswerOption().insert(testOpion7)
        roomDb.practiceAnswerOption().insert(testOpion8)
        roomDb.practiceAnswerOption().insert(testOpion9)
        roomDb.practiceAnswerOption().insert(testOpion10)
        roomDb.practiceAnswerOption().insert(testOpion11)
        roomDb.practiceAnswerOption().insert(testOpion12)
        roomDb.practiceAnswerOption().insert(testOpion13)
        roomDb.practiceAnswerOption().insert(testOpion14)
        roomDb.practiceAnswerOption().insert(testOpion15)
        roomDb.practiceAnswerOption().insert(testOpion16)
        roomDb.practiceAnswerOption().insert(testOpion17)
        roomDb.practiceAnswerOption().insert(testOpion18)
        roomDb.practiceAnswerOption().insert(testOpion19)
        roomDb.practiceAnswerOption().insert(testOpion20)
        roomDb.practiceAnswerOption().insert(testOpion21)
        roomDb.practiceAnswerOption().insert(testOpion22)
        roomDb.practiceAnswerOption().insert(testOpion23)

        roomDb.practiceQuestion().insert(testQuestion4)
        roomDb.practiceQuestion().insert(testQuestion5)
        roomDb.practiceQuestion().insert(testQuestion6)
        roomDb.practiceQuestion().insert(testQuestion7)
        roomDb.practiceQuestion().insert(testQuestion8)

        roomDb.practiceQuestionTemplate().insert(testQuestionTemplate3)
        roomDb.practiceSection().insert(testQuestionSection2)
    }

    private fun loadBasicSection1(roomDb: RoomDB) {
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

        var testQuestionSection1 = PracticeSection(
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
            totalDifficultyLevel = 3.5,
            totalScore = 16.0,
            totalTimeInMinutes = 8.0,
            practiceSectionIds = "1,2"
        )


        roomDb.practiceAnswerOption().insert(testOpion1)
        roomDb.practiceAnswerOption().insert(testOpion2)
        roomDb.practiceAnswerOption().insert(testOpion3)
        roomDb.practiceQuestion().insert(testQuestion1)
        roomDb.practiceQuestion().insert(testQuestion2)
        roomDb.practiceQuestion().insert(testQuestion3)
        roomDb.practiceQuestionTemplate().insert(testQuestionTemplate1)
        roomDb.practiceQuestionTemplate().insert(testQuestionTemplate2)
        roomDb.practiceSection().insert(testQuestionSection1)
        roomDb.examSimulation().insert(examSimulation1)
    }

}