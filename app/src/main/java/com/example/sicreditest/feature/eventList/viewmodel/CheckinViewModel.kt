package com.example.sicreditest.feature.eventList.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.sicreditest.feature.eventList.datasource.EventSource
import com.example.sicreditest.feature.eventList.datasource.Rest
import com.example.sicreditest.feature.eventList.model.CheckinData
import com.example.sicreditest.feature.eventList.viewmodel.state.EventCheckinState
import kotlinx.coroutines.launch

class CheckinViewModel(private val repository: EventSource): ViewModel() {

    private val _validForm: MutableLiveData<Boolean> = MutableLiveData()
    val validForm: LiveData<Boolean> = _validForm

    private val _checkinLoad: MutableLiveData<EventCheckinState> = MutableLiveData()
    val checkinLoading: LiveData<EventCheckinState> = _checkinLoad

    private val _checkinResult: MutableLiveData<EventCheckinState> = MutableLiveData()
    val checkinResult: LiveData<EventCheckinState> = _checkinResult

    fun sendCheckin(checkinData: CheckinData) {

        viewModelScope.launch {

            _checkinLoad.value = EventCheckinState.CheckinShowLoading()

            try {
                val response = repository.checkin(checkinData)
                _checkinResult.value = when(response.code()) {
                    201 -> EventCheckinState.EventCheckinSuccess()
                    else -> {
                        EventCheckinState.EventCheckinError()
                    }
                }
            } catch (e: Exception) {
                _checkinResult.value = EventCheckinState.EventCheckinError()
            }
        }
    }

    fun validateForm(name: String, email:String) {
        _validForm.value = name.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
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