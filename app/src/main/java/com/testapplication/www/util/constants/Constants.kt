package com.testapplication.www.util.constants

object Constants {
    const val PREFERENCE_MANAGER_USER_ID_COUNT = 0
    const val DEFAULT_USER_ID = -1

    //Onboarding Constants
    const val SIGNUP_CTA_NAME = "Signup"
    const val SIGNIN_CTA_NAME = "Sign-In"

    //Signup Constants
    const val SIGNUP_SCREEN_TITLE = "Sign up to FST account"
    const val SIGNUP_PHONE_NUMBER_TEXT = "Phone"
    const val SIGNUP_PHONE_NUMBER_PLACE_HOLDER = "Enter your Phone Number"
    const val SIGNUP_PASSWORD_TEXT = "Password"
    const val SIGNUP_PASSWORD_PLACE_HOLDER = "Enter your Password"
    const val DEFAULT_CHECK_IN_VALUE = false
    const val ERROR_INFO_ICON = "Error Message"
    const val EMPTY_PHONE_NUMBER_ERROR_MSG = "Phone number cannot be empty"
    const val EMPTY_PASSWORD_ERROR_MSG = "Password cannot be empty"
    const val PHONE_NUMBER_INVALID_MSG = "Invalid phone number"
    const val PASSWORD_ERROR_MSG =
        "Password must be alphanumeric with special characters and at least 6 characters long"
    const val SUCCESSFUL_SIGNUP_TOAST = "User Added to the FST"
    const val EXISTING_USER_MSG = "User Already Exists. Please Login"

    //Signup DB Constants
    const val DB_NAME = "test_application_db"
    const val DB_VERSION = 6
    const val TABLE_NAME = "signup"
    const val ID_COL = "id"
    const val PHONE_COL = "phone"
    const val PASSWORD = "password"

}