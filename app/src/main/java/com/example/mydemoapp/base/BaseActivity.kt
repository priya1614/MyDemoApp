package com.example.mydemoapp.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

import androidx.fragment.app.FragmentActivity


abstract class BaseActivity: FragmentActivity() {
    abstract fun getContentViewId(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewId())
    }
    fun pushFragment(
        @IdRes containerLyt: Int, fragment: Fragment,
        addToBackStack: Boolean,
        replace: Boolean
    ) {
        supportFragmentManager.let {
            val transaction = it.beginTransaction()
            if (replace) {
                transaction.replace(containerLyt, fragment)
            } else {
                transaction.add(containerLyt, fragment)
            }
            if (addToBackStack) {
                transaction.addToBackStack(fragment.tag)
            }
            transaction.commit()
        }
    }
}
