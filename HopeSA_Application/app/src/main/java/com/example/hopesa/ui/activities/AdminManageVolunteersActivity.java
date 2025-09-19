package com.example.hopesa.ui.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hopesa.R;
import android.widget.Toast;
import com.google.firebase.firestore.*;
import java.util.*;
import android.app.ProgressDialog;
import android.util.Log;


public class AdminManageVolunteersActivity extends AppCompatActivity {
        private FirebaseFirestore db;
        private ListView listView;
        private ArrayAdapter<String> adapter;
        private List<String> volunteerList = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_manage_volunteers);
            listView = findViewById(R.id.listViewVolunteers);
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, volunteerList);
            listView.setAdapter(adapter);
            db = FirebaseFirestore.getInstance();

            // Set click listener for list items
            listView.setOnItemClickListener((parent, view, position, id) -> {
                String selectedName = volunteerList.get(position);
                // You can add action here, like opening a detail view
                Toast.makeText(this, "Selected: " + selectedName, Toast.LENGTH_SHORT).show();
            });

            loadVolunteers();
        }

        private void loadVolunteers() {
            // Show loading progress
            ProgressDialog progress = ProgressDialog.show(this, "Loading", "Please wait...");

            db.collection("VOLUNTEERS").get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        volunteerList.clear();
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            String name = doc.getString("name");
                            volunteerList.add(name);
                        }
                        adapter.notifyDataSetChanged();
                        progress.dismiss(); // Dismiss when successful
                    })
                    .addOnFailureListener(e -> {
                        progress.dismiss(); // Dismiss even if failed
                        Toast.makeText(this, "Error loading volunteers", Toast.LENGTH_SHORT).show();
                        Log.e("Firestore", "Error loading volunteers", e);
                    });
        }
    }