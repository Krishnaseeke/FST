package com.testapplication.www.homescreen.scheduledvisits

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.testapplication.www.homescreen.bottomnavigation.BottomBar
import com.testapplication.www.util.displayList

@Composable
fun ScheduledVisitsScreen(
    toOnboarding: () -> Unit,
    toHome: (Any?) -> Unit,
    toFollowupCalls: (Any?) -> Unit,
    toLeadsScreen: (Any?) -> Unit,
    context: Context,
    userID:Long,
    modifier: Modifier = Modifier
){
    Column {
        displayList()
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