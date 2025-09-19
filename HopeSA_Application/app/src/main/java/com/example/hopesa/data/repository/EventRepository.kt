package com.example.hopesa.data.repository

import com.example.hopesa.data.model.Event
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class EventRepository(private val db: FirebaseFirestore) {

    private val eventCollection = db.collection("events")

    suspend fun getEvents(): List<Event> {
        return try {
            val snapshot = eventCollection.get().await()
            snapshot.toObjects(Event::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addEvent(event: Event) {
        try {
            eventCollection.document(event._id).set(event).await()
        } catch (e: Exception) { }
    }

    suspend fun updateEvent(event: Event) {
        try {
            eventCollection.document(event._id).set(event).await()
        } catch (e: Exception) {
            throw e // Or handle the error as needed
        }
    }

    suspend fun deleteEvent(event: Event) {
        try {
            eventCollection.document(event._id).delete().await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun deleteAll() {
        try {
            val snapshot = eventCollection.get().await()
            for (doc in snapshot.documents) {
                doc.reference.delete().await()
            }
        } catch (e: Exception) { }
    }

    suspend fun insertAll(events: List<Event>) {
        try {
            for (event in events) {
                eventCollection.document(event._id).set(event).await()
            }
        } catch (e: Exception) { }
    }
}
