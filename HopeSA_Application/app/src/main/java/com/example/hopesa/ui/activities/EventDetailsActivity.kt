package com.example.hopesa.ui.activities


import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hopesa.R

class EventDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        val title = intent.getStringExtra("title")
        val date = intent.getStringExtra("date")
        val location = intent.getStringExtra("location")
        val description = intent.getStringExtra("description")
        val organizer = intent.getStringExtra("organizer")

        findViewById<TextView>(R.id.tvEventTitle).text = "Title: $title"
        findViewById<TextView>(R.id.tvEventDate).text = "Date: $date"
        findViewById<TextView>(R.id.tvEventLocation).text = "Location: $location"
        findViewById<TextView>(R.id.tvEventDescription).text = "Description: $description"
        findViewById<TextView>(R.id.tvEventOrganizer).text = "Organizer: $organizer"
    }
}
