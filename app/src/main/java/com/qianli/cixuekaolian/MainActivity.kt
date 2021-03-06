package com.qianli.cixuekaolian

import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.qianli.cixuekaolian.adapter.CommonViewPagerAdapter
import com.qianli.cixuekaolian.base.BaseActivity
import com.qianli.cixuekaolian.module.huo.HuoFragment
import com.qianli.cixuekaolian.module.huo.simple.MyHuoFragment
import com.yechaoa.yutilskt.ActivityUtilKt
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun createPresenter() {

    }

    override fun initView() {

        toolbar.title = resources.getString(R.string.app_name)
        setSupportActionBar(toolbar)

        initActionBarDrawer()

        initFragments()
    }

    private fun initActionBarDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    /**
     * 初始化Fragment
     */
    private fun initFragments() {
        val viewPagerAdapter = CommonViewPagerAdapter(supportFragmentManager).apply {
            addFragment(HuoFragment())
            addFragment(HuoFragment())
            addFragment(MyHuoFragment())
            addFragment(HuoFragment())
            addFragment(HuoFragment())
        }
        view_pager.offscreenPageLimit = 1
        view_pager.adapter = viewPagerAdapter
        bottom_navigation.selectedItemId = R.id.navigation_huo
    }

    override fun onResume() {
        super.onResume()
//        setBadge()
        initListener()
    }

    /**
     * 给BottomNavigationView 设置Badge 小红点
     *
     * BottomNavigationMenuView中的每一个Tab是一个FrameLayout，所以可以在上面随意添加View、这样就可以实现角标了
     */
    private fun setBadge() {
        //获取底部菜单view
        val menuView = bottom_navigation.getChildAt(0) as BottomNavigationMenuView
        //获取第2个itemView
        val itemView = menuView.getChildAt(1) as BottomNavigationItemView
        //引入badgeView
        val badgeView =
            LayoutInflater.from(this).inflate(R.layout.layout_badge_view, menuView, false)
        //把badgeView添加到itemView中
        itemView.addView(badgeView)
        //获取子view并设置显示数目
        val count = badgeView.findViewById<TextView>(R.id.tv_badge)
        count.text = "2"

        //不显示则隐藏
        //count.visibility=View.GONE
    }

    private fun initListener() {

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                bottom_navigation.menu.getItem(position).isChecked = true
                //设置checked为true，但是不能触发ItemSelected事件，所以滑动时也要设置一下标题
                when (position) {
                    0 -> toolbar.title =
                        resources.getString(R.string.activity_main_content_bottom_menu_ci)
                    1 -> toolbar.title =
                        resources.getString(R.string.activity_main_content_bottom_menu_xue)
                    2 -> toolbar.title =
                        resources.getString(R.string.activity_main_content_bottom_menu_huo)
                    3 -> toolbar.title =
                        resources.getString(R.string.activity_main_content_bottom_menu_lian)
                    4 -> toolbar.title =
                        resources.getString(R.string.activity_main_content_bottom_menu_kao)
                    else -> toolbar.title = resources.getString(R.string.app_name)
                }
            }
        })

        /**
         * bottom_navigation 点击事件
         */
        bottom_navigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_ci -> {
                    view_pager.currentItem = 0
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_xue -> {
                    view_pager.currentItem = 1
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_huo -> {
                    view_pager.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_lian -> {
                    view_pager.currentItem = 3
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_kao -> {
                    view_pager.currentItem = 4
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    /**
     * 拦截返回事件，自处理
     */
    private var mExitTime: Long = 0 // 保存用户按返回键的时间
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtilKt.showCenterToast("再按一次退出" + resources.getString(R.string.app_name))
                mExitTime = System.currentTimeMillis()
            } else {
                ActivityUtilKt.closeAllActivity()
            }
        }
        //super.onBackPressed()
    }
}