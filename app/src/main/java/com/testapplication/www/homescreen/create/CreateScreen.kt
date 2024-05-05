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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.testapplication.www.homescreen.create.CustomOutlinedTextField
import com.testapplication.www.homescreen.create.DropdownLists


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CreateScreen(
    toHome: (Any?) -> Unit,
    context: Context,
    userID: Long,
    modifier: Modifier = Modifier
) {
    val viewModel: CreateScreenViewModel = viewModel { CreateScreenViewModel(context, userID) }
    val state by viewModel.state.collectAsState()

    // Navigate to home on successful submission
    LaunchedEffect(state.isSubmissionSuccessful) {
        if (state.isSubmissionSuccessful) {
            Toast.makeText(context, "Successfully FST Created", Toast.LENGTH_SHORT).show()
            toHome(userID)
        }
    }

    val mContext = LocalContext.current
    val mCalendar = Calendar.getInstance()
    val mYear = mCalendar.get(Calendar.YEAR)
    val mMonth = mCalendar.get(Calendar.MONTH)
    val mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    val mDatePickerDialog = android.app.DatePickerDialog(
        mContext,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val formattedDate = "$dayOfMonth/${month + 1}/$year"
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
        { _, hour: Int, minute: Int ->
            val formattedTime = "$hour:$minute"
            viewModel.updateFollowUpTime(formattedTime)
        },
        mHour,
        mMinute,
        false
    )

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black,

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            OutlinedTextField(
                value = state.address,
                onValueChange = { viewModel.updateAddress(it) },
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
                    Button(
                        onClick = {
                            // Implement attachment functionality
                        },
                        modifier = Modifier
                            .fillMaxWidth() // Ensure the button fills the available space
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(Color.LightGray)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add icon",
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = "Attach Image",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }

            }

            var bcExpanded by remember { mutableStateOf(false) }
            var csExpanded by remember { mutableStateOf(false) }
            var lsExpanded by remember { mutableStateOf(false) }

            val bcIcon =
                if (bcExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
            val csIcon =
                if (csExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
            val lsIcon =
                if (lsExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown

            Box(
                modifier = Modifier.fillMaxWidth() // Ensures dropdown flexibility
            ) {
                OutlinedTextField(
                    value = state.businessCategory,
                    onValueChange = { viewModel.updateBusinessCategory(it) },
                    trailingIcon = {
                        Icon(
                            bcIcon,
                            "Dropdown Icon",
                            Modifier.clickable { bcExpanded = !bcExpanded },Color.Black
                        )
                    },
                    label = { Text("Business Category") },
                    colors = textFieldColors,
                    textStyle = textFieldStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )

                DropdownMenu(
                    expanded = bcExpanded,
                    onDismissRequest = { bcExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(200.dp).align(Alignment.Center).background(color = Color.White)
                ) {
                    DropdownLists.bussinessCategory.forEach { category ->
                        DropdownMenuItem(
                            text = {
                                Text(category, color = Color.Black)
                            },
                            onClick = {
                                viewModel.updateBusinessCategory(category)
                                bcExpanded = false
                            }
                        )
                        Divider()
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.callStatus,
                    onValueChange = { viewModel.updateCallStatus(it) },
                    trailingIcon = {
                        Icon(
                            csIcon,
                            "Dropdown Icon",
                            Modifier.clickable { csExpanded = !csExpanded },Color.Black
                        )
                    },
                    label = { Text("Call Status") },
                    colors = textFieldColors,
                    textStyle = textFieldStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )

                DropdownMenu(
                    expanded = csExpanded,
                    onDismissRequest = { csExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(200.dp).align(Alignment.Center).background(color = Color.White)
                ) {
                    DropdownLists.callStatus.forEach { status ->
                        DropdownMenuItem(
                            text = {
                                Text(status, color = Color.Black)
                            },
                            onClick = {
                                viewModel.updateCallStatus(status)
                                csExpanded = false
                            }
                        )
                        Divider()
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = state.leadStatus,
                    onValueChange = { viewModel.updateLeadStatus(it) },
                    trailingIcon = {
                        Icon(
                            lsIcon,
                            "Dropdown Icon",
                            Modifier.clickable { lsExpanded = !lsExpanded },Color.Black
                        )
                    },
                    label = { Text("Lead Status*") },
                    colors = textFieldColors,
                    textStyle = textFieldStyle,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                )

                DropdownMenu(
                    expanded = lsExpanded,
                    onDismissRequest = { lsExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .height(200.dp).align(Alignment.Center).background(color = Color.White)
                ) {
                    DropdownLists.leadStatus.forEach { status ->
                        DropdownMenuItem(
                            text = {
                                Text(status, color = Color.Black)
                            },
                            onClick = {
                                viewModel.updateLeadStatus(status)
                                lsExpanded = false
                            }
                        )
                        Divider()
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.followUpDate,
                    onValueChange = { viewModel.updateFollowUpDate(it) },
                    label = { Text("Follow Up Date*") },
                    colors = textFieldColors,
                    textStyle = textFieldStyle,
                    modifier = Modifier
                        .weight(1f) // Make field responsive
                        .padding(5.dp)
                )

                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable { mDatePickerDialog.show() }
                        .padding(10.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = state.followUpTime,
                    onValueChange = { viewModel.updateFollowUpTime(it) },
                    label = { Text("Follow Up Time*") },
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
                        .clickable { mTimePickerDialog.show() }
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
            Button(
                onClick = {
                    viewModel.saveFST() // Save to the database
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
