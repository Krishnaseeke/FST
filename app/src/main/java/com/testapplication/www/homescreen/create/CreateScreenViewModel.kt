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
    val isCustomerNameValid:Boolean = false,
    val isPhoneNumberValid: Boolean = false,
    val isImageAttached: Boolean = false,
    val isBusinessCategorySelected:Boolean = false,
    val isCallStatusSelected: Boolean = false,
    val isLeadStatusSelected: Boolean = false,
    val isFollowUpDateSelected: Boolean = false,
    val isFollowUpTimeSelected: Boolean= false,
    val isFollowUpActionSelected: Boolean = false
)

class CreateScreenViewModel(context: Context, private val userID: Long, private val itemId: Long?) :
    ViewModel() {
    private val _state = MutableStateFlow(CreateScreenState())
    val state: StateFlow<CreateScreenState> = _state.asStateFlow()
    val createScreendb = CreateScreenDB(context)
    @SuppressLint("StaticFieldLeak")
    val ctx1 = context
    val value1:Long = 0
    private var fieldsPopulated = false


    private val DB_NAME = "create_screen_db"
    private val DB_VERSION = 6
    private val TABLE_NAME = "create_screen_data"
    private val ID_COL = "id"
    private val USER_ID_COL = "user_id"
    private val CUSTOMER_NAME_COL = "customer_name"
    private val PHONE_NUMBER_COL = "phone_number"
    private val ALTERNATE_PHONE_COL = "alternate_phone_number"
    private val ADDRESS_COL = "address"
    private val PROOF_IMAGE_COL = "proof_image"
    private val BUSINESS_CATEGORY_COL = "business_category"
    private val CALL_STATUS_COL = "call_status"
    private val LEAD_STATUS_COL = "lead_status"
    private val FOLLOW_UP_DATE_COL = "follow_up_date"
    private val FOLLOW_UP_TIME_COL = "follow_up_time"
    private val FOLLOW_UP_ACTION_CALL_COL = "follow_up_action_call"  // Change column name
    private val FOLLOW_UP_ACTION_VISIT_COL = "follow_up_action_visit"  // Change column name
    private val COMMENTS_COL = "comments"
    private val CHECKIN_TIME_COL = "checkin_time"
    private val CHECKIN_LOCATION_COL = "location" // New Column
    private val CHECKIN_IMAGE_COL = "checkin_image" // New Column


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
        _state.update { it.copy(phoneNumber = number, isPhoneNumberValid = number.isBlank()) }
    }

    fun updateAlternatePhoneNumber(number: String) {
        _state.update { it.copy(alternatePhoneNumber = number) }
    }

    fun updateAddress(address: String) {
        _state.update { it.copy(address = address) }
    }

    fun updateBusinessCategory(category: String) {
        _state.update { it.copy(businessCategory = category,) }
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
        _state.update { it.copy(followUpActionCall = !it.followUpActionCall) }
    }

    fun toggleFollowUpActionVisit() {
        _state.update { it.copy(followUpActionVisit = !it.followUpActionVisit) }
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
        return state.customerName.isNotEmpty() && state.phoneNumber.isNotEmpty() && state.leadStatus.isNotEmpty() &&
                (state.followUpDate.isNotEmpty() && state.followUpTime.isNotEmpty())
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
                db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $ID_COL = ?", arrayOf(itemId.toString()))

            val screeData =  if (cursor.moveToFirst()) {
                val id = cursor.getLong(cursor.getColumnIndex(ID_COL))
                val stringValue = cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME_COL))
                val phoneNumber = cursor.getString(cursor.getColumnIndex(PHONE_NUMBER_COL))
                val alternatePhoneNumber = cursor.getString(cursor.getColumnIndex(ALTERNATE_PHONE_COL))
                val address = cursor.getString(cursor.getColumnIndex(ADDRESS_COL))
                val proofImage = cursor.getString(cursor.getColumnIndex(PROOF_IMAGE_COL))
                val businessCategory = cursor.getString(cursor.getColumnIndex(BUSINESS_CATEGORY_COL))
                val callStatus = cursor.getString(cursor.getColumnIndex(CALL_STATUS_COL))
                val leadStatus = cursor.getString(cursor.getColumnIndex(LEAD_STATUS_COL))
                val followUpDate = cursor.getString(cursor.getColumnIndex(FOLLOW_UP_DATE_COL))
                val followUpTime = cursor.getString(cursor.getColumnIndex(FOLLOW_UP_TIME_COL))
                val followUpActionCall = cursor.getInt(cursor.getColumnIndex(FOLLOW_UP_ACTION_CALL_COL))
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
                // Handle the case where the record is not found
                _showToast.emit("Record not found")

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
                    if (itemId != null && itemId !=0L) {
                        // Update the existing record
                        isSuccess = createScreendb.updateFST(
                            itemId = itemId,
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

                if (isSuccess) {
                    Toast.makeText(ctx1, "Operation successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(ctx1, "Operation failed", Toast.LENGTH_SHORT).show()
                }
            }
        } else {

            Toast.makeText(ctx1, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
        }
    }


}