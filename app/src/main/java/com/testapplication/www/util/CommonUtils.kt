package com.testapplication.www.util

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testapplication.www.homescreen.home.customTextHome
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun HeaderText(text:String){
    Text(
        text = text,
        color = Color.Black,
        fontSize = 25.sp,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold

    )
}

@Composable
fun TextFieldText(text: String){
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
    placeholder: String = "Enter your Phone Number",
    keyboardType: KeyboardType = KeyboardType.Text

) {
    TextField(
        value = phoneNumber.value,
        onValueChange = { phoneNumber.value = it },
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
        }
    )
}

@Composable
fun CustomButton(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier,
    buttonColor: Color = Color.LightGray,
    textColor: Color = Color.White,
    buttonHeight: Dp = 70.dp,
    textSize: TextUnit = 20.sp,
    fontWeight: FontWeight = FontWeight.Bold,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(buttonHeight)
            .padding(top = 20.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Text(
            text = buttonText,
            fontSize = textSize,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center,
            color = textColor
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

fun Date.convertDateToString(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(this)
}