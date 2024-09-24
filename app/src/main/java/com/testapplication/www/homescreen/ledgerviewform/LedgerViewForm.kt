import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Required imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testapplication.www.homescreen.ledgerviewform.LedgerViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerViewForm(ledgerItemId: Long?, context: Context) {
    // Obtain the AndroidViewModel
    val viewModel: LedgerViewModel = viewModel()

    // State to hold the ledger list
    val ledgerList by remember { mutableStateOf(viewModel.getLedgerList(ledgerItemId)) }

    Column(modifier = Modifier.background(color = Color.White)) {
        Column {
            Text(text = "Ledger Details")
        }

        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(ledgerList) { ledgerItem ->
                Text(
                    text = ledgerItem,  // Display each ledger item
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }

    }

    // Rest of the UI remains the same

}

