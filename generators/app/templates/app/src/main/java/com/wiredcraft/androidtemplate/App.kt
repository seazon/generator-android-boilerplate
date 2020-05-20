package com.wiredcraft.androidtemplate

import androidx.appcompat.app.AppCompatDelegate
import com.jakewharton.threetenabp.AndroidThreeTen
import com.wiredcraft.androidtemplate.di.DaggerAppComponent
import com.wiredcraft.androidtemplate.plugin.LoggingPlugin
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication(), LoggingPlugin {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

}