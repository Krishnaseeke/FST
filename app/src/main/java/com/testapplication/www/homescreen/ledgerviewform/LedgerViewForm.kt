package com.testapplication.www.homescreen.ledgerviewform

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun LedgerViewForm(ledgerItemId: Long?, context: Context) {
    Column(modifier = Modifier.background(color = Color.White)) {
        Text(text = ledgerItemId.toString())
        Text(text = "Hello")
    }
}