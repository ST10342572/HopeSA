package com.example.hopesa.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopesa.R
import com.example.hopesa.data.model.Request
import com.example.hopesa.data.repository.RequestRepository
import com.example.hopesa.ui.activities.AdminRequestAdapter
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class AdminRequestActivity : AppCompatActivity() {
        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: AdminRequestAdapter
        private val db = FirebaseFirestore.getInstance()
        private val requests = mutableListOf<Request>()
        private val requestRepository = RequestRepository()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_admin_manage_requests)

            recyclerView = findViewById(R.id.recyclerAdminRequests)
            adapter = AdminRequestAdapter(requests) { requestId ->
                showDeleteConfirmationDialog(requestId)  // Handle delete click
            }
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter

            fetchRequests()
        }

        private fun showDeleteConfirmationDialog(requestId: String) {
            AlertDialog.Builder(this)
                .setTitle("Delete Request")
                .setMessage("Are you sure you want to delete this request?")
                .setPositiveButton("Delete") { _, _ ->
                    deleteRequest(requestId)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        private fun deleteRequest(requestId: String) {
            lifecycleScope.launch {
                try {
                    requestRepository.deleteRequest(requestId)
                    requests.removeAll { it.requestId == requestId }
                    adapter.notifyDataSetChanged()
                    Toast.makeText(
                        this@AdminRequestActivity,
                        "Request deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(
                        this@AdminRequestActivity,
                        "Error deleting request: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        private fun fetchRequests() {
            db.collection("requests").get().addOnSuccessListener { result ->
                requests.clear()
                for (doc in result) {
                    val request = doc.toObject(Request::class.java)
                    requests.add(request)
                }
                adapter.notifyDataSetChanged()
            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Error loading requests: ${it.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }