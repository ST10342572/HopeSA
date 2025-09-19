package com.example.hopesa.ui.viewmodel

import androidx.lifecycle.*
import com.example.hopesa.data.model.Donation
import com.example.hopesa.data.repository.DonationRepository
import kotlinx.coroutines.launch

class DonationViewModel(private val repository: DonationRepository) : ViewModel() {

    private val _donations = MutableLiveData<List<Donation>>()
    val donations: LiveData<List<Donation>> = _donations

    fun loadDonations() {
        viewModelScope.launch {
            val donationsList = repository.getDonations()
            _donations.value = donationsList
        }
    }


    fun addDonation(donation: Donation) {
        viewModelScope.launch {
            repository.addDonation(donation)
        }
    }

    fun updateDonation(donation: Donation) {
        viewModelScope.launch {
            repository.updateDonation(donation)
        }
    }


    fun deleteDonation(donation: Donation) {
        viewModelScope.launch {
            repository.deleteDonation(donation)
        }
    }

    fun updateDonationStatus(donationId: String, status: String, transactionId: String = "") {
        viewModelScope.launch {
            repository.updateDonationStatus(donationId, status, transactionId)
        }
    }
}

