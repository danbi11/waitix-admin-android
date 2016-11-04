package com.danbi_000.waitix;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danbi_000.waitix.anim.CloseAnimation;
import com.danbi_000.waitix.anim.ExpandAnimation;

public class ModifyActivity extends Activity implements View.OnClickListener {

    /* slide menu */
    private DisplayMetrics metrics;
    private LinearLayout ll_mainLayout;
    private LinearLayout ll_menuLayout;
    private FrameLayout.LayoutParams leftMenuLayoutPrams;
    private int leftMenuWidth;
    private static boolean isLeftExpanded;
    private ImageView btn_menu;
//    private TextView btn_modify;

//    MyDBHelper dbHelper;
    private EditText etID;
    private EditText etFirstName;
    private EditText etLastName;
    private EditText etAddress;
    private EditText etSalary;
    private Button btnInsert;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnLoadAll;
    private TextView tvFinalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        initSildeMenu();

//        dbHelper = new MyDBHelper(ModifyActivity.this);
        init();
    }
    private void initSildeMenu() {

        // init left menu width
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        leftMenuWidth = (int) ((metrics.widthPixels) * 0.75);

        // init main view
        ll_mainLayout = (LinearLayout) findViewById(R.id.ll_mainlayout);

        // init left menu
        ll_menuLayout = (LinearLayout) findViewById(R.id.ll_menuLayout);
        leftMenuLayoutPrams = (FrameLayout.LayoutParams) ll_menuLayout.getLayoutParams();
        leftMenuLayoutPrams.width = leftMenuWidth;
        ll_menuLayout.setLayoutParams(leftMenuLayoutPrams);

        // init ui
        btn_menu = (ImageView)findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(this);
/*
        btn_modify = (TextView)findViewById(btn_modify);
        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        ModifyActivity.class); // 다음 넘어갈 클래스 지정
                startActivity(intent); // 다음 화면으로 넘어간다
            }
        });*/
    }

    /**
     * left menu toggle
     */
    private void menuLeftSlideAnimationToggle() {

        if (!isLeftExpanded) {

            isLeftExpanded = true;

            // Expand
            new ExpandAnimation(ll_mainLayout, leftMenuWidth, "left",
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);

            // disable all of main view
            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
                    .getParent();
            enableDisableViewGroup(viewGroup, false);

            // enable empty view
            ((LinearLayout) findViewById(R.id.ll_empty))
                    .setVisibility(View.VISIBLE);

            findViewById(R.id.ll_empty).setEnabled(true);
            findViewById(R.id.ll_empty).setOnTouchListener(
                    new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View arg0, MotionEvent arg1) {
                            menuLeftSlideAnimationToggle();
                            return true;
                        }
                    });

        } else {
            isLeftExpanded = false;

            // close
            new CloseAnimation(ll_mainLayout, leftMenuWidth,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);

            // enable all of main view
            FrameLayout viewGroup = (FrameLayout) findViewById(R.id.ll_fragment)
                    .getParent();
            enableDisableViewGroup(viewGroup, true);

            // disable empty view
            ((LinearLayout) findViewById(R.id.ll_empty))
                    .setVisibility(View.GONE);
            findViewById(R.id.ll_empty).setEnabled(false);

        }
    }

    /**
     * 뷰의 동작을 제어한다. 하위 모든 뷰들이 enable 값으로 설정된다.
     *
     * @param viewGroup
     * @param enabled
     */
    public static void enableDisableViewGroup(ViewGroup viewGroup,
                                              boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            if (view.getId() != R.id.btn_menu) {
                view.setEnabled(enabled);
                if (view instanceof ViewGroup) {
                    enableDisableViewGroup((ViewGroup) view, enabled);
                }
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_menu:
                menuLeftSlideAnimationToggle();
                break;
        }

    }


    private void init() {
        etID = (EditText) findViewById(R.id.etID);
//        etFirstName = (EditText) findViewById(R.id.etFirstName);
//        etLastName = (EditText) findViewById(R.id.etLastName);
//        etAddress = (EditText) findViewById(R.id.etAddress);
//        etSalary = (EditText) findViewById(R.id.etSalary);
//
//        btnInsert = (Button) findViewById(R.id.btnInsert);
//        btnUpdate = (Button) findViewById(R.id.btnUpdate);
//        btnDelete = (Button) findViewById(R.id.btnDelete);
//        btnLoadAll = (Button) findViewById(R.id.btnLoadAll);

/*        btnInsert.setOnClickListener(dbButtonsListener);
        btnUpdate.setOnClickListener(dbButtonsListener);
        btnDelete.setOnClickListener(dbButtonsListener);
        btnLoadAll.setOnClickListener(dbButtonsListener);*/

//        tvFinalData = (TextView) findViewById(R.id.tvData);


    }

    private View.OnClickListener dbButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                /*
                case R.id.btnInsert:
                    long resultInsert = dbHelper.insert(Integer.parseInt(getValue(etID)), getValue(etFirstName),
                            getValue(etLastName), getValue(etAddress), Double.valueOf(getValue(etSalary)));
                    if(resultInsert == -1){
                        Toast.makeText(ModifyActivity.this, "Some error occurred while inserting", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ModifyActivity.this, "Data inserted successfully, ID: " + resultInsert, Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btnUpdate:
                    long resultUpdate = dbHelper.update(Integer.parseInt(getValue(etID)), getValue(etFirstName),
                            getValue(etLastName), getValue(etAddress), Double.valueOf(getValue(etSalary)));
                    if(resultUpdate == 0){
                        Toast.makeText(ModifyActivity.this, "Some error occurred while updating", Toast.LENGTH_SHORT).show();
                    } else if(resultUpdate == 1) {
                        Toast.makeText(ModifyActivity.this, "Data updated successfully, ID: " + resultUpdate, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ModifyActivity.this, "Some error occurred, multiple records updated.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btnDelete:
                    long resultDelete = dbHelper.delete(Integer.parseInt(getValue(etID)));
                    if(resultDelete == 0){
                        Toast.makeText(ModifyActivity.this, "Some error occurred while inserting", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ModifyActivity.this, "Data deleted successfully.", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.btnLoadAll:

                    StringBuffer finalData = new StringBuffer();
                    Cursor cursor = dbHelper.getAllRecords();
//                    Cursor cursor = dbHelper.getDataBasedOnQuery("SELECT * FROM " + MyDBHelper.TABLE_NAME
//                    + " WHERE " + MyDBHelper.ADDRESS + " = 'UK'");

                    for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                        finalData.append(cursor.getInt(cursor.getColumnIndex(MyDBHelper.ID)));
                        finalData.append(" / ");
                        finalData.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.FIRST_NAME)));
                        finalData.append(" / ");
                        finalData.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.LAST_NAME)));
                        finalData.append(" / ");
                        finalData.append(cursor.getString(cursor.getColumnIndex(MyDBHelper.ADDRESS)));
                        finalData.append(" / ");
                        finalData.append(cursor.getLong(cursor.getColumnIndex(MyDBHelper.SALARY)));
                        finalData.append("\n");
                    }

                    tvFinalData.setText(finalData);
                    break;
                    */

            }
        }
    };

    private String getValue(EditText editText) {
        return editText.getText().toString().trim();
    }


    @Override
    protected void onStart() {
        super.onStart();
//        dbHelper.openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        dbHelper.closeDB();
    }
}
