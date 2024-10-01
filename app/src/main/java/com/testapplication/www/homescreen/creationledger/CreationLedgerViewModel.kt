package com.testapplication.www.homescreen.creationledger

import CreateScreenDB
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.testapplication.www.R
import com.testapplication.www.common.PreferencesManager
import com.testapplication.www.ui.theme.AppleGreen
import com.testapplication.www.ui.theme.DarkGreen
import com.testapplication.www.util.DisplayLeadText
import com.testapplication.www.util.LedgerDisplayDetails
import com.testapplication.www.util.constants.Constants
import com.testapplication.www.util.constants.Constants.LEDGER_ACTION_TYPE
import com.testapplication.www.util.constants.Constants.LEDGER_DETAILS_SCREEN_STATUS
import com.testapplication.www.util.constants.Constants.LEDGER_FOLLOWUP_DATE
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
    toLedgerViewForm: (Any?) -> Unit,  // Accept ledger as a parameter
    viewModel: CreationLedgerViewModel = viewModel(factory = CreationLedgerViewModelFactory(context))
) {
   val preferencesManager = PreferencesManager(context)
    // State to hold the ledger list
    var ledgerList by remember { mutableStateOf<List<List<String>>>(emptyList()) }

    // Coroutine scope to load the data
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            ledgerList = viewModel.getCreationLedgerList(userId, itemId)
            preferencesManager.saveLedgerCount(ledgerList.size)
        }
    }

    // Displaying the fetched list
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
    ) {
        if (ledgerList.isEmpty()) {


            Box(
                Modifier
                    .background(Color.White)
                    .fillMaxWidth() // Ensures the box fills the width
                    .aspectRatio(1f), // Keeps the box square or specify a ratio for different shapes
                contentAlignment = Alignment.Center // Aligns content in the center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = R.mipmap.nodatafound_foreground),
                    contentDescription = Constants.NO_DATA_FOUND_IMAGE_DESCRIPTION,

                    )
            }
        }
        for (ledger in ledgerList) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                elevation = 4.dp
            ) {
                // Tag at the top-right corner

                Column(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(10.dp)
                ) {


                    Row(
                        modifier = Modifier.fillMaxWidth(), // Ensures the Row takes full width
                        verticalAlignment = Alignment.CenterVertically // Optional: Aligns items vertically center
                    ) {
                        // Display Action Type
                        DisplayLeadText(ledger.get(Constants.LEDGER_ACTION_TYPE)?.toIntOrNull() ?: 0)
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .background(color = AppleGreen).height(20.dp)
                                .padding(2.dp)
                        ) {
                            Text(
                                text = ledger.getOrNull(LEDGER_DETAILS_SCREEN_STATUS) ?: "N/A",
                                fontSize = 12.sp,
                                color = DarkGreen,
                                fontWeight = FontWeight.Normal,
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Display Time and Date
                    LedgerDisplayDetails(
                        columnHeader = Constants.LEDGER_HEADER_FOLLOWUP_DATE_AND_TIME,
                        columnValue = ledger.get(LEDGER_FOLLOWUP_DATE) + " | " + ledger.get(
                            Constants.LEDGER_FOLLOWUP_TIME
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Add View Form button
                    Button(modifier = Modifier,
                        onClick = {
                            toLedgerViewForm(ledger.get(0))  // Pass the selected ledger
                        }
                    ) {
                        Text(text = "View Form")
                    }


                }
            }
        }
    }
}


