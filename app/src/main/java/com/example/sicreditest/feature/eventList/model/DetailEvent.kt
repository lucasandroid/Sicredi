package com.example.sicreditest.feature.eventList.model

import java.io.Serializable

class DetailEvent: Serializable {
    var title: String? = null
    var date: String? = null
    var description: String? = null
    var id: Int? = null
    var imageUrl: String? = null
    var latitude: Double? = null
    var longitude: Double? = null
    var price: String? = null
}
