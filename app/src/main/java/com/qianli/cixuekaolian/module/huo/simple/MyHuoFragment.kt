package com.qianli.cixuekaolian.module.huo.simple

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.base.BaseBean
import com.qianli.cixuekaolian.http.RemoteAPI
import com.qianli.cixuekaolian.module.huo.*
import com.qianli.cixuekaolian.utils.GlideImageLoader
import com.qianli.cixuekaolian.utils.and
import com.yechaoa.yutilskt.ActivityUtilKt
import com.yechaoa.yutilskt.ToastUtilKt
import com.yechaoa.yutilskt.YUtilsKt
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_huo.*
import java.util.*
import kotlin.math.roundToInt

class MyHuoFragment : Fragment(), OnBannerListener, OnLoadMoreListener,
    OnItemClickListener, OnItemChildClickListener {
    lateinit var mContext: Context
    private lateinit var mDataList: MutableList<ArticleDetail>
    private lateinit var bannerList: List<Banner>
    private lateinit var mAdapterArticle: AdapterArticle
    private var mPosition: Int = 0

    companion object {
        private const val TOTAL_COUNTER = 20//每次加载数量
        private var CURRENT_SIZE = 0//当前加载数量
        private var CURRENT_PAGE = 0//当前加载页数
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_huo, container, false)
        mContext = ActivityUtilKt.currentActivity!!
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RemoteAPI.apis.getBanner()
            .and(success = { updateBanner(it) }, fail = { apiCallFailure(it) })
        RemoteAPI.apis.getArticleList(CURRENT_PAGE)
            .and(success = { updateArticleList(it) }, fail = { apiCallFailure(it) })
    }

    private fun apiCallFailure(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    private fun updateBanner(banners: BaseBean<MutableList<Banner>>) {
        bannerList = banners.data

        val images: MutableList<String> = ArrayList()
        val titles: MutableList<String> = ArrayList()
        for (i in banners.data.indices) {
            images.add(banners.data[i].imagePath)
            titles.add(banners.data[i].title)
        }

        //动态设置高度
        val layoutParams = banner.layoutParams
        layoutParams.height = (YUtilsKt.getScreenWidth() / 1.8).roundToInt()

        banner
            .setImages(images)
            .setBannerTitles(titles)
            .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            .setImageLoader(GlideImageLoader())
            .start()

        banner.setOnBannerListener(this@MyHuoFragment)
    }

    fun updateArticleList(article: BaseBean<Article>) {
        CURRENT_SIZE = article.data.datas.size
        mDataList = article.data.datas
        mAdapterArticle = AdapterArticle().apply {
            animationEnable = true
            //item点击事件
            setOnItemClickListener(this@MyHuoFragment)
            //item子view点击事件
            setOnItemChildClickListener(this@MyHuoFragment)
            //加载更多
            loadMoreModule.setOnLoadMoreListener(this@MyHuoFragment)
        }

        recycler_view.adapter = mAdapterArticle
        mAdapterArticle.setList(mDataList)
    }

    override fun onLoadMore() {
        recycler_view.postDelayed({
            if (CURRENT_SIZE < TOTAL_COUNTER) {
                mAdapterArticle.loadMoreModule.loadMoreEnd(true)
            } else {
                CURRENT_PAGE++
                RemoteAPI.apis.getArticleList(CURRENT_PAGE)
                    .and(success = { updateArticleList(it) }, fail = { apiCallFailure(it) })
            }
        }, 1000)
    }

    override fun OnBannerClick(position: Int) {

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }


}