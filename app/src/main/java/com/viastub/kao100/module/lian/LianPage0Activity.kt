package com.viastub.kao100.module.lian

import android.media.MediaPlayer
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.PracticeSection
import kotlinx.android.synthetic.main.activity_lian_item_page.*

class LianPage0Activity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_lian_item_page
    }


    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        var sections = intent?.extras?.get("sections") as Array<PracticeSection>

        sections?.let {


        }


//        var category = intent?.let {
//            it.getStringExtra("category")
//        }
//        var target = intent?.let {
//            it.getStringExtra("target")
//        }
//        var lianItem: LianItem? = null
//        lianItem =
//            when (TempUtil.categoryMap[category]) {
//                0 -> prepareDateReading()
//                1 -> prepareCompletion()
//                2 -> prepareDataListnening()
//                3 -> prepareDataSelectSentences()
//                4 -> prepareDataSelectSingleOption()
//                5 -> prepareDataAnswerSingleWord()
//                6 -> prepareDataAnswerCorrectSentence()
//                7 -> prepareDataAnswerCorectFormsByChinesewords()
//                8 -> prepareDataAnswerByChineseTranslation()
//                else -> Fill_Single_One_Question()
//            }
//        lianItem = lianItem!!
//        TempUtil.counter += 1
//
//        lian_item_category.text = (target?.let { "$it>" } ?: "") + lianItem.category
//        lian_item_requirment.text = lianItem.requirement
//        lian_item_main_text.text = lianItem.itemMainText
//
//        lian_item_main_text.visibility =
//            if (lianItem.itemMainAudio != null) View.GONE else View.VISIBLE
//        lian_item_main_audio_start.visibility =
//            if (lianItem.itemMainAudio != null) View.VISIBLE else View.GONE
//        lian_item_main_holder.visibility =
//            if (lianItem.itemMainAudio == null && lianItem.itemMainText == null) View.GONE else View.VISIBLE
//
//        when (lianItem.type) {
//            LianItemType.FILL_ONE_LEN10 -> recycler_view_lian_item_questions.layoutManager =
//                GridLayoutManager(this, 2)
//            else -> recycler_view_lian_item_questions.layoutManager = LinearLayoutManager(this)
//        }
//
//        var adapter = LianItemQuestionAdapter(lianItem, recycler_view_lian_item_questions)
//        adapter.data = lianItem.questions!!
//        recycler_view_lian_item_questions.adapter = adapter
//
//        lian_item_show_review_btn.setOnClickListener {
//            lian_item_explanations.visibility =
//                if (lian_item_explanations.isVisible) View.INVISIBLE else View.VISIBLE
//            if (!lian_item_main_text.isVisible) {
//                lian_item_main_text.visibility = View.VISIBLE
//            }
//            if (!lian_item_main_holder.isVisible) {
//                lian_item_main_holder.visibility =
//                    if (lianItem.itemMainAudio == null && lianItem.itemMainText == null) View.GONE else View.VISIBLE
//            }
//        }
//
//        lian_item_explanations.visibility = View.INVISIBLE
//        lian_item_explanations.text = lianItem.reviews
//
//        lian_item_main_audio_start.setOnClickListener { plyDemoMp3Reading() }
//
//        lian_item_result_submit.setOnClickListener {
//            lianItem.submitted = true
//            recycler_view_lian_item_questions.adapter?.notifyDataSetChanged()
//        }
    }

    var mediaPlayer: MediaPlayer? = null

    private fun plyDemoMp3Reading() {
        mediaPlayer =
            mediaPlayer ?: MediaPlayer.create(this, R.raw.demo_2018_national_test_vol2_section1)
        mediaPlayer?.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer?.stop()
    }

//    private fun Fill_Single_One_Question(): LianItem? {
//
//        var lianItem = LianItem()
//
//        val lines =
//            BufferedReader(InputStreamReader(getResources().openRawResource(R.raw.demo_excercise_1))).readLines()
//
//        lianItem.questions = mutableListOf()
//
//        var tempQuestion = LianItemQuestion()
//
//        lines.forEach { x ->
//            var line = x.replace("<br>", "\\n")
//
//            if (line.startsWith("#Category:", ignoreCase = true)) {
//                lianItem.category = line.substring(10).trim()
//            } else if (line.startsWith("#QType:", ignoreCase = true)) {
//                lianItem.type = LianItemType.get(line.substring(6).trim())
//            } else if (line.startsWith("#Requirement:", ignoreCase = true)) {
//                lianItem.requirement = line.substring(13).trim()
//            } else if (line.startsWith("#Review:", ignoreCase = true)) {
//                lianItem.reviews = line.substring(8).trim()
//            } else if (line.startsWith("##Q:", ignoreCase = true)) {
//                //##Q:1:$
//                tempQuestion.id = line.substring(0, line.indexOf("$")).split(":")[1].toInt()
//                tempQuestion.questionMainText = line.substring(line.indexOf("$") + 1).trim()
//            } else if (line.startsWith("##A_Type:", ignoreCase = true)) {
//                tempQuestion.type =
//                    LianItemQuestionType.get(line.substring(line.indexOf("$") + 1).trim())
//            } else if (line.startsWith("##A_Correct:", ignoreCase = true)) {
//                tempQuestion.answerReviewed = line.substring(line.indexOf("$") + 1).trim()
//                tempQuestion.answerLians = mutableListOf(
//                    LianQuestionAnswer(
//                        1,
//                        null,
//                        correctAnswers = mutableSetOf(tempQuestion!!.answerReviewed!!)
//                    )
//                )
//                tempQuestion.type
//            } else if (line.startsWith("##A_Review:", ignoreCase = true)) {
//                tempQuestion.answerExplained = line.substring(line.indexOf("$") + 1).trim()
//            } else if (line.startsWith("##Q_END", ignoreCase = true)) {
//                lianItem.questions!!.add(tempQuestion)
//                tempQuestion = LianItemQuestion()
//            }
//        }
//
//        return lianItem
//
//    }
//
//    private fun prepareDataAnswerByChineseTranslation(): LianItem {
//        val requirement = """
//            根据汉语提示完成句子
//        """.trimIndent()
//        val itemMainAudio = null
//
//        val reviews = """
//
//        """.trimIndent()
////        val itemMainText = """
////
////        """.trimIndent()
//        val itemMainText = null
//
//        val questions = mutableListOf<LianItemQuestion>(
//            LianItemQuestion(
//                1, LianItemQuestionType.FILL_ONE_LEN10,
//                """
//                    他的棒球在床下
//                    His baseball ____ ____ the bed.
//                """.trimIndent(),
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, correctAnswers = mutableSetOf("is")),
//                    LianQuestionAnswer(2, correctAnswers = mutableSetOf("under", "below"))
//                )
//            ),
//            LianItemQuestion(
//                2, LianItemQuestionType.FILL_ONE_LEN10,
//                """
//                    你的父母现在在哪里?
//                    ____ ____ your parents now?
//                """.trimIndent(),
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, correctAnswers = mutableSetOf("Where")),
//                    LianQuestionAnswer(2, correctAnswers = mutableSetOf("are"))
//                )
//            ),
//            LianItemQuestion(
//                3, LianItemQuestionType.FILL_ONE_LEN10,
//                """
//                    盒子里有一些橙子。
//                    Some oranges ____ ____ the box.
//                """.trimIndent(),
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, correctAnswers = mutableSetOf("are")),
//                    LianQuestionAnswer(2, correctAnswers = mutableSetOf("in"))
//                )
//            ),
//            LianItemQuestion(
//                4, LianItemQuestionType.FILL_ONE_LEN10,
//                """
//                    我认为这本书是吉娜的。
//                    I ____ the book is ____.
//                """.trimIndent(),
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, correctAnswers = mutableSetOf("think")),
//                    LianQuestionAnswer(2, correctAnswers = mutableSetOf("Gina's"))
//                )
//            ),
//            LianItemQuestion(
//                5, LianItemQuestionType.FILL_ONE_LEN10,
//                """
//                    我不认识这位老师。
//                    I ____ ____ the teacher.
//                """.trimIndent(),
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, correctAnswers = mutableSetOf("don't")),
//                    LianQuestionAnswer(2, correctAnswers = mutableSetOf("know"))
//                )
//            ),
//
//            )
//
//
//        return LianItem(
//            1, "汉语提示完成句子", LianItemType.FILL_ONE_LEN40, requirement = requirement,
//            itemMainText = itemMainText,
//            itemMainAudio = itemMainAudio,
//            questions = questions,
//            reviews = reviews
//        )
//    }
//
//    private fun prepareDataAnswerCorectFormsByChinesewords(): LianItem {
//        val requirement = """
//            根据句意以及汉语提示填写单词
//        """.trimIndent()
//        val itemMainAudio = null
//
//        val reviews = """
//
//        """.trimIndent()
////        val itemMainText = """
////
////        """.trimIndent()
//        val itemMainText = null
//
//        val questions = mutableListOf<LianItemQuestion>(
//            LianItemQuestion(
//                1, LianItemQuestionType.FILL_ONE_LEN40,
//                "I ____(认为) the ruler is in the pencil box.",
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, correctAnswers = mutableSetOf("think"))
//                )
//            ),
//            LianItemQuestion(
//                2, LianItemQuestionType.FILL_ONE_LEN40,
//                """
//                    -Where are my books?
//                    -They're ____(在...下) the desk.
//                """.trimIndent(),
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, correctAnswers = mutableSetOf("under", "below"))
//                )
//            ),
//            LianItemQuestion(
//                3, LianItemQuestionType.FILL_ONE_LEN40,
//                """
//                   Those are Li Lei's and Li Ming's ____(椅子).
//                """.trimIndent(),
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, correctAnswers = mutableSetOf("chairs"))
//                )
//            ),
//            LianItemQuestion(
//                4, LianItemQuestionType.FILL_ONE_LEN40,
//                """
//                   Your hat in on your ____(头).
//                """.trimIndent(),
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, correctAnswers = mutableSetOf("head"))
//                )
//            ),
//            LianItemQuestion(
//                5, LianItemQuestionType.FILL_ONE_LEN40,
//                """
//                   -What's her telephone number?
//                   -Sorry, I don't ____(知道).
//                """.trimIndent(),
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, correctAnswers = mutableSetOf("know"))
//                )
//            ),
//
//            )
//
//
//        return LianItem(
//            1, "汉语提示填写单词", LianItemType.FILL_ONE_LEN40, requirement = requirement,
//            itemMainText = itemMainText,
//            itemMainAudio = itemMainAudio,
//            questions = questions,
//            reviews = reviews
//        )
//    }
//
//    private fun prepareDataAnswerCorrectSentence(): LianItem {
//        val requirement = """
//            将每一句的正确形式写在每句话的下面。
//        """.trimIndent()
//        val itemMainAudio = null
//
//        val reviews = """
//
//        """.trimIndent()
//        val itemMainText = """
//
//            When I was little, Friday’s night was our family game night. After supper, we would play card games of all sort in the sitting room. As the kid, I loved to watch cartoons, but no matter how many times I asked to watching them, my parents would not to let me. They would say to us that playing card games would help my brain. Still I unwilling to play the games for them sometimes. I didn’t realize how right my parents are until I entered high school. The games my parents taught me where I was a child turned out to be very useful later in my life.
//
//        """.trimIndent()
//
//        val questions = mutableListOf<LianItemQuestion>(
//            LianItemQuestion(
//                1,
//                LianItemQuestionType.FILL_ONE_LEN40,
//                "When I was little, Friday’s night was our family game night.",
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(
//                        1,
//                        "When I was little, Friday’s night was our family game night.",
//                        correctAnswers = mutableSetOf("When I was little, Friday night was our family game night.")
//                    )
//                ),
//                answerReviewed = "答案: Friday's->Friday"
//            ),
//            LianItemQuestion(
//                2,
//                LianItemQuestionType.FILL_ONE_LEN40,
//                "After supper, we would play card games of all sort in the sitting room.",
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(
//                        1,
//                        "After supper, we would play card games of all sort in the sitting room.",
//                        correctAnswers = mutableSetOf("After supper, we would play card games of all sorts in the sitting room.")
//                    )
//                ),
//                answerReviewed = "答案: sort->sorts"
//            ),
//            LianItemQuestion(
//                3,
//                LianItemQuestionType.FILL_ONE_LEN40,
//                "As the kid, I loved to watch cartoons, but no matter how many times I asked to watching them, my parents would not to let me.",
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(
//                        1,
//                        "As the kid, I loved to watch cartoons, but no matter how many times I asked to watching them, my parents would not to let me.",
//                        correctAnswers = mutableSetOf("As a kid, I loved to watch cartoons, but no matter how many times I asked to watch them, my parents would not let me.")
//
//                    )
//                ),
//                answerReviewed = "答案: As the kid -> As a kid ; watching->watch ; would not to let me -> would not let me"
//            ),
//            LianItemQuestion(
//                4,
//                LianItemQuestionType.FILL_ONE_LEN40,
//                "They would say to us that playing card games would help my brain.",
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(
//                        1,
//                        "They would say to us that playing card games would help my brain.",
//                        correctAnswers = mutableSetOf("They would say to me that playing card games would help my brain.")
//                    )
//                ),
//                answerReviewed = "答案: us->me"
//            ),
//            LianItemQuestion(
//                5,
//                LianItemQuestionType.FILL_ONE_LEN40,
//                "Still I unwilling to play the games for them sometimes.",
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(
//                        1,
//                        "Still I unwilling to play the games for them sometimes.",
//                        correctAnswers = mutableSetOf("Still I was unwilling to play the games with them sometimes.")
//                    )
//                ),
//                answerReviewed = "答案: I unwilling -> I was unwilling ; games for them-> games with them"
//            ),
//            LianItemQuestion(
//                6,
//                LianItemQuestionType.FILL_ONE_LEN40,
//                "I didn’t realize how right my parents are until I entered high school.",
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(
//                        1,
//                        "I didn’t realize how right my parents are until I entered high school.",
//                        correctAnswers = mutableSetOf("I didn’t realize how right my parents were until I entered high school.")
//                    )
//                ),
//                answerReviewed = "答案: are -> were"
//            ),
//            LianItemQuestion(
//                7,
//                LianItemQuestionType.FILL_ONE_LEN40,
//                "The games my parents taught me where I was a child turned out to be very useful later in my life.",
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(
//                        1,
//                        "The games my parents taught me where I was a child turned out to be very useful later in my life.",
//                        correctAnswers = mutableSetOf("The games my parents taught me when I was a child turned out to be very useful later in my life.")
//                    )
//                ),
//                answerReviewed = "答案: where -> when"
//            ),
//        )
//
//
//        return LianItem(
//            1, "短文改错", LianItemType.FILL_ONE_LEN40, requirement = requirement,
//            itemMainText = itemMainText,
//            itemMainAudio = itemMainAudio,
//            questions = questions,
//            reviews = reviews
//        )
//    }
//
//    private fun prepareDataAnswerSingleWord(): LianItem {
//        val requirement = """
//            阅读下面短文，在空白处填入1个适当的单词或括号内单词的正确形式。
//        """.trimIndent()
//        val itemMainAudio = null
//
//        val reviews = """
//
//        """.trimIndent()
//        val itemMainText = """
//
//            Diets have changed in China — and so too has its top crop. Since 2011, the country   1____（grow）more corn than rice. Corn production has jumped nearly 125 percent over   2____   past 25 years, while rice has increased only 7 percent.
//
//            A taste for meat is   3____ (actual) behind the change: An important part of its corn is used to feed chickens, pigs, and cattle. Another reason for corn’s rise: The government encourages farmers to grow corn instead of rice
//
//            4____  (improve) water quality. Corn uses less water   5____   rice and creates less fertilizer(化肥) runoff. This switch has decreased   6____   (pollute) in the country’s major lakes and reservoirs and made drinking water safer for people.
//
//            According to the World Bank, China accounts for about 30 percent of total   7____  (globe) fertilizer consumption. The Chinese Ministry of Agriculture finds that between 2005 — when the government   8____  (start) a soil-testing program   9____   gives specific fertilizer recommendations to farmers — and 2011, fertilizer use dropped by 7.7 million tons. That prevented the emission(排放) of 51.8 million tons of carbon dioxide. China’s approach to protecting its environment while   10____  (feed) its citizens ＂offers useful lessons for agriculture and food policymakers worldwide.＂ says the bank’s Juergen Voegele.
//        """.trimIndent()
//
//        val questions = mutableListOf<LianItemQuestion>(
//            LianItemQuestion(
//                1, LianItemQuestionType.FILL_ONE_LEN40, null,
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, "grow", mutableSetOf("has grown"))
//                )
//            ),
//            LianItemQuestion(
//                2, LianItemQuestionType.FILL_ONE_LEN40, null,
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, "", mutableSetOf("the"))
//                )
//            ),
//            LianItemQuestion(
//                3, LianItemQuestionType.FILL_ONE_LEN40, null,
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, "actual", mutableSetOf("actually"))
//                )
//            ),
//            LianItemQuestion(
//                4, LianItemQuestionType.FILL_ONE_LEN40, null,
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, "improve", mutableSetOf("to improve"))
//                )
//            ),
//            LianItemQuestion(
//                5, LianItemQuestionType.FILL_ONE_LEN40, null,
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, "", mutableSetOf("than"))
//                )
//            ),
//            LianItemQuestion(
//                6, LianItemQuestionType.FILL_ONE_LEN40, null,
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, "pollute", mutableSetOf("pollution"))
//                )
//            ),
//            LianItemQuestion(
//                7, LianItemQuestionType.FILL_ONE_LEN40, null,
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, "globe", mutableSetOf("global"))
//                )
//            ),
//            LianItemQuestion(
//                8, LianItemQuestionType.FILL_ONE_LEN40, null,
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, "start", mutableSetOf("started"))
//                )
//            ),
//            LianItemQuestion(
//                9, LianItemQuestionType.FILL_ONE_LEN40, null,
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, "", mutableSetOf("that", "which"))
//                )
//            ),
//            LianItemQuestion(
//                10, LianItemQuestionType.FILL_ONE_LEN40, null,
//                answerLians = mutableListOf(
//                    LianQuestionAnswer(1, "feed", mutableSetOf("feeding"))
//                )
//            ),
//
//            )
//
//
//        return LianItem(
//            1, "语法填空", LianItemType.FILL_ONE_LEN10, requirement = requirement,
//            itemMainText = itemMainText,
//            itemMainAudio = itemMainAudio,
//            questions = questions,
//            reviews = reviews
//        )
//    }
//
//
//    private fun prepareDataSelectSingleOption(): LianItem {
//        val requirement = """
//            选择正确的选项填空
//        """.trimIndent()
//        val itemMainAudio = null
//
//        /*
//        1.【答案】D 
//              【解析】考查交际用语。根据后句“人无完人”可知，前一个人犯错误了，应叫他take it easy（放松）。
//            2.【答案】A 
//              【解析】考查动词短语辨析。根据句意，与我们一道去看电影，故选A。come along with…与…一道。
//            3.【答案】A 
//              【解析】考查并列连词。根据句意：再次见到Jenny我很高兴，但我不想整天都和她一起度过。
//            4.【答案】C 
//              【解析】考查定语从句。定语从句中stay为不及物动词，故不缺主干成分，用关系副词；先行词为house，指地点，故用关系副词where。
//            5.【答案】D 
//              【解析】考查非谓语动词。根据句意，因为我赶上了7:30的车，所以那天我更早地到了办公室，可知赶车发生在到办公室之前，且与主语I之间为主动关系，故使用现在分词完成体表主动完成。
//
//         */
//        val reviews = """
//                    """.trimIndent()
//        val itemMainText = null
//
//        val questions = mutableListOf<LianItemQuestion>(
//            LianItemQuestion(
//                1,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                """
//                    --I'm sorry I made a mistake! 
//                    --____ Nobody is perfect.    
//                """.trimIndent(),
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. Take your time "),
//                    LianQuestionOption(2, "B. You're right "),
//                    LianQuestionOption(3, "C. Whatever you say "),
//                    LianQuestionOption(4, "D. Take it easy ", correctOption = true),
//                ),
//                answerReviewed = "【答案】D ",
//                answerExplained = "【解析】考查交际用语。根据后句“人无完人”可知，前一个人犯错误了，应叫他take it easy（放松）。"
//            ),
//            LianItemQuestion(
//                2,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                """
//                    Would you like to ____ with us to the film tonight?
//                """.trimIndent(),
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. come along", correctOption = true),
//                    LianQuestionOption(2, "B. come off"),
//                    LianQuestionOption(3, "C. come across "),
//                    LianQuestionOption(4, "D. come through "),
//                ),
//                answerReviewed = "【答案】A ",
//                answerExplained = "【解析】考查动词短语辨析。根据句意，与我们一道去看电影，故选A。come along with…与…一道。"
//            ),
//            LianItemQuestion(
//                3,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                """
//                    I was glad to meet Jenny again, ____ I didn't want to spend all day with her.
//                """.trimIndent(),
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. but", correctOption = true),
//                    LianQuestionOption(2, "B. and"),
//                    LianQuestionOption(3, "C. so"),
//                    LianQuestionOption(4, "D. or"),
//                ),
//                answerReviewed = "【答案】A ",
//                answerExplained = "【解析】考查并列连词。根据句意：再次见到Jenny我很高兴，但我不想整天都和她一起度过。"
//            ),
//            LianItemQuestion(
//                4,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                """
//                    When I arrived, Bryan took me to see the house ____ I would be staying.
//                """.trimIndent(),
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. what   "),
//                    LianQuestionOption(2, "B. when"),
//                    LianQuestionOption(3, "C. where", correctOption = true),
//                    LianQuestionOption(4, "D. which"),
//                ),
//                answerReviewed = "【答案】C ",
//                answerExplained = "【解析】考查定语从句。定语从句中stay为不及物动词，故不缺主干成分，用关系副词；先行词为house，指地点，故用关系副词where。"
//            ),
//            LianItemQuestion(
//                5,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                """
//                    I got to the office earlier that day, ____ the 7:30 train from Paddington
//                """.trimIndent(),
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. caught"),
//                    LianQuestionOption(2, "B. to have caught"),
//                    LianQuestionOption(3, "C. to catch"),
//                    LianQuestionOption(4, "D. having caught", correctOption = true),
//                ),
//                answerReviewed = "【答案】D ",
//                answerExplained = "【解析】考查非谓语动词。根据句意，因为我赶上了7:30的车，所以那天我更早地到了办公室，可知赶车发生在到办公室之前，且与主语I之间为主动关系，故使用现在分词完成体表主动完成。"
//            ),
//        )
//
//
//        return LianItem(
//            1, "单项选择", LianItemType.SELECT_ONE_LEN40, requirement = requirement,
//            itemMainText = itemMainText,
//            itemMainAudio = itemMainAudio,
//            questions = questions,
//            reviews = reviews
//        )
//    }
//
//    private fun prepareDataSelectSentences(): LianItem {
//        val requirement = """
//            根据短文内容，从短文后的选项中选出能填入空白处的最佳选项。选项中有两项为多余选项。
//        """.trimIndent()
//        val itemMainAudio = null
//
//        val reviews = """
//
//        """.trimIndent()
//        val itemMainText = """
//
//        If you are already making the time to exercise, it is good indeed! With such busy lives, it can be hard to try and find the time to work out.   1____   Working out in the morning provides additional benefits beyond being physically fit.
//             Your productivity is improved. Exercising makes you more awake and ready to handle whatever is ahead of you for the day.   2____
//             Your metabolism(新陈代谢) gets a head start.   3____   If you work out in the mornings, then you will be getting the calorie(卡路里) burning benefits for the whole day, not in your sleep.
//             4____   Studies found that people who woke up early for exercise slept better than those who exercised in the evening. Exercise energizes you, so it is more difficult to relax and have a peaceful sleep when you are very excited.
//             5____   If you work out bright and early in the morning, you will be more likely to stick to healthy food choices throughout the day. Who would want to ruin their good workout by eating junk food? You will want to continue to focus on positive choices.
//        There are a lot of benefits to working out, especially in the mornings. Set your alarm clock an hour early and push yourself to work out! You will feel energized all day long.
//
//        A. You will stick to your diet.
//        B. Your quality of sleep improves.
//        C. You prefer healthy food to fast food.
//        D. There is no reason you should exercise in the morning.
//        E. You can keep your head clear for 4-10 hours after exercise.
//        F. After you exercise, you continue to burn calories throughout the day.
//        G. If you are planning to do exercise regularly, or you’re doing it now, then listen up!
//
//        """.trimIndent()
//
//        val questions = mutableListOf<LianItemQuestion>(
//            LianItemQuestion(
//                1, LianItemQuestionType.SELECT_ONE_LEN2, null,
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A."),
//                    LianQuestionOption(2, "B."),
//                    LianQuestionOption(3, "C.", correctOption = true),
//                    LianQuestionOption(4, "D."),
//                    LianQuestionOption(5, "E."),
//                    LianQuestionOption(6, "F."),
//                    LianQuestionOption(7, "G.")
//                )
//            ),
//            LianItemQuestion(
//                2, LianItemQuestionType.SELECT_ONE_LEN2, null,
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A."),
//                    LianQuestionOption(2, "B."),
//                    LianQuestionOption(3, "C."),
//                    LianQuestionOption(4, "D."),
//                    LianQuestionOption(5, "E.", correctOption = true),
//                    LianQuestionOption(6, "F."),
//                    LianQuestionOption(7, "G.")
//                )
//            ),
//            LianItemQuestion(
//                3, LianItemQuestionType.SELECT_ONE_LEN2, null,
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A."),
//                    LianQuestionOption(2, "B.", correctOption = true),
//                    LianQuestionOption(3, "C."),
//                    LianQuestionOption(4, "D."),
//                    LianQuestionOption(5, "E."),
//                    LianQuestionOption(6, "F."),
//                    LianQuestionOption(7, "G.")
//                )
//            ),
//            LianItemQuestion(
//                4, LianItemQuestionType.SELECT_ONE_LEN2, null,
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A."),
//                    LianQuestionOption(2, "B."),
//                    LianQuestionOption(3, "C."),
//                    LianQuestionOption(4, "D."),
//                    LianQuestionOption(5, "E."),
//                    LianQuestionOption(6, "F."),
//                    LianQuestionOption(7, "G.", correctOption = true)
//                )
//            ),
//            LianItemQuestion(
//                5, LianItemQuestionType.SELECT_ONE_LEN2, null,
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A.", correctOption = true),
//                    LianQuestionOption(2, "B."),
//                    LianQuestionOption(3, "C."),
//                    LianQuestionOption(4, "D."),
//                    LianQuestionOption(5, "E."),
//                    LianQuestionOption(6, "F."),
//                    LianQuestionOption(7, "G.")
//                )
//            )
//        )
//
//
//        return LianItem(
//            1, "短文填空", LianItemType.SELECT_ONE_LEN40, requirement = requirement,
//            itemMainText = itemMainText,
//            itemMainAudio = itemMainAudio,
//            questions = questions,
//            reviews = reviews
//        )
//    }
//
//    private fun prepareDataListnening(): LianItem {
//        val requirement = """
//            听下面5短对话，每段对话后有一个小题，从题中给的A、B、C三个选项中选出最佳选项。听完每段对话后，你都有10秒钟的时间来回答有关小题和阅读下一小题。每段对话仅读一遍。
//            例：How much is the shirt? 
//            A. ￡19.15.  B. ￡9.18. C. ￡9.15. 
//        """.trimIndent()
//        val itemMainAudio = "R.raw.demo_2018_national_test_vol2_section1"
//
//        val reviews = """
//
//        """.trimIndent()
//        val itemMainText = """
//            Text 1  
//            W: So, how is your German class going, John? 
//            M: Well, not bad. The pronunciation is fine with me, and its vocabulary is similar to English. But I’m finding the grammar awful.  
//            W: Well, it takes a while to get it right. 
//
//            Text 2  
//            W: I hope you can come to the party on Saturday. 
//            M: I didn’t know I was invited. 
//            W: Sure you are. Everyone in our office is invited. 
//
//            Text 3 
//            W: May I help you? 
//            M: Yes. When is the next train to London? 
//            W: Oh, let me check. It leaves in twenty minutes. 
//            M: One ticket, please. 
//
//            Text 4  
//            W: Charlie, do you know a restaurant called Bravo? 
//            M: Bravo…I know the name. But I’m not sure where it is. 
//            W: It’s on George Street. The food there is excellent. 
//
//            Text 5 
//            W: Brian, I just had an interview. They said they would make a decision soon.  
//            M: What are your chances of getting the job? 
//            W: Quite good. I think the interview went very well. 
//        """.trimIndent()
//
//        val questions = mutableListOf<LianItemQuestion>(
//            LianItemQuestion(
//                1,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                "What does John find difficult in learning German?",
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. Pronunciation. "),
//                    LianQuestionOption(2, "B. Vocabulary. "),
//                    LianQuestionOption(3, "C. Grammar. ", true),
//                )
//            ),
//            LianItemQuestion(
//                2,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                "What is the probable relationship between the speakers?",
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. Colleagues. "),
//                    LianQuestionOption(2, "B. Brother and sister.  "),
//                    LianQuestionOption(3, "C. Teacher and student. ", true)
//                )
//            ),
//            LianItemQuestion(
//                3,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                "Where does the conversation probably take place? ",
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. In a bank. "),
//                    LianQuestionOption(2, "B. At a ticket office."),
//                    LianQuestionOption(3, "C. On a train. ", true)
//                )
//            ),
//            LianItemQuestion(
//                4, LianItemQuestionType.SELECT_ONE_LEN40, "What are the speakers talking about? ",
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. restaurant. "),
//                    LianQuestionOption(2, "B. street. "),
//                    LianQuestionOption(3, "C. dish. ", true)
//                )
//            ),
//            LianItemQuestion(
//                5,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                "What does the woman think of her interview? ",
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. It was tough. "),
//                    LianQuestionOption(2, "B. It was interesting."),
//                    LianQuestionOption(3, "C. It was successful.", true)
//                )
//            )
//        )
//
//
//        return LianItem(
//            1, "听力测试", LianItemType.SELECT_ONE_LEN40, requirement = requirement,
//            itemMainText = itemMainText,
//            itemMainAudio = itemMainAudio,
//            questions = questions,
//            reviews = reviews
//        )
//    }
//
//    private fun prepareCompletion(): LianItem {
//        val requirement = "阅读下面短文，从短文后各题所给的A、B、C和D四个选项中，选出可以填入空白处的最佳选项。"
//        val reviews = """
//
//        """.trimIndent()
//        val itemMainText = """
//
//            Since our twins began learning to walk, my wife and I have kept telling them that our sliding glass door is just a window. The   1____   is obvious. If we   2____   it is a door, they’ll want to go outside   3____  . It will drive us crazy. The kids apparently know the   4____  . But our insisting it’s   5____   a window has kept them from   6____   millions of requests to open the door.
//
//            I hate lying to the kids. One day they’ll   7____   and discover that everything they’ve always known about windows is a   8____  .
//
//            I wonder if   9____   should always tell the truth no matter the   10____  . I have a very strong   11____   that the lie we’re telling is doing   12____   damage to our children. Windows and doors have   13____   metaphorical（比喻） meanings. I’m telling them they can’t open what they absolutely know is a door. What if later in   14____   they come to a metaphorical door, like an opportunity（机会） of some sort, and   15____   opening the door and taking the opportunity, they just   16____   it and wonder, ＂What if it isn’t a door?＂ That is, ＂What if it isn’t a   17____   opportunity?＂
//
//            Maybe it’s an unreasonable fear. But the   18____   is that I shouldn’t lie to my kids. I should just   19____   repeatedly having to say, ＂No. We can’t go outside now.＂ Then when they come to other doors in life, be they real or metaphorical, they won’t   20____   to open them and walk through.
//        """.trimIndent()
//        val questions = mutableListOf<LianItemQuestion>(
//            LianItemQuestion(
//                1, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. relief "),
//                    LianQuestionOption(2, "B. target"),
//                    LianQuestionOption(3, "C. reason", true),
//                    LianQuestionOption(4, "D. case")
//                )
//            ),
//            LianItemQuestion(
//                2, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. admit "),
//                    LianQuestionOption(2, "B. believe"),
//                    LianQuestionOption(3, "C. mean", true),
//                    LianQuestionOption(4, "D. realize")
//                )
//            ),
//            LianItemQuestion(
//                3, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. gradually "),
//                    LianQuestionOption(2, "B. constantly"),
//                    LianQuestionOption(3, "C. temporarily", true),
//                    LianQuestionOption(4, "D. casually")
//                )
//            ),
//            LianItemQuestion(
//                4, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. result "),
//                    LianQuestionOption(2, "B. danger"),
//                    LianQuestionOption(3, "C. method", true),
//                    LianQuestionOption(4, "D. truth")
//                )
//            ),
//            LianItemQuestion(
//                5, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. merely "),
//                    LianQuestionOption(2, "B. slightly"),
//                    LianQuestionOption(3, "C. hardly", true),
//                    LianQuestionOption(4, "D. partly")
//                )
//            ),
//            LianItemQuestion(
//                6, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. reviewing "),
//                    LianQuestionOption(2, "B. approving"),
//                    LianQuestionOption(3, "C. receiving", true),
//                    LianQuestionOption(4, "D. attempting")
//                )
//            ),
//            LianItemQuestion(
//                7, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. win out "),
//                    LianQuestionOption(2, "B. give up"),
//                    LianQuestionOption(3, "C. wake up", true),
//                    LianQuestionOption(4, "D. stand out")
//                )
//            ),
//            LianItemQuestion(
//                8, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. dream "),
//                    LianQuestionOption(2, "B. lie"),
//                    LianQuestionOption(3, "C. fantasy", true),
//                    LianQuestionOption(4, "D. fact")
//                )
//            ),
//            LianItemQuestion(
//                9, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. parents "),
//                    LianQuestionOption(2, "B. twins"),
//                    LianQuestionOption(3, "C. colleagues", true),
//                    LianQuestionOption(4, "D. teachers")
//                )
//            ),
//            LianItemQuestion(
//                10, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. restrictions "),
//                    LianQuestionOption(2, "B. explanations"),
//                    LianQuestionOption(3, "C. differences", true),
//                    LianQuestionOption(4, "D. consequences")
//                )
//            ),
//            LianItemQuestion(
//                11, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. demand "),
//                    LianQuestionOption(2, "B. fear"),
//                    LianQuestionOption(3, "C. desire", true),
//                    LianQuestionOption(4, "D. doubt")
//                )
//            ),
//            LianItemQuestion(
//                12, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. physical "),
//                    LianQuestionOption(2, "B. biological"),
//                    LianQuestionOption(3, "C. spiritual", true),
//                    LianQuestionOption(4, "D. behavioral")
//                )
//            ),
//            LianItemQuestion(
//                13, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. traditional "),
//                    LianQuestionOption(2, "B. important"),
//                    LianQuestionOption(3, "C. double", true),
//                    LianQuestionOption(4, "D. original")
//                )
//            ),
//            LianItemQuestion(
//                14, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. life "),
//                    LianQuestionOption(2, "B. time"),
//                    LianQuestionOption(3, "C. reply", true),
//                    LianQuestionOption(4, "D. history")
//                )
//            ),
//            LianItemQuestion(
//                15, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. by comparison with "),
//                    LianQuestionOption(2, "B. in addition to "),
//                    LianQuestionOption(3, "C. regardless of ", true),
//                    LianQuestionOption(4, "D. instead of")
//                )
//            ),
//            LianItemQuestion(
//                16, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. get hold of "),
//                    LianQuestionOption(2, "B. stare at "),
//                    LianQuestionOption(3, "C. knock on ", true),
//                    LianQuestionOption(4, "D. make use of")
//                )
//            ),
//            LianItemQuestion(
//                17, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. real "),
//                    LianQuestionOption(2, "B. typical"),
//                    LianQuestionOption(3, "C. similar", true),
//                    LianQuestionOption(4, "D. limited")
//                )
//            ),
//            LianItemQuestion(
//                18, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. safety rule "),
//                    LianQuestionOption(2, "B. comfort zone "),
//                    LianQuestionOption(3, "C. bottom line ", true),
//                    LianQuestionOption(4, "D. top secret")
//                )
//            ),
//            LianItemQuestion(
//                19, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. delay "),
//                    LianQuestionOption(2, "B. regret"),
//                    LianQuestionOption(3, "C. enjoy", true),
//                    LianQuestionOption(4, "D. accept")
//                )
//            ),
//            LianItemQuestion(
//                20, LianItemQuestionType.SELECT_ONE_LEN10, null, mutableListOf(
//                    LianQuestionOption(1, "A. hurry "),
//                    LianQuestionOption(2, "B. decide"),
//                    LianQuestionOption(3, "C. hesitate", true),
//                    LianQuestionOption(4, "D. intend")
//                )
//            ),
//
//
//            )
//
//
//        return LianItem(
//            1, "完形填空", LianItemType.SELECT_ONE_LEN40, requirement = requirement,
//            itemMainText = itemMainText,
//            questions = questions,
//            reviews = reviews
//        )
//    }
//
//    private fun prepareDateReading(): LianItem {
//        val requirement = "阅读下列短文，从每题所给的A、B、C和D四个选项中，选出最佳选项。"
//        val reviews = """
//            【解析】【分析】本文是一篇议论文，作者认为把大学四年的学制缩短为三年不是一个好主意，保证足够的时间才能保证大学教育的质量。 
// （1）考查主旨大意。根据第二段中的“Few US universities have formally approved a ‘three-year degree’ model.”很少有美国大学正式批准“三年学位”模式；以及最后一段中的“In my opinion, a quality four-year education is always superior to a quality three-year education.”在我看来，优质的四年教育总是优于优质的三年教育。可推断，文章作者认为把大学四年的学制缩短为三年不是一个好主意，学好大学功课是需要付出时间的，所以这篇文章的中心思想是“最好的学习是随着时间的推移而进行的。”，故选B。 
// （2）考查推理判断。根据第二段中的“Few US universities have formally approved a ‘three-year degree’ model.”美国很少有大学正式批准“三年制学位”模式。由此推断出，大多数美国大学反对“三年制学位”模式，故选A。 
// （3）考查细节理解。根据第三段中的“For one thing, most universities already allow highly qualified students to graduate early by testing out of certain classes and obtaining a number of college credits.”一方面，大多数大学已经允许合格的学生通过某些课程的考试和获得一些大学学分提前毕业。可知，特别优秀的大学生可以提前毕业。故选D。 
// （4）考查推理判断。根据第四段中的“A college education requires sufficient time for a student to become skilled in their major and do coursework in fields outside their major.”大学教育要求学生有足够的时间掌握专业技能，并在专业以外的领域学习课程；以及“It is not a good idea to water down education, any more than it's not a good idea to water down medicine.”削弱教育不是个好主意，就像削弱医学不是个好主意一样。可知作者非常看重大学教育质量。故选C。 
// 【点评】本题考点涉及细节理解，推理判断和主旨大意三个题型的考查，是一篇教育类阅读，考生需要准确捕捉细节信息，并根据上下文进行逻辑推理，概括归纳，从而选出正确答案。 
//        """.trimIndent()
//        val itemMainText = """
//            Race walking shares many fitness benefits with running, research shows, while most likely contributing to fewer injuries. It does, however, have its own problem.
//
//                        Race walkers are conditioned athletes. The longest track and field event at the Summer Olympics is the 50-kilometer race walk, which is about five miles longer than the marathon. But the sport’s rules require that a race walker’s knees stay straight through most of the leg swing and one foot remain in contact(接触) with the ground at all times. It’s this strange form that makes race walking such an attractive activity, however, says Jaclyn Norberg, an assistant professor of exercise science at Salem State University in Salem, Mass.
//
//                        Like running, race walking is physically demanding, she says. According to most calculations, race walkers moving at a pace of six miles per hour would burn about 800 calories(卡路里) per hour, which is approximately twice as many as they would burn walking, although fewer than running, which would probably burn about 1,000 or more calories per hour.
//
//                        However, race walking does not pound the body as much as running does, Dr. Norberg says. According to her research, runners hit the ground with as much as four times their body weight per step, while race walkers, who do not leave the ground, create only about 1.4 times their body weight with each step.
//
//                        As a result, she says, some of the injuries associated with running, such as runner’s knee, are uncommon among race walkers. But the sport’s strange form does place considerable stress on the ankles and hips, so people with a history of such injuries might want to be cautious in adopting the sport. In fact, anyone wishing to try race walking should probably first consult a coach or experienced racer to learn proper technique, she says. It takes some practice.
//
//        """.trimIndent()
//        val questions = mutableListOf<LianItemQuestion>(
//            LianItemQuestion(
//                1,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                "Why are race walkers conditioned athletes?",
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. They must run long distances."),
//                    LianQuestionOption(2, "B. They are qualified for the marathon."),
//                    LianQuestionOption(3, "C. They have to follow special rules.", true),
//                    LianQuestionOption(4, "D. They are good at swinging their legs.")
//                )
//            ),
//            LianItemQuestion(
//                2,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                "What advantage does race walking have over running?",
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. It’s more popular at the Olympics."),
//                    LianQuestionOption(2, "B. It’s less challenging physically."),
//                    LianQuestionOption(3, "C. It’s more effective in body building.", true),
//                    LianQuestionOption(4, "D. It’s less likely to cause knee injuries.")
//                )
//            ),
//            LianItemQuestion(
//                3,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                "What is Dr. Norberg’s suggestion for someone trying race walking?",
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. Getting experts’ opinions."),
//                    LianQuestionOption(2, "B. Having a medical checkup."),
//                    LianQuestionOption(3, "C. Hiring an experienced coach.", true),
//                    LianQuestionOption(4, "D. Doing regular exercises.")
//                )
//            ),
//            LianItemQuestion(
//                4,
//                LianItemQuestionType.SELECT_ONE_LEN40,
//                "Which word best describes the author’s attitude to race walking?",
//                optionLians = mutableListOf(
//                    LianQuestionOption(1, "A. Skeptical."),
//                    LianQuestionOption(2, "B. Objective."),
//                    LianQuestionOption(3, "C. Tolerant.", true),
//                    LianQuestionOption(4, "D. Conservative.")
//                )
//            )
//        )
//
//
//        return LianItem(
//            1, "阅读理解", LianItemType.SELECT_ONE_LEN40, requirement = requirement,
//            itemMainText = itemMainText,
//            questions = questions,
//            reviews = reviews
//        )
//    }

}