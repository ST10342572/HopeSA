package com.example.hopesa.ui.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hopesa.R
import com.example.hopesa.data.model.Event


class AdminEventAdapter(
    private var events: List<Event>,
    private val onUpdateClick: (Event) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<AdminEventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: EditText = itemView.findViewById(R.id.etEventTitle)
        val date: EditText = itemView.findViewById(R.id.etEventDate)
        val organizer: EditText = itemView.findViewById(R.id.etEventOrganizer)
        val location: EditText = itemView.findViewById(R.id.etEventLocation)
        val description: EditText = itemView.findViewById(R.id.etEventDescription)
        val saveButton: Button = itemView.findViewById(R.id.btnSaveChanges)
        val deleteButton: Button = itemView.findViewById(R.id.btnEventDel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        holder.title.setText(event.title)
        holder.date.setText(event.date)
        holder.organizer.setText(event.organizer)
        holder.location.setText(event.location)
        holder.description.setText(event.description)

        holder.saveButton.setOnClickListener {
            val updatedEvent = event.copy(
                title = holder.title.text.toString(),
                date = holder.date.text.toString(),
                organizer = holder.organizer.text.toString(),
                location = holder.location.text.toString(),
                description = holder.description.text.toString()
            )
            onUpdateClick(updatedEvent)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(event._id)
        }
    }

    override fun getItemCount() = events.size

    fun updateEvents(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
}