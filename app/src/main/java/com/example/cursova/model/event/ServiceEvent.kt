package com.example.cursova.model.event

import android.annotation.SuppressLint

data class ServiceEvent(
    val service: String,
    val createdAt: Long = System.currentTimeMillis()
) : IEvent {
    @SuppressLint("SimpleDateFormat")
    override fun getEventTime(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(java.util.Date(createdAt))
    }

    override fun getEventType(): Int = IEvent.SERVICE_EVENT_TYPE
}

