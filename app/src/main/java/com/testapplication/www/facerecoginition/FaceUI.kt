package com.testapplication.www.facerecoginition

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.onboardingscreen.OnboardingScreen

@Composable
fun FaceRecognitionScreen(
    toLoginScreen: () -> Unit,
    toSignupScreen: () -> Unit,
    toHomeScreen: (Any?) -> Unit,
    toFaceRecognitionScreen: () -> Unit, // Callback to launch FaceRecognition
    context: Context
) {
    val preferencesManager = PreferencesManager(context)
    val userValue = preferencesManager.getUserId(-1)

    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        if (userValue > 0) {
            // Navigate to home screen if user is logged in
            toHomeScreen(userValue)
        } else {
            // Show onboarding UI if user is not logged in
            OnboardingScreen(
                toLoginScreen = toLoginScreen,
                toSignupScreen = {
                    toFaceRecognitionScreen() // Launch FaceRecognition on signup

                },
                toHomeScreen,
                toFaceRecognitionScreen,
                modifier = Modifier,
                context
            )
        }

    }
}
