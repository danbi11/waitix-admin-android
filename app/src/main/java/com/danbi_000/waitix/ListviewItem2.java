package com.danbi_000.waitix;

/**
 * Created by danbi_000 on 2016-11-01.
 */
public class ListviewItem2 {
    private int waitingNum;
    private String time;
    private int numOfPeople;
    private int status;

    public int getWaitingNum() {
        return waitingNum;
    }
    public int getNumOfPeople(){
        return numOfPeople;
    }
    public int getStatus(){
        return status;
    }
    public String getTime(){
        return time;
    }

    public ListviewItem2(int waitingNum, int numOfPeople,  int status, String time){
        this.waitingNum = waitingNum;
        this.numOfPeople = numOfPeople;
        this.status = status;
        this.time = time;
    }
}
