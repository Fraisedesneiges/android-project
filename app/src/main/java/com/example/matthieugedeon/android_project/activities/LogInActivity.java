package com.example.matthieugedeon.android_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.classes.DataBaseHelper;

public class LogInActivity extends AppCompatActivity {
    DataBaseHelper dbHelper;
    private Button logIn;
    private EditText un, pw;

    public void toast(String text)
    {
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        dbHelper = new DataBaseHelper(this);
        un = (EditText)findViewById(R.id.login_username);
        pw = (EditText)findViewById(R.id.login_password);
        logIn = (Button)findViewById(R.id.login_button);
        logIn.setOnClickListener(new loginOnClickListener());

    }

    class loginOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v)
        {
            String u = un.getText().toString();
            String p = pw.getText().toString();
            Cursor cursor = dbHelper.getUserData(u);
            String d = "";
            if (cursor.moveToFirst()) {
                do {
                    StringBuilder sb = new StringBuilder();
                    int columnsQty = cursor.getColumnCount();
                    for (int idx=0; idx<columnsQty; ++idx) {
                        sb.append(cursor.getString(idx));
                        if (idx < columnsQty - 1)
                            sb.append("; ");
                    }
                    Log.v("LOG", String.format("Row: %d, Values: %s", cursor.getPosition(),
                            sb.toString()));
                } while (cursor.moveToNext());
            }
            //toast(d);
        }
    }
}