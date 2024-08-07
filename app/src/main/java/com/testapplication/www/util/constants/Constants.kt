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

    //ScreenHeaders
    const val SCREEN_SCHEDULED_VISIT = "Scheduled Visits"
    const val SCREEN_FOLLOW_UP_CALLS = "Follow Up Calls"
    const val SCREEN_LEADS = "Leads"
    const val SCREEN_HOME = "Home"
    const val SCREEN_CREATE = "Create"
    const val SCREEN_CHECK_IN = "Check-In"

    //Home Screen Leads Table Names
    const val TABLE_LEADS_CREATED = "Leads Created"
    const val TABLE_DEMOS_SCHEDULED = "Demos Scheduled"
    const val TABLE_DEMOS_COMPLETED = "Demos Completed"
    const val TABLE_LICENSES_SOLD = "Licenses Sold"

    //HomeScreen Default State
    const val DEFAULT_CHECK_IN_STATUS = false
    const val DEFAULT_LOCATION_ALERT_DIALOG = false

    //Location Alert Pop-up
    const val ALERT_TITLE = "Enable Location Services"
    const val ALERT_DESCRIPTION = "Please enable location services manually in FST app settings."
    const val ALERT_ALLOW_CTA = "Allow"
    const val DEFAULT_ALERT_POP_UP = false
    const val SHOW_ALERT_POP_UP = true

    //Alert
    const val GENERAL_ALERT_TITLE = "Alert!!!"
    const val CHECK_IN_ALERT_DESCRIPTION = "Please Check-In to Create FST"
    const val GENERAL_ALERT_ALLOW_CTA = "OK"

    //Create
    const val ADD_ICON_DESCRIPTION = "Add Icon"

    //Home Screen Icon Description


    //Item Display List Value Type
    const val FOLLOW_UP_CALL_LIST_TYPE = "call"
    const val LEADS_LIST_TYPE = ""
    const val SCHEDULED_VISIT_LIST_TYPE = "visit"
    const val SHOW_ALL_CTA = "Show All"

}