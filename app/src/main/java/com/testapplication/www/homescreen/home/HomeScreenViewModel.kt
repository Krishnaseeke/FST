package com.testapplication.www.homescreen.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {

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


}
