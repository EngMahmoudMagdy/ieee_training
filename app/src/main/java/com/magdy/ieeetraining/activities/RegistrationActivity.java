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
import com.google.firebase.database.FirebaseDatabase;
import com.magdy.ieeetraining.R;

public class RegistrationActivity extends AppCompatActivity {
    TextInputEditText name, email, password, confirmPassword;
    RelativeLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name = findViewById(R.id.nameEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.confirmPasswordEditText);
        progress = findViewById(R.id.progress);

        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(name.getText()) &&
                        !TextUtils.isEmpty(email.getText()) &&
                        !TextUtils.isEmpty(password.getText()) &&
                        !TextUtils.isEmpty(confirmPassword.getText())
                ) {
                    if (password.getText().toString().equals(confirmPassword.getText().toString())) {

                        progress.setVisibility(View.VISIBLE);
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(),
                                password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progress.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    String userId = FirebaseAuth.getInstance().getUid();
                                    if (userId != null) {
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("users").child(userId).child("email").setValue(email.getText().toString());
                                        FirebaseDatabase.getInstance().getReference()
                                                .child("users").child(userId).child("name").setValue(name.getText().toString());
                                    }
                                } else {
                                    if (task.getException() != null)
                                        Toast.makeText(getBaseContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                    } else {
                        Toast.makeText(getBaseContext(), "Password doesn't match with confirmation", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }
}
