package com.testapplication.www.homescreen.bottomnavigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    currentScreen: String,
    toOnboarding: () -> Unit,
    toHome: () -> Unit,
    toScheduledVisits: () -> Unit,
    toFollowupCalls: () -> Unit,
    toLeadsScreen: () -> Unit,
    function: () -> Unit
) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(20.dp, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp), clip = false) // Stronger shadow with rounded corners
            .background(Color.White), // Ensure background is white
        containerColor = Color.Transparent // To allow shadow visibility
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(color = Color.White).fillMaxWidth(1f)
        ) {
            // Home Icon
            BottomNavigationItem(Icons.Default.Home, "Home", toHome, currentScreen == "Home")


            // Scheduled Visits Icon
            BottomNavigationItem(
                Icons.Default.DateRange,
                "Scheduled visits",
                toScheduledVisits,
                currentScreen == "Scheduled visits"
            )

            // Follow-up Calls Icon
            BottomNavigationItem(
                Icons.Default.Call,
                "Follow up Calls",
                toFollowupCalls,
                currentScreen == "Follow up Calls"
            )

            // Leads Icon
            BottomNavigationItem(
                Icons.Default.Person,
                "Leads",
                toLeadsScreen,
                currentScreen == "Leads"
            )
        }
    }
}
@Composable
fun BottomNavigationItem(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit,
    isCurrentScreen: Boolean
) {
    val backgroundColor = if (isCurrentScreen) Color.Transparent else Color.Transparent
    val textColor = if (isCurrentScreen) Color.Black else Color.Gray

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick).background(color = Color.White)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = textColor, // Adjust the tint color of the icon
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
                .background(backgroundColor)
        )
        Text(
            text = description,
            color = textColor, // Set text color based on whether it's the current screen or not
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}
