package com.example.hopesa.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopesa.R
import com.example.hopesa.ui.activities.AdminDonationAdapter
import com.example.hopesa.data.model.Donation
import com.google.firebase.firestore.FirebaseFirestore

class AdminDonationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminDonationAdapter
    private val db = FirebaseFirestore.getInstance()
    private val donations = mutableListOf<Donation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_donation)

        recyclerView = findViewById(R.id.recyclerAdminDonations)
        adapter = AdminDonationAdapter(donations)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fetchDonations()
    }

    private fun fetchDonations() {
        db.collection("donations")
            .get()
            .addOnSuccessListener { result ->
                donations.clear()
                for (doc in result) {
                    val donation = doc.toObject(Donation::class.java)
                    donations.add(donation)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {

            }
    }
}
