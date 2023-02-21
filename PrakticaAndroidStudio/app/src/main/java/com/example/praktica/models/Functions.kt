package com.example.praktica.models

import com.google.gson.Gson
import java.net.URL
import java.time.LocalDateTime



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
    Thread{
        var result = URL("http://f0780687.xsph.ru/API/GetBookingAPI.php/?userId=${userId}").readText()
        val gson = Gson()
        var bookings = gson.fromJson(result, Bookings()::class.java)
        onThreadComplite(bookings)
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
