package com.example.hopesa.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hopesa.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            auth = FirebaseAuth.getInstance()

            // Regular login button
            binding.btnLogin.setOnClickListener {
                val email = binding.editTextEmail.text.toString().trim()
                val password = binding.editTextPassword.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            checkUserRoleAndRedirect(userId, isAdminLogin = false)
                        } else {
                            Toast.makeText(this, "User ID not found", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Login failed: ${it.message}", Toast.LENGTH_LONG).show()
                    }
            }

            // Admin login button
            binding.btnLoginAdmin.setOnClickListener {
                val email = binding.editTextEmail.text.toString().trim()
                val password = binding.editTextPassword.text.toString().trim()

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        val userId = auth.currentUser?.uid
                        if (userId != null) {
                            checkUserRoleAndRedirect(userId, isAdminLogin = true)
                        } else {
                            Toast.makeText(this, "User ID not found", Toast.LENGTH_LONG).show()
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Admin login failed: ${it.message}", Toast.LENGTH_LONG).show()
                    }
            }

            binding.textViewSignupLink.setOnClickListener {
                startActivity(Intent(this, SignupActivity::class.java))
            }
        }

        private fun checkUserRoleAndRedirect(userId: String, isAdminLogin: Boolean) {
            db.collection("USERS").document(userId).get()
                .addOnSuccessListener { document ->
                    val role = document.getString("role") ?: "user"

                    when {
                        role == "admin" -> {
                            Toast.makeText(this, "Welcome, Admin!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, AdminDashboardActivity::class.java))
                            finish()
                        }
                        isAdminLogin -> {
                            // If this was an admin login attempt but user isn't admin
                            auth.signOut()
                            Toast.makeText(
                                this,
                                "Access denied. Admin privileges required.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else -> {
                            // Regular user login
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to fetch user data: ${it.message}", Toast.LENGTH_LONG).show()
                }
        }
    }