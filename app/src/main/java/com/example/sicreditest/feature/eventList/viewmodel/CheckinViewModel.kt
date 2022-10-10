package com.example.sicreditest.feature.eventList.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sicreditest.feature.eventList.datasource.EventSource
import com.example.sicreditest.feature.eventList.datasource.Rest
import com.example.sicreditest.feature.eventList.model.CheckinData
import kotlinx.coroutines.launch

class CheckinViewModel(private val repository: EventSource): ViewModel() {


    fun sendCheckin(checkinData: CheckinData) {

        viewModelScope.launch {
            try {
                val response = repository.checkin(checkinData)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return CheckinViewModel(Rest.service.create(EventSource::class.java) ) as T
            }
        }
    }
}