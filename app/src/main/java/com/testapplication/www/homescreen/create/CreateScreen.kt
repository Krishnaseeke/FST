package com.testapplication.www.homescreen.create

import CreateScreenDB
import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import java.util.Calendar
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateScreen(
    toHome: (Any?) -> Unit, context: Context, userID: Long, modifier: Modifier = Modifier
) {
    val activiy = context as Activity

    var db: CreateScreenDB = CreateScreenDB(context)


    var customerName = remember {
        mutableStateOf(TextFieldValue())
    }
    var phoneNumer = remember {
        mutableStateOf(TextFieldValue())
    }
    var alternatePhoneNumber = remember {
        mutableStateOf(TextFieldValue())
    }
    var address = remember {
        mutableStateOf(TextFieldValue())
    }
    var comments = remember {
        mutableStateOf(TextFieldValue())
    }
    var isSelected = remember {
        mutableStateOf("value")
    }
    var isCallSelected by remember { mutableStateOf(false) }
    var isVisitSelected by remember { mutableStateOf(false) }

    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()
    val mCalendar1 = Calendar.getInstance()
    val mHour = mCalendar1[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar1[Calendar.MINUTE]
    val mTime = remember { mutableStateOf("") }

    val mDate = remember { mutableStateOf("") }

    val state = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(true) }

    var bcExpanded by remember { mutableStateOf(false) }

    // Create a list of cities
    val bussinessCategory = listOf(
        "Retailer-General Trade",
        "Distributor-General Trade",
        "Wholeseller-General Trade",
        "Retailer-Paint Industry",
        "Distributor-Paint Industry",
        "Wholeseller-Paint Industry",
        "Retailer-Household Electrical Equipments",
        "Distributor-Household Electrical Equipments",
        "Wholeseller-Household Electrical Equipments",
        "Manufacturer-General Trade",
        "Manufacturer-Chemical Industry"
    )

    // Create a string value to store the selected city
    var bcSelectedText by remember { mutableStateOf("") }

    var bcTextFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (bcExpanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    var csExpanded by remember { mutableStateOf(false) }

    // Create a list of cities
    val callStatus = listOf(
        "Ring & Not Received",
        "Connected",
        "Disconnected",
        "Switch Off",
        "Busy",
        "Wrong Number",
        "Call Back Required Specific",
        "Positive"
    )

    // Create a string value to store the selected city
    var csSelectedText by remember { mutableStateOf("") }

    var csTextFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val csicon = if (csExpanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    var lsExpanded by remember { mutableStateOf(false) }

    // Create a list of cities
    val leadStatus = listOf(
        "First Meet",
        "Requires Follow Up",
        "Interested",
        "Demo Scheduled",
        "Demo Re-scheduled",
        "Demo Completed",
        "License Purchased",
        "Not Interested"
    )

    // Create a string value to store the selected city
    var lsSelectedText by remember { mutableStateOf("") }

    var lsTextFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val lsicon = if (lsExpanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Absolute.Left,
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .fillMaxWidth()
        ) {

            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                modifier = Modifier
                    .clickable { toHome(userID) }
                    .padding(start = 10.dp, end = 2.dp, top = 12.5.dp, bottom = 10.dp)
                    .size(30.dp))
            Text(
                text = "Create",
                color = Color.Black,
                fontSize = 30.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 2.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)

            )

        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Color.LightGray)
        )
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(750.dp)
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CustomOutlinedTextField(text1 = customerName, text = "Customer Name*")
            CustomOutlinedTextField(text1 = phoneNumer, text = "Phone Number*")
            CustomOutlinedTextField(text1 = alternatePhoneNumber, text = "Alternate Phone Number")
            CustomOutlinedTextField(text1 = address, text = "Address*")


            Box() {
                Text(
                    text = "Proof Of Meeting*",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    modifier = Modifier.padding(10.dp)
                )

                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .height(80.dp)
                        .align(Alignment.CenterStart)
                        .padding(top = 40.dp),
                    colors = ButtonDefaults.buttonColors(Color.LightGray)
                ) {
                    Icon(
                        Icons.Filled.Add,
                        "Add icon",
                        modifier = Modifier
                            .padding(end = 3.dp)
                            .size(20.dp)
                            .background(
                                Color.Black
                            )
                    )
                    Text(

                        text = "Attach Image",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier.padding(1.dp)
                    )

                }

            }


            // with icon and not expanded
            Box() {
                OutlinedTextField(value = bcSelectedText,
                    onValueChange = { bcSelectedText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp))
                        .padding(5.dp)
                        .onGloballyPositioned { coordinates ->
                            bcTextFieldSize = coordinates.size.toSize()
                        },
                    label = { Text("Business Category") },
                    trailingIcon = {
                        Icon(icon,
                            "contentDescription",
                            Modifier.clickable { bcExpanded = !bcExpanded })
                    })


                DropdownMenu(
                    expanded = bcExpanded,
                    onDismissRequest = { bcExpanded = false },
                    modifier = Modifier
                        .background(Color.White)
                        .width(with(LocalDensity.current) { bcTextFieldSize.width.toDp() })
                        .height(200.dp)
                ) {
                    bussinessCategory.forEach { label ->
                        DropdownMenuItem(text = {
                            Text(
                                text = label,
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Normal,
                                modifier = Modifier.padding(10.dp)
                            )

                        }, onClick = {
                            bcSelectedText = label
                            bcExpanded = false
                        })

                    }
                }
            }


            // with icon and not expanded
            Box() {
                OutlinedTextField(value = csSelectedText,
                    onValueChange = { csSelectedText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp))
                        .padding(5.dp)
                        .onGloballyPositioned { coordinates ->
                            csTextFieldSize = coordinates.size.toSize()
                        },
                    label = { Text("Call Status") },
                    trailingIcon = {
                        Icon(icon,
                            "contentDescription",
                            Modifier.clickable { csExpanded = !csExpanded })
                    })

                // Create a drop-down menu with list of cities,
                // when clicked, set the Text Field text as the city selected
                DropdownMenu(
                    expanded = csExpanded,
                    onDismissRequest = { csExpanded = false },
                    modifier = Modifier
                        .background(Color.White)
                        .width(with(LocalDensity.current) { csTextFieldSize.width.toDp() })
                        .height(200.dp)
                ) {
                    callStatus.forEach { label ->
                        DropdownMenuItem(text = {
                            Text(
                                text = label,
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Normal,
                                modifier = Modifier.padding(10.dp)
                            )

                        }, onClick = {
                            csSelectedText = label
                            csExpanded = false
                        })

                    }
                }
            }


            // with icon and not expanded
            Box() {
                OutlinedTextField(value = lsSelectedText,
                    onValueChange = { lsSelectedText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp))
                        .padding(5.dp)
                        .onGloballyPositioned { coordinates ->
                            lsTextFieldSize = coordinates.size.toSize()
                        },
                    label = { Text("Lead Status*") },
                    trailingIcon = {
                        Icon(icon,
                            "contentDescription",
                            Modifier.clickable { lsExpanded = !lsExpanded })
                    })

                // Create a drop-down menu with list of cities,
                // when clicked, set the Text Field text as the city selected
                DropdownMenu(
                    expanded = lsExpanded,
                    onDismissRequest = { lsExpanded = false },
                    modifier = Modifier
                        .background(Color.White)
                        .width(with(LocalDensity.current) { lsTextFieldSize.width.toDp() })
                        .height(200.dp)
                ) {
                    leadStatus.forEach { label ->
                        DropdownMenuItem(text = {
                            Text(
                                text = label,
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontStyle = FontStyle.Normal,
                                modifier = Modifier.padding(10.dp)
                            )

                        }, onClick = {
                            lsSelectedText = label
                            lsExpanded = false
                        })

                    }
                }
            }




            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val mDatePickerDialog = android.app.DatePickerDialog(
                    mContext, { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                        mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
                    }, mYear, mMonth, mDay
                )
                OutlinedTextField(
                    value = mDate.value,
                    onValueChange = { mDate.value = it },
                    label = {
                        Text(
                            text = "Follow Up Date*"
                        )
                    },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(5.dp))
                        .weight(1f)
                        .padding(5.dp, end = 0.dp)
                )
                Icon(imageVector = Icons.Default.DateRange,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable { mDatePickerDialog.show() }
                        .padding(5.dp))
            }


            var isTimePickerDialogOpen by remember { mutableStateOf(false) }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val mTimePickerDialog = TimePickerDialog(
                    mContext, { _, mHour: Int, mMinute: Int ->
                        mTime.value = "$mHour:$mMinute"
                    }, mHour, mMinute, false
                )

                OutlinedTextField(
                    value = mTime.value,
                    onValueChange = { mTime.value = it },
                    label = {
                        Text(
                            text = "Follow Up Time*"
                        )
                    },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(5.dp))
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clickable { isTimePickerDialogOpen = true },

                    )

            }
            Column() {

                Text(
                    text = "Follow Up Action*",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(5.dp)

                )
                Row(
                    horizontalArrangement = Arrangement.Absolute.Left,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(selected = isCallSelected,
                        onClick = { isCallSelected = !isCallSelected })
                    Text(
                        text = "Call",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(start = 0.dp, top = 12.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.Absolute.Left,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButton(selected = isVisitSelected,
                        onClick = { isVisitSelected = !isVisitSelected })
                    Text(
                        text = "Visit",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(start = 0.dp, top = 12.dp)
                    )
                }
            }




            CustomOutlinedTextField(text1 = comments, text = "Comments")


        }

        Spacer(modifier = Modifier.weight(1f))
        // Save button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val isSuccess = db.createFST(
                        userId = userID,
                        customerName = customerName.value.text,
                        phoneNumber = phoneNumer.value.text,
                        alternatePhoneNumber = alternatePhoneNumber.value.text,
                        address = address.value.text,
                        businessCategory = bcSelectedText,
                        callStatus = csSelectedText,
                        leadStatus = lsSelectedText,
                        followUpDate = mDate.value,
                        followUpTime = mTime.value,
                        followUpActionCall = if (isCallSelected) "Call" else "",
                        followUpActionVisit = if (isVisitSelected) "Visit" else "",
                        comments = comments.value.text
                    )
                    if (isSuccess) {
                        toHome(userID)
                        Toast.makeText(context, "Successfully FST is Created", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(context, "Failed to Create FST", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

