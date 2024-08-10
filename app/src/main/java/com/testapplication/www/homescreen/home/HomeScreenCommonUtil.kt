package com.testapplication.www.homescreen.home


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTextHome(
    text: String,
    fontSize: TextUnit = 14.sp,fontWeight: FontWeight = FontWeight.Normal // Default font size is 14.sp
) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = fontSize,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(5.dp),
        textAlign = TextAlign.Left
    )
}


//This only for Value in the Cards
@Composable
fun CustomValuesHome(text: String) {

    Text(
        text = text,
        color = Color.Black,
        fontSize = 20.sp,
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(5.dp),
        textAlign = TextAlign.Left
    )


}

