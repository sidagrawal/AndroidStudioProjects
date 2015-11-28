package com.phplogin.sidag.myapplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Siddhant on 10/26/2015.
 */
public class TimeStamp {

    int year;
    int month;
    int day;
    int time;

    public TimeStamp(String timestamp){
        String[] date_time = timestamp.split(" ");
        String[] date = date_time[0].split("-");
        this.year = Integer.parseInt(date[0]);
        this.month = Integer.parseInt(date[1]);
        this.day = Integer.parseInt(date[2]);
        String[] tim = date_time[1].split(":");
        this.time = Integer.parseInt(tim[0])*60*60 + Integer.parseInt(tim[1])*60 + Integer.parseInt(tim[2]);
    }

    public boolean newerThan(TimeStamp time_stamp){
        return getTotalSeconds() >= time_stamp.getTotalSeconds();
    }

    public int getTotalSeconds(){
        return year*365*24*60*60 + month*30*24*60*60 + day*24*60*60;
    }

    public String toString(){
        int hour = time/3600;
        int minute = time%3600/60;
        int seconds = time%3600%60;
        return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day)
                + " " + Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(seconds);
    }

    public void updateTimestamp(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String timestamp = sdf.format(c.getTime());
        String[] date_time = timestamp.split(" ");
        String[] date = date_time[0].split("-");
        this.year = Integer.parseInt(date[0]);
        this.month = Integer.parseInt(date[1]);
        this.day = Integer.parseInt(date[2]);
        String[] tim = date_time[1].split(":");
        this.time = Integer.parseInt(tim[0])*60*60 + Integer.parseInt(tim[1])*60 + Integer.parseInt(tim[2]);
    }
}
