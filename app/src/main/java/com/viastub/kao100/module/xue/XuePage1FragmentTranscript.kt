package com.viastub.kao100.module.xue

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.viastub.kao100.R
import com.viastub.kao100.adapter.TranscriptItemAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.beans.TranscriptItem
import kotlinx.android.synthetic.main.activity_xue_detail_page_frag_transcript.*

class XuePage1FragmentTranscript : BaseFragment(), View.OnClickListener {

    override fun id(): Int {
        return R.layout.activity_xue_detail_page_frag_transcript
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {

        var adapter = TranscriptItemAdapter(this)
        adapter.data = prepareDemoData()
        recycler_view_transcipt.adapter = adapter
        //https://www.jianshu.com/p/e68a0b5fd383
        recycler_view_transcipt.addItemDecoration(
            DividerItemDecoration(
                mContext,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun prepareDemoData(): MutableList<TranscriptItem> {
        var map = mutableMapOf<String, String>()
        var keyList = mutableListOf<Int>()
        demoScript.lines().forEachIndexed { index: Int, s: String ->
            var eng = index % 2 == 0
            var idx: Int = index / 2 + 1
            if (!keyList.contains(idx)) keyList.add(idx)
            map.put(idx.toString() + if (eng) "_e" else "_c", s)
        }

        var partCounter = 0
        var partLineCounter = 0
        return keyList.mapIndexed { index, i ->
            var eng = map.get(i.toString() + "_e")
            val cn = map.get(i.toString() + "_c")
            val isChapter = (eng + cn).trim().startsWith("*", ignoreCase = true)
            partCounter = if (isChapter) partCounter + 1 else partCounter
            partLineCounter = if (isChapter) 0 else partLineCounter + 1
            var seq = if (isChapter) {
                "$partCounter"
            } else {
                "$partCounter.$partLineCounter"
            }

            TranscriptItem(index + 1, seq, eng, cn, isChapter)
        }.toMutableList()

    }

    override fun onClick(v: View?) {
    }

    val demoScript = """*Unit one:
*单元一:
Hello! I’m Zoom. 
你好！我是祖姆。
Hi!My name’s Zip.
你好！我是次波。
Hello, I’m Mike.
你好，我是迈克。
Hi,I’m Wu Yifan.
你好，我是吴一凡。
Goodbye!
再见！
Bye,Miss White!
再见，怀特小姐！
Hello,I’m Chen Jie.What’s your name?
你好，我是陈杰。你叫什么名字？
My name’s Sarah.
我叫萨拉。
*Let’s talk 
*让我们讨论
Hello,I’m Miss White.
你们好，我是怀特小姐。
Hello,I’m Wu Yifan.
你好，我是吴一凡。
Hi,I’m Sarah.
你好，我是萨拉。
*Let’s play 
*让我们玩耍
Hello,I’m Liu Xin.
你好，我是刘欣。
Hi,I’m John.
你好，我是约翰。
*Let’s chant 
*让我们吟诵
I have a ruler.Me too!
我有一把尺子。我也有！
I have a pencil.Me too!
我有一支铅笔。我也有！
I have a crayon.Me too!
我有一支蜡笔。我也有！
I have an eraser.Me too!
我有一块橡皮。我也有！
*Let’s learn 
*让我们学习
I have a ruler.
我有一把尺子。
I have an eraser.
我有一块橡皮。
ruler;  eraser;  pencil;   crayon 
尺子；橡皮；  铅笔；   蜡笔
*Let’s sing 
*让我们歌唱
A B C song
字母歌
Aa-Bb-Cc-Dd-Ee-Ff-Gg,Hh-Ii-Jj-Kk-Ll-Mm-Nn.
Aa-Bb-Cc-Dd-Ee-Ff-Gg,Hh-Ii-Jj-Kk-Ll-Mm-Nn.
Oo-Pp-Qq,Rr-Ss-Tt,Uu-Vv-Ww-Xx-Yy-Zz.
Oo-Pp-Qq,Rr-Ss-Tt,Uu-Vv-Ww-Xx-Yy-Zz.
Xx-Yy-Zz,now you see,I can say my ABCs.
Xx-Yy-Zz,现在你看，我会说我的ABCs。
*Let’s find out 
*让我们找出答案
*Let’s talk 
*让我们讨论
Hello,I’m Mike.What’s your name?
你好，我是迈克。你叫什么名字？
My name’s John.
我叫约翰。
Goodbye!
再见！
Bye,Miss White.
再见，怀特小姐。
Let’s play 让我们玩耍
What’s your name?
你叫什么名字？
My name’s Lily.
我叫莉莉。
*Let’s learn 
*让我们学习
Zoom!Your bag!
祖姆！你的包！
Oh,no!
哦，不！
bag;  pen;   pencil box;  book
包； 钢笔； 铅笔盒；   书
Let’s do 让我们做
Open your pencil box.
打开你的铅笔盒。
Show me your pen.
给我看看你的钢笔。
Close your book.
合上你的书。
Carry your bag.
背上你的包。
*Start to read 
*开始读
Circle the same letters.
圈出相同的字母。
*Read and count.
*读并数数。
ruler;  pen;  crayon;eraser;pencil;  bag;  pencil box; book
尺子；钢笔；蜡笔；橡皮；铅笔；背包；铅笔盒；  书
*Let’s check 
*让我们检查
*Listen and number.
*听并标序号。
*
*听力原文：
1.Miss White:Hello,I’m Miss White. 
你们好，我是怀特小姐。
Boys and girls: Hello, Miss White! 
你好，怀特小姐！
2.Zoom: Hello! I’m Zoom. 
你好！我是祖姆。
3.Mike: Hi, I’m Mike.What’s your name? 
你好，我是迈克。你叫什么名字？
Chen Jie: My name’s Chen Jie. 
我叫陈杰。
4.Wu Yifan: Goodbye! 
再见！
Sarah:Bye! 
再见！
*Look and match.
*看并连线
*Let’s sing 
*让我们唱歌
Hello,Sarah!Do,oh,do.Hello,John!Do,oh,do.
你好，萨拉！做，哦，做。你好，约翰！做，哦，做。
Hello,Mike!Do,oh,do.Hello!Hello!Hello!
你好，迈克！做，哦，做！
*Story time 
*故事时间
Hello!
你好！
Hi! Who’s there?
你好！谁在那里？
Guess!
猜！
Are you Tutu?
你是图图吗？
No!
不是！
Haha! I’m Zip!
哈哈！我是次波！
Hi, Zip!My name’s Zoom.
你好，次波！我叫祖姆。
Let’s play! OK?
让我们一起玩吧！好吗？
Great!
太好了！""".trimIndent()
}