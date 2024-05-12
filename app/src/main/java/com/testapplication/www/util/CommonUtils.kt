package com.testapplication.www.util

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
    placeholder: String = "Enter your Phone Number"
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
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor
        )
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
fun setCustomDate(): Date {

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring a Calendar instance
    val mCalendar = Calendar.getInstance()

    // Getting today's date
    val todayYear = mCalendar.get(Calendar.YEAR)
    val todayMonth = mCalendar.get(Calendar.MONTH)
    val todayDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    // State variable to hold the selected date (initially today's date)
    val selectedDate = remember { mutableStateOf(Calendar.getInstance().time) }

    // Function to format the date string with leading zeros for year
    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(date)
    }

    // Updating the selectedDate state based on user selection
    val onDateSet: (DatePicker, Int, Int, Int) -> Unit = { _, year, month, dayOfMonth ->
        mCalendar.set(Calendar.YEAR, year)
        mCalendar.set(Calendar.MONTH, month)
        mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        selectedDate.value = mCalendar.time
    }

    // Check if selected date is today
    val isTodaySelected = mCalendar.get(Calendar.YEAR) == todayYear &&
            mCalendar.get(Calendar.MONTH) == todayMonth &&
            mCalendar.get(Calendar.DAY_OF_MONTH) == todayDay

    val text = if (isTodaySelected) {
        "Select Date:${formatDate(selectedDate.value)}"
    } else {
        "Select Date:${formatDate(selectedDate.value)}"
    }

    // Return the selected date

    Column(modifier = Modifier.fillMaxWidth().clickable {
        DatePickerDialog(
            mContext,
            onDateSet,
            todayYear,
            todayMonth,
            todayDay
        ).show()
    }) {
        Text(
            text = text,
            color = Color.Black,
            textAlign = TextAlign.Start, // Align text to the left
            modifier = Modifier.padding(16.dp) // Add padding for better touch area
        )
    }

    return selectedDate.value
}
