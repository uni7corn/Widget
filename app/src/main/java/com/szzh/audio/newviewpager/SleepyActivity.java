package com.szzh.audio.newviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

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

    // private DaySleepHistogramView mDaySleepHistogramView;
    private List<SleepData> mSleepDataList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sleep);
        //mDaySleepHistogramView = (DaySleepHistogramView) findViewById(R.id.sleepy_data_view);
        initData();
    }

    private void initData() {
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


}
