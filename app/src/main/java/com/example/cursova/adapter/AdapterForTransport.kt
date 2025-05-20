package com.example.cursova.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.cursova.R
import com.example.cursova.databinding.ItemCarBinding
import com.example.cursova.databinding.ItemMinibusBinding
import com.example.cursova.databinding.ItemMotorcycleBinding
import com.example.cursova.model.transport.Car
import com.example.cursova.model.transport.ITransport
import com.example.cursova.model.transport.Minibus
import com.example.cursova.model.transport.Motorcycle

class AdapterForTransport : RecyclerView.Adapter<AdapterForTransport.BossOfTheHolder>() {
    interface OnTransportClickListener {
        fun onTransportClick(transport: ITransport)
        fun onEditClick(transport: ITransport)
    }
    private var transportList = mutableListOf<ITransport>()

    override fun getItemViewType(position: Int): Int {
        return transportList[position].getTransportType()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: MutableList<ITransport>){
        transportList = newList
        notifyDataSetChanged()
    }

    private fun removeItemAt(position: Int) {
        val removedItem = transportList[position]
        transportList.removeAt(position)
        notifyItemRemoved(position)
        onItemRemoved?.invoke(removedItem)
    }
    private var transportClickListener: OnTransportClickListener? = null

    fun setOnTransportClickListener(listener: OnTransportClickListener) {
        this.transportClickListener = listener
    }
    override fun onBindViewHolder(holder: BossOfTheHolder, position: Int) {
        val item = transportList[position]
        holder.bind(item)

        holder.itemView.setOnClickListener {
            transportClickListener?.onTransportClick(item)
        }

        holder.itemView.findViewById<Button>(R.id.removeButton).setOnClickListener {
            removeItemAt(position)
        }

        holder.itemView.findViewById<Button>(R.id.editButton).setOnClickListener {
            transportClickListener?.onEditClick(item)
        }
    }



    internal var onItemRemoved: ((ITransport) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BossOfTheHolder {
        return when (viewType){
            ITransport.CAR_TYPE -> CarViewHolder(
                ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            ITransport.MINIBUS_TYPE -> MinibusViewHolder(
                ItemMinibusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            ITransport.MOTORCYCLE_TYPE -> MotorcycleViewHolder(
                ItemMotorcycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalStateException("Unexpected viewType $viewType")
        }
    }

    abstract class BossOfTheHolder(itemViewBinding: ViewBinding) : RecyclerView.ViewHolder(itemViewBinding.root){
        abstract fun bind(item: ITransport)
    }

    override fun getItemCount(): Int = transportList.size

    class CarViewHolder(private val itemViewBinding: ItemCarBinding) : BossOfTheHolder(itemViewBinding){
        override fun bind (item: ITransport){
            val car = item as Car
            itemViewBinding.transportName.text = car.nameOfCar
            itemViewBinding.transportType.text = car.getTransportTypeName()
            itemViewBinding.icon.setImageResource(car.getImageResId())
        }
    }

    class MinibusViewHolder(private val itemViewBinding: ItemMinibusBinding) : BossOfTheHolder(itemViewBinding){
        override fun bind(item: ITransport) {
            val minibus = item as Minibus
            itemViewBinding.transportName.text = minibus.nameOfMinibus
            itemViewBinding.transportType.text = minibus.getTransportTypeName()
            itemViewBinding.icon.setImageResource(minibus.getImageResId())
        }
    }

    class MotorcycleViewHolder(private val itemViewBinding: ItemMotorcycleBinding) : BossOfTheHolder(itemViewBinding){
        override fun bind(item: ITransport){
            val motorcycle = item as Motorcycle
            itemViewBinding.transportName.text = motorcycle.nameOfMoto
            itemViewBinding.transportType.text = motorcycle.getTransportTypeName()
            itemViewBinding.icon.setImageResource(motorcycle.getImageResId())
        }
    }
}