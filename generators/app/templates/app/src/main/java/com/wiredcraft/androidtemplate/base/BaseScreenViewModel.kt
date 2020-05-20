package com.wiredcraft.androidtemplate.base

import androidx.lifecycle.*
import com.wiredcraft.androidtemplate.plugin.LoggingPlugin
import com.wiredcraft.androidtemplate.ui.custom.LiveError
import kotlinx.coroutines.cancelChildren

abstract class BaseScreenViewModel : ViewModel(), LoggingPlugin, LifecycleObserver {
    protected val mError: SingleLiveEvent<LiveError> = SingleLiveEvent()
    val error: LiveData<LiveError> = mError

    protected val mIsLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean> = mIsLoading
    protected val mIsLoadingModal: MutableLiveData<Boolean> = MutableLiveData()
    val isLoadingModal: LiveData<Boolean> = mIsLoadingModal
    protected val mLoadingTitle: MutableLiveData<String> = MutableLiveData()
    val loadingTitle: LiveData<String> = mLoadingTitle

    fun updateLoading(loading: Boolean, modal: Boolean = false, title: String? = null) {
        mIsLoadingModal.postValue(modal)
        mLoadingTitle.postValue(title)

        // mIsLoading should update at last
        mIsLoading.postValue(loading)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun autoCancelChildren() {
        viewModelScope.coroutineContext.cancelChildren()
    }
}
