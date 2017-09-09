package com.szzh.audio.newviewpager.sleepy;

import java.util.Date;

/**
 * Created by jzz
 * on 2017/6/4.
 * <p>
 * desc:
 */

public class SleepyDay {

    private Date date;
    private String day;
    private String week;//week
    private boolean isClick; //是否选中
    private int progress;//0 表示没有睡眠数据

    public Date getDate() {
        return date;
    }

    public SleepyDay setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getDay() {
        return day;
    }

    public SleepyDay setDay(String day) {
        this.day = day;
        return this;
    }

    public String getWeek() {
        return week;
    }

    public SleepyDay setWeek(String week) {
        this.week = week;
        return this;
    }

    public boolean isClick() {
        return isClick;
    }

    public SleepyDay setClick(boolean click) {
        isClick = click;
        return this;
    }

    public float getProgress() {
        return progress;
    }

    public SleepyDay setProgress(int progress) {
        this.progress = progress;
        return this;
    }

    @Override
    public String toString() {
        return "SleepyDay{" +
                "date=" + date +
                ", day='" + day + '\'' +
                ", week='" + week + '\'' +
                ", isClick=" + isClick +
                ", progress=" + progress +
                '}';
    }
}
