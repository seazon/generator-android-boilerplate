package com.wiredcraft.androidtemplate.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wiredcraft.androidtemplate.MainActivity
import com.wiredcraft.androidtemplate.App
import com.wiredcraft.androidtemplate.factory.CoreViewModelFactory
import com.wiredcraft.androidtemplate.ui.view.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AppModule {
    @Binds
    abstract fun bindApplication(app: App): Application

    @Binds
    abstract fun bindCoreViewModelFactory(coreViewModelFactory: CoreViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(vm: MainViewModel): ViewModel

}