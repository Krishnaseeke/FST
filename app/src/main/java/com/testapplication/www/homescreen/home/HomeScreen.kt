package com.testapplication.www.homescreen.home

import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.*
import com.google.android.gms.location.LocationServices
import android.Manifest


import CreateScreenDB
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.homescreen.bottomnavigation.BottomBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    toOnboarding: () -> Unit,
    toScheduledVisits: (Any?) -> Unit,
    toFollowupCalls: (Any?) -> Unit,
    toLeadsScreen: (Any?) -> Unit,
    userID: Long?,
    context: Context,
    toCreate: (Long?, Long?) -> Unit,
    toCheckIn:(Any?)-> Unit,
    modifier: Modifier = Modifier
) {
    val ctx = LocalContext.current
    val viewModel: HomeScreenViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel { HomeScreenViewModel(ctx) }
    viewModel.initialize(context, userID)

    var checked by remember { mutableStateOf(false) }
    var showLocationDialog by remember { mutableStateOf(false) }
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val preferencesManager = PreferencesManager(context)

    // Fetch initial check-in status
    LaunchedEffect(Unit) {
        checked = preferencesManager.getCheckInStatus(false)
        if (!locationPermissionState.allPermissionsGranted) {
            locationPermissionState.launchMultiplePermissionRequest()
        }
    }

    val checkInColors = SwitchDefaults.colors(
        checkedBorderColor = Color.Transparent,
        uncheckedThumbColor = Color.Gray,
        checkedThumbColor = Color.White,
        checkedIconColor = Color.White,
        uncheckedBorderColor = Color.Transparent,
        disabledCheckedTrackColor = Color.White,
        checkedTrackColor = Color.Black
    )

    // Function to open app settings for location permissions
    fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    // Check location permissions when the screen is displayed
    LaunchedEffect(locationPermissionState.allPermissionsGranted) {
        if (locationPermissionState.allPermissionsGranted) {
            // Call function to get last known location
            getLastLocation(ctx)
        } else {
            Toast.makeText(
                ctx,
                "Location permissions are required to use this app.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Row(){
        if (showLocationDialog) {
            AlertDialog(
                onDismissRequest = { showLocationDialog = false },
                title = {
                    Text(
                        text = "Enable Location Services",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = { Text("Please enable location services manually in FST app settings." ,
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold) },
                containerColor = Color.White,
                confirmButton = {
                    Button(
                        onClick = {
                            openAppSettings()
                            showLocationDialog = false
                        }
                    ) {
                        Text(
                            "Allow",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    }



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
                    text = "Home",
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
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .padding(start = 10.dp, top = 10.dp, bottom = 15.dp)
                .fillMaxWidth(1f)
                .clip(shape = RoundedCornerShape(5.dp)),

            Arrangement.Top,
            Alignment.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(1f)
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
                        if (locationPermissionState.allPermissionsGranted) {
                            preferencesManager.saveCheckInStatus(it)
                            val dateTime =
                                SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(
                                    Date()
                                )
                            val checkInStatus = if (it) 1 else 0
                            if(checkInStatus==1){
                                toCheckIn(userID)
                                checked = it
                            }else{
                                checked = it
                                viewModel.insertCheckIn(userID,1, dateTime,null,null,null)
                            }
                        } else {
                            showLocationDialog = true
                        }
                    },
                    colors = checkInColors
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .fillMaxHeight(1f)
                .padding(start = 5.dp, end = 5.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(start = 1.dp, top = 10.dp, bottom = 15.dp, end = 1.dp)
                    .fillMaxWidth(1f),
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
                        customValuesHome(text = viewModel.leadsCreatedCount.collectAsState().value.toString())
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
                        customValuesHome(text = viewModel.demosScheduledCount.collectAsState().value.toString())
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
                        customValuesHome(text = viewModel.demosCompletedCount.collectAsState().value.toString())
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
                        customValuesHome(text = viewModel.licensesSoldCount.collectAsState().value.toString())
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier
                    .height(350.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .fillMaxWidth(1f)
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

                if (userID != null) {
                    com.testapplication.www.homescreen.home.displayList(
                        context = context,
                        userId = userID,
                        "",
                        valueType = "visit"
                    ) { userId, itemId ->
                        toCreate.invoke(userId, itemId)
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Column(
                modifier = Modifier
                    .height(350.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(start = 1.dp, top = 10.dp, bottom = 15.dp, end = 1.dp)
                    .fillMaxWidth(1f),
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

                if (userID != null) {
                    com.testapplication.www.homescreen.home.displayList(
                        context = context,
                        userId = userID,
                        "",
                        valueType = "call"
                    ) { userId, itemId ->
                        toCreate.invoke(userId, itemId)
                    }
                }
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
                    if (checked) {
                        getLastLocation(ctx)
                        toCreate(userID, 0)
                    } else {
                        Toast.makeText(ctx, "Please Check-In to Create FST", Toast.LENGTH_SHORT)
                            .show()
                    }
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

public fun getLastLocation(context: Context) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    if (ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // Request missing location permission.
        return
    }
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            // Handle the location object
//            Toast.makeText(
//                context,
//                "Latitude: ${location.latitude}, Longitude: ${location.longitude}",
//                Toast.LENGTH_LONG
//            ).show()
        }
    }
}