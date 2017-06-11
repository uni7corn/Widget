package com.szzh.audio.newviewpager.calender;

import java.util.Date;

/**
 * Created by jzz
 * on 2017/6/4.
 * <p>
 * desc:
 */

public class Calender {

    private Date date;
    private String day;
    private String week;//week
    private int dayOfWeek;//week
    private int type; //0x01 已被预约 0x02 已排班,未被预约过  0x03 已被约满
    private int count;
    private boolean isAfterDay;//是否是今天的前一天
    private boolean isClick; //是否选中

    public Date getDate() {
        return date;
    }

    public Calender setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getWeek() {
        return week;
    }

    public Calender setWeek(String week) {
        this.week = week;
        return this;
    }

    public String getDay() {
        return day;
    }

    public Calender setDay(String day) {
        this.day = day;
        return this;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public Calender setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public int getType() {
        return type;
    }

    public Calender setType(int type) {
        this.type = type;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Calender setCount(int count) {
        this.count = count;
        return this;
    }

    public boolean isClick() {
        return isClick;
    }

    public Calender setClick(boolean click) {
        isClick = click;
        return this;
    }

    public boolean isAfterDay() {
        return isAfterDay;
    }

    public void setAfterDay(boolean afterDay) {
        isAfterDay = afterDay;
    }

    @Override
    public String toString() {
        return "Calender{" +
                "date=" + date +
                ", day='" + day + '\'' +
                ", week='" + week + '\'' +
                ", dayOfWeek=" + dayOfWeek +
                ", type=" + type +
                ", count=" + count +
                ", isAfterDay=" + isAfterDay +
                ", isClick=" + isClick +
                '}';
    }
}
