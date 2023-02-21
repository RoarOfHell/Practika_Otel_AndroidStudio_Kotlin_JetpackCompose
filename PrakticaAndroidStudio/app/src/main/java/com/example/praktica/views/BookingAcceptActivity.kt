package com.example.praktica.views

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.praktica.models.CompliteBooking
import com.example.praktica.models.Room
import com.example.praktica.userId
import com.example.praktica.views.ui.theme.PrakticaTheme
import java.time.LocalDate
import java.time.LocalDateTime

private  var bookingActivity: BookingAcceptActivity? = null
private var room: Room? = null
private var roomPrice = 0
@RequiresApi(Build.VERSION_CODES.O)
private var dateIn = LocalDateTime.now()
@RequiresApi(Build.VERSION_CODES.O)
private var dateOut = LocalDateTime.now()
private var fullName = ""


class BookingAcceptActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        bookingActivity = this@BookingAcceptActivity
        room = intent.getParcelableExtra<Room>("roomInfoBooking")!!
        roomPrice = intent.getIntExtra("roomPrice", 0)
        fullName = intent.getStringExtra("fullName")!!
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            dateIn = intent.getSerializableExtra("dateIn", LocalDateTime::class.java)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            dateOut = intent.getSerializableExtra("dateOut", LocalDateTime::class.java)
        }

        super.onCreate(savedInstanceState)

        setContent {
            PrakticaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting3()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Greeting3() {
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 10.dp, 10.dp, 80.dp)) {
            Text(text = "Номер отеля: 1294326", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Номер комнаты: ${room!!.roomId}", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Максимальное кол-во чел: ${room!!.maxOccupancy}", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "ФИО: ${fullName}", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "К оплате: ${roomPrice} руб", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Дата и время заезда: ${dateIn.dayOfMonth}-${dateIn.monthValue}-${dateIn.year} ${dateIn.hour}-${dateIn.minute}", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Дата и время выезда: ${dateOut.dayOfMonth}-${dateOut.monthValue}-${dateOut.year} ${dateOut.hour}-${dateOut.minute}", fontWeight = FontWeight.SemiBold)

        }
        //SelectDate(date)
        //SelectTime(time)

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

                CompliteBooking(room = room!!, fullName = fullName, price = roomPrice, dateIn = dateIn, dateOut = dateOut, userId = userId )
                Toast.makeText(bookingActivity, "Успешно", Toast.LENGTH_SHORT).show()
                bookingActivity!!.finish()
            },
                modifier = Modifier.width(180.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0.3f,0.3f,1f,1f))) {
                Text(text = "Продолжить", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    PrakticaTheme {
        Greeting3()
    }
}