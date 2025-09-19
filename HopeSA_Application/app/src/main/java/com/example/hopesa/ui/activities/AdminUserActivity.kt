package com.example.hopesa.ui.activities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hopesa.R
import com.example.hopesa.data.model.User
import com.example.hopesa.ui.activities.AdminUserAdapter
import com.google.firebase.firestore.FirebaseFirestore

class AdminUserActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminUserAdapter
    private val db = FirebaseFirestore.getInstance()
    private val users = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_manage_users)

        recyclerView = findViewById(R.id.recyclerAdminUsers)
        adapter = AdminUserAdapter(users)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        fetchUsers()
    }

    private fun fetchUsers() {
        db.collection("users").get().addOnSuccessListener { result ->
            users.clear()
            for (doc in result) {
                users.add(doc.toObject(User::class.java))
            }
            adapter.notifyDataSetChanged()
        }
    }
}
