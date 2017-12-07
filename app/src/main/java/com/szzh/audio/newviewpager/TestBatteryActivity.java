package com.szzh.audio.newviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.szzh.audio.newviewpager.battery.BatteryView;

/**
 * Created by jzz
 * on 2017/12/3.
 * <p>
 * desc:
 */

public class TestBatteryActivity extends Activity {

    private BatteryView mBatteryView;

    private float power = 10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_battery);

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                power++;
                mBatteryView.setPower(power);
            }
        });
        this.mBatteryView = (BatteryView) findViewById(R.id.battery);

        //this.mBatteryView.setPower(power);
    }
}
