package com.example.cursova.model.transport

import com.example.cursova.R
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Motorcycle(
    val nameOfMoto: String,
) : ITransport {
    override fun getTransportType() = ITransport.MOTORCYCLE_TYPE
    override fun getTransportTypeName() = "Motorcycle"
    override fun getName() = nameOfMoto
    override fun getImageResId() = R.drawable.ic_moto
}
