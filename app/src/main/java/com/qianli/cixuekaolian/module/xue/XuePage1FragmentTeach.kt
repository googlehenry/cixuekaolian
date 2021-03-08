package com.qianli.cixuekaolian.module.xue

import android.os.Bundle
import android.view.View
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.adapter.ExpandableTextAdapter
import com.qianli.cixuekaolian.base.BaseFragment
import com.qianli.cixuekaolian.beans.TeachItem
import kotlinx.android.synthetic.main.activity_xue_page_frag_teach.*

class XuePage1FragmentTeach : BaseFragment() {

    override fun id(): Int {
        return R.layout.activity_xue_page_frag_teach
    }
    var teachItemAdapter = ExpandableTextAdapter()
    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        recycler_view.adapter = teachItemAdapter
        teachItemAdapter.data = mutableListOf(
            TeachItem(
                1, "如何与别人打招呼", "下面让我们来看一看它们的具体使用语境:\n" +
                        "语境(1)问候:在学校遇见老师或同学时,我们可以用Helo!”或“Hi!”进行问候。\n" +
                        "\n" +
                        "语境(2)打招呼:在公园里,我们看见同学在玩游戏,想与他/她打招呼,可以说Helo,X×.”或“Hi,XⅩ.”\n" +
                        "\n" +
                        "语境(3)引起别人的注意:在多功能厅里看节目时,我们给好朋友留了一个位置,看见他/她走过来,为了引起他/她的注意,我们可以说“ Hello!”或“Hi!”。\n" +
                        "\n" +
                        "语境(4)打电话用语:我们在打电话时,拿起听筒先说一声“Helo!”,相当于用汉语打电话时的“喂!”。\n" +
                        "\n" +
                        "易混辨析\n" +
                        "hel与hi\n" +
                        "hello用于打招呼、问候或唤起对方的注意\n" +
                        "hi比helo更随意,可以翻译成“嗨”,一般用于熟人之间见面打招呼"
            ),
            TeachItem(
                2, "介绍自己的句型: I'm...", "【课文应用】Hello, I'm Wu yifan你好,我是吴一凡。\n" +
                        "【句型结构】Im+其他(Amy,tll, seven...)\n" +
                        "【重点解析】在向别人进行自我介绍时,经常会用到“I'm..”句型。例如:\n" +
                        "介绍自己的姓名:I' m Betty.我是贝蒂。\n" +
                        "介绍自己的年龄: I'm eleven.我十一岁了。\n" +
                        "介绍自己的身材:I'm tall.我个子高。\n" +
                        "\n" +
                        "【生活实例】开学了,老师让新转来的同学 Lingling进行自我介绍。让我们来看一看 Lingling是如何说的:\n" +
                        "I'm Lingling. I'm eight, I'm short.I'm from Jilin.我是玲玲。我八岁了。我个子矮。我来自吉林"
            ),
        )
    }

}