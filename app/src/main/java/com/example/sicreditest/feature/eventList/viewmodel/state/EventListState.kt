package com.example.sicreditest.feature.eventList.viewmodel.state

import com.example.sicreditest.feature.eventList.model.DetailEvent

open class EventListState {

    class EventListSuccess(val list : List<DetailEvent>) : EventListState()
    class EventListError: EventListState()
    class ShowLoading: EventListState()
    class HideLoading: EventListState()
}

open class EventDetailState {
    class EventDetailSuccess(val details: DetailEvent): EventDetailState()
}

open class EventCheckinState {
    class EventCheckinSuccess: EventCheckinState()
    class EventCheckinError: EventCheckinState()
    class CheckinShowLoading: EventCheckinState()
}
