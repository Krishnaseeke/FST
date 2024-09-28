import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testapplication.www.homescreen.creationledger.CreationLedgerViewModel
import com.testapplication.www.homescreen.creationledger.CreationLedgerViewModelFactory
import com.testapplication.www.homescreen.creationledger.LedgerList
import com.testapplication.www.util.constants.Constants

@Composable
fun CreationLedgerScreen(
    toHome: (Any?) -> Unit,
    toCreate: (Long?, Long?) -> Unit,
    toCreationLedger: (Long, Long) -> Unit,
    toLedgerViewForm: (Any?) -> Unit,
    userID: Long?,
    itemID: Long?,
    context: Context,
    modifier: Modifier = Modifier
) {
    // Retrieve the ViewModel with the custom factory
    val viewModel: CreationLedgerViewModel = viewModel(
        factory = CreationLedgerViewModelFactory(context)
    )

    Column(
        modifier = modifier
            .background(Color.LightGray)
            .fillMaxSize() // Fill the entire screen size
    ) {
        // Top Title Row
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = Constants.CREATION_LEDGER_SCREEN_TITLE,
                color = Color.Black,
                fontSize = 25.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold
            )
        }

        // Spacer between DisplayList and LedgerList
        Spacer(modifier = Modifier.height(5.dp))

        // Box for DisplayList to occupy a portion of the screen height
        if (userID != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp) // Specify a fixed height or try different values here
                    .clip(RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                com.testapplication.www.homescreen.home.DisplayList(
                    context = context,
                    userId = userID,
                    itemId = itemID,
                    selectedDate = "",
                    valueType = Constants.SPECIFIC_ITEM_LIST,
                    toCreationLedger = { userId, itemId ->
                        toCreationLedger(userId, itemId)
                    },
                    toCreate = { userId, itemId ->
                        toCreate(userId, itemId)
                    }
                )
            }
        }

        // Spacer between DisplayList and LedgerList
        Spacer(modifier = Modifier.height(5.dp))

        // Scrollable LedgerList Column
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .fillMaxWidth()
                .weight(1f) // Assign weight to take remaining space
                .verticalScroll(rememberScrollState())
        ) {
            // Additional spacing
            Spacer(modifier = Modifier.height(10.dp))

            // LedgerList is added here and only displayed when IDs are non-null
            if (userID != null && itemID != null) {
                LedgerList(
                    context = context,
                    userId = userID,
                    itemId = itemID,
                    toLedgerViewForm,
                    viewModel = viewModel // Pass ViewModel here
                )
            }

            // Extra spacing at the bottom for better visibility
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

