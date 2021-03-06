package com.qianli.cixuekaolian.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yechaoa.yutilskt.ActivityUtilKt
import com.yechaoa.yutilskt.YUtilsKt

/**
 * Created by yechao on 2020/1/13/013.
 * Describe :
 */
abstract class BaseFragment : Fragment(), DataUpdater {

    protected lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(getLayoutId(), container, false)
        mContext = ActivityUtilKt.currentActivity!!
        registerDataFetcher()
        return view
    }

    /**
     * 初始化方法要放在onViewCreated或者onActivityCreated方法里，不然找不到控件
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    /**
     * 懒加载，当Fragment显示的时候再请求数据
     * 如果数据不需要每次都刷新，可以先判断数据是否存在
     * 不存在 -> 请求数据，存在 -> 什么都不做
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
//        if (hidden) initData()
    }

    protected abstract fun registerDataFetcher()

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

    protected abstract fun initData()

    override fun showLoading() {
        YUtilsKt.showLoading(ActivityUtilKt.currentActivity!!, "加载中")
    }

    override fun hideLoading() {
        YUtilsKt.hideLoading()
    }

    override fun onErrorCode(bean: BaseBean<Any>) {

    }
}