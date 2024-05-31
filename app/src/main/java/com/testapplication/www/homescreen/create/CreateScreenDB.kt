import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CreateScreenDB(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID_COL + " INTEGER, "
                + CUSTOMER_NAME_COL + " TEXT, "
                + PHONE_NUMBER_COL + " TEXT, "
                + ALTERNATE_PHONE_COL + " TEXT, "
                + ADDRESS_COL + " TEXT, "
                + BUSINESS_CATEGORY_COL + " TEXT, "
                + CALL_STATUS_COL + " TEXT, "
                + LEAD_STATUS_COL + " TEXT, "
                + FOLLOW_UP_DATE_COL + " TEXT, "
                + FOLLOW_UP_TIME_COL + " TEXT, "
                + FOLLOW_UP_ACTION_CALL_COL + " INTEGER, "
                + FOLLOW_UP_ACTION_VISIT_COL + " INTEGER, "
                + COMMENTS_COL + " TEXT)")

        val createCheckInTableQuery = ("CREATE TABLE " + CHECKIN_TABLE_NAME + " ("
                + CHECKIN_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CHECKIN_USER_ID_COL + " INTEGER, "
                + CHECKIN_STATUS_COL + " INTEGER, "
                + CHECKIN_TIME_COL + " TEXT)")

        db.execSQL(createTableQuery)
        db.execSQL(createCheckInTableQuery)
    }

    fun createFST(
        userId: Long,
        customerName: String?,
        phoneNumber: String?,
        alternatePhoneNumber: String?,
        address: String?,
        businessCategory: String?,
        callStatus: String?,
        leadStatus: String?,
        followUpDate: String?,
        followUpTime: String?,
        followUpActionCall: Int,
        followUpActionVisit: Int,
        comments: String?
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(USER_ID_COL, userId)
            put(CUSTOMER_NAME_COL, customerName)
            put(PHONE_NUMBER_COL, phoneNumber)
            put(ALTERNATE_PHONE_COL, alternatePhoneNumber)
            put(ADDRESS_COL, address)
            put(BUSINESS_CATEGORY_COL, businessCategory)
            put(CALL_STATUS_COL, callStatus)
            put(LEAD_STATUS_COL, leadStatus)
            put(FOLLOW_UP_DATE_COL, followUpDate)
            put(FOLLOW_UP_TIME_COL, followUpTime)
            put(FOLLOW_UP_ACTION_CALL_COL, followUpActionCall)
            put(FOLLOW_UP_ACTION_VISIT_COL, followUpActionVisit)
            put(COMMENTS_COL, comments)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
        return true
    }

    fun updateFST(
        itemId: Long?,
        customerName: String?,
        phoneNumber: String?,
        alternatePhoneNumber: String?,
        address: String?,
        businessCategory: String?,
        callStatus: String?,
        leadStatus: String?,
        followUpDate: String?,
        followUpTime: String?,
        followUpActionCall: Int,
        followUpActionVisit: Int,
        comments: String?
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(CUSTOMER_NAME_COL, customerName)
            put(PHONE_NUMBER_COL, phoneNumber)
            put(ALTERNATE_PHONE_COL, alternatePhoneNumber)
            put(ADDRESS_COL, address)
            put(BUSINESS_CATEGORY_COL, businessCategory)
            put(CALL_STATUS_COL, callStatus)
            put(LEAD_STATUS_COL, leadStatus)
            put(FOLLOW_UP_DATE_COL, followUpDate)
            put(FOLLOW_UP_TIME_COL, followUpTime)
            put(FOLLOW_UP_ACTION_CALL_COL, followUpActionCall)
            put(FOLLOW_UP_ACTION_VISIT_COL, followUpActionVisit)
            put(COMMENTS_COL, comments)
        }

        val rowsUpdated = db.update(TABLE_NAME, values, "$ID_COL = ?", arrayOf(itemId.toString()))
        db.close()
        return rowsUpdated > 0
    }

    fun insertCheckIn(userId: Long?, checkInStatus: Int, checkInTime: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(CHECKIN_USER_ID_COL, userId)
            put(CHECKIN_STATUS_COL, checkInStatus)
            put(CHECKIN_TIME_COL, checkInTime)
        }
        val result = db.insert(CHECKIN_TABLE_NAME, null, values)
        db.close()
        // Return true if insert was successful (result is not -1), otherwise return false
        return result != -1L
    }

    fun getCheckInStatus(userId: Long?): Int {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            CHECKIN_TABLE_NAME,
            arrayOf(CHECKIN_STATUS_COL),
            "$CHECKIN_USER_ID_COL = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )
        var checkInStatus = -1
        while (cursor.moveToLast()) {
            checkInStatus = cursor.getInt(cursor.getColumnIndexOrThrow(CHECKIN_STATUS_COL))
        }
        cursor.close()
        db.close()
        return checkInStatus
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $CHECKIN_TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "create_screen_db"
        private const val DB_VERSION = 3 // Incremented version to trigger onUpgrade
        private const val TABLE_NAME = "create_screen_data"
        private const val ID_COL = "id"
        private const val USER_ID_COL = "user_id"
        private const val CUSTOMER_NAME_COL = "customer_name"
        private const val PHONE_NUMBER_COL = "phone_number"
        private const val ALTERNATE_PHONE_COL = "alternate_phone_number"
        private const val ADDRESS_COL = "address"
        private const val BUSINESS_CATEGORY_COL = "business_category"
        private const val CALL_STATUS_COL = "call_status"
        private const val LEAD_STATUS_COL = "lead_status"
        private const val FOLLOW_UP_DATE_COL = "follow_up_date"
        private const val FOLLOW_UP_TIME_COL = "follow_up_time"
        private const val FOLLOW_UP_ACTION_CALL_COL = "follow_up_action_call"
        private const val FOLLOW_UP_ACTION_VISIT_COL = "follow_up_action_visit"
        private const val COMMENTS_COL = "comments"

        // CheckIn table constants
        private const val CHECKIN_TABLE_NAME = "checkin_data"
        private const val CHECKIN_ID_COL = "id"
        private const val CHECKIN_USER_ID_COL = "user_id"
        private const val CHECKIN_STATUS_COL = "checkin_status"
        private const val CHECKIN_TIME_COL = "checkin_time"
    }
}
