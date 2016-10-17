package com.danbi_000.waitix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener{
    ImageView btn_login;
    TextView btn_signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (ImageView)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_signup = (TextView)findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Intent intentToMain = new Intent(getApplicationContext(), // 현재 화면의 제어권자
                        MainActivity.class); // 다음 넘어갈 클래스 지정
                finish();
                startActivity(intentToMain); // 다음 화면으로 넘어간다
                Toast.makeText(this,"환영합니다",Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_signup:
                Intent intentToSignup = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intentToSignup);
                break;
        }
    }
}
