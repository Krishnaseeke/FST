package com.testapplication.www.homescreen.create

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.testapplication.www.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AttachImageButton() {
    val context = LocalContext.current

    // State to manage captured image URI
    var capturedImageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    var showAttachImage by remember { mutableStateOf(false) }

    // Create image file and URI
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        context,
        "${BuildConfig.APPLICATION_ID}.provider",
        file
    )

    // Camera launcher
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            capturedImageUri = uri
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

    // UI Components
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val permissionCheckResult = ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.CAMERA
                )
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(uri)
                } else {
                    permissionLauncher.launch(android.Manifest.permission.CAMERA)
                }
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

        // Display captured image
        if (capturedImageUri != Uri.EMPTY) {
            Image(
                painter = rememberImagePainter(capturedImageUri),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth()
            )
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
