package com.example.vclab.shipcrew;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vclab.shipcrew.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

/**
 * A login screen that offers login via email/password.
 */
public class CrewSignIN extends Activity {
    Button CrewSignIN_Btn, CrewSignUP_Btn;
    EditText UP_Crew_Email_Text, Up_Crew_PW_Text, Up_Crew_Name;

    EditText Crew_Email, Crew_PW_Text;

    String loginId, loginPwd, loginName;


    private FirebaseRemoteConfig firebaseRemoteConfig; // 원격으로 테마를 적용 받기 위해서는 필요함.
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStaceListener;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crew_sign_in);

        CrewSignIN_Btn = (Button) findViewById(R.id.Crew_singIN_Btn);
        CrewSignUP_Btn = (Button) findViewById(R.id.Crew_singUP_Btn);
        UP_Crew_Email_Text = (EditText) findViewById(R.id.up_crew_email);
        Up_Crew_PW_Text = (EditText) findViewById(R.id.up_crew_password);
        Up_Crew_Name = (EditText) findViewById(R.id.up_crew_name);

        Crew_Email = (EditText)findViewById(R.id.crew_email);
        Crew_PW_Text = (EditText)findViewById(R.id.crew_password);

        firebaseRemoteConfig = firebaseRemoteConfig.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();  // auth를 받아오는 이벤트!!
        firebaseAuth.signOut();


        mDatabase = FirebaseDatabase.getInstance().getReference();

        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        loginId = auto.getString("inputId", null);
        loginPwd = auto.getString("inputPwd", null);
        loginName = auto.getString("inputName",null);

        if (loginId != null && loginPwd != null) {
            // 자동 로그인.
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("crew",true);
            intent.putExtra("name", loginName);
            startActivity(intent);
            finish();

        } else if (loginId == null && loginPwd == null) {
            // 회원 가입을 이미 했을 경우.
            CrewSignIN_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        loginEvent();
                    }
                });
        }

        // 로그인 인터페이스 리스너
        authStaceListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // 로그인이 되었거나, 로그아웃이 되어 상태가 변경되었을떄
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    // 로그인
                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

                    //auto의 loginId와 loginPwd에 값을 저장해 줍니다.
                    SharedPreferences.Editor autoLogin = auto.edit();
                    autoLogin.putString("inputId", UP_Crew_Email_Text.getText().toString());
                    autoLogin.putString("inputPwd", Up_Crew_PW_Text.getText().toString());
                    autoLogin.putString("inputName", Up_Crew_Name.getText().toString());
                    loginName = Up_Crew_Name.getText().toString();

                    autoLogin.commit();

                    //Toast.makeText(CrewSignIN.this, "리스너 실행 완료", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CrewSignIN.this, MainActivity.class);
                    intent.putExtra("crew",true);
                    intent.putExtra("name", loginName);
                    startActivity(intent);
                    finish();

                }else{
                    // 로그아웃
                }
            }
        };




        CrewSignUP_Btn.setOnClickListener(new OnClickListener() { // 처음 아이디 없을 때 가입 시.
            @Override
            public void onClick(View view) {
                if (UP_Crew_Email_Text.getText().toString() == null || Up_Crew_PW_Text.getText().toString() == null || Up_Crew_Name.getText().toString() == null) {
                    return;
                }
                // 아래의 명령으로 회원가입 정보를 넣을 수 있음.
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(UP_Crew_Email_Text.getText().toString(), Up_Crew_PW_Text.getText().toString())
                        .addOnCompleteListener(CrewSignIN.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // uid를 받아와 데이터베이스에 저장.
                                //  String uid = task.getResult().getUser().getUid();
                                final String uid = task.getResult().getUser().getUid();
                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(Up_Crew_Name.getText().toString()).build();

                                task.getResult().getUser().updateProfile(userProfileChangeRequest);

                                final UserModel userModel = new UserModel();
                                userModel.userName = Up_Crew_Name.getText().toString();
                                userModel.uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                FirebaseDatabase.getInstance().getReference().child("users").child(uid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(CrewSignIN.this, "회원가입 완료", Toast.LENGTH_SHORT).show();
                                        CrewSignIN.this.finish();
                                    }
                                });
                            }
                        });
            }
        });
    }


    void loginEvent(){
        firebaseAuth.signInWithEmailAndPassword(Crew_Email.getText().toString(), Crew_PW_Text.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() { // 로그인이 완료되었다고만 알려주는 것.
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()) {
                            // 로그인이 실패했을때

                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStaceListener);
        // 시작했을때 리스너를 실행.
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStaceListener);
        // 멈추었을때 리스너를 실행 .
    }

}
