package com.testapplication.www.homescreen.followupcalls

import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun FollowupCallsScreen(
    toOnboarding: () -> Unit,
    toHome: (Any?) -> Unit,
    toLeadsScreen: (Any?) -> Unit,
    toScheduledVisits: (Any?) -> Unit,
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
                    text = "Follow Up Calls",
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
            .fillMaxWidth(1f)
            .padding(start = 1.dp, top = 0.dp, bottom = 15.dp, end = 1.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)) {
            if (userID != null) {
                com.testapplication.www.homescreen.home.displayList(
                    context = context,
                    userId = userID,
                    dateSelected,
                    valueType = "call",
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
            currentScreen = "Follow up Calls",
            toOnboarding = { toOnboarding() },
            toLeadsScreen={toLeadsScreen(userID)},
            toHome = { toHome(userID) },
            toScheduledVisits = { toScheduledVisits(userID) },
            toFollowupCalls = {  }) {

        }
    }
}