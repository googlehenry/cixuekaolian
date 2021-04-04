package com.viastub.kao100.utils

import kotlinx.coroutines.*

class Coroutines {
    companion object {
        //Kotlin coroutine, to load data async
        fun <T> doAsync(dataAction: () -> T, uiAction: (result: T) -> Unit) {
            CoroutineScope(Dispatchers.Main).launch {
                var rs = async(Dispatchers.IO) {
                    dataAction.invoke()
                }.await()
                uiAction.invoke(rs)
            }
        }

        fun <T> doAsync(delay: Long = 0, dataAction: () -> T, uiAction: (result: T) -> Unit) {
            CoroutineScope(Dispatchers.Main).launch {
                var rs = async(Dispatchers.IO) {
                    if (delay > 0) delay(delay)
                    dataAction.invoke()
                }.await()
                uiAction.invoke(rs)
            }
        }

    }
}