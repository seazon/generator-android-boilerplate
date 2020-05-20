package com.wiredcraft.androidtemplate.ui.custom

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.afollestad.materialdialogs.MaterialDialog
import com.wiredcraft.androidtemplate.R

class CoreMaterialDialog(builder: Builder) : MaterialDialog(builder), LifecycleObserver {
    class Builder(context: Context) : MaterialDialog.Builder(context) {
        fun build(func: Builder.() -> Unit, owner: LifecycleOwner): CoreMaterialDialog {
            //
            typeface(Typeface.DEFAULT_BOLD, Typeface.DEFAULT)
            contentColor(ContextCompat.getColor(context, R.color.primaryColor))
            positiveColor(ContextCompat.getColor(context, R.color.primaryColor))
            negativeColor(ContextCompat.getColor(context, R.color.hh_error))
            canceledOnTouchOutside(false)

            //
            positiveText("OK")

            //
            func.invoke(this)

            return CoreMaterialDialog(this).apply {
                owner.lifecycle.addObserver(this)
            }
        }

        fun show(
            func: Builder.() -> Unit,
            owner: LifecycleOwner,
            leastState: Lifecycle.State
        ): CoreMaterialDialog {
            return build(func, owner).apply {
                if (owner.lifecycle.currentState.isAtLeast(leastState)) {
                    show()
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun autoDismiss() {
        dismiss()
    }
}
