package com.example.cursova.model

import com.example.cursova.model.event.IEvent
import com.example.cursova.model.transport.ITransport
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransportWithEvents(
    val transport: ITransport,
    val events: MutableList<IEvent>
)