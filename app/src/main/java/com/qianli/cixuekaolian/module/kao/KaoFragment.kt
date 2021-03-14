package com.qianli.cixuekaolian.module.kao

import android.os.Bundle
import android.view.View
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_kao.*

class KaoFragment : BaseFragment() {


    override fun id(): Int {
        return R.layout.fragment_kao
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        ferrisWheelView.startAnimation()
    }


}