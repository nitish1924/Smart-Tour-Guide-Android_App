package com.example.nitis.smarttourapp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User (
        @SerializedName("email") val email: String ?,
        @SerializedName ("password") val password: String ?,
        @SerializedName("name") val name: String ?,
@SerializedName ("contact") val contact: String ?
) : Serializable