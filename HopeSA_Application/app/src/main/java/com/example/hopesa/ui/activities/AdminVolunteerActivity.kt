package com.example.hopesa.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopesa.R
import com.example.hopesa.data.model.Volunteer
import com.example.hopesa.data.repository.VolunteerRepository
import com.example.hopesa.ui.activities.AdminVolunteerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.appcompat.app.AlertDialog
import android.widget.Toast

class AdminVolunteerActivity : AppCompatActivity() {
        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: AdminVolunteerAdapter
        private val db = FirebaseFirestore.getInstance()
        private val volunteers = mutableListOf<Volunteer>()
        private val volunteerRepository = VolunteerRepository()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_admin_manage_volunteers)

            recyclerView = findViewById(R.id.recyclerAdminVolunteers)
            adapter = AdminVolunteerAdapter(volunteers) { volunteer ->
                // This is the delete click handler
                showDeleteConfirmationDialog(volunteer)
            }
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter

            fetchVolunteers()
        }

        private fun showDeleteConfirmationDialog(volunteer: Volunteer) {
            AlertDialog.Builder(this)
                .setTitle("Delete Volunteer")
                .setMessage("Are you sure you want to delete ${volunteer.name}?")
                .setPositiveButton("Delete") { _, _ ->
                    deleteVolunteer(volunteer)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        private fun deleteVolunteer(volunteer: Volunteer) {
            lifecycleScope.launch {
                try {
                    volunteerRepository.deleteVolunteer(volunteer)
                    volunteers.remove(volunteer)
                    adapter.notifyDataSetChanged()
                    Toast.makeText(
                        this@AdminVolunteerActivity,
                        "${volunteer.name} deleted successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Toast.makeText(
                        this@AdminVolunteerActivity,
                        "Error deleting volunteer: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        private fun fetchVolunteers() {
            db.collection("volunteers").get().addOnSuccessListener { result ->
                volunteers.clear()
                for (doc in result) {
                    volunteers.add(doc.toObject(Volunteer::class.java))
                }
                adapter.notifyDataSetChanged()
            }
        }
    }


private fun fetchVolunteers() {
        db.collection("volunteers").get().addOnSuccessListener { result ->
            volunteers.clear()
            for (doc in result) {
                volunteers.add(doc.toObject(Volunteer::class.java))
            }
            adapter.notifyDataSetChanged()
        }
    }

