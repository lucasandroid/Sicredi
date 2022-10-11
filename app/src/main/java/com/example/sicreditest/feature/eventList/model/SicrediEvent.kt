package com.example.sicreditest.feature.eventList.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SicrediEvent(
    @SerializedName("date")
    val date: Long,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val imageUrl: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("people")
    val people: List<Any>,
    @SerializedName("price")
    val price: Double,
    @SerializedName("title")
    val title: String
) : Serializable
