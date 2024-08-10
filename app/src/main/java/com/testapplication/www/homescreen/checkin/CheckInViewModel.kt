package com.testapplication.www.homescreen.checkin

import CreateScreenDB
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CheckInState(
    val isSubmissionSuccessful: Boolean = false,
    val isLoading: Boolean = false
)

class CheckInViewModel(context: Context) : ViewModel() {

    val checkInStatusFlow = MutableSharedFlow<Boolean>()
    private val _state = MutableStateFlow(CheckInState())
    val state: StateFlow<CheckInState> = _state.asStateFlow()
    private val db: CreateScreenDB = CreateScreenDB(context)

    fun insertCheckIn(
        userId: Long?,
        checkInStatus: Int,
        checkInTime: String,
        longitude: Double?,
        latitude: Double?,
        checkInImage: String?,
        context: Context
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            var isSuccess = false
            _state.update { it.copy(isLoading = true) }
            try {
                isSuccess = db.insertCheckIn(
                    userId,
                    checkInStatus,
                    checkInTime,
                    longitude,
                    latitude,
                    checkInImage
                )
            }  catch (e: Exception) {
                Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }finally {
                _state.update { it.copy(isLoading = false, isSubmissionSuccessful = isSuccess) }
            }

            if (isSuccess) {
                checkInStatusFlow.emit(true)
            }
        }
    }
}