package com.example.hopesa.ui.activities


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hopesa.R
import com.example.hopesa.data.model.Event

class EventAdapter : ListAdapter<Event, EventAdapter.EventViewHolder>(DiffCallback()) {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvLocation: TextView = itemView.findViewById(R.id.tvLocation)

        fun bind(event: Event) {
            tvTitle.text = event.title
            tvDate.text = event.date
            tvLocation.text = event.location

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, EventDetailsActivity::class.java).apply {
                    putExtra("title", event.title)
                    putExtra("date", event.date)
                    putExtra("location", event.location)
                    putExtra("description", event.description)
                    putExtra("organizer", event.organizer)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
            oldItem._id == newItem._id

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
            oldItem == newItem
    }
}
