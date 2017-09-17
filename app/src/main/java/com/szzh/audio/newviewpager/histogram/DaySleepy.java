package com.szzh.audio.newviewpager.histogram;

/**
 * Created by jzz
 * on 2017/9/12
 * <p>
 * desc:一天睡眠统计bean
 */

public class DaySleepy {

    private int id;//主键
    private int deepSleepCount;// 深睡时长统计   单位:min
    private int soberCount;//清醒时长统计        单位:min
    private int lightSleepCount;//浅睡时长统计   单位:min
    private int eogCount;//快速眼动时长统计       单位:min
    private int sleepCount;//睡眠总时长统计      单位:min
    private Today today;//睡眠数据统计日期  yyyy-mm-dd

    public int getId() {
        return id;
    }

    public DaySleepy setId(int id) {
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

    public int getSleepCount() {
        return sleepCount;
    }

    public DaySleepy setSleepCount(int sleepCount) {
        this.sleepCount = sleepCount;
        return this;
    }

    public Today getToday() {
        return today;
    }

    public DaySleepy setToday(Today today) {
        this.today = today;
        return this;
    }

    @Override
    public String toString() {
        return "DaySleepy{" +
                "id=" + id +
                ", deepSleepCount=" + deepSleepCount +
                ", soberCount=" + soberCount +
                ", lightSleepCount=" + lightSleepCount +
                ", eogCount=" + eogCount +
                ", sleepCount=" + sleepCount +
                ", today=" + today +
                '}';
    }
}
