package com.example.mydemoapp.view

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mydemoapp.R
import com.example.mydemoapp.base.BaseFragment
import com.example.mydemoapp.databinding.FragmentProfileBinding


class ProfileFragment(email: String) :BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_profile

    lateinit var binding : FragmentProfileBinding
    var email :String = ""

    init {
        this.email=email
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userName.text = email
        binding.profileImage.setOnClickListener {
            val dashboardFragment = DashboardFragment(email)
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, dashboardFragment)
            //transaction?.disallowAddToBackStack()
            transaction?.remove(LoginFragment())
            transaction?.commit()
        }
    }
}