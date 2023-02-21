package com.example.praktica

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.praktica.models.*
import com.example.praktica.ui.theme.PrakticaTheme
import com.example.praktica.views.AutorizationActivity

private var mainActivity: MainActivity? = null

private var bookings = mutableStateOf(Bookings())
var rooms = mutableStateOf(Rooms())

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GetAllRooms(result = {value -> rooms.value = value})
        setContent {
            PrakticaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    mainActivity = this@MainActivity

                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "HomeMenu") {
                        composable("HomeMenu") { HomeMenu() }
                        composable("SearchMenu") { SearchMenu() }
                        composable("ProfileMenu") { ProfileMenu() }
                    }
                    MainMenuPanel(navController)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PrakticaTheme {
        ProfileMenu()
    }
}

@Composable
fun MainMenuPanel(navController: NavController){
    Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxHeight()){
        val buttomItems = listOf("Search", "Home", "Profile")
        BottomAppBar(modifier = Modifier.padding(0.dp)) {
            Spacer(Modifier.weight(1f, true))
            IconButton(onClick = { navController.navigate("SearchMenu") }, modifier = Modifier
                .height(50.dp)
                .width(50.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Search, contentDescription = "Home" ,
                        modifier = Modifier
                            .height(25.dp)
                            .width(25.dp))
                    Text(text = "Search")
                }
            }
            Spacer(Modifier.weight(1f, true))
            IconButton(onClick = { navController.navigate("HomeMenu") }, modifier = Modifier
                .height(50.dp)
                .width(50.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.Home, contentDescription = "Home" ,
                        modifier = Modifier
                            .height(25.dp)
                            .width(25.dp))
                    Text(text = "Home")
                }
            }
            Spacer(Modifier.weight(1f, true))
            IconButton(onClick = { navController.navigate("ProfileMenu")
                GetAllBooking(userInfo.value.id!!.toInt(), onThreadComplite = { value -> bookings.value = value })}, modifier = Modifier
                .height(50.dp)
                .width(50.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.AccountBox, contentDescription = "Home" ,
                        modifier = Modifier
                            .height(25.dp)
                            .width(25.dp))
                    Text(text = "Profile")
                }
            }
            Spacer(Modifier.weight(1f, true))
        }
    }
}

@Composable
fun HomeMenu(){
    LazyColumn(modifier = Modifier.fillMaxSize(),
        content = {
            itemsIndexed(rooms.value.rooms){it, room ->
                BlockRoom(room = room, context = mainActivity!!)
            }
        })
}

@Composable
fun SearchMenu(){
    Text(text = "search")
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileMenu(){
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Gray)){
            Text(text = "Профиль", fontSize = 18.sp, modifier = Modifier.padding(15.dp))
            TextButton(modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(15.dp),
                onClick = { RemoveSaveUser(mainActivity!!)
                val autorization = Intent(mainActivity!!, AutorizationActivity::class.java)
                mainActivity!!.startActivity(autorization)
            }) {
                Text(text = "Выйти", color = Color.Red)
            }
        }
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 0.dp, 0.dp, 85.dp),
            content = {
                itemsIndexed(bookings.value.bookings){it, booking ->
                    var room: Room = Room()
                    GetRoomAtId(booking.roomId!!.toInt(), rooms.value, result = {value -> room = value})
                    BlockRoomBooking(booking = booking, room = room, context = mainActivity!!)
                }
            }
        )
    }

}


