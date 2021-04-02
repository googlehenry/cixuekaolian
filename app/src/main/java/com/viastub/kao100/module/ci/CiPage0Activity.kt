package com.viastub.kao100.module.ci

import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import kotlinx.android.synthetic.main.activity_ci_word_detail_page.*

class CiPage0Activity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_ci_word_detail_page
    }

    override fun afterCreated() {
        supportActionBar?.hide()

        val explanation = """
            <html>
             <head></head>
             <body>
              <font size="+1" color="purple">world</font>
              <font color="gold"> ★★★★★</font>
              <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
              <link href="collins.css" rel="stylesheet" type="text/css" />
              <div class="tab_content" id="dict_tab_101" style="display:block">
               <div class="part_main">
                <div class="collins_content">
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">1.</span>
                   <span class="st" tid="1_71601">N-SING 单数名词</span>
                   <span class="text_blue">世界;地球</span> 
                   <b>The world</b> is the planet that we live on. 
                   <span>
                    <div id="word_gram_1_71601">
                     <div>
                      <div>
                       <br />【搭配模式】：
                       <l>
                        the
                       </l> N
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>It's a beautiful part of the <span class="text_blue">world</span>...</p><p>这是世界上很美的一个地区。</p></li>
                   <li><p>More than anything, I'd like to drive around the <span class="text_blue">world</span>...</p><p>我最想做的事是开车周游世界。</p></li>
                   <li><p>The satellite enables us to calculate their precise location anywhere in the <span class="text_blue">world</span>.</p><p>卫星使我们能够计算出他们在世界上任何地方的精确位置。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">2.</span>
                   <span class="st" tid="2_71602">N-SING 单数名词</span>
                   <span class="text_blue">人类;社会;生活</span> 
                   <b>The world</b> refers to all the people who live on this planet, and our societies, institutions, and ways of life. 
                   <span>
                    <div id="word_gram_2_71602">
                     <div>
                      <div>
                       <br />【搭配模式】：
                       <l>
                        the
                       </l> N
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>The <span class="text_blue">world</span> was, and remains, shocked...</p><p>世人当时感到震惊，现在仍然如此。</p></li>
                   <li><p>He wants to show the <span class="text_blue">world</span> that anyone can learn to be an ambassador.</p><p>他想向世人表明任何人都能通过学习而成为一名大使。</p></li>
                   <li><p>...his personal contribution to <span class="text_blue">world</span> history.</p><p>他个人对世界历史的贡献</p></li>
                   <li class="sentence_2"><p>...inflationary pressures in the <span class="text_blue">world</span> economy.</p><p>世界经济的通货膨胀压力</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">3.</span>
                   <span class="st" tid="3_71603">ADJ 形容词</span>
                   <span class="text_blue">世界上最重要的;举世瞩目的</span> You can use 
                   <b>world</b> to describe someone or something that is one of the most important or significant of its kind on earth. 
                   <span>
                    <div id="word_gram_3_71603">
                     <div>
                      <div>
                       <br />【搭配模式】：ADJ n
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>Abroad, Mr Bush was seen as a <span class="text_blue">world</span> statesman...</p><p>在国外，布什先生被看作一位国际上举足轻重的政治家。</p></li>
                   <li><p>China has once again emerged as a <span class="text_blue">world</span> power...</p><p>中国又一次以世界强国的姿态崛起。</p></li>
                   <li><p>He was one of Newcastle's most distinguished medical men, a <span class="text_blue">world</span> authority on heart-diseases.</p><p>他是纽卡斯尔最优秀的医生之一，也是世界心脏病方面的权威。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">4.</span>
                   <span class="st" tid="4_71604">N-SING 单数名词</span>
                   <span class="text_blue">(指某一群国家或历史时期)世界，国家，时代</span> You can use 
                   <b>world</b> in expressions such as 
                   <b>the Arab world</b> ,
                   <b>the western world</b>, and 
                   <b>the ancient world</b> to refer to a particular group of countries or a particular period in history. 
                   <span>
                    <div id="word_gram_4_71604">
                     <div>
                      <div>
                       <br />【搭配模式】：
                       <l>
                        the
                       </l> supp N
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>Athens had strong ties to the Arab <span class="text_blue">world</span>.</p><p>雅典与阿拉伯世界关系密切。</p></li>
                   <li><p>...the developing <span class="text_blue">world</span>...</p><p>发展中国家</p></li>
                   <li><p>Dogs were also associated with healing in the ancient <span class="text_blue">world</span>.</p><p>在古代，狗也与治病有关。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">5.</span>
                   <span class="st" tid="5_71605">N-COUNT 可数名词</span>
                   <span class="text_blue">人生；生活圈子；阅历</span> Someone's 
                   <b>world</b> is the life they lead, the people they have contact with, and the things they experience. 
                   <span>
                    <div id="word_gram_5_71605">
                     <div>
                      <div>
                       <br />【搭配模式】：oft poss N
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>His <span class="text_blue">world</span> seemed so different from mine...</p><p>他的生活圈子和我的似乎有天壤之别。</p></li>
                   <li><p>I lost my job and it was like my <span class="text_blue">world</span> collapsed...</p><p>我丢了工作，我的世界好像都垮掉了。</p></li>
                   <li><p>I tried to understand the adult <span class="text_blue">world</span> and could not.</p><p>我试着去了解成人世界，却无法猜透。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">6.</span>
                   <span class="st" tid="6_71606">N-SING 单数名词</span>
                   <span class="text_blue">(人们活动的)领域，界，圈子</span> You can use 
                   <b>world</b> to refer to a particular field of activity, and the people involved in it. 
                   <span>
                    <div id="word_gram_6_71606">
                     <div>
                      <div>
                       <br />【搭配模式】：
                       <l>
                        the
                       </l> N
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>The publishing <span class="text_blue">world</span> had certainly never seen an event quite like this.</p><p>出版界以前确实没有遇见过类似这样的事。</p></li>
                   <li><p>...the latest news from the <span class="text_blue">world</span> of finance.</p><p>来自金融界的最新消息</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">7.</span>
                   <span class="st" tid="7_71607">N-COUNT 可数名词</span>
                   <span class="text_blue">（有某种特色的）地方，环境；（某种生活的）世界，境况</span> You can use 
                   <b>world</b> to refer to a place or way of life by describing its strongest features. 
                   <span>
                    <div id="word_gram_7_71607">
                     <div>
                      <div>
                       <br />【搭配模式】：with supp
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>...a golf course set in a hidden <span class="text_blue">world</span> of parkland, forest and lakes...</p><p>位于一处有公共绿地、森林和湖泊的隐秘之地的高尔夫球场</p></li>
                   <li><p>The patient must re-enter a <span class="text_blue">world</span> full of problems and stresses.</p><p>患者必须再次进入充满问题和压力的世界。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">8.</span>
                   <span class="st" tid="8_71608">N-SING 单数名词</span>
                   <span class="text_blue">今世/来世</span> You can use 
                   <b>world</b> in expressions such as 
                   <b>this world</b> ,
                   <b>the next world</b>, and 
                   <b>the world to come</b> to refer to the state of being alive or a state of existence after death. 
                   <span>
                    <div id="word_gram_8_71608">
                     <div>
                      <div>
                       <br />【搭配模式】：with supp
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>Good fortune will follow you, both in this <span class="text_blue">world</span> and the next.</p><p>无论今世还是来生，好运都会伴随你。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">9.</span>
                   <span class="st" tid="9_71609">N-SING 单数名词</span>
                   <span class="text_blue">（生物的）界</span> You can use 
                   <b>world</b> to refer to a particular group of living things, for example 
                   <b>the animal world</b> ,
                   <b>the plant world</b>, and 
                   <b>the insect world</b>. 
                   <span>
                    <div id="word_gram_9_71609">
                     <div>
                      <div>
                       <br />【搭配模式】：
                       <l>
                        the
                       </l> n N
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul></ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">10.</span>
                   <span class="st" tid="10_71610">N-COUNT 可数名词</span>
                   <span class="text_blue">星球;天体</span> A 
                   <b>world</b> is a planet. 
                  </div>
                  <ul>
                   <li><p>He looked like something from another <span class="text_blue">world</span>...</p><p>他看上去像是从另一个星球来的。</p></li>
                   <li><p>Man was drawing closer to the stars, opening new <span class="text_blue">world</span>s.</p><p>人类在接近各个星球，探索新的天体。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">11.</span>
                   <span class="st" style="font-weight:bold;">See also:</span>
                   <b class="text_blue"><a class="explain" href="entry://brave_new_world">brave new world</a></b>;
                   <b class="text_blue"><a class="explain" href="entry://New World">New World</a></b>;
                   <b class="text_blue"><a class="explain" href="entry://real world">real world</a></b>;
                   <b class="text_blue"><a class="explain" href="entry://Third World">Third World</a></b>;
                   <span class="text_blue"></span> 
                  </div>
                  <ul></ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">12.</span>
                   <span class="st" tid="12_71612">PHRASE 短语</span>
                   <span class="text_blue">大相径庭;迥然不同</span> If you say that two people or things are 
                   <b>worlds apart</b>, you are emphasizing that they are very different from each other. 
                   <span>
                    <div id="word_gram_12_71612">
                     <div>
                      <div>
                       <br />【搭配模式】：usu v-link PHR
                      </div>
                      <div>
                       <br />【语用信息】：emphasis
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>Intellectually, this man and I are <span class="text_blue">world</span>s apart...</p><p>在智力上，我和这个男子有天壤之别。</p></li>
                   <li><p>The novel is <span class="text_blue">world</span>s apart from his academic writings.</p><p>这本小说和他的学术著作截然不同。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">13.</span>
                   <span class="st" tid="13_71613">PHRASE 短语</span>
                   <span class="text_blue">两头受益;两全其美</span> If you say that someone has 
                   <b>the best of both worlds</b>, you mean that they have only the benefits of two things and none of the disadvantages. 
                   <span>
                    <div id="word_gram_13_71613">
                     <div>
                      <div>
                       <br />【搭配模式】：PHR after v
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>Her living room provides the best of both <span class="text_blue">world</span>s, with an office at one end and comfortable sofas at the other.</p><p>她家客厅一头是办公处，一头是舒适的沙发，真是两全其美。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">14.</span>
                   <span class="st" tid="14_71614">PHRASE 短语</span>
                   <span class="text_blue">生（孩子）</span> If a woman 
                   <b>brings</b> a child 
                   <b>into the world</b>, she gives birth to it. 
                   <span>
                    <div id="word_gram_14_71614">
                     <div>
                      <div>
                       <br />【搭配模式】：V inflects
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>I never felt I achieved a great deal in my life, apart from bringing my children into the <span class="text_blue">world</span>.</p><p>除了生了几个孩子，我从未觉得自己这辈子取得了很大成就。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">15.</span>
                   <span class="st" tid="15_71615">PHRASE 短语</span>
                   <span class="text_blue">(表示强调)天壤之别</span> If you say that there is 
                   <b>a world of difference</b> between one thing and another, you are emphasizing that they are very different from each other. 
                   <span>
                    <div id="word_gram_15_71615">
                     <div>
                      <div>
                       <br />【搭配模式】：v-link PHR
                      </div>
                      <div>
                       <br />【语用信息】：emphasis
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>There's a <span class="text_blue">world</span> of difference between an amateur video and a slick Hollywood production.</p><p>业余制作的录像短片与好莱坞制作精湛的电影作品有天壤之别。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">16.</span>
                   <span class="st" tid="16_71616">PHRASE 短语</span>
                   <span class="text_blue">决不；无论如何也不</span> If you say that you would not do something 
                   <b>for the world</b>, you are emphasizing that you definitely would not do it. 
                   <span>
                    <div id="word_gram_16_71616">
                     <div>
                      <div>
                       <br />【搭配模式】：with brd-neg
                      </div>
                      <div>
                       <br />【语用信息】：emphasis
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>I wouldn't have missed this for the <span class="text_blue">world</span>.</p><p>我决不会错过这个。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">17.</span>
                   <span class="st" tid="17_71617">PHRASE 短语</span>
                   <span class="text_blue">让…感觉好很多；对…大有好处</span> If you say that something 
                   <b>has done</b> someone 
                   <b>the world of good</b> or 
                   <b>a world of good</b>, you mean that it has made them feel better or improved their life. 
                   <span>
                    <div id="word_gram_17_71617">
                     <div>
                      <div>
                       <br />【搭配模式】：V inflects
                      </div>
                      <div>
                       <br />【语域标签】：INFORMAL 非正式
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>A sleep will do you the <span class="text_blue">world</span> of good.</p><p>睡一觉你就会感觉舒服多了。</p></li>
                   <li><p>...a mature performance which must have done his career prospects a <span class="text_blue">world</span> of good.</p><p>肯定为他的事业打开了一片坦途的成熟表演</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">18.</span>
                   <span class="st" tid="18_71618">PHRASE 短语</span>
                   <span class="text_blue">(用于加强语气)</span> You use 
                   <b>in the world</b> to emphasize a statement that you are making. 
                   <span>
                    <div id="word_gram_18_71618">
                     <div>
                      <div>
                       <br />【搭配模式】：oft PHR after superl
                      </div>
                      <div>
                       <br />【语用信息】：emphasis
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>The saddest thing in the <span class="text_blue">world</span> is a little baby nobody wants...</p><p>世上最让人难受的莫过于小婴儿遭到遗弃。</p></li>
                   <li><p>He had no one in the <span class="text_blue">world</span> but her.</p><p>除了她，他再也没有任何亲人。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">19.</span>
                   <span class="st" tid="19_71619">PHRASE 短语</span>
                   <span class="text_blue">(尤用于强调惊讶或愤怒)究竟，到底</span> You can use 
                   <b>in the world</b> in expressions such as 
                   <b>what in the world</b> and 
                   <b>who in the world</b> to emphasize a question, especially when expressing surprise or anger. 
                   <span>
                    <div id="word_gram_19_71619">
                     <div>
                      <div>
                       <br />【搭配模式】：quest PHR
                      </div>
                      <div>
                       <br />【语用信息】：emphasis
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>What in the <span class="text_blue">world</span> is he doing?...</p><p>他究竟在干什么？</p></li>
                   <li><p>Where in the <span class="text_blue">world</span> were you when I was struggling for my life?</p><p>我挣扎求生的时候，你究竟在哪儿？</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">20.</span>
                   <span class="st" tid="20_71620">PHRASE 短语</span>
                   <span class="text_blue">在理想（或完美）的世界中；在理想状况下</span> You can use 
                   <b>in an ideal world</b> or 
                   <b>in a perfect world</b> when you are talking about things that you would like to happen, although you realize that they are not likely to happen. 
                   <span>
                    <div id="word_gram_20_71620">
                     <div>
                      <div>
                       <br />【搭配模式】：PHR with cl
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>In an ideal <span class="text_blue">world</span> Karen Stevens says she would love to stay at home with her two-and-half-year old son...</p><p>卡伦&middot;史蒂文斯说在理想的生活中，她非常愿意呆在家里照料两岁半的儿子。</p></li>
                   <li><p>In a perfect <span class="text_blue">world</span>, there would be the facilities and money to treat every sick person.</p><p>理想的状况是有足够的设备和财力治疗每一位病人。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">21.</span>
                   <span class="st" tid="21_71621">PHRASE 短语</span>
                   <span class="text_blue">认为（自己）生来就该享福</span> If you say that someone thinks that 
                   <b>the world owes</b> them 
                   <b>a living</b>, you are criticizing them because they think it is their right to have a comfortable life without having to make any effort at all. 
                   <span>
                    <div id="word_gram_21_71621">
                     <div>
                      <div>
                       <br />【搭配模式】：V inflects
                      </div>
                      <div>
                       <br />【语用信息】：disapproval
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>All young people must face up to reality and not kid themselves that the <span class="text_blue">world</span> owes them a living.</p><p>所有的年轻人都必须接受现实，不要自欺欺人，觉得生来就该享清福。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">22.</span>
                   <span class="st" tid="22_71622">PHRASE 短语</span>
                   <span class="text_blue">阅历丰富的人;见过世面的人；老成稳重的人</span> If you say that someone is 
                   <b>a man of the world</b> or 
                   <b>a woman of the world</b>, you mean that they are experienced and know about the practical or social aspects of life, and are not easily shocked by immoral or dishonest actions. 
                   <span>
                    <div id="word_gram_22_71622">
                     <div>
                      <div>
                       <br />【搭配模式】：
                       <l>
                        man/woman
                       </l> inflects
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>Look, we are both men of the <span class="text_blue">world</span>, would anyone really mind?</p><p>听着，我们都是见过世面的人，真有人会介意吗？</p></li>
                   <li><p>...an elegant, clever and tough woman of the <span class="text_blue">world</span>.</p><p>优雅、聪明、顽强并且阅历丰富的女人</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">23.</span>
                   <span class="st" tid="23_71623">PHRASE 短语</span>
                   <span class="text_blue">极好的;一流的；非常棒的</span> If you say that something is 
                   <b>out of this world</b>, you are emphasizing that it is extremely good or impressive. 
                   <span>
                    <div id="word_gram_23_71623">
                     <div>
                      <div>
                       <br />【搭配模式】：v-link PHR
                      </div>
                      <div>
                       <br />【语用信息】：emphasis
                      </div>
                      <div>
                       <br />【语域标签】：INFORMAL 非正式
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>These new trains are out of this <span class="text_blue">world</span>.</p><p>这些新火车太棒了。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">24.</span>
                   <span class="st" tid="24_71624">PHRASE 短语</span>
                   <span class="text_blue">外面的世界;外界</span> You can use 
                   <b>the outside world</b> to refer to all the people who do not live in a particular place or who are not involved in a particular situation. 
                  </div>
                  <ul>
                   <li><p>For many, the post office is the only link with the outside <span class="text_blue">world</span>...</p><p>对很多人来说，这个邮局是与外界联系的唯一纽带。</p></li>
                   <li><p>This, at least, was the situation as it appeared to the outside <span class="text_blue">world</span>.</p><p>至少在外界看来情况是这样的。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">25.</span>
                   <span class="st" tid="25_71625">PHRASE 短语</span>
                   <span class="text_blue">全世界;世界各地</span> If you say that something happens or exists 
                   <b>the world over</b>, you mean that it happens or exists in every part of the world. 
                   <span>
                    <div id="word_gram_25_71625">
                     <div>
                      <div>
                       <br />【搭配模式】：PHR after v
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>Some problems are the same the <span class="text_blue">world</span> over...</p><p>有些问题在全世界都一样。</p></li>
                   <li><p>Governments the <span class="text_blue">world</span> over should do something about it.</p><p>世界各国政府应该对此采取行动。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">26.</span>
                   <span class="st" tid="26_71626">PHRASE 短语</span>
                   <span class="text_blue">在自己的世界里;在自己的小天地里</span> If you say that someone is 
                   <b>in a world of</b> their 
                   <b>own</b>, you mean that they seem not to notice other people or the things going on around them. 
                   <span>
                    <div id="word_gram_26_71626">
                     <div>
                      <div>
                       <br />【搭配模式】：v-link PHR
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>When I'm swimming I'm in a <span class="text_blue">world</span> of my own...</p><p>游泳的时候，我沉浸在自己的世界里。</p></li>
                   <li><p>Sarah was nine years old and until that moment she had been locked in a <span class="text_blue">world</span> of her own.</p><p>萨拉当时9岁，之前她一直封闭在自己的世界里。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">27.</span>
                   <span class="st" tid="27_71627">PHRASE 短语</span>
                   <span class="text_blue">非常喜欢；非常在乎</span> If you 
                   <b>think the world of</b> someone, you like them or care about them very much. 
                   <span>
                    <div id="word_gram_27_71627">
                     <div>
                      <div>
                       <br />【搭配模式】：V inflects
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>I think the <span class="text_blue">world</span> of him, but something tells me it's not love...</p><p>我很在乎他，但是某种感觉告诉我这不是爱。</p></li>
                   <li><p>We were really close. We thought the <span class="text_blue">world</span> of each other.</p><p>我们的确很亲近，非常在乎对方。</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">28.</span>
                   <span class="st" tid="28_71628">PHRASE 短语</span>
                   <span class="text_blue">发迹/落魄;飞黄腾达/潦倒</span> If you say that someone 
                   <b>has gone up in the world</b>, you mean they have become richer or have a higher social status than before. If you say they 
                   <b>have come down in the world</b>, you mean they have become poorer or have a lower social status. 
                   <span>
                    <div id="word_gram_28_71628">
                     <div>
                      <div>
                       <br />【搭配模式】：V inflects
                      </div>
                      <div>
                       <br />【语域标签】：mainly BRIT 主英
                      </div>
                     </div>
                    </div></span>
                  </div>
                  <ul>
                   <li><p>When they started to go up in the <span class="text_blue">world</span>, they moved to a flat in London.</p><p>他们开始发家的时候，搬进了伦敦的一所公寓。</p></li>
                   <li><p>...young women of middle class families which had come down in the <span class="text_blue">world</span>.</p><p>出身于落魄的中产阶级家庭的年轻女子</p></li>
                  </ul>
                 </div>
                 <div class="collins_en_cn">
                  <div class="caption">
                   <span class="num">29.</span>
                   <span class="text_blue"></span> 
                   <b>not be the end of the world</b>
                   <span class="text_gray" style="font-weight:bold;">→see: </span>
                   <b class="text_blue"><a class="explain" href="entry://end">end</a></b>; the world is your oyster
                   <span class="text_gray" style="font-weight:bold;">→see: </span>
                   <b class="text_blue"><a class="explain" href="entry://oyster">oyster</a></b>; on top of the world
                   <span class="text_gray" style="font-weight:bold;">→see: </span>
                   <b class="text_blue"><a class="explain" href="entry://top">top</a></b>; 
                  </div>
                  <ul></ul>
                 </div>
                </div>
               </div>
              </div>
             </body>
            </html>
        """
        ci_word_detail.loadDataWithBaseURL(
            "file:///android_asset/www/",
            explanation,
            "text/html",
            "utf-8",
            null
        );

        header_back.setOnClickListener { onBackPressed() }
    }

}