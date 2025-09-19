package com.example.hopesa.ui.viewmodel

import androidx.lifecycle.*
import com.example.hopesa.data.model.Request
import com.example.hopesa.data.repository.RequestRepository
import kotlinx.coroutines.launch

class RequestViewModel(private val repository: RequestRepository) : ViewModel() {

    val requests = MutableLiveData<List<Request>>()

    fun loadRequests() {
        viewModelScope.launch {
            requests.value = repository.getAllRequests()
        }
    }

    fun addRequest(request: Request) {
        viewModelScope.launch {
            repository.addRequest(request)
            loadRequests()
        }
    }

    fun updateRequest(request: Request) {
        viewModelScope.launch {
            repository.updateRequest(request)
            loadRequests()
        }
    }

    fun deleteRequest(requestId: String) {
        viewModelScope.launch {
            repository.deleteRequest(requestId)
            loadRequests()
        }
    }
}


