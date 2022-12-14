package com.laurawilson.travall3.LoginServices;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.laurawilson.travall3.MainMenu;
import com.laurawilson.travall3.R;

public class CreateLogin extends AppCompatActivity {

    private Button create;
    private EditText username, password, repassword;
    UserDB userdb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_login);

        create = findViewById(R.id.create);
        username = findViewById(R.id.usernameCreate);
        password = findViewById(R.id.passwordCreate);
        repassword = findViewById(R.id.repasswordCreate);
        userdb = new UserDB(this);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)) {
                    Toast.makeText(CreateLogin.this, "All fields required", Toast.LENGTH_SHORT).show();
                } else {
                    if(pass.equals(repass)) {
                        Boolean checkuser = userdb.checkusername(user);
                        if (!checkuser) {
                            Boolean insert = userdb.insertData(user, pass);
                            if (insert) {
                                Toast.makeText(CreateLogin.this, "Success! Welcome to Travall!", Toast.LENGTH_SHORT).show();
                                openMainMenu();
                            } else {
                                Toast.makeText(CreateLogin.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CreateLogin.this, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CreateLogin.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }


    protected void openMainMenu(){

        Intent main = new Intent(this, MainMenu.class);
        startActivity(main);
    }
}