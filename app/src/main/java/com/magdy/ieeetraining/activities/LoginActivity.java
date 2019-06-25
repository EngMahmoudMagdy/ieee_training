package com.magdy.ieeetraining.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.magdy.ieeetraining.R;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText email, password;
    RelativeLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        progress = findViewById(R.id.progress);

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        !TextUtils.isEmpty(email.getText()) &&
                                !TextUtils.isEmpty(password.getText())
                ) {

                    progress.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(),
                            password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progress.setVisibility(View.GONE);

                            if (task.isSuccessful()) {
                                Toast.makeText(getBaseContext(), "Welcome", Toast.LENGTH_SHORT).show();

                            } else {
                                if (task.getException() != null)
                                    Toast.makeText(getBaseContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });

    }
}
