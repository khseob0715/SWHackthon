package com.example.vclab.shipcrew;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    Intent intent;
    Button Btn_Passenger, Btn_Crew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Btn_Passenger = (Button)findViewById(R.id.Passenger_Btn);
        Btn_Crew = (Button)findViewById(R.id.Crew_Btn);

        Btn_Passenger.setOnClickListener(this);
        Btn_Crew.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.Passenger_Btn:
                intent = new Intent(getApplicationContext(),PassengerSignIN.class);

                break;
            case R.id.Crew_Btn:
                intent = new Intent(getApplicationContext(),CrewSignIN.class);

                break;
        }
        startActivity(intent);
    }
}
