package com.example.mydemoapp.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mydemoapp.data.model.User


class DashBoardViewModel: ViewModel() {
    private val users = MutableLiveData<List<User>>()
    private val latitude = MutableLiveData<String>()

    init {
        fetchUsers()
    }
//    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(Context)
//    companion object {
//        val locationRequest: LocationRequest = LocationRequest.create().apply {
//            interval = 10000
//            fastestInterval = 5000
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        }
//    }
//    private val locationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult?) {
//            locationResult ?: return
//            for (location in locationResult.locations) {
//                setLocationData(location)
//            }
//        }
//    }
//    @SuppressLint("MissingPermission")
//    private fun startLocationUpdates() {
//        fusedLocationClient.requestLocationUpdates(
//            locationRequest,
//            locationCallback,
//            null
//        )
//    }
//    private fun setLocationData(location: Location) {
//        value = LocationModel(
//            longitude = location.longitude,
//            latitude = location.latitude
//        )
//    }
//
//
//
//        fun abc(){
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location: Location? ->
//                location?.also {
//                    setLocationData(it)
//                }
//            }
//        startLocationUpdates()
//    }
//}
//

fun fetchUsers() {
        val allUsersFromApi = mutableListOf<User>()
        for(i in 0 .. 100){
            allUsersFromApi.addAll(i, listOf(User("priya")))
        }
        allUsersFromApi.addAll(allUsersFromApi)
        users.postValue(allUsersFromApi)

    }

    fun getUsers(): LiveData<List<User>> {
        return users
    }
}