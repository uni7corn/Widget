package com.szzh.audio.newviewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.szzh.audio.newviewpager.progress.LoadingProgress;

/**
 * Created by jzz
 * on 2017/4/12.
 *
 * desc:
 */

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LoadingProgress progressBar = (LoadingProgress) findViewById(R.id.viewPager);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.cancel();
            }
        });
    }
}
