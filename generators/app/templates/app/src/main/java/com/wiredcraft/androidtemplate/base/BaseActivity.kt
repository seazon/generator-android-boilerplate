package <%= appPackage %>.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import <%= appPackage %>.plugin.LoggingPlugin
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasAndroidInjector, LoggingPlugin {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}