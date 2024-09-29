import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Required imports
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.google.android.gms.maps.model.LatLng
import com.testapplication.www.homescreen.ledgerviewform.LedgerViewModel
import com.testapplication.www.util.DisplayLeadText
import com.testapplication.www.util.LedgerDisplayDetails
import com.testapplication.www.util.constants.Constants.LEDGER_ACTION_TYPE
import com.testapplication.www.util.constants.Constants.LEDGER_ALTERNATE_PHONE_NUMBER
import com.testapplication.www.util.constants.Constants.LEDGER_BUSSINESS_CATEGORY
import com.testapplication.www.util.constants.Constants.LEDGER_CALL_STATUS
import com.testapplication.www.util.constants.Constants.LEDGER_COMMENTS
import com.testapplication.www.util.constants.Constants.LEDGER_CUSTOMER_NAME
import com.testapplication.www.util.constants.Constants.LEDGER_DETAILS
import com.testapplication.www.util.constants.Constants.LEDGER_FOLLOWUP_DATE
import com.testapplication.www.util.constants.Constants.LEDGER_FOLLOWUP_TIME
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_ALTERNATIVE_PHONE_NUMBER
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_BUSINESS_CATEGORY
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_BUSINESS_NAME
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_BUSINESS_TYPE
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_CALL_STATUS
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_CURRENT_SOFTWARE
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_CUSTOMER_NAME
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_FOLLOWUP_DATE_AND_TIME
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_FOLLOWUP_NOTES
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_FOLLOWUP_REQUIRED
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_LEAD_STATUS
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_LOCATION_ADDRESS
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_PHONE_NUMBER
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_POS_SALE
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_PREFERRED_LANGUAGE
import com.testapplication.www.util.constants.Constants.LEDGER_HEADER_SHOP_IMAGE
import com.testapplication.www.util.constants.Constants.LEDGER_ID
import com.testapplication.www.util.constants.Constants.LEDGER_LATITUDE
import com.testapplication.www.util.constants.Constants.LEDGER_LEAD_STATUS
import com.testapplication.www.util.constants.Constants.LEDGER_LONGITUDE
import com.testapplication.www.util.constants.Constants.LEDGER_PHONE_NUMBER
import com.testapplication.www.util.constants.Constants.LEDGER_PROOF_IMAGE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LedgerViewForm(ledgerItemId: Long?, context: Context) {
    val viewModel: LedgerViewModel = viewModel()

    val ledgerList by remember { mutableStateOf(viewModel.getLedgerList(ledgerItemId)) }

    val latLng = LatLng(
        ledgerList.get(LEDGER_LATITUDE)?.toDoubleOrNull()
            ?: 0.0,  // Convert latitude to Double
        ledgerList.get(LEDGER_LONGITUDE)?.toDoubleOrNull()
            ?: 0.0   // Convert longitude to Double
    )

    Column(modifier = Modifier.background(color = Color.LightGray)) {

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = LEDGER_DETAILS,
                color = Color.Black,
                fontSize = 25.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(modifier = Modifier.height(5.dp))
//



        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .weight(1f)  // Use weight to fill remaining space
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),

        ) {

            // Make sure that DisplayLeadText is a composable function and you call it within a composable context
            DisplayLeadText(ledgerList.get(LEDGER_ACTION_TYPE)?.toIntOrNull() ?: 0)
            LedgerDisplayDetails(
                columnHeader = LEDGER_HEADER_CUSTOMER_NAME,
                columnValue = ledgerList.get(LEDGER_CUSTOMER_NAME)
            )

            LedgerDisplayDetails(
                columnHeader = LEDGER_HEADER_BUSINESS_NAME,
                columnValue = "NA"
            )
            
            LedgerDisplayDetails(
                columnHeader = LEDGER_HEADER_PHONE_NUMBER,
                columnValue = ledgerList.get(LEDGER_PHONE_NUMBER)
            )

            LedgerDisplayDetails(
                columnHeader = LEDGER_HEADER_ALTERNATIVE_PHONE_NUMBER,
                columnValue = ledgerList.get(LEDGER_ALTERNATE_PHONE_NUMBER)
            )

            LedgerDisplayDetails(
                columnHeader = LEDGER_HEADER_LOCATION_ADDRESS,
                columnValue = getAddress(latLng, context)
            )

            LedgerDisplayDetails(
                columnHeader = LEDGER_HEADER_BUSINESS_TYPE,
                columnValue = "NA"
            )

            LedgerDisplayDetails(
                columnHeader = LEDGER_HEADER_BUSINESS_CATEGORY,
                columnValue = ledgerList.get(LEDGER_BUSSINESS_CATEGORY)
            )

            LedgerDisplayDetails(
                columnHeader =  LEDGER_HEADER_CURRENT_SOFTWARE,
                columnValue = "NA"
            )

            LedgerDisplayDetails(
                columnHeader =  LEDGER_HEADER_PREFERRED_LANGUAGE,
                columnValue = "NA"
            )
            
            LedgerDisplayDetails(
                columnHeader = LEDGER_HEADER_LEAD_STATUS,
                columnValue = ledgerList.get(LEDGER_CALL_STATUS)
            )
            LedgerDisplayDetails(
                columnHeader = LEDGER_HEADER_CALL_STATUS,
                columnValue = ledgerList.get(LEDGER_LEAD_STATUS)
            )

            LedgerDisplayDetails(
                columnHeader =  LEDGER_HEADER_FOLLOWUP_REQUIRED,
                columnValue = "NA"
            )
            
            LedgerDisplayDetails(
                columnHeader = LEDGER_HEADER_FOLLOWUP_DATE_AND_TIME,
                columnValue = ledgerList.get(LEDGER_FOLLOWUP_DATE) +" | "+ ledgerList.get(LEDGER_FOLLOWUP_TIME)
            )

            LedgerDisplayDetails(
                columnHeader =  LEDGER_HEADER_POS_SALE,
                columnValue = "NA"
            )
            
            Column(modifier = Modifier.padding(1.dp)) {
                LedgerDisplayDetails(
                    columnHeader = LEDGER_HEADER_SHOP_IMAGE,
                    columnValue = ""
                )
                Box(
                    Modifier
                        .background(Color.White)
                        .fillMaxWidth() // Ensures the box fills the width
                        .aspectRatio(1f), // Keeps the box square or specify a ratio for different shapes
                    contentAlignment = Alignment.Center // Aligns content in the center
                ) {
                    Image(
                        painter = rememberImagePainter(Uri.parse(ledgerList[LEDGER_PROOF_IMAGE])),
                        contentDescription = "Image Proof",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize()// Fills the entire box
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(5.dp))

            LedgerDisplayDetails(
                columnHeader =  LEDGER_HEADER_FOLLOWUP_NOTES,
                columnValue = ledgerList.get(LEDGER_COMMENTS)
            )







        }


    }
}


