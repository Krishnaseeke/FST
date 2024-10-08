import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.loginscreen.LoginScreenDB
import com.testapplication.www.util.constants.Constants.EMPTY_PASSWORD_ERROR_MSG
import com.testapplication.www.util.constants.Constants.EMPTY_PHONE_NUMBER_ERROR_MSG
import com.testapplication.www.util.constants.Constants.PHONE_NUMBER_INVALID_MSG
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

    fun initDatabaseHandlers(context: Context) {
        // Initialize database handlers
        dbHandler = LoginScreenDB(context)
        dbSignUp = SignupScreenDB(context)
    }

    fun login(context: Context, toHome: (Any?) -> Unit, onValidationError: (String?, String?) -> Unit): Boolean {
        val phoneText = phoneNumber.value.text
        val passwordText = password.value.text

        val phoneError: String? = when {
            phoneText.isBlank() -> EMPTY_PHONE_NUMBER_ERROR_MSG
            !phoneText.matches("^[6-9]\\d{9}$".toRegex()) -> PHONE_NUMBER_INVALID_MSG
            else -> null
        }

        val passwordError: String? = when {
            passwordText.isBlank() -> EMPTY_PASSWORD_ERROR_MSG
            else -> null
        }

        if (phoneError != null || passwordError != null) {
            onValidationError(phoneError, passwordError)
            return true
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
                showToast(context, "Login successful")
            } else {
                onValidationError("","Credentials don't exist, Please SignUp")
            }
        }
        return false
    }


    private fun showToast(context: Context, message: String) {
        toastMessage.value = message
    }
}
