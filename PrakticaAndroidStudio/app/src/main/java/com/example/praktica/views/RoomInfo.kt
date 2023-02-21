package com.example.praktica.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Layout.Alignment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.praktica.models.Room
import com.example.praktica.views.ui.theme.PrakticaTheme

private var room: Room = Room()
private var roomInfo: RoomInfo? = null

class RoomInfo : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra<Room>("roomInfo")!!
        roomInfo = this@RoomInfo
        setContent {
            PrakticaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting2("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting2(name: String) {
    LazyColumn(modifier = Modifier.fillMaxSize(),
        content = {
        item {
            Box(modifier = Modifier.background(Color.Gray).fillMaxSize()){
                Image(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                    contentScale = ContentScale.Crop,
                    painter = rememberImagePainter(room.photoUrl.toString()), contentDescription = "image")
                Column(modifier = Modifier.fillMaxSize().height(300.dp).padding(10.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = androidx.compose.ui.Alignment.End) {
                    Text(text = room.roomType.toString(), fontSize = 28.sp, color = Color(1f,1f,1f,0.5f), fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.Serif)
                }
            }

        }
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Стоимость: ${room.price.toString()} руб/сутки",
                        softWrap = true,
                        modifier = Modifier
                            .padding(15.dp,5.dp,5.dp,5.dp), fontSize = 16.sp
                    )
                    Text(text = "Вместимость: ${room.maxOccupancy.toString()} чел",
                        softWrap = true,
                        modifier = Modifier
                            .padding(15.dp,5.dp,5.dp,5.dp), fontSize = 16.sp)
                    Text(text = "Тип комнаты: ${room.roomType.toString()}",
                        softWrap = true,
                        modifier = Modifier
                            .padding(15.dp,5.dp,5.dp,5.dp), fontSize = 16.sp)
                }
            }
            item {
                Text(text = "Описание комнаты", softWrap = true, modifier = Modifier.padding(15.dp,5.dp,5.dp,5.dp), fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                Text(text = room.description.toString(), softWrap = true, modifier = Modifier.padding(15.dp,5.dp,5.dp,5.dp))
            }
    })
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0f, 0.4f, 0f))){
            TextButton(onClick = {
                val booking = Intent(roomInfo, BookingActivity::class.java)
                booking.putExtra("roomInfoBooking", room)

                roomInfo!!.startActivity(booking)
            }) {
                Text(text = "Забронировать", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    PrakticaTheme {
        Greeting2("Android")
    }
}