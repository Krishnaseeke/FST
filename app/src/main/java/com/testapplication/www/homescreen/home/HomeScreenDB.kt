package com.testapplication.www.homescreen.home

import CreateScreenDB
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.testapplication.www.util.constants.Constants.CREATE_DB_NAME
import com.testapplication.www.util.constants.Constants.CREATE_TABLE_NAME
import com.testapplication.www.util.constants.Constants.DB_VERSION
import com.testapplication.www.util.constants.Constants.LEAD_STATUS_COL
import com.testapplication.www.util.constants.Constants.USER_ID_COL

// Database class to fetch required data for HomeScreen.
class HomeScreenDB(context: Context?) :
    SQLiteOpenHelper(context, CREATE_DB_NAME, null, DB_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {
        // The database schema should already exist from the existing CreateScreenDB class.
    }

    //This is required - Just to Instialize the DB after Login/Signup
    val createdb = CreateScreenDB(context)
    val readablefile = createdb.readableDatabase


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $CREATE_TABLE_NAME")
        onCreate(db)
    }

    // Fetch the count of rows based on given lead status and user ID.
    private fun countByLeadStatusAndUserId(statuses: List<String>, userId: Long?): Int {
        val db = this.readableDatabase
        var count = 0

        if (userId != null) {
            val query = """
                SELECT COUNT(*)
                FROM $CREATE_TABLE_NAME
                WHERE $USER_ID_COL = ?
                  AND $LEAD_STATUS_COL IN (${statuses.joinToString(",") { "?" }})
            """
            val cursor = db.rawQuery(query, arrayOf(userId.toString(), *statuses.toTypedArray()))
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0)
            }
            cursor.close()
        }

        db.close()
        return count
    }

    // Public methods to fetch data for HomeScreen.
    fun getLeadsCreatedCount(userId: Long?): Int {
        val statuses = listOf("First Meet", "Requires Follow Up", "Interested", "Demo Re-scheduled")
        return countByLeadStatusAndUserId(statuses, userId)
    }

    fun getDemosScheduledCount(userId: Long?): Int {
        return countByLeadStatusAndUserId(listOf("Demo Scheduled"), userId)
    }

    fun getDemosCompletedCount(userId: Long?): Int {
        return countByLeadStatusAndUserId(listOf("Demo Completed"), userId)
    }

    fun getLicensesSoldCount(userId: Long?): Int {
        return countByLeadStatusAndUserId(listOf("License Purchased"), userId)
    }
}
