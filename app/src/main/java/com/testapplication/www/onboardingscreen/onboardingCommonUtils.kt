package com.testapplication.www.onboardingscreen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    onClick: () -> Unit,
    buttonText: String,
    modifier: Modifier = Modifier,
    buttonWidth: Dp = 150.dp,
    buttonHeight: Dp = 50.dp,
    buttonColor: Color = Color.Black,
    textColor: Color = Color.White,
    textSize: TextUnit = 20.sp,
    fontStyle: FontStyle = FontStyle.Normal,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(5.dp)
            .width(buttonWidth)
            .height(buttonHeight),
        colors = ButtonDefaults.buttonColors(buttonColor)
    ) {
        Text(
            text = buttonText,
            color = textColor,
            fontSize = textSize,
            fontStyle = fontStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(5.dp)
        )
    }
}
