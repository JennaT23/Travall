package com.laurawilson.travall3;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    Button login, createLogin;
    EditText username, password;
    UserDB userdb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.loginbutton);
        createLogin = findViewById(R.id.createLoginButton);

        username = findViewById(R.id.email);
        password = findViewById(R.id.password);
        userdb = new UserDB(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = username.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)){
                    Toast.makeText(Login.this, "All fields required", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkuserpass = userdb.checkerusernameandpassword(email, pass);
                    if(checkuserpass) {
                        Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        openMainMenu();
                    } else {
                        Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        createLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateLoginWindow();
            }
        });
    }

    protected void openMainMenu(){

        Intent main = new Intent(this, MainMenu.class);
        startActivity(main);
    }

    protected void openCreateLoginWindow(){
        Intent create = new Intent(this, CreateLogin.class);
        startActivity(create);
    }
}
