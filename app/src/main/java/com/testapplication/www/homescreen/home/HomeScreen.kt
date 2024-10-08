package com.testapplication.www.homescreen.home

import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.*
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
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
import com.testapplication.www.homescreen.checkin.CheckInViewModel
import com.testapplication.www.onboardingscreen.pressClickEffect
import com.testapplication.www.ui.theme.AppleGreen
import com.testapplication.www.ui.theme.CheckInGreen
import com.testapplication.www.util.ActionType
import com.testapplication.www.util.AllowSettingPopup
import com.testapplication.www.util.LogoutOrExitScreen
import com.testapplication.www.util.constants.Constants
import com.testapplication.www.util.constants.Constants.ADD_ICON_DESCRIPTION
import com.testapplication.www.util.constants.Constants.ALERT_ALLOW_CTA
import com.testapplication.www.util.constants.Constants.ALERT_DESCRIPTION
import com.testapplication.www.util.constants.Constants.ALERT_TITLE
import com.testapplication.www.util.constants.Constants.CHECK_IN_ALERT_DESCRIPTION
import com.testapplication.www.util.constants.Constants.DEFAULT_ALERT_POP_UP
import com.testapplication.www.util.constants.Constants.DEFAULT_CHECK_IN_STATUS
import com.testapplication.www.util.constants.Constants.DEFAULT_LOCATION_ALERT_DIALOG
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_CALL_LIST_TYPE
import com.testapplication.www.util.constants.Constants.GENERAL_ALERT_ALLOW_CTA
import com.testapplication.www.util.constants.Constants.GENERAL_ALERT_TITLE
import com.testapplication.www.util.constants.Constants.SCHEDULED_VISIT_LIST_TYPE
import com.testapplication.www.util.constants.Constants.SCREEN_CHECK_IN
import com.testapplication.www.util.constants.Constants.SCREEN_CHECK_IN_TEXT
import com.testapplication.www.util.constants.Constants.SCREEN_CREATE
import com.testapplication.www.util.constants.Constants.SCREEN_FOLLOW_UP_CALLS
import com.testapplication.www.util.constants.Constants.SCREEN_HOME
import com.testapplication.www.util.constants.Constants.SCREEN_SCHEDULED_VISIT
import com.testapplication.www.util.constants.Constants.SCREEN_SCHEDULED_VISIT_AND_FOLLOWUP_CALLS
import com.testapplication.www.util.constants.Constants.SHOW_ALERT_POP_UP
import com.testapplication.www.util.constants.Constants.SHOW_ALL_CTA
import com.testapplication.www.util.constants.Constants.TABLE_DEMOS_COMPLETED
import com.testapplication.www.util.constants.Constants.TABLE_DEMOS_SCHEDULED
import com.testapplication.www.util.constants.Constants.TABLE_LEADS_CREATED
import com.testapplication.www.util.constants.Constants.TABLE_LICENSES_SOLD
import com.testapplication.www.util.isInternetAvailable
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
    toCheckIn: (Any?) -> Unit,
    toCreationLedger: (Long, Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val ctx = LocalContext.current
    val viewModel: HomeScreenViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel { HomeScreenViewModel(ctx) }
    viewModel.initialize(context, userID)

    val viewModelCheckeIn: CheckInViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel { CheckInViewModel(context) }

    var checked by remember { mutableStateOf(DEFAULT_CHECK_IN_STATUS) }
    var showLocationDialog by remember { mutableStateOf(DEFAULT_ALERT_POP_UP) }
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val preferencesManager = PreferencesManager(context)
    var showAlert by remember { mutableStateOf(DEFAULT_ALERT_POP_UP) }

    // Fetch initial check-in status
    LaunchedEffect(Unit) {
        checked = preferencesManager.getCheckInStatus(DEFAULT_CHECK_IN_STATUS)
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
        checkedTrackColor = CheckInGreen
    )


    fun openAppSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    AllowSettingPopup(
        context = ctx,
        showDialog = showLocationDialog,
        onDismiss = { showLocationDialog = DEFAULT_LOCATION_ALERT_DIALOG },
        title = ALERT_TITLE,
        description = ALERT_DESCRIPTION,
        confirmButtonText = ALERT_ALLOW_CTA,
        onConfirm = { openAppSettings(context) }
    )


    if (showAlert) {
        AllowSettingPopup(
            context = ctx,
            showDialog = showAlert,
            onDismiss = { showAlert = DEFAULT_ALERT_POP_UP },
            title = GENERAL_ALERT_TITLE,
            description = CHECK_IN_ALERT_DESCRIPTION,
            confirmButtonText = GENERAL_ALERT_ALLOW_CTA,
            onConfirm = { /* Handle confirmation if needed */ }
        )
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
                    text = SCREEN_HOME,
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))

                LogoutOrExitScreen(preferencesManager, toOnboarding, ActionType.LOGOUT)
                LogoutOrExitScreen(preferencesManager, toOnboarding, ActionType.EXIT)
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
            Text(text = SCREEN_CHECK_IN_TEXT,color = Color.Black,
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(1.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Text(
                    text = SCREEN_CHECK_IN,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(5.dp)
                )

                Switch(
                    checked = checked, modifier = Modifier.height(5.dp),
                    onCheckedChange = {
                        if (locationPermissionState.allPermissionsGranted) {
                            if (isInternetAvailable(context)) { // Check for internet connection
                                preferencesManager.saveCheckInStatus(it)
                                val dateTime =
                                    SimpleDateFormat(
                                        "yyyy-MM-dd HH:mm:ss",
                                        Locale.getDefault()
                                    ).format(Date())
                                if (it) {
                                    toCheckIn(userID)
                                    checked = it
                                } else {
                                    checked = it
                                    viewModelCheckeIn.insertCheckIn(
                                        userID,
                                        1,
                                        dateTime,
                                        null,
                                        null,
                                        null,
                                        context
                                    )
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "No internet connection. Please check your network.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            showLocationDialog = SHOW_ALERT_POP_UP
                        }
                    },
                    colors = checkInColors
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .weight(1f)
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
                        CustomTextHome(text = TABLE_LEADS_CREATED)
                        CustomValuesHome(text = viewModel.leadsCreatedCount.collectAsState().value.toString())
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
                        CustomTextHome(text = TABLE_DEMOS_SCHEDULED)
                        CustomValuesHome(text = viewModel.demosScheduledCount.collectAsState().value.toString())
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
                        CustomTextHome(text = TABLE_DEMOS_COMPLETED)
                        CustomValuesHome(text = viewModel.demosCompletedCount.collectAsState().value.toString())
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
                        CustomTextHome(text = TABLE_LICENSES_SOLD)
                        CustomValuesHome(text = viewModel.licensesSoldCount.collectAsState().value.toString())
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
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
                    CustomTextHome(
                        text = SCREEN_SCHEDULED_VISIT_AND_FOLLOWUP_CALLS,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
//                    ClickableText(text = buildAnnotatedString {
//                        append(SHOW_ALL_CTA)
//                        withStyle(style = SpanStyle(color = Color.Black)) {
//                        }
//                    }, onClick = {
//                        toScheduledVisits(userID)
//                    }, modifier = Modifier.padding(top = 10.dp))
                }

                if (userID != null) {
                    com.testapplication.www.homescreen.home.DisplayList(
                        context = context,
                        userId = userID,
                        itemId = 0L,
                        "",
                        valueType = Constants.LEADS_AND_VISIT_LIST,
                        toCreationLedger = { userId, itemId ->
                            toCreationLedger(userId, itemId)
                        },
                        toCreate = { userId, itemId ->
                            toCreate(userId, itemId)
                        }
                    )
                }
            }

        }
        Column(
            modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,

            ) {
            BottomBar(
                currentScreen = SCREEN_HOME,
                toOnboarding = { toOnboarding() },
                toLeadsScreen = { toLeadsScreen(userID) },
                toHome = { },
                toScheduledVisits = { toScheduledVisits(userID) },
                toFollowupCalls = { toFollowupCalls(userID) }) {

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
                        text = SCREEN_CREATE,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal
                    )
                },
                onClick = {
                    if (checked && isInternetAvailable(context)) {
                        getLastLocation(ctx)
                        toCreate(userID, 0)
                    } else {
                        if (isInternetAvailable(context)) {
                            showAlert = SHOW_ALERT_POP_UP
                        } else {

                            Toast.makeText(
                                context,
                                "No internet connection. Please check your network.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                },
                contentColor = Color.White,
                containerColor = Color.Red,
                modifier = Modifier
                    .pressClickEffect()
                    .clip(shape = RoundedCornerShape(30.dp)),
                icon = { Icon(Icons.Filled.AddCircle, ADD_ICON_DESCRIPTION) }
            )
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
        return
    }
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
    }
}