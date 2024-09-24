package com.testapplication.www.homescreen.ledgerviewform

import CreateScreenDB
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class LedgerViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = CreateScreenDB(application)

    fun getLedgerList(ledgerItemId: Long?): List<String> {
        return dbHelper.getLedgerList(ledgerItemId)
    }
}
