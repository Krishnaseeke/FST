package com.testapplication.www.homescreen.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.testapplication.www.homescreen.create.DropdownLists.bussinessCategory
import com.testapplication.www.homescreen.create.DropdownLists.callStatus
import com.testapplication.www.homescreen.create.DropdownLists.leadStatus
import kotlinx.coroutines.delay

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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit, onCategorySelected: (String) -> Unit,value:String) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val isSheetOpened = remember {
        mutableStateOf(false
        )
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = {
            BottomSheetDefaults.DragHandle() }, containerColor = Color.White
    ) {
        CategoryList(onCategorySelected,value)
    }

    LaunchedEffect(modalBottomSheetState.currentValue) {
        if (modalBottomSheetState.currentValue == SheetValue.Hidden) {
            if (isSheetOpened.value) {
                isSheetOpened.value = false
                onDismiss.invoke()
            } else {
                isSheetOpened.value = true
                modalBottomSheetState.show()
            }
        }
    }
}

@Composable
fun CategoryList(onCategorySelected: (String) -> Unit,type:String) {
    var showList = if(type=="BussinessCategory"){
        bussinessCategory
    }else if (type=="CallStatus"){
        callStatus
    }else{
        leadStatus
    }

    LazyColumn {
        items(showList) { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .clickable { onCategorySelected(category) } // Trigger callback on item click
            ) {
                Text(
                    text = category,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
            Divider()
        }
    }
}



