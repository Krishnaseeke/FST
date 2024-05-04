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
import androidx.compose.ui.text.font.FontWeight
import com.testapplication.www.homescreen.create.CreateScreenViewModel
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
                    .padding(start = 10.dp, end = 2.dp, top = 12.5.dp, bottom = 10.dp)
                    .size(30.dp))
            Text(
                text = "Create",
                color = Color.Black,
                fontSize = 30.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 2.dp, end = 5.dp, top = 10.dp, bottom = 10.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(Color.LightGray)
        )

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .height(750.dp)
                .padding(10.dp)
                .verticalScroll(rememberScrollState())
        ) {

            OutlinedTextField(
                value = TextFieldValue(state.customerName),
                onValueChange = { viewModel.updateCustomerName(it.text) },
                label = { Text("Customer Name*") },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            OutlinedTextField(
                value = TextFieldValue(state.phoneNumber),
                onValueChange = { viewModel.updatePhoneNumber(it.text) },
                label = { Text("Phone Number*") },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            OutlinedTextField(
                value = TextFieldValue(state.alternatePhoneNumber),
                onValueChange = { viewModel.updateAlternatePhoneNumber(it.text) },
                label = { Text("Alternate Phone Number") },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            OutlinedTextField(
                value = TextFieldValue(state.address),
                onValueChange = { viewModel.updateAddress(it.text) },
                label = { Text("Address*") },
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth().height(102.dp)
                    .padding(5.dp)
            )

            Box() {
                Text(
                    text = "Proof Of Meeting*",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Normal,
                    modifier = Modifier.padding(10.dp)
                )

                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .height(80.dp)
                        .align(Alignment.CenterStart)
                        .padding(top = 40.dp),
                    colors = ButtonDefaults.buttonColors(Color.LightGray)
                ) {
                    Icon(
                        Icons.Filled.Add,
                        "Add icon",
                        modifier = Modifier
                            .padding(end = 3.dp)
                            .size(20.dp)
                            .background(
                                Color.Black
                            )
                    )
                    Text(

                        text = "Attach Image",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier.padding(1.dp)
                    )

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

           Box(){
               OutlinedTextField(
                   value = TextFieldValue(state.businessCategory),
                   onValueChange = { viewModel.updateBusinessCategory(it.text) },
                   trailingIcon = {
                       Icon(
                           bcIcon,
                           "Dropdown Icon",
                           Modifier.clickable { bcExpanded = !bcExpanded }
                       )
                   },
                   label = { Text("Business Category") },
                   modifier = Modifier
                       .fillMaxWidth()
                       .clip(shape = RoundedCornerShape(5.dp))
                       .padding(5.dp)
               )

               DropdownMenu(
                   expanded = bcExpanded,
                   onDismissRequest = { bcExpanded = false },
                   modifier = Modifier
                       .fillMaxWidth().height(200.dp)
                       .background(Color.White)
               ) {
                   DropdownLists.bussinessCategory.forEach { category ->
                       DropdownMenuItem(
                           text = {
                               Text(category, modifier = Modifier.padding(10.dp))
                           },
                           onClick = {
                               viewModel.updateBusinessCategory(category)
                               bcExpanded = false
                           }
                       )
                   }
               }
           }

            Box() {
                OutlinedTextField(
                    value = TextFieldValue(state.callStatus),
                    onValueChange = { viewModel.updateCallStatus(it.text) },
                    trailingIcon = {
                        Icon(
                            csIcon,
                            "Dropdown Icon",
                            Modifier.clickable { csExpanded = !csExpanded }
                        )
                    },
                    label = { Text("Call Status") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp))
                        .padding(5.dp)
                )

                DropdownMenu(
                    expanded = csExpanded,
                    onDismissRequest = { csExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth().height(200.dp)
                        .background(Color.White)
                ) {
                    DropdownLists.callStatus.forEach { status ->
                        DropdownMenuItem(
                            text = {
                                Text(status, modifier = Modifier.padding(10.dp))
                            },
                            onClick = {
                                viewModel.updateCallStatus(status)
                                csExpanded = false
                            }
                        )
                    }
                }

            }
            Box() {
                OutlinedTextField(
                    value = TextFieldValue(state.leadStatus),
                    onValueChange = { viewModel.updateLeadStatus(it.text) },
                    trailingIcon = {
                        Icon(
                            lsIcon,
                            "Dropdown Icon",
                            Modifier.clickable { lsExpanded = !lsExpanded }
                        )
                    },
                    label = { Text("Lead Status*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp))
                        .padding(5.dp)
                )

                DropdownMenu(
                    expanded = lsExpanded,
                    onDismissRequest = { lsExpanded = false },
                    modifier = Modifier
                        .fillMaxWidth().height(200.dp)
                        .background(Color.White)
                ) {
                    DropdownLists.leadStatus.forEach { status ->
                        DropdownMenuItem(
                            text = {
                                Text(status, modifier = Modifier.padding(10.dp))
                            },
                            onClick = {
                                viewModel.updateLeadStatus(status)
                                lsExpanded = false
                            }
                        )
                    }
                }

            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = TextFieldValue(state.followUpDate),
                    onValueChange = { viewModel.updateFollowUpDate(it.text) },
                    label = { Text("Follow Up Date*") },
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(5.dp))
                        .weight(1f)
                        .padding(5.dp, end = 0.dp)
                )
                Icon(imageVector = Icons.Default.DateRange,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable { mDatePickerDialog.show() }
                        .padding(5.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = TextFieldValue(state.followUpTime),
                    onValueChange = { viewModel.updateFollowUpTime(it.text) },
                    label = { Text("Follow Up Time*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(5.dp))
                        .padding(5.dp)
                        .clickable { mTimePickerDialog.show() }

                )
                Icon(imageVector = Icons.Default.AddCircle,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable { mDatePickerDialog.show() }
                        .padding(5.dp))

            }

            Column {
                Text(
                    text = "Follow Up Action*",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(5.dp)

                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.followUpActionCall,
                        onClick = { viewModel.toggleFollowUpActionCall() }
                    )
                    Text(
                        text = "Call",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,

                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.followUpActionVisit,
                        onClick = { viewModel.toggleFollowUpActionVisit() }
                    )
                    Text(
                        text = "Visit",
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }

            OutlinedTextField(
                value = TextFieldValue(state.comments),
                onValueChange = { viewModel.updateComments(it.text) },
                label = { Text("Comments") },
                modifier = Modifier
                    .fillMaxWidth().height(131.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .padding(5.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Save button logic
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .fillMaxHeight(),
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
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

