package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.music_player.db.DBHelper;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button signinButton;
    TextView signupTextView;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        emailEditText = findViewById(R.id.email_editText);
        passwordEditText = findViewById(R.id.password_editText);
        signinButton = findViewById(R.id.login_button);
        signupTextView = findViewById(R.id.signup_textView);
        DB =new DBHelper(this);

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String pass = passwordEditText.getText().toString();

                if ( TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) )
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                else  {
                        Boolean checkLogin = DB.checkLogin(email, pass);
                        if (checkLogin == true) {
                                Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                }
            }
        });

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Sign Up", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext() , SignupActivity.class);
                startActivity(intent);
            }
        });


    }
}