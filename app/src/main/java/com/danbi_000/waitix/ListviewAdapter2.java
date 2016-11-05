package com.danbi_000.waitix;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by danbi_000 on 2016-11-01.
 */

public class ListviewAdapter2 extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ListviewItem2> data;
    private int layout;

    public ListviewAdapter2 (Context context, int layout, ArrayList<ListviewItem2> data){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getTime();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(layout,parent,false);
        }

        String grayColor = "#666666";
        String redColor = "#dd3d3d";

        ListviewItem2 list_item_past_waiting = data.get(position);

        TextView waitingNum = (TextView)convertView.findViewById(R.id.txt_waitingNum);
        TextView numOfPeople = (TextView)convertView.findViewById(R.id.txt_numOfPeople);
        TextView status = (TextView) convertView.findViewById(R.id.txt_status);
        TextView time = (TextView)convertView.findViewById(R.id.txt_time);


        waitingNum.setText(String.format("%03d",list_item_past_waiting.getWaitingNum()));
        numOfPeople.setText(String.valueOf(list_item_past_waiting.getNumOfPeople()));
        time.setText(list_item_past_waiting.getTime());


        // Status
        // 앱 발급 0
        // 오프라인 발급 1
        // 취소 2
        if(list_item_past_waiting.getStatus()==0){
            status.setText("앱 발급");
            status.setTextColor(Color.parseColor(grayColor));
            waitingNum.setTextColor(Color.parseColor(grayColor));
            numOfPeople.setTextColor(Color.parseColor(grayColor));
            time.setTextColor(Color.parseColor(grayColor));
        }else if(list_item_past_waiting.getStatus()==1){
            status.setText("오프라인");
            status.setTextColor(Color.parseColor(grayColor));
            waitingNum.setTextColor(Color.parseColor(grayColor));
            numOfPeople.setTextColor(Color.parseColor(grayColor));
            time.setTextColor(Color.parseColor(grayColor));
        }else {
            status.setText("취소");
            status.setTextColor(Color.parseColor(redColor));
            waitingNum.setTextColor(Color.parseColor(redColor));
            numOfPeople.setTextColor(Color.parseColor(grayColor));
            time.setTextColor(Color.parseColor(redColor));
        }


        return convertView;
    }
}
