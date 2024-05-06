import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

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
    val isLoading: Boolean = false,
    val isSubmissionSuccessful: Boolean = false
)

class CreateScreenViewModel(context: Context, private val userID: Long) : ViewModel() {
    private val _state = MutableStateFlow(CreateScreenState())
    val state: StateFlow<CreateScreenState> = _state
    val createScreendb = CreateScreenDB(context)
    val ctx1 = context

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
    fun isValidInput(state: CreateScreenState): Boolean {
        // Implement your validation logic here
        // For example, check if required fields are not empty
        return state.customerName.isNotEmpty() && state.phoneNumber.isNotEmpty() &&
                state.address.isNotEmpty() && state.leadStatus.isNotEmpty() &&
                (state.followUpDate.isNotEmpty() && state.followUpTime.isNotEmpty())
    }

    fun saveFST( userId: Long,
                 customerName: String?,
                 phoneNumber: String?,
                 alternatePhoneNumber: String?,
                 address: String?,
                 businessCategory: String?,
                 callStatus: String?,
                 leadStatus: String?,
                 followUpDate: String,
                 followUpTime: String?,
                 followUpActionCall: Int,  // Change parameter name and data type to Int
                 followUpActionVisit: Int,  // Change parameter name and data type to Int
                 comments: String?) {
        val stateValue = _state.value
        if (isValidInput(stateValue)) {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }
                var isSuccess = false // Default to false
                try {
                    isSuccess = createScreendb.createFST( userId = userId,
                        customerName = customerName,
                        phoneNumber = phoneNumber,
                        alternatePhoneNumber = alternatePhoneNumber,
                        address = address,
                        businessCategory = businessCategory,
                        callStatus = callStatus,
                        leadStatus = leadStatus,
                        followUpDate = followUpDate,
                        followUpTime = followUpTime,
                        followUpActionCall = followUpActionCall,
                        followUpActionVisit = followUpActionVisit,
                        comments = comments)
                    delay(2000) // Simulate network/database delay

                } catch (e: Exception) {
                    // Handle exceptions if necessary
                } finally {
                    _state.update { it.copy(isLoading = false, isSubmissionSuccessful = isSuccess) }
                }
            }
        } else {
            Toast.makeText(ctx1, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
        }
    }


}
