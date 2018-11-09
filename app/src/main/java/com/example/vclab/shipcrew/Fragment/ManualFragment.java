package com.example.vclab.shipcrew.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vclab.shipcrew.R;

import org.w3c.dom.Text;

/**
 * Created by Aiden on 2018-08-29.
 */

public class ManualFragment extends Fragment implements View.OnClickListener{


    private View rootView;


    private TextView Page1, Page2, Page3, Page4, Page5, Page6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_manual, null);


        Page1 = (TextView)rootView.findViewById(R.id.Page1);
        Page1.setOnClickListener(this);
        Page2 = (TextView)rootView.findViewById(R.id.Page2);
        Page2.setOnClickListener(this);
        Page3 = (TextView)rootView.findViewById(R.id.Page3);
        Page3.setOnClickListener(this);
        Page4 = (TextView)rootView.findViewById(R.id.Page4);
        Page4.setOnClickListener(this);
        Page5 = (TextView)rootView.findViewById(R.id.Page5);
        Page5.setOnClickListener(this);
        Page6 = (TextView)rootView.findViewById(R.id.Page6);
        Page6.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Page1:

                break;
            case R.id.Page2:

                break;
            case R.id.Page3:

                break;
            case R.id.Page4:

                break;
            case R.id.Page5:

                break;
            case R.id.Page6:

                break;


        }

    }
}
