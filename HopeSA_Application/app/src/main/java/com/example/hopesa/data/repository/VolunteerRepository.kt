package com.example.hopesa.data.repository


import com.example.hopesa.data.model.Volunteer
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class VolunteerRepository {

    private val volunteerCollection = FirebaseFirestore.getInstance().collection("volunteers")

    suspend fun getVolunteers(): List<Volunteer> {
        return try {
            val snapshot = volunteerCollection.get().await()
            snapshot.toObjects(Volunteer::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addVolunteer(volunteer: Volunteer) {
        try {
            volunteerCollection.document(volunteer._id).set(volunteer).await()
        } catch (e: Exception) {
            // Handle or log error
        }
    }

    suspend fun updateVolunteer(volunteer: Volunteer) {
        try {
            volunteerCollection.document(volunteer._id).set(volunteer).await()
        } catch (e: Exception) {
            // Handle or log error
        }
    }

    suspend fun deleteVolunteer(volunteer: Volunteer) {
        try {
            volunteerCollection.document(volunteer._id).delete().await()
        } catch (e: Exception) {
            // Handle or log error
        }
    }

    suspend fun deleteAll() {
        try {
            val snapshot = volunteerCollection.get().await()
            for (doc in snapshot.documents) {
                doc.reference.delete().await()
            }
        } catch (e: Exception) {
            // Handle or log error
        }
    }

    suspend fun insertAll(volunteers: List<Volunteer>) {
        try {
            for (volunteer in volunteers) {
                volunteerCollection.document(volunteer._id).set(volunteer).await()
            }
        } catch (e: Exception) {
            // Handle or log error
        }
    }
}
