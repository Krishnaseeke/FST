package com.testapplication.www.signupscreen

import LoginViewModel
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.util.CustomButton
import com.testapplication.www.util.CustomTextField
import com.testapplication.www.util.HeaderText
import com.testapplication.www.util.TextFieldText
import com.testapplication.www.util.constants.Constants.DEFAULT_CHECK_IN_VALUE
import com.testapplication.www.util.constants.Constants.SIGNUP_CTA_NAME
import com.testapplication.www.util.constants.Constants.SIGNUP_PASSWORD_PLACE_HOLDER
import com.testapplication.www.util.constants.Constants.SIGNUP_PASSWORD_TEXT
import com.testapplication.www.util.constants.Constants.SIGNUP_PHONE_NUMBER_PLACE_HOLDER
import com.testapplication.www.util.constants.Constants.SIGNUP_PHONE_NUMBER_TEXT
import com.testapplication.www.util.constants.Constants.SIGNUP_SCREEN_TITLE

@Composable
fun SignupScreen(
    toOnboarding: () -> Unit,
    toHome: (Any?) -> Unit,
    context: Context,
    modifier: Modifier = Modifier
) {
    val viewModel = remember { SignupViewModel(context) }
    var isPhoneError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var phoneErrorMessage by rememberSaveable { mutableStateOf("") }
    var passwordErrorMessage by rememberSaveable { mutableStateOf("") }


    val toastMessage = viewModel.toastMessage.value

    val preferencesManager = PreferencesManager(context) // Instantiate PreferencesManager

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            HeaderText(text = SIGNUP_SCREEN_TITLE)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
                .padding(horizontal = 10.dp)
        ) {

            TextFieldText(text = SIGNUP_PHONE_NUMBER_TEXT)
            CustomTextField(
                phoneNumber = viewModel.phoneNumber,
                modifier = Modifier.fillMaxWidth(),
                placeholder = SIGNUP_PHONE_NUMBER_PLACE_HOLDER,
                keyboardType = KeyboardType.Phone,
                isError = isPhoneError,
                errorMessage = phoneErrorMessage,
                onValueChange = {
                    isPhoneError = false
                    phoneErrorMessage = ""
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldText(text = SIGNUP_PASSWORD_TEXT)
            CustomTextField(
                phoneNumber = viewModel.password,
                modifier = Modifier.fillMaxWidth(),
                placeholder = SIGNUP_PASSWORD_PLACE_HOLDER,
                keyboardType = KeyboardType.Password,
                isError = isPasswordError,
                errorMessage = passwordErrorMessage,
                onValueChange = {
                    isPasswordError = false
                    passwordErrorMessage = ""
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

            CustomButton(
                onClick = {
                    viewModel.signup(context, {
                        preferencesManager.saveCheckInStatus(DEFAULT_CHECK_IN_VALUE) // Set check-in status to 0 (false)
                        toHome(it)
                    }) { phoneError, passwordError ->
                        isPhoneError = phoneError != null
                        isPasswordError = passwordError != null
                        phoneErrorMessage = phoneError ?: ""
                        passwordErrorMessage = passwordError ?: ""
                    }
                },
                buttonText = SIGNUP_CTA_NAME,
                buttonColor = Color.LightGray,
                textColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                textSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            toastMessage?.let {
                Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
