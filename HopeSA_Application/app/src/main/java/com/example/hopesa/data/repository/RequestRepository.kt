package com.example.hopesa.data.repository

import com.example.hopesa.data.model.Request
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RequestRepository {

    private val db = FirebaseFirestore.getInstance()
    private val requestsRef = db.collection("requests")

    suspend fun addRequest(request: Request) {
        requestsRef.document(request.requestId).set(request).await()
    }

    suspend fun getAllRequests(): List<Request> {
        val snapshot = requestsRef.get().await()
        return snapshot.toObjects(Request::class.java)
    }

    suspend fun updateRequest(request: Request) {
        requestsRef.document(request.requestId).set(request).await()
    }

    suspend fun deleteRequest(requestId: String) {
        requestsRef.document(requestId).delete().await()
    }
}


