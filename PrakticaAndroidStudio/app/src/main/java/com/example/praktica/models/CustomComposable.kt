package com.example.praktica.models

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberImagePainter
import com.example.praktica.MainActivity
import com.example.praktica.views.RoomInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*




@Composable
fun BlockRoom(room: Room, context: Context){
    Spacer(modifier = Modifier.height(5.dp))
    Row(modifier = Modifier
        .clickable {
            val newWindow = Intent(context, RoomInfo::class.java)
            newWindow.putExtra("roomInfo", room)
            context.startActivity(newWindow)
        }
        .fillMaxWidth()
        .background(Color(0.3f, 0.3f, 0.3f, 0.5f))
        .height(120.dp)) {
        Image(modifier = Modifier
            .height(110.dp)
            .width(180.dp)
            .align(Alignment.CenterVertically)
            .padding(5.dp, 0.dp, 0.dp, 0.dp),
            painter = rememberImagePainter(room.photoUrl.toString()),
            contentDescription = "room")
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = room.roomType.toString(), color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(10.dp,5.dp,0.dp,0.dp))
            Text(text = "Вместимость: ${room.maxOccupancy.toString()} чел", color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(10.dp,5.dp,0.dp,0.dp))
            Spacer(modifier = Modifier.height(35.dp))
            Text(text = "${room.price.toString()} руб/сутки", color = Color.White, fontSize = 16.sp, modifier = Modifier
                .align(Alignment.End)
                .padding(0.dp, 0.dp, 10.dp, 0.dp))
        }
    }
}

@Composable
fun BlockRoomBooking(room: Room, booking: Booking,context: Context){
    Spacer(modifier = Modifier.height(5.dp))
    Row(modifier = Modifier
        .clickable {
            //val newWindow = Intent(context, RoomInfo::class.java)
            //newWindow.putExtra("roomInfo", room)
            //context.startActivity(newWindow)
        }
        .fillMaxWidth()
        .background(Color(0.3f, 0.3f, 0.3f, 0.5f))
        .height(150.dp)) {
        Image(modifier = Modifier
            .height(110.dp)
            .width(180.dp)
            .align(Alignment.CenterVertically)
            .padding(5.dp, 0.dp, 0.dp, 0.dp),
            painter = rememberImagePainter(room.photoUrl.toString()),
            contentDescription = "room")
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = room.roomType.toString(), color = Color.White, fontSize = 20.sp, modifier = Modifier.padding(10.dp,5.dp,0.dp,0.dp))
            Text(text = "C: ${booking.checkinDate}", color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(10.dp,5.dp,0.dp,0.dp))
            Text(text = "По: ${booking.checkoutDate}", color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(10.dp,5.dp,0.dp,0.dp))
            Text(text = "Стоимость: ${booking.price}", color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(10.dp,5.dp,0.dp,0.dp))
            Text(text = "ФИО: ${booking.guestName}", color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(10.dp,5.dp,0.dp,0.dp))

        }
    }
}




@Preview(showBackground = true)
@Composable
fun TestingCustomComposable(){
    LazyColumn(modifier = Modifier.fillMaxSize(),
        content = {
            item {
                BlockRoomBooking(Room(), Booking(),MainActivity())
            }
    })
}

