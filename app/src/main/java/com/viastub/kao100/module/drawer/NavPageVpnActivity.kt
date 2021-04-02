package com.viastub.kao100.module.drawer

import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.http.RemoteAPIVpnService
import com.viastub.kao100.utils.and
import kotlinx.android.synthetic.main.activity_ci_word_detail_page.header_back
import kotlinx.android.synthetic.main.activity_left_nav_vpn.*

class NavPageVpnActivity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_left_nav_vpn
    }

    override fun afterCreated() {
        supportActionBar?.hide()

        vpn_start.setOnClickListener {
            RemoteAPIVpnService.apis.vpnSart()
                .and(success = { vpn_logs.text = it.string() }, fail = { apiCallFailure(it) })
        }
        vpn_status.setOnClickListener {
            RemoteAPIVpnService.apis.getVpnStatus()
                .and(success = { vpn_logs.text = it.string() }, fail = { apiCallFailure(it) })
        }
        vpn_stop.setOnClickListener {
            RemoteAPIVpnService.apis.vpnStop()
                .and(success = { vpn_logs.text = it.string() }, fail = { apiCallFailure(it) })
        }

        //init with vpn status logs
        showVpnStatus()

        header_back.setOnClickListener { onBackPressed() }
    }

    fun showVpnStatus() {
        RemoteAPIVpnService.apis.getVpnStatus()
            .and(success = { vpn_logs.text = it.string() }, fail = { apiCallFailure(it) })
    }

}