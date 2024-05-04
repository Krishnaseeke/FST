package com.testapplication.www.signupscreen


import LoginViewModel
import SignupScreenDB
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testapplication.www.util.CustomButton
import com.testapplication.www.util.CustomTextField
import com.testapplication.www.util.HeaderText
import com.testapplication.www.util.TextFieldText

@Composable
fun SignupScreen(
    toOnboarding: () -> Unit,
    toHome: (Any?) -> Unit,
    context: Context,
    modifier: Modifier = Modifier
) {
    val viewModel = remember { LoginViewModel(context) } // Create an instance of LoginViewModel
    viewModel.initDatabaseHandlers(LocalContext.current) // Initialize database handlers


    val phoneNumber = viewModel.phoneNumber
    val password = viewModel.password
    val toastMessage = viewModel.toastMessage.value


    val isButtonEnabled = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            HeaderText(text = "SignUp to FST account")
        }
        Spacer(modifier = Modifier.height(3.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
                .padding(start = 10.dp, end = 10.dp)
        ) {
            var dbHandler: SignupScreenDB = SignupScreenDB(context)

            TextFieldText(text = "Phone")
            CustomTextField(
                phoneNumber = phoneNumber,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Enter your Phone Number"
            )
            TextFieldText(text = "Password")
            CustomTextField(
                phoneNumber = password,
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Enter your Password"
            )

            CustomButton(
                onClick = {
                    viewModel.signup(context) { userId ->
                        toHome(userId)
                    }
                },
                buttonText = "Sign-up",
                buttonColor = Color.LightGray,
                textColor = Color.White,
                buttonHeight = 70.dp,
                textSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            toastMessage?.let {
                Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
