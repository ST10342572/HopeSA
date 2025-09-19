package com.example.hopesa.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hopesa.databinding.ActivityAboutUsBinding
import com.example.hopesa.R

class AboutUsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle bottom navigation
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
                R.id.bottom_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Optional: Set current item to disable re-click
        binding.bottomNavigationView.selectedItemId = R.id.bottom_home
    }
}
