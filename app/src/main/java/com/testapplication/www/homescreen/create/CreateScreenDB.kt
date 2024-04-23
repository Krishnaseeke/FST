import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class CreateScreenDB(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_ID_COL + " INTEGER, "
                + CUSTOMER_NAME_COL + " TEXT, "
                + PHONE_NUMBER_COL + " TEXT, "  // Change data type to TEXT
                + ALTERNATE_PHONE_COL + " TEXT, "  // Change data type to TEXT
                + ADDRESS_COL + " TEXT, "
                + BUSINESS_CATEGORY_COL + " TEXT, "
                + CALL_STATUS_COL + " TEXT, "
                + LEAD_STATUS_COL + " TEXT, "
                + FOLLOW_UP_DATE_COL + " TEXT, "  // Change data type to TEXT
                + FOLLOW_UP_TIME_COL + " TEXT, "  // Change data type to TEXT
                + FOLLOW_UP_ACTION_CALL_COL + " INTEGER, "  // Change data type to INTEGER
                + FOLLOW_UP_ACTION_VISIT_COL + " INTEGER, "  // Change data type to INTEGER
                + COMMENTS_COL + " TEXT)")

        db.execSQL(query)
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
        followUpDate: String,
        followUpTime: String?,
        followUpActionCall: String,  // Change parameter name and data type to Int
        followUpActionVisit: String,  // Change parameter name and data type to Int
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

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "create_screen_db"
        private const val DB_VERSION = 1
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
        private const val FOLLOW_UP_ACTION_CALL_COL = "follow_up_action_call"  // Change column name
        private const val FOLLOW_UP_ACTION_VISIT_COL = "follow_up_action_visit"  // Change column name
        private const val COMMENTS_COL = "comments"
    }
}
