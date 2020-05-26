package <%= appPackage %>

import androidx.appcompat.app.AppCompatDelegate
import com.jakewharton.threetenabp.AndroidThreeTen
import <%= appPackage %>.di.DaggerAppComponent
import <%= appPackage %>.plugin.LoggingPlugin
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