package com.wiredcraft.androidtemplate.base

import com.wiredcraft.androidtemplate.api.ApiManager
import com.wiredcraft.androidtemplate.ktx.RepoScope
import com.wiredcraft.androidtemplate.plugin.LoggingPlugin
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