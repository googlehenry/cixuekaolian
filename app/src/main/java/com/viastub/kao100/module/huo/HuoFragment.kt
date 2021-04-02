package com.viastub.kao100.module.huo

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.viastub.kao100.R
import com.viastub.kao100.adapter.ArticleAdapter
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.beans.Article
import com.viastub.kao100.beans.ArticleDetail
import com.viastub.kao100.beans.Banner
import com.viastub.kao100.beans.BaseBean
import com.viastub.kao100.http.RemoteAPI
import com.viastub.kao100.module.huo.*
import com.viastub.kao100.utils.GlideImageLoader
import com.viastub.kao100.utils.and
import com.yechaoa.yutilskt.YUtilsKt
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_huo.*
import java.util.*
import kotlin.math.roundToInt

class HuoFragment : BaseFragment(), OnBannerListener, OnLoadMoreListener,
    OnItemClickListener, OnItemChildClickListener {

    private lateinit var mDataList: MutableList<ArticleDetail>
    private lateinit var bannerList: List<Banner>
    private lateinit var mArticleAdapter: ArticleAdapter
    private var mPosition: Int = 0

    companion object {
        private const val TOTAL_COUNTER = 20//每次加载数量
        private var CURRENT_SIZE = 0//当前加载数量
        private var CURRENT_PAGE = 0//当前加载页数
    }

    override fun id(): Int {
        return R.layout.fragment_huo
    }

    override fun afterViewCreated(view: View, savedInstanceState: Bundle?) {
        RemoteAPI.apis.getBanner()
            .and(success = { updateBanner(it) }, fail = { apiCallFailure(it) })
        RemoteAPI.apis.getArticleList(CURRENT_PAGE)
            .and(success = { updateArticleList(it) }, fail = { apiCallFailure(it) })
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

        banner.setOnBannerListener(this@HuoFragment)
    }

    private fun updateArticleList(article: BaseBean<Article>) {
        CURRENT_SIZE = article.data.datas.size
        mDataList = article.data.datas
        mArticleAdapter = ArticleAdapter().apply {
            animationEnable = true
            //item点击事件
            setOnItemClickListener(this@HuoFragment)
            //item子view点击事件
            setOnItemChildClickListener(this@HuoFragment)
            //加载更多
            loadMoreModule.setOnLoadMoreListener(this@HuoFragment)
        }

        recycler_view_high.adapter = mArticleAdapter
        mArticleAdapter.setList(mDataList)
    }

    override fun onLoadMore() {
        recycler_view_high.postDelayed({
            if (CURRENT_SIZE < TOTAL_COUNTER) {
                mArticleAdapter.loadMoreModule.loadMoreEnd(true)
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