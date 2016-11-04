package com.danbi_000.waitix;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MamualActivity extends Activity {
    ImageView howtouse_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mamual);

        howtouse_btn = (ImageView)findViewById(R.id.howtouse_btn);
        howtouse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
