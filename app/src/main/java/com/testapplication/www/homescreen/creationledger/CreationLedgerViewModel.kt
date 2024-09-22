package com.testapplication.www.homescreen.creationledger

import CreateScreenDB
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

import kotlinx.coroutines.withContext

class CreationLedgerViewModel(context: Context) : ViewModel() {
    val db: CreateScreenDB = CreateScreenDB(context)

    suspend fun getCreationLedgerList(userId: Long, itemId: Long): List<List<String>> {
        return withContext(Dispatchers.IO) {
            db.creationLedgers(userId, itemId)
        }
    }
}

class CreationLedgerViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreationLedgerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreationLedgerViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



@Composable
fun LedgerList(
    context: Context,
    userId: Long,
    itemId: Long,
    viewModel: CreationLedgerViewModel
) {
    // State to hold the ledger list
    var ledgerList by remember { mutableStateOf<List<List<String>>>(emptyList()) }

    // Coroutine scope to load the data
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            ledgerList = viewModel.getCreationLedgerList(userId, itemId)
        }
    }

    // Displaying the fetched list
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        for (ledger in ledgerList) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Display only the first four values from each list
                    for (i in 0 until minOf(4, ledger.size)) {
                        BasicText(text = ledger[i])
                    }
                }
            }
        }
    }
}

//
//viewModelScope.launch {
//    val ledgerList = viewModel.getCreationLedgerList(userId, itemId)
//    // Now you can use ledgerList in your UI
//}

