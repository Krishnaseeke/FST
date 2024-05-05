package com.testapplication.www.util

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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


data class StoreData(val date: String, val time: String, val stringValue: String)
@OptIn(ExperimentalUnitApi::class)
@Composable
fun displayList() {
    val storesMap = hashMapOf(
        "Store 1" to StoreData("2024-04-13", "10:00 AM", "Value 1"),
        "Store 2" to StoreData("2024-04-14", "11:00 AM", "Value 2"),
        "Store 3" to StoreData("2024-04-14", "11:00 AM", "Value 3"),
        "Store 4" to StoreData("2024-04-15", "12:00 PM", "Value 4"),
        "Store 5" to StoreData("2024-04-16", "01:00 PM", "Value 5")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp), // Padding around the whole column
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(), // Ensure the LazyColumn fills the space
            contentPadding = PaddingValues(10.dp) // Provide internal padding
        ) {
            items(storesMap.entries.toList()) { (store, storeData) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp), // Padding between items
                    verticalArrangement = Arrangement.spacedBy(10.dp) // Space between items in the column
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween, // Ensure space between elements
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f) // Ensure proportional width
                        ) {
                            Text(
                                text = store,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1, // Ensure single-line text
                                overflow = TextOverflow.Ellipsis // Handle text overflow
                            )
                            Spacer(modifier = Modifier.height(5.dp)) // Space between elements
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.AddCircle, contentDescription = "Add icon")
                                Text(
                                    text = storeData.time,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp)) // Space between icon and text
                                Icon(Icons.Default.DateRange, contentDescription = "Date icon")
                                Text(
                                    text = storeData.date,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Normal,
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Navigate",
                            modifier = Modifier
                                .clickable {  } // Navigation handler
                        )
                    }

                    Divider(modifier = Modifier.padding(top = 10.dp)) // Divider between items
                }
            }
        }
    }
}


