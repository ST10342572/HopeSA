package com.example.hopesa.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hopesa.R
import com.example.hopesa.databinding.ActivityMainBinding
import com.example.hopesa.data.repository.EventRepository
import com.example.hopesa.ui.viewmodel.EventViewModel
import com.example.hopesa.ui.viewmodel.EventViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: EventViewModel
    private lateinit var adapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set welcome message (optional if you want to programmatically set it)
        binding.tvWelcome.text = "Welcome to HopeSA Home!"

        // Setup RecyclerView
        adapter = EventAdapter()
        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        binding.rvEvents.adapter = adapter

        // ViewModel
        val repository = EventRepository(FirebaseFirestore.getInstance())
        val factory = EventViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[EventViewModel::class.java]

        // Observe events and show/hide "No events available"
        viewModel.events.observe(this) { events ->
            adapter.submitList(events)
            if (events.isNullOrEmpty()) {
                binding.tvNoEvents.visibility = android.view.View.VISIBLE
            } else {
                binding.tvNoEvents.visibility = android.view.View.GONE
            }
        }

        viewModel.loadEvents()

        // Bottom navigation
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> true // Already on home
                R.id.bottom_donate -> {
                    startActivity(Intent(this, DonateActivity::class.java))
                    true
                }
                R.id.bottom_request -> {
                    startActivity(Intent(this, RequestActivity::class.java))
                    true
                }
                R.id.bottom_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}

