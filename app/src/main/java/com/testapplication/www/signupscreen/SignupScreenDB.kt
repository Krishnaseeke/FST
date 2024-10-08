import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.testapplication.www.util.constants.Constants.DB_NAME
import com.testapplication.www.util.constants.Constants.DB_VERSION
import com.testapplication.www.util.constants.Constants.ID_COL
import com.testapplication.www.util.constants.Constants.PASSWORD
import com.testapplication.www.util.constants.Constants.PHONE_COL
import com.testapplication.www.util.constants.Constants.TABLE_NAME

class SignupScreenDB
    (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PHONE_COL + " INTEGER," // Change data type to INTEGER
                + PASSWORD + " TEXT)")

        db.execSQL(query)
    }

    fun signup(
        phoneNumber: Long?,
        password: String?
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PHONE_COL, phoneNumber)
        values.put(PASSWORD, password)

        // Check if the phone number already exists in the database
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(ID_COL),
            "$PHONE_COL = ?",
            arrayOf(phoneNumber.toString()),
            null,
            null,
            null,
            null
        )

        val phoneExists = cursor.count > 0
        cursor.close()

        if (phoneExists) {
            db.close()
            return false // Phone number already exists, return false
        } else {
            // Insert the new user into the database
            db.insert(TABLE_NAME, null, values)
            db.close()
            return true
        }
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }


    @SuppressLint("Range")
    fun getUserIdByPhoneNumber(phoneNumber: Long): Long? {
        val db = this.readableDatabase
        val selection = "$PHONE_COL = ?"
        val selectionArgs = arrayOf(phoneNumber.toString())
        val cursor: Cursor? = db.query(
            TABLE_NAME,
            arrayOf(ID_COL),
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        var userId: Long? = null
        cursor?.use {
            if (it.moveToFirst()) {
                userId = it.getLong(it.getColumnIndex(ID_COL))
            }
        }
        cursor?.close()
        db.close()
        return userId
    }

}
