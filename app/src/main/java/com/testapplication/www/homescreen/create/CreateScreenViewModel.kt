import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.testapplication.www.homescreen.home.ScreenData
import com.testapplication.www.homescreen.home.ScreenData1
import com.testapplication.www.util.constants.Constants.ADDRESS_COL
import com.testapplication.www.util.constants.Constants.ALTERNATE_PHONE_COL
import com.testapplication.www.util.constants.Constants.BUSINESS_CATEGORY_COL
import com.testapplication.www.util.constants.Constants.CALL_STATUS_COL
import com.testapplication.www.util.constants.Constants.COMMENTS_COL
import com.testapplication.www.util.constants.Constants.CREATE_TABLE_NAME
import com.testapplication.www.util.constants.Constants.CUSTOMER_NAME_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_ACTION_CALL_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_ACTION_VISIT_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_DATE_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_TIME_COL
import com.testapplication.www.util.constants.Constants.ID_COL
import com.testapplication.www.util.constants.Constants.LEAD_STATUS_COL
import com.testapplication.www.util.constants.Constants.PHONE_NUMBER_COL
import com.testapplication.www.util.constants.Constants.PROOF_IMAGE_COL

// Dummy CreateScreenDB implementation


data class CreateScreenState(
    val customerName: String = "",
    val phoneNumber: String = "",
    val alternatePhoneNumber: String = "",
    val address: String = "",
    var businessCategory: String = "",
    var callStatus: String = "",
    var leadStatus: String = "",
    val followUpDate: String = "",
    val followUpTime: String = "",
    val followUpActionCall: Boolean = false,
    val followUpActionVisit: Boolean = false,
    val comments: String = "",
    var proofImage: String = "", // New field
    val longitudeLocation: String = "", // New field
    val latitudeLocation: String = "", // New field
    val isLoading: Boolean = false,
    val isSubmissionSuccessful: Boolean = false,
    val isCustomerNameValid: Boolean = false,
    val isPhoneNumberValid: Boolean = false,
    val isAlternativePhoneNumberValid: Boolean = false,
    val isImageAttached: Boolean = false,
    val isBusinessCategorySelected: Boolean = false,
    val isCallStatusSelected: Boolean = false,
    val isLeadStatusSelected: Boolean = false,
    val isFollowUpDateSelected: Boolean = false,
    val isFollowUpTimeSelected: Boolean = false,
    val isFollowUpActionSelected: Boolean = false
)

class CreateScreenViewModel(context: Context, private val userID: Long, private val itemId: Long?) :
    ViewModel() {
    private val _state = MutableStateFlow(CreateScreenState())
    val state: StateFlow<CreateScreenState> = _state.asStateFlow()
    val createScreendb = CreateScreenDB(context)

    @SuppressLint("StaticFieldLeak")
    val ctx1 = context
    private var fieldsPopulated = false


    private val db = CreateScreenDB(context)

    private val _showToast = MutableStateFlow<String?>(null)
    val showToast = _showToast.asStateFlow()

    fun updateCustomerName(name: String) {
        _state.update {
            it.copy(
                customerName = name,
                isCustomerNameValid = name.isBlank()
            )
        }
    }

    fun updatePhoneNumber(number: String) {
        val maxLength = 10
        val filteredNumber = number.filter { it.isDigit() } // Remove special characters
        val truncatedNumber = filteredNumber.take(maxLength)

        val isPhoneNumberValid = truncatedNumber.length == maxLength &&
                truncatedNumber.firstOrNull()?.let { it.isDigit() && it < '6' } == true

        _state.update {
            it.copy(
                phoneNumber = truncatedNumber,
                isPhoneNumberValid = isPhoneNumberValid
            )
        }

    }


    fun updateAlternatePhoneNumber(number: String) {
        val maxLength = 10
        val filteredNumber = number.filter { it.isDigit() } // Remove special characters
        val truncatedNumber = filteredNumber.take(maxLength)

        val isPhoneNumberValid = truncatedNumber.length == maxLength &&
                truncatedNumber.firstOrNull()?.let { it.isDigit() && it < '6' } == true
        _state.update {
            it.copy(
                alternatePhoneNumber = truncatedNumber,
                isAlternativePhoneNumberValid = isPhoneNumberValid
            )
        }
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
        _state.update { it.copy(followUpDate = date, isFollowUpDateSelected = date.isBlank()) }
    }

    fun updateFollowUpTime(time: String) {
        _state.update { it.copy(followUpTime = time, isFollowUpTimeSelected = time.isBlank()) }
    }

    fun toggleFollowUpActionCall() {
        _state.update {
            it.copy(
                followUpActionCall = true,
                followUpActionVisit = false
            )
        }
    }

    fun toggleFollowUpActionVisit() {
        _state.update {
            it.copy(
                followUpActionCall = false,
                followUpActionVisit = true
            )
        }
    }

    fun updateComments(comments: String) {
        _state.update { it.copy(comments = comments) }
    }

    fun updateProofImage(image: String) {
        _state.update { it.copy(proofImage = image) }
    }

    fun updateLongitudeLocation(longitude: String) {
        _state.update { it.copy(longitudeLocation = longitude) }
    }

    fun updateLatitudeLocation(latitude: String) {
        _state.update { it.copy(latitudeLocation = latitude) }
    }

    fun isValidInput(state: CreateScreenState): Boolean {
        // Implement your validation logic here
        // For example, check if required fields are not empty
        return state.customerName.isNotEmpty() && (state.phoneNumber.isNotEmpty() || state.phoneNumber.length < 10) &&
                (state.followUpDate.isNotEmpty() || state.followUpTime.isNotEmpty()) && state.proofImage.isNotEmpty() && (state.followUpActionVisit || state.followUpActionCall)
    }

    init {
        itemId?.let {
            fetchExistingRecord(context, it)
        }
    }

    @SuppressLint("Range")
    fun fetchExistingRecord(context: Context, itemId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = CreateScreenDB(context).readableDatabase
            val cursor =
                db.rawQuery(
                    "SELECT * FROM $CREATE_TABLE_NAME WHERE $ID_COL = ?",
                    arrayOf(itemId.toString())
                )

            val screeData = if (cursor.moveToFirst()) {
                val id = cursor.getLong(cursor.getColumnIndex(ID_COL))
                val stringValue = cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME_COL))
                val phoneNumber = cursor.getString(cursor.getColumnIndex(PHONE_NUMBER_COL))
                val alternatePhoneNumber =
                    cursor.getString(cursor.getColumnIndex(ALTERNATE_PHONE_COL))
                val address = cursor.getString(cursor.getColumnIndex(ADDRESS_COL))
                val proofImage = cursor.getString(cursor.getColumnIndex(PROOF_IMAGE_COL))
                val businessCategory =
                    cursor.getString(cursor.getColumnIndex(BUSINESS_CATEGORY_COL))
                val callStatus = cursor.getString(cursor.getColumnIndex(CALL_STATUS_COL))
                val leadStatus = cursor.getString(cursor.getColumnIndex(LEAD_STATUS_COL))
                val followUpDate = cursor.getString(cursor.getColumnIndex(FOLLOW_UP_DATE_COL))
                val followUpTime = cursor.getString(cursor.getColumnIndex(FOLLOW_UP_TIME_COL))
                val followUpActionCall =
                    cursor.getInt(cursor.getColumnIndex(FOLLOW_UP_ACTION_CALL_COL))
                val followUpActionVisit =
                    cursor.getInt(cursor.getColumnIndex(FOLLOW_UP_ACTION_VISIT_COL))
                val comments = cursor.getString(cursor.getColumnIndex(COMMENTS_COL))

                ScreenData1(
                    id,
                    stringValue,
                    phoneNumber,
                    alternatePhoneNumber,
                    proofImage,
                    address,
                    businessCategory,
                    callStatus,
                    leadStatus,
                    followUpDate,
                    followUpTime,
                    followUpActionCall,
                    followUpActionVisit,
                    comments
                )
            } else {
                null
            }.also { cursor.close(); db.close() }

            if (screeData != null) {
                populateFields(screeData)
            } else {
//                // Handle the case where the record is not found
//                _showToast.emit("Record not found")

            }
        }
    }


    private fun populateFields(existingItem: ScreenData1) {
        if (!fieldsPopulated) { // Only populate if not already done
            _state.value = state.value.copy(
                customerName = existingItem.stringValue,
                phoneNumber = existingItem.phoneNumber,
                alternatePhoneNumber = existingItem.alternatePhoneNumber,
                proofImage = existingItem.proofImage,
                address = existingItem.address,
                businessCategory = existingItem.businessCategory,
                callStatus = existingItem.callStatus,
                leadStatus = existingItem.leadStatus,
                followUpDate = existingItem.followUpDate,
                followUpTime = existingItem.followUpTime,
                followUpActionCall = existingItem.followUpActionCall == 1,
                followUpActionVisit = existingItem.followUpActionVisit == 1,
                comments = existingItem.comments
            )
            fieldsPopulated = true // Set the flag to true after populating fields
        }
    }

    fun saveFST(
        userId: Long,
        customerName: String?,
        phoneNumber: String?,
        alternatePhoneNumber: String?,
        address: String?,
        businessCategory: String?,
        callStatus: String?,
        leadStatus: String?,
        followUpDate: String?,
        followUpTime: String?,
        followUpActionCall: Int,
        followUpActionVisit: Int,
        comments: String?,
        proofImage: String?,
        longitudeLocation: String?,
        latitudeLocation: String?
    ) {
        val stateValue = _state.value
        if (isValidInput(stateValue)) {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }
                var isSuccess = false

                try {
                    if (itemId != null && itemId != 0L) {
                        // Update the existing record
                        isSuccess = createScreendb.updateFST(
                            itemId = itemId,
                            userId =userId,
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
                            comments = comments,
                            proofImage = proofImage,
                            longitudeLocation = longitudeLocation,
                            latitudeLocation = latitudeLocation
                        )
                    } else {
                        // Create a new record
                        isSuccess = createScreendb.createFST(
                            userId = userId,
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
                            comments = comments,
                            proofImage = proofImage,
                            longitudeLocation = longitudeLocation,
                            latitudeLocation = latitudeLocation
                        )
                    }
                } catch (e: Exception) {
                    Toast.makeText(ctx1, "An error occurred: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                } finally {
                    _state.update { it.copy(isLoading = false, isSubmissionSuccessful = isSuccess) }
                }
//
//                if (isSuccess) {
//                    Toast.makeText(ctx1, "Operation successful", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(ctx1, "Operation failed", Toast.LENGTH_SHORT).show()
//                }
            }
        } else {
            updateValidationState(stateValue)
            Toast.makeText(ctx1, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateValidationState(stateValue: CreateScreenState) {
        _state.update {
            it.copy(
                isCustomerNameValid = stateValue.customerName.isNullOrBlank(),
                isPhoneNumberValid = stateValue.phoneNumber.isNullOrBlank() || stateValue.phoneNumber.length<10,
                isImageAttached = stateValue.proofImage.isNullOrBlank() || stateValue.alternatePhoneNumber.length<10,
                isBusinessCategorySelected = stateValue.businessCategory.isNullOrBlank(),
                isCallStatusSelected = stateValue.callStatus.isNullOrBlank(),
                isLeadStatusSelected = stateValue.leadStatus.isNullOrBlank(),
                isFollowUpDateSelected = stateValue.followUpDate.isNullOrBlank(),
                isFollowUpTimeSelected = stateValue.followUpTime.isNullOrBlank(),
                isFollowUpActionSelected = !stateValue.followUpActionVisit && !stateValue.followUpActionCall
            )
        }
    }


}