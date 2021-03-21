package com.example.matthieugedeon.android_project.fragments;

import android.app.Activity;
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
import android.widget.Spinner;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.activities.AddressDetailsActivity;
import com.example.matthieugedeon.android_project.activities.MainActivity;
import com.example.matthieugedeon.android_project.classes.AsyncCourseFetcher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainBaseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainBaseFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final Activity ARG_PARAM1 = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainBaseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainBaseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainBaseFragment newInstance(String param1, String param2) {
        MainBaseFragment fragment = new MainBaseFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateView(view);

        Button b1=(Button)view.findViewById(R.id.fetch_button);
        b1.setOnClickListener(new AddressDetailsOnClickListener());
    }

    //Tailored onClickListener that launch our parameterized AsyncTask
    class AddressDetailsOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.i("Button","Clicked");
            EditText address = (EditText)getView().findViewById(R.id.address);

            Intent intent = new Intent(getActivity(), AddressDetailsActivity.class);
            intent.putExtra("address", address.getText().toString());

            Spinner spinner = (Spinner)getView().findViewById(R.id.coin_type);
            String text = spinner.getSelectedItem().toString();
            intent.putExtra("coin", text);
            startActivity(intent);

        }
    }


    private void populateView(View view){


        SpinnerListener sl = new SpinnerListener();


        //Populate Spinner (Android Developers)
        Spinner spinner = (Spinner) view.findViewById(R.id.coin_type);
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

            if(parent.getId() == R.id.coin_type){
                ImageView iv = (ImageView) getView().findViewById(R.id.icon_search);
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