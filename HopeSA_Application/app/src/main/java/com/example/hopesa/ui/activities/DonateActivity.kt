package com.example.hopesa.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hopesa.databinding.ActivityDonateBinding
import com.example.hopesa.data.model.Donation
import com.example.hopesa.ui.viewmodel.DonationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.hopesa.R
import android.content.Context
import android.content.ClipData
import android.content.ClipboardManager
import android.net.Uri
import androidx.appcompat.app.AlertDialog


class DonateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDonateBinding
    private val viewModel: DonationViewModel by viewModels()

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityDonateBinding.inflate(layoutInflater)
                setContentView(binding.root)

                binding.btnDonate.setOnClickListener {
                    val name = binding.editTextName.text.toString().trim()
                    val amountText = binding.editTextAmount.text.toString().trim()
                    val amount = amountText.toDoubleOrNull()

                    // Get selected payment method
                    val paymentMethod = when (binding.radioPaymentGroup.checkedRadioButtonId) {
                        R.id.radioCreditCard -> "Credit Card"
                        R.id.radioPayPal -> "PayPal"
                        R.id.radioBankTransfer -> "Bank Transfer"
                        else -> ""
                    }

                    if (name.isNotEmpty() && amount != null && paymentMethod.isNotEmpty()) {
                        val donation = Donation(
                            name = name,
                            amount = amount,
                            paymentMethod = paymentMethod
                        )

                        // Process payment based on method
                        when (paymentMethod) {
                            "Credit Card" -> processCreditCardPayment(donation)
                            "PayPal" -> processPayPalPayment(donation)
                            "Bank Transfer" -> processBankTransfer(donation)
                        }
                    } else {
                        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                }

                setupBottomNavigation()
            }

    private fun processCreditCardPayment(donation: Donation) {
        // For now, we'll keep the simulation
        Toast.makeText(this, "Credit Card payments coming soon!", Toast.LENGTH_SHORT).show()
        binding.btnDonate.isEnabled = true
    }

    private fun processPayPalPayment(donation: Donation) {
        // Create PayPal donation URL with parameters
        val paypalUrl = "https://www.paypal.com/donate" +
                "?business=62885810575%40fnb.co.za" +  // Your PayPal email or business ID
                "&amount=${donation.amount}" +
                "&currency_code=ZAR" +
                "&item_name=Donation+to+Hope+SA+Foundation" +
                "&return=https://yourwebsite.com/thankyou" +
                "&cancel_return=https://yourwebsite.com/cancel"

        // Open PayPal in browser
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(paypalUrl))
            startActivity(intent)

            // Mark donation as pending (will be confirmed via webhook or manually)
            donation.status = "Pending"
            viewModel.addDonation(donation)
            showDonationConfirmation(donation)
            Toast.makeText(this, "Redirecting to PayPal...", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error opening PayPal: ${e.message}", Toast.LENGTH_SHORT).show()
            binding.btnDonate.isEnabled = true
        }
    }

    private fun processBankTransfer(donation: Donation) {
        // Create bank transfer details dialog
        val bankDetails = """
        Please make transfer to:
        
        Bank: FNB
        Account Holder: Hope SA Foundation
        Account Number: 6288 581 0575
        Branch Code: 250655
        SWIFT Code: FIRNZAJJ
        Reference: ${donation.name.take(5).uppercase()}${System.currentTimeMillis().toString().takeLast(4)}
        
        Amount: ZAR ${"%.2f".format(donation.amount)}
    """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Bank Transfer Instructions")
            .setMessage(bankDetails)
            .setPositiveButton("Copy Details") { _, _ ->
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Bank Details", bankDetails)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Bank details copied", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Close", null)
            .setNeutralButton("Share") { _, _ ->
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, bankDetails)
                }
                startActivity(Intent.createChooser(shareIntent, "Share Bank Details"))
            }
            .show()

        // Save donation as pending
        donation.status = "Pending"
        viewModel.addDonation(donation)
        showDonationConfirmation(donation)
    }


        private fun showDonationConfirmation(donation: Donation) {
            val message = when (donation.paymentMethod) {
                "PayPal" -> "Your PayPal donation has been initiated. You'll receive a confirmation email from PayPal."
                "Bank Transfer" -> "Please complete your bank transfer using the details provided."
                else -> "Thank you for your donation!"
            }

            AlertDialog.Builder(this)
                .setTitle("Donation Received")
                .setMessage(message)
                .setPositiveButton("OK") { _, _ -> }
                .show()
        }



            private fun setupBottomNavigation() {
                binding.bottomNavigationView.setOnItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.bottom_home -> {
                            startActivity(Intent(this, MainActivity::class.java))
                            true
                        }
                        R.id.bottom_donate -> true
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