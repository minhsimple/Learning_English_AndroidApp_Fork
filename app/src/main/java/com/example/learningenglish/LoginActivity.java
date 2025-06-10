package com.example.learningenglish;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText email = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        Button login = findViewById(R.id.buttonLogin);
        TextView register = findViewById(R.id.textRegister);

        ExecutorService executor = Executors.newSingleThreadExecutor();

        login.setOnClickListener(v -> {
            String user = email.getText().toString();
            String pass = password.getText().toString();
            executor.execute(() -> {
                boolean success = DatabaseHelper.checkUser(user, pass);
                runOnUiThread(() -> {
                    if (success) {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        });

        register.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
