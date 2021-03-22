package com.qianli.cixuekaolian.utils

class TempUtil {
    companion object {
        var counter = 0
        var categoryMap: MutableMap<String, Int> = mutableMapOf(
            "阅读理解" to 0,
            "完形填空" to 1,
            "听力测试" to 2,
            "短文填空" to 3,
            "单项选择" to 4,
            "语法填空" to 5,
            "短文改错" to 6,
            "汉语提示填写单词" to 7,
            "汉语提示完成句子" to 8
        )
    }
}