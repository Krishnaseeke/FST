package com.testapplication.www.signupscreen


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
    val phoneNumber = remember {
        mutableStateOf(TextFieldValue())
    }
    val password = remember {
        mutableStateOf(TextFieldValue())
    }

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
                    val phoneText = phoneNumber.value.text
                    val passwordText = password.value.text

                    if (phoneText.isBlank() || passwordText.isBlank()) {
                        Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                        return@CustomButton
                    }

                    if (!phoneText.matches("\\d{10}".toRegex())) {
                        Toast.makeText(context, "Invalid phone number", Toast.LENGTH_SHORT).show()
                        return@CustomButton
                    }

                    if (!passwordText.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}\$".toRegex())) {
                        Toast.makeText(context, "Password must be alphanumeric with special characters and at least 6 characters long", Toast.LENGTH_SHORT).show()
                        return@CustomButton
                    }

                    val phoneLong = phoneText.toLong()
                    val booleanValue = dbHandler.signup(phoneLong, passwordText)
                    if (booleanValue) {
                        val userId = dbHandler.getUserIdByPhoneNumber(phoneLong)
                        toHome(userId)
                        Toast.makeText(context, "User Added to the FST", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "User Already Exists. Please Login", Toast.LENGTH_SHORT).show()
                    }
                },
                buttonText = "Sign-up",
                buttonColor = Color.LightGray,
                textColor = Color.White,
                buttonHeight = 70.dp,
                textSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
