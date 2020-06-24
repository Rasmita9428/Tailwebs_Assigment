package com.rasmitap.tailwebs_assigment.Views.activity;
import com.rasmitap.tailwebs_assigment.R;
import com.rasmitap.tailwebs_assigment.Views.fragment.Slider1Fragment;
import com.rasmitap.tailwebs_assigment.Views.fragment.Slider2Fragment;
import com.rasmitap.tailwebs_assigment.Views.fragment.Slider3Fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class WelcomeActivity extends AppCompatActivity {
    Slider1Fragment fragmentTutorial1;
    Slider2Fragment fragmentTutorial2;
    Slider3Fragment fragmentTutorial3;

    private FragmentPagerAdapter adapter;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.


    TextView btn_continue;
    ImageView img_slider;
    ViewPager viewpager;
    CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        img_slider = findViewById(R.id.img_slider);
        btn_continue = findViewById(R.id.btn_continue);
        viewpager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circleIndicator);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),lOGINActivity.class);
                startActivity(intent);
                finish();

            }
        });


        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(0);
        viewpager.setSaveFromParentEnabled(false);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 5 - 1) {
                    currentPage = 0;
                }
                viewpager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        circleIndicator.setViewPager(viewpager);
        img_slider.setImageResource(R.drawable.slider1);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private static final float thresholdOffset = 0.5f;
            private boolean scrollStarted, checkDirection;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int lastIndex = adapter.getCount() - 1;

                int currentPage = viewpager.getCurrentItem();

                if (checkDirection) {
                    if (thresholdOffset > positionOffset && lastIndex == currentPage) {

                        Log.i("Left", "going left");
                    } else {

                        Log.i("Right", "going right");
                    }
                    checkDirection = false;
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("get position", position + "");
                if (position == 1) {
                    img_slider.setImageResource(R.drawable.slider1);
                } else if (position == 2) {
                    img_slider.setImageResource(R.drawable.slider2);
                }else {
                    img_slider.setImageResource(R.drawable.slider3);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (!scrollStarted && state == ViewPager.SCROLL_STATE_DRAGGING) {
                    scrollStarted = true;
                    checkDirection = true;

                } else {
                    scrollStarted = false;
                }

            }
        });

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment f = new Fragment();

            switch (position) {
                case 0:
                    fragmentTutorial1 = new Slider1Fragment();
                    f = fragmentTutorial1;
                    break;
                case 1:
                    fragmentTutorial2 = new Slider2Fragment();
                    f = fragmentTutorial2;
                    break;

                case 2:
                    fragmentTutorial3 = new Slider3Fragment();
                    f = fragmentTutorial3;
                    break;
            }
            return f;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


}
