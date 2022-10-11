package com.example.sicreditest.feature.eventList.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class StringUtils {
    companion object {
        fun convertToCurrency(value: Double): String {
            val formatter = DecimalFormat("R$##,###.00")
            return formatter.format(value)
        }

        fun convertLongToDateString(date:Long): String {
            val format = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
            var dateFormated = ""
            try {
                dateFormated = format.format(date)
            } catch (e: IllegalArgumentException) {
                e.stackTrace
            }
            return dateFormated
        }
    }
}