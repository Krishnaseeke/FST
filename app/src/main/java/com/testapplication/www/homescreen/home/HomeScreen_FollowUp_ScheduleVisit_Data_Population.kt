package com.testapplication.www.homescreen.home

import CreateScreenDB
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.testapplication.www.R
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.util.AllowSettingPopup
import com.testapplication.www.util.SelectedDateItemRow
import com.testapplication.www.util.constants.Constants
import com.testapplication.www.util.constants.Constants.CHECKIN_LATITUDE_COL
import com.testapplication.www.util.constants.Constants.CHECKIN_LONGITUDE_COL
import com.testapplication.www.util.constants.Constants.CREATE_TABLE_NAME
import com.testapplication.www.util.constants.Constants.CUSTOMER_NAME_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_ACTION_CALL_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_ACTION_VISIT_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_CALL_LIST_TYPE
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_DATE_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_TIME_COL
import com.testapplication.www.util.constants.Constants.ID_COL
import com.testapplication.www.util.constants.Constants.INVALID_LIST_TYPE
import com.testapplication.www.util.constants.Constants.LEADS_LIST_TYPE
import com.testapplication.www.util.constants.Constants.LEAD_STATUS_COL
import com.testapplication.www.util.constants.Constants.NO_DATA_FOUND_IMAGE_DESCRIPTION
import com.testapplication.www.util.constants.Constants.SCHEDULED_VISIT_LIST_TYPE
import com.testapplication.www.util.constants.Constants.SPECIFIC_ITEM_LIST
import com.testapplication.www.util.constants.Constants.USER_ID_COL
import java.time.LocalTime
import java.time.format.DateTimeFormatter


data class ScreenData1(
    val id: Long,
    val stringValue: String,
    val phoneNumber: String,
    val alternatePhoneNumber: String,
    val proofImage: String,
    val address: String,
    val businessCategory: String,
    val callStatus: String,
    val leadStatus: String,
    val followUpDate: String,
    val followUpTime: String,
    val followUpActionCall: Int,
    val followUpActionVisit: Int,
    val comments: String
)

data class ScreenData(
    val id: Long,
    val date: String,
    val time: String,
    val stringValue: String,
    val leadStatus: String,
    val longitudeValue: String,
    val latitudeValue: String
)


@Composable
fun DisplayList(
    context: Context,
    userId: Long,
    itemId: Long?, // Make itemId nullable
    selectedDate: String?,
    valueType: String,
    toCreationLedger: (userId: Long, itemId: Long) -> Unit,
    toCreate: (userId: Long, itemId: Long) -> Unit
) {

    val preferencesManager = PreferencesManager(context)

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var currentLatitude by remember { mutableStateOf(0.0) }
    var currentLongitude by remember { mutableStateOf(0.0) }
    val dist = FloatArray(1)

    val itemData = fetchDataFromDB(context, userId, valueType, itemId) // Pass itemId here

    var dataListDisplay: ArrayList<ScreenData> = ArrayList()
    if (valueType == SCHEDULED_VISIT_LIST_TYPE) {
        dataListDisplay.addAll(itemData)
    } else if (valueType == FOLLOW_UP_CALL_LIST_TYPE) {
        dataListDisplay.addAll(itemData)
    } else if (valueType == LEADS_LIST_TYPE) {
        dataListDisplay.addAll(itemData)
    } else if (valueType == SPECIFIC_ITEM_LIST && itemId != null) {
        dataListDisplay.addAll(itemData)
    }

    val sortedDataListDisplay = dataListDisplay.sortedWith(
        compareBy({ it.date }, { LocalTime.parse(it.time, DateTimeFormatter.ofPattern("h:mm a")) })
    )
    var showAlert by remember { mutableStateOf(Constants.DEFAULT_ALERT_POP_UP) }

    if (showAlert) {
        AllowSettingPopup(
            context = context,
            showDialog = showAlert,
            onDismiss = { showAlert = Constants.DEFAULT_ALERT_POP_UP },
            title = Constants.GENERAL_ALERT_TITLE,
            description = Constants.CHECK_IN_EDIT_ALERT_DESCRIPTION,
            confirmButtonText = Constants.GENERAL_ALERT_ALLOW_CTA,
            onConfirm = { /* Handle confirmation if needed */ }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        if (dataListDisplay.isEmpty()) {
            Image(
                painter = painterResource(id = R.mipmap.nodatafound_foreground),
                contentDescription = NO_DATA_FOUND_IMAGE_DESCRIPTION,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(1.dp)
        ) {

            items(sortedDataListDisplay) { screenData ->
                if (selectedDate == null || selectedDate == "") {
                    SelectedDateItemRow(
                        screenData = screenData,
                        userId = userId,
                        toCreate = toCreate,
                        toCreationLedger = toCreationLedger,
                        preferencesManager = preferencesManager,
                        showAlert = showAlert,
                        setShowAlert = { newValue -> showAlert = newValue },
                        fusedLocationClient = fusedLocationClient,
                        context = context,
                        currentLatitude = currentLatitude,
                        currentLongitude = currentLongitude,
                        setCurrentLatitude = { newLat -> currentLatitude = newLat },
                        setCurrentLongitude = { newLong -> currentLongitude = newLong },
                        valueType = valueType
                    )
                } else if (screenData.date == selectedDate) {
                    SelectedDateItemRow(
                        screenData = screenData,
                        userId = userId,
                        toCreate = toCreate,
                        toCreationLedger = toCreationLedger,
                        preferencesManager = preferencesManager,
                        showAlert = showAlert,
                        setShowAlert = { newValue -> showAlert = newValue },
                        fusedLocationClient = fusedLocationClient,
                        context = context,
                        currentLatitude = currentLatitude,
                        currentLongitude = currentLongitude,
                        setCurrentLatitude = { newLat -> currentLatitude = newLat },
                        setCurrentLongitude = { newLong -> currentLongitude = newLong },
                        valueType = valueType
                    )
                }
            }

        }
    }
}



@SuppressLint("Range")
private fun fetchDataFromDB(context: Context, userId: Long, valueType: String, itemId: Long?): List<ScreenData> {
    val db = CreateScreenDB(context).readableDatabase
    val query: String
    val selectionArgs: Array<String>

    when (valueType) {
        SCHEDULED_VISIT_LIST_TYPE -> {
            query =
                "SELECT * FROM $CREATE_TABLE_NAME WHERE $USER_ID_COL = ? AND $FOLLOW_UP_ACTION_VISIT_COL = 1"
            selectionArgs = arrayOf(userId.toString())
        }

        FOLLOW_UP_CALL_LIST_TYPE -> {
            query =
                "SELECT * FROM $CREATE_TABLE_NAME WHERE $USER_ID_COL = ? AND $FOLLOW_UP_ACTION_CALL_COL = 1"
            selectionArgs = arrayOf(userId.toString())
        }

        LEADS_LIST_TYPE -> {
            query =
                "SELECT * FROM $CREATE_TABLE_NAME WHERE $USER_ID_COL = ? AND $LEAD_STATUS_COL IN (?, ?, ?, ?)"
            selectionArgs = arrayOf(
                userId.toString(),
                "First Meet",
                "Requires Follow Up",
                "Interested",
                "Demo Re-scheduled"
            )
        }

        SPECIFIC_ITEM_LIST -> {
            if (itemId == null) throw IllegalArgumentException("Item ID must be provided for ITEM type")
            query =
                "SELECT * FROM $CREATE_TABLE_NAME WHERE $USER_ID_COL = ? AND $ID_COL = ?"
            selectionArgs = arrayOf(userId.toString(), itemId.toString())
        }

        else -> throw IllegalArgumentException(INVALID_LIST_TYPE)
    }

    val cursor = db.rawQuery(query, selectionArgs)
    val data = mutableListOf<ScreenData>()
    with(cursor) {
        while (moveToNext()) {
            val id = getLong(getColumnIndex(ID_COL))
            val date = getString(getColumnIndex(FOLLOW_UP_DATE_COL))
            val time = getString(getColumnIndex(FOLLOW_UP_TIME_COL))
            val stringValue = getString(getColumnIndex(CUSTOMER_NAME_COL))
            val leadStatus = getString(getColumnIndex(LEAD_STATUS_COL))
            val longitudeValue = getString(getColumnIndex(CHECKIN_LONGITUDE_COL))
            val latitudeValue = getString(getColumnIndex(CHECKIN_LATITUDE_COL))
            data.add(
                ScreenData(
                    id,
                    date,
                    time,
                    stringValue,
                    leadStatus,
                    longitudeValue,
                    latitudeValue
                )
            )
        }
    }
    cursor.close()
    db.close()

    return data
}



