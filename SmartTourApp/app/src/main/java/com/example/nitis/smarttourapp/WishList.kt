package com.example.nitis.smarttourapp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WishList(
        @SerializedName("user")var user:String?,
        @SerializedName("name")var name:String?,
        @SerializedName("description")var description:String?,
        @SerializedName("rating")var rating: String?,
        @SerializedName("price")var price:String?,
        @SerializedName("contact")var contact:String?,
        @SerializedName("photoUrl")var photoUrl:String?,
        @SerializedName("latitude")var latitude:String?,
        @SerializedName("longitude")var longitude:String?,
        @SerializedName("address")var address:String?
): Serializable