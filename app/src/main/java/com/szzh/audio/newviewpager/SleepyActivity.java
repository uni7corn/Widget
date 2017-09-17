package com.szzh.audio.newviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.szzh.audio.newviewpager.histogram.DaySleepy;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sleep);
        mWeekSleepHistogramView = (WeekSleepHistogramView) findViewById(R.id.sleepy_data_view);

        List<DaySleepy> daySleepies = initWeekData();

        List<DaySleepy> sleepies = daySleepies.subList(0, 9);

        Log.e("TAG", "onCreate: ------------>" + sleepies.size());

        mWeekSleepHistogramView.addSleepData(sleepies);
        //initDayData();
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
        // mDaySleepHistogramView.setData(mSleepDataList);
    }


    private List<DaySleepy> initWeekData() {

        List<DaySleepy> daySleepies = new ArrayList<>();

        DaySleepy daySleepy;

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/1");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/2");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/3");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/4");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/5");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/6");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/7");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/8");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/9");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(3)
                .setEogCount(4)
                .setLightSleepCount(5)
                .setDeepSleepCount(5)
                .setDate("9/10");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(3)
                .setEogCount(2)
                .setLightSleepCount(5)
                .setDeepSleepCount(2)
                .setDate("9/11");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(0)
                .setSoberCount(0)
                .setEogCount(0)
                .setLightSleepCount(0)
                .setDeepSleepCount(0)
                .setDate("9/12");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(3)
                .setEogCount(2)
                .setLightSleepCount(3)
                .setDeepSleepCount(5)
                .setDate("9/13");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(3)
                .setEogCount(4)
                .setLightSleepCount(5)
                .setDeepSleepCount(2)
                .setDate("9/14");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(3)
                .setEogCount(3)
                .setLightSleepCount(3)
                .setDeepSleepCount(2)
                .setDate("9/15");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/16");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/17");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/18");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/19");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/20");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/21");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/22");

        daySleepies.add(daySleepy);

        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/23");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/24");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/25");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/26");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/27");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/28");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/29");

        daySleepies.add(daySleepy);


        daySleepy = new DaySleepy()
                .setId(1)
                .setSoberCount(2)
                .setEogCount(3)
                .setLightSleepCount(5)
                .setDeepSleepCount(4)
                .setDate("9/30");

        daySleepies.add(daySleepy);

        return daySleepies;
    }


}
