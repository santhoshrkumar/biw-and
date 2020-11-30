package com.centurylink.biwf.analytics

/**
 * Common Class All the Analytics keys will be configured
 *
 * @constructor Create empty Analytics keys
 */
class AnalyticsKeys {
    companion object {
        /***Event Tags***/
        const val EVENT_TYPE_SCREEN_LAUNCH = "screen_launch"
        const val EVENT_TYPE_BUTTON_CLICKED = "button_clicked"
        const val EVENT_TYPE_CARD_SELECTED = "card_selected"
        const val EVENT_TYPE_API_CALL = "api_call"
        const val EVENT_TYPE_TOGGLE_STATE_CHANGED = "toggle_state_changed"
        const val EVENT_BIOMETRICS_LOGIN = "biometrics_login"
        const val EVENT_TYPE_LIST_CLICKED = "list_item_clicked"

        /***Screens***/
        /*Account Module*/
        const val SCREEN_CANCEL_SUBSCRIPTION_DETAILS = "Cancel Subscription Details Screen"
        const val SCREEN_CANCEL_SUBSCRIPTION = "Cancel Subscription Screen"
        const val SCREEN_SUBSCRIPTION = "Subscription Screen"
        const val SCREEN_PERSONAL_INFO = "Personal Info Screen"
        const val SCREEN_ACCOUNTS = "Accounts Screen"
        const val SCREEN_SUBSCRIPTION_STATEMENT = "Invoice Statement Screen"
        const val SCREEN_EDIT_PAYMENT_DETAILS = "Edit Payment Details Screen"
        /*Support Module*/
        const val SCREEN_SUPPORT = "Support Screen"
        const val SCREEN_FAQ = "FAQ Screen"
        const val SCREEN_SCHEDULE_CALLBACK_SUPPORT = "Schedule-Callback Screen"
        const val SCREEN_ADDITIONAL_INFO = "Additional Info Screen"
        /*Device Module*/
        const val SCREEN_DEVICES = "Devices Screen"
        const val SCREEN_DEVICE_DETAILS = "Devices Details Screen"
        /*Dashboard Module*/
        const val SCREEN_DASHBOARD = "Dashboard Screen"
        const val SCREEN_MODIFY_APPOINTMENT = "Modify Appointment Screen"
        const val APPOINTMENT_CONFIRMATION_SCREEN = "Appointment Confirmation Screen"
        const val SCREEN_SCHEDULE_APPOINTMENT = "Appointment Scheduled/Dispatched Screen"
        const val SCREEN_ENROUTE_APPOINTMENT = "Technician Enroute Screen"
        const val SCREEN_IN_PROGRESS_APPOINTMENT = "Work In Progress Screen"
        const val SCREEN_COMPLETED_APPOINTMENT = "Installation Completed Screen"
        const val SCREEN_CANCELLED_APPOINTMENT = "Appointment Cancelled Screen"
        const val SCREEN_NETWORK_INFORMATION = "Network Information Screen"

        /***Buttons***/
        /*Account Module*/
        const val BUTTON_CONTINUE_CANCELLATION = "Cancel Subscription Button"
        const val BUTTON_LOG_OUT = "Logout Button"
        const val BUTTON_DONE_PERSONAL_INFO = "Done Button Personal Info"
        const val BUTTON_DONE_SUBSCRIPTION_SCREEN = "Done Button Subscription"
        const val BUTTON_DONE_EDIT_PAYMENT_DETAILS = "Done Button Edit Payment Details"
        const val BUTTON_BACK_EDIT_PAYMENT_DETAILS = "Back Button Edit Payment Details"
        const val BUTTON_DONE_PREVIOUS_STATEMENT = "Done Button Previous Statements"
        const val BUTTON_BACK_PREVIOUS_STATEMENT = "Back Button Previous Statements"
        const val BUTTON_CANCEL_CANCEL_SUBSCRIPTION = "Cancel Button Cancel Subscription"
        const val BUTTON_BACK_CANCEL_SUBSCRIPTION = "Back Button Cancel Subscription"
        const val BUTTON_BACK_CANCEL_SUBSCRIPTION_CONFIRMATION = "Back Button Cancel Subscription Confirmation"
        const val BUTTON_CANCEL_CANCEL_SUBSCRIPTION_CONFIRMATION = "Cancel Button Cancel Subscription Confirmation"
        const val BUTTON_MANAGE_SUBSCRIPTION = "Manage Subscription Button"
        const val BUTTON_PREVIOUS_STATEMENT = "Previous Statements Button"
        const val BUTTON_EDIT_BILLING_INFO = "Edit Billing Info Button"
        const val EXPANDABLE_LIST_DEVICES = "Connected devices expandable list"
        const val BUTTON_REMOVE_DEVICES_DEVICE_DETAILS = "Remove Devices Button Device Details"
        /*Devices Module*/
        const val BUTTON_PAUSE_CONNECTION_DEVICE_SCREEN = "Pause Connection Device Screen"
        const val BUTTON_RESUME_CONNECTION_DEVICE_SCREEN = "Resume Connection Device Screen"
        const val BUTTON_PAUSE_CONNECTION_DEVICE_DETAILS = "Pause Connection Device Details"
        const val BUTTON_RESUME_CONNECTION_DEVICE_DETAILS = "Resume Connection Device Details"
        const val BUTTON_DEVICE_CONNECTION_STATUS_DEVICE_DETAILS = "Connection Status Button Devices Details"
        const val BUTTON_DONE_DEVICE_DETAILS = "Done Button Devices Details"
        const val BUTTON_SUBMIT_CANCEL_SUBSCRIPTION_CONFIRMATION = "Submit Button Cancel Subscription Confirmation"
        /*Support Module*/
        const val BUTTON_SUPPORT_HOME_SCREEN = "Button Support Home Screen"
        const val BUTTON_DONE_SUPPORT = "Done Button Support"
        const val FAQ_ITEM_SUPPORT = "FAQ Item Support"
        const val LIVE_CHAT_SUPPORT = "Live Chat Support"
        const val SCHEDULE_A_CALLBACK_SUPPORT = "Schedule a Callback Support"
        const val BUTTON_RUN_SPEED_TEST_SUPPORT = "Run Speed Test Button Support"
        const val BUTTON_RESTART_MODEM_SUPPORT = "Restart Modem Button Support"
        const val BUTTON_VISIT_WEBSITE_SUPPORT = "Visit Website Button Support"
        const val BUTTON_CANCEL_SCHEDULE_CALLBACK = "Cancel Button Schedule-Callback Screen"
        const val BUTTON_BACK_SCHEDULE_CALLBACK = "Back Button Schedule-Callback Screen"
        const val BUTTON_CALL_US_SCHEDULE_CALLBACK = "Call us now Schedule-Callback Screen"
        /*Support & FAQ Module*/
        const val BUTTON_DONE_FAQ_DETAILS = "Done Button FAQ Details Screen"
        const val BUTTON_BACK_FAQ_DETAILS = "Back Button FAQ Details Screen"
        const val BUTTON_LIVE_CHAT_FAQ_DETAILS = "Live Chat FAQ Details Screen"
        const val BUTTON_SCHEDULE_A_CALLBACK_FAQ_DETAILS = "Schedule a Callback FAQ Details Screen"
        const val BUTTON_NEXT_ADDITIONAL_INFO = "Next Button Additional Info Screen"
        const val BUTTON_CANCEL_ADDITIONAL_INFO = "Cancel Button Additional Info Screen"
        const val BUTTON_BACK_ADDITIONAL_INFO = "Back Button Additional Info Screen"
        const val EXPAND_LIST_FAQ_DETAILS = "FAQ Details list expand"
        const val COLLAPSE_LIST_FAQ_DETAILS = "FAQ Details list collapse"
        /*Dashboard Module*/
        const val BUTTON_RUN_SPEED_TEST_DASHBOARD = "Run Speed Test Button Dashboard"
        const val BUTTON_CONNECTED_DEVICES_DASHBOARD = "Visit Connected Devices Dashboard"
        const val BUTTON_CHANGE_APPOINTMENT_DASHBOARD = "Change Appointment Dashboard"
        const val BUTTON_CANCEL_APPOINTMENT_DASHBOARD = "Cancel Appointment Confirmation Popup"
        const val BUTTON_GET_STARTED_DASHBOARD = "Get Started Button Dashboard"
        const val BUTTON_BACK_CHANGE_APPOINTMENT = "Back Button Modify Appointment"
        const val BUTTON_DONE_NETWORK_INFORMATION = "Done Button Network Information"
        const val BUTTON_DONE_QR_CODE = "Done Button Join QR Code Screen"
        const val WIFI_NETWORK_STATE_CHANGE_NETWORK_INFORMATION = "Wifi Network State Change Network Information"
        const val GUEST_NETWORK_STATE_CHANGE_NETWORK_INFORMATION = "Guest Network State Change Network Information"
        const val BUTTON_NEXT_CHANGE_APPOINTMENT = "Next Button Modify Appointment"
        const val BUTTON_DONE_APPOINTMENT_BOOKED = "Done Button Appointment Confirmation"
        const val BUTTON_VIEW_DASHBOARD_APPOINTMENT_BOOKED = "View my Dashboard Appointment Confirmation"
        const val BUTTON_DISMISS_NOTIFICATION = "Dismiss Notification Dashboard"
        const val ALERT_ENABLE_BIOMETRICS_OK = "Enable Biometrics Ok Popup"
        const val ALERT_ENABLE_BIOMETRICS_DONT_ALLOW = "Enable Biometrics Don't Allow Popup"
        const val ALERT_KEEP_CANCEL_APPOINTMENT_CONFIRMATION = "Keep It Cancel Appointment Confirmation"
        const val ALERT_CANCEL_CANCEL_APPOINTMENT_CONFIRMATION = "Cancel It Cancel Appointment Confirmation"
        const val DATE_TAP_CHANGE_APPOINTMENT = "Date Selected Modify Appointment"
        const val SLOT_TAP_CHANGE_APPOINTMENT = "Slot Selected Modify Appointment"

        /*TODO: For later use
        const val BUTTON_NOTIFICATION_ICON_DASHBOARD = "Notification Icon Dashboard"
        const val BUTTON_RESTART_MODEM_DASHBOARD = "Restart Modem Button Dashboard"
        const val BUTTON_VISIT_WEBSITE_DASHBOARD = "Visit Website Modem Button Dashboard"*/

        /***Alert Dialog***/
        const val ALERT_UPDATE_EMAIL_INFO = "Change Email Info Popup"
        const val ALERT_REMOVED_DEVICES_RESTORE_ACCESS = "Restore-Access Removed Devices Confirmation"
        const val ALERT_REMOVED_DEVICES_CANCEL_ACCESS = "Cancel Restore-Access Removed Devices Confirmation"
        const val ALERT_REMOVE_CONFIRMATION_USAGE_DETAILS = "Remove Device Confirmation Devices Details"
        const val ALERT_CANCEL_CONFIRMATION_USAGE_DETAILS = "Cancel Confirmation Devices Details"
        const val ALERT_CANCEL_SUBSCRIPTION_KEEP_SERVICE = "Keep Service Cancel Subscription Confirmation"
        const val ALERT_CANCEL_SUBSCRIPTION_CANCEL_SERVICE = "Cancel Service Cancel Subscription Confirmation"
        const val ALERT_RESTART_MODEM_SUCCESS = "Restart Modem Success Popup"
        const val ALERT_RESTART_MODEM_FAILURE = "Restart Modem Failure Popup"
        const val ERROR_POPUP_NETWORK_INFORMATION = "Discard Changes and Close Clicked"
        const val ALERT_SAVE_CLICK_NETWORK_INFORMATION = "Save Button Changes Clicked"
        const val ALERT_DISCARD_CLICK_NETWORK_INFORMATION = "Popup Button Discard Changes Clicked"

        /***Card View***/
        const val CARD_SUBSCRIPTION_INFO = "Subscription Info"
        const val CARD_PERSONAL_INFO = "Personal Info"
        const val CARD_NETWORK_INFO = "Network Info Card"
        const val QR_IMAGE = "QR Image Dashboard"

        /***Toggle***/
        const val TOGGLE_BIOMETRIC = "Biometric"
        const val TOGGLE_MARKETING_CALLS_AND_TEXT = "Marketing calls & texts"
        const val TOGGLE_SERVICE_CALLS_AND_TEXT = "Service calls & texts"
        const val TOGGLE_MARKETING_EMAILS = "Marketing Emails"

        /***List***/
        const val LIST_ITEM_CONNECTED_DEVICES = "List item clicked Connected devices list"
        const val LIST_ITEM_REMOVED_DEVICES = "List item clicked Removed devices list"
        const val LIST_ITEM_SCHEDULE_CALLBACK = "List item Schedule-Callback Screen"

        /***API Calls***/
        /*Account Module*/
        const val GET_ACCOUNT_DETAILS_SUCCESS = "Account Details Api Success"
        const val GET_ACCOUNT_DETAILS_FAILURE = "Account Details Api Failure"
        const val GET_CONTACT_DETAILS_SUCCESS = "Contact Api Success"
        const val GET_CONTACT_DETAILS_FAILURE = "Contact Api Failure"
        const val GET_INVOICES_LIST_SUCCESS = "Invoices List Api Success"
        const val GET_INVOICES_LIST_FAILURE = "Invoices List  Api Failure"
        const val GET_LIVE_CARD_INFO_SUCCESS = "Live Card Info Api Success"
        const val GET_LIVE_CARD_INFO_FAILURE = "Live Card Info Api Failure"
        const val LOG_OUT_SUCCESS = "Logout Success"
        const val LOG_OUT_FAILURE = "Logout  Failure"
        const val RESET_PASSWORD_SUCCESS = "Reset Password Api Success"
        const val RESET_PASSWORD_FAILURE = "Reset Password Api  Failure"
        const val RECORD_TYPE_ID_SUCCESS = "Record Type Id Api Success"
        const val RECORD_TYPE_ID_FAILURE = "Record Type Id Api  Failure"
        const val GET_USER_DETAILS_SUCCESS = "User Details Api Success"
        const val GET_USER_DETAILS_FAILURE = "User Details Api Failure"
        const val GET_SUBSCRIPTION_DATE_SUCCESS = "Subscription Date Api Success"
        const val GET_SUBSCRIPTION_DATE_FAILURE = "Subscription Date Api Failure"
        const val GET_PAYMENT_INFO_SUCCESS = "Payment Info Api Success"
        const val GET_PAYMENT_INFO_FAILURE = "Payment Info Api Failure"
        const val POST_CASE_FOR_SUBSCRIPTION_SUCCESS = "Submit Case For Subscription Success"
        const val POST_CASE_FOR_SUBSCRIPTION_FAILURE = "Submit Case For Subscription Failure"
        /*Devices Module*/
        const val GET_DEVICES_DETAILS_SUCCESS = "Devices Details Api Success"
        const val GET_DEVICES_DETAILS_FAILURE = "Devices Details Api Failure"
        const val UNBLOCK_DEVICE_SUCCESS = "UnBlock Device Api Success"
        const val UNBLOCK_DEVICE_FAILURE = "UnBlock Device  Api Failure"
        const val BLOCK_DEVICE_SUCCESS = "Block Device Api Success"
        const val BLOCK_DEVICE_FAILURE = "Block Device  Api Failure"
        const val GET_MODEM_INFO_SUCCESS = "Modem Info Api Success"
        const val GET_MODEM_INFO_FAILURE = "Modem Info Api Failure"
        const val GET_USAGE_DETAILS_DAILY_SUCCESS = "Usage Details Daily Api Success"
        const val GET_USAGE_DETAILS_DAILY_FAILURE = "Usage Details Daily Api Failure"
        const val GET_USAGE_DETAILS_MONTHLY_SUCCESS = "Usage Details BiWeekly Api Success"
        const val GET_USAGE_DETAILS_MONTHLY_FAILURE = "Usage Details BiWeekly Api Failure"
        /*Support & FAQ Module*/
        const val GET_RECORD_TYPE_ID_SUCCESS = "Record Type Id Api Success"
        const val GET_RECORD_TYPE_ID_FAILURE = "Record Type Id Api Failure"
        const val GET_FAQ_QUESTION_DETAILS_SUCCESS = "FAQ Question Api Success"
        const val GET_FAQ_QUESTION_DETAILS_FAILURE = "FAQ Question Api Failure"
        const val START_SPEED_TEST_SUCCESS = "Start Speed Test Api Success"
        const val START_SPEED_TEST_FAILURE = "Start Speed Test Api Failure"
        const val CHECK_SPEED_TEST_SUCCESS = "Check Speed Test Api Success"
        const val CHECK_SPEED_TEST_FAILURE = "Check Speed Test Api Failure"
        const val GET_UPSTREAM_RESULTS_SUCCESS = "Get Upstream Results Api Success"
        const val GET_UPSTREAM_RESULTS_FAILURE = "Get Upstream Results Api Failure"
        const val GET_DOWNSTREAM_RESULT_SUCCESS = "Get Downstream Results Api Success"
        const val GET_DOWNSTREAM_RESULT_FAILURE = "Get Downstream Results Api Failure"
        /*Dashboard Module*/
        const val GET_WIFI_LIST_AND_CREDENTIALS_SUCCESS = "Get Wifi List And Credentials Api Success"
        const val GET_WIFI_LIST_AND_CREDENTIALS_FAILURE = "Get Wifi List And Credentials Api Failure"
        const val CANCEL_APPOINTMENT_SUCCESS = "Cancel Appointment Api Success"
        const val CANCEL_APPOINTMENT_FAILURE = "Cancel Appointment Api Failure"
        const val DISABLE_NETWORK_SUCCESS = "Disable Network Api Success"
        const val DISABLE_NETWORK_FAILURE = "Disable Network Api Failure"
        const val ENABLE_NETWORK_SUCCESS = "Enable Network Api Success"
        const val ENABLE_NETWORK_FAILURE = "Enable Network Api Failure"
        const val REQUEST_TO_GET_NETWORK_SUCCESS = "Request to Get Network Api Success"
        const val REQUEST_TO_GET_NETWORK_FAILURE = "Request to Get Network Api Failure"
        const val MODIFY_APPOINTMENT_INFO_SUCCESS = "Modify Appointment Info Api Success"
        const val MODIFY_APPOINTMENT_INFO_FAILURE = "Modify Appointment Info Api Failure"
        const val GET_APPOINTMENT_INFO_SUCCESS = "Get Appointment Info Api Success"
        const val GET_APPOINTMENT_INFO_FAILURE = "Get Appointment Info Api Failure"
        const val GET_APPOINTMENT_SLOTS_SUCCESS = "Get Appointment Slots Api Success"
        const val GET_APPOINTMENT_SLOTS_FAILURE = "Get Appointment Slots Api Failure"
        const val UPDATE_NETWORK_NAME_SUCCESS = "Update Network Name Api Success"
        const val UPDATE_NETWORK_NAME_FAILURE = "Update Network Name Api Failure"
        const val UPDATE_NETWORK_PASSWORD_SUCCESS = "Update Network Password Api Success"
        const val UPDATE_NETWORK_PASSWORD_FAILURE = "Update Network Password Api Failure"
    }
}
