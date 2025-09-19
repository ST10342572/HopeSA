package com.example.hopesa.ui.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hopesa.R
import com.example.hopesa.data.model.Volunteer
import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.Toast

class AdminVolunteerAdapter(private var volunteers: List<Volunteer>,private val onDeleteClickListener: (Volunteer) -> Unit) :
    RecyclerView.Adapter<AdminVolunteerAdapter.VolunteerViewHolder>() {

    class VolunteerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvVolunteerName)
        val skill: TextView = itemView.findViewById(R.id.tvVolunteerSkill)
        val btnSendEmail: Button = itemView.findViewById(R.id.btnSendEmail)
        val btnDeleteUser: Button = itemView.findViewById(R.id.btnDeleteUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_volunteer, parent, false)
        return VolunteerViewHolder(view)
    }

    override fun onBindViewHolder(holder: VolunteerViewHolder, position: Int) {
        val volunteer = volunteers[position]
        holder.name.text = volunteer.name
        holder.skill.text = volunteer.skill

        holder.btnSendEmail.setOnClickListener {
            sendAcceptanceEmail(holder.itemView.context, volunteer)
        }

        holder.btnDeleteUser.setOnClickListener {
            onDeleteClickListener(volunteer)
        }
    }

    override fun getItemCount(): Int = volunteers.size

    fun update(newVolunteers: List<Volunteer>) {
        volunteers = newVolunteers
        notifyDataSetChanged()
    }
    private fun sendAcceptanceEmail(context: Context, volunteer: Volunteer) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(volunteer.email)) // Add email field to Volunteer model if not exists
            putExtra(Intent.EXTRA_SUBJECT, "Volunteer Application Accepted")
            putExtra(Intent.EXTRA_TEXT, createEmailBody(volunteer))
        }

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createEmailBody(volunteer: Volunteer): String {
        return """
            Hey ${volunteer.name},
            
            We have seen your application to volunteer. We like your skill ${volunteer.skill} and would like to accept your application.
         Welcome to Hope SA! We would love to have you work with us.
            
            You will be contacted soon with the details of the event.
            
            Have a nice day ${volunteer.name}!
        """.trimIndent()
    }
}