package com.danbi_000.waitix;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class PastWaitingListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_waiting_list);

        int waitingNum=1;

        /* 리스트에 데이터 넣기 */
        ListView listView = (ListView) findViewById(R.id.listView_past_waiting);
        ArrayList<ListviewItem2> data = new ArrayList<>();
        ListviewItem2 sample1 = new ListviewItem2(waitingNum,"2016.03.25 11:54:26", 4, 0);
        ListviewItem2 sample2 = new ListviewItem2(++waitingNum,"2016.03.25 11:54:26", 2, 1);
        data.add(sample1);
        data.add(sample2);

        ListviewAdapter2 adapter = new ListviewAdapter2(this, R.layout.list_item_past_waiting, data);
        listView.setAdapter(adapter);
    }
}
