package com.testapplication.www.util

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.android.gms.location.FusedLocationProviderClient
import com.testapplication.www.R
import com.testapplication.www.common.MainActivity
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.homescreen.create.DropdownLists
import com.testapplication.www.homescreen.home.ScreenData
import com.testapplication.www.util.constants.Constants
import com.testapplication.www.util.constants.Constants.ERROR_INFO_ICON
import getLastLocation
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 25.sp,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold

    )
}

@Composable
fun TextFieldText(text: String) {
    Text(
        text = text,
        color = Color.Gray,
        fontSize = 22.sp,
        fontStyle = FontStyle.Normal,
        modifier = Modifier.padding(start = 10.dp, top = 10.dp)
    )
}
@Composable
fun CustomTextField(
    phoneNumber: MutableState<TextFieldValue>,
    modifier: Modifier = Modifier,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean,
    errorMessage: String,
    onValueChange: () -> Unit
) {
    TextField(
        value = phoneNumber.value,
        onValueChange = {
            phoneNumber.value = it
            onValueChange()
        },
        trailingIcon = {
            if (isError)
                Icon(Icons.Filled.Info, ERROR_INFO_ICON, tint = MaterialTheme.colors.error)
        },
        textStyle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Justify
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        modifier = modifier
            .padding(start = 10.dp, top = 5.dp, bottom = 1.dp, end = 10.dp)
            .fillMaxWidth()
            .height(55.dp)
            .border(width = 2.dp, color = Color.Gray),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        singleLine = true,
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 20.sp,
            )
        },
    )

    if (isError) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}


@Composable
fun setCustomDate(defaultDate: String = ""): String? {

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring a Calendar instance
    val mCalendar = Calendar.getInstance()

    // State variable to hold the selected date as String
    val selectedDate = remember { mutableStateOf<String?>(null) }

    // Function to format the date string
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(date)
    }

    // Updating the selectedDate state based on user selection
    val onDateSet: (DatePickerDialog, Int, Int, Int) -> Unit = { _, year, month, dayOfMonth ->
        mCalendar.set(Calendar.YEAR, year)
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        selectedDate.value = formatDate(mCalendar.time)
    }

    // Set initial date if defaultDate is not empty
    if (defaultDate.isNotEmpty()) {
        @SuppressLint("SimpleDateFormat")
        val initialDate = SimpleDateFormat("dd/MM/yyyy").parse(defaultDate)
        if (initialDate != null) {
            selectedDate.value = formatDate(initialDate)
            mCalendar.time = initialDate
        }
    }

    // Text to display on the date selector
    val text = "Select Date: ${selectedDate.value ?: "Not selected"}"

    // Composable for the date selector
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            DatePickerDialog(
                mContext,
                { _, year, month, dayOfMonth ->
                    onDateSet(DatePickerDialog(mContext), year, month, dayOfMonth)
                },
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    ) {
        Text(
            text = text,
            color = Color.Black,
            textAlign = TextAlign.Start, // Align text to the left
            modifier = Modifier.padding(16.dp) // Add padding for better touch area
        )
    }

    Log.e("TAG", "setCustomDate: ${selectedDate.value}")

    // Return the selected date as a string or null if none selected
    return selectedDate.value
}


@Composable
fun ScreenHeaders(text:String){
    Text(
        text = text,
        color = Color.Black,
        fontSize = 25.sp,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun LogoutOrExitScreen(
    preferencesManager: PreferencesManager,
    toOnboarding: () -> Unit,
    actionType: ActionType
) {
    val activity: MainActivity = MainActivity()
    var showDialog by remember { mutableStateOf(false) }

    Icon(
        painter = painterResource(id = if (actionType == ActionType.LOGOUT) R.drawable.logout else R.drawable.exit),
        contentDescription = if (actionType == ActionType.LOGOUT) "Logout" else "Exit",
        modifier = Modifier
            .clickable {
                showDialog = true
            }
            .padding(10.dp)
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            confirmButton = {
                TextButton(onClick = {
                    if (actionType == ActionType.LOGOUT) {
                        preferencesManager.clearPreferences()
                        toOnboarding()
                    } else if (actionType == ActionType.EXIT) {
                        activity.finish()
                        System.exit(0)
                    }
                    showDialog = false
                }) {
                    Text("Yes",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                }) {
                    Text("No",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Text("Confirmation",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold)
            },
            text = {
                Text(if (actionType == ActionType.LOGOUT) "Are you sure you want to Logout?" else "Are you sure you want to Exit?",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold)
            },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
            containerColor = Color.White
        )
    }
}

enum class ActionType {
    LOGOUT, EXIT
}

@Composable
fun AllowSettingPopup(
    context: Context,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    title: String,
    description: String,
    confirmButtonText: String,
    onConfirm: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = description,
                    color = Color.Black,
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
            },
            containerColor = Color.White,
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text(
                        text = confirmButtonText,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )
    }
}


@Composable
fun SelectedDateItemRow(
    screenData: ScreenData,
    userId: Long,
    toCreate: (userId: Long, itemId: Long) -> Unit,
    preferencesManager: PreferencesManager,
    showAlert: Boolean,
    setShowAlert: (Boolean) -> Unit,
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    currentLatitude: Double,
    currentLongitude: Double,
    setCurrentLatitude: (Double) -> Unit,
    setCurrentLongitude: (Double) -> Unit
) {
    val dist = FloatArray(1)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (preferencesManager.getCheckInStatus(false)) {
                    getLastLocation(fusedLocationClient, context) { location ->
                        setCurrentLatitude(location.latitude)
                        setCurrentLongitude(location.longitude)
                    }
                    Location.distanceBetween(
                        currentLatitude,
                        currentLongitude,
                        screenData.latitudeValue.toDouble(),
                        screenData.longitudeValue.toDouble(),
                        dist
                    )
                    val RADIUS_IN_METER = 1000

                    if (dist[0] / 1000 >= RADIUS_IN_METER) {
                        Toast
                            .makeText(
                                context,
                                "Please be in the Range to Edit the Account",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    } else {
                        toCreate.invoke(userId, screenData.id)
                    }
                } else {
                    setShowAlert(Constants.SHOW_ALERT_POP_UP)
                }
            }
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row {
                    Text(
                        text = screenData.stringValue + " |",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = screenData.leadStatus,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Add icon")
                    Text(
                        text = screenData.time,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(Icons.Default.DateRange, contentDescription = "Date icon")
                    Text(
                        text = screenData.date,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Navigate",
                modifier = Modifier
            )
        }

        Divider(modifier = Modifier.padding(top = 2.dp))
    }
}

@Composable
fun OnSavingDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        // Launch a coroutine to delay and dismiss the dialog after 500 milliseconds
        LaunchedEffect(Unit) {
            delay(50L)
            onDismiss()
        }

        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Transparent, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator(color = Color.Green)
            }
        }
    }
}
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
) {
    // Determine border and label color based on the error and enabled state
    val borderColor = when {
        isError -> Color.Red
        enabled -> Color.Black
        else -> Color.Black
    }

    val labelColor = when {
        isError -> Color.Red
        enabled -> Color.Black
        else -> Color.Black
    }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = borderColor,
        unfocusedBorderColor = borderColor,
        focusedLabelColor = labelColor,
        unfocusedLabelColor = labelColor,
        disabledTextColor = Color.Black,
        disabledBorderColor = borderColor,
        disabledLabelColor = labelColor,
        errorBorderColor = Color.Red,
        errorLabelColor = Color.Red,
    )

    val textFieldStyle = TextStyle(color = Color.Black)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        colors = textFieldColors,
        textStyle = textFieldStyle,
        singleLine = true,
        isError = isError,
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit, onCategorySelected: (String) -> Unit,value:String) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val isSheetOpened = remember {
        mutableStateOf(false
        )
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = {
            BottomSheetDefaults.DragHandle() }, containerColor = Color.White
    ) {
        CategoryList(onCategorySelected,value)
    }

    LaunchedEffect(modalBottomSheetState.currentValue) {
        if (modalBottomSheetState.currentValue == SheetValue.Hidden) {
            if (isSheetOpened.value) {
                isSheetOpened.value = false
                onDismiss.invoke()
            } else {
                isSheetOpened.value = true
                modalBottomSheetState.show()
            }
        }
    }
}

@Composable
fun CategoryList(onCategorySelected: (String) -> Unit,type:String) {
    var showList = if(type=="BussinessCategory"){
        DropdownLists.bussinessCategory
    }else if (type=="CallStatus"){
        DropdownLists.callStatus
    }else{
        DropdownLists.leadStatus
    }

    LazyColumn {
        items(showList) { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .clickable { onCategorySelected(category) } // Trigger callback on item click
            ) {
                Text(
                    text = category,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
            Divider()
        }
    }
}

