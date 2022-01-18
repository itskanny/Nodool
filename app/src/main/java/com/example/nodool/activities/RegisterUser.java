package com.example.nodool.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nodool.R;
import com.example.nodool.database.UserDatabase;
import com.example.nodool.entities.User;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterUser extends AppCompatActivity {

    EditText password, confirmPassword;
    TextInputEditText name, email;
    Button signup;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getSupportActionBar().hide();



        password = findViewById(R.id.userPassword);
        confirmPassword = findViewById(R.id.userConfirmPassword);
        name = findViewById(R.id.userName);
        email = findViewById(R.id.userEmail);
        signup = findViewById(R.id.buttonSignup);
        toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (email.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    return;

                }else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    Toast.makeText(getApplicationContext(), "Email address not valid", Toast.LENGTH_SHORT).show();
                    return;
                } else if (password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password fields cannot be empty", Toast.LENGTH_SHORT).show();
                    return;

                }else if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Password do not match", Toast.LENGTH_SHORT).show();
                    return;

                }
                else {
                    User user = new User();
                    user.setEmail(email.getText().toString());
                    user.setName(name.getText().toString());
                    user.setPassword(password.getText().toString());

                    class SaveUser extends AsyncTask<Void, Void,Void>{

                        @Override
                        protected Void doInBackground(Void... voids) {

                            UserDatabase.getUserDatabase(getApplicationContext()).userDao().registerUser(user);
                            return null;
                        }


                        @Override
                        protected void onPostExecute(Void unused) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_SHORT).show();
                                    email.setText("");
                                    name.setText("");
                                    password.setText("");
                                    confirmPassword.setText("");
                                    onBackPressed();
                                }
                            });
                            super.onPostExecute(unused);
                        }
                    }

                    new  SaveUser().execute();

                }
            }
        });
    }
}