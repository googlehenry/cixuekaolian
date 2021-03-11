package com.qianli.cixuekaolian.module.lian

import android.os.Bundle
import android.view.View
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_lian.*

class LianFragment : BaseFragment() {


    override fun id(): Int {
        return R.layout.fragment_lian
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        ferrisWheelView.startAnimation()
    }


}