package com.example.sicreditest.feature.eventList.datasource

import com.example.sicreditest.feature.eventList.model.Registered
import com.example.sicreditest.feature.eventList.model.SicrediEvent
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventSource {

    @GET("events")
    suspend fun getList(): List<SicrediEvent>

    @GET("events/{id}")
    suspend fun getEvent(@Path("id") id: Int): SicrediEvent

    @POST("checkin")
    suspend fun checkin(@Body registered: Registered)
}