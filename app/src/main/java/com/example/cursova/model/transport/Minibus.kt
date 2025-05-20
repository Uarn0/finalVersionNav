package com.example.cursova.model.transport

import com.example.cursova.R

data class Minibus(
    val nameOfMinibus: String
) : ITransport {
    override fun getTransportType() = ITransport.MINIBUS_TYPE
    override fun getTransportTypeName() = "Minibus"
    override fun getName() = nameOfMinibus
    override fun getImageResId(): Int {
        return R.drawable.ic_minibus
    }
}
