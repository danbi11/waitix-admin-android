package com.danbi_000.waitix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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

        ListviewItem list_item_waiting = data.get(position);

        ImageView call = (ImageView)convertView.findViewById(R.id.img_call);
        call.setImageResource(list_item_waiting.getCall());

        TextView waitingNum = (TextView)convertView.findViewById(R.id.txt_waitingNum);
        waitingNum.setText(String.format("%03d",list_item_waiting.getWaitingNum()));
//        waitingNum.setText(String.valueOf(list_item_waiting.getWaitingNum()));

        TextView time = (TextView)convertView.findViewById(R.id.txt_time);
        time.setText(list_item_waiting.getTime());

        TextView numOfPeople = (TextView)convertView.findViewById(R.id.txt_numOfPeople);
        numOfPeople.setText(String.valueOf(list_item_waiting.getNumOfPeople()));

        return convertView;
    }
}
