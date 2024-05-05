package com.testapplication.www.loginscreen

import LoginViewModel
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testapplication.www.util.CustomButton
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
                placeholder = "Enter your Phone Number"
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldText(text = "Password")
            CustomTextField(
                phoneNumber = password,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Enter your Password"
            )

            Spacer(modifier = Modifier.height(25.dp))

            CustomButton(
                onClick = {
                    viewModel.login(context) { userId ->
                        toHome(userId)
                    }
                },
                buttonText = "Sign-In",
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
