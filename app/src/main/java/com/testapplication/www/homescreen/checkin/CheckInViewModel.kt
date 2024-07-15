package com.testapplication.www.homescreen.checkin

import CreateScreenDB
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class CheckInViewModel(context: Context):ViewModel() {

    val checkInStatusFlow = MutableSharedFlow<Boolean>()

    private val db: CreateScreenDB = CreateScreenDB(context)

    fun insertCheckIn(
        userId: Long?,
        checkInStatus: Int,
        checkInTime: String,
        longitude: Double?,
        latitude: Double?,
        checkInImage: String?
    ){
        viewModelScope.launch(Dispatchers.IO) {
            val valueInsert = db.insertCheckIn(userId, checkInStatus, checkInTime,longitude,latitude, checkInImage)
            if (valueInsert) {
                checkInStatusFlow.emit(true)
            }
        }
    }
}