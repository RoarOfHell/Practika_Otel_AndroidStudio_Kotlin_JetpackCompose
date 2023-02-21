package com.example.praktica.models

import android.os.Build
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.time.LocalDateTime


@Composable
fun DateTimePicker(
    dateTime: LocalDateTime,
    onDateTimeChanged: (LocalDateTime) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Selected date and time:",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = dateTime.toString(),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Select date and time")
        }
    }

    if (showDialog) {
        DateTimePickerDialog(
            dateTime = dateTime,
            onDateTimeSelected = { newDateTime ->
                onDateTimeChanged(newDateTime)
                showDialog = false
            },
            onDismissRequest = { showDialog = false }
        )
    }
}

@Composable
fun DateTimePickerDialog(
    dateTime: LocalDateTime,
    onDateTimeSelected: (LocalDateTime) -> Unit,
    onDismissRequest: () -> Unit
) {
    var selectedDateTime by remember { mutableStateOf(dateTime) }

    Dialog(onDismissRequest = onDismissRequest) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Select date and time:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )



            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Button(
                    onClick = onDismissRequest,
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Cancel")
                }

                Button(
                    onClick = { onDateTimeSelected(selectedDateTime) },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "OK")
                }
            }
        }
    }
}

