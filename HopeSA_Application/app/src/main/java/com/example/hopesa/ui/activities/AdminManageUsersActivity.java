package com.example.hopesa.ui.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hopesa.R;
import com.google.firebase.firestore.*;
import java.util.*;

public class AdminManageUsersActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_users);
        listView = findViewById(R.id.listViewUsers);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userList);
        listView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        loadUsers();
    }

    private void loadUsers() {
        db.collection("USERS").get().addOnSuccessListener(queryDocumentSnapshots -> {
            userList.clear();
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                String name = doc.getString("name");
                String role = doc.getString("role");
                userList.add(name + " - " + role);
            }
            adapter.notifyDataSetChanged();
        });
    }
}