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
    const val DB_VERSION = 7
    const val TABLE_NAME = "signup"
    const val ID_COL = "id"
    const val PHONE_COL = "phone"
    const val PASSWORD = "password"

    //ScreenHeaders
    const val SCREEN_SCHEDULED_VISIT = "Scheduled Visits"
    const val SCREEN_FOLLOW_UP_CALLS = "Follow Up Calls"
    const val SCREEN_SCHEDULED_VISIT_AND_FOLLOWUP_CALLS = "Visits & Calls"
    const val SCREEN_LEADS = "Leads"
    const val SCREEN_HOME = "Home"
    const val SCREEN_CREATE = "Create"
    const val SCREEN_CHECK_IN = "Check-In"


    //Create Screen
    const val CREATE_SCREEN_BACK_CTA_DESCRIPTION = "Back"
    const val CREATE_CUSTOMER_NAME_FIELD = "Customer Name*"
    const val CREATE_CUSTOMER_MOBILE_NO_FIELD = "Phone Number*"
    const val CREATE_CUSTOMER_ALTERNATE_MOBILE_NO_FIELD = "Alternate Phone Number"
    const val CREATE_ADDRESS_FIELD = "Address*"
    const val CREATE_PROOF_OF_MEETING_FIELD = "Proof Of Meeting*"
    const val CREATE_IMAGE_RECAPTURE_DESCRIPTION = "Re-Capture"
    const val CREATE_ATTACH_IMAGE_CTA = "Attach Image"
    const val CREATE_BUSSINESS_CATEGORY_FIELD_LIST = "BussinessCategory"
    const val CREATE_BUSSINESS_CATEGORY_FIELD = "Business Category"
    const val CREATE_CATEGORY_FIELD_LABEL_TEXT = "Select Category"
    const val CREATE_DROP_DOWN_ICON_DESCRIPTION = "Dropdown Icon"
    const val CREATE_CALL_STATUS_FIELD_LIST = "CallStatus"
    const val CREATE_CALL_STATUS_FIELD = "Call Status"
    const val CREATE_LEAD_STATUS_FIELD_LIST = "LeadStatus"
    const val CREATE_LEAD_STATUS_FIELD = "Lead Status"
    const val CREATE_FOLLOW_UP_DATE_FIELD = "Follow Up Date*"
    const val CREATE_FOLLOW_UP_TIME_FIELD = "Follow Up Time*"
    const val CREATE_FOLLOW_UP_ACTION_RADIO_BTN = "Follow Up Action*"
    const val CREATE_FOLLOW_UP_CALL_RADIO_BTN = "Call"
    const val CREATE_FOLLOW_UP_VISIT_RADIO_BTN = "Visit"
    const val CREATE_COMMENTS_FIELD = "Comments"
    const val CREATE_SAVE_BTN = "Save"


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
    const val ON_SAVE_DIALOG_DELAY = 500L

    //Alert
    const val GENERAL_ALERT_TITLE = "Alert!!!"
    const val CHECK_IN_ALERT_DESCRIPTION = "Please Check-In to Create FST"
    const val CHECK_IN_EDIT_ALERT_DESCRIPTION = "Please Check-In to Edit FST"
    const val GENERAL_ALERT_ALLOW_CTA = "OK"

    //Create
    const val ADD_ICON_DESCRIPTION = "Add Icon"

    //Home Screen Icon Description


    //Item Display List Value Type
    const val FOLLOW_UP_CALL_LIST_TYPE = "call"
    const val LEADS_LIST_TYPE = "leads"
    const val LEADS_AND_VISIT_LIST = "leadsandvisit"
    const val SPECIFIC_ITEM_LIST = "item"
    const val SCHEDULED_VISIT_LIST_TYPE = "visit"
    const val INVALID_LIST_TYPE = "Invalid valueType"
    const val SHOW_ALL_CTA = "Show All"


    //CREATE DB STRINGS
    const val CREATE_DB_NAME = "create_screen_db"
    const val CREATE_TABLE_NAME = "create_screen_data"
    const val USER_ID_COL = "user_id"
    const val CUSTOMER_NAME_COL = "customer_name"
    const val PHONE_NUMBER_COL = "phone_number"
    const val ALTERNATE_PHONE_COL = "alternate_phone_number"
    const val ADDRESS_COL = "address"
    const val BUSINESS_CATEGORY_COL = "business_category"
    const val CALL_STATUS_COL = "call_status"
    const val LEAD_STATUS_COL = "lead_status"
    const val FOLLOW_UP_DATE_COL = "follow_up_date"
    const val FOLLOW_UP_TIME_COL = "follow_up_time"
    const val FOLLOW_UP_ACTION_CALL_COL = "follow_up_action_call"  // Change column name
    const val FOLLOW_UP_ACTION_VISIT_COL = "follow_up_action_visit"  // Change column name
    const val COMMENTS_COL = "comments"
    const val CHECKIN_LONGITUDE_COL = "longitude_location" // New Column
    const val CHECKIN_LATITUDE_COL = "latitude_location" // New Column
    const val PROOF_IMAGE_COL = "proof_image" // New Column
    const val LONGITUDE_LOCATION_COL = "longitude_location" // New Column
    const val LATITUDE_LOCATION_COL = "latitude_location" // New Column

    //CREATE LEDGER
    const val CREATE_LEDGER_TABLE_NAME = "creation_ledger"
    const val ACTION_TYPE = "action_type"
    const val LEDGER_STATUS = "ledger_status"
    const val LEDGER_TIME_STAMP = "ledger_time_stamp"
    const val CREATION_ITEM_ID = "create_item_id"

    // CheckIn table constants
    const val CHECKIN_TABLE_NAME = "checkin_data"
    const val CHECKIN_ID_COL = "id"
    const val CHECKIN_USER_ID_COL = "user_id"
    const val CHECKIN_STATUS_COL = "checkin_status"
    const val CHECKIN_TIME_COL = "checkin_time"
    const val CHECKIN_IMAGE_COL = "checkin_image" // New Column

    //Image Descriptions
    const val NO_DATA_FOUND_IMAGE_DESCRIPTION = "No Data Found"


    //CreationLedgerConstants
    const val CREATION_LEDGER_SCREEN_TITLE = "Creation Ledger"
    const val LEDGER_FOLLOWING_ACTION1 = "Lead Validation"
    const val LEDGER_FOLLOWING_ACTION2 = "Lead Information"
    const val LEDGER_FOLLOWING_ACTION3 = "Customer Meeting"

    //Ledger Details

    const val LEDGER_DETAILS = "Ledger Details"

    //Ledger Details - Index
    const val LEDGER_ID = 0
    const val LEDGER_CREATE_ITEM_ID = 1
    const val LEDGER_ACTION_TYPE = 2
    const val LEDGER_DETAILS_SCREEN_STATUS = 3
    const val LEDGER_DETAILS_SCREEN_TIME_STAMP = 4
    const val LEDGER_USER_ID = 5
    const val LEDGER_CUSTOMER_NAME = 6
    const val LEDGER_PHONE_NUMBER = 7
    const val LEDGER_ALTERNATE_PHONE_NUMBER = 8
    const val LEDGER_ADDRESS = 9
    const val LEDGER_BUSSINESS_CATEGORY = 10
    const val LEDGER_CALL_STATUS = 11
    const val LEDGER_LEAD_STATUS = 12
    const val LEDGER_FOLLOWUP_DATE = 13
    const val LEDGER_FOLLOWUP_TIME = 14
    const val LEDGER_FOLLOWUP_ACTION_CALL = 15
    const val LEDGER_FOLLOWUP_ACTION_VISIT = 16
    const val LEDGER_COMMENTS = 17
    const val LEDGER_PROOF_IMAGE = 18
    const val LEDGER_LONGITUDE = 19
    const val LEDGER_LATITUDE = 20
    const val DEFAULT_LEDGER_COUNT_ID = -1

    //Ledger Screen Constants
    const val  LEDGER_ACTION_TYPE_TEXT = "Action Type: "
    const val LEDGER_ACTION_TYPE_TEXT1 = "Lead Validation"
    const val LEDGER_ACTION_TYPE_TEXT2 = "Lead Information"
    const val LEDGER_ACTION_TYPE_TEXT3 = "Customer Meet Form"
    const val LEDGER_HEADER_CUSTOMER_NAME = "Customer Name: "
    const val LEDGER_HEADER_PHONE_NUMBER = "Phone Number: "
    const val LEDGER_HEADER_ALTERNATIVE_PHONE_NUMBER = "Alternate Phone Number: "
    const val LEDGER_HEADER_BUSINESS_NAME = "Business Name: "
    const val LEDGER_HEADER_BUSINESS_TYPE = "Business Type: "
    const val LEDGER_HEADER_BUSINESS_CATEGORY = "Business Category: "
    const val LEDGER_HEADER_CURRENT_SOFTWARE = "Current Software: "
    const val LEDGER_HEADER_PREFERRED_LANGUAGE = "Preferred Language: "
    const val LEDGER_HEADER_LOCATION_ADDRESS = "Address/Location:"
    const val LEDGER_HEADER_LEAD_STATUS = "Lead Status: "
    const val LEDGER_HEADER_CALL_STATUS = "Call Status: "
    const val LEDGER_HEADER_FOLLOWUP_REQUIRED = "Follow Up Required? "
    const val LEDGER_HEADER_FOLLOWUP_DATE_AND_TIME = "Follow Up Date & Time: "
    const val LEDGER_HEADER_POS_SALE = "POS Sale: "
    const val LEDGER_HEADER_SHOP_IMAGE = "Shop Image: "
    const val LEDGER_HEADER_FOLLOWUP_NOTES = "Follow Up Notes: "




}