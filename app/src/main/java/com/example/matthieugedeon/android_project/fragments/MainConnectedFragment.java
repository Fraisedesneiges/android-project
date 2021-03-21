package com.example.matthieugedeon.android_project.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.activities.AddressDetailsActivity;
import com.example.matthieugedeon.android_project.classes.ListAdapter;
import com.example.matthieugedeon.android_project.classes.SessionData;
import com.example.matthieugedeon.android_project.classes.WalletListAdapter;
import com.example.matthieugedeon.android_project.models.Wallet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainConnectedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainConnectedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainConnectedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainConnectedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainConnectedFragment newInstance(String param1, String param2) {
        MainConnectedFragment fragment = new MainConnectedFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_connected, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateView(view);

        Button b1=(Button)view.findViewById(R.id.c_fetch);
        b1.setOnClickListener(new AddressDetailsOnClickListener());

        ListView list = (ListView)view.findViewById(R.id.a_list);
        WalletListAdapter adapter = new WalletListAdapter(SessionData.getMain(),new Vector<Wallet>(), SessionData.getMain());
        list.setAdapter(adapter);
        SessionData.setWadapter(adapter);

        ArrayList<Wallet> wallets = SessionData.getWallets();
        wallets.forEach(wallet -> {
            SessionData.getWadapter().add(wallet);
            SessionData.getWadapter().notifyDataSetChanged();
        });

    }

    //Tailored onClickListener that launch our parameterized AsyncTask
    class AddressDetailsOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.i("Button","Clicked");
            EditText address = (EditText)getView().findViewById(R.id.c_address);

            Intent intent = new Intent(getActivity(), AddressDetailsActivity.class);
            intent.putExtra("address", address.getText().toString());
            intent.putExtra("context", "fetch");

            Spinner spinner = (Spinner)getView().findViewById(R.id.c_coin_type);
            String text = spinner.getSelectedItem().toString();
            intent.putExtra("coin", text);
            startActivity(intent);

        }
    }

    private void populateView(View view){


        SpinnerListener sl = new SpinnerListener();


        //Populate Spinner (Android Developers)
        Spinner spinner = (Spinner) view.findViewById(R.id.c_coin_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.coin_types, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(sl);
    }

    public class SpinnerListener implements AdapterView.OnItemSelectedListener {


        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {

            if(parent.getId() == R.id.c_coin_type){
                ImageView iv = (ImageView) getView().findViewById(R.id.c_icon);
                String s = (String)parent.getItemAtPosition(pos);

                switch (s) {
                    case "BTC":
                        iv.setImageDrawable(getActivity().getDrawable(R.drawable.btc));
                        break;
                    case "ETH":
                        iv.setImageDrawable(getActivity().getDrawable(R.drawable.eth));
                        break;
                    default: break;
                }
            }
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }
}