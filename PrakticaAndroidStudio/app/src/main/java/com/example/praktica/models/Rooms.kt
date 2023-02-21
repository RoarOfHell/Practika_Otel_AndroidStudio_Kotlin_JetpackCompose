package com.example.praktica.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Rooms(
    @SerializedName("rooms" ) var rooms : ArrayList<Room> = arrayListOf()
)

data class Room (
    @SerializedName("room_id"       ) var roomId       : String? = null,
    @SerializedName("room_type"     ) var roomType     : String? = null,
    @SerializedName("price"         ) var price        : String? = null,
    @SerializedName("max_occupancy" ) var maxOccupancy : String? = null,
    @SerializedName("description"   ) var description  : String? = null,
    @SerializedName("photo_url"     ) var photoUrl     : String? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(roomId)
        parcel.writeString(roomType)
        parcel.writeString(price)
        parcel.writeString(maxOccupancy)
        parcel.writeString(description)
        parcel.writeString(photoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Room> {
        override fun createFromParcel(parcel: Parcel): Room {
            return Room(parcel)
        }

        override fun newArray(size: Int): Array<Room?> {
            return arrayOfNulls(size)
        }
    }
}