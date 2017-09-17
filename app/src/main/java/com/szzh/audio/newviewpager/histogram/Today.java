package com.szzh.audio.newviewpager.histogram;

/**
 * Created by jzz
 * on 2017/9/15
 * <p>
 * desc:用来存储日期的 bean
 */

public class Today {

    private int year;
    private int month;
    private int date;

    public int getYear() {
        return year;
    }

    public Today setYear(int year) {
        this.year = year;
        return this;
    }

    public int getMonth() {
        return month;
    }

    public Today setMonth(int month) {
        this.month = month;
        return this;
    }

    public int getDate() {
        return date;
    }

    public Today setDate(int date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "Today{" +
                "year=" + year +
                ", month=" + month +
                ", date=" + date +
                '}';
    }
}
