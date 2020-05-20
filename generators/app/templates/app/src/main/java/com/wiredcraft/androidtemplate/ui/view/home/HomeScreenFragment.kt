package com.wiredcraft.androidtemplate.ui.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.wiredcraft.androidtemplate.base.BaseScreenFragment
import com.wiredcraft.androidtemplate.R
import com.wiredcraft.androidtemplate.databinding.FragmentHomeScreenBinding
import com.wiredcraft.androidtemplate.env.NavEnv
import com.wiredcraft.androidtemplate.plugin.LoggingPlugin
import com.wiredcraft.androidtemplate.ui.view.home.HomeScreenViewModel

class HomeScreenFragment : BaseScreenFragment(),
    HomeScreenViewModel.Navigator,
    LoggingPlugin {
    private val mVm: HomeScreenViewModel by viewModels { factory }
    private lateinit var mBinding: FragmentHomeScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home_screen,
            container,
            false
        )

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.vm = mVm
        mBinding.lifecycleOwner = viewLifecycleOwner
        mVm.setNavigator(this)


    }

    override fun goToLogin() {
        with(mainActivity) {
            goToLogin(NavEnv.FORWARD_NAV_OPTIONS_BUILDER.build())
        }
    }


}
