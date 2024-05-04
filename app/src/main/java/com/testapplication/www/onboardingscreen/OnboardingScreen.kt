package com.testapplication.www.onboardingscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen(
    toLoginScreen: () -> Unit,
    toSignupScreen: () -> Unit,
    toHomeScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        Arrangement.Center, Alignment.CenterHorizontally
    ) {
        CustomButton(
            onClick = toSignupScreen,
            buttonText = "Signup",
            buttonWidth = 150.dp,
            buttonHeight = 50.dp,
            buttonColor = Color.Black,
            textColor = Color.White,
            textSize = 20.sp,
            fontStyle = FontStyle.Normal
        )

        CustomButton(
            onClick = toLoginScreen,
            buttonText = "Sign-In",
            buttonWidth = 150.dp,
            buttonHeight = 50.dp,
            buttonColor = Color.Black,
            textColor = Color.White,
            textSize = 20.sp,
            fontStyle = FontStyle.Normal
        )

    }
}