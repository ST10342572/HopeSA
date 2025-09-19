package com.example.hopesa.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hopesa.R
import com.example.hopesa.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        loadUserProfile()

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.bottom_volunteer -> {
                    startActivity(Intent(this, VolunteerActivity::class.java))
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
                R.id.bottom_profile -> true
                R.id.bottom_request -> {
                    startActivity(Intent(this, RequestActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    private fun loadUserProfile() {
        val userId = auth.currentUser?.uid ?: return

        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val name = document.getString("name")
                    val email = document.getString("email")
                    val phone = document.getString("phone")

                    binding.tvProfileName.text = "Name: $name"
                    binding.tvProfileEmail.text = "Email: $email"
                    binding.tvProfilePhone.text = "Phone: $phone"
                } else {
                    Toast.makeText(this, "User info not found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load user info", Toast.LENGTH_SHORT).show()
            }
    }
}
