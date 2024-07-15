package com.testapplication.www.loginscreen

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LoginScreenDB(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION)  {

    fun validateLogin(phoneNumber: Long?, password: String?): Boolean {
        val db = this.readableDatabase
        val selection = "$PHONE_COL = ? AND $PASSWORD = ?"
        val selectionArgs = arrayOf(phoneNumber.toString(), password)
        val cursor: Cursor? = db.query(
            TABLE_NAME,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        val count = cursor?.count ?: 0
        cursor?.close()
        db.close()
        return count > 0
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PHONE_COL + " INTEGER,"
                + PASSWORD + " TEXT)")

        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "test_application_db"
        private const val DB_VERSION = 6
        private const val TABLE_NAME = "signup"
        private const val ID_COL = "id"
        private const val PHONE_COL = "phone"
        private const val PASSWORD = "password"
    }



}