package com.example.medapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    EditText name, email, password, confirm;
    boolean isNameValid, isEmailValid, isPasswordValid, ispasswordConfirm;
    TextInputLayout nameError, emailError, passError, confirmError;
    FirebaseAuth mAuth;
    Button register_btn;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        android.graphics.drawable.Drawable background = RegisterActivity.this.getResources().getDrawable(R.drawable.color);
        getWindow().setBackgroundDrawable(background);

        register_btn = findViewById(R.id.register_btn);
        name = (EditText) findViewById(R.id.name_edit_text);
        email = (EditText) findViewById(R.id.email_edit_text);
        password = (EditText) findViewById(R.id.password_edit_text);
        confirm = (EditText) findViewById(R.id.confirm_edit_text);
        nameError = (TextInputLayout) findViewById(R.id.name_text_layout);
        emailError = (TextInputLayout) findViewById(R.id.email_text_layout);
        passError = (TextInputLayout) findViewById(R.id.user_text_layout);
        confirmError = (TextInputLayout) findViewById(R.id.password_register_text_layout);
        mAuth = FirebaseAuth.getInstance();
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValidation();
            }
        });

    }

    public void setValidation() {
        // Check for a valid name.
        if (name.getText().toString().isEmpty()) {
            nameError.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else {
            isNameValid = true;
            nameError.setErrorEnabled(false);
        }

        // Check for a valid email address.
        if (email.getText().toString().isEmpty()) {
            emailError.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            emailError.setError(getResources().getString(R.string.error_invalid_email));
            isEmailValid = false;
        } else {
            isEmailValid = true;
            emailError.setErrorEnabled(false);
        }

        // Check for a valid password.
        if (password.getText().toString().isEmpty()) {
            passError.setError(getResources().getString(R.string.password_error));
            isPasswordValid = false;
        } else if (password.getText().length() < 6) {
            passError.setError(getResources().getString(R.string.error_invalid_password));
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
            passError.setErrorEnabled(false);
        }

        if (confirm.getText().toString().isEmpty()) {
            confirmError.setError(getResources().getString(R.string.password_error));
            ispasswordConfirm = false;
        } else if (confirm.getText().length() < 6) {
            confirmError.setError(getResources().getString(R.string.error_invalid_password));
            ispasswordConfirm = false;
        } else if(confirm.getText().toString().equals(password.getText().toString())){
            ispasswordConfirm = true;
            passError.setErrorEnabled(false);
        }

        if (isNameValid && isEmailValid && isPasswordValid && ispasswordConfirm) {
            UserDetails details = new UserDetails(name.getText().toString(), email.getText().toString(), password.getText().toString());
            mAuth.createUserWithEmailAndPassword(details.getEmail(), details.getPassword())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in details's information
                                FirebaseUser user = mAuth.getCurrentUser();
//
                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(details.getName()).build();
                                user.updateProfile(profileUpdate)
                                        .addOnCompleteListener(task1 -> {
                                            if (task1.isSuccessful()) {
                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });


                            } else {
//                                // If sign in fails, display a message to the details.
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
