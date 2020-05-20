package com.wiredcraft.androidtemplate.util

object NumberUtil {
    fun formatComma(num: Long): String {
        return String.format("%,d", num)
    }
}
