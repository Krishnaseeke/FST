import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Calendar
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.TimePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.google.android.gms.location.LocationServices
import com.testapplication.www.BuildConfig
import com.testapplication.www.util.BottomSheet
import com.testapplication.www.util.CustomOutlinedTextField
import com.testapplication.www.util.OnSavingDialog
import com.testapplication.www.util.constants.Constants.ADD_ICON_DESCRIPTION
import com.testapplication.www.util.constants.Constants.CREATE_ADDRESS_FIELD
import com.testapplication.www.util.constants.Constants.CREATE_ATTACH_IMAGE_CTA
import com.testapplication.www.util.constants.Constants.CREATE_BUSSINESS_CATEGORY_FIELD
import com.testapplication.www.util.constants.Constants.CREATE_BUSSINESS_CATEGORY_FIELD_LIST
import com.testapplication.www.util.constants.Constants.CREATE_CALL_STATUS_FIELD
import com.testapplication.www.util.constants.Constants.CREATE_CALL_STATUS_FIELD_LIST
import com.testapplication.www.util.constants.Constants.CREATE_CATEGORY_FIELD_LABEL_TEXT
import com.testapplication.www.util.constants.Constants.CREATE_COMMENTS_FIELD
import com.testapplication.www.util.constants.Constants.CREATE_CUSTOMER_ALTERNATE_MOBILE_NO_FIELD
import com.testapplication.www.util.constants.Constants.CREATE_CUSTOMER_MOBILE_NO_FIELD
import com.testapplication.www.util.constants.Constants.CREATE_CUSTOMER_NAME_FIELD
import com.testapplication.www.util.constants.Constants.CREATE_DROP_DOWN_ICON_DESCRIPTION
import com.testapplication.www.util.constants.Constants.CREATE_FOLLOW_UP_ACTION_RADIO_BTN
import com.testapplication.www.util.constants.Constants.CREATE_FOLLOW_UP_CALL_RADIO_BTN
import com.testapplication.www.util.constants.Constants.CREATE_FOLLOW_UP_DATE_FIELD
import com.testapplication.www.util.constants.Constants.CREATE_FOLLOW_UP_TIME_FIELD
import com.testapplication.www.util.constants.Constants.CREATE_FOLLOW_UP_VISIT_RADIO_BTN
import com.testapplication.www.util.constants.Constants.CREATE_IMAGE_RECAPTURE_DESCRIPTION
import com.testapplication.www.util.constants.Constants.CREATE_LEAD_STATUS_FIELD
import com.testapplication.www.util.constants.Constants.CREATE_LEAD_STATUS_FIELD_LIST
import com.testapplication.www.util.constants.Constants.CREATE_PROOF_OF_MEETING_FIELD
import com.testapplication.www.util.constants.Constants.CREATE_SAVE_BTN
import com.testapplication.www.util.constants.Constants.CREATE_SCREEN_BACK_CTA_DESCRIPTION
import com.testapplication.www.util.constants.Constants.DEFAULT_ALERT_POP_UP
import com.testapplication.www.util.constants.Constants.ON_SAVE_DIALOG_DELAY
import com.testapplication.www.util.constants.Constants.SCREEN_CREATE
import com.testapplication.www.util.constants.Constants.SHOW_ALERT_POP_UP
import kotlinx.coroutines.delay
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
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

    var showDialog by remember { mutableStateOf(DEFAULT_ALERT_POP_UP) }
    if (state.isSubmissionSuccessful) {
        OnSavingDialog(
            showDialog = SHOW_ALERT_POP_UP,
            onDismiss = { showDialog = DEFAULT_ALERT_POP_UP })
        LaunchedEffect(Unit) {
            delay(ON_SAVE_DIALOG_DELAY)
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
            viewModel.updateLongitudeLocation(location.longitude.toString())
            viewModel.updateLatitudeLocation(location.latitude.toString())

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
        disabledLabelColor = Color.Black,
        errorBorderColor = Color.Red,
        errorPlaceholderColor = Color.Red,
        errorLabelColor = Color.Red

    )

    val textFieldStyle = TextStyle(color = Color.Black)


    // State to manage captured image URI
    var capturedImageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }


    // Create image file and URI
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        file
    )

    // Camera launcher
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                capturedImageUri = uri
                viewModel.updateProofImage(uri.toString())
            }
        }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

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
                contentDescription = CREATE_SCREEN_BACK_CTA_DESCRIPTION,
                modifier = Modifier
                    .clickable { toHome(userID) }
                    .padding(10.dp) //Need
            )
            Text(
                text = SCREEN_CREATE,
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
            CustomOutlinedTextField(
                value = state.customerName,
                onValueChange = { viewModel.updateCustomerName(it) },
                label = CREATE_CUSTOMER_NAME_FIELD,
                isError = state.isCustomerNameValid
            )


            CustomOutlinedTextField(
                value = state.phoneNumber,
                onValueChange = { viewModel.updatePhoneNumber(it) },
                label = CREATE_CUSTOMER_MOBILE_NO_FIELD,
                isError = state.isPhoneNumberValid,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )

            CustomOutlinedTextField(
                value = state.alternatePhoneNumber,
                onValueChange = { viewModel.updateAlternatePhoneNumber(it) },
                label = CREATE_CUSTOMER_ALTERNATE_MOBILE_NO_FIELD,
                isError = state.isAlternativePhoneNumberValid,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
            )


            OutlinedTextField(
                enabled = false,
                value = address,
                onValueChange = { viewModel.updateAddress(address) },
                label = { Text(CREATE_ADDRESS_FIELD) },
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
                Column {
                    Text(
                        text = CREATE_PROOF_OF_MEETING_FIELD,
                        color = if (state.isImageAttached && state.proofImage == "") Color.Red else Color.Black,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier.padding(10.dp)
                    )
                    // UI Components
                    Column(
                        Modifier
                            .fillMaxSize()
                            .background(Color.LightGray)
                            .padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (capturedImageUri != Uri.EMPTY || state.proofImage != "") {
                            Box(
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                            ) {
                                Image(
                                    painter = rememberImagePainter(Uri.parse(state.proofImage)),
                                    contentDescription = "Image Proof",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .matchParentSize()
                                )
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = CREATE_IMAGE_RECAPTURE_DESCRIPTION,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                        .clickable {
                                            capturedImageUri = Uri.EMPTY
                                            state.proofImage = ""
                                        }
                                )
                            }
                        } else {
                            Row(
                                Modifier
                                    .clickable {
                                        val permissionCheckResult =
                                            ContextCompat.checkSelfPermission(
                                                context,
                                                Manifest.permission.CAMERA
                                            )
                                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                                            cameraLauncher.launch(uri)
                                        } else {
                                            permissionLauncher.launch(Manifest.permission.CAMERA)
                                        }
                                    }
                                    .fillMaxSize(1f), Arrangement.Center) {
                                androidx.compose.material.Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = ADD_ICON_DESCRIPTION,
                                    modifier = Modifier.padding(5.dp)
                                )
                                androidx.compose.material.Text(
                                    text = CREATE_ATTACH_IMAGE_CTA,
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontStyle = FontStyle.Normal,
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                        }
                    }
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

            Box {
                if (bcshowSheet) {
                    BottomSheet(
                        onDismiss = {
                            bcshowSheet = false
                        },
                        onCategorySelected = { category ->
                            state.businessCategory = category
                            bcshowSheet = false
                        }, CREATE_BUSSINESS_CATEGORY_FIELD_LIST
                    )
                }

                OutlinedTextField(
                    enabled = false,
                    singleLine = true,
                    value = if (state.businessCategory.isNotEmpty()) state.businessCategory else CREATE_CATEGORY_FIELD_LABEL_TEXT,
                    onValueChange = { viewModel.updateBusinessCategory(it) },
                    isError = state.isBusinessCategorySelected,
                    trailingIcon = {
                        Icon(
                            bcIcon,
                            CREATE_DROP_DOWN_ICON_DESCRIPTION,
                            tint = Color.Black // Specify icon tint
                        )
                    },
                    label = { Text(CREATE_BUSSINESS_CATEGORY_FIELD) },
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
                        }, CREATE_CALL_STATUS_FIELD_LIST
                    )
                }

                OutlinedTextField(
                    enabled = false,
                    singleLine = true,
                    value = if (state.callStatus.isNotEmpty()) state.callStatus else CREATE_CATEGORY_FIELD_LABEL_TEXT,
                    onValueChange = { viewModel.updateCallStatus(it) },
                    isError = state.isCallStatusSelected,
                    trailingIcon = {
                        Icon(
                            csIcon,
                            CREATE_DROP_DOWN_ICON_DESCRIPTION,
                            tint = Color.Black // Specify icon tint
                        )
                    },
                    label = { Text(CREATE_CALL_STATUS_FIELD) },
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

            Box {
                if (lsshowSheet) {
                    BottomSheet(
                        onDismiss = {
                            lsshowSheet = false
                        },
                        onCategorySelected = { category ->
                            state.leadStatus = category
                            lsshowSheet = false
                        }, CREATE_LEAD_STATUS_FIELD_LIST
                    )
                }


                OutlinedTextField(
                    enabled = false,
                    singleLine = true,
                    value = if (state.leadStatus.isNotEmpty()) state.leadStatus else CREATE_CATEGORY_FIELD_LABEL_TEXT,
                    onValueChange = { viewModel.updateLeadStatus(it) },
                    isError = state.isLeadStatusSelected,
                    trailingIcon = {
                        Icon(
                            lsIcon,
                            CREATE_DROP_DOWN_ICON_DESCRIPTION,
                            tint = Color.Black // Specify icon tint
                        )
                    },
                    label = { Text(CREATE_LEAD_STATUS_FIELD) },
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

                CustomOutlinedTextField(
                    value = state.followUpDate,
                    onValueChange = { viewModel.updateFollowUpDate(it) },
                    label = CREATE_FOLLOW_UP_DATE_FIELD,
                    isError = state.isFollowUpDateSelected,
                    enabled = false
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
                CustomOutlinedTextField(
                    value = state.followUpTime,
                    onValueChange = { viewModel.updateFollowUpTime(it) },
                    label = CREATE_FOLLOW_UP_TIME_FIELD,
                    isError = state.isFollowUpTimeSelected,
                    enabled = false
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
                    text = CREATE_FOLLOW_UP_ACTION_RADIO_BTN,
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
                    Text(CREATE_FOLLOW_UP_CALL_RADIO_BTN, modifier = Modifier.padding(10.dp))
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = state.followUpActionVisit,
                        onClick = { viewModel.toggleFollowUpActionVisit() }
                    )
                    Text(CREATE_FOLLOW_UP_VISIT_RADIO_BTN, modifier = Modifier.padding(10.dp))
                }
            }

            OutlinedTextField(
                value = state.comments,
                onValueChange = { viewModel.updateComments(it) },
                label = { Text(CREATE_COMMENTS_FIELD) },
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
                        comments = state.comments,
                        proofImage = state.proofImage,
                        longitudeLocation = state.longitudeLocation,
                        latitudeLocation = state.latitudeLocation
                    ) // Save to the database
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Red)
            ) {
                Text(
                    text = CREATE_SAVE_BTN,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}

fun Context.createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_${timeStamp}_"
    val storageDir: File? = externalCacheDir
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        storageDir      /* directory */
    )
}