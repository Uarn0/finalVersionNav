package com.example.cursova.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.cursova.R
import com.example.cursova.databinding.ItemRepairBinding
import com.example.cursova.databinding.ItemServiceBinding
import com.example.cursova.databinding.ItemTripBinding
import com.example.cursova.model.event.IEvent
import com.example.cursova.model.event.RepairEvent
import com.example.cursova.model.event.ServiceEvent
import com.example.cursova.model.event.TripEvent
import com.example.cursova.model.transport.ITransport

class AdapterForEvent : RecyclerView.Adapter<AdapterForEvent.BossOfTheHolder>() {

    private var eventList = mutableListOf<IEvent>()

    override fun getItemCount(): Int = eventList.size

    override fun getItemViewType(position: Int): Int {
        return eventList[position].getEventType()
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: MutableList<IEvent>){
        eventList = newList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BossOfTheHolder {
        return when(viewType) {
            IEvent.REPAIR_EVENT_TYPE -> RepairViewHolder(
                ItemRepairBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            IEvent.TRIP_EVENT_TYPE -> TripViewHolder(
                ItemTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            IEvent.SERVICE_EVENT_TYPE -> ServiceViewHolder(
                ItemServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalStateException("Unexpected viewType $viewType")
        }
    }


    override fun onBindViewHolder(holder: BossOfTheHolder, position: Int) {
        val item = eventList[position]
        holder.bind(item)

        holder.itemView.findViewById<Button>(R.id.removeButton).setOnClickListener {
            removeItemAt(position)
        }

    }

    internal var onItemRemoved: ((IEvent) -> Unit)? = null

    private fun removeItemAt(position: Int) {
        val removedItem = eventList[position]
        eventList.removeAt(position)
        notifyItemRemoved(position)
        onItemRemoved?.invoke(removedItem)
    }

    abstract class BossOfTheHolder(itemViewBinding: ViewBinding): RecyclerView.ViewHolder(itemViewBinding.root){
        abstract fun bind(item: IEvent)
    }

    class RepairViewHolder(private val itemViewBinding: ItemRepairBinding) : BossOfTheHolder(itemViewBinding){
        override fun bind(item: IEvent) {
            val repair = item as RepairEvent
            itemViewBinding.eventType.text = repair.getName()
            itemViewBinding.eventTime.text = repair.getEventTime()
        }
    }

    class TripViewHolder(private val itemViewBinding: ItemTripBinding) : BossOfTheHolder(itemViewBinding){
        override fun bind(item: IEvent) {
            val trip = item as TripEvent
            itemViewBinding.eventType.text = trip.getName()
            itemViewBinding.eventTime.text = trip.getEventTime()
        }
    }

    class ServiceViewHolder(private val itemViewBinding: ItemServiceBinding) : BossOfTheHolder(itemViewBinding){
        override fun bind(item: IEvent) {
            val service = item as ServiceEvent
            itemViewBinding.eventType.text = service.getName()
            itemViewBinding.eventTime.text = service.getEventTime()
        }
    }
}