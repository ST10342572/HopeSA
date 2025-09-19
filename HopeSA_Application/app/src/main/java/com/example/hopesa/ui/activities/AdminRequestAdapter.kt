package com.example.hopesa.ui.activities


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hopesa.R
import com.example.hopesa.data.model.Request

class AdminRequestAdapter(
    private var requests: List<Request>,
    private val onDeleteClick: (String) -> Unit  // Add delete callback
) : RecyclerView.Adapter<AdminRequestAdapter.RequestViewHolder>() {

    class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userId: TextView = itemView.findViewById(R.id.tvRequestUserId)
        val helpType: TextView = itemView.findViewById(R.id.tvRequestType)
        val status: TextView = itemView.findViewById(R.id.tvRequestStatus)
        val deleteButton: Button = itemView.findViewById(R.id.btnReqDel)  // Add button reference
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_request, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requests[position]
        holder.userId.text = request.userId
        holder.helpType.text = request.typeOfHelp
        holder.status.text = request.status

        // Set up delete button click listener
        holder.deleteButton.setOnClickListener {
            onDeleteClick(request.requestId)  // Pass request ID to delete
        }
    }

    override fun getItemCount(): Int = requests.size

    fun update(newRequests: List<Request>) {
        requests = newRequests
        notifyDataSetChanged()
    }
}