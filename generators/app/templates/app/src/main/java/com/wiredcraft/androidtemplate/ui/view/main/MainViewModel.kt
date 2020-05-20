package com.wiredcraft.androidtemplate.ui.view.main

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.wiredcraft.androidtemplate.api.ApiManager
import com.wiredcraft.androidtemplate.plugin.LoggingPlugin
import javax.inject.Inject
import kotlin.collections.set

class MainViewModel
@Inject constructor(
    app: Application,
    private val mApiManager: ApiManager
) : AndroidViewModel(app),
    LoggingPlugin,
    NavController.OnDestinationChangedListener {

    interface ScreenChangeListener {
        fun onScreenChange(arguments: Bundle?)
    }

    private val mCurrentScreen: MutableLiveData<NavDestination> = MutableLiveData()

    private val mScreenChangeListeners: HashMap<Pair<Int?, Int>, MutableList<ScreenChangeListener>> =
        hashMapOf()


    init {

    }


    fun registerScreenChangeListener(
        previous: Int,
        current: Int,
        listener: ScreenChangeListener
    ) {
        val key = Pair(previous, current)
        if (mScreenChangeListeners.containsKey(key)) {
            mScreenChangeListeners[key]?.add(listener)
        } else {
            mScreenChangeListeners[key] = mutableListOf(listener)
        }
    }


    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val key = Pair(mCurrentScreen.value?.id, destination.id)
        mScreenChangeListeners[key]?.forEach {
            it.onScreenChange(arguments)
        }

        mCurrentScreen.postValue(destination)
    }


    override fun onCleared() {

        mScreenChangeListeners.clear()
        super.onCleared()
    }


    suspend fun login(
        username: String
    ) {
        try {
            mApiManager.logIn(username)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
