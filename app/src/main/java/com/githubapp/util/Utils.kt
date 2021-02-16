package com.githubapp.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun formatDate(date: String): String {
    val format1 = SimpleDateFormat("yyyy-MM-dd")
    val format2 = SimpleDateFormat("dd/MM/yyyy")
    var dt: Date? = null
    try {
        dt = format1.parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
        return ""
    }
    return format2.format(dt)
}

