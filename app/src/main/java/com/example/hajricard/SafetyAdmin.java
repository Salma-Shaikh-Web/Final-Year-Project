package com.example.hajricard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SafetyAdmin extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_safety_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;



            usernameEditText = findViewById(R.id.username_edit_text);
            passwordEditText = findViewById(R.id.password_edit_text);

            // Start the emergency monitoring service on app launch
            Intent serviceIntent;
            serviceIntent = new Intent(SafetyAdmin.this, EmergencyMonitoringService.java);
            startService(serviceIntent);

        });
    }
}