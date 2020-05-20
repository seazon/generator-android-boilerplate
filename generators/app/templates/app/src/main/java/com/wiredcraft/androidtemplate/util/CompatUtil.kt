package com.wiredcraft.androidtemplate.util

import android.text.Html
import android.text.Spanned

object CompatUtil {
    fun fromHtml(html: String): Spanned =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(html)
        }

}