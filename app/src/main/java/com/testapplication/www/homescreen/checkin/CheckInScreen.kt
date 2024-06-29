import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.testapplication.www.R
import java.io.File
import java.util.*

@Composable
fun CheckInScreen(
    toHome: (Any?) -> Unit,
    userID: Long,
    context: Context,
    modifier: Modifier = Modifier
) {
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
    var previewView by remember { mutableStateOf<PreviewView?>(null) }
    val lifecycleOwner = LocalContext.current as LifecycleOwner
    var address by remember { mutableStateOf("") }

    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getLastLocation(fusedLocationClient, context) { location ->
                address = getAddressFromLocation(location, context)
            }
        } else {
            Toast.makeText(context, "Location Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Camera Permission Granted", Toast.LENGTH_SHORT).show()
            previewView?.let {
                startCameraPreview(context, it, lifecycleOwner)
            }
        } else {
            Toast.makeText(context, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        val locationPermissionCheckResult = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (locationPermissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            getLastLocation(fusedLocationClient, context) { location ->
                address = getAddressFromLocation(location, context)
            }
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        val cameraPermissionCheckResult = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )
        if (cameraPermissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            previewView?.let {
                startCameraPreview(context, it, lifecycleOwner)
            }
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxSize()
    ) {

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
                    text = "Tap the Sign in button to Start your work",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier
                        .clickable {
                            toHome(context)
                        }
                        .padding(10.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(2.dp)
                .background(Color.White)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(1.dp))
                .weight(1f)
        ) {
            Text(
                text = "Picture",
                color = Color.Black,
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 600.dp)
            ) {
                AndroidView(
                    factory = { context ->
                        PreviewView(context).also {
                            previewView = it
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                startCameraPreview(context, it, lifecycleOwner)
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
                capturedImage?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Captured Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = address,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .background(Color.Black.copy(alpha = 0.7f))
                            .padding(8.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .background(Color.White)
        ) {
            Button(onClick = {
                if (capturedImage == null) {
                    val cameraPermissionCheckResult = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    )
                    if (cameraPermissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        previewView?.let {
                            captureImage(context, it, lifecycleOwner) { bitmap ->
                                capturedImage = bitmap
                                getLastLocation(fusedLocationClient, context) { location ->
                                    address = getAddressFromLocation(location, context)
                                }
                            }
                        }
                    } else {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                } else {
                    // Handle Check-In Logic
                    toHome(context)
                }
            }) {
                Text(text = if (capturedImage == null) "Capture Photo" else "Check-In")
            }
        }
    }
}

fun startCameraPreview(context: Context, previewView: PreviewView, lifecycleOwner: LifecycleOwner) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }, ContextCompat.getMainExecutor(context))
}

fun captureImage(
    context: Context,
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    onImageCaptured: (Bitmap) -> Unit
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val imageCapture = ImageCapture.Builder().build()
        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
            .build()

        try {
            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                imageCapture,
                Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
            )

            val outputOptions =
                ImageCapture.OutputFileOptions.Builder(createTempFile(context)).build()
            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(context),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val bitmap = BitmapFactory.decodeFile(outputFileResults.savedUri?.path)
                        onImageCaptured(bitmap)
                        cameraProvider.unbindAll() // Unbind all to stop the camera
                    }

                    override fun onError(exception: ImageCaptureException) {
                        exception.printStackTrace()
                    }
                })
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }, ContextCompat.getMainExecutor(context))
}

fun createTempFile(context: Context): File {
    val tempFile = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
    tempFile.createNewFile()
    return tempFile
}

fun getAddressFromBitmap(bitmap: Bitmap, context: Context): String {
    // Dummy coordinates; will be replaced by real coordinates from getLastLocation
    val latLng = LatLng(0.0, 0.0)
    return getAddress(latLng, context)
}

fun getLastLocation(
    fusedLocationClient: FusedLocationProviderClient,
    context: Context,
    onLocationReceived: (Location) -> Unit
) {
    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return
    }

    fusedLocationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            location?.let {
                onLocationReceived(it)
            } ?: run {
                Toast.makeText(context, "Location not found", Toast.LENGTH_SHORT).show()
            }
        }
}

fun getAddressFromLocation(location: Location, context: Context): String {
    val latLng = LatLng(location.latitude, location.longitude)
    return getAddress(latLng, context)
}

fun getAddress(latLng: LatLng, context: Context): String {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address>? = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
    val address: Address?
    return if (addresses != null && addresses.isNotEmpty()) {
        address = addresses[0]
        val fullAddress = address.getAddressLine(0)
//        val city = address.locality
//        val state = address.adminArea
//        val country = address.countryName
//        val postalCode = address.postalCode
//        val knownName = address.featureName
//        "$fullAddress, $city, $state, $country, $postalCode, $knownName"
        fullAddress
    } else {
        "Location not found"
    }
}
