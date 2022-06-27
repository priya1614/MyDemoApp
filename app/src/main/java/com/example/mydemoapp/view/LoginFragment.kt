package com.example.mydemoapp.view

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.mydemoapp.R
import com.example.mydemoapp.base.BaseFragment
import com.example.mydemoapp.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment() {
    override fun layoutId(): Int = R.layout.fragment_login
    private var signinButton: Button? = null
    lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signinButton = view.findViewById(R.id.btn_login)
        signinButton?.setOnClickListener {

            if (TextUtils.isEmpty(binding.inputEmail.text.toString())) {
                binding.inputEmail.error = getString(R.string.user_name_empty_error)
            } else if (TextUtils.isEmpty(binding.inputPassword.text.toString())) {
                 binding.inputPassword.error = getString(R.string.passowrd_empty_error)
            } else {
                val dashboardFragment = ProfileFragment(binding.inputEmail.text.toString())
                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.fragment_container, dashboardFragment)
                transaction?.remove(LoginFragment())
                transaction?.commit()
            }
        }
    }

}