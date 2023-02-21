package com.example.praktica.views

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktica.models.DateTimePicker
import com.example.praktica.models.GetPriceAtDateTime
import com.example.praktica.models.Room

import com.example.praktica.views.ui.theme.PrakticaTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.absoluteValue

private  var bookingActivity: BookingActivity? = null

private var room: Room? = null

@RequiresApi(Build.VERSION_CODES.O)
private var dateIn = mutableStateOf(LocalDate.now())
@RequiresApi(Build.VERSION_CODES.O)
private var dateOut = mutableStateOf(LocalDate.now())
private var countDay = mutableStateOf(0)
private var fullName = mutableStateOf("")

private var sumPrice = mutableStateOf(0)

class BookingActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        bookingActivity = this@BookingActivity
        room = intent.getParcelableExtra<Room>("roomInfoBooking")!!
        dateIn.value = LocalDate.now()
        dateOut.value = LocalDate.now()
        countDay.value = 0
        sumPrice.value = 0
        super.onCreate(savedInstanceState)
        setContent {
            PrakticaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BookingWindow()
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    PrakticaTheme {
        BookingWindow()
    }
}


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BookingWindow(){

    var timeIn = mutableStateOf(LocalTime.now())

    var timeOut = mutableStateOf(LocalTime.now())


    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 10.dp, 10.dp, 80.dp)) {
            Text(text = "Номер отеля: 1294326", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Номер комнаты: ${room!!.roomId}", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Максимальное кол-во чел: ${room!!.maxOccupancy}", fontWeight = FontWeight.SemiBold)

            Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Дата и время заезда:", fontWeight = FontWeight.SemiBold)
                TextButton(onClick = {
                    SelectDate(date = dateIn, onDateSelected = {value -> dateIn.value = value
                    })
                }) {
                    Text(text = "${dateIn.value.dayOfMonth}-${dateIn.value.monthValue}-${dateIn.value.year}", fontWeight = FontWeight.SemiBold, textDecoration = TextDecoration.Underline)
                }
                TextButton(onClick = { SelectTime(time = timeIn, onTimeSelected = {value -> timeIn.value = value}) }) {
                    Text(text = "${timeIn.value.hour}:${timeIn.value.minute}", fontWeight = FontWeight.SemiBold, textDecoration = TextDecoration.Underline)
                }
            }

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Дата и время выезда:", fontWeight = FontWeight.SemiBold)
                TextButton(onClick = {
                    SelectDate(date = dateOut, onDateSelected = {value -> dateOut.value = value}) }) {
                    Text(text = "${dateOut.value.dayOfMonth}-${dateOut.value.monthValue}-${dateOut.value.year}", fontWeight = FontWeight.SemiBold, textDecoration = TextDecoration.Underline)
                }
                TextButton(onClick = { SelectTime(time = timeOut, onTimeSelected = {value -> timeOut.value = value}) }) {
                    Text(text = "${timeOut.value.hour}:${timeOut.value.minute}", fontWeight = FontWeight.SemiBold, textDecoration = TextDecoration.Underline)
                }
            }
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "ФИО: ", fontWeight = FontWeight.SemiBold)
                TextField(value = fullName.value, onValueChange = {value -> fullName.value = value}, singleLine = true)
            }
        }
        //SelectDate(date)
        //SelectTime(time)

        Column(modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 0.dp, 0.dp, 50.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Сумма: ${sumPrice.value} руб", fontWeight = FontWeight.SemiBold)
        }

        Row(modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { bookingActivity!!.finish() },
            modifier = Modifier.width(180.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0.6f,0f,0f,1f))
            ) {
                Text(text = "Отмена", fontSize = 18.sp, color = Color.White)
            }
            Button(onClick = {
                if(fullName.value.trim().split(" ").size > 1){
                    val bookingAcceptActivity = Intent(bookingActivity, BookingAcceptActivity::class.java)
                    bookingAcceptActivity.putExtra("roomInfoBooking", room)
                    bookingAcceptActivity.putExtra("roomPrice", sumPrice.value)
                    bookingAcceptActivity.putExtra("dateIn", (LocalDateTime.of(dateIn.value, timeIn.value)))
                    bookingAcceptActivity.putExtra("dateOut", (LocalDateTime.of(dateOut.value, timeOut.value)))
                    bookingAcceptActivity.putExtra("fullName", fullName.value)
                    bookingActivity!!.startActivity(bookingAcceptActivity)
                    bookingActivity!!.finish()
                }
                else{
                    Toast.makeText(bookingActivity, "ФИО не соответствует", Toast.LENGTH_SHORT).show()
                }
            },
                modifier = Modifier.width(180.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0.3f,0.3f,1f,1f))) {
                Text(text = "Продолжить", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun SelectDate(date: MutableState<LocalDate>, onDateSelected: (LocalDate) -> Unit){

    val datePickerDialog = DatePickerDialog(
        bookingActivity!!,
        DatePickerDialog.OnDateSetListener{ datePicker, year, month, day ->
            onDateSelected(LocalDate.of(year,month+1, day))
            countDay.value = ChronoUnit.DAYS.between(dateIn.value, dateOut.value).toInt()
            sumPrice.value = GetPriceAtDateTime(countDay.value, 0, 0, room!!.price!!.toFloat()).toInt()
        }
        ,
        date.value.year, date.value.month.value-1, date.value.dayOfMonth
    )
    datePickerDialog.show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun SelectTime(time: MutableState<LocalTime>,
               onTimeSelected: (LocalTime) -> Unit){

    val timePickerDialog = TimePickerDialog(
        bookingActivity!!,
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            onTimeSelected(LocalTime.of(hourOfDay, minute))
        }, time.value.hour, time.value.minute, false
    )
    timePickerDialog.show()
}