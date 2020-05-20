package com.wiredcraft.androidtemplate.ui.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.wiredcraft.androidtemplate.base.BaseScreenFragment
import com.wiredcraft.androidtemplate.R
import com.wiredcraft.androidtemplate.databinding.FragmentLoginScreenBinding

class LoginScreenFragment : BaseScreenFragment(),
    LoginScreenViewModel.LoginScreenNavigator {
    private val mVm: LoginScreenViewModel by viewModels { factory }

    private lateinit var mBinding: FragmentLoginScreenBinding
    private lateinit var mFocused: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(mVm)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login_screen, container, false)

        mBinding.vm = mVm
        mBinding.lifecycleOwner = viewLifecycleOwner

        return mBinding.root
    }

    override fun setVmObserversOnViewCreated() {
        mVm.isLoading.observe(this, Observer {
            if (it) {
                mainActivity.showLoading()
            } else {
                mainActivity.dismissLoading()
            }
        })

        mVm.error.observe(this, Observer {
            showMd {
                title("title")
                content("content")
                positiveText("ok")
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mVm.setNavigator(this)
    }


    override fun goUp() {
        findNavController().popBackStack()
    }


}
