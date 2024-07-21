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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testapplication.www.util.constants.Constants.PREFERENCE_MANAGER_USER_ID_COUNT
import com.testapplication.www.util.constants.Constants.SIGNIN_CTA_NAME
import com.testapplication.www.util.constants.Constants.SIGNUP_CTA_NAME


@Composable
fun OnboardingScreen(
    toLoginScreen: () -> Unit,
    toSignupScreen: () -> Unit,
    toHomeScreen: (Any?) -> Unit,
    modifier: Modifier = Modifier,
    context: Context
) {
    val onboardingViewModel: OnboardingViewModel = viewModel(factory = OnboardingViewModel.Factory(context))
    val userValue = onboardingViewModel.getUserId()

    if (userValue > PREFERENCE_MANAGER_USER_ID_COUNT) {
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
                buttonText = SIGNUP_CTA_NAME,
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
                buttonText = SIGNIN_CTA_NAME,
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
