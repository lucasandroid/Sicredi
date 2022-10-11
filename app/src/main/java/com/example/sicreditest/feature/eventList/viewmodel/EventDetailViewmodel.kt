package com.example.sicreditest.feature.eventList.viewmodel

import android.os.Build
import android.os.Bundle
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sicreditest.feature.eventList.datasource.EventSource
import com.example.sicreditest.feature.eventList.datasource.Rest
import com.example.sicreditest.feature.eventList.model.DetailEvent
import com.example.sicreditest.feature.eventList.viewmodel.state.EventDetailState

class EventDetailViewmodel: ViewModel() {

    private val eventDetail: MutableLiveData<EventDetailState> = MutableLiveData()
    val eventDetailLiveData: LiveData<EventDetailState> = eventDetail

    fun init(bundle: Bundle) {
        val event = if (Build.VERSION.SDK_INT >= 33) {
            bundle.getSerializable("event", DetailEvent::class.java)
        } else {
            bundle.getSerializable("event") as DetailEvent
        }

        event?.let {
            eventDetail.value = EventDetailState.EventDetailSuccess(it)
        }
    }
}