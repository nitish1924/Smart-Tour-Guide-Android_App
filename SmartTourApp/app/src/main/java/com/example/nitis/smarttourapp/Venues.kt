package com.example.nitis.smarttourapp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Venues(
        @SerializedName("meta")val meta: Meta?,
        @SerializedName("response")val response: Response?
):Serializable

data class Response(
        @SerializedName("venues")val venues: List<Venue?>?
):Serializable

data class Venue(
        @SerializedName("categories")val categories: List<Category?>?,
        @SerializedName("delivery")val delivery: Delivery?,
        @SerializedName("hasPerk")val hasPerk: Boolean?,
        @SerializedName("id")val id: String?,
        @SerializedName("location")val location: Location?,
        @SerializedName("name")val name: String?,
        @SerializedName("referralId")val referralId: String?,
        @SerializedName("venuePage")val venuePage: VenuePage?,
        @SerializedName("description")var description:String?,
        @SerializedName("isOpen")var isOpen: String?,
        @SerializedName("rating")var rating: String?,
        @SerializedName("price")var price:String?,
        @SerializedName("contact")var contact:String?,
        @SerializedName("photoUrl")var photoUrl:String?
):Serializable


data class VenuePage(
        @SerializedName("id")val id: String?
):Serializable

data class Category(
        @SerializedName("icon")val icon: Icon?,
        @SerializedName("id")val id: String?,
        @SerializedName("name")val name: String?,
        @SerializedName("pluralName")val pluralName: String?,
        @SerializedName("primary")val primary: Boolean?,
        @SerializedName("shortName")val shortName: String?
):Serializable


data class Delivery(
        @SerializedName("id")val id: String?,
        @SerializedName("provider")val provider: Provider?,
        @SerializedName("url")val url: String?
):Serializable

data class Provider(
        @SerializedName("icon")val icon: Icon?,
        @SerializedName("name")val name: String?
):Serializable

data class Icon(
        @SerializedName("name")val name: String?,
        @SerializedName("prefix") val prefix: String?,
        @SerializedName("suffix")val suffix: String?,
        @SerializedName("sizes")val sizes: List<Int?>?
):Serializable

data class Location(
        @SerializedName("address")val address: String?,
        @SerializedName("cc")val cc: String?,
        @SerializedName("city")val city: String?,
        @SerializedName("country")val country: String?,
        @SerializedName("distance")val distance: Int?,
        @SerializedName("formattedAddress")val formattedAddress: List<String?>?,
        @SerializedName("labeledLatLngs")val labeledLatLngs: List<LabeledLatLng?>?,
        @SerializedName("lat") var lat: Double?,
        @SerializedName("lng") var lng: Double?,
        @SerializedName("postalCode")val postalCode: String?,
        @SerializedName("state") val state: String?
):Serializable
data class LabeledLatLng(
        @SerializedName("label")val label: String?,
        @SerializedName("lat")val lat: Double?,
        @SerializedName("lng")val lng: Double?
):Serializable

data class Meta(
        @SerializedName("code")val code: Int?,
        @SerializedName("requestId") val requestId: String?
):Serializable

