package com.testapplication.www.homescreen.followupcalls

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.homescreen.bottomnavigation.BottomBar
import com.testapplication.www.util.ActionType
import com.testapplication.www.util.LogoutOrExitScreen
import com.testapplication.www.util.ScreenHeaders
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_CALL_LIST_TYPE
import com.testapplication.www.util.constants.Constants.SCREEN_FOLLOW_UP_CALLS
import com.testapplication.www.util.setCustomDate

@Composable
fun FollowupCallsScreen(
    toOnboarding: () -> Unit,
    toHome: (Any?) -> Unit,
    toLeadsScreen: (Any?) -> Unit,
    toScheduledVisits: (Any?) -> Unit,
    toCreationLedger: (Long, Long) -> Unit,
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
                ScreenHeaders(SCREEN_FOLLOW_UP_CALLS)
                Spacer(modifier = Modifier.weight(1f))
                LogoutOrExitScreen(preferencesManager, toOnboarding, ActionType.LOGOUT)
                LogoutOrExitScreen(preferencesManager, toOnboarding, ActionType.EXIT)
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
        Column( modifier = Modifier.weight(1f)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(Color.White)
            .fillMaxWidth(1f)
            .padding(start = 1.dp, top = 0.dp, bottom = 15.dp, end = 1.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)) {
            if (userID != null) {
                com.testapplication.www.homescreen.home.DisplayList(
                    context = context,
                    userId = userID,
                    itemId = 0L,
                    dateSelected,
                    valueType = FOLLOW_UP_CALL_LIST_TYPE,
                    toCreationLedger = { userId, itemId ->
                        toCreationLedger(userId, itemId)
                    },
                    toCreate = { userId, itemId ->
                        toCreate(userId, itemId)
                    }
                )
            }
        }
        Column(modifier = Modifier, Arrangement.Bottom) {
            BottomBar(
                currentScreen = SCREEN_FOLLOW_UP_CALLS,
                toOnboarding = { toOnboarding() },
                toLeadsScreen={toLeadsScreen(userID)},
                toHome = { toHome(userID) },
                toScheduledVisits = { toScheduledVisits(userID) },
                toFollowupCalls = {  }) {

            }
        }
    }


}