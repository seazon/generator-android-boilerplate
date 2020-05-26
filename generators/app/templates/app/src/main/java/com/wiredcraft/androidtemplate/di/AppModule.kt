package <%= appPackage %>.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import <%= appPackage %>.MainActivity
import <%= appPackage %>.App
import <%= appPackage %>.factory.CoreViewModelFactory
import <%= appPackage %>.ui.view.main.MainViewModel
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