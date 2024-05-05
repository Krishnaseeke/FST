import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import android.content.Context

// Dummy CreateScreenDB implementation


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
        _state.update { it.copy(customerName = name) }
    }

    fun updatePhoneNumber(number: String) {
        _state.update { it.copy(phoneNumber = number) }
    }

    fun updateAlternatePhoneNumber(number: String) {
        _state.update { it.copy(alternatePhoneNumber = number) }
    }

    fun updateAddress(address: String) {
        _state.update { it.copy(address = address) }
    }

    fun updateBusinessCategory(category: String) {
        _state.update { it.copy(businessCategory = category) }
    }

    fun updateCallStatus(status: String) {
        _state.update { it.copy(callStatus = status) }
    }

    fun updateLeadStatus(status: String) {
        _state.update { it.copy(leadStatus = status) }
    }

    fun updateFollowUpDate(date: String) {
        _state.update { it.copy(followUpDate = date) }
    }

    fun updateFollowUpTime(time: String) {
        _state.update { it.copy(followUpTime = time) }
    }

    fun toggleFollowUpActionCall() {
        _state.update { it.copy(followUpActionCall = !it.followUpActionCall) }
    }

    fun toggleFollowUpActionVisit() {
        _state.update { it.copy(followUpActionVisit = !it.followUpActionVisit) }
    }

    fun updateComments(comments: String) {
        _state.update { it.copy(comments = comments) }
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
            _state.update { it.copy(isSubmissionSuccessful = isSuccess) }
        }
    }

    fun updateStateConcurrently() {
        viewModelScope.launch {
            val deferredUpdates = listOf(
                async { updateCustomerName("Customer A") },
                async { updatePhoneNumber("1234567890") },
                async { updateAlternatePhoneNumber("0987654321") },
                async { updateAddress("123 Main St") },
                async { updateBusinessCategory("Retail") },
                async { updateCallStatus("Completed") },
                async { updateLeadStatus("Qualified") },
                async { updateFollowUpDate("2024-05-15") },
                async { updateFollowUpTime("14:30") },
                async { toggleFollowUpActionCall() },
                async { toggleFollowUpActionVisit() },
                async { updateComments("No additional comments") }
            )

            deferredUpdates.awaitAll() // Wait for all concurrent updates to complete
        }
    }
}
