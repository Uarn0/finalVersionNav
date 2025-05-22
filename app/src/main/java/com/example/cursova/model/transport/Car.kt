package com.example.cursova.model.transport
import com.squareup.moshi.JsonClass

import com.example.cursova.R
@JsonClass(generateAdapter = true)
data class Car(
    val nameOfCar: String,
) : ITransport {
    override fun getTransportType() = ITransport.CAR_TYPE
    override fun getTransportTypeName() = "Car"
    override fun getName() = nameOfCar
    override fun getImageResId() = R.drawable.ic_car
}
