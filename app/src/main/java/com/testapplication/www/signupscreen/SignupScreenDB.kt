import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
        phoneNumber: Long?, // Change parameter type to Int
        password: String?
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PHONE_COL, phoneNumber) // Store phone number as INTEGER
        values.put(PASSWORD, password)
        db.insert(TABLE_NAME, null, values)
        db.close()
        return true
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "test_application_db"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "signup"
        private const val ID_COL = "id"
        private const val PHONE_COL = "phone"
        private const val PASSWORD = "password"
    }
}
