package com.danbi_000.waitix;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.danbi_000.waitix.anim.CloseAnimation;
import com.danbi_000.waitix.anim.ExpandAnimation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity implements View.OnClickListener {

    /* slide menu */
    private DisplayMetrics metrics;
    private LinearLayout ll_mainLayout;
    private LinearLayout ll_menuLayout;
    private FrameLayout.LayoutParams mainLayoutPrams;
    private FrameLayout.LayoutParams leftMenuLayoutPrams;
    private int leftMenuWidth;
    private static boolean isLeftExpanded;
    private ImageView btn_menu, btn_refresh, btn_offline;
    private TextView btn_waitList, btn_storeManager, btn_setting;

    public int waitingNum=1;

    /* NFC */
    private final String MIMETYPE = "application/neonlight88.com.nfctools";
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSildeMenu();

        /* NFC */
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        //안드로이드빔 nfc 연결되어있는지 체크
        if(Tools.checkNFC(nfcAdapter)) {
            intentHandler(getIntent());
        } else {
            Tools.displayToast(this, "This device doesn't support NFC or it is disabled.");
        }


        long now = System.currentTimeMillis(); //현재시간 msec로 구한다
        Date date = new Date(now);  //현재시간을 저장
        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm:SS");
        String strNow = sdfNow.format(date);

        ListView listView = (ListView) findViewById(R.id.listView_waiting);

        ArrayList<ListviewItem> data = new ArrayList<>();
        ListviewItem sample1 = new ListviewItem(waitingNum,strNow,4,R.drawable.bell01);
        ListviewItem sample2 = new ListviewItem(++waitingNum,"13:24 AM",2,R.drawable.bell01);
            data.add(sample1);
            data.add(sample2);



        ////////////////////////////////////////////////////////////////////////////////////////

        btn_refresh= (ImageView)findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"새로고침 되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });
        btn_offline= (ImageView)findViewById(R.id.btn_offline);
        btn_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ListviewItem sample3 = new ListviewItem(++waitingNum,"13:24 AM",2,R.drawable.bell01);
//                data.add(sample3);
                Toast.makeText(getApplicationContext(),"오프라인 번호를 발급하였습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        ListviewAdapter adapter = new
                ListviewAdapter(this, R.layout.list_item_waiting, data);
        listView.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        intentHandler(getIntent());
    }

    private void intentHandler(Intent intent) {
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())
                && intent.getType().equals(MIMETYPE)) {
            Parcelable[] messagesRaw = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage ndefMessage = (NdefMessage) messagesRaw[0];
            if(ndefMessage != null) {
                Tools.displayToast(
                        getApplicationContext(),
                        new String(ndefMessage.getRecords()[0].getPayload())
                );
            }
        }
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


        // init ui
        btn_menu = (ImageView) findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(this);

        btn_setting = (TextView) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(this);


        btn_storeManager = (TextView)findViewById(R.id.btn_storeManager);
        btn_storeManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        SignupActivity.class); // 다음 넘어갈 클래스 지정

                isLeftExpanded = false;
                menuLeftSlideAnimationToggle();
                finish();
                startActivity(intent); // 다음 화면으로 넘어간다


            }
        });

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
            case R.id.btn_setting:
                Intent intent = new Intent(
                        getApplicationContext(), // 현재 화면의 제어권자
                        SettingActivity.class); // 다음 넘어갈 클래스 지정

                isLeftExpanded = false;
                menuLeftSlideAnimationToggle();
                finish();
                startActivity(intent); // 다음 화면으로 넘어간다
                break;

        }

    }
}
