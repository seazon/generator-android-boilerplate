package com.wiredcraft.androidtemplate.env

import androidx.navigation.NavOptions
import com.wiredcraft.androidtemplate.R

object NavEnv {
    enum class LinkDest {
        Search,
        HorizontalBanners
    }

    val POPUP_NAV_OPTIONS_BUILDER: NavOptions.Builder
        get() = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_up)
            .setExitAnim(R.anim.slide_down)
            .setPopEnterAnim(R.anim.slide_up)
            .setPopExitAnim(R.anim.slide_down)

    val FORWARD_NAV_OPTIONS_BUILDER: NavOptions.Builder
        get() = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_right)

    val BACKWARD_NAV_OPTIONS_BUILDER: NavOptions.Builder
        get() = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_right)
}