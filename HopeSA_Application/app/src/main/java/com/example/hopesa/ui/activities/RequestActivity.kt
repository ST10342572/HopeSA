package com.example.hopesa.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.hopesa.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class RequestActivity : AppCompatActivity() {

    private lateinit var editUserId: EditText
    private lateinit var editTypeOfHelp: EditText
    private lateinit var spinnerStatus: Spinner
    private lateinit var btnSubmit: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        // Initialize views
        editUserId = findViewById(R.id.editUserId)
        editTypeOfHelp = findViewById(R.id.editTypeOfHelp)
        btnSubmit = findViewById(R.id.btnSubmitRequest)

        db = FirebaseFirestore.getInstance()

        // Spinner options

        // Submit request to Firestore
        btnSubmit.setOnClickListener {
            val userId = editUserId.text.toString().trim()
            val helpType = editTypeOfHelp.text.toString().trim()


            if (userId.isNotEmpty() && helpType.isNotEmpty()) {
                val request = hashMapOf(
                    "userId" to userId,
                    "typeOfHelp" to helpType,

                )

                db.collection("requests")
                    .add(request)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Request submitted", Toast.LENGTH_SHORT).show()
                        editUserId.text.clear()
                        editTypeOfHelp.text.clear()
                        spinnerStatus.setSelection(0)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Error submitting request", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
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

                    true
                }
                R.id.bottom_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                R.id.bottom_request -> {
                    startActivity(Intent(this, RequestActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
