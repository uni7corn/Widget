package com.szzh.audio.newviewpager.schedule;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jzz
 * on 2017/4/12.
 *
 * desc:医生排班日历model
 */

public class Schedules implements Serializable {

    private String date;
    private String days;
    private List<Schedule> Schedules;
    private String weekType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public List<Schedule> getSchedules() {
        return Schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        Schedules = schedules;
    }

    public String getWeekType() {
        return weekType;
    }

    public void setWeekType(String weekType) {
        this.weekType = weekType;
    }

    @Override
    public String toString() {
        return "Schedules{" +
                "date='" + date + '\'' +
                ", days='" + days + '\'' +
                ", Schedules=" + Schedules +
                ", weekType='" + weekType + '\'' +
                '}';
    }

    public static class Schedule implements Serializable {
        private int bookType;
        private int timeType;

        public int getBookType() {
            return bookType;
        }

        public void setBookType(int bookType) {
            this.bookType = bookType;
        }

        public int getTimeType() {
            return timeType;
        }

        public void setTimeType(int timeType) {
            this.timeType = timeType;
        }

        @Override
        public String toString() {
            return "Schedule{" +
                    "bookType=" + bookType +
                    ", timeType=" + timeType +
                    '}';
        }
    }

}
