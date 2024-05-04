package com.testapplication.www.homescreen.create

import CreateScreenDB
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.content.Context

data class CreateScreenState(
    val customerName: String = "",
    val phoneNumber: String = "",
    val alternatePhoneNumber: String = "",
    val address: String = "",
    val businessCategory: String = "",
    val callStatus: String = "",
    val leadStatus: String = "",
    val followUpDate: String = "",
    val followUpTime: String = "",
    val followUpActionCall: Boolean = false,
    val followUpActionVisit: Boolean = false,
    val comments: String = "",
    val isSubmissionSuccessful: Boolean = false
)

class CreateScreenViewModel(context: Context, private val userID: Long) : ViewModel() {
    private val _state = MutableStateFlow(CreateScreenState())
    val state: StateFlow<CreateScreenState> = _state

    private val db = CreateScreenDB(context)

    fun updateCustomerName(name: String) {
        _state.value = _state.value.copy(customerName = name)
    }

    fun updatePhoneNumber(number: String) {
        _state.value = _state.value.copy(phoneNumber = number)
    }

    fun updateAlternatePhoneNumber(number: String) {
        _state.value = _state.value.copy(alternatePhoneNumber = number)
    }

    fun updateAddress(address: String) {
        _state.value = _state.value.copy(address = address)
    }

    fun updateBusinessCategory(category: String) {
        _state.value = _state.value.copy(businessCategory = category)
    }

    fun updateCallStatus(status: String) {
        _state.value = _state.value.copy(callStatus = status)
    }

    fun updateLeadStatus(status: String) {
        _state.value = _state.value.copy(leadStatus = status)
    }

    fun updateFollowUpDate(date: String) {
        _state.value = _state.value.copy(followUpDate = date)
    }

    fun updateFollowUpTime(time: String) {
        _state.value = _state.value.copy(followUpTime = time)
    }

    fun toggleFollowUpActionCall() {
        _state.value = _state.value.copy(
            followUpActionCall = !_state.value.followUpActionCall
        )
    }

    fun toggleFollowUpActionVisit() {
        _state.value = _state.value.copy(
            followUpActionVisit = !_state.value.followUpActionVisit
        )
    }

    fun updateComments(comments: String) {
        _state.value = _state.value.copy(comments = comments)
    }

    fun saveFST() {
        val stateValue = _state.value
        viewModelScope.launch {
            val isSuccess = db.createFST(
                userId = userID,
                customerName = stateValue.customerName,
                phoneNumber = stateValue.phoneNumber,
                alternatePhoneNumber = stateValue.alternatePhoneNumber,
                address = stateValue.address,
                businessCategory = stateValue.businessCategory,
                callStatus = stateValue.callStatus,
                leadStatus = stateValue.leadStatus,
                followUpDate = stateValue.followUpDate,
                followUpTime = stateValue.followUpTime,
                followUpActionCall = if (stateValue.followUpActionCall) "1" else "0",
                followUpActionVisit = if (stateValue.followUpActionVisit) "1" else "0",
                comments = stateValue.comments
            )
            _state.value = _state.value.copy(isSubmissionSuccessful = isSuccess)
        }
    }
}
