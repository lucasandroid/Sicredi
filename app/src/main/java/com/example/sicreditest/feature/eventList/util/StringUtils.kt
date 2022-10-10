package com.example.sicreditest.feature.eventList.util

import java.text.DecimalFormat

class StringUtils {
    companion object {
        fun convertToCurrency(value: Double): String {
            val formatter = DecimalFormat("R$##,###.00")
            return formatter.format(value)
        }
    }
}