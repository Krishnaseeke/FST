import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
    userID: Long?,
    itemID: Long?,
    context: Context,
    modifier: Modifier = Modifier
) {
    // Retrieve the ViewModel with the custom factory
    val viewModel: CreationLedgerViewModel = viewModel(
        factory = CreationLedgerViewModelFactory(context)
    )

    Column(modifier = Modifier.background(Color.LightGray)) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {
            Row(
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
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        // LedgerList is added here
        if (userID != null && itemID != null) {
            LedgerList(
                context = context,
                userId = userID,
                itemId = itemID,
                viewModel = viewModel // Pass ViewModel here
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .clip(shape = RoundedCornerShape(5.dp))
                .background(Color.White)
                .fillMaxWidth(1f)
                .padding(start = 1.dp, top = 0.dp, bottom = 15.dp, end = 1.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            if (userID != null) {
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
    }
}
