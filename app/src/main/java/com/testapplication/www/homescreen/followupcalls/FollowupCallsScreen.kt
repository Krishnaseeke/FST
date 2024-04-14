package com.testapplication.www.homescreen.followupcalls

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
fun FollowupCallsScreen(
    toOnboarding: () -> Unit,
    toHome: ()->Unit,
    toLeadsScreen: ()-> Unit,
    toScheduledVisits: ()-> Unit,
    context: Context,
    modifier: Modifier = Modifier
){
    Column {
        displayList()
    }


    Column(modifier = Modifier.padding(2.dp), Arrangement.Bottom) {


        BottomBar(
            currentScreen = "Follow up Calls",
            toOnboarding = { toOnboarding() },
            toLeadsScreen={toLeadsScreen()},
            toHome = { toHome() },
            toScheduledVisits = { toScheduledVisits() },
            toFollowupCalls = {  }) {

        }
    }
}