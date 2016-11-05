package com.danbi_000.waitix;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.danbi_000.waitix.anim.CloseAnimation;
import com.danbi_000.waitix.anim.ExpandAnimation;

public class ModifyActivity extends Activity implements View.OnClickListener {

    /* slide menu */
    private DisplayMetrics metrics;
    private LinearLayout ll_mainLayout;
    private LinearLayout ll_menuLayout;
    private FrameLayout.LayoutParams mainLayoutPrams;
    private FrameLayout.LayoutParams leftMenuLayoutPrams;
    private int leftMenuWidth;
    private static boolean isLeftExpanded;
    private ImageView btn_menu, btn_ok;
    private RelativeLayout btn_waitingList, btn_pastWaitingList, btn_modify, btn_waitingClose, btn_setting, btn_manual;
    private TextView tv_storeName;
    private ImageView btn_logout;
    public String name;
    public int snum;

    private BackPressCloseHandler backPressCloseHandler; //뒤로가기 두번눌러종료


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        initSildeMenu();

        /* 뒤로가기 두번눌러종료 */
        backPressCloseHandler = new BackPressCloseHandler(this);

        /* 로그인하고 받은 snum,name 정의 */
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        snum = sharedPreferences.getInt("snum", 0);
        name = sharedPreferences.getString("name", "");
        tv_storeName = (TextView)findViewById(R.id.tv_storeName);
        tv_storeName.setText(name);

        btn_ok = (ImageView)findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"수정되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initSildeMenu() {

        // init left menu width
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        leftMenuWidth = (int) ((metrics.widthPixels) * 0.75);

        // init main view
        ll_mainLayout = (LinearLayout) findViewById(R.id.ll_mainlayout);
        mainLayoutPrams = (FrameLayout.LayoutParams) ll_mainLayout
                .getLayoutParams();
        mainLayoutPrams.width = metrics.widthPixels;
        ll_mainLayout.setLayoutParams(mainLayoutPrams);

        // init left menu
        ll_menuLayout = (LinearLayout) findViewById(R.id.ll_menuLayout);
        leftMenuLayoutPrams = (FrameLayout.LayoutParams) ll_menuLayout.getLayoutParams();
        leftMenuLayoutPrams.width = leftMenuWidth;
        ll_menuLayout.setLayoutParams(leftMenuLayoutPrams);
        ll_menuLayout.setVisibility(View.GONE);



        // init ui
        btn_menu = (ImageView) findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(this);


        btn_waitingList = (RelativeLayout) findViewById(R.id.btn_waitingList);
        btn_waitingList.setOnClickListener(this);

        btn_pastWaitingList = (RelativeLayout) findViewById(R.id.btn_pastWaitingList);
        btn_pastWaitingList.setOnClickListener(this);

        btn_modify = (RelativeLayout)findViewById(R.id.btn_modify);
        btn_modify.setOnClickListener(this);

        btn_waitingClose = (RelativeLayout) findViewById(R.id.btn_waitingClose);
        btn_waitingClose.setOnClickListener(this);

        btn_setting = (RelativeLayout) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(this);

        btn_manual = (RelativeLayout) findViewById(R.id.btn_manual);
        btn_manual.setOnClickListener(this);

        btn_logout = (ImageView) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);


    }

    /**
     * left menu toggle
     */
    private void menuLeftSlideAnimationToggle() {

        if (!isLeftExpanded) {

            isLeftExpanded = true;
            ll_menuLayout.setVisibility(View.VISIBLE);

            // Expand
            new ExpandAnimation(ll_mainLayout, leftMenuWidth,"left",
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
    public static void enableDisableViewGroup(ViewGroup viewGroup,boolean enabled) {
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

            case R.id.btn_waitingList: //대기팀 관리
                isLeftExpanded = false;
                finish();
                Intent intentToMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentToMain);
                break;

            case R.id.btn_pastWaitingList: //지난 대기 번호 목록
                Intent intentToPast = new Intent(getApplicationContext(), PastWaitingListActivity.class);
                isLeftExpanded = false;
                finish();
                startActivity(intentToPast);
                break;

            case R.id.btn_modify: //매장 정보 관리
                Intent intentToModify = new Intent(getApplicationContext(), ModifyActivity.class);
                isLeftExpanded = false;
                finish();
                startActivity(intentToModify);
                break;

            case R.id.btn_waitingClose: //대기번호 발급 마감
                Intent intentToWatingClose = new Intent(getApplicationContext(), CloseActivity.class);
                isLeftExpanded = false;
                finish();
                startActivity(intentToWatingClose);
                break;

            case R.id.btn_setting:  //대기번호 통계
                Intent intentToStats = new Intent(getApplicationContext(), StatsActivity.class);
                isLeftExpanded = false;
                finish();
                startActivity(intentToStats);
                break;

            case R.id.btn_manual:  //태그 등록 및 이용방법 메뉴
                Intent intentToManual = new Intent(getApplicationContext(), ManualActivity.class);
                isLeftExpanded = false;
                finish();
                startActivity(intentToManual);
                break;

            case R.id.btn_logout: //로그아웃
                Intent intentToLogin = new Intent(getApplicationContext(), LoginActivity.class);
                isLeftExpanded = false;
                finish();
                startActivity(intentToLogin);
                Toast.makeText(getApplicationContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if (isLeftExpanded){
            menuLeftSlideAnimationToggle();
        }else {
//            super.onBackPressed();
            backPressCloseHandler.onBackPressed();
        }
    }
}
