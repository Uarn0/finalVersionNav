package com.example.cursova.model.transport

import com.example.cursova.R

data class Car(
    val nameOfCar: String
) : ITransport {
    override fun getTransportType() = ITransport.CAR_TYPE
    override fun getTransportTypeName() = "Car"
    override fun getName() = nameOfCar
    override fun getImageResId(): Int {
        return R.drawable.ic_car
    }
}
