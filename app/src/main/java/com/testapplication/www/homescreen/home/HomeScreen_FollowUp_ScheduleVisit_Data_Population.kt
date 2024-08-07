package com.testapplication.www.homescreen.home

import CreateScreenDB
import CreateScreenViewModel
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.testapplication.www.R
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.util.ActionType
import com.testapplication.www.util.HeaderText
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_CALL_LIST_TYPE
import com.testapplication.www.util.constants.Constants.SCHEDULED_VISIT_LIST_TYPE
import getLastLocation
import java.util.logging.Logger


data class ScreenData1(
    val id: Long,
    val stringValue: String,
    val phoneNumber: String,
    val alternatePhoneNumber: String,
    val proofImage:String,
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


private const val DB_NAME = "create_screen_db"
private const val DB_VERSION = 6
private const val TABLE_NAME = "create_screen_data"
private const val ID_COL = "id"
private const val USER_ID_COL = "user_id"
private const val CUSTOMER_NAME_COL = "customer_name"
private const val PHONE_NUMBER_COL = "phone_number"
private const val ALTERNATE_PHONE_COL = "alternate_phone_number"
private const val ADDRESS_COL = "address"
private const val BUSINESS_CATEGORY_COL = "business_category"
private const val CALL_STATUS_COL = "call_status"
private const val LEAD_STATUS_COL = "lead_status"
private const val FOLLOW_UP_DATE_COL = "follow_up_date"
private const val FOLLOW_UP_TIME_COL = "follow_up_time"
private const val FOLLOW_UP_ACTION_CALL_COL = "follow_up_action_call"  // Change column name
private const val FOLLOW_UP_ACTION_VISIT_COL = "follow_up_action_visit"  // Change column name
private const val COMMENTS_COL = "comments"
private const val CHECKIN_LONGITUDE_COL = "longitude_location" // New Column
private const val CHECKIN_LATITUDE_COL = "latitude_location" // New Column

@Composable
fun displayList(
    context: Context,
    userId: Long,
    selectedDate: String?,
    valueType: String,
    toCreate: (userId: Long, itemId: Long) -> Unit
) {
    val userId = userId
    val preferencesManager = PreferencesManager(context)

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var currentLatitude by remember { mutableStateOf(0.0) }
    var itemLatitude by remember { mutableStateOf(0.0) }
    var currentLongitude by remember { mutableStateOf(0.0) }
    var itemLongitude by remember { mutableStateOf(0.0) }
    val dist = FloatArray(1)



    val followUpActionCallData = fetchDataFromDB(context, userId, valueType)
    val followUpActionVisitData = fetchDataFromDB(context, userId, valueType)
    var dataListDisplay: ArrayList<ScreenData> = ArrayList()
    if (valueType == "visit") {
        dataListDisplay.addAll(followUpActionVisitData)
    } else if (valueType == "call") {
        dataListDisplay.addAll(followUpActionCallData)
    }
//    else if(selectedDate!=null){
//        dataListDisplay = dateRangeData
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        if(dataListDisplay.isEmpty()){
            Image(
                painter = painterResource(id = R.mipmap.nodatafound_foreground),
                contentDescription = "No data found",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp)
        ) {


            items(dataListDisplay) { screenData ->
                if (selectedDate == null || selectedDate == "") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (preferencesManager.getCheckInStatus(false)) {
                                    toCreate.invoke(userId, screenData.id)
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "Please Check-In to Edit FST",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Row {
                                    Text(
                                        text = screenData.stringValue + " |",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
//                                    text = if (screenData.leadStatus.contains("First Meet") ||
//                                        screenData.leadStatus.contains("Requires Follow Up") ||
//                                        screenData.leadStatus.contains("Interested") ||
//                                        screenData.leadStatus.contains("Demo Re-scheduled")) {
//                                        screenData.leadStatus
//                                    } else {
//                                        // If lead status doesn't contain any of the specified values, don't display it
//                                        ""
//                                    },
                                        text = screenData.leadStatus,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(start = 5.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(5.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.AddCircle, contentDescription = "Add icon")
                                    Text(
                                        text = screenData.time,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(start = 5.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Icon(Icons.Default.DateRange, contentDescription = "Date icon")
                                    Text(
                                        text = screenData.date,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(start = 5.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))


                                }
                            }


                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Navigate",
                                modifier = Modifier

                            )
                        }

                        Divider(modifier = Modifier.padding(top = 2.dp))
                    }
                } else if (screenData.date == selectedDate) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (preferencesManager.getCheckInStatus(false)) {
                                    //Get the location before Comparsion in if Condition
                                    getLastLocation(fusedLocationClient, context) { location ->
                                        currentLongitude = location.longitude
                                        currentLatitude = location.latitude
                                    }
//                                    itemLatitude = screenData.latitudeValue.toDouble()
//                                    itemLongitude = screenData.longitudeValue.toDouble()
                                    //Get the Location of each item from DB in Display list
                                    //Use the Distance Range to go for edit
                                    Location.distanceBetween(
                                        currentLatitude,
                                        currentLongitude,
                                        screenData.latitudeValue.toDouble(),
                                        screenData.longitudeValue.toDouble(),
                                        dist
                                    )
                                    val RADIUS_IN_METER = 0.5

                                    if (dist[0] / 1000 > RADIUS_IN_METER) {
                                        Toast
                                            .makeText(
                                                context,
                                                "Please  in the Range to Edit the Account",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    } else {
                                        toCreate.invoke(userId, screenData.id)
                                    }
                                } else {
                                    Toast
                                        .makeText(
                                            context,
                                            "Please Check-In to Edit FST",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                }
                            }
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Row {
                                    Text(
                                        text = screenData.stringValue + " |",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
//                                    text = if (screenData.leadStatus.contains("First Meet") ||
//                                        screenData.leadStatus.contains("Requires Follow Up") ||
//                                        screenData.leadStatus.contains("Interested") ||
//                                        screenData.leadStatus.contains("Demo Re-scheduled")) {
//                                        screenData.leadStatus
//                                    } else {
//                                        // If lead status doesn't contain any of the specified values, don't display it
//                                        ""
//                                    },
                                        text = screenData.leadStatus,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(start = 5.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(5.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.AddCircle, contentDescription = "Add icon")
                                    Text(
                                        text = screenData.time,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(start = 5.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Icon(Icons.Default.DateRange, contentDescription = "Date icon")
                                    Text(
                                        text = screenData.date,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Normal,
                                        modifier = Modifier.padding(start = 5.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))


                                }
                            }


                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Navigate",
                                modifier = Modifier,

                                )
                        }

                        Divider(modifier = Modifier.padding(top = 2.dp))
                    }
                }
            }
        }
    }
}


@SuppressLint("Range")
private fun fetchDataFromDB(context: Context, userId: Long, valueType: String): List<ScreenData> {
    val db = CreateScreenDB(context).readableDatabase
    val followUpAction = when (valueType) {
        SCHEDULED_VISIT_LIST_TYPE -> FOLLOW_UP_ACTION_VISIT_COL
        FOLLOW_UP_CALL_LIST_TYPE -> FOLLOW_UP_ACTION_CALL_COL
        else -> throw IllegalArgumentException("Invalid valueType")
    }
    val cursor = db.rawQuery(
        "SELECT * FROM $TABLE_NAME WHERE $USER_ID_COL = ? AND $followUpAction = 1",
        arrayOf(userId.toString())
    )

    val data = mutableListOf<ScreenData>()
    with(cursor) {
        while (moveToNext()) {
            val id = getLong(getColumnIndex(ID_COL)) // Fetch ID_COL
            val date = getString(getColumnIndex(FOLLOW_UP_DATE_COL))
            val time = getString(getColumnIndex(FOLLOW_UP_TIME_COL))
            val stringValue = getString(getColumnIndex(CUSTOMER_NAME_COL))
            val leadStatus = getString(getColumnIndex(LEAD_STATUS_COL)) // Fetch leadStatus
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
            ) // Pass leadStatus to ScreenData
        }
    }
    cursor.close()
    db.close()

    return data
}



