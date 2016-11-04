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
    public String getTime(){
        return time;
    }
    public int getNumOfPeople(){
        return numOfPeople;
    }
    public int getStatus(){
        return status;
    }

    public ListviewItem2(int waitingNum, String time, int numOfPeople, int status){
        this.waitingNum = waitingNum;
        this.time = time;
        this.numOfPeople = numOfPeople;
        this.status = status;
    }
}
