package com.danbi_000.waitix;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by danbi_000 on 2016-07-26.
 */
public class ListviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ListviewItem> data;
    private int layout;

    public ListviewAdapter (Context context, int layout, ArrayList<ListviewItem> data){
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount(){return data.size();}

    @Override
    public String getItem(int position){return data.get(position).getTime();}

    @Override
    public long getItemId(int position){return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = inflater.inflate(layout,parent,false);
        }

        String grayColor = "#666666";
        String blueColor = "#348eea";
        String orangeColor = "#E0844F";

        ListviewItem list_item_waiting = data.get(position);

        ImageView call = (ImageView)convertView.findViewById(R.id.img_call);
        TextView waitingNum = (TextView)convertView.findViewById(R.id.txt_waitingNum);
        TextView time = (TextView)convertView.findViewById(R.id.txt_time);
        TextView numOfPeople = (TextView)convertView.findViewById(R.id.txt_numOfPeople);

        waitingNum.setText(String.format("%03d",list_item_waiting.getWaitingNum()));
        time.setText(list_item_waiting.getTime());
        numOfPeople.setText(String.valueOf(list_item_waiting.getNumOfPeople()));


        // Call
        // 대기중인팀 0
        // 호출 전송된 팀 1
        // 오프라인 대기중인 팀 2
        if(list_item_waiting.getCall()==0){
            call.setImageResource(R.drawable.store_main_albtn);
            waitingNum.setTextColor(Color.parseColor(grayColor));
            time.setTextColor(Color.parseColor(grayColor));
            numOfPeople.setTextColor(Color.parseColor(grayColor));
        }else if (list_item_waiting.getCall()==1){
            call.setImageResource(R.drawable.store_main_entbtn);
            waitingNum.setTextColor(Color.parseColor(blueColor));
            time.setTextColor(Color.parseColor(blueColor));
            numOfPeople.setTextColor(Color.parseColor(blueColor));
        }else{
            call.setImageResource(R.drawable.store_main_offentbtn);
            waitingNum.setTextColor(Color.parseColor(orangeColor));
            time.setTextColor(Color.parseColor(orangeColor));
            numOfPeople.setTextColor(Color.parseColor(orangeColor));
        }


        return convertView;
    }
}
