package com.example.sicreditest.feature.eventList.datasource

import com.example.sicreditest.feature.eventList.model.CheckinData
import com.example.sicreditest.feature.eventList.model.SicrediEvent
import okhttp3.ResponseBody
import retrofit2.Response
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
    suspend fun checkin(@Body checkinData: CheckinData): Response<ResponseBody>
}