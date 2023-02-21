package com.example.praktica.models

import android.app.Activity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.toLowerCase
import com.google.gson.Gson
import java.net.URL
import java.time.LocalDateTime

var userInfo = mutableStateOf(UserInfo())

fun GetAllRooms(result: (Rooms) -> Unit){
    Thread{
        var rooms : Rooms = Rooms()
        val gson = Gson()
        var json = URL("http://f0780687.xsph.ru/API/GetAllRooms.php").readText()
        rooms = gson.fromJson(json, Rooms::class.java)
        result(rooms)
    }.start()
}

fun GetPriceAtDateTime(days:Int, hour:Int, min:Int, price:Float) : Float{
    var priceAtHour = price/24
    var priceAtMinutes = priceAtHour/60

    return (days*price) + (hour * priceAtHour) + (min * priceAtMinutes)
}

fun CompliteBooking(room: Room, fullName:String, price:Int, dateIn:LocalDateTime, dateOut:LocalDateTime, userId: Int){
    var urlGet = "?roomId=${room.roomId}&fullName=${fullName}&dateIn=${dateIn}&dateOut=${dateOut}&status=Confirmed&price=${price}&userId=${userId}"
    println(urlGet)
    Thread{
        var status = URL("http://f0780687.xsph.ru/API/AddBookingAPI.php/${urlGet}").readText()
    }.start()
}

fun GetAllBooking(userId: Int, onThreadComplite: (Bookings) -> Unit){
    println(userId)
    Thread{
        var result = URL("http://f0780687.xsph.ru/API/GetBookingAPI.php/?userId=${userId}").readText()
        if(result != "null"){
            val gson = Gson()
            var bookings = gson.fromJson(result, Bookings()::class.java)
            onThreadComplite(bookings)
        }

    }.start()
}

fun GetRoomAtId(roomId: Int, rooms: Rooms,result: (Room) -> Unit){
    if(rooms.rooms.size >= roomId){
        for (i in 0 until rooms.rooms.size){
            if(rooms.rooms[i].roomId == roomId.toString()){
                result(rooms.rooms[i])
            }
        }
    }
}

fun LoginAccount(activity: Activity, login: String, password: String, onAutorization: (String) -> Unit){
    Thread{
        val urlRequest = "http://f0780687.xsph.ru/API/LoginAPI.php?login=${login.toLowerCase()}&password=${password}"
        var result = URL(urlRequest).readText()

        if(result == "error"){
            onAutorization("Не верный логин или пароль")
        }
        else{
            val gson = Gson()
            userInfo.value = gson.fromJson(result, UserInfo::class.java)
            SaveUserAccount(activity, login, password)
            onAutorization("Успешно")
        }



    }.start()
}

fun RegistrationAccount(login: String, password: String, onRegistration: (String) -> Unit){
    Thread{
        val urlRequest = "http://f0780687.xsph.ru/API/RegistrationAPI.php?login=${login.toLowerCase()}&password=${password}"
        println(urlRequest)
        val result = URL(urlRequest).readText()
        println(result)
        var resultReg = ""
        resultReg = "Произошла непредвиденная ошибка"
        if(result == "user error create"){
            resultReg = "Такой аккаунт уже существует"
        }
        if(result == "error"){
            resultReg = "Отсутствует логин или пароль"
        }
        if(result == "complited"){
            resultReg = "Аккаунт успешно создан"
        }

        onRegistration(resultReg)
    }.start()
}

fun RemoveSaveUser(activity: Activity){
    val sharedPreferences = activity.getSharedPreferences("appPraktica", 0)
    sharedPreferences.edit().putString("login", "").apply()
    sharedPreferences.edit().putString("password", "").apply()
}

fun SaveUserAccount(activity: Activity, login: String, password: String){
    val sharedPreferences = activity.getSharedPreferences("appPraktica", 0)
    sharedPreferences.edit().putString("login", login.toLowerCase()).apply()
    sharedPreferences.edit().putString("password", password).apply()
}

fun LoadUserAccount(activity: Activity, onLoadUserInfo: (String, String) -> Unit){
    val sharedPreferences = activity.getSharedPreferences("appPraktica", 0)
    var login = sharedPreferences.getString("login", "")!!
    var password = sharedPreferences.getString("password", "")!!
    onLoadUserInfo(login, password)
}