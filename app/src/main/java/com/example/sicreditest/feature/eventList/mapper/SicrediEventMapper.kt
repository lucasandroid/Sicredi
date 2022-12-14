package com.example.sicreditest.feature.eventList.mapper

import com.example.sicreditest.feature.eventList.model.DetailEvent
import com.example.sicreditest.feature.eventList.model.SicrediEvent
import com.example.sicreditest.feature.eventList.util.StringUtils

class SicrediEventMapper {

    fun parseEventListResponseToDetailList(response: List<SicrediEvent>):List<DetailEvent>{

        val newList = ArrayList<DetailEvent>()
        response.map {
            val detail = DetailEvent()
            detail.id = it.id
            detail.title = it.title
            detail.date = StringUtils.convertLongToDateString(it.date)
            detail.price = StringUtils.convertToCurrency(it.price)
            detail.description = it.description
            detail.latitude = it.latitude
            detail.longitude = it.longitude
            detail.imageUrl = it.imageUrl
            newList.add(detail)
        }
        return newList
    }
}
