package com.example.matthieugedeon.android_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.classes.DataBaseHelper;
import com.example.matthieugedeon.android_project.classes.Parser;
import com.example.matthieugedeon.android_project.classes.SessionData;
import com.example.matthieugedeon.android_project.classes.WalletListAdapter;
import com.example.matthieugedeon.android_project.fragments.ButtonConnectedFragment;
import com.example.matthieugedeon.android_project.fragments.MainConnectedFragment;

public class LogInActivity extends AppCompatActivity {
    DataBaseHelper dbHelper;
    private Button logIn;
    private EditText un, pw;
    Vibrator vib;

    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        dbHelper = new DataBaseHelper(this);
        un = (EditText) findViewById(R.id.login_username);
        pw = (EditText) findViewById(R.id.login_password);
        logIn = (Button) findViewById(R.id.login_button);
        logIn.setOnClickListener(new loginOnClickListener());
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    //Logs the wallet of the users credentials
    class loginOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String u = un.getText().toString();
            String p = pw.getText().toString();
            boolean b = dbHelper.userCheck(u, p);
            if (b) {
                Cursor cursor = dbHelper.getUserData(u);
                if (cursor.moveToFirst()) {
                    do {
                        StringBuilder sb = new StringBuilder();
                        sb.append(cursor.getString(0));
                        SessionData.setRowWallets(sb.toString());
                        SessionData.setWallets(Parser.getWallet(sb.toString()));
                        Log.i("LOG", sb.toString());

                    } while (cursor.moveToNext());
                }

                SessionData.setUsername(un.getText().toString());
                SessionData.setConnected(true);

                SessionData.getMainFM().beginTransaction()
                        .replace(R.id.button_container, ButtonConnectedFragment.class, null)
                        .commitAllowingStateLoss();

                SessionData.getMainFM().beginTransaction()
                        .replace(R.id.main_container, MainConnectedFragment.class, null)
                        .commitAllowingStateLoss();

                vib.vibrate(300);
            } else {
                toast("No such user in the database");
            }
            finish();
        }
    }
}