package com.thirteen.andriod_imitationzhihu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thirteen.andriod_imitationzhihu.adapter.BottomFragmentPagerAdapter;
import com.thirteen.andriod_imitationzhihu.fragment.IndexFragment;
import com.thirteen.andriod_imitationzhihu.fragment.MessageFragment;
import com.thirteen.andriod_imitationzhihu.fragment.PersonalFragment;
import com.thirteen.andriod_imitationzhihu.fragment.QuestionFragment;
import com.thirteen.andriod_imitationzhihu.fragment.VipShopFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomFragmentPagerAdapter bottomFragmentPagerAdapter;
    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;
    private List<Fragment> fragments;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        fragments = new ArrayList<Fragment>();
        fragments.add(new IndexFragment());
        fragments.add(new VipShopFragment());
        fragments.add(new QuestionFragment());
        fragments.add(new MessageFragment());
        fragments.add(new PersonalFragment());
        bottomFragmentPagerAdapter = new BottomFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(bottomFragmentPagerAdapter);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.index:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.vip:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.question:
                        viewPager.setCurrentItem(2, false);
                        break;
                    case R.id.message:
                        viewPager.setCurrentItem(3, false);
                        break;
                    case R.id.personal:
                        viewPager.setCurrentItem(4, false);
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null){
                    menuItem.setChecked(false);
                }else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
