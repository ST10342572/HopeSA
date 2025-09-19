package com.example.hopesa.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hopesa.R
import com.example.hopesa.databinding.ActivityVolunteerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class VolunteerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVolunteerBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Now use binding instead of findViewById
        binding.btnApply.setOnClickListener {
            val name = binding.editTextName.text.toString().trim()
            val skill = binding.editTextSkill.text.toString().trim()
            val location = binding.editTextLocation.text.toString().trim()
            val availability = binding.editTextAvailability.text.toString().trim()

            if (name.isEmpty() || skill.isEmpty() || location.isEmpty() || availability.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uid = auth.currentUser?.uid ?: return@setOnClickListener
            val data = hashMapOf(
                "userId" to uid,
                "name" to name,
                "skill" to skill,
                "location" to location,
                "availability" to availability
            )

            firestore.collection("volunteer_applications")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Application submitted!,Will be contacted soon", Toast.LENGTH_SHORT).show()
                    binding.editTextName.text.clear()
                    binding.editTextSkill.text.clear()
                    binding.editTextLocation.text.clear()
                    binding.editTextAvailability.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to submit: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        // Bottom navigation item selection
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.bottom_donate -> {
                    startActivity(Intent(this, DonateActivity::class.java))
                    true
                }
                R.id.bottom_request -> {
                    startActivity(Intent(this, RequestActivity::class.java))
                    true
                }
                R.id.bottom_volunteer -> true
                R.id.bottom_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
