package com.example.sicreditest.feature.eventList.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sicreditest.feature.eventList.datasource.EventSource
import com.example.sicreditest.feature.eventList.datasource.Rest
import com.example.sicreditest.feature.eventList.viewmodel.state.EventListState
import com.example.sicreditest.feature.eventList.mapper.SicrediEventMapper
import kotlinx.coroutines.launch

class EventListViewModel(private val repository: EventSource, private val mapper: SicrediEventMapper): ViewModel() {

    private var eventList : MutableLiveData<EventListState> = MutableLiveData()
    val eventListLiveData: LiveData<EventListState> = eventList

    private val _listLoad: MutableLiveData<EventListState> = MutableLiveData()
    val listLoading: LiveData<EventListState> = _listLoad

    fun init() {
        _listLoad.value = EventListState.ShowLoading()
        viewModelScope.launch {
            try {
                val response = repository.getList()
                val detailList = mapper.parseEventListResponseToDetailList(response)
                eventList.value = EventListState.EventListSuccess(detailList)
            } catch (e: Exception) {
                eventList.value = EventListState.EventListError()
            } finally {
                _listLoad.value = EventListState.HideLoading()
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