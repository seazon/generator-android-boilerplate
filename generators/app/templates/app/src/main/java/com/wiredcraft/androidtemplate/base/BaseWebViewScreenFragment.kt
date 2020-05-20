package com.wiredcraft.androidtemplate.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.webkit.WebResourceErrorCompat
import androidx.webkit.WebViewClientCompat
import com.wiredcraft.androidtemplate.databinding.FragmentWebViewBinding

abstract class BaseWebViewScreenFragment : BaseScreenFragment() {
    protected lateinit var mBinding: FragmentWebViewBinding
    private val mOnBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goBack()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher
            .addCallback(this, mOnBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentWebViewBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAppbar()
        initWebView()
    }

    private fun initAppbar() {
        var titleStr = requireArguments().getString("title")
        if (titleStr.isNullOrEmpty()) {
            val titleIntRes = requireArguments().getInt("titleIntRes", 0)
            if (titleIntRes != 0) {
                titleStr = mainActivity.getString(titleIntRes)
            }
        }
//        mBinding.titleText.text = titleStr
//        mBinding.back.onClick(lifecycleScope) {
//            goBack()
//        }
    }

    override fun onDestroyView() {
        mainActivity.dismissLoading()
        super.onDestroyView()
    }

    protected open fun goBack() {
        if (mBinding.webView.canGoBack()) {
            mBinding.webView.goBack()
        } else {
            findNavController().navigateUp()
        }
    }

    protected open var mWebViewClient = object : WebViewClientCompat() {
        override fun shouldOverrideUrlLoading(
            view: WebView,
            request: WebResourceRequest
        ): Boolean {
            return requireArguments().getBoolean("shouldOverrideUrlLoading") ||
                super.shouldOverrideUrlLoading(view, request)
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceErrorCompat
        ) {
            mainActivity.dismissLoading()
            super.onReceivedError(view, request, error)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            mainActivity.dismissLoading()
            super.onPageFinished(view, url)
        }
    }

    private fun initWebView() {
        mBinding.webView.settings.apply {
            javaScriptEnabled = requireArguments().getBoolean("javaScriptEnabled")
            domStorageEnabled = requireArguments().getBoolean("domStorageEnabled")
        }
        mBinding.webView.webViewClient = mWebViewClient
        mainActivity.showLoading()
        var urlStr = requireArguments().getString("url")
        if (urlStr.isNullOrEmpty()) {
            val urlIntRes = requireArguments().getInt("urlIntRes", 0)
            if (urlIntRes != 0) {
                urlStr = mainActivity.getString(urlIntRes)
            }
        }
        mBinding.webView.loadUrl(urlStr)
    }
}
