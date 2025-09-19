package com.example.hopesa.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopesa.R
import com.example.hopesa.data.model.Event
import  com.example.hopesa.ui.activities.AdminEventAdapter
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.widget.Toast
import com.example.hopesa.data.repository.EventRepository

class AdminEventActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminEventAdapter
    private val db = FirebaseFirestore.getInstance()
    private val events = mutableListOf<Event>()
    private lateinit var eventRepository: EventRepository  // Removed duplicate declaration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_manage_events)

        // Initialize repository
        eventRepository = EventRepository(db)

        recyclerView = findViewById(R.id.recyclerAdminEvents)
        adapter = AdminEventAdapter(
            events,
            onUpdateClick = { updatedEvent -> updateEvent(updatedEvent) },
            onDeleteClick = { eventId -> deleteEvent(eventId) }  // Added delete handler
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fetchEvents()
    }

    private fun updateEvent(updatedEvent: Event) {
        lifecycleScope.launch {
            try {
                eventRepository.updateEvent(updatedEvent)
                Toast.makeText(
                    this@AdminEventActivity,
                    "Event updated successfully",
                    Toast.LENGTH_SHORT
                ).show()
                fetchEvents()
            } catch (e: Exception) {
                Toast.makeText(
                    this@AdminEventActivity,
                    "Error updating event: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun deleteEvent(eventId: String) {
        lifecycleScope.launch {
            try {
                eventRepository.deleteEvent(Event(_id = eventId))
                events.removeAll { it._id == eventId }
                adapter.notifyDataSetChanged()
                Toast.makeText(
                    this@AdminEventActivity,
                    "Event deleted successfully",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    this@AdminEventActivity,
                    "Error deleting event: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun fetchEvents() {
        db.collection("events").get().addOnSuccessListener { result ->
            events.clear()
            for (doc in result) {
                events.add(doc.toObject(Event::class.java))
            }
            adapter.notifyDataSetChanged()
        }.addOnFailureListener { e ->
            Toast.makeText(
                this,
                "Error loading events: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}