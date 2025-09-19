package com.example.hopesa.ui.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hopesa.R;
import com.google.firebase.firestore.*;
import java.util.*;

public class AdminManageEventsActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_events);
        listView = findViewById(R.id.listViewEvents);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventList);
        listView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        loadEvents();
    }

    private void loadEvents() {
        db.collection("EVENTS").get().addOnSuccessListener(queryDocumentSnapshots -> {
            eventList.clear();
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                String title = doc.getString("title");
                eventList.add(title);
            }
            adapter.notifyDataSetChanged();
        });
    }
}