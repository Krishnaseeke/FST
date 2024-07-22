package com.testapplication.www.signupscreen

import SignupScreenDB
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.loginscreen.LoginScreenDB
import com.testapplication.www.util.constants.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel(context: Context): ViewModel()  {
    private lateinit var dbHandler: LoginScreenDB
    private lateinit var dbSignUp: SignupScreenDB
    private val preferencesManager = PreferencesManager(context)

    val phoneNumber: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue())
    val password: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue())
    val toastMessage: MutableState<String?> = mutableStateOf(null)

    init {
        // Initialize ViewModelScope with IO Dispatcher
        viewModelScope.launch(Dispatchers.IO) {
            // Initialize database handlers in the background
            initDatabaseHandlers(context)
        }
    }

    fun initDatabaseHandlers(context: Context) {
        // Initialize database handlers
        dbHandler = LoginScreenDB(context)
        dbSignUp = SignupScreenDB(context)
    }

    fun signup(context: Context, toHome: (Any?) -> Unit, onValidationError: (String?, String?) -> Unit): Boolean {
        val phoneText = phoneNumber.value.text
        val passwordText = password.value.text

        val phoneError: String? = when {
            phoneText.isBlank() -> Constants.EMPTY_PHONE_NUMBER_ERROR_MSG
            !phoneText.matches("^[6-9]\\d{9}$".toRegex()) -> Constants.PHONE_NUMBER_INVALID_MSG
            else -> null
        }

        val passwordError: String? = when {
            passwordText.isBlank() -> Constants.EMPTY_PASSWORD_ERROR_MSG
            !passwordText.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}\$".toRegex()) -> Constants.PASSWORD_ERROR_MSG
            else -> null
        }

        if (phoneError != null || passwordError != null) {
            onValidationError(phoneError, passwordError)
            return true
        }

        viewModelScope.launch {
            val phoneLong = phoneText.toLong()
            val signupSuccessful = withContext(Dispatchers.IO) {
                dbSignUp.signup(phoneLong, passwordText)
            }

            if (signupSuccessful) {
                val userId = withContext(Dispatchers.IO) {
                    dbSignUp.getUserIdByPhoneNumber(phoneLong)
                }
                preferencesManager.saveUserId(userId)
                toHome(userId)
                showToast(context, Constants.SUCCESSFUL_SIGNUP_TOAST)
            } else {
                onValidationError("", Constants.EXISTING_USER_MSG)
            }
        }
        return false
    }


    private fun showToast(context: Context, message: String) {
        toastMessage.value = message
    }
}