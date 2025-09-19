package com.example.hopesa.data.repository


import com.example.hopesa.data.model.Donation
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class DonationRepository {

    private val donationCollection = FirebaseFirestore.getInstance().collection("donations")

    suspend fun getDonations(): List<Donation> {
        return try {
            val snapshot = donationCollection.get().await()
            snapshot.toObjects(Donation::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addDonation(donation: Donation) {
        try {
            donationCollection.document(donation._id).set(donation).await()
        } catch (e: Exception) {

        }
    }

    suspend fun updateDonation(donation: Donation) {
        try {
            donationCollection.document(donation._id).set(donation).await()
        } catch (e: Exception) {
            // Handle or log the error
        }
    }

    suspend fun deleteDonation(donation: Donation) {
        try {
            donationCollection.document(donation._id).delete().await()
        } catch (e: Exception) {
            // Handle or log the error
        }
    }

    suspend fun updateDonationStatus(donationId: String, status: String, transactionId: String = "") {
        try {
            donationCollection.document(donationId).update(
                mapOf(
                    "status" to status,
                    "transactionId" to transactionId,
                    "timestamp" to System.currentTimeMillis()
                )
            ).await()
        } catch (e: Exception) {
            // Handle error
        }
    }
}
