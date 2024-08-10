import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.testapplication.www.util.constants.Constants.ADDRESS_COL
import com.testapplication.www.util.constants.Constants.ALTERNATE_PHONE_COL
import com.testapplication.www.util.constants.Constants.BUSINESS_CATEGORY_COL
import com.testapplication.www.util.constants.Constants.CALL_STATUS_COL
import com.testapplication.www.util.constants.Constants.CHECKIN_ID_COL
import com.testapplication.www.util.constants.Constants.CHECKIN_IMAGE_COL
import com.testapplication.www.util.constants.Constants.CHECKIN_LATITUDE_COL
import com.testapplication.www.util.constants.Constants.CHECKIN_LONGITUDE_COL
import com.testapplication.www.util.constants.Constants.CHECKIN_STATUS_COL
import com.testapplication.www.util.constants.Constants.CHECKIN_TABLE_NAME
import com.testapplication.www.util.constants.Constants.CHECKIN_TIME_COL
import com.testapplication.www.util.constants.Constants.CHECKIN_USER_ID_COL
import com.testapplication.www.util.constants.Constants.COMMENTS_COL
import com.testapplication.www.util.constants.Constants.CREATE_DB_NAME
import com.testapplication.www.util.constants.Constants.CREATE_TABLE_NAME
import com.testapplication.www.util.constants.Constants.CUSTOMER_NAME_COL
import com.testapplication.www.util.constants.Constants.DB_VERSION
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_ACTION_CALL_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_ACTION_VISIT_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_DATE_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_TIME_COL
import com.testapplication.www.util.constants.Constants.ID_COL
import com.testapplication.www.util.constants.Constants.LATITUDE_LOCATION_COL
import com.testapplication.www.util.constants.Constants.LEAD_STATUS_COL
import com.testapplication.www.util.constants.Constants.LONGITUDE_LOCATION_COL
import com.testapplication.www.util.constants.Constants.PHONE_NUMBER_COL
import com.testapplication.www.util.constants.Constants.PROOF_IMAGE_COL
import com.testapplication.www.util.constants.Constants.USER_ID_COL

class CreateScreenDB(context: Context?) :
    SQLiteOpenHelper(context, CREATE_DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE " + CREATE_TABLE_NAME + " ("
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
                + COMMENTS_COL + " TEXT, "
                + PROOF_IMAGE_COL + " TEXT, "  // New Column
                + LONGITUDE_LOCATION_COL + " TEXT, " // New Column
                + LATITUDE_LOCATION_COL + " TEXT)") // New Column

        val createCheckInTableQuery = ("CREATE TABLE " + CHECKIN_TABLE_NAME + " ("
                + CHECKIN_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CHECKIN_USER_ID_COL + " INTEGER, "
                + CHECKIN_STATUS_COL + " INTEGER, "
                + CHECKIN_TIME_COL + " TEXT, "
                + CHECKIN_LONGITUDE_COL + " DOUBLE, " // Replaced Column
                + CHECKIN_LATITUDE_COL + " DOUBLE, " // Replaced Column
                + CHECKIN_IMAGE_COL + " TEXT)") // New Column

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
        comments: String?,
        proofImage: String?, // New Parameter
        longitudeLocation: String?, // New Parameter
        latitudeLocation: String? // New Parameter
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
            put(PROOF_IMAGE_COL, proofImage) // New Column
            put(LONGITUDE_LOCATION_COL, longitudeLocation) // New Column
            put(LATITUDE_LOCATION_COL, latitudeLocation) // New Column
        }
        db.insert(CREATE_TABLE_NAME, null, values)
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
        comments: String?,
        proofImage: String?, // New Parameter
        longitudeLocation: String?, // New Parameter
        latitudeLocation: String? // New Parameter
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
//            put(CUSTOMER_NAME_COL, customerName)
//            put(PHONE_NUMBER_COL, phoneNumber)
//            put(ALTERNATE_PHONE_COL, alternatePhoneNumber)
 //           put(ADDRESS_COL, address)
            put(BUSINESS_CATEGORY_COL, businessCategory)
            put(CALL_STATUS_COL, callStatus)
            put(LEAD_STATUS_COL, leadStatus)
            put(FOLLOW_UP_DATE_COL, followUpDate)
            put(FOLLOW_UP_TIME_COL, followUpTime)
            put(FOLLOW_UP_ACTION_CALL_COL, followUpActionCall)
            put(FOLLOW_UP_ACTION_VISIT_COL, followUpActionVisit)
            put(COMMENTS_COL, comments)
//            put(PROOF_IMAGE_COL, proofImage) // New Column
//            put(LONGITUDE_LOCATION_COL, longitudeLocation) // New Column
//            put(LATITUDE_LOCATION_COL, latitudeLocation) // New Column
        }

        val rowsUpdated = db.update(CREATE_TABLE_NAME, values, "$ID_COL = ?", arrayOf(itemId.toString()))
        db.close()
        return rowsUpdated > 0
    }

    fun insertCheckIn(
        userId: Long?,
        checkInStatus: Int,
        checkInTime: String,
        longitude: Double?,
        latitude: Double?,
        checkInImage: String?
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(CHECKIN_USER_ID_COL, userId)
            put(CHECKIN_STATUS_COL, checkInStatus)
            put(CHECKIN_TIME_COL, checkInTime)
            put(CHECKIN_LONGITUDE_COL, longitude)
            put(CHECKIN_LATITUDE_COL, latitude)
            put(CHECKIN_IMAGE_COL, checkInImage)
        }
        val result = db.insert(CHECKIN_TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    fun getCheckInStatus(userId: Long?): Pair<Int, Pair<Float?, Float?>> {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            CHECKIN_TABLE_NAME,
            arrayOf(CHECKIN_STATUS_COL, CHECKIN_LONGITUDE_COL, CHECKIN_LATITUDE_COL),
            "$CHECKIN_USER_ID_COL = ?",
            arrayOf(userId.toString()),
            null,
            null,
            null
        )
        var checkInStatus = -1
        var longitude: Float? = null
        var latitude: Float? = null
        while (cursor.moveToLast()) {
            checkInStatus = cursor.getInt(cursor.getColumnIndexOrThrow(CHECKIN_STATUS_COL))
            longitude = cursor.getFloat(cursor.getColumnIndexOrThrow(CHECKIN_LONGITUDE_COL))
            latitude = cursor.getFloat(cursor.getColumnIndexOrThrow(CHECKIN_LATITUDE_COL))
        }
        cursor.close()
        db.close()
        return Pair(checkInStatus, Pair(longitude, latitude))
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 5) { // Add these columns if upgrading from version 4 to 5
            db.execSQL("ALTER TABLE $CREATE_TABLE_NAME ADD COLUMN $PROOF_IMAGE_COL TEXT")
            db.execSQL("ALTER TABLE $CREATE_TABLE_NAME ADD COLUMN $LONGITUDE_LOCATION_COL DOUBLE")
            db.execSQL("ALTER TABLE $CREATE_TABLE_NAME ADD COLUMN $LATITUDE_LOCATION_COL DOUBLE")
            db.execSQL("ALTER TABLE $CHECKIN_TABLE_NAME ADD COLUMN $CHECKIN_LONGITUDE_COL FLOAT")
            db.execSQL("ALTER TABLE $CHECKIN_TABLE_NAME ADD COLUMN $CHECKIN_LATITUDE_COL FLOAT")
        }
    }

}
