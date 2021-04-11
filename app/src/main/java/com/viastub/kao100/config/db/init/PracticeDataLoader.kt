package com.viastub.kao100.config.db.init

import com.viastub.kao100.R
import com.viastub.kao100.db.*
import com.viastub.kao100.utils.TempUtil


class PracticeDataLoader : DataLoader {
    override fun load(roomDb: RoomDB): Int {
        loadTextBook1(roomDb)
        return -1
    }

    private fun loadTextBook1(roomDb: RoomDB) {

        var book1 = PracticeBook(
            name = "普通高中课程标准试验教科书(英语 1)必修",
            coverImagePath = TempUtil.loadRawFile(
                R.raw.demo_high_school_rjb_bixiu_1,
                "demo_high_school_rjb_bixiu_1"
            )
        ).bindId(1).bindUnitSectionsDBToThis(
            mutableListOf(
                PracticeSection(name = "第1单元", description = "Unit 1: Friendship").bindId(6)
                    .bindTemplatesDbToThis(
                        mutableListOf(
                            PracticeTemplate(
                                category = "单句填空",
                                requirement = "在空白处填入一个适当的单词或括号内单词的正确形式。",
                                totalScore = 10.0,
                                totalTimeInMinutes = 7.5
                            ).bindId(9).bindQuestionsDbToThis(
                                mutableListOf(
                                    PracticeQuestion(
                                        type = QuestionType.FILL.name,
                                        layoutOptionsPerRow = 2,
                                        text = "As far as I am ____(concern), children should often play outdoors.",
                                        answerStandard = "concerned",
                                        answerKeyPoints = "句意为:在我看来,孩子们应该经常在户外玩耍。as far as sb be concerned为固定表达,意为“依某人看来;就某人而言” "
                                    ).bindId(33).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "concerned"
                                            ).bindId(80)
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.FILL.name,
                                        layoutOptionsPerRow = 2,
                                        text = "Our motherland is getting more and more ____(power).",
                                        answerStandard = "powerful",
                                        answerKeyPoints = "句意为:我们的祖国正变得越来越强大。设空处在句中作 getting的表语,其前有 more and more修饰,应用形容词。"
                                    ).bindId(34).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "powerful"
                                            ).bindId(81)
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.FILL.name,
                                        layoutOptionsPerRow = 2,
                                        text = "To be honest. I was ____(entire) frightened by the thunder just now.",
                                        answerStandard = "entirely",
                                        answerKeyPoints = "句意为:老实说,我完全被刚才的雷声吓到了。修饰谓语动词 was frightened 应该用副词,故填 entirely。"
                                    ).bindId(35).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "entirely"
                                            ).bindId(82)
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.FILL.name,
                                        layoutOptionsPerRow = 2,
                                        text = "The room has been empty for a long time and all the furniture is ____(dust).",
                                        answerStandard = "dusty",
                                        answerKeyPoints = "句意为这个房间空置很长时间了,所有的家具都布满了灰尘。dust为名词结合句意可知填形容词dusty作表语,意为“布满灰尘的."
                                    ).bindId(36).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "dusty"
                                            ).bindId(83)
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.FILL.name,
                                        layoutOptionsPerRow = 2,
                                        text = "His computer had been broken so he set ____ what the boss said using a pen.",
                                        answerStandard = "down",
                                        answerKeyPoints = "句意为:他的电脑坏了,所以他用一支钢笔记下了老板说的话。 set down意为“写下;记下”。"
                                    ).bindId(37).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "down"
                                            ).bindId(84)
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.FILL.name,
                                        layoutOptionsPerRow = 2,
                                        text = "It is important to remember that you must add a lot of efforts ____ your work.",
                                        answerStandard = "to",
                                        answerKeyPoints = "句意为:重要的是记住你必须在工作中付出很多努力。add to..意为\"把……加到……中\"."
                                    ).bindId(38).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "to"
                                            ).bindId(85)
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.FILL.name,
                                        layoutOptionsPerRow = 2,
                                        text = "The strong-minded boy managed to finish the task on time, although he was going ____ many difficulties.",
                                        answerStandard = "through",
                                        answerKeyPoints = "句意为:尽管他正经历着很多困难,但这个意志坚定的男孩准时完成了任务。 go through为固定搭配,意为“经历”。"
                                    ).bindId(39).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "through"
                                            ).bindId(86)
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.FILL.name,
                                        layoutOptionsPerRow = 2,
                                        text = "Many people tend to bring electronic products when ____(travel), including me.",
                                        answerStandard = "travelling",
                                        answerKeyPoints = "本题考查when引导的时间状语从句的省略形式。当从句的主语与主句的主语一致,且从句含有be动词时,可以省略从句的主语和be动词。此处省略了 they are,补充完整为when they are travelling。故填 travelling"
                                    ).bindId(40).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "travelling"
                                            ).bindId(87)
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.FILL.name,
                                        layoutOptionsPerRow = 2,
                                        text = "(ignore)____ the difference between the two research findings will be one of the worst mistakes you make.",
                                        answerStandard = "Ignoring",
                                        answerKeyPoints = "句意为:忽视这两个研究成果之间的区别将会是你犯的最严重的错误之一。此时用动名词短语 Ignoring the difference between the two research findings作句子的主语。 ignore意为“忽视;不理睬”。"
                                    ).bindId(41).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "Ignoring"
                                            ).bindId(88)
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.FILL.name,
                                        layoutOptionsPerRow = 2,
                                        text = "It is no pleasure ____(join) in your walking.",
                                        answerStandard = "joining",
                                        answerKeyPoints = "句意为:跟你一起散步没有乐趣。 It is no pleasure doing sth意为“做某事没有乐趣”,为固定句型。It在此为形式主语, joining in your walking为真正的主语。 Join In意为“加入,参加”。"
                                    ).bindId(42).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "joining"
                                            ).bindId(89)
                                        )
                                    ),
                                )
                            ),
                            PracticeTemplate(
                                category = "单句改错",
                                requirement = "修改以下各题中的错误,每题仅涉及一个单词的增加、删除或修改。注意错误及修改均仅限一词",
                                itemMainText = """
                                1. You don't need to get the computer repairing, because I've decided to buy a new one.
                                2. It was the third time in a month that you have been late for school.
                                3. Nowadays people are more concerned to the environment where they live.
                                4. You would have made full preparations for the important interview. It is a pity that you are refused.
                                5. The heavy snow added up to our difficulty in finding our way out of the mountain.
                                6. There have been a series of activities to get people together and make them closer to each other in this town.
                                7. While visiting the campus the other day, we run into several Germen.
                                8. When you are upset to everything, you might not feel like being friendly.
                                9. I think the teacher did it in purpose in order to give us a good lesson.
                                10. There was a time that girls were not allowed to receive education.
                            """.trimIndent(),
                                totalScore = 10.0,
                                totalTimeInMinutes = 7.5
                            ).bindId(10).bindQuestionsDbToThis(
                                mutableListOf(
                                    PracticeQuestion(
                                        type = QuestionType.CORRECT.name,
                                        answerStandard = "repairing->repaired",
                                        answerKeyPoints = "此处为“get+宾语+宾补”结构,get为使役动词,表示“使;让”。 the computer与 repaIr之间为逻辑上的被动关系,应用过去分词作宾补。 get sth done表示\"使某物被...\""
                                    ).bindId(43).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                90,
                                                LayoutUI.CORRECTION.name,
                                                null,
                                                correctAnswersSplitByPipes = "repairing->repaired"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.CORRECT.name,
                                        answerStandard = "was->is 或者 have->had",
                                        answerKeyPoints = "“It/This+be+ the first/second... time that...\"意为“这是第一/二次…”。前面的be动词若是is则that从句用现在完成时;若是was,则that从句用过去完成时"
                                    ).bindId(44).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                91,
                                                LayoutUI.CORRECTION.name,
                                                null,
                                                correctAnswersSplitByPipes = "was->is|have->had"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.CORRECT.name,
                                        answerStandard = "to->for/about",
                                        answerKeyPoints = "be concerned for/about为固定搭配,意为“为...担忧”。"
                                    ).bindId(45).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                92,
                                                LayoutUI.CORRECTION.name,
                                                null,
                                                correctAnswersSplitByPipes = "to->for|to->about"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.CORRECT.name,
                                        answerStandard = "would->should",
                                        answerKeyPoints = "句意:你本应该为这次重要的面试做充分准备的。你被拒绝了真是遗憾。表示“本应该做某事而未做”应用 should have done sth结构"
                                    ).bindId(46).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                93,
                                                LayoutUI.CORRECTION.name,
                                                null,
                                                correctAnswersSplitByPipes = "would->should"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.CORRECT.name,
                                        answerStandard = "added up to->added to",
                                        answerKeyPoints = "句意为:大雪增加了我们寻找出山的路的难度。 add to意为“使增加;使抄大”;add up to为“总共是;总计为”。"
                                    ).bindId(47).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                94,
                                                LayoutUI.CORRECTION.name,
                                                null,
                                                correctAnswersSplitByPipes = "added up to->added to"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.CORRECT.name,
                                        answerStandard = "have->has",
                                        answerKeyPoints = "a series of+可数名词复数”作主语时,谓语动词用单数形式。本句为 there be句型的完成式,助动词have应用第人称单数形式has"
                                    ).bindId(48).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                95,
                                                LayoutUI.CORRECTION.name,
                                                null,
                                                correctAnswersSplitByPipes = "have->has"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.CORRECT.name,
                                        answerStandard = "Germen>Germans",
                                        answerKeyPoints = "句意为:前几天参观校园时,我们遇到了几个德国人。 German作名词可表示“德国人”,此处被 several修饰,应用复数形式。 German的复数为 Germans,不是 Germen."
                                    ).bindId(49).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                96,
                                                LayoutUI.CORRECTION.name,
                                                null,
                                                correctAnswersSplitByPipes = "Germen>Germans"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.CORRECT.name,
                                        answerStandard = "to->about/at/over/by",
                                        answerKeyPoints = "句意为:当你对一切事物都感到烦恼时,你可能不想对他人友好。 be upset aboul/at/over/by意为\"为...感到心烦意乱”,为固定搭配。"
                                    ).bindId(50).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                97,
                                                LayoutUI.CORRECTION.name,
                                                null,
                                                correctAnswersSplitByPipes = "to->about|to->at|to->over|to->by"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.CORRECT.name,
                                        answerStandard = "第一个in->on",
                                        answerKeyPoints = "on purpose为固定短语,意为\"故意地\"。"
                                    ).bindId(51).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                98,
                                                LayoutUI.CORRECTION.name,
                                                null,
                                                correctAnswersSplitByPipes = "in->on"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        type = QuestionType.CORRECT.name,
                                        answerStandard = "that->when",
                                        answerKeyPoints = "There was a time when…意为“曾经有一段时间…”,为固定句型,其中 a time是先行词, when是关系副词,引导定语从句,并在从句中作时间状语。"
                                    ).bindId(52).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                99,
                                                LayoutUI.CORRECTION.name,
                                                null,
                                                correctAnswersSplitByPipes = "that->when"
                                            )
                                        )
                                    )
                                )
                            ),
                            PracticeTemplate(
                                layoutQuestionsPerRow = 2,
                                category = "语法填空",
                                requirement = "[河北武邑中学2019高一月考]阅读下面短文,在空白处填入1个适当的单词或括号内单词的正确形式",
                                itemMainText = """
                                In the distant past, friends relied on each other for their survival. They hunted together and defended each other against 1____(danger) animals and enemies. In those days, if you didnt have a friend, you would either starve, 2____(eat) or killed. Nowadays, friendship isn't 3____(exact) a matter of life and death. However, friendship is still of great importance and not having a friend is something to be 4____(concern) about. Most people look upon a friend as someone they can depend on when they are going 5____times of trouble. In such times, friends provide them with emotional support and sometimes financial help. It is in these troubled times 6____ they find out who their true friends are As the old saying 7____(go), in times of success, friends will be plenty; in times of 8____(suffer), not one in twenty. And there is another saying 9____says you can hardly make a friend in a year, but you can easily upset one in an hour. So do your best to get along with and be grateful to all those who 10___(be) willing to side with you even when you are in trouble as they are your true friends.
                            """.trimIndent(),
                                keyPoints = "木文是一篇议论文,论述了朋友的重要性。真心的朋友会永远在你身边支持你,会在你困难的时候给予情感上的支持和必要时经济上的援助。",
                                totalScore = 10.0,
                                totalTimeInMinutes = 7.5
                            ).bindId(11).bindQuestionsDbToThis(
                                mutableListOf(
                                    PracticeQuestion(
                                        answerKeyPoints = "修饰名词短语 animals and enemies应用形容词。",
                                        type = QuestionType.FILL.name,
                                    ).bindId(53).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                id = 100,
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "dangerous"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        answerKeyPoints = "设空处与 starve并列,eat与主语是被动关系,所以设空处应用被动语态。前有 would,故填 be eaten",
                                        type = QuestionType.FILL.name,
                                    ).bindId(54).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                id = 101,
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "be eaten"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        answerKeyPoints = "修饰be动词应用副词,故填 exactly",
                                        type = QuestionType.FILL.name,
                                    ).bindId(55).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                id = 102,
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "exactly"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        answerKeyPoints = "be concerned about意为“为……担忧”",
                                        type = QuestionType.FILL.name,
                                    ).bindId(56).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                id = 103,
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "concerned"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        answerKeyPoints = "句意为:大多数人把朋友看作是当他们遇到困难时可以依靠的人。 go through意为“经历(困难、痛苦等)”",
                                        type = QuestionType.FILL.name,
                                    ).bindId(57).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                id = 104,
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "through"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        answerKeyPoints = "此处是It is…that…强调结构,强调时间状语 in these troubled times",
                                        type = QuestionType.FILL.name,
                                    ).bindId(58).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                id = 105,
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "that"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        answerKeyPoints = "goes as the old saying goes是固定表达,意为“老话说得好”",
                                        type = QuestionType.FILL.name,
                                    ).bindId(59).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                id = 106,
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "goes"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        answerKeyPoints = "suffering介词of后一般接名词、代词或动名词作宾语",
                                        type = QuestionType.FILL.name,
                                    ).bindId(60).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                id = 107,
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "suffering"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        answerKeyPoints = "____that says you can hardly make a friend in a year为定语从句,修饰先行词 another saying,关系词在从句中作主语,且该句为there be句型,只能用that引导,故填that",
                                        type = QuestionType.FILL.name,
                                    ).bindId(61).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                id = 108,
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "that"
                                            )
                                        )
                                    ),
                                    PracticeQuestion(
                                        answerKeyPoints = "who引导定语从句,代替先行词 those在从句中作主语,从句中的谓语动词的数应取决于先行词的数,所以应用复数形式。",
                                        type = QuestionType.FILL.name,
                                    ).bindId(62).bindOptionsDbToThis(
                                        mutableListOf(
                                            PracticeAnswerOption(
                                                id = 109,
                                                layoutUI = LayoutUI.EDIT_TEXT.name,
                                                correctAnswersSplitByPipes = "are"
                                            )
                                        )
                                    ),

                                    )
                            )

                        )
                    ),
                PracticeSection(
                    name = "第2单元",
                    description = "Unit 2: English aroun the world"
                ).bindId(7),
                PracticeSection(name = "第3单元", description = "Unit 3: Travel journal").bindId(8),
                PracticeSection(name = "第4单元", description = "Unit 4: Earthquakes").bindId(9),
                PracticeSection(
                    name = "第5单元",
                    description = "Unit 5: Nelson Mandela - a modern hero"
                ).bindId(10),
                PracticeSection(name = "期中测试").bindId(11),
                PracticeSection(name = "期末测试").bindId(12),
            )
        )


        var target = PracticeTarget(
            name = "教材配套",
            description = "针对教材的配套练习资料"
        ).bindId(1)

        target.bindBooksDBToThis(mutableListOf(book1))

        target.booksDb?.forEach { book ->
            book.unitSectionsDb?.forEach { unitSection ->
                unitSection.templatesDB?.forEach { template ->
                    template.questionsDb!!.forEach { question ->
                        question.optionsDb!!.forEach { option ->
                            roomDb.practiceAnswerOption().insert(option)
                        }
                        roomDb.practiceQuestion().insert(question)
                    }
                    roomDb.practiceTemplate().insert(template)
                }
                roomDb.practiceSection().insert(unitSection)
            }
            roomDb.practiceBook().insert(book)
        }

        roomDb.practiceTarget().insert(target)

    }

}