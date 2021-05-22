package com.viastub.kao100

import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.viastub.kao100.adapter.CommonViewPagerAdapter
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.base.BaseFragment
import com.viastub.kao100.config.db.init.BasicDataLoader
import com.viastub.kao100.config.db.init.ConfigGlobalLoader
import com.viastub.kao100.db.ConfigGlobal
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.module.ci.CiFragment
import com.viastub.kao100.module.drawer.NavPageVpnActivity
import com.viastub.kao100.module.kao.KaoFragment
import com.viastub.kao100.module.lian.LianFragment
import com.viastub.kao100.module.my.MyCiHistoryPageActivity
import com.viastub.kao100.module.my.MyCollectionHistoryPageActivity
import com.viastub.kao100.module.my.MyDataManagmentActivity
import com.viastub.kao100.module.my.MyPracticeHistoryPageActivity
import com.viastub.kao100.module.xue.XueFragment
import com.viastub.kao100.utils.VariablesMain
import com.yechaoa.yutilskt.ActivityUtilKt
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_content.*
import kotlinx.android.synthetic.main.fragment_kao.*

class MainActivity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_main
    }

    override fun afterCreated() {

        toolbar.title = resources.getString(R.string.app_name)
        toolbar.contentInsetStartWithNavigation = 0
        setSupportActionBar(toolbar)

        initActionBarDrawer()

        initFragments()
        initListener()


        header_action_refresh.setOnClickListener {
            var pagerAdapter = (view_pager.adapter as CommonViewPagerAdapter)
            (pagerAdapter.getItem(view_pager.currentItem) as BaseFragment).refresh()
        }

        awaitAsync({
            var roomDb = RoomDB.get(applicationContext)
            BasicDataLoader().load(applicationContext, roomDb)
            ConfigGlobalLoader().load(applicationContext, roomDb)
            roomDb.configGlobal().getByKey(ConfigGlobal.key_grades)
        }, {
            it?.valuesByComma()?.let {
                spin_my_grade.attachDataSource(it)
                spin_my_grade.setOnSpinnerItemSelectedListener { parent, view, position, id ->
                    VariablesMain.selectedGrade = spin_my_grade.selectedItem as String
                    var pagerAdapter = (view_pager.adapter as CommonViewPagerAdapter)
                    var currentFragment =
                        (pagerAdapter.getItem(view_pager.currentItem) as BaseFragment)
                    currentFragment.gradeChanged()
                }
            }
        })

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
            addFragment(KaoFragment())
        }
        view_pager.offscreenPageLimit = 3
        view_pager.adapter = viewPagerAdapter
    }

    private fun initListener() {
        var header = nav_view.getHeaderView(0)
        var profileSetting = header.findViewById<ImageView>(R.id.myprofile_settings)

        nav_view.setNavigationItemSelectedListener {
            // Handle navigation view item clicks here.

            var pagerAdapter = (view_pager.adapter as CommonViewPagerAdapter)
            var currentFragment = (pagerAdapter.getItem(view_pager.currentItem) as BaseFragment)

            when (it.itemId) {
                R.id.nav_mywordhistory -> {
                    var intent = Intent(this, MyCiHistoryPageActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_mycollect -> {
                    var intent = Intent(this, MyCollectionHistoryPageActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_mypracticehistory -> {
                    var intent = Intent(this, MyPracticeHistoryPageActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_vpn_manage -> {
                    var intent = Intent(this, NavPageVpnActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_db_management -> {
                    var intent = Intent(this, MyDataManagmentActivity::class.java)
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
                    0 -> {
                        toolbar.title = "词典"
                        spin_my_grade.visibility = View.GONE
                    }
                    1 -> {
                        toolbar.title = "教材学习"
                        spin_my_grade.visibility = View.VISIBLE
                    }
                    2 -> {
                        toolbar.title = "配套刷题"
                        spin_my_grade.visibility = View.VISIBLE
                    }
                    3 -> {
                        toolbar.title = "试卷真题"
                        spin_my_grade.visibility = View.VISIBLE
                    }
                    else -> toolbar.title = "词学练考100分"
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