package com.example.matthieugedeon.android_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.classes.DataBaseHelper;

public class SignUpActivity extends AppCompatActivity {

    DataBaseHelper dbHelper;
    private Button signUp;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dbHelper = new DataBaseHelper(this);
        username = (EditText)findViewById(R.id.signup_username);
        password = (EditText)findViewById(R.id.signup_password);
        signUp = (Button)findViewById(R.id.signup_button);
        signUp.setOnClickListener(new AddUserOnClickListener());
    }

    public void toast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    class AddUserOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String un = username.getText().toString();
            String pw = password.getText().toString();
            boolean inserted = dbHelper.addUser(un, pw);
            if(username.length() == 0 || password.length() == 0)
            {
                toast("Please fill out both fields!");
            }
            else
            {
                if (inserted) {
                    toast("Added user : " + un + " with password : " + pw);
                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(intent);
                }
                else
                {
                    toast("ERROR while adding");
                }
            }
            }

    }

}