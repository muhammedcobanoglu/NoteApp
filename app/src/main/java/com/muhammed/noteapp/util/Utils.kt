package com.muhammed.noteapp.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.dateToString(): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    val date = Date()
    return formatter.format(date)
}