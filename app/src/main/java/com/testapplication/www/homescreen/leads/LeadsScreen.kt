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
    toHome: ()->Unit,
    toFollowupCalls: ()-> Unit,
    toScheduledVisits: ()-> Unit,
    context: Context,
    modifier: Modifier = Modifier
){


    Column(modifier = Modifier.padding(2.dp), Arrangement.Bottom) {
        BottomBar(
            currentScreen = "Leads",
            toOnboarding = { toOnboarding() },
            toLeadsScreen={},
            toHome = { toHome() },
            toScheduledVisits = { toScheduledVisits() },
            toFollowupCalls = { toFollowupCalls() }) {

        }
    }

}