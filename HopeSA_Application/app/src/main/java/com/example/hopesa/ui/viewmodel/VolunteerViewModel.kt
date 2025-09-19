package com.example.hopesa.ui.viewmodel

import androidx.lifecycle.*
import com.example.hopesa.data.model.Volunteer
import com.example.hopesa.data.repository.VolunteerRepository
import kotlinx.coroutines.launch

class VolunteerViewModel(private val repository: VolunteerRepository) : ViewModel() {

    private val _volunteers = MutableLiveData<List<Volunteer>>()
    val volunteers: LiveData<List<Volunteer>> = _volunteers

    fun loadVolunteers() {
        viewModelScope.launch {
            val volunteerList = repository.getVolunteers()
            _volunteers.value = volunteerList
        }
    }

    fun addVolunteer(volunteer: Volunteer) {
        viewModelScope.launch {
            repository.addVolunteer(volunteer)
        }
    }

    fun updateVolunteer(volunteer: Volunteer) {
        viewModelScope.launch {
            repository.updateVolunteer(volunteer)
        }
    }

    fun deleteVolunteer(volunteer: Volunteer) {
        viewModelScope.launch {
            repository.deleteVolunteer(volunteer)
        }
    }
}
