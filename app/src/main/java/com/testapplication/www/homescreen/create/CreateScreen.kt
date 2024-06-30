import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Calendar
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import android.app.TimePickerDialog
import android.widget.TimePicker
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import com.google.android.gms.location.LocationServices
import com.testapplication.www.homescreen.create.AttachImageButton
import com.testapplication.www.homescreen.create.BottomSheet
import com.testapplication.www.homescreen.create.CustomOutlinedTextField
import com.testapplication.www.homescreen.create.DropdownLists
import com.testapplication.www.homescreen.home.ScreenData1
import java.text.SimpleDateFormat
import java.util.Locale


@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateScreen(
    toHome: (Any?) -> Unit,
    context: Context,
    userID: Long,
    itemId: Long?,
    modifier: Modifier = Modifier,

    ) {
    val viewModel: CreateScreenViewModel =
        viewModel { CreateScreenViewModel(context, userID, itemId) }
    val state by viewModel.state.collectAsState()
    // Navigate to home on successful submission
    LaunchedEffect(state.isSubmissionSuccessful) {
        if (state.isSubmissionSuccessful) {
            Toast.makeText(context, "Successfully FST Created", Toast.LENGTH_SHORT).show()
            toHome(userID)
        }
    }
    var address by remember { mutableStateOf("") }

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val showToastMessage = viewModel.showToast.collectAsState().value
    LaunchedEffect(key1 = showToastMessage) {
        if (!showToastMessage.isNullOrEmpty()) {
            Toast.makeText(context, showToastMessage, Toast.LENGTH_SHORT).show()
        }
        getLastLocation(fusedLocationClient, context) { location ->
            address = getAddressFromLocation(location, context)
        }

    }
    if (itemId != null) {
        viewModel.fetchExistingRecord(context, itemId)
    }

    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val mDatePickerDialog = android.app.DatePickerDialog(
        mContext,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val calendar = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }
            val formattedDate = dateFormatter.format(calendar.time)
            viewModel.updateFollowUpDate(formattedDate)
        },
        mYear,
        mMonth,
        mDay
    )

    val mCalendar1 = Calendar.getInstance()
    val mHour = mCalendar1[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar1[Calendar.MINUTE]

    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _: TimePicker, hour: Int, minute: Int ->
            val formattedTime = String.format("%02d:%02d", hour, minute)
            viewModel.updateFollowUpTime(formattedTime)
        },
        mHour,
        mMinute,
        true  // Use 24-hour format
    )

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black,
        disabledTextColor = Color.Black,
        disabledBorderColor = Color.Black,
        disabledLabelColor = Color.Black

    )

    val textFieldStyle = TextStyle(color = Color.Black)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Absolute.Left,
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { toHome(userID) }
                    .padding(10.dp) //Need
            )
            Text(
                text = "Create",
                color = Color.Black,
                fontSize = 24.sp, // Adjusted to be responsive
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(3.dp)) // Flexible spacing //Need

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = state.customerName,
                onValueChange = { viewModel.updateCustomerName(it) },
                label = { Text("Customer Name*") },
                colors = textFieldColors,
                textStyle = textFieldStyle,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            OutlinedTextField(
                value = state.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                label = { Text("Phone Number*") },
                colors = textFieldColors,
                textStyle = textFieldStyle,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            OutlinedTextField(
                value = state.alternatePhoneNumber,
                onValueChange = { viewModel.updateAlternatePhoneNumber(it) },
                label = { Text("Alternate Phone Number") },
                colors = textFieldColors,
                textStyle = textFieldStyle,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            OutlinedTextField(
                enabled = false,
                value = address,
                onValueChange = { viewModel.updateAddress(address) },
                label = { Text("Address*") },
                colors = textFieldColors,
                textStyle = textFieldStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(102.dp)
                    .padding(5.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Column() {
                    Text(
                        text = "Proof Of Meeting*",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier.padding(10.dp)
                    )
                    AttachImageButton()
                }

            }


            var csshowSheet by remember { mutableStateOf(false) }
            var lsshowSheet by remember { mutableStateOf(false) }


            val csIcon =
                if (csshowSheet) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
            val lsIcon =
                if (lsshowSheet) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

            var bcshowSheet by remember { mutableStateOf(false) }
            val bcIcon =
                if (bcshowSheet) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

            Box{
                if (bcshowSheet) {
                    BottomSheet(
                        onDismiss = {
                            bcshowSheet = false
                        },
                        onCategorySelected = { category ->
                            state.businessCategory = category
                            bcshowSheet = false
                        }, "BussinessCategory"
                    )
                }

                OutlinedTextField(
                    enabled = false,
                    singleLine = true,
                    value = if (state.businessCategory.isNotEmpty()) state.businessCategory else "Select category",
                    onValueChange = { viewModel.updateBusinessCategory(it) },
                    trailingIcon = {
                        Icon(
                            bcIcon,
                            "Dropdown Icon",
                            tint = Color.Black // Specify icon tint
                        )
                    },
                    label = { Text("Business Category") },
                    colors = textFieldColors,
                    textStyle = textFieldStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            bcshowSheet = true
                        }
                        .padding(5.dp)

                )
            }

            Box {
                if (csshowSheet) {
                    BottomSheet(
                        onDismiss = {
                            csshowSheet = false
                        },
                        onCategorySelected = { category ->
                            state.callStatus = category
                            csshowSheet = false
                        }, "CallStatus"
                    )
                }

                OutlinedTextField(
                    enabled = false,
                    singleLine = true,
                    value = if (state.callStatus.isNotEmpty()) state.callStatus else "Select category",
                    onValueChange = { viewModel.updateCallStatus(it) },
                    trailingIcon = {
                        Icon(
                            csIcon,
                            "Dropdown Icon",
                            tint = Color.Black // Specify icon tint
                        )
                    },
                    label = { Text("Call Status") },
                    colors = textFieldColors,
                    textStyle = textFieldStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            csshowSheet = true
                        }
                        .padding(5.dp)
                )
            }

            Box{
                if (lsshowSheet) {
                    BottomSheet(
                        onDismiss = {
                            lsshowSheet = false
                        },
                        onCategorySelected = { category ->
                            state.leadStatus = category
                            lsshowSheet = false
                        }, "LeadStatus"
                    )
                }


                OutlinedTextField(
                    enabled = false,
                    singleLine = true,
                    value = if (state.leadStatus.isNotEmpty()) state.leadStatus else "Select category",
                    onValueChange = { viewModel.updateLeadStatus(it) },
                    trailingIcon = {
                        Icon(
                            lsIcon,
                            "Dropdown Icon",
                            tint = Color.Black // Specify icon tint
                        )
                    },
                    label = { Text("Lead Status") },
                    colors = textFieldColors,
                    textStyle = textFieldStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { lsshowSheet = true }
                        .padding(5.dp)
                )
            }




            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mDatePickerDialog.show() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    enabled = false,
                    value = state.followUpDate,
                    onValueChange = { viewModel.updateFollowUpDate(it) },
                    label = { Text("Follow Up Date*") },
                    colors = textFieldColors,
                    singleLine = true,
                    textStyle = textFieldStyle,
                    modifier = Modifier
                        .weight(1f) // Make field responsive
                        .padding(5.dp)
                )



                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { mTimePickerDialog.show() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    enabled = false,
                    value = state.followUpTime,
                    onValueChange = { viewModel.updateFollowUpTime(it) },
                    label = { Text("Follow Up Time*") },
                    singleLine = true,
                    colors = textFieldColors,
                    textStyle = textFieldStyle,
                    modifier = Modifier
                        .weight(1f)
                        .padding(5.dp)
                )

                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(10.dp)
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Follow Up Action*",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.followUpActionCall,
                        onClick = { viewModel.toggleFollowUpActionCall() }
                    )
                    Text("Call", modifier = Modifier.padding(10.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.followUpActionVisit,
                        onClick = { viewModel.toggleFollowUpActionVisit() }
                    )
                    Text("Visit", modifier = Modifier.padding(10.dp))
                }
            }

            OutlinedTextField(
                value = state.comments,
                onValueChange = { viewModel.updateComments(it) },
                label = { Text("Comments") },
                colors = textFieldColors,
                textStyle = textFieldStyle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(131.dp)
                    .padding(10.dp)
            )
        }

        //Spacer(modifier = Modifier.weight(1f)) // Fills remaining space to ensure button stays at the bottom

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                // Show progress bar here (e.g., CircularProgressIndicator)
            }
            Button(
                onClick = {
                    viewModel.saveFST(
                        userId = userID,
                        customerName = state.customerName,
                        phoneNumber = state.phoneNumber,
                        alternatePhoneNumber = state.alternatePhoneNumber,
                        address = state.address,
                        businessCategory = state.businessCategory,
                        callStatus = state.callStatus,
                        leadStatus = state.leadStatus,
                        followUpDate = state.followUpDate,
                        followUpTime = state.followUpTime,
                        followUpActionCall = if (state.followUpActionCall) 1 else 0,
                        followUpActionVisit = if (state.followUpActionVisit) 1 else 0,
                        comments = state.comments
                    ) // Save to the database
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(
                    text = "Save",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}