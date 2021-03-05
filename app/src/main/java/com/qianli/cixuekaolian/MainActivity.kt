package com.qianli.cixuekaolian

import androidx.appcompat.app.ActionBarDrawerToggle
import com.qianli.cixuekaolian.base.BaseActivity
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
        bottom_navigation.selectedItemId = R.id.navigation_huo
    }


}