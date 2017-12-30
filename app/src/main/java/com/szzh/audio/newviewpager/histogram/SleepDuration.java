package com.szzh.audio.newviewpager.histogram;

/**
 * Created by jzz
 * on 2017/10/10.
 * desc:图表一天相关睡眠时长统计
 */

public class SleepDuration {

    private int sleep_duration;//图表一天睡眠时长  单位:sec
    private int awake_duration;//图表一天清醒时长  单位:sec
    private int deep_duration;//图表一天深睡时长   单位:sec
    private int light_duration;//图表一天浅睡时长  单位:sec
    private int eog_duration;//图标一天眼动时长    单位:sec

    public int getSleep_duration() {
        return sleep_duration;
    }

    public void setSleep_duration(int sleep_duration) {
        this.sleep_duration = sleep_duration;
    }

    public int getAwake_duration() {
        return awake_duration;
    }

    public void setAwake_duration(int awake_duration) {
        this.awake_duration = awake_duration;
    }

    public int getDeep_duration() {
        return deep_duration;
    }

    public void setDeep_duration(int deep_duration) {
        this.deep_duration = deep_duration;
    }

    public int getLight_duration() {
        return light_duration;
    }

    public void setLight_duration(int light_duration) {
        this.light_duration = light_duration;
    }

    public int getEog_duration() {
        return eog_duration;
    }

    public void setEog_duration(int eog_duration) {
        this.eog_duration = eog_duration;
    }

    @Override
    public String toString() {
        return "SleepDuration{" +
            "sleep_duration=" + sleep_duration +
            ", awake_duration=" + awake_duration +
            ", deep_duration=" + deep_duration +
            ", light_duration=" + light_duration +
            ", eog_duration=" + eog_duration +
            '}';
    }
}
