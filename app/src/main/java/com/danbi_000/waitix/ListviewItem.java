package com.danbi_000.waitix;

/**
 * Created by danbi_000 on 2016-07-26.
 */
public class ListviewItem {
    private int waitingNum;
    private String time;
    private int numOfPeople;
    private int call;

    public int getWaitingNum() {
        return waitingNum;
    }
    public String getTime(){
        return time;
    }
    public int getNumOfPeople(){
        return numOfPeople;
    }
    public int getCall(){
        return call;
    }

    public ListviewItem(int waitingNum, String time, int numOfPeople, int call){
        this.waitingNum = waitingNum;
        this.time = time;
        this.numOfPeople = numOfPeople;
        this.call = call;
    }
}
