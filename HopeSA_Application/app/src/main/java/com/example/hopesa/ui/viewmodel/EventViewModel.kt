package com.example.hopesa.ui.viewmodel

import androidx.lifecycle.*
import com.example.hopesa.data.model.Event
import com.example.hopesa.data.repository.EventRepository
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    fun loadEvents() {
        viewModelScope.launch {
            val eventsList = repository.getEvents()
            _events.value = eventsList
        }
    }


    fun addEvent(event: Event) = viewModelScope.launch {
        repository.addEvent(event)
    }

    fun updateEvent(event: Event) = viewModelScope.launch {
        repository.updateEvent(event)
    }

    fun deleteEvent(event: Event) = viewModelScope.launch {
        repository.deleteEvent(event)
    }
}
