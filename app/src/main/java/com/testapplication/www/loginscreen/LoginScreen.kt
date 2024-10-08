package com.testapplication.www.loginscreen

import CreateScreenDB
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
import com.testapplication.www.onboardingscreen.CustomButton
import com.testapplication.www.util.CustomTextField
import com.testapplication.www.util.HeaderText
import com.testapplication.www.util.TextFieldText


@Composable
fun LoginScreen(
    toOnboarding: () -> Unit,
    toHome: (Any?) -> Unit,
    context: Context
) {
    val viewModel = remember { LoginViewModel(context) } // Create an instance of LoginViewModel
    viewModel.initDatabaseHandlers(LocalContext.current) // Initialize database handlers

    val phoneNumber = viewModel.phoneNumber
    val password = viewModel.password
    val toastMessage = viewModel.toastMessage.value
    var isPhoneError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var phoneErrorMessage by rememberSaveable { mutableStateOf("") }
    var passwordErrorMessage by rememberSaveable { mutableStateOf("") }

    val preferencesManager = PreferencesManager(context) // Instantiate PreferencesManager

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderText(text = "Login to FST account")
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
                .padding(horizontal = 16.dp)
        ) {
            TextFieldText(text = "Phone")
            CustomTextField(
                phoneNumber = phoneNumber,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Enter your Phone Number",
                keyboardType = KeyboardType.Phone,
                isError = isPhoneError,
                errorMessage = phoneErrorMessage,
                onValueChange = {
                    isPhoneError = false
                    phoneErrorMessage = ""
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldText(text = "Password")
            CustomTextField(
                phoneNumber = password,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Enter your Password",
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
                    val createdb = CreateScreenDB(context)
                    val readablefile = createdb.readableDatabase
                    readablefile.close()
                    viewModel.login(context, {
                        preferencesManager.saveCheckInStatus(false) // Set check-in status to 0 (false)
                        toHome(it)
                    }) { phoneError, passwordError ->
                        isPhoneError = phoneError != null
                        isPasswordError = passwordError != null
                        phoneErrorMessage = phoneError ?: ""
                        passwordErrorMessage = passwordError ?: ""
                    }
                },
                buttonText = "Sign-In",
                buttonColor = Color.Black,
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