package com.thirteen.andriod_imitationzhihu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.thirteen.andriod_imitationzhihu.R;
import com.thirteen.andriod_imitationzhihu.adapter.BottomFragmentPagerAdapter;
import com.thirteen.andriod_imitationzhihu.adapter.IndexFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class IndexFragment extends Fragment {
    private ViewPager viewPager;
    private IndexFragmentPagerAdapter indexFragmentPagerAdapter;
    private TabLayout tabLayout;
    private TabLayout.Tab index_Nex;
    private TabLayout.Tab index_Rec;
    private TabLayout.Tab index_Hot;
    private List<Fragment> fragments;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager_index);

        fragments = new ArrayList<Fragment>();
        fragments.add(new IndexNewFragment());
        fragments.add(new IndexRecFragment());
        fragments.add(new IndexHotFrament());
        indexFragmentPagerAdapter = new IndexFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(indexFragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

        index_Nex = tabLayout.getTabAt(0);
        index_Rec = tabLayout.getTabAt(1);
        index_Hot = tabLayout.getTabAt(2);

        return view;
    }
}
