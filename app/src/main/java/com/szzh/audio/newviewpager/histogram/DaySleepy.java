package com.szzh.audio.newviewpager.histogram;

/**
 * Created by jzz
 * on 2017/9/12
 * <p>
 * desc:一天睡眠统计bean
 */

public class DaySleepy {

    private int id;//主键
    private int deepSleepCount;// 深睡时长统计   单位:小时
    private int soberCount;//清醒时长统计        单位:小时
    private int lightSleepCount;//浅睡时长统计   单位:小时
    private int eogCount;//快速眼动时长统计       单位:小时
    private String date;//睡眠数据统计日期

    int getId() {
        return id;
    }

    DaySleepy setId(int id) {
        this.id = id;
        return this;
    }

    public int getDeepSleepCount() {
        return deepSleepCount;
    }

    public DaySleepy setDeepSleepCount(int deepSleepCount) {
        this.deepSleepCount = deepSleepCount;
        return this;
    }

    public int getSoberCount() {
        return soberCount;
    }

    public DaySleepy setSoberCount(int soberCount) {
        this.soberCount = soberCount;
        return this;
    }

    public int getLightSleepCount() {
        return lightSleepCount;
    }

    public DaySleepy setLightSleepCount(int lightSleepCount) {
        this.lightSleepCount = lightSleepCount;
        return this;
    }

    public int getEogCount() {
        return eogCount;
    }

    public DaySleepy setEogCount(int eogCount) {
        this.eogCount = eogCount;
        return this;
    }

    public String getDate() {
        return date;
    }

    public DaySleepy setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DaySleepy)) return false;

        DaySleepy daySleepy = (DaySleepy) o;

        if (getId() != daySleepy.getId()) return false;
        if (getDeepSleepCount() != daySleepy.getDeepSleepCount()) return false;
        if (getSoberCount() != daySleepy.getSoberCount()) return false;
        if (getLightSleepCount() != daySleepy.getLightSleepCount()) return false;
        if (getEogCount() != daySleepy.getEogCount()) return false;
        return getDate() != null ? getDate().equals(daySleepy.getDate()) : daySleepy.getDate() == null;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getDeepSleepCount();
        result = 31 * result + getSoberCount();
        result = 31 * result + getLightSleepCount();
        result = 31 * result + getEogCount();
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DaySleepy{" +
                "id=" + id +
                ", deepSleepCount=" + deepSleepCount +
                ", soberCount=" + soberCount +
                ", lightSleepCount=" + lightSleepCount +
                ", eogCount=" + eogCount +
                ", date='" + date + '\'' +
                '}';
    }
}
