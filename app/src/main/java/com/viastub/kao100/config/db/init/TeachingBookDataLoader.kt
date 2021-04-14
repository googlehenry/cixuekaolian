package com.viastub.kao100.config.db.init

import com.viastub.kao100.R
import com.viastub.kao100.db.*
import com.viastub.kao100.utils.TempUtil


class TeachingBookDataLoader : DataLoader {
    override fun load(roomDb: RoomDB): Int {
        loadDictionary(roomDb)
        loadTeachingBook1(roomDb)
        return -1
    }

    private fun loadDictionary(roomDb: RoomDB) {
        var dict = DictionaryConfig(
            id = 1, title = "牛津英汉双解词典", dictFilePath = TempUtil.loadRawFile(
                R.raw.dict_oxford_en_zh_biolingol, "dict_oxford_en_zh_biolingol.mdx"
            )
        )

        roomDb.dictionaryConfig().insert(dict)
    }

    private fun loadTeachingBook1(roomDb: RoomDB) {
        var tb = TeachingBook(
            name = "人教版普通高中课程标准试验教科书(英语1)必修",
            grade = 10,//高一
            phase = TeachingBookPhase.HIGH_SCHOOL.name,
            bookCoverImagePath = TempUtil.loadRawFile(
                R.raw.demo_high_school_textbook_required_1,
                "demo_high_school_textbook_required_1"
            )
        ).bindId(1).bindUnits(
            mutableListOf(
                TeachingBookUnitSection(
                    name = "第一单元",
                    description = "Unit 1: Friendship",
                    unitCoverImagePath = TempUtil.loadRawFile(R.raw.tb_007, "tb_007")
                ).bindId(1)
                    .bindTeachingPoints(TempUtil.loadTeachingPoints(R.raw.tb_grade11_01_unit1_teachingpoints))
                    .bindTranslations(
                        mutableListOf(
                            TeachingTranslation(
                                id = 1,
                                name = "page1",
                                text_eng = """
                            Unit 1 Friendship

                            Warming Up

                            Are you good to your friends ?Do the following survey .Add up your score and see how many points you get .
                             
                            1. You want to see a very interesting film with your friend ,but your friend can't go until he /she finishes cleaning his /her bicycle .You will
                            A go without your friend .
                            B help your friend clean the bicycle so you can leave early .
                            C plan to go another time .

                            2. Your friend asks to borrow your favourite camera .When he /she borrowed it last time ,he /she broke it and you had to pay to get repaired .You will
                            A say no .
                            B let your friend borrow it without saying anything
                            C let your friend borrow it ,but tell him /her that if the camera is broken again ,he /she will have to pay to get it repaired .

                            3. Your friend comes to school very upset .The bell rings so you need to go to class .You will
                            A Ignore the bell and go somewhere quiet to calm your friend down .
                            B tell your friend that you've got to go to class .
                            C tell your friend that you are concerned about him /her and you will meet after class and talk then .

                            4. Your friend has gone on holiday and asked you to take care of his /her dog .While walking the dog ,you were careless and it got loose and was hit by a car .The dog's leg was broken .You will
                            A take the dog to the vet and pay the bill yourself .
                            B ask your parents to take the dog to the vet and pay for it . 
                            C take the dog to the vet but give the bill to your friend to pay .

                            5. You are taking your end-of-term exam .Your friend ,who doesn't work hard ,asks you to help him /her cheat in the exam by looking at your paper .You will
                            A let him /herlook at your paper .
                            B tell him /her that he /she should have studied ,so you don't let him /her look at your paper .
                            C tell him /her to look at someone else's paper .

                            Work out your score on page 8 .

                            page 1
                        """.trimIndent(),
                                text_zh = """
                            第一单元友谊

                            热身

                            你对你的朋友好吗？做下面的调查。把你的分数加起来，看看你得了多少分。


                            1你想和你的朋友一起看一部很有趣的电影，但是你的朋友要等他/她把自行车擦干净才能去，你会去的

                            A不带你朋友去。

                            B帮你朋友打扫自行车，这样你就可以早点离开了。

                            C我打算改天去。


                            2你的朋友向你借你最喜欢的相机。上次他/她借的时候，他/她把相机弄坏了，你得付钱去修理。你会的

                            A说不。

                            B什么也不说就让你朋友借了

                            C让你的朋友借，但告诉他/她，如果相机又坏了，他/她必须付钱修理。


                            三。你的朋友来学校很不高兴。铃响了，所以你需要去上课。你会的

                            A别理铃声，去安静的地方让你的朋友安静下来。

                            B告诉你的朋友你得去上课了。

                            C告诉你的朋友你很关心他/她，然后你会在课后见面并交谈。


                            4你的朋友去度假了，让你照顾他/她的狗。你在遛狗的时候，不小心把狗弄松了，被车撞了。狗的腿断了。你会的

                            A带狗去看兽医，自己付帐。

                            B让你的父母带狗去看兽医并付钱。

                            C带狗去看兽医，但把帐单给你的朋友付。


                            5你正在参加期末考试。你的朋友不用功，他让你帮他/她看你的试卷作弊。你会的

                            A让他/她看看你的论文。

                            B告诉他/她应该学习，这样你就不会让他/她看你的论文了。

                            C告诉他/她去看别人的报纸。

                            在第8页算出你的分数。
                            第1页
                        """.trimIndent()
                            ),
                            TeachingTranslation(
                                id = 2,
                                text_eng = """
                                    Unit 1 Friendship  

                                    Pre-reading  

                                    1. Why do you need friends ?Make list of reasons why friends are important to you . 
                                    2. Does a friend always have to be a person ?What else can be your friend?
                                    3. Skim the first paragraph of the reading passage below and find who was Anne's best friend . 

                                    Reading  

                                    ANNE'S BEST FRIEND  

                                    Do you want a friend whom you could tell everything to ,like your deepest feelings and thoughts ? Or are you afraid that your friend would laugh at you ,or would not understand what you are  going through ?Anne Frank wanted the first kind ,so she made her diary her best friend.

                                    Anne lived in Amsterdam in the Netherlands during World War .Her family was Jewish so  they had to hide or they would be caught by the German Nazis .She and her family hid away for  nearly twenty-five months before they were discovered . During that time the only true friend was  her diary .She said ,"I don't want to set down a series of  facts in a diary as  most people do ,but I want this diary itself to be my friend . and I shall call my  friend Kitty." Now read how she felt  after being in the  hiding place since  July 1942.


                                    Thursday 15th June ,1944    

                                    Dear Kitty,

                                    I wonder if it's because haven't been able to be outdoors for so long 
                                    that I've grown so crazy about everything to do with nature .I can well  
                                    remember that there was a time when a deep blue sky ,the song of the birds , 
                                    moonlight and flowers could never have kept me spellbound .That's changed 
                                    since I came here .

                                    For example ,one evening when it was so warm ,I stayed awake on purpose 
                                    until half past eleven in order to have a good look at the moon by myself .But 
                                    as the moon gave far too much light ,I didn't dare open a window .Another 
                                    time five months ago ,I happened to be upstairs at dusk when the window was 
                                    open .I didn't go downstairs until the window had to be shut .The dark ,rainy  
                                    evening, the wind ,the thundering clouds held me entirely in their power ;it 
                                    was the first time in a year and a half that I'd seen the night face to face . .. 

                                    ... Sadly .. I am only able to look at nature through dirty curtains hanging  
                                    before very dusty windows .It's pleasure looking through these any longer  
                                    because nature is one thing that really must be experienced . 

                                    Yours . 
                                    Anne

                                    page 2
                                """.trimIndent(),
                                text_zh = """
                                    第一单元友谊



                                    预读



                                    1你为什么需要朋友？列出为什么朋友对你很重要的原因。

                                    2一个朋友必须是一个人吗？还有什么能成为你的朋友？

                                    三。浏览下面这篇文章的第一段，找出谁是安妮最好的朋友。



                                    阅读



                                    安妮最好的朋友



                                    你想要一个能告诉你一切的朋友，比如你最深的感情和想法吗？或者你害怕你的朋友嘲笑你，或者不明白你正在经历什么？安妮·弗兰克想要第一种，所以她把日记当成了最好的朋友。



                                    安妮在第二次世界大战期间住在荷兰的阿姆斯特丹。她的家人是犹太人，所以他们不得不躲起来，否则他们会被德国纳粹抓获。她和她的家人在被发现之前躲了将近25个月。在那段时间里，唯一真正的朋友是她的日记，她说：“我不想像大多数人那样把一系列事实写在日记里，但我希望这本日记本身就是我的朋友。我要给我的朋友基蒂打电话。”现在读一下她自1942年7月以来在藏身处的感受。




                                    1944年6月15日星期四



                                    亲爱的基蒂，



                                    我不知道是不是因为我很久没能在户外活动了

                                    我对一切与大自然有关的事都变得如此痴迷。我能很好地理解

                                    记得有一段时间，深蓝的天空，鸟儿的歌声，

                                    月光和鲜花永远不会让我着迷。这已经改变了

                                    自从我来这里。



                                    例如，有一天晚上天气很暖和，我故意保持清醒

                                    一直到十一点半，为了一个人好好看看月亮。但是

                                    因为月光太亮了，我不敢打开窗户。又在

                                    五个月前的一次，黄昏时分，我碰巧在楼上，窗户关上了

                                    门开着。直到窗户关上我才下楼。天黑，下着雨

                                    傍晚，风，雷鸣的云彩把我完全控制在它们的力量里；它

                                    是一年半以来我第一次面对面地看到夜晚。。



                                    ... 悲哀地。。我只能透过挂着的脏窗帘看大自然

                                    在满是灰尘的窗户前。我很高兴再透过这些窗户看下去

                                    因为自然是一件必须经历的事情。



                                    你的。

                                    安妮

                                    第2页
                                """.trimIndent()
                            ),
                            TeachingTranslation(
                                id = 3,
                                text_eng = """
                                    Unit 1 Friendship  

                                    Comprehending  

                                    1. Read the passage and join the correct parts of the sentences . 

                                    	1 Anne kept a diary because  
                                    	A she couldn't meet her friends . 
                                    	2 She felt very lonely because  
                                    	B Jews were caught by Nazis and killed  
                                    	3 They had hide because  
                                    	C she could tell everything to it . 
                                    	4 Anne named her diary Kitty because  
                                    	D she wanted it to be her best friend  

                                    2. Read the passage again and answer the following questions . 

                                    	1. About how long had Anne and her family been in the hiding place when she wrote this part of  her diary ? 

                                    	2. How did Anne feel about nature before she and her family hid away ? 

                                    	3. Why do you think her feelings changed towards nature ? 

                                    	4 Why did Anne no longer just like looking at nature out of the window ? 

                                    3. How would you describe Anne's feelings as she was looking out at the night sky ? 

                                    	1. With a partner brainstorm some adjectives to describe her feelings .Make a list of at least five . 
                                    	
                                    	2. Share your list with another pair .Choose five good adjectives from the two lists . 

                                    4. Imagine you have to go into hiding like Anne and her family .What would you miss  most ?Give your reasons . 

                                    	Things I would miss  -- Reasons 

                                    page 3
                                """.trimIndent(),
                                text_zh = """
                                    第一单元友谊

                                    理解


                                    1读短文，把句子的正确部分连起来。

                                    	1安妮写日记是因为 -- A她不能见她的朋友。

                                    	2她感到很孤独因为 -- B犹太人被纳粹抓获并杀害

                                    	3他们躲起来是因为 -- C她可以把一切都告诉它。

                                    	4安妮给她的日记起名叫凯蒂，因为 -- D她希望这是她最好的朋友



                                    2重读课文，回答下列问题。

                                    	1当安妮写日记的这一部分时，她和她的家人在藏身处呆了多久？

                                    	2在安妮和她的家人躲藏起来之前，她对大自然的感觉如何？

                                    	3为什么你认为她对大自然的感情发生了变化？

                                    	4为什么安妮不再只喜欢看着窗外的大自然？



                                    3当安妮望着夜空时，你会如何描述她的感受？

                                    	1和一个同伴一起头脑风暴一些形容词来描述她的感受。列出至少五个。

                                    	2与另一对分享你的清单。从两个清单中选择五个好的形容词。

                                    4想象一下，你不得不像安妮和她的家人一样躲藏起来。你最想念什么？说出你的理由。

                                    	我会错过的事情——原因

                                    第3页
                                """.trimIndent()
                            ),
                            TeachingTranslation(
                                id = 4,
                                text_eng = """
                                    Unit 1 Friendship  
                                    Learning about Language  

                                    Discovering useful words and expressions  

                                    1.Find the word or expression for each of the following meanings from the text . 
                                    	1 ____ not inside building  
                                    	2 ____ feeling disturbed  
                                    	3 ____ to be worried about  
                                    	4 ____ free ,not tied up  
                                    	5 ____ experience something  
                                    	6 ____ to take no notice of  
                                    	7 ____ staying close to and looking at somebody  
                                    	8 ____ to become quiet after nervous activity  
                                    	9 ____ piece of material hung to cover a window  
                                    	10____ number things that happen one after another  

                                    2.Complete this passage with some of the words and phrases above . 

                                    	Anne's sister Margot was very ____ that the family had to move .However ,she knew that  she had got to ____ all the difficulties with her family .She found it difficult to settle and  ____ in the hiding place ,because she was ____ whether they would be discovered . She suffered from loneliness ,but she had to learn to like it there .What she really missed was  going ____ and walking the dog for her neighbour .It was such fun to watch it run ____ the park .She wished she could tell her neighbour ____ that she was sorry  not to be able to do it any longer ,but she knew that was too dangerous ! 

                                    3.Complete the following sentences using words and expressions from the text . 
                                    	1.When the man saw the car accident on the highway ,he stopped ____ offer help . 
                                    	2."How can Linda recover from her illness in this room when it's so dirty and ____ It  will only make her worse ,"said the doctor . 
                                    	3.After Peter died ,George ____ the story of their friendship in book  
                                    	4.When the street lights go on ____ they make a beautiful picture ,so different from the  daytime . 
                                    	5.Good friends do not ____ what they do for each other ;instead they offer help when it  is needed . 
                                    	6.Although Tim and Mike come from ____ different backgrounds ,they became close  friends . 
                                    	7.Please draw the ____; the sunlight is too bright . 
                                    	8.Sorry.I didn't break the plate ____ "
                                    	  It's OK .Don't worry about it .

                                    page 4
                                """.trimIndent(),
                                text_zh = """
                                    第一单元友谊

                                    学习语言

                                    发现有用的词语


                                    1.从课文中找出下列每种含义的单词或表达。

                                    	1.不在建筑物内

                                    	2.感到不安

                                    	3.要担心

                                    	4.个自由，不捆绑

                                    	5.体验

                                    	6.不理会

                                    	7.靠近某人并看着某人

                                    	8.在紧张活动后变得安静

                                    	9.挂在窗户上的一块材料

                                    	10.把一件又一件发生的事情数一数



                                    用上面的一些单词和短语完成这篇文章。


                                    	安妮的妹妹玛格特非常担心全家要搬家，但是她知道她必须面对家里所有的困难，她发现很难解决，而且很难躲在隐蔽的地方，因为她担心他们是否会被发现。她饱受孤独之苦，但她必须学会喜欢那里。她真正怀念的是去为邻居遛狗。看着它在公园里奔跑真是太有趣了。她希望能告诉邻居她很抱歉不能再这样做了，但她知道那太危险了！



                                    3.用课文中的单词和短语完成下列句子。

                                    	1.当那人在高速公路上看到车祸时，他停下来提供帮助。

                                    	2.医生说：“在这个房间里，琳达的病又脏又重，只会让她更糟，她怎么能康复呢？”。

                                    	3.彼得死后，乔治在书中讲述了他们的友谊故事

                                    	4.当路灯亮起的时候，会形成一幅美丽的图画，与白天截然不同。

                                    	5.好朋友不会为彼此做什么，而是在需要时提供帮助。

                                    	6.尽管蒂姆和迈克来自不同的背景，但他们成了亲密的朋友。

                                    	7.请画这幅画，因为阳光太亮了。

                                    	8.对不起，我没打碎盘子

                                    	  没事。别担心。

                                    第4页
                                """.trimIndent()
                            ),
                            TeachingTranslation(
                                id = 5,
                                text_eng = """
                                    Unit 1 Friendship  

                                    Discovering useful structures  

                                    1.Look at these sentences .Can you find the difference between direct speech and  indirect speech ? 

                                    	" Idon't want to set down a series facts in a diary ,"said Anne .( Directspeech ) 
                                    	Anne said that she didn't want to set down a series of facts in diary .( Indirectspeech ) 
                                    	Does a friend always have to be a person ?" thewriter asks us . 
                                    	The writer asks us if a friend always has to be person . 

                                    	Anne's sister asked her what she called her diary .( Indirectspeech ) 
                                    	" Whatdo you call your diary ?"Anne's sister asked her .( Directspeech ) 
                                    	Father asked Anne why she had gone to bed so late the night before  
                                    	Why did you go to bed so late last night ?" Fatherasked Anne . 

                                    2.Change the first four sentences from direct speech into indirect speech and the  rest from indirect speech into direct speech . 
                                    	1.I don't know the address of my new home ,said Anne . 
                                    	2.I've got tired of looking at nature through dirty curtains and dusty windows ,Anne said to  her father . 
                                    	3.I need to pack up my things in the suitcase very quickly ,"thegirl said . 
                                    	4." Whydid you choose your diary and old letters ?"herfather asked her . 
                                    	5.Mother asked her if /whethershe was very hot with so many clothes on  
                                    	6.Margot asked her what else she had hidden under her overcoat . 
                                    	7.Anne asked her father when they would go back home . 
                                    	8.Father asked Anne why she had talked so much to that boy . 

                                    3.Pair work .One you will be a child and the other the grandmother .The grandmother  is listening to a weather report with her grandchild .Try to use indirect speech in  your dialogue.

                                    Beijing  rain  16℃~24℃ 
                                    Shanghai  cloudy  23℃~28℃ 
                                    Guangzhou  sunny  26℃~33℃ 
                                    Chongqing  foggy  22℃-30℃ 
                                    Changchun  overcast  12℃~22℃ 

                                    GM :What's the weather in Beijing tomorrow ?can't  hear the man clearly on the TV  
                                    GC :That's all right ,I can help .The man said ...
                                    GM :What did he say about Shanghai ? 
                                    GC :...

                                    page 5
                                """.trimIndent(),
                                text_zh = """
                                    第一单元友谊

                                    发现有用的结构

                                    1.看这些句子。你能找出直接引语和间接引语的区别吗？


                                    	“我不想把一系列的事实写在日记里，”安妮说
                                    	
                                    	安妮说她不想在日记里记下一系列事实
                                    	
                                    	一个朋友总要成为一个人吗？”作者问我们。
                                    	
                                    	作者问我们，朋友是否总是要成为一个人。


                                    	安妮的姐姐问她什么叫日记
                                    	
                                    	“你的日记叫什么？”安妮的姐姐问她
                                    	
                                    	父亲问安妮前晚为什么睡得这么晚
                                    	
                                    	你昨晚为什么睡得这么晚？”父亲问安妮。


                                    2.将前四句由直接引语改为间接引语，其余由间接引语改为直接引语。

                                    	1.安妮说：我不知道我新家的地址。

                                    	2.“我已经厌倦了透过脏兮兮的窗帘和满是灰尘的窗户看大自然，”安妮对她父亲说。

                                    	3.我需要很快地把我的东西装进手提箱。

                                    	4.“你为什么选择日记和旧信？”她父亲问她。

                                    	5.母亲问她穿这么多衣服是否很烫

                                    	6.玛戈特问她大衣下面还藏了什么。

                                    	7.安妮问她父亲他们什么时候回家。

                                    	8.父亲问安妮为什么和那个男孩说那么多。


                                    3.结对练习。一个你是个孩子，另一个是祖母。祖母和她的孙子正在听天气预报。在对话中尽量使用间接语。


                                    北京--雨16℃~24℃

                                    上海--多云23℃~28℃

                                    广州--晴26℃~33℃

                                    重庆--大雾22℃-30℃

                                    长春--阴12℃~22℃


                                    GM：明天北京的天气怎么样？在电视上听不清那个人的声音

                                    GC:没关系，我可以帮你。那个人说。。。

                                    GM：他说上海怎么样？

                                    GC:。。。

                                    第5页
                                """.trimIndent()
                            ),
                            TeachingTranslation(
                                id = 6,
                                text_eng = """
                                    Unit 1 Friendship  
                                    Using Language  
                                    Reading and listening  

                                    1.Read the letter that Lisa wrote to Miss Wang of Radio for Teenagers and predict  what Miss Wang will say .After listening ,check and discuss her advice . 

                                    	Dear Miss Wang  

                                    	I am having some trouble with my classmates at the moment .I'm getting along well with  boy in my class .We often do homework together and we enjoy helping each other .We have  become really good friends .But other students have started gossiping .They say that this  boy and I have fallen in love .This has made me angry .I don't want to end the friendship ,but  I hate others gossiping .What should do ? 
                                    	Yours , 
                                    	Lisa  

                                    2.Listen to the tape and try to spell the words as you hear their pronunciation .Then  divide each of the sentences into several sense groups . 

                                    	1 There is nothing wrong with you and this boy ____ friends and ____ together . 
                                    	2 ____ your friendship with this boy would be a ____ thing to do . 
                                    	3 Teenagers like to ____ and they often see something that isn't real . 
                                    	4 My advice is to ____ your classmates .That way you will ____ them that you are more ____ than they are . 


                                    3 Listen to the tape again and use the exercise above to help you answer the  following questions . 

                                    	1 What does Miss Wang say about their friendship ? 
                                    	  She says that  _______

                                    	2 Why doesn't she think that Lisa should end their friendship ? 
                                    	  She thinks that  _______

                                    	3 How does she explain why Lisa's classmates gossip about their friendship ? 
                                    	  She says that  _______

                                    	4 What is Miss Wang's advice ? 
                                    	  She asks Lisa to  _______

                                    Speaking  
                                    Do you agree with Miss Wang's advice ?Discuss it in small groups .You may use the  following expressions in your conversation . 

                                    AGREEING  
                                    	I agree .
                                    	Yes ,I think so .
                                    	So do I.
                                    	Me too . 
                                    	Exactly .
                                    	No problem .
                                    	Sure .
                                    	Certainly  
                                    	Of course .
                                    	All right .
                                    	You're right /correct. 
                                    	Good idea .
                                    	I think that's a good idea . 


                                    DISAGREEING
                                    	don't think so .
                                    	Neither do I. 
                                    	That's not right .
                                    	Yes ,but . .. 
                                    	I'm afraid not .
                                    	No way .
                                    	Of course not . 
                                    	I'm sorry ,but don't agree .
                                    	I disagree . 
                                    	
                                    page 6
                                """.trimIndent(),
                                text_zh = """
                                    第一单元友谊

                                    使用语言

                                    阅读和听力



                                    1.读丽莎写给青少年广播电台王小姐的信，预测王小姐会说什么，听完后，检查并讨论她的建议。

                                    	尊敬的王小姐：

                                    	我现在和同学们有些矛盾。我和班上的男孩相处得很好。我们经常一起做作业，我们喜欢互相帮助。我们已经成为了真正的好朋友。但是其他同学开始说闲话。他们说我和这个男孩相爱了。这让我很生气。我不想结束这段感情友情，但我讨厌别人八卦。该怎么办？

                                    	你的，

                                    	丽莎


                                    2.听录音，听到单词的发音就拼，然后把每个句子分成几个意义组。

                                    	1 你和这个男孩在一起没有什么不好。

                                    	2 你和这个男孩的友谊是一件值得做的事。

                                    	3 青少年喜欢看一些不真实的东西。

                                    	4 我的建议是让你的同学知道你比他们更优秀。


                                    3 再听一遍录音，用上面的练习回答下列问题。

                                    	王小姐怎么说他们的友谊？
                                    	她这么说的_______

                                    	为什么她不认为丽莎应该结束他们的友谊？
                                    	她认为_______

                                    	她如何解释丽莎的同学们为什么八卦他们的友谊？
                                    	她这么说的_______

                                    	4王小姐有什么建议？
                                    	她让丽莎_______


                                    讲话

                                    你同意王小姐的建议吗？小组讨论。你可以在谈话中使用下列表达方式。


                                    同意

                                    我同意。

                                    是的，我想是的。

                                    我也是。

                                    我也是。

                                    确切地。

                                    没问题。

                                    当然。

                                    当然

                                    当然。

                                    好吧。

                                    你说得对。

                                    好主意。

                                    我认为这是个好主意。


                                    不同意

                                    别这么想。

                                    我也是。

                                    这是不对的。

                                    是的，但是。。

                                    恐怕不行。

                                    不可能。

                                    当然不是。

                                    对不起，我不同意。

                                    我不同意。

                                    第6页
                                """.trimIndent()
                            ),
                            TeachingTranslation(
                                id = 7,
                                text_eng = """
                                    Unit 1 Friendship  
                                    Reading and writing  

                                    	Dear Miss Wang , 
                                    		I'm a student from Huzhou Senior High School .I have a problem .I'm not very good at communicating with people .Although I try   to talk to my classmates ,still find it hard to make good friends with them .So I feel  quite lonely sometimes .I do want to change situation ,but I don't know how .I would  be grateful if you could give me some advice . 
                                    	Yours , Xiao Dong  
                                    	
                                    Miss Wang has received a letter from  Xiao Dong .He is also asking for some advice . Read the letter on the right carefully and help  Miss Wang answer it .
                                    1. Before you write ,brainstorm with a   partner about ways to change the situation .Make list your ideas and give your reasons .For example : 

                                    	
                                      
                                      Ideas ---- Why  
                                      to ask people their likes and dislikes  ---- find classmates with the same interests  
                                      to join in discussions and show interest in other people's ideas ---- to get to know different people and let them see you are friendly
                                      
                                    2. Decide which are the best ideas and put them into an order .Then write down  your advice and explain how it will help .Each idea can make one paragraph . The following sample and the expressions may help you . 

                                    	Dear Xiao Dong . 
                                    	I'm sorry you are having trouble in making friends .However ,the situation is easy to  change you take my advice .Here are some tips to help you : 

                                    	First ,why not ...  
                                    	If you do this ,..  

                                    	Secondly ,you should /can...  
                                    	Then /Thatway ,...
                                    	 
                                    	Thirdly ,it would be good idea if . . 
                                    	By doing this ,...  

                                    	I hope you will find these ideas useful . 

                                    	Yours . 
                                    	Miss Wang  

                                    3. Swap your letter with your partner .Look at his /herwork and help to improve it . Pick out any mistakes you see in spelling ,verb forms ,or punctuation .Swap  back .Correct any mistakes and write out your letter .


                                    page 7


                                """.trimIndent(),
                                text_zh = """
                                    第一单元友谊

                                    阅读与写作


                                    尊敬的王小姐：，
                                    我是湖州高中的一名学生。我有一个问题。我不太善于与人沟通。虽然我试着和我的同学交谈，但仍然很难与他们交到好朋友。所以我有时感到很孤独。我确实想改变现状，但我不知道怎么做。如果你能给我一些建议，我将不胜感激。
                                    你的，
                                    小东


                                    王小姐收到了小东的来信，他也在征求意见。仔细阅读右边的信，帮助王小姐回答。

                                    1 在你写作之前，和一个伙伴一起集思广益，讨论改变现状的方法。列出你的想法并给出理由。例如：
                                    想法----为什么
                                    问别人喜欢什么，不喜欢什么 ---- 找兴趣相投的同学
                                    参加讨论，对别人的想法表现出兴趣 ---- 了解不同的人，让他们看到你是友好的


                                    2决定哪些是最好的主意并把它们排成一个顺序。然后写下你的建议并解释它将如何起作用。每个主意可以组成一个段落。下面的示例和表达式可能会对您有所帮助。


                                    	亲爱的小东。
                                    	很抱歉，你在交朋友方面遇到了困难。不过，情况很容易改变。你接受我的建议。以下是一些帮助你的建议：

                                    	首先，为什么不。。。
                                    	如果你这么做，。。

                                    	第二，你应该/可以。。。
                                    	然后/那样，。。。

                                    	第三，如果。
                                    	通过这样做，。。。

                                    	我希望你会发现这些想法很有用。

                                    	你的。
                                    	王小姐



                                    三。和你的搭档交换你的信。看看他/她的工作，帮助他/她改进。找出你在拼写、动词形式或标点符号上看到的任何错误。调回原处。纠正所有错误并写出你的信。

                                    第7页
                                """.trimIndent()
                            ),
                            TeachingTranslation(
                                id = 8,
                                text_eng = """
                                    Unit 1 Friendship  

                                    SUMMING UP 

                                    Write down what you have learned about friends and friendship . 
                                    ______________________
                                    ______________________

                                    From this unit you have also learned  

                                    useful verbs : ______________________
                                    useful nouns : ______________________
                                    useful expressions : ______________________
                                    a new grammar item : ______________________

                                    LEARNING TIP  
                                    It's a good habit for you to keep a diary .It  can help you remember past events .You  can express your feelings and thoughts in  it .It will help you improve your English if  you write your diary in English .Why not  have a try ? 



                                    READING FOR FUN  
                                    	Promise  --by Jessica Sills  

                                    	As you sit in silence ,
                                    	Wondering why 
                                    	I'll be your shoulder to cry on  
                                    	Until your tears run dry .
                                    	When you've been hurt ,
                                    	And can't believe what they've done 
                                    	If you need someone to talk to  
                                    	I'll be the one .


                                    	If a close friend hurts you ,
                                    	And you don't understand  
                                    	Remember I'm here , 
                                    	I'll lend a helping hand.  
                                    	Burdens are lighter  
                                    	when carried by two , 
                                    	And I just want you to know  
                                    	I'm here for you  
                                     
                                    Scoring sheet for the survey on page  
                                    1 A1 B3 C2  2 A1 B2 C3  3 A1 B2 C3  4 A3 B2 C1  5 A0 B3 C0

                                    page 8
                                """.trimIndent(),
                                text_zh = """
                                    第一单元友谊

                                    总结

                                    写下你所学到的关于朋友和友谊的知识。

                                    ______________________

                                    ______________________


                                    从这个单元你也学到了


                                    有用动词：______________________

                                    有用名词：______________________

                                    有用的表达式：______________________

                                    新语法项：______________________



                                    学习提示

                                    记日记对你来说是个好习惯。它可以帮助你记住过去的事情。你可以在日记中表达你的感受和想法。如果你用英语写日记，它将帮助你提高你的英语水平。为什么不试试呢？




                                    为好玩而读书

                                    承诺 —— 杰西卡·西尔斯


                                    当你静静地坐着，

                                    想知道为什么吗

                                    我将是你哭泣的肩膀

                                    直到你的眼泪流干。

                                    当你受伤的时候，

                                    简直不敢相信他们的所作所为

                                    如果你需要找个人谈谈

                                    我就是那个。




                                    如果一个亲密的朋友伤害了你，

                                    你不明白吗

                                    记住我在这里，

                                    我会伸出援助之手。

                                    负担更轻

                                    当两个人带着的时候，

                                    我只想让你知道

                                    我在这里等你



                                    第1页调查计分表

                                    1 A1 B3 C2 2 A1 B2 C3 3 A1 B2 C3 4 A3 B2 C1 5 A0 B3 C0

                                    第8页
                                """.trimIndent()
                            ),
                        )
                    )
                    .bindWords(TempUtil.loadTeachingWords(R.raw.tb_grade11_01_unit1_wordlist))
                    .bindAudiosPaths(
                        mutableListOf(
                            TempUtil.loadRawFile(
                                R.raw.tb_grade11_01_unit1_textbook,
                                "tb_grade11_01_unit1_textbook"
                            )!!,
                            TempUtil.loadRawFile(
                                R.raw.tb_grade11_02_unit1_words,
                                "tb_grade11_02_unit1_words"
                            )!!,
                        )
                    )
                    .bindPageSnapshopPaths(
                        mutableListOf(
                            TempUtil.loadRawFile(R.raw.tb_007, "tb_007")!!,
                            TempUtil.loadRawFile(R.raw.tb_008, "tb_008")!!,
                            TempUtil.loadRawFile(R.raw.tb_009, "tb_009")!!,
                            TempUtil.loadRawFile(R.raw.tb_010, "tb_010")!!,
                            TempUtil.loadRawFile(R.raw.tb_011, "tb_011")!!,
                            TempUtil.loadRawFile(R.raw.tb_012, "tb_012")!!,
                            TempUtil.loadRawFile(R.raw.tb_013, "tb_013")!!,
                            TempUtil.loadRawFile(R.raw.tb_014, "tb_014")!!,
                            TempUtil.loadRawFile(R.raw.tb_047, "tb_047")!!,
                            TempUtil.loadRawFile(R.raw.tb_048, "tb_048")!!,
                            TempUtil.loadRawFile(R.raw.tb_049, "tb_049")!!,
                            TempUtil.loadRawFile(R.raw.tb_050, "tb_050")!!,
                            TempUtil.loadRawFile(R.raw.tb_051, "tb_051")!!,
                            TempUtil.loadRawFile(R.raw.tb_052, "tb_052")!!,
                            TempUtil.loadRawFile(R.raw.tb_053, "tb_053")!!,
                        )
                    ),
                TeachingBookUnitSection(
                    name = "第2单元",
                    description = "Unit 2: English around the world",
                    unitCoverImagePath = TempUtil.loadRawFile(R.raw.tb_015, "tb_015")
                ).bindId(2)
            )

        )

        tb.unitsDb?.forEach {
            it.bookWordItemsDb?.let { roomDb.teachingBookWord().insert(it) }
            it.bookTeachingPointsDb?.let { roomDb.teachingPoint().insert(it) }
            it.bookTranslationsDb?.let { roomDb.teachingTranslation().insert(it) }
        }
        roomDb.teachingBookUnitSection().insert(tb.unitsDb!!)
        roomDb.teachingBook().insert(tb)
    }

}
