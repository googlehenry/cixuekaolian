package com.qianli.cixuekaolian

import android.content.Intent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.qianli.cixuekaolian.adapter.CommonViewPagerAdapter
import com.qianli.cixuekaolian.base.BaseActivity
import com.qianli.cixuekaolian.module.ci.CiFragment
import com.qianli.cixuekaolian.module.huo.HuoFragment
import com.qianli.cixuekaolian.module.leftnav.NavPageVpnActivity
import com.qianli.cixuekaolian.module.lian.LianFragment
import com.qianli.cixuekaolian.module.xue.XueFragment
import com.yechaoa.yutilskt.ActivityUtilKt
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*

class MainActivity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_main
    }

    override fun afterCreated() {

        toolbar.title = resources.getString(R.string.app_name)
        setSupportActionBar(toolbar)

        initActionBarDrawer()

        initFragments()
        initListener()
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
            addFragment(CiFragment())
            addFragment(XueFragment())
            addFragment(LianFragment())
            addFragment(HuoFragment())
        }
        view_pager.offscreenPageLimit = 1
        view_pager.adapter = viewPagerAdapter
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initListener() {

        nav_view.setNavigationItemSelectedListener {
            // Handle navigation view item clicks here.
            when (it.itemId) {
                R.id.nav_vpn_manage -> {
                    var intent = Intent(this, NavPageVpnActivity::class.java)
                    startActivity(intent)
                }
            }

            //关闭侧边栏
            drawer_layout.closeDrawer(GravityCompat.START)

            true
        }

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
                        resources.getString(R.string.activity_main_content_bottom_menu_lian)
                    3 -> toolbar.title =
                        resources.getString(R.string.activity_main_content_bottom_menu_kao)
                    else -> toolbar.title = resources.getString(R.string.app_name)
                }
            }
        })

        /**
         * bottom_navigation 点击事件
         */


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
                R.id.navigation_lian -> {
                    view_pager.currentItem = 2
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_kao -> {
                    view_pager.currentItem = 3
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
        bottom_navigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottom_navigation.selectedItemId = R.id.navigation_ci
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