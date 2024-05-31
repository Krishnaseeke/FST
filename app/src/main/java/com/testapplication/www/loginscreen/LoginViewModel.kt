import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.loginscreen.LoginScreenDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(context: Context) : ViewModel() {
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

    public fun initDatabaseHandlers(context: Context) {
        // Initialize database handlers
        dbHandler = LoginScreenDB(context)
        dbSignUp = SignupScreenDB(context)
    }

    fun login(context: Context, toHome: (Any?) -> Unit) {
        val phoneText = phoneNumber.value.text
        val passwordText = password.value.text

        if (phoneText.isBlank() && passwordText.isBlank()) {
            showToast(context, "Fields cannot be empty")
            return
        }else if(phoneText.isBlank() ){
            showToast(context, "Phone Number cannot be empty")
            return
        }else if(passwordText.isBlank()){
            showToast(context, "Password cannot be empty")
            return
        }

        if (!phoneText.matches("\\d{10}".toRegex())) {
            showToast(context, "Invalid phone number")
            return
        }

        viewModelScope.launch {
            val loginSuccessful = withContext(Dispatchers.IO) {
                val phoneLong = phoneText.toLong()
                dbHandler.validateLogin(phoneLong, passwordText)
            }

            if (loginSuccessful) {
                val userId = withContext(Dispatchers.IO) {
                    dbSignUp.getUserIdByPhoneNumber(phoneText.toLong())
                }
                preferencesManager.saveUserId(userId)
                toHome(userId)
                showToast(context, "Logged in successfully")
            } else {
                showToast(context, "Failed to login")
            }
        }
    }

    fun signup(context: Context, toHome: (Any?) -> Unit) {
        val phoneText = phoneNumber.value.text
        val passwordText = password.value.text

        if (phoneText.isBlank() || passwordText.isBlank()) {
            showToast(context, "Fields cannot be empty")
            return
        }

        if (!phoneText.matches("\\d{10}".toRegex())) {
            showToast(context, "Invalid phone number")
            return
        }

        if (!passwordText.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}\$".toRegex())) {
            showToast(context, "Password must be alphanumeric with special characters and at least 6 characters long")
            return
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
                showToast(context, "User Added to the FST")
            } else {
                showToast(context, "User Already Exists. Please Login")
            }
        }
    }


    private fun showToast(context: Context, message: String) {
        toastMessage.value = message
    }
}
