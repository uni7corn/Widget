package com.szzh.audio.newviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.szzh.audio.newviewpager.progress.VoiceProgress;

/**
 * Created by jzz
 * on 2017/4/12.
 * <p>
 * desc:
 */

public class TestActivity extends AppCompatActivity {

    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final VoiceProgress progressBar = (VoiceProgress) findViewById(R.id.viewPager);

        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: ----->");
                if (v.getTag() == null) {
                    progressBar.play();
                    v.setTag(true);
                } else {
                    progressBar.stop();
                    v.setTag(null);
                }
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getTag() == null) {
                    progressBar.play();
                    v.setTag(true);
                } else {
                    progressBar.stop();
                    v.setTag(null);
                }


            }
        });
    }
}
