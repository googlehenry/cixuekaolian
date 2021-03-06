package com.viastub.kao100.module.my

import android.widget.Toast
import com.google.gson.Gson
import com.viastub.kao100.R
import com.viastub.kao100.base.BaseActivity
import com.viastub.kao100.beans.UserSignupResponse
import com.viastub.kao100.beans.UserSignupResponseCode
import com.viastub.kao100.db.RoomDB
import com.viastub.kao100.http.RemoteAPIDataService
import com.yechaoa.yutilskt.LogUtilKt
import kotlinx.android.synthetic.main.activity_ci_word_setting.header_back
import kotlinx.android.synthetic.main.activity_my_account_setting.*
import retrofit2.HttpException


class MyBindingAccountActivity : BaseActivity() {

    override fun id(): Int {
        return R.layout.activity_my_account_setting
    }

    var blockBackAction: Boolean = false

    override fun afterCreated() {
        supportActionBar?.hide()
        header_back.setOnClickListener { onBackPressed() }

        blockBackAction = intent.getBooleanExtra("blockBackAction", false)

        awaitAsync({
            val roomDb = RoomDB.get(applicationContext)
            roomDb.myUser().getById(1)
        }, {
            it?.let {
                it.officialName?.let {
                    usernameView.setText(it.toCharArray(), 0, it.length)
                    usernameView.isEnabled = false
                }
            }
        })

        okButton.setOnClickListener {

            val username = usernameView.text.toString()
            val password = passwordView.text.toString()
            if (username.isNotBlank() && password.isNotBlank()) {
                awaitAsync({
                    val roomDb = RoomDB.get(applicationContext)
                    roomDb.myUser().getById(1)?.let {
                        it.officialPassword = password
                        it.officialName = username

                        RemoteAPIDataService.apis.signUpUser(it.toMap()).onErrorReturn {
                            var errobody = (it as HttpException).response()?.errorBody()?.string()
                            LogUtilKt.e("login erro:$errobody")
                            if ((it as HttpException).response()?.code() == 400) {
                                Gson().fromJson(errobody, UserSignupResponse::class.java)
                            } else {
                                UserSignupResponse(null, null, null)
                            }
                        }.subscribe { resp ->
                            when (resp.code) {
                                UserSignupResponseCode.validated.name -> {
                                    roomDb.myUser().getById(1)?.let {
                                        it.expiryInSeconds = resp.expiryInSeconds
                                        it.officialName = username
                                        it.officialPassword = password

                                        roomDb.myUser().update(it)
                                    }
                                    blockBackAction = false
                                    LogUtilKt.i("expirayInSeconds:${it.expiryInSeconds}")
                                    runOnUiThread {
                                        usernameView.isEnabled = false
                                        passwordView.isEnabled = false
                                        result_image.setImageResource(R.drawable.icon_lian_result_tick)
                                        result_message.text = "??????/??????????????????"
                                        Toast.makeText(this, "??????/????????????", Toast.LENGTH_SHORT).show()
                                        onBackPressed()//auto return after success
                                    }

                                }
                                UserSignupResponseCode.username_conflict.name -> {
                                    runOnUiThread {
                                        result_image.setImageResource(R.drawable.icon_lian_result_cross)
                                        result_message.text = "?????????????????????"
                                    }
                                }
                                UserSignupResponseCode.invalid_password_or_username.name -> {
                                    runOnUiThread {
                                        result_image.setImageResource(R.drawable.icon_lian_result_cross)
                                        result_message.text = """
                                            ?????????/?????????????????????????????????
                                            ?????????(????????????6???)
                                            ??????(????????????8???)
                                        """.trimIndent()
                                    }
                                }
                                else -> {
                                    runOnUiThread {
                                        result_image.setImageResource(R.drawable.icon_lian_result_cross)
                                        result_message.text = "????????????,???????????????"
                                    }
                                }
                            }
                        }
                    }
                }, {})

            } else {
                Toast.makeText(this, "??????????????????/??????", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onBackPressed() {
        if (!blockBackAction) {
            super.onBackPressed()
        } else {
            Toast.makeText(this, "?????????????????????/??????", Toast.LENGTH_SHORT).show()
        }
    }

}