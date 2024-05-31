package com.testapplication.www.homescreen.home

import CreateScreenDB
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Database class to fetch required data for HomeScreen.
class HomeScreenDB(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "create_screen_db"
        private const val DB_VERSION = 3
        private const val TABLE_NAME = "create_screen_data"
        private const val USER_ID_COL = "user_id"
        private const val LEAD_STATUS_COL = "lead_status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // The database schema should already exist from the existing CreateScreenDB class.
    }

    val createdb = CreateScreenDB(context)
    val readablefile = createdb.readableDatabase


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Fetch the count of rows based on given lead status and user ID.
    private fun countByLeadStatusAndUserId(statuses: List<String>, userId: Long?): Int {
        val db = this.readableDatabase
        var count = 0

        if (userId != null) {
            val query = """
                SELECT COUNT(*)
                FROM $TABLE_NAME
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
