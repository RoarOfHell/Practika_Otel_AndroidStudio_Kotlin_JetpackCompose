package com.example.praktica.models

import com.google.gson.annotations.SerializedName

data class UserInfo (

    @SerializedName("id"       ) var id       : String? = null,
    @SerializedName("username" ) var username : String? = null,
    @SerializedName("password" ) var password : String? = null

)