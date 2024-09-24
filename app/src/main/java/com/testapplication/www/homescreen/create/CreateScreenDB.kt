import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.testapplication.www.util.constants.Constants.ACTION_TYPE
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
import com.testapplication.www.util.constants.Constants.CREATE_LEDGER_TABLE_NAME
import com.testapplication.www.util.constants.Constants.CREATE_TABLE_NAME
import com.testapplication.www.util.constants.Constants.CREATION_ITEM_ID
import com.testapplication.www.util.constants.Constants.CUSTOMER_NAME_COL
import com.testapplication.www.util.constants.Constants.DB_VERSION
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_ACTION_CALL_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_ACTION_VISIT_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_DATE_COL
import com.testapplication.www.util.constants.Constants.FOLLOW_UP_TIME_COL
import com.testapplication.www.util.constants.Constants.ID_COL
import com.testapplication.www.util.constants.Constants.LATITUDE_LOCATION_COL
import com.testapplication.www.util.constants.Constants.LEAD_STATUS_COL
import com.testapplication.www.util.constants.Constants.LEDGER_STATUS
import com.testapplication.www.util.constants.Constants.LEDGER_TIME_STAMP
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

        val createLedgerTableQuery =  ("CREATE TABLE " + CREATE_LEDGER_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CREATION_ITEM_ID + " INTEGER, "
                + ACTION_TYPE + " INTEGER, "
                + LEDGER_STATUS + " TEXT, "
                + LEDGER_TIME_STAMP + " TEXT, "
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
                + PROOF_IMAGE_COL + " TEXT, "
                + LONGITUDE_LOCATION_COL + " TEXT, "
                + LATITUDE_LOCATION_COL + " TEXT)")

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
        db.execSQL(createLedgerTableQuery)
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
            put(PROOF_IMAGE_COL, proofImage)
            put(LONGITUDE_LOCATION_COL, longitudeLocation)
            put(LATITUDE_LOCATION_COL, latitudeLocation) // New Column
        }
        db.insert(CREATE_TABLE_NAME, null, values)
        db.close()
        return true
    }

    fun updateFST(
        itemId: Long?,
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

        if (rowsUpdated > 0) {
            // Insert into the ledger table
            val ledgerValues = ContentValues().apply {
                put(CREATION_ITEM_ID, itemId) // Foreign key to the updated row in create_table
                put(ACTION_TYPE, 2) // Assuming '2' represents an 'Update' action
                put(LEDGER_STATUS, "Updated")
                put(LEDGER_TIME_STAMP, System.currentTimeMillis().toString()) // Use the current time as the timestamp
                put(USER_ID_COL, userId) // Update this with userId if required
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
                put(PROOF_IMAGE_COL, proofImage)
                put(LONGITUDE_LOCATION_COL, longitudeLocation)
                put(LATITUDE_LOCATION_COL, latitudeLocation)
            }

            db.insert(CREATE_LEDGER_TABLE_NAME, null, ledgerValues)
        }
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
    fun creationLedgers(userId: Long, itemId: Long): List<List<String>> {
        val db = this.readableDatabase
        val ledgerList = mutableListOf<List<String>>()

        // Query to retrieve all records from the ledger table that match the provided userId and itemId
        val cursor: Cursor = db.query(
            CREATE_LEDGER_TABLE_NAME,
            null, // Passing 'null' selects all columns
            "$USER_ID_COL = ? AND $CREATION_ITEM_ID = ?",
            arrayOf(userId.toString(), itemId.toString()),
            null,
            null,
            null
        )

        // Iterating over the result set
        if (cursor.moveToFirst()) {
            do {
                val rowList = mutableListOf<String>()

                // Loop through each column and add the value to the list
                for (i in 0 until cursor.columnCount) {
                    val columnValue = cursor.getString(i) ?: "" // Avoid null values
                    rowList.add(columnValue)
                }

                ledgerList.add(rowList) // Add the row list to the ledger list
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return ledgerList
    }

    fun getLedgerList(ledgerItemId: Long?): List<String> {
        val db = this.readableDatabase
        val ledgerList = mutableListOf<String>()

        // Query the database with a selection based on ledgerItemId
        val cursor: Cursor = db.query(
            CREATE_LEDGER_TABLE_NAME,  // Table name
            null,                      // All columns
            "id = ?",                  // WHERE clause
            arrayOf(ledgerItemId.toString()), // Selection arguments (replace '?' with the ledgerItemId)
            null,                      // GroupBy
            null,                      // Having
            null                       // OrderBy
        )

        // Iterate through the cursor and extract data
        if (cursor.moveToFirst()) {
            do {
                // Extract each column value based on column index or name
                for (i in 0 until cursor.columnCount) {
                    ledgerList.add(cursor.getString(i)) // Adds each column value to the list
                }
            } while (cursor.moveToNext())
        }

        // Close the cursor to prevent memory leaks
        cursor.close()

        return ledgerList
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
        if (oldVersion < 7) { // Add these columns if upgrading from version 4 to 5
          db.execSQL("CREATE TABLE IF NOT EXISTS " + CREATE_LEDGER_TABLE_NAME + " ("
                  + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                  + CREATION_ITEM_ID + "INTEGER,"
                  + ACTION_TYPE + "INTEGER,"
                  + LEDGER_STATUS + "TEXT,"
                  + LEDGER_TIME_STAMP + "TEXT,"
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
                  + PROOF_IMAGE_COL + " TEXT, "
                  + LONGITUDE_LOCATION_COL + " TEXT, "
                  + LATITUDE_LOCATION_COL + " TEXT)")
        }
    }

}
