package com.testapplication.www.homescreen.leads

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.testapplication.www.homescreen.bottomnavigation.BottomBar

@Composable
fun LeadScreen(
    toOnboarding: () -> Unit,
    toHome: (Any?) -> Unit,
    toFollowupCalls: (Any?) -> Unit,
    toScheduledVisits: (Any?) -> Unit,
    userID: Long,
    context: Context,
    modifier: Modifier = Modifier
) {


    Column(modifier = Modifier, Arrangement.Bottom) {
        BottomBar(
            currentScreen = "Leads",
            toOnboarding = { toOnboarding() },
            toLeadsScreen = {},
            toHome = { toHome(userID) },
            toScheduledVisits = { toScheduledVisits(userID) },
            toFollowupCalls = { toFollowupCalls(userID) }) {

        }
    }

}