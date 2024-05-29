package com.testapplication.www.homescreen.scheduledvisits

import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.homescreen.bottomnavigation.BottomBar
import com.testapplication.www.util.convertDateToString
import com.testapplication.www.util.setCustomDate
import java.util.Date

@Composable
fun ScheduledVisitsScreen(
    toOnboarding: () -> Unit,
    toHome: (Any?) -> Unit,
    toFollowupCalls: (Any?) -> Unit,
    toLeadsScreen: (Any?) -> Unit,
    context: Context,
    userID:Long,
    toCreate: (Long?,Long?) -> Unit,
    modifier: Modifier = Modifier
){
    val preferencesManager = PreferencesManager(context)
    var dateSelected: String? = null

    Column(modifier = Modifier.background(Color.LightGray)) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Scheduled Visits",
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .clickable {
                            preferencesManager.clearPreferences()
                            toOnboarding()
                        }
                        .padding(10.dp)
                )

                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = "Back",
                    modifier = Modifier
                        .clickable { }
                        .padding(10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(modifier = Modifier
            .wrapContentHeight()
            .background(Color.White)
            .fillMaxWidth(),
            Arrangement.Top,
            Alignment.Start) {
            dateSelected= setCustomDate()
        }
        Spacer(modifier = Modifier.height(5.dp))

        Column( modifier = Modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .background(Color.White)
            .width(500.dp)
            .padding(start = 1.dp, top = 0.dp, bottom = 15.dp, end = 1.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)) {
            if (userID != null) {
                com.testapplication.www.homescreen.home.displayList(
                    context = context,
                    userId = userID,
                    dateSelected,
                    valueType = "visit",
                ) { userId, itemId ->
                    // Here you can define what you want to do with userId and itemId
                    // For example, you can navigate to another screen or perform some action
                    toCreate.invoke(userId,itemId)
                }
            }
        }
    }
    Column(modifier = Modifier, Arrangement.Bottom) {

        BottomBar(
            currentScreen = "Scheduled visits",
            toOnboarding = { toOnboarding() },
            toLeadsScreen={toLeadsScreen(userID)},
            toHome = { toHome(userID) },
            toScheduledVisits = { },
            toFollowupCalls = { toFollowupCalls(userID) }) {

        }
    }
}
