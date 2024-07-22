package com.testapplication.www.loginscreen

import android.annotation.SuppressLint
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




}