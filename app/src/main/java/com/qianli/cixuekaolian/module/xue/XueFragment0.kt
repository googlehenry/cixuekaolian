package com.qianli.cixuekaolian.module.xue

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_xue.*

class XueFragment0 : BaseFragment() {

    override fun id(): Int {
        return R.layout.fragment_xue
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_xue_start.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    XuePage0Activity::class.java
                )
            )
        }
    }

}