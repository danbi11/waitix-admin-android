package com.danbi_000.waitix;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SignupActivity extends Activity implements View.OnClickListener{
    RelativeLayout btn_cancel;
    ImageView  btn_ok, ivImgsrc;
    EditText etID, etName, etTel, etLocation, etDesc, etPassword, etAlarmtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btn_cancel = (RelativeLayout)findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        btn_ok = (ImageView)findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        etID = (EditText)findViewById(R.id.etID);
        etName = (EditText)findViewById(R.id.etName);
        etTel = (EditText) findViewById(R.id.etTel );
        etLocation = (EditText) findViewById(R.id.etLocation );
        etDesc = (EditText) findViewById(R.id.etDesc );
        etPassword = (EditText) findViewById(R.id.etPassword );
        ivImgsrc = (ImageView) findViewById(R.id.ivImgsrc);
        etAlarmtime = (EditText) findViewById(R.id.etAlarmtime );
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_cancel:
                finish();
                break;

            case R.id.btn_ok:
                ServerNetworkManager serverRequest = ServerNetworkManager.newInstance();

                List<Pair<String, String>> parameters = new ArrayList<Pair<String, String>>();
                parameters.add(Pair.create("storeid", etID.getText().toString()));
                parameters.add(Pair.create("name", etName.getText().toString()));
                parameters.add(Pair.create("tel", etTel.getText().toString()));
                parameters.add(Pair.create("address", etLocation.getText().toString()));
                parameters.add(Pair.create("text", etDesc.getText().toString()));
                parameters.add(Pair.create("password", etPassword.getText().toString()));
                parameters.add(Pair.create("alarmtime", etAlarmtime.getText().toString()));
                parameters.add(Pair.create("imgsrc", "TEST.png"));

                serverRequest.get("store_signup.php", parameters, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SignupActivity.this, "오류가 발생했습니다.", Toast.LENGTH_LONG).show();
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

                                    Toast.makeText(SignupActivity.this, jsonObject.get("desc").getAsString(), Toast.LENGTH_LONG).show();
                                    if (jsonObject.get("success").getAsBoolean()) {
                                        finish();
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
        }
    }

}
