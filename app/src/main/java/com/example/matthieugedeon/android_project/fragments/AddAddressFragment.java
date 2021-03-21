package com.example.matthieugedeon.android_project.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.activities.LogInActivity;
import com.example.matthieugedeon.android_project.classes.DataBaseHelper;
import com.example.matthieugedeon.android_project.classes.Parser;
import com.example.matthieugedeon.android_project.classes.SessionData;
import com.example.matthieugedeon.android_project.models.Wallet;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAddressFragment extends Fragment {
    DataBaseHelper dbHelper;
    private Button logIn;
    private EditText un, pw;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAddressFragment newInstance(String param1, String param2) {
        AddAddressFragment fragment = new AddAddressFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHelper = new DataBaseHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button fetch = (Button)getActivity().findViewById(R.id.add_address);
        fetch.setOnClickListener(new fetchOnClickListener());
    }

    class fetchOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            TextView address = (TextView)getActivity().findViewById(R.id.address_val);
            String address_val = address.getText().toString();

            dbHelper.updateWallet(SessionData.getUsername(),address_val);

            SessionData.setWallets(new ArrayList<Wallet>());
            Cursor cursor = dbHelper.getUserData(SessionData.getUsername());
            if (cursor.moveToFirst()) {
                do {
                    StringBuilder sb = new StringBuilder();
                    sb.append(cursor.getString(0));
                    SessionData.setRowWallets(sb.toString());
                    SessionData.setWallets(Parser.getWallet(sb.toString()));
                    Log.i("LOG", sb.toString());

                } while (cursor.moveToNext());
            }

            ArrayList<Wallet> wallets = SessionData.getWallets();
            wallets.forEach(wallet -> {
                SessionData.getWadapter().add(wallet);
                SessionData.getWadapter().notifyDataSetChanged();
            });
        }
    }
}