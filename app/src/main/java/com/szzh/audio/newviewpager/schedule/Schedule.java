package com.szzh.audio.newviewpager.schedule;

import android.support.annotation.NonNull;

/**
 * Created by jzz
 * on 2017/4/12.
 * <p>
 * desc:排班日历model
 */

public class Schedule implements Comparable<Schedule> {

    private String date;
    private String week;
    private long timeMills;
    private int isAm = -1;// 0 am 1 pm 2 both
    private int amBookType;//0没有排班信息 1.立即预约 2.全部约满(无号) 3.全部排班(该科室没有号,有可能多点执业有号)
    private int pmBookType;//0没有排班信息 1.立即预约 2.全部约满(无号) 3.全部排班(该科室没有号,有可能多点执业有号)

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public long getTimeMills() {
        return timeMills;
    }

    public void setTimeMills(long timeMills) {
        this.timeMills = timeMills;
    }

    public int getIsAm() {
        return isAm;
    }

    public void setIsAm(int isAm) {
        this.isAm = isAm;
    }

    public int getAmBookType() {
        return amBookType;
    }

    public void setAmBookType(int amBookType) {
        this.amBookType = amBookType;
    }

    public int getPmBookType() {
        return pmBookType;
    }

    public void setPmBookType(int pmBookType) {
        this.pmBookType = pmBookType;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "date='" + date + '\'' +
                ", week='" + week + '\'' +
                ", timeMills=" + timeMills +
                ", isAm=" + isAm +
                ", amBookType=" + amBookType +
                ", pmBookType=" + pmBookType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule schedule = (Schedule) o;

        if (timeMills != schedule.timeMills) return false;
        if (isAm != schedule.isAm) return false;
        if (amBookType != schedule.amBookType) return false;
        if (pmBookType != schedule.pmBookType) return false;
        if (!date.equals(schedule.date)) return false;
        return week.equals(schedule.week);

    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + week.hashCode();
        result = 31 * result + (int) (timeMills ^ (timeMills >>> 32));
        result = 31 * result + isAm;
        result = 31 * result + amBookType;
        result = 31 * result + pmBookType;
        return result;
    }

    @Override
    public int compareTo(@NonNull Schedule o) {
        return (int) (this.timeMills - o.timeMills);
    }
}
