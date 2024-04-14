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
    toHome: ()->Unit,
    toFollowupCalls: ()-> Unit,
    toLeadsScreen: ()-> Unit,
    context: Context,
    modifier: Modifier = Modifier
){
    Column {
        displayList()
    }
    Column(modifier = Modifier.padding(2.dp), Arrangement.Bottom) {

        BottomBar(
            currentScreen = "Scheduled visits",
            toOnboarding = { toOnboarding() },
            toLeadsScreen={toLeadsScreen()},
            toHome = { toHome() },
            toScheduledVisits = { },
            toFollowupCalls = { toFollowupCalls() }) {

        }
    }
}