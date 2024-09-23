package com.testapplication.www.homescreen.creationledger

import CreateScreenDB
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// ViewModel class
class CreationLedgerViewModel(context: Context) : ViewModel() {
    private val db: CreateScreenDB = CreateScreenDB(context)

    suspend fun getCreationLedgerList(userId: Long, itemId: Long): List<List<String>> {
        return withContext(Dispatchers.IO) {
            db.creationLedgers(userId, itemId)
        }
    }
}

// Factory to create ViewModel with context
class CreationLedgerViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreationLedgerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreationLedgerViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

// Composable function to display the ledger list
@Composable
fun LedgerList(
    context: Context,
    userId: Long,
    itemId: Long,
    viewModel: CreationLedgerViewModel = viewModel(factory = CreationLedgerViewModelFactory(context))
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
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
    ) {


        for (ledger in ledgerList) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(10.dp)
                ) {
                    // Display Ledger Status
                    Text(
                        text = "Ledger Status: ${ledger.getOrNull(3) ?: "N/A"}",
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Display Action Type
                    Text(
                        text = "Action Type: ${ledger.getOrNull(2) ?: "N/A"}",
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    // Display Time and Date
                    Text(
                        text = "Time and Date: ${ledger.getOrNull(13) ?: "N/A"} ${
                            ledger.getOrNull(
                                14
                            ) ?: ""
                        }",
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Add View Form button
                    Button(
                        onClick = {
                            // Handle button click (you can add any navigation or action here)
                        }
                    ) {
                        Text(text = "View Form")
                    }
                }
            }
        }

    }

}


