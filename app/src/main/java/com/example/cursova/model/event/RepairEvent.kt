package com.example.cursova.model.event
import com.squareup.moshi.JsonClass
import android.annotation.SuppressLint
@JsonClass(generateAdapter = true)

data class RepairEvent(
    val repair : String,
    val createdAt: Long = System.currentTimeMillis()
) : IEvent {
    @SuppressLint("SimpleDateFormat")
    override fun getEventTime(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(java.util.Date(createdAt))
    }
    override fun getName() = "Repair"

    override fun getEventType(): Int {
        return IEvent.REPAIR_EVENT_TYPE
    }
}
