package com.testapplication.www.homescreen.home

import CreateScreenDB
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel(context: Context) : ViewModel() {

    private var homeScreenDB: HomeScreenDB? = null
    val leadsCreatedCount = MutableStateFlow<Int>(0)
    val demosScheduledCount = MutableStateFlow<Int>(0)
    val demosCompletedCount = MutableStateFlow<Int>(0)
    val licensesSoldCount = MutableStateFlow<Int>(0)
    fun initialize(context: Context, userID: Long?) {
        viewModelScope.launch(Dispatchers.IO) {
            homeScreenDB = HomeScreenDB(context)
            leadsCreatedCount.value = homeScreenDB?.getLeadsCreatedCount(userID) ?: 0
            demosScheduledCount.value = homeScreenDB?.getDemosScheduledCount(userID) ?: 0
            demosCompletedCount.value = homeScreenDB?.getDemosCompletedCount(userID) ?: 0
            licensesSoldCount.value = homeScreenDB?.getLicensesSoldCount(userID) ?: 0
        }
    }


    private val db: CreateScreenDB = CreateScreenDB(context)

    fun insertCheckIn(
        userId: Long?,
        checkInStatus: Int,
        checkInTime: String,
        longitude: Double?,
        latitude: Double?,
        checkInImage: String?
    ):Boolean {
        var valueInsert = false
        viewModelScope.launch(Dispatchers.IO) {
           valueInsert = db.insertCheckIn(userId, checkInStatus, checkInTime,longitude,latitude, checkInImage)
        }
        return valueInsert
    }


//    fun getCheckInStatus(userId: Long?, callback: (Int) -> Unit) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val status = db.getCheckInStatus(userId)
//            callback(status)
//        }
//    }


}
