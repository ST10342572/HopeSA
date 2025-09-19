package com.example.hopesa.ui.activities



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hopesa.data.model.Volunteer
import com.example.hopesa.databinding.ItemVolunteerBinding

class VolunteerAdapter : RecyclerView.Adapter<VolunteerAdapter.VolunteerViewHolder>() {

    private var volunteers = listOf<Volunteer>()

    fun submitList(newList: List<Volunteer>) {
        volunteers = newList
        notifyDataSetChanged()
    }

    inner class VolunteerViewHolder(private val binding: ItemVolunteerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(volunteer: Volunteer) {
            binding.volunteerName.text = volunteer.name
            binding.volunteerPhone.text = volunteer.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VolunteerViewHolder {
        val binding = ItemVolunteerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VolunteerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VolunteerViewHolder, position: Int) {
        holder.bind(volunteers[position])
    }

    override fun getItemCount() = volunteers.size
}
