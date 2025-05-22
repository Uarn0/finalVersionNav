package com.example.cursova.model.transport
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true, generator = "sealed:type")
sealed interface ITransport {
    fun getTransportType():Int
    fun getTransportTypeName(): String
    fun getName(): String
    fun getImageResId(): Int
    companion object{
        const val CAR_TYPE = 0
        const val MINIBUS_TYPE = 1
        const val MOTORCYCLE_TYPE = 2
    }
}