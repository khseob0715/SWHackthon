package com.example.vclab.shipcrew;


import android.content.Intent;
import android.content.SharedPreferences;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class PassengerSignIN extends Activity {

    Button Register_btn;
    EditText Passenger_Name_Text, Passenger_Phone_Text;

    String loginId, loginPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_sign_in);

        Register_btn = (Button) findViewById(R.id.Passenger_Register_Btn);
        Passenger_Name_Text = (EditText) findViewById(R.id.Passenger_Name);
        Passenger_Phone_Text = (EditText) findViewById(R.id.Passenger_Phone_Num);

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        // 처음에 ShaderPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        // getString의 첫번째 인자는 저장될 키, 두번째 인자는 값이다.
        // 처음에는 값이 없으므로 키값은 원하는 것으로 하고 값을 null로 해준다.

        loginId = auto.getString("inputId", null);
        loginPwd = auto.getString("inputPwd", null);

        if (loginId != null && loginPwd != null) {  // 자동 로그인
                Toast.makeText(this, loginId +"님 자동로그인 입니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("crew",false);
                intent.putExtra("name", loginId);
                startActivity(intent);
                finish();
        }
        //id와 pwd가 null이면 Mainactivity가 보여짐.
        else if (loginId == null && loginPwd == null) {
            Register_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                    //SharedPreferences.Editor를 통해
                    //auto의 loginId와 loginPwd에 값을 저장해 줍니다.
                    SharedPreferences.Editor autoLogin = auto.edit();
                    autoLogin.putString("inputId", Passenger_Name_Text.getText().toString());
                    autoLogin.putString("inputPwd", Passenger_Phone_Text.getText().toString());
                    // commit()을 해줘야 값이 저장됩니다
                    autoLogin.commit();

                    Intent intent = new Intent(PassengerSignIN.this, MainActivity.class);
                    intent.putExtra("crew",false);
                    intent.putExtra("name", Passenger_Name_Text.getText().toString());
                    startActivity(intent);
                    finish();
                }
            });
        }
    }


}