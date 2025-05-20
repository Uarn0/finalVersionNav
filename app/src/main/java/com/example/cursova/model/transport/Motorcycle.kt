package com.example.cursova.model.transport

import com.example.cursova.R

data class Motorcycle(
    val nameOfMoto: String
) : ITransport {
    override fun getTransportType() = ITransport.MOTORCYCLE_TYPE
    override fun getTransportTypeName() = "Motorcycle"
    override fun getName() = nameOfMoto
    override fun getImageResId(): Int {
        return R.drawable.ic_moto
    }
}
