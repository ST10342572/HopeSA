package com.example.hopesa.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.hopesa.R;

public class AdminDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        Button btnUsers = findViewById(R.id.btnManageUsers);
        Button btnEvents = findViewById(R.id.btnManageEvents);
        Button btnVolunteers = findViewById(R.id.btnManageVolunteers);
        Button btnRequests = findViewById(R.id.btnManageRequests);

        btnUsers.setOnClickListener(v -> startActivity(new Intent(this, AdminManageUsersActivity.class)));
        btnEvents.setOnClickListener(v -> startActivity(new Intent(this, AdminManageEventsActivity.class)));
        btnVolunteers.setOnClickListener(v -> startActivity(new Intent(this, AdminManageVolunteersActivity.class)));
        btnRequests.setOnClickListener(v -> startActivity(new Intent(this, AdminManageRequestsActivity.class)));
    }
}