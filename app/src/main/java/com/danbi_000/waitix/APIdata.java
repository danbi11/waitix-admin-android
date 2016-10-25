package com.danbi_000.waitix;

import com.google.gson.annotations.SerializedName;

/**
 * Created by danbi_000 on 2016-10-25.
 */

public class APIdata {

    public class signup{
        @SerializedName("storeid")
        private String storeid;

        @SerializedName("name")
        private String name;

        @SerializedName("address")
        private String address;

        @SerializedName("tel")
        private String tel;

        @SerializedName("text")
        private String text;

        @SerializedName("imgsrc")
        private String imgsrc;

        @SerializedName("alarmtime")
        private String alarmtime;

        @SerializedName("password")
        private String password;

        @SerializedName("waittime")
        private String waittime;

        public String getStoreid(){return storeid;}
        public String getName(){return name;}
        public String getAddress(){return address;}
        public String getTel(){return tel;}
        public String getText(){return text;}
        public String getImgsrc(){return imgsrc;}
        public String getAlarmtime(){return alarmtime;}
        public String getPassword(){return password;}
        public String getWaittime(){return waittime;}
    }
    public class signup{
        @SerializedName("storeid")
        private String storeid;
}
