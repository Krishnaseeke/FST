package com.testapplication.www.onboardingscreen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testapplication.www.common.PreferencesManager


@Composable
fun OnboardingScreen(
    toLoginScreen: () -> Unit,
    toSignupScreen: () -> Unit,
    toHomeScreen: (Any?) -> Unit,
    modifier: Modifier = Modifier,
    context: Context
) {
    val preferencesManager = PreferencesManager(context)
    var userValue = preferencesManager.getUserId(-1)
    if (userValue > 0) {
        toHomeScreen(userValue)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomButton(
                onClick = toSignupScreen,
                buttonText = "Signup",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                buttonColor = Color.Black,
                textColor = Color.White,
                textSize = 20.sp,
                fontStyle = FontStyle.Normal
            )

            CustomButton(
                onClick = toLoginScreen,
                buttonText = "Sign-In",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                buttonColor = Color.Black,
                textColor = Color.White,
                textSize = 20.sp,
                fontStyle = FontStyle.Normal
            )
        }
    }

}
