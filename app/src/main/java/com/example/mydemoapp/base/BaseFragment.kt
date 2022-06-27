package com.example.mydemoapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.mydemoapp.base.BaseActivity


abstract class BaseFragment : Fragment(), LifecycleOwner {
    abstract fun layoutId(): Int
    protected var mActivity: BaseActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.mActivity = activity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(layoutId(), container, false)
        return view
    }
}

