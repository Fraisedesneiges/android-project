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
import android.widget.Button;
import android.widget.TextView;

import com.example.matthieugedeon.android_project.R;
import com.example.matthieugedeon.android_project.activities.LogInActivity;
import com.example.matthieugedeon.android_project.classes.SessionData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ButtonConnectedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ButtonConnectedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ButtonConnectedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ButtonConnectedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ButtonConnectedFragment newInstance(String param1, String param2) {
        ButtonConnectedFragment fragment = new ButtonConnectedFragment();
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
        return inflater.inflate(R.layout.fragment_button_connected, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = (TextView)view.findViewById(R.id.user_name);

        tv.setText(SessionData.getUsername());

        Button b1=(Button)view.findViewById(R.id.disconnect);
        b1.setOnClickListener(new DisconnectOnClickListener());
    }

    class DisconnectOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.i("Disconnect","Clicked");
            SessionData.setConnected(false);
            SessionData.setUsername("");
            SessionData.getMainFM().beginTransaction()
                    .replace(R.id.main_container, MainBaseFragment.class, null)
                    .commitAllowingStateLoss();

            SessionData.getMainFM().beginTransaction()
                    .replace(R.id.button_container, ButtonBaseFragment.class, null)
                    .commitAllowingStateLoss();

        }
    }

}