package com.qianli.cixuekaolian.module.lian

import android.view.View
import androidx.core.view.isVisible
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.adapter.LianItemQuestionAdapter
import com.qianli.cixuekaolian.base.BaseActivity
import com.qianli.cixuekaolian.beans.LianItem
import com.qianli.cixuekaolian.beans.LianItemQuestion
import com.qianli.cixuekaolian.beans.LianQuestionOption
import kotlinx.android.synthetic.main.activity_lian_item_page.*

class LianPage0Activity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_lian_item_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()

        header_back.setOnClickListener { onBackPressed() }

        val lianItem = if (Math.random() >= 0.5) prepareDateReading() else prepareCompletion()
//        val lianItem = prepareDateReading()//阅读理解
//        val lianItem = prepareCompletion()//完形填空
//


        lian_item_requirment.text = lianItem.requirement
        lian_item_main_text.text = lianItem.itemMainText

        var adapter = LianItemQuestionAdapter()
        adapter.data = lianItem.questions!!
        recycler_view_lian_item_questions.adapter = adapter

        lian_item_show_review_btn.setOnClickListener {
            lian_item_explanations.visibility =
                if (lian_item_explanations.isVisible) View.INVISIBLE else View.VISIBLE
        }

        lian_item_explanations.visibility = View.INVISIBLE
        lian_item_explanations.text = """
            
        """.trimIndent()

    }

    private fun prepareCompletion(): LianItem {
        val requirement = "阅读下面短文，从短文后各题所给的A、B、C和D四个选项中，选出可以填入空白处的最佳选项。"
        val reviews = """
            
        """.trimIndent()
        val itemMainText = """
            
            Since our twins began learning to walk, my wife and I have kept telling them that our sliding glass door is just a window. The   1____   is obvious. If we   2____   it is a door, they’ll want to go outside   3____  . It will drive us crazy. The kids apparently know the   4____  . But our insisting it’s   5____   a window has kept them from   6____   millions of requests to open the door.

            I hate lying to the kids. One day they’ll   7____   and discover that everything they’ve always known about windows is a   8____  .
            
            I wonder if   9____   should always tell the truth no matter the   10____  . I have a very strong   11____   that the lie we’re telling is doing   12____   damage to our children. Windows and doors have   13____   metaphorical（比喻） meanings. I’m telling them they can’t open what they absolutely know is a door. What if later in   14____   they come to a metaphorical door, like an opportunity（机会） of some sort, and   15____   opening the door and taking the opportunity, they just   16____   it and wonder, ＂What if it isn’t a door?＂ That is, ＂What if it isn’t a   17____   opportunity?＂
            
            Maybe it’s an unreasonable fear. But the   18____   is that I shouldn’t lie to my kids. I should just   19____   repeatedly having to say, ＂No. We can’t go outside now.＂ Then when they come to other doors in life, be they real or metaphorical, they won’t   20____   to open them and walk through.
        """.trimIndent()
        val questions = mutableListOf<LianItemQuestion>(
            LianItemQuestion(
                1, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. relief "),
                    LianQuestionOption(2, "B. target"),
                    LianQuestionOption(3, "C. reason", true),
                    LianQuestionOption(4, "D. case")
                )
            ),
            LianItemQuestion(
                2, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. admit "),
                    LianQuestionOption(2, "B. believe"),
                    LianQuestionOption(3, "C. mean", true),
                    LianQuestionOption(4, "D. realize")
                )
            ),
            LianItemQuestion(
                3, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. gradually "),
                    LianQuestionOption(2, "B. constantly"),
                    LianQuestionOption(3, "C. temporarily", true),
                    LianQuestionOption(4, "D. casually")
                )
            ),
            LianItemQuestion(
                4, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. result "),
                    LianQuestionOption(2, "B. danger"),
                    LianQuestionOption(3, "C. method", true),
                    LianQuestionOption(4, "D. truth")
                )
            ),
            LianItemQuestion(
                5, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. merely "),
                    LianQuestionOption(2, "B. slightly"),
                    LianQuestionOption(3, "C. hardly", true),
                    LianQuestionOption(4, "D. partly")
                )
            ),
            LianItemQuestion(
                6, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. reviewing "),
                    LianQuestionOption(2, "B. approving"),
                    LianQuestionOption(3, "C. receiving", true),
                    LianQuestionOption(4, "D. attempting")
                )
            ),
            LianItemQuestion(
                7, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. win out "),
                    LianQuestionOption(2, "B. give up"),
                    LianQuestionOption(3, "C. wake up", true),
                    LianQuestionOption(4, "D. stand out")
                )
            ),
            LianItemQuestion(
                8, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. dream "),
                    LianQuestionOption(2, "B. lie"),
                    LianQuestionOption(3, "C. fantasy", true),
                    LianQuestionOption(4, "D. fact")
                )
            ),
            LianItemQuestion(
                9, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. parents "),
                    LianQuestionOption(2, "B. twins"),
                    LianQuestionOption(3, "C. colleagues", true),
                    LianQuestionOption(4, "D. teachers")
                )
            ),
            LianItemQuestion(
                10, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. restrictions "),
                    LianQuestionOption(2, "B. explanations"),
                    LianQuestionOption(3, "C. differences", true),
                    LianQuestionOption(4, "D. consequences")
                )
            ),
            LianItemQuestion(
                11, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. demand "),
                    LianQuestionOption(2, "B. fear"),
                    LianQuestionOption(3, "C. desire", true),
                    LianQuestionOption(4, "D. doubt")
                )
            ),
            LianItemQuestion(
                12, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. physical "),
                    LianQuestionOption(2, "B. biological"),
                    LianQuestionOption(3, "C. spiritual", true),
                    LianQuestionOption(4, "D. behavioral")
                )
            ),
            LianItemQuestion(
                13, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. traditional "),
                    LianQuestionOption(2, "B. important"),
                    LianQuestionOption(3, "C. double", true),
                    LianQuestionOption(4, "D. original")
                )
            ),
            LianItemQuestion(
                14, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. life "),
                    LianQuestionOption(2, "B. time"),
                    LianQuestionOption(3, "C. reply", true),
                    LianQuestionOption(4, "D. history")
                )
            ),
            LianItemQuestion(
                15, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. by comparison with "),
                    LianQuestionOption(2, "B. in addition to "),
                    LianQuestionOption(3, "C. regardless of ", true),
                    LianQuestionOption(4, "D. instead of")
                )
            ),
            LianItemQuestion(
                16, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. get hold of "),
                    LianQuestionOption(2, "B. stare at "),
                    LianQuestionOption(3, "C. knock on ", true),
                    LianQuestionOption(4, "D. make use of")
                )
            ),
            LianItemQuestion(
                17, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. real "),
                    LianQuestionOption(2, "B. typical"),
                    LianQuestionOption(3, "C. similar", true),
                    LianQuestionOption(4, "D. limited")
                )
            ),
            LianItemQuestion(
                18, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. safety rule "),
                    LianQuestionOption(2, "B. comfort zone "),
                    LianQuestionOption(3, "C. bottom line ", true),
                    LianQuestionOption(4, "D. top secret")
                )
            ),
            LianItemQuestion(
                19, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. delay "),
                    LianQuestionOption(2, "B. regret"),
                    LianQuestionOption(3, "C. enjoy", true),
                    LianQuestionOption(4, "D. accept")
                )
            ),
            LianItemQuestion(
                20, "单选", null, mutableListOf(
                    LianQuestionOption(1, "A. hurry "),
                    LianQuestionOption(2, "B. decide"),
                    LianQuestionOption(3, "C. hesitate", true),
                    LianQuestionOption(4, "D. intend")
                )
            ),


            )


        return LianItem(
            1, "阅读理解", requirement = requirement,
            itemMainText = itemMainText,
            questions = questions,
            reviews = reviews
        )
    }

    private fun prepareDateReading(): LianItem {
        val requirement = "阅读下列短文，从每题所给的A、B、C和D四个选项中，选出最佳选项。"
        val reviews = """
            【解析】【分析】本文是一篇议论文，作者认为把大学四年的学制缩短为三年不是一个好主意，保证足够的时间才能保证大学教育的质量。 
 （1）考查主旨大意。根据第二段中的“Few US universities have formally approved a ‘three-year degree’ model.”很少有美国大学正式批准“三年学位”模式；以及最后一段中的“In my opinion, a quality four-year education is always superior to a quality three-year education.”在我看来，优质的四年教育总是优于优质的三年教育。可推断，文章作者认为把大学四年的学制缩短为三年不是一个好主意，学好大学功课是需要付出时间的，所以这篇文章的中心思想是“最好的学习是随着时间的推移而进行的。”，故选B。 
 （2）考查推理判断。根据第二段中的“Few US universities have formally approved a ‘three-year degree’ model.”美国很少有大学正式批准“三年制学位”模式。由此推断出，大多数美国大学反对“三年制学位”模式，故选A。 
 （3）考查细节理解。根据第三段中的“For one thing, most universities already allow highly qualified students to graduate early by testing out of certain classes and obtaining a number of college credits.”一方面，大多数大学已经允许合格的学生通过某些课程的考试和获得一些大学学分提前毕业。可知，特别优秀的大学生可以提前毕业。故选D。 
 （4）考查推理判断。根据第四段中的“A college education requires sufficient time for a student to become skilled in their major and do coursework in fields outside their major.”大学教育要求学生有足够的时间掌握专业技能，并在专业以外的领域学习课程；以及“It is not a good idea to water down education, any more than it's not a good idea to water down medicine.”削弱教育不是个好主意，就像削弱医学不是个好主意一样。可知作者非常看重大学教育质量。故选C。 
 【点评】本题考点涉及细节理解，推理判断和主旨大意三个题型的考查，是一篇教育类阅读，考生需要准确捕捉细节信息，并根据上下文进行逻辑推理，概括归纳，从而选出正确答案。 
        """.trimIndent()
        val itemMainText = """
            Race walking shares many fitness benefits with running, research shows, while most likely contributing to fewer injuries. It does, however, have its own problem.

                        Race walkers are conditioned athletes. The longest track and field event at the Summer Olympics is the 50-kilometer race walk, which is about five miles longer than the marathon. But the sport’s rules require that a race walker’s knees stay straight through most of the leg swing and one foot remain in contact(接触) with the ground at all times. It’s this strange form that makes race walking such an attractive activity, however, says Jaclyn Norberg, an assistant professor of exercise science at Salem State University in Salem, Mass.

                        Like running, race walking is physically demanding, she says. According to most calculations, race walkers moving at a pace of six miles per hour would burn about 800 calories(卡路里) per hour, which is approximately twice as many as they would burn walking, although fewer than running, which would probably burn about 1,000 or more calories per hour.

                        However, race walking does not pound the body as much as running does, Dr. Norberg says. According to her research, runners hit the ground with as much as four times their body weight per step, while race walkers, who do not leave the ground, create only about 1.4 times their body weight with each step.

                        As a result, she says, some of the injuries associated with running, such as runner’s knee, are uncommon among race walkers. But the sport’s strange form does place considerable stress on the ankles and hips, so people with a history of such injuries might want to be cautious in adopting the sport. In fact, anyone wishing to try race walking should probably first consult a coach or experienced racer to learn proper technique, she says. It takes some practice.

        """.trimIndent()
        val questions = mutableListOf<LianItemQuestion>(
            LianItemQuestion(
                1, "单选", "Why are race walkers conditioned athletes?",
                optionLians = mutableListOf(
                    LianQuestionOption(1, "A. They must run long distances."),
                    LianQuestionOption(2, "B. They are qualified for the marathon."),
                    LianQuestionOption(3, "C. They have to follow special rules.", true),
                    LianQuestionOption(4, "D. They are good at swinging their legs.")
                )
            ),
            LianItemQuestion(
                2, "单选", "What advantage does race walking have over running?",
                optionLians = mutableListOf(
                    LianQuestionOption(1, "A. It’s more popular at the Olympics."),
                    LianQuestionOption(2, "B. It’s less challenging physically."),
                    LianQuestionOption(3, "C. It’s more effective in body building.", true),
                    LianQuestionOption(4, "D. It’s less likely to cause knee injuries.")
                )
            ),
            LianItemQuestion(
                3, "单选", "What is Dr. Norberg’s suggestion for someone trying race walking?",
                optionLians = mutableListOf(
                    LianQuestionOption(1, "A. Getting experts’ opinions."),
                    LianQuestionOption(2, "B. Having a medical checkup."),
                    LianQuestionOption(3, "C. Hiring an experienced coach.", true),
                    LianQuestionOption(4, "D. Doing regular exercises.")
                )
            ),
            LianItemQuestion(
                4, "单选", "Which word best describes the author’s attitude to race walking?",
                optionLians = mutableListOf(
                    LianQuestionOption(1, "A. Skeptical."),
                    LianQuestionOption(2, "B. Objective."),
                    LianQuestionOption(3, "C. Tolerant.", true),
                    LianQuestionOption(4, "D. Conservative.")
                )
            )
        )


        return LianItem(
            1, "阅读理解", requirement = requirement,
            itemMainText = itemMainText,
            questions = questions,
            reviews = reviews
        )
    }

}