package com.viastub.kao100.utils

import android.content.Context
import android.content.Intent
import android.os.Looper
import android.widget.Toast
import com.viastub.kao100.module.ci.CiPage0Activity

class ActivityUtils {
    companion object {
        fun goToDictionary(context: Context, wordlist: List<String>, word: String) {
            var intent = Intent(context, CiPage0Activity::class.java)

            val startIdx = wordlist.indexOf(word)
            VariablesCi.ciContext!!.currentWordList = wordlist.toMutableList()
            VariablesCi.ciContext!!.currentIndex = startIdx
            VariablesCi.ciContext!!.initIndex = startIdx
            VariablesCi.ciContext!!.currentWordRootLinks = mutableMapOf()

            context.startActivity(
                intent
            )
        }

        fun showToastFromThread(mContext: Context, message: String) {
            Looper.prepare()
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
            Looper.loop()
        }

    }

}