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
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment(), IHomeView, OnBannerListener, OnLoadMoreListener,
    OnItemClickListener,
    OnItemChildClickListener {

    companion object {
        private const val TOTAL_COUNTER = 20//每次加载数量
        private var CURRENT_SIZE = 0//当前加载数量
        private var CURRENT_PAGE = 0//当前加载页数
    }

    private lateinit var mHomePresenter: HomePresenter
    private lateinit var mDataList: MutableList<ArticleDetail>
    private lateinit var bannerList: List<Banner>
    private lateinit var mArticleAdapter: ArticleAdapter
    private var mPosition: Int = 0

    override fun createPresenter() {
        mHomePresenter = HomePresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {

    }

    override fun initData() {
        mHomePresenter.getBanner()
        mHomePresenter.getArticleList(CURRENT_PAGE)
    }

    override fun getBanner(banners: BaseBean<MutableList<Banner>>) {
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


    override fun getBannerError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun getArticleList(article: BaseBean<Article>) {
        CURRENT_SIZE = article.data.datas.size
        mDataList = article.data.datas
        mArticleAdapter = ArticleAdapter().apply {
            animationEnable = true
            //item点击事件
            setOnItemClickListener(this@HomeFragment)
            //item子view点击事件
            setOnItemChildClickListener(this@HomeFragment)
            //加载更多
            loadMoreModule.setOnLoadMoreListener(this@HomeFragment)
        }

        recycler_view.adapter = mArticleAdapter
        mArticleAdapter.setList(mDataList)
    }

    override fun getArticleError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun getArticleMoreList(article: BaseBean<Article>) {
        mDataList.addAll(article.data.datas)
        CURRENT_SIZE = article.data.datas.size
        mArticleAdapter.addData(article.data.datas)
        mArticleAdapter.loadMoreModule.loadMoreComplete()
    }

    override fun getArticleMoreError(msg: String) {
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
        mArticleAdapter.notifyDataSetChanged()
    }

    override fun unCollect(msg: String) {
        ToastUtilKt.showCenterToast(msg)
        mDataList[mPosition].collect = false
        mArticleAdapter.notifyDataSetChanged()
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
                mArticleAdapter.loadMoreModule.loadMoreEnd(true)
            } else {
                CURRENT_PAGE++
                mHomePresenter.getArticleMoreList(CURRENT_PAGE)
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
            mHomePresenter.unCollect(mDataList[position].id)
        } else {
            mHomePresenter.collect(mDataList[position].id)
        }
    }

}

