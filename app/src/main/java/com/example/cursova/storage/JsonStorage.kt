package com.example.cursova.storage

import com.example.cursova.model.TransportWithEvents
import android.content.Context
import com.example.cursova.model.event.*
import com.example.cursova.model.transport.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.File

object JsonStorage {
    private const val FILE_NAME = "transports.json"

    // 1. Опис Moshi з підтримкою поліморфізму
    private val moshi: Moshi = Moshi.Builder()
        .add(
            PolymorphicJsonAdapterFactory.of(ITransport::class.java, "type")
                .withSubtype(Car::class.java, "Car")
                .withSubtype(Minibus::class.java, "Minibus")
                .withSubtype(Motorcycle::class.java, "Motorcycle")
        )
        .add(
            PolymorphicJsonAdapterFactory.of(IEvent::class.java, "eventType")
                .withSubtype(RepairEvent::class.java, "RepairEvent")
                .withSubtype(ServiceEvent::class.java, "ServiceEvent")
                .withSubtype(TripEvent::class.java, "TripEvent")
        )
        .add(KotlinJsonAdapterFactory())
        .build()

    // 2. Тип для списку com.example.cursova.model.TransportWithEvents
    private val type = Types.newParameterizedType(List::class.java, TransportWithEvents::class.java)
    private val adapter = moshi.adapter<List<TransportWithEvents>>(type)

    fun save(context: Context, list: List<TransportWithEvents>) {
        val json = adapter.toJson(list)
        File(context.filesDir, FILE_NAME).writeText(json)
    }

    fun load(context: Context): MutableList<TransportWithEvents> {
        val file = File(context.filesDir, FILE_NAME)
        if (!file.exists()) return mutableListOf()
        val json = file.readText()
        val loaded = adapter.fromJson(json)
        return loaded?.toMutableList() ?: mutableListOf()
    }
}
