package com.example.nodool.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nodool.R;
import com.example.nodool.database.UserDatabase;
import com.example.nodool.entities.User;
import com.google.android.material.textfield.TextInputEditText;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class LoginUser extends AppCompatActivity {

    Button signIn, signUp;
    TextInputEditText email;
    EditText password;
    private final String LOGGED_IN_USER_FILE = "LoggedInUser.txt";

    User loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);


        signIn = findViewById(R.id.buttonSignIn);
        signUp = findViewById(R.id.ButtonDirectSignUp);
        email = findViewById(R.id.signInEmail);
        password = findViewById(R.id.signInPassword);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginUser.this, RegisterUser.class));
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString();
                String passwordText = password.getText().toString();

                if(emailText.isEmpty() || passwordText.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {

                    @SuppressLint("StaticFieldLeak")
                    class CheckAndLoginUser extends AsyncTask<Void, Void, Void> {

                        @Override
                        protected Void doInBackground(Void... voids) {
                            loginUser = UserDatabase.getUserDatabase(getApplicationContext()).userDao().loginUser(emailText, passwordText);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);

//                            Log.e("login_data", "here");
                            if (loginUser != null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                try {
                                    FileOutputStream fileOutput=openFileOutput(LOGGED_IN_USER_FILE, MODE_PRIVATE);
                                    OutputStreamWriter outputWriter=new OutputStreamWriter(fileOutput);
                                    outputWriter.write(emailText+","+passwordText);
                                    outputWriter.close();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                Intent intent = new Intent(LoginUser.this, MainActivity.class);
                                intent.putExtra("user", loginUser);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                    new CheckAndLoginUser().execute();
                }
            }
        });

    }
}