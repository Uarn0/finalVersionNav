package com.example.cursova.model.event

interface IEvent {
    fun getEventType(): Int
    fun getEventTime(): String
    fun getName() : String
    companion object{
        const val REPAIR_EVENT_TYPE = 0
        const val TRIP_EVENT_TYPE = 1
        const val SERVICE_EVENT_TYPE = 2
    }
}