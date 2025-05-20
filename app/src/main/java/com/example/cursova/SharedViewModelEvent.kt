package com.example.cursova

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cursova.model.event.IEvent

class SharedViewModelEvent : ViewModel() {
    private val _eventList = MutableLiveData<MutableList<IEvent>>()
    val eventList: LiveData<MutableList<IEvent>> = _eventList

    fun addEvent(transport: IEvent) {
        val current = _eventList.value?.toMutableList() ?: mutableListOf()
        current.add(transport)
        _eventList.value = current
        _eventList.value = _eventList.value
    }
    fun removeEvent(transport: IEvent) {
        _eventList.value = _eventList.value?.toMutableList()?.apply {
            remove(transport)
        }
    }
}