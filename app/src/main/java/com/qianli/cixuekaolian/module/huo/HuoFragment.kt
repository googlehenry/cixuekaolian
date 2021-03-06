package com.qianli.cixuekaolian.module.huo

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.qianli.cixuekaolian.R
import com.qianli.cixuekaolian.base.BaseBean
import com.qianli.cixuekaolian.base.BaseFragment
import com.qianli.cixuekaolian.utils.GlideImageLoader
import com.yechaoa.yutilskt.ToastUtilKt
import com.yechaoa.yutilskt.YUtilsKt
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_huo.*
import java.util.*
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass.
 */
class HuoFragment : BaseFragment(), HuoDataUpdater, OnBannerListener, OnLoadMoreListener,
    OnItemClickListener, OnItemChildClickListener {

    companion object {
        private const val TOTAL_COUNTER = 20//每次加载数量
        private var CURRENT_SIZE = 0//当前加载数量
        private var CURRENT_PAGE = 0//当前加载页数
    }

    private lateinit var mHuoDataFetcher: HuoDataFetcher
    private lateinit var mDataList: MutableList<ArticleDetail>
    private lateinit var bannerList: List<Banner>
    private lateinit var mAdapterArticle: AdapterArticle
    private var mPosition: Int = 0

    override fun registerDataFetcher() {
        mHuoDataFetcher = HuoDataFetcher(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_huo
    }

    override fun initView() {

    }

    override fun initData() {
        mHuoDataFetcher.getBanner()
        mHuoDataFetcher.getArticleList(CURRENT_PAGE)
    }

    override fun updateBanner(banners: BaseBean<MutableList<Banner>>) {
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

        banner.setOnBannerListener(this)
    }


    override fun updateBannerError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun updateArticleList(article: BaseBean<Article>) {
        CURRENT_SIZE = article.data.datas.size
        mDataList = article.data.datas
        mAdapterArticle = AdapterArticle().apply {
            animationEnable = true
            //item点击事件
            setOnItemClickListener(this@HuoFragment)
            //item子view点击事件
            setOnItemChildClickListener(this@HuoFragment)
            //加载更多
            loadMoreModule.setOnLoadMoreListener(this@HuoFragment)
        }

        recycler_view.adapter = mAdapterArticle
        mAdapterArticle.setList(mDataList)
    }

    override fun updateArticleError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun updateArticleMoreList(article: BaseBean<Article>) {
        mDataList.addAll(article.data.datas)
        CURRENT_SIZE = article.data.datas.size
        mAdapterArticle.addData(article.data.datas)
        mAdapterArticle.loadMoreModule.loadMoreComplete()
    }

    override fun updateArticleMoreError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun login(msg: String) {
        showLoginDialog(msg)
    }

    private fun showLoginDialog(msg: String) {
        val builder = AlertDialog.Builder(mContext).apply {
            setTitle("提示")
            setMessage(msg)
            setPositiveButton("确定") { _, _ ->
                //startActivity(Intent(mContext, LoginActivity::class.java))
            }
            setNegativeButton("取消", null)
        }
        builder.create().show()
    }

    override fun collect(msg: String) {
        ToastUtilKt.showCenterToast(msg)
        mDataList[mPosition].collect = true
        mAdapterArticle.notifyDataSetChanged()
    }

    override fun unCollect(msg: String) {
        ToastUtilKt.showCenterToast(msg)
        mDataList[mPosition].collect = false
        mAdapterArticle.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //结束轮播
        banner.stopAutoPlay()
    }

    override fun OnBannerClick(position: Int) {
        /*val intent = Intent(mContext, DetailActivity::class.java).apply {
            putExtra(DetailActivity.WEB_URL, bannerList[position].url)
            putExtra(DetailActivity.WEB_TITLE, bannerList[position].title)
        }
        startActivity(intent)*/
    }

    override fun onLoadMore() {
        recycler_view.postDelayed({
            if (CURRENT_SIZE < TOTAL_COUNTER) {
                mAdapterArticle.loadMoreModule.loadMoreEnd(true)
            } else {
                CURRENT_PAGE++
                mHuoDataFetcher.getArticleMoreList(CURRENT_PAGE)
            }
        }, 1000)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        /*val intent = Intent(mContext, DetailActivity::class.java).apply {
            putExtra(DetailActivity.WEB_URL, mDataList[position].link)
            putExtra(DetailActivity.WEB_TITLE, mDataList[position].title)
        }
        startActivity(intent)*/
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        mPosition = position
        if (mDataList[position].collect) {
            mHuoDataFetcher.unCollect(mDataList[position].id)
        } else {
            mHuoDataFetcher.collect(mDataList[position].id)
        }
    }

}

