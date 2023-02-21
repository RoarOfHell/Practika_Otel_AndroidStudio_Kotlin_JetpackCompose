package com.example.praktica.models

import com.google.gson.annotations.SerializedName


data class Bookings (

    @SerializedName("bookings" ) var bookings : ArrayList<Booking> = arrayListOf()

)

data class Booking (

    @SerializedName("booking_id"    ) var bookingId    : String? = null,
    @SerializedName("room_id"       ) var roomId       : String? = null,
    @SerializedName("guest_name"    ) var guestName    : String? = null,
    @SerializedName("checkin_date"  ) var checkinDate  : String? = null,
    @SerializedName("checkout_date" ) var checkoutDate : String? = null,
    @SerializedName("status"        ) var status       : String? = null,
    @SerializedName("price"         ) var price        : String? = null,
    @SerializedName("user_id"       ) var userId       : String? = null

)