package <%= appPackage %>.base

import <%= appPackage %>.api.ApiManager
import <%= appPackage %>.ktx.RepoScope
import <%= appPackage %>.plugin.LoggingPlugin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

abstract class BaseRepository : CoroutineScope by RepoScope(), LoggingPlugin {

    @Inject
    protected lateinit var mApi: ApiManager

    open fun dispose() {
        cancel()
    }
}