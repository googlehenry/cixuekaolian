package com.viastub.kao100.config.db.init

import com.viastub.kao100.R
import com.viastub.kao100.db.*
import com.viastub.kao100.utils.TempUtil


class ExamDataLoader : DataLoader {
    override fun load(roomDb: RoomDB): Int {
        loadBasicSection5(roomDb)
        loadBasicSection4(roomDb)
        loadBasicSection3(roomDb)
        loadBasicSection2(roomDb)
        loadBasicSection1(roomDb)
        loadBasicExam(roomDb)
        loadBasicUsers(roomDb)
        return -1
    }

    private fun loadBasicSection5(roomDb: RoomDB) {
        var testQuestionSection5 = PracticeSection(
            name = "写作部分",
        ).bindId(5).bindTemplatesDbToThis(
            mutableListOf(
                PracticeTemplate(
                    category = "短文改错",
                    requirement = """
                     全文共10处错误,添词，删词，修改词，请找出作答。
                """.trimIndent(),
                    itemMainText = """
                    Nearly five years before,and with the help by our father,my sister and I planted some cherry tomatoes(圣女果)in our back garden.Since then—for all these year—we had been allowing tomatoes to self-seed where they please.
                    
                    As result,the plants are growing somewhere.The fruits are small in size,but juicy and taste.There are so much that we often share them with our neighbors.Although we allow tomato plants to grow in the same place year after year,but we have never had any disease or insect attack problems.We are growing wonderfully tomatoes at no cost!
                """.trimIndent(),
                    itemMainAudioPath = null,
                    totalScore = 10.0,
                    totalTimeInMinutes = 7.5,
                ).bindId(8).bindQuestionsDbToThis(
                    mutableListOf(
                        PracticeQuestion(
                            type = QuestionType.CORRECT.name,
                            answerKeyPoints = "①before->ago,此处讲述的是过去发生的事情,和一般过去时连用的应是ago,before常和完成时连用。"
                        ).bindId(23).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    70,
                                    LayoutUI.CORRECTION.name,
                                    null,
                                    correctAnswersSplitByPipes = "before->ago"
                                )
                            )
                        ),
                        PracticeQuestion(
                            type = QuestionType.CORRECT.name,
                            answerKeyPoints = "②by->of, with the help of...在……的帮助下,为固定短语。"
                        ).bindId(24).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    71,
                                    LayoutUI.CORRECTION.name,
                                    null,
                                    correctAnswersSplitByPipes = "by->of"
                                )
                            )
                        ),
                        PracticeQuestion(
                            type = QuestionType.CORRECT.name,
                            answerKeyPoints = "③year->years,句中year是可数名词,前面的these应修饰复数形式的名词。"
                        ).bindId(25).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    72,
                                    LayoutUI.CORRECTION.name,
                                    null,
                                    correctAnswersSplitByPipes = "year->years"
                                )
                            )
                        ),
                        PracticeQuestion(
                            type = QuestionType.CORRECT.name,
                            answerKeyPoints = "④had->have,时间状语since then 通常和现在完成时连用,而不是过去完成时。"
                        ).bindId(26).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    73,
                                    LayoutUI.CORRECTION.name,
                                    null,
                                    correctAnswersSplitByPipes = "had->have"
                                )
                            )
                        ),
                        PracticeQuestion(
                            type = QuestionType.CORRECT.name,
                            answerKeyPoints = "⑤As result->As a result,as a result 结果,为固定短语。"
                        ).bindId(27).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    74,
                                    LayoutUI.CORRECTION.name,
                                    null,
                                    correctAnswersSplitByPipes = "As result->As a result"
                                )
                            )
                        ),
                        PracticeQuestion(
                            type = QuestionType.CORRECT.name,
                            answerKeyPoints = "⑥somewhere->everywhere,根据句意可知此处表示“圣女果到处生长”,所以用everywhere。"
                        ).bindId(28).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    75,
                                    LayoutUI.CORRECTION.name,
                                    null,
                                    correctAnswersSplitByPipes = "somewhere->everywhere"
                                )
                            )
                        ),
                        PracticeQuestion(
                            type = QuestionType.CORRECT.name,
                            answerKeyPoints = "⑦taste->tasty,本句中small,juicy和tasty是并列的表语,所以要用形容词。"
                        ).bindId(29).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    76,
                                    LayoutUI.CORRECTION.name,
                                    null,
                                    correctAnswersSplitByPipes = "taste->tasty"
                                )
                            )
                        ),
                        PracticeQuestion(
                            type = QuestionType.CORRECT.name,
                            answerKeyPoints = "⑧much->many,根据前面的be动词are和后面的them可知应是代替可数名词复数的many。"
                        ).bindId(30).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    77,
                                    LayoutUI.CORRECTION.name,
                                    null,
                                    correctAnswersSplitByPipes = "much->many"
                                )
                            )
                        ),
                        PracticeQuestion(
                            type = QuestionType.CORRECT.name,
                            answerKeyPoints = "⑨but->yet/,but we->, we,引导让步状语从句的although不能和连词but连用,但是可以和副词yet连用。所以可以删掉but,也可以把but改为yet。"
                        ).bindId(31).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    78,
                                    LayoutUI.CORRECTION.name,
                                    null,
                                    correctAnswersSplitByPipes = "but->yet|,but we->, we,"
                                )
                            )
                        ),
                        PracticeQuestion(
                            type = QuestionType.CORRECT.name,
                            answerKeyPoints = "⑩wonderfully->wonderful,修饰名词时应用形容词。"
                        ).bindId(32).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    79,
                                    LayoutUI.CORRECTION.name,
                                    null,
                                    correctAnswersSplitByPipes = "wonderfully->wonderful"
                                )
                            )
                        ),
                    )
                )
            )
        )

        testQuestionSection5.let { section ->
            section.templatesDB?.forEach { template ->
                template.questionsDb?.forEach { question ->
                    question.optionsDb?.let { roomDb.practiceAnswerOption().insertAll(it) }
                }
                template.questionsDb?.let { roomDb.practiceQuestion().insertAll(it) }
            }
            section.templatesDB?.let { roomDb.practiceTemplate().insertAll(it) }
        }

        roomDb.practiceSection().insert(testQuestionSection5)

    }

    private fun loadBasicUsers(roomDb: RoomDB) {
        var user1 = MyUser(1, "henry", "no_nick_name")
        roomDb.myUser().insert(user1)
    }


    private fun loadBasicSection4(roomDb: RoomDB) {
        var testQuestionSection4 = PracticeSection(
            name = "听力部分",
        ).bindId(4).bindTemplatesDbToThis(
            mutableListOf(
                PracticeTemplate(
                    category = "听力部分",
                    requirement = """
                    听下面5段对话。每段对话后有一个小题，从题中所给的A、B、C三个选项中选出最佳选项。听完每段对话后，你都有10秒钟的时间来回答有关小题和阅读下一小题。每段对话仅读一遍。
                    例：How much is the shirt?
                    A. £19.15.     B. £9.18.    C. £9.15.
                    答案是C。
                """.trimIndent(),
                    """
                    录音稿
                    （Text 1）
                    W: Excuse me, sir. Visiting hours are over now. Your wife must get some rest.
                    M: Oh, I’m sorry, doctor. I didn’t hear the bell or I would have left earlier.

                    （Text 2）
                    M: Hello, my name is Jack. I need to get in shape. How do I register for the classes?
                    W: We’ll need you to join the gym, and then you can find out which classes fit your schedule the best.

                    （Text 3）
                    W: I’ll see you at the theater.
                    M: Better still. Let’s meet in the Red Lion Bar to have a nice little talk.
                    W: Good idea. And I’d love to have a drink there.

                    （Text 4）
                    M: Hello, my name is John Arber, and I’m calling to ask about the position advertised in Friday’s .
                    W: Yes, the position is still open. You could come over and have a talk with us.

                    （Text 5）
                    M: I have an extra ticket to the concert tonight. Would you like to join me?
                    W: Thanks, but I already have one. You can ask Emily. She might be interested.
                """.trimIndent(),
                    itemMainAudioPath = TempUtil.loadRawFile(
                        R.raw.demo_listening_2019_cee_vol1_00_02_49__00_06_26,
                        "demo_listening_2019_cee_vol1_00_02_49__00_06_26"
                    ),
                    totalScore = 7.5,
                    totalTimeInMinutes = 5.0,
                    keyPoints = "参考听力原文"
                ).bindId(6).bindQuestionsDbToThis(
                    mutableListOf(
                        PracticeQuestion(
                            type = QuestionType.SELECT.name,
                            text = "Where does this conversation take place?",
                        ).bindId(16).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    52,
                                    LayoutUI.TEXT_VIEW.name,
                                    "A. In a classroom."
                                ),
                                PracticeAnswerOption(
                                    53,
                                    LayoutUI.TEXT_VIEW.name,
                                    "B. In a hospital. ",
                                    correctAnswersSplitByPipes = "true"
                                ),
                                PracticeAnswerOption(54, LayoutUI.TEXT_VIEW.name, "C.In a museum."),
                            )
                        ),
                        PracticeQuestion(
                            text = "What does Jack want to do?"
                        ).bindId(17).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    55,
                                    displayText = "A. Take fitness classes.",
                                    correctAnswersSplitByPipes = "true"
                                ),
                                PracticeAnswerOption(
                                    56,
                                    displayText = "B. Buy a pair of gym shoes."
                                ),
                                PracticeAnswerOption(
                                    57,
                                    displayText = "C. Change his work schedule."
                                ),
                            )
                        ),
                        PracticeQuestion(
                            text = "What are the speakers talking about?"
                        ).bindId(18).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(58, displayText = "A. What to drink."),
                                PracticeAnswerOption(
                                    59,
                                    displayText = "B. Where to meet. ",
                                    correctAnswersSplitByPipes = "true"
                                ),
                                PracticeAnswerOption(60, displayText = "C. When to leave."),
                            )
                        ),
                        PracticeQuestion(
                            text = "What is the relationship between the speakers?"
                        ).bindId(19).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(61, displayText = "A. Colleges."),
                                PracticeAnswerOption(62, displayText = "B. Classmates. "),
                                PracticeAnswerOption(
                                    63,
                                    displayText = "C. Strangers.",
                                    correctAnswersSplitByPipes = "true"
                                ),
                            )
                        ),
                        PracticeQuestion(
                            text = "Why is Emily mentioned in the conversation?"
                        ).bindId(20).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    61,
                                    displayText = "A. She might want a ticket.",
                                    correctAnswersSplitByPipes = "true"
                                ),
                                PracticeAnswerOption(
                                    62,
                                    displayText = "B. She is looking for the man."
                                ),
                                PracticeAnswerOption(
                                    63,
                                    displayText = "C. She has an extra ticket."
                                ),
                            )
                        ),
                    )
                ),
                PracticeTemplate(
                    category = "听力部分",
                    requirement = """
                    听下面对话或独白。对话或独白后有几个小题，从题中所给的A、B、C三个选项中选出最佳选项。听每段对话或独白前，你将有时间阅读各个小题，每小题5秒钟；听完后，各小题将给出5秒钟的作答时间。每段对话或独白读两遍。
                """.trimIndent(),
                    itemMainText = """
                        W: Did you know James went out of business?
                        
                        M: Really? When was that?
                        
                        W: Last month.
                        
                        M: That’s too bad. He had owned that business for fifteen years. What happened?
                        
                        W: I don’t know. But life must be pretty tough for his family now. His sons are still so young. One is thirteen, and the other is ten.
                        
                        M: Well, maybe things are not as bad as they seem to be.
                        
                        W: I hope so.
                    """.trimIndent(),
                    itemMainAudioPath = TempUtil.loadRawFile(
                        R.raw.demo_listening_2019_cee_vol1_00_07_28__00_09_04,
                        "demo_listening_2019_cee_vol1_00_07_28__00_09_04"
                    ),
                    totalScore = 3.0,
                    totalTimeInMinutes = 2.5,
                    keyPoints = "参考听力原文"
                ).bindId(7).bindQuestionsDbToThis(
                    mutableListOf(
                        PracticeQuestion(
                            text = "How long did James run his business？"
                        ).bindId(21).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    64,
                                    displayText = "A.10 years.",
                                ),
                                PracticeAnswerOption(
                                    65,
                                    displayText = "B.13years. ",
                                ),
                                PracticeAnswerOption(
                                    66,
                                    displayText = "C.15 years.",
                                    correctAnswersSplitByPipes = "true"
                                ),
                            )
                        ),
                        PracticeQuestion(
                            text = "How does the woman feel about James' situation？"
                        ).bindId(22).bindOptionsDbToThis(
                            mutableListOf(
                                PracticeAnswerOption(
                                    67,
                                    displayText = "A. Embarrassed.",
                                ),
                                PracticeAnswerOption(
                                    68,
                                    displayText = "B. Concerned.  ",
                                    correctAnswersSplitByPipes = "true"
                                ),
                                PracticeAnswerOption(
                                    69,
                                    displayText = "C. Disappointed.",
                                ),
                            )
                        )
                    )
                )
            )


        )

        /*
        roomDb.practiceAnswerOption().insert(testOpion51)
        roomDb.practiceQuestion().insert(testQuestion9)
        roomDb.practiceTemplate().insert(testQuestionTemplate5)
        roomDb.practiceSection().insert(testQuestionSection3)
         */
        testQuestionSection4.let { section ->
            section.templatesDB?.forEach { template ->
                template.questionsDb?.forEach { question ->
                    question.optionsDb?.let { roomDb.practiceAnswerOption().insertAll(it) }
                }
                template.questionsDb?.let { roomDb.practiceQuestion().insertAll(it) }
            }
            section.templatesDB?.let { roomDb.practiceTemplate().insertAll(it) }
        }

        roomDb.practiceSection().insert(testQuestionSection4)
    }

    private fun loadBasicSection3(roomDb: RoomDB) {
        var testOpion24 = PracticeAnswerOption(
            24,
            LayoutUI.TEXT_VIEW.name,
            "A. Through his father's friend's help.",
            "true"
        )
        var testOpion25 = PracticeAnswerOption(
            25,
            LayoutUI.TEXT_VIEW.name,
            "B. Through his own efforts to exams.",
            null
        )
        var testOpion26 = PracticeAnswerOption(
            26,
            LayoutUI.TEXT_VIEW.name,
            "C. Through his father's request. ",
            null
        )
        var testOpion27 = PracticeAnswerOption(
            27,
            LayoutUI.TEXT_VIEW.name,
            "D. Through Mrs. Gutzlaff's influence.",
            null
        )

        var testOpion28 = PracticeAnswerOption(
            28,
            LayoutUI.TEXT_VIEW.name,
            "A. An English school was more influential.",
            null
        )
        var testOpion29 = PracticeAnswerOption(
            29,
            LayoutUI.TEXT_VIEW.name,
            "B. Foreign trade with China was developing fast.",
            null
        )
        var testOpion30 = PracticeAnswerOption(
            30,
            LayoutUI.TEXT_VIEW.name,
            "C. It met with Chinese public opinion.",
            null
        )
        var testOpion31 = PracticeAnswerOption(
            31,
            LayoutUI.TEXT_VIEW.name,
            "D. He could become a successful interpreter.",
            "true"
        )

        var testOpion32 =
            PracticeAnswerOption(32, LayoutUI.TEXT_VIEW.name, "A. It was skeptical. ", null)
        var testOpion33 =
            PracticeAnswerOption(33, LayoutUI.TEXT_VIEW.name, "B. It was mysterious. ", "true")
        var testOpion34 =
            PracticeAnswerOption(34, LayoutUI.TEXT_VIEW.name, "C. It was thoughtful.", null)
        var testOpion35 =
            PracticeAnswerOption(35, LayoutUI.TEXT_VIEW.name, "D. It was wonderful.", null)

        var testOpion36 = PracticeAnswerOption(36, LayoutUI.TEXT_VIEW.name, "A.understand ", "true")
        var testOpion37 = PracticeAnswerOption(37, LayoutUI.TEXT_VIEW.name, "B.stare at ", null)
        var testOpion38 = PracticeAnswerOption(38, LayoutUI.TEXT_VIEW.name, "C.search for", null)
        var testOpion39 =
            PracticeAnswerOption(39, LayoutUI.TEXT_VIEW.name, "D.concern about ", null)

        var testOpion40 = PracticeAnswerOption(
            40,
            LayoutUI.TEXT_VIEW.name,
            "A.It was carried out in a lab.",
            null
        )
        var testOpion41 = PracticeAnswerOption(
            41,
            LayoutUI.TEXT_VIEW.name,
            "B.42 subjects' eye movements were recorded.",
            "true"
        )
        var testOpion42 = PracticeAnswerOption(
            42,
            LayoutUI.TEXT_VIEW.name,
            "C.The students' daily movements were tracked.",
            null
        )
        var testOpion43 = PracticeAnswerOption(
            43,
            LayoutUI.TEXT_VIEW.name,
            "D.Its subjects' personalities were determined by computer.",
            null
        )

        var testOpion44 = PracticeAnswerOption(
            44,
            LayoutUI.TEXT_VIEW.name,
            "A.Robots and computers are socially conscious.",
            null
        )
        var testOpion45 = PracticeAnswerOption(
            45,
            LayoutUI.TEXT_VIEW.name,
            "B.People care less about improved, personalized services.",
            null
        )
        var testOpion46 = PracticeAnswerOption(
            46,
            LayoutUI.TEXT_VIEW.name,
            "C.Today's robots and computers can accustom to non-verbal information.",
            null
        )
        var testOpion47 = PracticeAnswerOption(
            47,
            LayoutUI.TEXT_VIEW.name,
            "D.The discovery will improve the interaction between human beings and machines.",
            "true"
        )

        var testOpion48 =
            PracticeAnswerOption(48, LayoutUI.TEXT_VIEW.name, "A.Human Personality Traits ", null)
        var testOpion49 = PracticeAnswerOption(
            49,
            LayoutUI.TEXT_VIEW.name,
            "B.What Human Eye Movements Are",
            null
        )
        var testOpion50 = PracticeAnswerOption(
            50,
            LayoutUI.TEXT_VIEW.name,
            "C.Tell Personalities by Eye Movements.",
            "true"
        )
        var testOpion51 = PracticeAnswerOption(
            51,
            LayoutUI.TEXT_VIEW.name,
            "D.How Humans and Machines Interact",
            null
        )


        var testQuestion9 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                How was the author admitted to Mrs. Gutzlaff's school?
            """.trimIndent(),
            "A",
            "（1）考查细节理解。根据第三段中的“Mrs. Gutzlaff's comprador(买办) happened to come from my village and was actually my father's friend and neighbor. It was through him that my parents heard about Mrs. Gutzlaff's school”可知，作者是通过父亲朋友的帮助进入到Mrs. Gutzlaff学校的。故选A。 ",
            1,
            1,
            "24,25,26,27"
        ).bindId(9)
        var testQuestion10 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                Why did the author's parents put him into an English school？
            """.trimIndent(),
            "D",
            "（2）考查细节理解。根据最后一段中的“In this way he might become an interpreter and have a more advantageous position to enter the business and diplomatic world.”可知父母认为我也许会成为一个很好的翻译，在做生意外交方面会有优势，所以他们才送我去英语学校。故选D。 ",
            1,
            1,
            "28,29,30,31"
        ).bindId(10)
        var testQuestion11 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                What did the author think of his parents' decision to put him into an English school? 
            """.trimIndent(),
            "B",
            "（3）考查推理判断。根据第三段中的“It has always been a mystery to me why my parents should put me into a foreign school, instead of a traditional Confucian school, where my big brother was placed.”可知对于父母送我去英语学校而不是传统的中文学校，我感觉很困惑。故选B。 ",
            2,
            1,
            "32,33,34,35"
        ).bindId(11)

        var testQuestion12 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                What do the underlined words "peer into" in Paragraph 2 probably mean? 
            """.trimIndent(),
            "A",
            "（1）考查词义猜测。根据第二段中的“The eyes, they say, are the windows to the soul.”人们说眼睛是心灵的窗户。可知，划线词组所在句的句意为“如果这是真的，由复杂的人工智能算法驱动的计算机和机器人可能很快就能探究你的灵魂。”故选A。",
            2,
            1,
            "36,37,38,39"
        ).bindId(12)
        var testQuestion13 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                How did the researchers conduct the research? 
            """.trimIndent(),
            "B",
            "（2）考查细节理解。根据第三段中的“whose team follows 42 study subjects around the university campus recording their eye movements, then determines their personality traits（特点）with \"well-established questionnaires\" for determining personality type,”该研究小组在大学校园内对42名研究对象进行了观察，记录他们的眼球运动，然后用“成熟的问题”来确定他们的性格特征）可知，研究人员通过记录42名研究对象的眼球运动来进行这项研究的。故选B。  ",
            1,
            1,
            "40,41,42,43"
        ).bindId(13)
        var testQuestion14 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                According to Tobias Loetscher, what can we know? 
            """.trimIndent(),
            "D",
            "（3）考查细节理解。根据第六段中的“The new findings could improve the way human beings interact with their computers and other high-tech devices, even robots, allowing for more natural and realistic social interactions with machines”这项新发现可能会改善人类与电脑和其他高科技设备，甚至是机器人的互动方式，从而使人类与机器进行更自然、更现实的社会互动成为可能）可知，这一发现将改善人类和机器之间的互动。故选D。",
            1,
            1,
            "44,45,46,47"
        ).bindId(14)
        var testQuestion15 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                What can be a suitable title for the text? 
            """.trimIndent(),
            "C",
            "（4）考查主旨大意。纵观全文可知，本文介绍了一项新的研究发现——由人工智能驱动的计算机和机器人可以通过阅读人类的眼球运动来“解读”人类的性格。故选C。",
            1,
            1,
            "48,49,50,51"
        ).bindId(15)

        var testQuestionTemplate4 = PracticeTemplate(
            category = "阅读理解", requirement = "阅读文章,回答问题。",
            itemMainText =
            """
                	I was born on the 17th of November 1828, in the village of Nam Ping, which is about four miles southwest of the Portuguese Colony (殖民地) of Macao, and is located on Pedro Island lying west of Macao, from which it is separated by a channel of half a mile wide.
                	
                    As early as 1834, an English lady, Mrs. Gutzlaff, wife of a missionary to China, came to Macao. Supported by the Ladies' Association in London for the promotion of female education in India and the East, she immediately took up the work of starting a girls' school for Chinese girls, which was soon followed by the opening of a boys' school.
                	
                    Mrs. Gutzlaff's comprador(买办) happened to come from my village and was actually my father's friend and neighbor. It was through him that my parents heard about Mrs. Gutzlaff's school and it was doubtlessly through his influence and means that my father got me admitted into the school. It has always been a mystery to me why my parents should put me into a foreign school, instead of a traditional Confucian school, where my big brother was placed. Most certainly such a step would have been more suitable for Chinese public opinion, taste, and the wants of the country, than to allow me to attend an English school. Moreover, a Chinese belief is the only avenue in China that leads to political promotion, influence, power and wealth. I can only guess that as foreign communication with China was just beginning to grow, my parents hoped that it might be worthwhile to put one of their sons to learning English. In this way he might become an interpreter and have a more advantageous position to enter the business and diplomatic world. I am wondering if that influenced my parents to put me into Mrs. Gutzlaff's School. As to what other sequences it has eventually brought about in my later life, they were entirely left in the hands of God.
            """.trimIndent(),
            keyPoints = """
                【答案】（1）A（2）D（3）B  
                【解析】【分析】本文是一篇记叙文，小时候父亲送作者去英语学校而不是中文学校，并分析了具体原因。
                【点评】本题考点涉及细节理解和推理判断两个题型的考查，是一篇故事类阅读，考生需要准确掌握细节信息，同时根据上下文的逻辑关系，进行分析，推理，从而选出正确答案。
            """.trimIndent(),
            totalScore = 6.0,
            totalTimeInMinutes = 3.0,
            practiceQuestionIds = "9,10,11"
        ).bindId(4)

        var testQuestionTemplate5 = PracticeTemplate(
            category = "阅读理解", requirement = "阅读文章,回答问题。",
            itemMainText =
            """
    A new study has shown how computers and robots powered by artificial intelligence can read human eye movements to "read" human personalities.     
	        
    The eyes, they say, are the windows to the soul. And if that is true, computers and robots powered by sophisticated(复杂的)artificial intelligence algorithms(算法)may soon have the ability to peer into your soul. That is the result of a new study on the connection between eye movements and personality, conducted by neuroscience researchers based at the University of South Australia and Published in the scientific Journal Frontiers in Neuroscience.     
	        
    "Eye movements during an everyday task predict aspects of our personality," wrote the researchers, led by University of South Australia neuroscientist Tobias Loetscher, whose team follows 42 study subjects around the university campus recording their eye movements, then determines their personality traits(特点)with "well-established questionnaires" for determining personality type, according to a summary of the study published by the site Science Daily.     
	        
    The researchers fed the data into their Al algorithms and found that computers running the algorithms were able to record human eye movements and immediately determine a person's major personality traits, such as "neuroticism, extraversion(外向)，agreeableness, conscientiousness, as well as perceptual(感知的)curiosity", the scientists wrote.     
	        
    "The new findings could improve the way human beings interact with their computers and other high-tech devices, even robots, allowing for more natural and realistic social interactions with machines," Loetscher said.     
	        
    "People are always looking for improved, personalized services. Today's robots and computers are not socially aware so they cannot adapt to non-verbal information," Loetscher said in a statement quoted by Indian Express. This research provides opportunities to develop robots and computers so that they can become more natural, and better at interpreting human social signals."     
	        
    The study revealed previously undiscovered relations between specific personality characteristics and specific eye movement tendencies, according to a summary in Britain's Daily Mail newspaper. 
            """.trimIndent(),
            keyPoints = """
                【答案】 （1）A （2）B （3）D （4）C    
                【解析】【分析】本文是一篇说明文，一项新的研究表明，由人工智能驱动的计算机和机器人可以通过阅读人类的眼球运动来“解读”人类的性格。
                【点评】本题考点涉及细节理解，词义猜测和主旨大意三个题型的考查，是一篇科研类阅读，要求考生在捕捉细节信息的基础上，进一步根据上下文的逻辑关系，进行分析，推理，概括和归纳，从而选出正确答案。
            """.trimIndent(),
            totalScore = 8.0,
            totalTimeInMinutes = 4.0,
            practiceQuestionIds = "12,13,14,15"
        ).bindId(5)

        var testQuestionSection3 = PracticeSection(
            name = "阅读理解",
            practiceTemplateIds = "4,5"
        ).bindId(3)

        roomDb.practiceAnswerOption().insert(testOpion24)
        roomDb.practiceAnswerOption().insert(testOpion25)
        roomDb.practiceAnswerOption().insert(testOpion26)
        roomDb.practiceAnswerOption().insert(testOpion27)
        roomDb.practiceAnswerOption().insert(testOpion28)
        roomDb.practiceAnswerOption().insert(testOpion29)
        roomDb.practiceAnswerOption().insert(testOpion30)
        roomDb.practiceAnswerOption().insert(testOpion31)
        roomDb.practiceAnswerOption().insert(testOpion32)
        roomDb.practiceAnswerOption().insert(testOpion33)
        roomDb.practiceAnswerOption().insert(testOpion34)
        roomDb.practiceAnswerOption().insert(testOpion35)
        roomDb.practiceAnswerOption().insert(testOpion36)
        roomDb.practiceAnswerOption().insert(testOpion37)
        roomDb.practiceAnswerOption().insert(testOpion38)
        roomDb.practiceAnswerOption().insert(testOpion39)
        roomDb.practiceAnswerOption().insert(testOpion40)
        roomDb.practiceAnswerOption().insert(testOpion41)
        roomDb.practiceAnswerOption().insert(testOpion42)
        roomDb.practiceAnswerOption().insert(testOpion43)
        roomDb.practiceAnswerOption().insert(testOpion44)
        roomDb.practiceAnswerOption().insert(testOpion45)
        roomDb.practiceAnswerOption().insert(testOpion46)
        roomDb.practiceAnswerOption().insert(testOpion47)
        roomDb.practiceAnswerOption().insert(testOpion48)
        roomDb.practiceAnswerOption().insert(testOpion49)
        roomDb.practiceAnswerOption().insert(testOpion50)
        roomDb.practiceAnswerOption().insert(testOpion51)

        roomDb.practiceQuestion().insert(testQuestion9)
        roomDb.practiceQuestion().insert(testQuestion10)
        roomDb.practiceQuestion().insert(testQuestion11)
        roomDb.practiceQuestion().insert(testQuestion12)
        roomDb.practiceQuestion().insert(testQuestion13)
        roomDb.practiceQuestion().insert(testQuestion14)
        roomDb.practiceQuestion().insert(testQuestion15)

        roomDb.practiceTemplate().insert(testQuestionTemplate4)
        roomDb.practiceTemplate().insert(testQuestionTemplate5)
        roomDb.practiceSection().insert(testQuestionSection3)

    }

    private fun loadBasicSection2(roomDb: RoomDB) {
        var testOpion4 = PracticeAnswerOption(4, LayoutUI.TEXT_VIEW.name, "A. Take your time", null)
        var testOpion5 = PracticeAnswerOption(5, LayoutUI.TEXT_VIEW.name, "B. You're right", null)
        var testOpion6 =
            PracticeAnswerOption(6, LayoutUI.TEXT_VIEW.name, "C. Whatever you say", null)
        var testOpion7 = PracticeAnswerOption(7, LayoutUI.TEXT_VIEW.name, "D. Take it easy", "true")

        var testOpion8 = PracticeAnswerOption(8, LayoutUI.TEXT_VIEW.name, "A. come along", "true")
        var testOpion9 = PracticeAnswerOption(9, LayoutUI.TEXT_VIEW.name, "B. come off", null)
        var testOpion10 = PracticeAnswerOption(10, LayoutUI.TEXT_VIEW.name, "C. come across", null)
        var testOpion11 = PracticeAnswerOption(11, LayoutUI.TEXT_VIEW.name, "D. come through", null)

        var testOpion12 = PracticeAnswerOption(12, LayoutUI.TEXT_VIEW.name, "A. but", "true")
        var testOpion13 = PracticeAnswerOption(13, LayoutUI.TEXT_VIEW.name, "B. and", null)
        var testOpion14 = PracticeAnswerOption(14, LayoutUI.TEXT_VIEW.name, "C. so", null)
        var testOpion15 = PracticeAnswerOption(15, LayoutUI.TEXT_VIEW.name, "D. or", null)

        var testOpion16 = PracticeAnswerOption(16, LayoutUI.TEXT_VIEW.name, "A. what", null)
        var testOpion17 = PracticeAnswerOption(17, LayoutUI.TEXT_VIEW.name, "B. when", null)
        var testOpion18 = PracticeAnswerOption(18, LayoutUI.TEXT_VIEW.name, "C. where", "true")
        var testOpion19 = PracticeAnswerOption(19, LayoutUI.TEXT_VIEW.name, "D. which", null)

        var testOpion20 = PracticeAnswerOption(20, LayoutUI.TEXT_VIEW.name, "A. caught", null)
        var testOpion21 =
            PracticeAnswerOption(21, LayoutUI.TEXT_VIEW.name, "B. to have caught", null)
        var testOpion22 = PracticeAnswerOption(22, LayoutUI.TEXT_VIEW.name, "C. to catch", null)
        var testOpion23 =
            PracticeAnswerOption(23, LayoutUI.TEXT_VIEW.name, "D. having caught", "true")

        var testQuestion4 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                --I'm sorry I made a mistake! 
                --____ Nobody is perfect.    
            """.trimIndent(),
            "D", "考查交际用语。根据后句“人无完人”可知，前一个人犯错误了，应叫他take it easy（放松）。",
            1, 1, "4,5,6,7"
        ).bindId(4)
        var testQuestion5 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                Would you like to ____ with us to the film tonight?
            """.trimIndent(),
            "A", "考查动词短语辨析。根据句意，与我们一道去看电影，故选A。come along with…与…一道。",
            1, 1, "8,9,10,11"
        ).bindId(5)
        var testQuestion6 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                I was glad to meet Jenny again, ____ I didn't want to spend all day with her.
            """.trimIndent(),
            "A", "考查并列连词。根据句意：再次见到Jenny我很高兴，但我不想整天都和她一起度过。",
            1, 1, "12,13,14,15"
        ).bindId(6)
        var testQuestion7 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                When I arrived, Bryan took me to see the house ____ I would be staying.
            """.trimIndent(),
            "C", "考查定语从句。定语从句中stay为不及物动词，故不缺主干成分，用关系副词；先行词为house，指地点，故用关系副词where。",
            1, 1, "16,17,18,19"
        ).bindId(7)
        var testQuestion8 = PracticeQuestion(
            QuestionType.SELECT.name,
            """
                I got to the office earlier that day, ____ the 7:30 train from Paddington
            """.trimIndent(),
            "D",
            "考查非谓语动词。根据句意，因为我赶上了7:30的车，所以那天我更早地到了办公室，可知赶车发生在到办公室之前，且与主语I之间为主动关系，故使用现在分词完成体表主动完成。",
            1,
            1,
            "20,21,22,23"
        ).bindId(8)

        var testQuestionTemplate3 = PracticeTemplate(
            category = "单项选择", requirement = "选择正确的选项填空",
            keyPoints = """
                1.【答案】D 
                  【解析】考查交际用语。根据后句“人无完人”可知，前一个人犯错误了，应叫他take it easy（放松）。
                2.【答案】A 
                  【解析】考查动词短语辨析。根据句意，与我们一道去看电影，故选A。come along with…与…一道。
                3.【答案】A 
                  【解析】考查并列连词。根据句意：再次见到Jenny我很高兴，但我不想整天都和她一起度过。
                4.【答案】C 
                  【解析】考查定语从句。定语从句中stay为不及物动词，故不缺主干成分，用关系副词；先行词为house，指地点，故用关系副词where。
                5.【答案】D 
                  【解析】考查非谓语动词。根据句意，因为我赶上了7:30的车，所以那天我更早地到了办公室，可知赶车发生在到办公室之前，且与主语I之间为主动关系，故使用现在分词完成体表主动完成。
            """.trimIndent(),
            totalScore = 10.0,
            totalTimeInMinutes = 5.0,
            practiceQuestionIds = "4,5,6,7,8"
        ).bindId(3)

        var testQuestionSection2 = PracticeSection(
            name = "单项选择",
            practiceTemplateIds = "3"
        ).bindId(2)


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

        roomDb.practiceTemplate().insert(testQuestionTemplate3)
        roomDb.practiceSection().insert(testQuestionSection2)
    }

    private fun loadBasicSection1(roomDb: RoomDB) {
        var testOpion1 = PracticeAnswerOption(1, "EDIT_TEXT", null, "do good to you")
        var testOpion2 = PracticeAnswerOption(2, "EDIT_TEXT", null, "at")
        var testOpion3 = PracticeAnswerOption(3, "EDIT_TEXT", null, "for")
        var testOpion100 = PracticeAnswerOption(100, "EDIT_TEXT", null, "for")

        var testQuestion1 = PracticeQuestion(
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
        ).bindId(1)
        var testQuestion2 = PracticeQuestion(
            "FILL",
            """
                    Artificial intelligence (AI) may become extremely good ____ achieving something other than what we really want.
                """.trimIndent(),
            "at",
            "be good at(doing) sth.擅长干某事(近义短语:do well in sth.)",
            2,
            1,
            "2"
        ).bindId(2)
        var testQuestion3 = PracticeQuestion(
            "FILL",
            """
                    Of course everyone knows that excercise is good ____ the body. 
                """.trimIndent(),
            "for",
            "be good for sb./sth.对某人/某事有好处(反义短语: be bad for sb./sth.)",
            2,
            1,
            "3,100"
        ).bindId(3)

        var testQuestionTemplate1 = PracticeTemplate(
            category = "同义句转换", requirement = "修改原句为同义句",
            keyPoints = "do good to sb./do sb. good 对某人有好处(反义短语: do harm to sb./do sb. harm)",
            totalScore = 2.0,
            totalTimeInMinutes = 1.0,
            practiceQuestionIds = "1"
        ).bindId(1)
        var testQuestionTemplate2 = PracticeTemplate(
            category = "介词单项填空", requirement = "使用适合介词填空(一空一词)",
            keyPoints = """
                    be good at(doing) sth.擅长干某事(近义短语:do well in sth.)
                    be good for sb./sth.对某人/某事有好处(反义短语: be bad for sb./sth.)
                """.trimIndent(),
            totalScore = 4.0,
            totalTimeInMinutes = 2.0,
            practiceQuestionIds = "2,3"
        ).bindId(2)

        var testQuestionSection1 = PracticeSection(
            browseMode = "SEQUENCE",
            name = "基础训练部分",
            practiceTemplateIds = "1,2"
        ).bindId(1)


        roomDb.practiceAnswerOption().insert(testOpion1)
        roomDb.practiceAnswerOption().insert(testOpion2)
        roomDb.practiceAnswerOption().insert(testOpion3)
        roomDb.practiceQuestion().insert(testQuestion1)
        roomDb.practiceQuestion().insert(testQuestion2)
        roomDb.practiceQuestion().insert(testQuestion3)
        roomDb.practiceTemplate().insert(testQuestionTemplate1)
        roomDb.practiceTemplate().insert(testQuestionTemplate2)
        roomDb.practiceSection().insert(testQuestionSection1)
    }

    private fun loadBasicExam(roomDb: RoomDB) {
        var examSimulation1 = ExamSimulation(
            1,
            province = "四川省",
            city = "广元市",
            testType = "期中考试",
            grade = "高1年级",
            name = "2020-2021学年上学期广元市静安区高一上学期一模试卷",
            tags = "基础训练,XYZ模拟高考",
            practiceSectionIds = "1,2,3,4,5"
        )


        roomDb.examSimulation().insert(examSimulation1)
    }
}