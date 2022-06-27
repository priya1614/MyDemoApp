package com.example.mydemoapp

import android.os.Bundle
import com.example.mydemoapp.base.BaseActivity
import com.example.mydemoapp.view.LoginFragment

class MainActivity : BaseActivity() {
    override fun getContentViewId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var loginFragment = LoginFragment()
        pushFragment(R.id.fragment_container, loginFragment, false, false)
    }
}