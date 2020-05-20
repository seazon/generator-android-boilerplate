package com.wiredcraft.androidtemplate.di

import androidx.lifecycle.ViewModel
import com.wiredcraft.androidtemplate.ui.view.home.HomeScreenFragment
import com.wiredcraft.androidtemplate.ui.view.home.HomeScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {
    @ContributesAndroidInjector
    abstract fun bindHomeScreenFragment(): HomeScreenFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeScreenViewModel::class)
    abstract fun bindHomeScreenViewModel(vm: HomeScreenViewModel): ViewModel
}