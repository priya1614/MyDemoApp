package com.example.mydemoapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydemoapp.R
import com.example.mydemoapp.adapter.DashboardAdapter
import com.example.mydemoapp.data.model.User
import com.example.mydemoapp.databinding.FragmentDashboardBinding
import com.example.mydemoapp.viewmodel.DashBoardViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class DashboardFragment(mail: String) : Fragment() {

    private lateinit var viewModel: DashBoardViewModel
    private lateinit var adapter: DashboardAdapter
    private lateinit var binding: FragmentDashboardBinding
    private var mail: String = ""
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null

    init {
        this.mail = mail
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        fusedLocationClient = activity?.let { LocationServices.getFusedLocationProviderClient(it) }
        if (!checkPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions()
            }
        } else {
            getLastLocation()
        }
        setupViewModel()
        setupUI()
        setupObserver()
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileImg.setOnClickListener {
            val dashboardFragment = ProfileFragment(mail)
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, dashboardFragment)
            //transaction?.disallowAddToBackStack()
            transaction?.remove(LoginFragment())
            transaction?.commit()
        }
    }

    /*
    attached adpter to the recycler view
     */
    private fun setupUI() {
        binding.newsListRv.layoutManager = LinearLayoutManager(context)
        adapter =
            DashboardAdapter(
                arrayListOf()
            )
        binding.newsListRv.addItemDecoration(
            DividerItemDecoration(
                binding.newsListRv.context,
                (binding.newsListRv.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.newsListRv.adapter = adapter

    }

    private fun setupObserver() {
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            renderList(it)
            binding.newsListRv.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE

        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
        ).get(DashBoardViewModel::class.java)
    }


    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        try {
            fusedLocationClient?.lastLocation?.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    lastLocation = task.result
                    binding.latTextData.text = lastLocation?.latitude.toString()
                    binding.lonTextView.text = lastLocation?.longitude.toString()
                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)
                    Toast.makeText(
                        context,
                        "No location detected. Make sure location is enabled on the device.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun showSnackbar(
        mainTextStringId: String, actionStringId: String,
        listener: View.OnClickListener
    ) {
        Toast.makeText(context, mainTextStringId, Toast.LENGTH_LONG).show()
    }

    private fun checkPermissions(): Boolean {
        val permissionState = context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    private fun requestPermissions() {
        val shouldProvideRationale = activity?.let {
            ActivityCompat.shouldShowRequestPermissionRationale(
                it,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        }
        if (shouldProvideRationale!!) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            showSnackbar("Location permission is needed for core functionality", "Okay",
                View.OnClickListener {
                    startLocationPermissionRequest()
                })
        } else {
            Log.i(TAG, "Requesting permission")
            startLocationPermissionRequest()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            when {
                grantResults.isEmpty() -> {
                    // If user interaction was interrupted, the permission request is cancelled and you
                    // receive empty arrays.
                    Log.i(TAG, "User interaction was cancelled.")
                }
                grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    // Permission granted.
                    getLastLocation()
                }
                else -> {
                    showSnackbar("Permission was denied", "Settings",
                        View.OnClickListener {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                Build.DISPLAY, null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }

    companion object {
        private val TAG = "LocationProvider"
        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }

}