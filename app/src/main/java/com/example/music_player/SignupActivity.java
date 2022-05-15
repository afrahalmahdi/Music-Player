package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.music_player.db.DBHelper;

public class SignupActivity extends AppCompatActivity {

    EditText fnameEditText, lnameEditText, emailEditText, passwordEditText,repasswordEditText;
    Button signinButton, signupButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        fnameEditText = findViewById(R.id.first_name_id);
        lnameEditText = findViewById(R.id.last_name_id);
        emailEditText = findViewById(R.id.email_editText_id);
        passwordEditText = findViewById(R.id.password_editText_id);
        repasswordEditText = findViewById(R.id.confirm_password_id);
        signupButton = findViewById(R.id.signup_button);
        DB = new DBHelper(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = fnameEditText.getText().toString();
                String lname = lnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String pass = passwordEditText.getText().toString();
                String repass = repasswordEditText.getText().toString();

                if ( TextUtils.isEmpty(fname)
                        || TextUtils.isEmpty(lname)
                        || TextUtils.isEmpty(email)
                        || TextUtils.isEmpty(pass) )
                    Toast.makeText(getApplicationContext(), "All fields are required", Toast.LENGTH_SHORT).show();
                else  {
                    if(pass.equals(repass)) {
                        Boolean checkUser = DB.checkUsername(email);
                        if (checkUser == false) {
                            boolean insert = DB.insertData(email, pass);
                            if (insert == true) {
                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), "Registered Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password is not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        signinButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });

    }


}