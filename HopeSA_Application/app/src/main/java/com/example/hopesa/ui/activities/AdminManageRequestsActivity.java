package com.example.hopesa.ui.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hopesa.R;
import com.google.firebase.firestore.*;
import java.util.*;

public class AdminManageRequestsActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> requestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_requests);
        listView = findViewById(R.id.listViewRequests);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requestList);
        listView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        loadRequests();
    }

    private void loadRequests() {
        db.collection("REQUESTS").get().addOnSuccessListener(queryDocumentSnapshots -> {
            requestList.clear();
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                String type = doc.getString("typeOfHelp");
                String status = doc.getString("status");
                requestList.add(type + " - " + status);
            }
            adapter.notifyDataSetChanged();
        });
    }
}