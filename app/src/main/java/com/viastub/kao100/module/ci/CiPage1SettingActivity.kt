package com.viastub.kao100.module.ci

import android.widget.Toast
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.utils.VariablesCi
import kotlinx.android.synthetic.main.activity_ci_word_setting.*


class CiPage1SettingActivity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_ci_word_setting
    }

    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }
        VariablesCi.ciContext?.dictConfig?.let {
            it.onlineSpeakingLinkTemplate?.let {
                dict_settings_sound_link_template.setText(it.toCharArray(), 0, it.length)
            }

            if (it.ttsEnabled) {
                radiogroup_enable.isChecked = true
            } else {
                radiogroup_disable.isChecked = true
            }

            if (it.playSoundAtStart) {
                radiogroup_autosound_enable.isChecked = true
            } else {
                radiogroup_autosound_disable.isChecked = true
            }

            val interval = it.autoNextIntervalSeconds.toString()
            dict_settings_autonext_interval.setText(interval.toCharArray(), 0, interval.length)

        }

        btn_dict_config_save.setOnClickListener {
            saveConfig()
        }
    }

    private fun saveConfig() {
        VariablesCi.ciContext?.dictConfig?.let {
            it.onlineSpeakingLinkTemplate =
                dict_settings_sound_link_template.text.toString().trim()
            it.ttsEnabled = radiogroup_enable.isChecked
            it.playSoundAtStart = radiogroup_autosound_enable.isChecked
            it.autoNextIntervalSeconds =
                dict_settings_autonext_interval.text.toString().toIntOrNull() ?: 10

            it.autoNextIntervalSeconds =
                if (it.autoNextIntervalSeconds >= 5) it.autoNextIntervalSeconds else 10

            doAsync {
                RoomDB.get(applicationContext).dictionaryConfig().insert(it)
            }
            Toast.makeText(this, "配置已保存", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        saveConfig()
    }

}