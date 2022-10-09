package com.example.sicreditest.feature.eventList.model

open class EventListState {

    class EventListSuccess(val list : List<SicrediEvent>) : EventListState()
    class EventListError(val error: String): EventListState()

}

open class EventDetailState {
    class EventDetailSuccess(val details: SicrediEvent): EventDetailState()
}
