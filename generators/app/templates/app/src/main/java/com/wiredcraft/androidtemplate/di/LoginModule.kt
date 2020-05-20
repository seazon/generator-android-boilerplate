package com.wiredcraft.androidtemplate.di

import androidx.lifecycle.ViewModel
import com.wiredcraft.androidtemplate.ui.view.login.LoginScreenViewModel
import com.wiredcraft.androidtemplate.ui.view.login.LoginScreenFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class LoginModule {
    @ContributesAndroidInjector
    abstract fun bindLoginScreenFragment(): LoginScreenFragment

    @Binds
    @IntoMap
    @ViewModelKey(LoginScreenViewModel::class)
    abstract fun bindLoginScreenViewModel(vm: LoginScreenViewModel): ViewModel

}