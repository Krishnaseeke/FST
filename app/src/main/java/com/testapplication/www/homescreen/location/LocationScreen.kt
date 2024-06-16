package com.testapplication.www.homescreen.location

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LocationScreen(context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50))
            .padding(bottom = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Latitude:",
        )

        Text(
            text = "Latitude will be here!",
            color = Color(0xFFF5F5F5),
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        )

        Text(
            text = "Longitude:",
        )

        Text(
            text = "Longitude will be here!",
            color = Color(0xFFF5F5F5),
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        )
    }
}

