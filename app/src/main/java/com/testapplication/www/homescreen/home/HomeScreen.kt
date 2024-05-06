package com.testapplication.www.homescreen.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testapplication.www.homescreen.bottomnavigation.BottomBar
import com.testapplication.www.util.displayList


@Composable
fun HomeScreen(
    toOnboarding: () -> Unit,
    toScheduledVisits: (Any?) -> Unit,
    toFollowupCalls: (Any?) -> Unit,
    toLeadsScreen: (Any?) -> Unit,
    userID: Long?,
    context: Context,
    toCreate: (Any?) -> Unit,
    modifier: Modifier = Modifier
) {
    var checked = false
    val ctx = LocalContext.current
    val homeScreenDB = HomeScreenDB(context)
    val leadsCreatedCount = homeScreenDB.getLeadsCreatedCount(userID)
    val demosScheduledCount = homeScreenDB.getDemosScheduledCount(userID)
    val demosCompletedCount = homeScreenDB.getDemosCompletedCount(userID)
    val licensesSoldCount = homeScreenDB.getLicensesSoldCount(userID)
    Column(modifier = Modifier.background(Color.LightGray)) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .padding(start = 10.dp, top = 10.dp, bottom = 15.dp)
                .fillMaxWidth(), Arrangement.Top, Alignment.Start
        ) {
            Text(
                text = "Home",
                color = Color.Black,
                fontSize = 25.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold

            )



        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .padding(start = 10.dp, top = 10.dp, bottom = 15.dp)
                .width(500.dp)
                .clip(shape = RoundedCornerShape(5.dp)),

            Arrangement.Top,
            Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Check-In",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(5.dp)
                )

                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                    },
                    colors = SwitchDefaults.colors(Color.LightGray)
                )
            }

        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .verticalScroll(rememberScrollState())
        ) {

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(start = 1.dp, top = 10.dp, bottom = 15.dp, end = 1.dp)
                    .width(500.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                // First Row
                Row(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f)
                    ) {
                        customTextHome(text = "Leads Created")
                        customValuesHome(text = leadsCreatedCount.toString())
                    }
                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(60.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f)
                    ) {
                        customTextHome(text = "Demos Scheduled")
                        customValuesHome(text = demosScheduledCount.toString())
                    }
                }
                // Divider after first row
                Divider(
                    modifier = Modifier.fillMaxWidth()
                )

                // Second Row
                Row(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f)
                    ) {
                        customTextHome(text = "Demos Completed")
                        customValuesHome(text = demosCompletedCount.toString())
                    }

                    Divider(
                        modifier = Modifier
                            .width(1.dp)
                            .height(60.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(10.dp))

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f)
                    ) {
                        customTextHome(text = "Licenses Sold")
                        customValuesHome(text = licensesSoldCount.toString())
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier
                    .height(350.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .width(500.dp)
                    .padding(start = 1.dp, top = 10.dp, bottom = 15.dp, end = 1.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                Row(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    customTextHome(
                        text = "Scheduled Visits",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ClickableText(text = buildAnnotatedString {
                        append("Show All")
                        withStyle(style = SpanStyle(color = Color.Blue)) {
                        }
                    }, onClick = {
                        toScheduledVisits(userID)
                    }, modifier = Modifier.padding(top = 10.dp))
                }

                displayList()

            }
            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .height(350.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(start = 1.dp, top = 10.dp, bottom = 15.dp, end = 1.dp)
                    .width(500.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    customTextHome(
                        text = "Follow Up Calls",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ClickableText(text = buildAnnotatedString {
                        append("Show All")
                        withStyle(style = SpanStyle(color = Color.Blue)) {
                        }
                    }, onClick = {
                        toFollowupCalls(userID)
                    }, modifier = Modifier.padding(top = 10.dp))
                }

                displayList()
            }
        }

    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExtendedFloatingActionButton(
                text = {
                    Text(
                        text = "Create",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal
                    )
                },
                onClick = {
                    toCreate(userID)
                    Toast.makeText(ctx, "Create Screen Opens", Toast.LENGTH_SHORT).show()
                },
                contentColor = Color.White,
                containerColor = Color.Red,
                modifier = Modifier.clip(shape = RoundedCornerShape(30.dp)),
                icon = { Icon(Icons.Filled.AddCircle, "Add icon") }
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            BottomBar(
                currentScreen = "Home",
                toOnboarding = { toOnboarding() },
                toLeadsScreen = { toLeadsScreen(userID) },
                toHome = { },
                toScheduledVisits = { toScheduledVisits(userID) },
                toFollowupCalls = { toFollowupCalls(userID) }) {

            }
        }
    }


}