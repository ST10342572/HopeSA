package com.example.hopesa.ui.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hopesa.R
import com.example.hopesa.data.model.Donation

class AdminDonationAdapter(private val donations: List<Donation>) :
    RecyclerView.Adapter<AdminDonationAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val donorName: TextView = view.findViewById(R.id.tvDonorName)
        val donationAmount: TextView = view.findViewById(R.id.tvDonationAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_donation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val donation = donations[position]
        holder.donorName.text = donation.name
        holder.donationAmount.text = "Amount: R${donation.amount}"
    }

    override fun getItemCount(): Int = donations.size
}
