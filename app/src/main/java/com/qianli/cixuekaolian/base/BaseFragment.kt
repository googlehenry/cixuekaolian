package com.qianli.cixuekaolian.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yechaoa.yutilskt.ActivityUtilKt
import com.yechaoa.yutilskt.ToastUtilKt

abstract class BaseFragment : Fragment() {
    lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(id(), container, false)
        mContext = ActivityUtilKt.currentActivity!!
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterViewCreated(view, savedInstanceState)
    }

    /*
    Sub class could override below abstract method to load data or anyother
     */
    abstract fun id(): Int
    abstract fun afterViewCreated(view: View, savedInstanceState: Bundle?)

    fun apiCallFailure(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    fun toast(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    open fun touchEventAware(): Boolean = false
    open fun onTouchEvent(event: MotionEvent?): Boolean {
        return false
    }

}