package com.testapplication.www.homescreen.create

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun CustomOutlinedTextField(text1: MutableState<TextFieldValue>, text:String) {
    OutlinedTextField(
        value = text1.value,
        onValueChange = { text1.value = it },
        label = {
            Text(
                text = text
            )
        },
        modifier = Modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .padding(5.dp)
    )
}



