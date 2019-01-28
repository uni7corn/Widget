package com.szzh.audio.newviewpager.sleepy.bean;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by jzz
 * on 2017/9/4
 * <p>
 * desc:睡眠数据
 */

public class SleepData implements Serializable, Comparable<SleepData> {

    private long id;//主键
    private String uid;//用户 uid
    private int index;//睡眠数据下标(表明是第几条数据)
    private int indexCount;//数据总量
    private int fromTime;//数据收集开始时间点 hh:mm
    private int toTime;//数据收集结束时间点   hh:mm
    private int timeQuantum;//数据持续时间(单位为 min)
    private int state;//该条数据状态  0.没有数据 1.清醒 2.深睡 3.浅睡
    private int date;//该条数据的日期  2017-08-31   yyyy-mm-dd

    public long getId() {
        return id;
    }

    public SleepData setId(long id) {
        this.id = id;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public SleepData setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public SleepData setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getIndexCount() {
        return indexCount;
    }

    public SleepData setIndexCount(int indexCount) {
        this.indexCount = indexCount;
        return this;
    }

    public int getFromTime() {
        return fromTime;
    }

    public SleepData setFromTime(int fromTime) {
        this.fromTime = fromTime;
        return this;
    }

    public int getToTime() {
        return toTime;
    }

    public SleepData setToTime(int toTime) {
        this.toTime = toTime;
        return this;
    }

    public int getTimeQuantum() {
        return timeQuantum;
    }

    public SleepData setTimeQuantum(int timeQuantum) {
        this.timeQuantum = timeQuantum;
        return this;
    }

    public int getState() {
        return state;
    }

    public SleepData setState(int state) {
        this.state = state;
        return this;
    }

    public int getDate() {
        return date;
    }

    public SleepData setDate(int date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SleepData)) return false;

        SleepData sleepData = (SleepData) o;

        if (getId() != sleepData.getId()) return false;
        if (getIndex() != sleepData.getIndex()) return false;
        if (getIndexCount() != sleepData.getIndexCount()) return false;
        if (getFromTime() != sleepData.getFromTime()) return false;
        if (getToTime() != sleepData.getToTime()) return false;
        if (getTimeQuantum() != sleepData.getTimeQuantum()) return false;
        if (getState() != sleepData.getState()) return false;
        if (getDate() != sleepData.getDate()) return false;
        return getUid() != null ? getUid().equals(sleepData.getUid()) : sleepData.getUid() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getUid() != null ? getUid().hashCode() : 0);
        result = 31 * result + getIndex();
        result = 31 * result + getIndexCount();
        result = 31 * result + getFromTime();
        result = 31 * result + getToTime();
        result = 31 * result + getTimeQuantum();
        result = 31 * result + getState();
        result = 31 * result + getDate();
        return result;
    }


    @Override
    public String toString() {
        return "SleepData{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", index=" + index +
                ", indexCount=" + indexCount +
                ", fromTime=" + fromTime +
                ", toTime=" + toTime +
                ", timeQuantum=" + timeQuantum +
                ", state=" + state +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(@NonNull SleepData o) {
        return this.getFromTime() - o.getFromTime();
    }
}
