package com.danbi_000.waitix;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by danbi_000 on 2016-11-05.
 */

public class IssueCloseDialog extends Dialog implements View.OnClickListener {
    private Button btnNo, btnYes;

    public IssueCloseDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_issue_close);

        btnNo = (Button)findViewById(R.id.btnNo);
        btnNo.setOnClickListener(this);
        btnYes = (Button)findViewById(R.id.btnYes);
        btnYes.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNo:
                cancel();
                break;
            case R.id.btnYes:
                Toast.makeText(getContext(),"마감되었습니다.",Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }
}
