package com.szzh.audio.newviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.szzh.audio.newviewpager.histogram.DaySleepHistogramView;
import com.szzh.audio.newviewpager.histogram.DaySleepy;
import com.szzh.audio.newviewpager.histogram.Today;
import com.szzh.audio.newviewpager.histogram.WeekSleepHistogramView;
import com.szzh.audio.newviewpager.sleepy.bean.SleepData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jzz
 * on 2017/9/9
 * <p>
 * desc:
 */

public class SleepyActivity extends AppCompatActivity {

    private WeekSleepHistogramView mWeekSleepHistogramView;
    private List<SleepData> mSleepDataList;

    private DaySleepHistogramView mDaySleepHistogramView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sleep);
        this.mDaySleepHistogramView = (DaySleepHistogramView) findViewById(R.id.day_sleep_view);
        initDayData();

        this.mWeekSleepHistogramView = (WeekSleepHistogramView) findViewById(R.id.sleepy_data_view);

        List<DaySleepy> daySleepies = initWeekData();

        List<DaySleepy> sleepies = daySleepies.subList(0, 30);


        mWeekSleepHistogramView.addSleepData(sleepies);
    }

    private void initDayData() {
        mSleepDataList = new ArrayList<>();
        SleepData sleepData1 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(2120)
                .setToTime(2130)
                .setTimeQuantum(10)
                .setState(1);
        mSleepDataList.add(sleepData1);

        SleepData sleepData2 = new SleepData()
                .setId(2)
                .setUid("123")
                .setIndex(2)
                .setIndexCount(10)
                .setFromTime(2140)
                .setToTime(2230)
                .setTimeQuantum(50)
                .setState(2);
        mSleepDataList.add(sleepData2);

        SleepData sleepData3 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(2230)
                .setToTime(2250)
                .setTimeQuantum(20)
                .setState(2);
        mSleepDataList.add(sleepData3);


        SleepData sleepData4 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(2250)
                .setToTime(2300)
                .setTimeQuantum(10)
                .setState(2);
        mSleepDataList.add(sleepData4);


        SleepData sleepData5 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(2300)
                .setToTime(2350)
                .setTimeQuantum(50)
                .setState(2);
        mSleepDataList.add(sleepData5);


        SleepData sleepData6 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(0)
                .setToTime(20)
                .setTimeQuantum(20)
                .setState(3);
        mSleepDataList.add(sleepData6);


        SleepData sleepData7 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(120)
                .setToTime(220)
                .setTimeQuantum(60)
                .setState(3);
        mSleepDataList.add(sleepData7);


        SleepData sleepData8 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(220)
                .setToTime(340)
                .setTimeQuantum(80)
                .setState(3);
        mSleepDataList.add(sleepData8);


        SleepData sleepData9 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(340)
                .setToTime(400)
                .setTimeQuantum(20)
                .setState(2);
        mSleepDataList.add(sleepData9);


        SleepData sleepData10 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(410)
                .setToTime(450)
                .setTimeQuantum(40)
                .setState(2);
        mSleepDataList.add(sleepData10);


        SleepData sleepData11 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(500)
                .setToTime(520)
                .setTimeQuantum(20)
                .setState(1);
        mSleepDataList.add(sleepData11);


        SleepData sleepData12 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(520)
                .setToTime(600)
                .setTimeQuantum(40)
                .setState(2);
        mSleepDataList.add(sleepData12);


        SleepData sleepData13 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(650)
                .setToTime(750)
                .setTimeQuantum(60)
                .setState(2);
        mSleepDataList.add(sleepData13);

        SleepData sleepData14 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(750)
                .setToTime(810)
                .setTimeQuantum(20)
                .setState(3);
        mSleepDataList.add(sleepData14);

        SleepData sleepData15 = new SleepData()
                .setId(1)
                .setUid("123")
                .setIndex(1)
                .setIndexCount(10)
                .setFromTime(820)
                .setToTime(900)
                .setTimeQuantum(40)
                .setState(2);
        mSleepDataList.add(sleepData15);
        // }

        //Collections.sort(mSleepDataList);
        mDaySleepHistogramView.setData(mSleepDataList);
    }


    private List<DaySleepy> initWeekData() {

        List<DaySleepy> daySleepies = new ArrayList<>();

        DaySleepy daySleepy;

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(60)
                .setEogCount(3)
                .setLightSleepCount(60)
                .setDeepSleepCount(60)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(1));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(200)
                .setEogCount(20)
                .setLightSleepCount(400)
                .setDeepSleepCount(300)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(2));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(100)
                .setEogCount(3)
                .setLightSleepCount(60)
                .setDeepSleepCount(400)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(3));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(200)
                .setEogCount(3)
                .setLightSleepCount(200)
                .setDeepSleepCount(200)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(4));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(100)
                .setEogCount(3)
                .setLightSleepCount(300)
                .setDeepSleepCount(500)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(5));

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(500)
                .setEogCount(3)
                .setLightSleepCount(500)
                .setDeepSleepCount(200)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(6));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(100)
                .setEogCount(400)
                .setLightSleepCount(400)
                .setDeepSleepCount(400)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(7));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(200)
                .setEogCount(3)
                .setLightSleepCount(60)
                .setDeepSleepCount(40)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(8));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(500)
                .setEogCount(3)
                .setLightSleepCount(200)
                .setDeepSleepCount(400)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(9));

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(400)
                .setEogCount(4)
                .setLightSleepCount(500)
                .setDeepSleepCount(500)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(10));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(100)
                .setEogCount(2)
                .setLightSleepCount(100)
                .setDeepSleepCount(400)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(11));

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(0)
                .setSoberCount(400)
                .setEogCount(0)
                .setLightSleepCount(400)
                .setDeepSleepCount(500)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(12));

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(300)
                .setEogCount(2)
                .setLightSleepCount(300)
                .setDeepSleepCount(500)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(13));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(300)
                .setEogCount(4)
                .setLightSleepCount(500)
                .setDeepSleepCount(200)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(14));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(300)
                .setEogCount(3)
                .setLightSleepCount(500)
                .setDeepSleepCount(200)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(15));

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(0)
                .setEogCount(0)
                .setLightSleepCount(0)
                .setDeepSleepCount(0)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(16));

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(400)
                .setEogCount(3)
                .setLightSleepCount(500)
                .setDeepSleepCount(400)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(17));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(200)
                .setEogCount(3)
                .setLightSleepCount(550)
                .setDeepSleepCount(340)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(18));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(52)
                .setEogCount(322)
                .setLightSleepCount(245)
                .setDeepSleepCount(429)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(19));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(210)
                .setEogCount(3)
                .setLightSleepCount(50)
                .setDeepSleepCount(300)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(20));

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(242)
                .setEogCount(3)
                .setLightSleepCount(123)
                .setDeepSleepCount(421)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(21));

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(291)
                .setEogCount(3)
                .setLightSleepCount(501)
                .setDeepSleepCount(234)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(22));

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(201)
                .setEogCount(3)
                .setLightSleepCount(205)
                .setDeepSleepCount(401)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(23));

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(24));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(211)
                .setEogCount(3)
                .setLightSleepCount(521)
                .setDeepSleepCount(42)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(25));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(233)
                .setEogCount(332)
                .setLightSleepCount(25)
                .setDeepSleepCount(408)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(26));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(211)
                .setEogCount(3)
                .setLightSleepCount(235)
                .setDeepSleepCount(224)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(27));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(21)
                .setEogCount(3)
                .setLightSleepCount(59)
                .setDeepSleepCount(401)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(28));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(222)
                .setEogCount(3)
                .setLightSleepCount(35)
                .setDeepSleepCount(422)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(29));

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(21)
                .setEogCount(3)
                .setLightSleepCount(25)
                .setDeepSleepCount(422)
                .setToday(new Today().setYear(2017).setMonth(9).setDate(30));

        daySleepies.add(daySleepy);

        return daySleepies;
    }


}
