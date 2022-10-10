package com.example.sicreditest.feature.eventList.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sicreditest.feature.eventList.datasource.EventSource
import com.example.sicreditest.feature.eventList.datasource.Rest
import com.example.sicreditest.feature.eventList.model.EventListState
import com.example.sicreditest.feature.eventList.mapper.SicrediEventMapper
import kotlinx.coroutines.launch

class EventListViewModel(private val repository: EventSource, private val mapper: SicrediEventMapper): ViewModel() {

    private var eventList : MutableLiveData<EventListState> = MutableLiveData()
    val eventListLiveData: LiveData<EventListState> = eventList

    fun init() {

        viewModelScope.launch {
            try {
                val response = repository.getList()
                val detailList = mapper.parseEventListResponseToDetailList(response)
                eventList.value = EventListState.EventListSuccess(detailList)
            } catch (e: Exception) {
                eventList.value = EventListState.EventListError(e.message ?: "")
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
                val mapper = SicrediEventMapper()
                return EventListViewModel(Rest.service.create(EventSource::class.java), mapper) as T
            }
        }
    }

}