package com.example.hopesa.data.repository

import com.example.hopesa.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    suspend fun getUsers(): List<User> {
        return try {
            val snapshot = usersCollection.get().await()
            snapshot.toObjects(User::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addUser(user: User) {
        usersCollection.document(user._id).set(user).await()
    }

    suspend fun updateUser(user: User) {
        usersCollection.document(user._id).set(user).await()
    }

    suspend fun deleteUser(user: User) {
        usersCollection.document(user._id).delete().await()
    }
}
