package com.danbi_000.waitix;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends Activity implements View.OnClickListener{
    ImageView btn_login;
    TextView btn_signup;
    EditText editText6;
    EditText editText7;

    public static boolean isFirstEnter = true;  //처음만 splash 화면 나오기


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* splash 화면 띄우기 */
        if (isFirstEnter){
            startActivity(new Intent(this, SplashActivity.class));
            isFirstEnter = false;
        }


        editText6 = (EditText) findViewById(R.id.editText6);
        editText7 = (EditText) findViewById(R.id.editText7);
        btn_login = (ImageView)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        btn_signup = (TextView)findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);


        /* 자동 로그인 */
        boolean isLoginned = ServerNetworkManager.newInstance().cookieManager.getCookieStore().getCookies().size() == 1;
        if (isLoginned) { successLogin(); }
    }

    private void successLogin() {
        Intent intentToMain = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        startActivity(intentToMain);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                ServerNetworkManager serverRequest = ServerNetworkManager.newInstance();

                List<Pair<String, String>> parameters = new ArrayList<Pair<String, String>>();
                parameters.add(Pair.create("storeid", editText6.getText().toString()));
                parameters.add(Pair.create("password", editText7.getText().toString()));

                serverRequest.get("store_login.php", parameters, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "오류가 발생했습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String body = response.body().string();
                                    Log.d("response", body);

                                    Gson gson = new Gson();
                                    JsonObject jsonObject = gson.fromJson(body, JsonObject.class);

                                    Toast.makeText(LoginActivity.this, jsonObject.get("desc").getAsString(), Toast.LENGTH_LONG).show();
                                    if (jsonObject.get("success").getAsBoolean()) {
                                        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putInt("snum", jsonObject.get("snum").getAsInt());
                                        editor.putString("name", jsonObject.get("name").getAsString());
                                        editor.commit();
                                        successLogin();
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } finally {
                                    response.close();
                                }
                            }
                        });
                    }
                });
                break;

            case R.id.btn_signup:
                Intent intentToSignup = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intentToSignup);
                break;
        }
    }
}
