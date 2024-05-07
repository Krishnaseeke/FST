package com.testapplication.www.homescreen.home

import CreateScreenDB
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class ScreenData(
    val date: String,
    val time: String,
    val stringValue: String,
    val leadStatus: String
)

private const val DB_NAME = "create_screen_db"
private const val TABLE_NAME = "create_screen_data"
private const val USER_ID_COL = "user_id"
private const val CUSTOMER_NAME_COL = "customer_name"
private const val LEAD_STATUS_COL = "lead_status"
private const val FOLLOW_UP_DATE_COL = "follow_up_date"
private const val FOLLOW_UP_TIME_COL = "follow_up_time"
private const val FOLLOW_UP_ACTION_CALL_COL = "follow_up_action_call"
private const val FOLLOW_UP_ACTION_VISIT_COL = "follow_up_action_visit"

@Composable
fun displayList(context: Context,userId: Long,valueType:String) {
    val userId = userId



    val followUpActionCallData = fetchDataFromDB(context, userId, valueType)
    val followUpActionVisitData = fetchDataFromDB(context, userId,valueType)
    var dataListDisplay: List<ScreenData>? = null
    if (valueType == "visit") {
        dataListDisplay = followUpActionVisitData
    } else if (valueType == "call") {
        dataListDisplay = followUpActionCallData
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(dataListDisplay ?: emptyList()) { screenData ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                                .clickable { /* Handle navigation */ }
                        )
                    }

                    Divider(modifier = Modifier.padding(top = 2.dp))
                }
            }
        }
    }
}
@SuppressLint("Range")
private fun fetchDataFromDB(context: Context, userId: Long, valueType: String): List<ScreenData> {
    val db = CreateScreenDB(context).readableDatabase
    val followUpAction = when (valueType) {
        "visit" -> FOLLOW_UP_ACTION_VISIT_COL
        "call" -> FOLLOW_UP_ACTION_CALL_COL
        else -> throw IllegalArgumentException("Invalid valueType")
    }
    val cursor = db.rawQuery(
        "SELECT * FROM $TABLE_NAME WHERE $USER_ID_COL = ? AND $followUpAction = 1",
        arrayOf(userId.toString())
    )

    val data = mutableListOf<ScreenData>()
    with(cursor) {
        while (moveToNext()) {
            val date = getString(getColumnIndex(FOLLOW_UP_DATE_COL))
            val time = getString(getColumnIndex(FOLLOW_UP_TIME_COL))
            val stringValue = getString(getColumnIndex(CUSTOMER_NAME_COL))
            val leadStatus = getString(getColumnIndex(LEAD_STATUS_COL)) // Fetch leadStatus
            data.add(
                ScreenData(
                    date,
                    time,
                    stringValue,
                    leadStatus
                )
            ) // Pass leadStatus to ScreenData
        }
    }
    cursor.close()
    db.close()

    return data
}
