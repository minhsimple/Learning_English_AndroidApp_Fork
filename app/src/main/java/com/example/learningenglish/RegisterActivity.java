package com.example.learningenglish;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText email = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        Button register = findViewById(R.id.buttonRegister);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        register.setOnClickListener(v -> {
            if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                String user = email.getText().toString();
                String pass = password.getText().toString();
                executor.execute(() -> {
                    boolean added = DatabaseHelper.registerUser(user, pass);
                    runOnUiThread(() -> {
                        if (added) {
                            Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
