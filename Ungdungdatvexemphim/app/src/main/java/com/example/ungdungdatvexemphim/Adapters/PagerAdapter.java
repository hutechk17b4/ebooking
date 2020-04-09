package com.example.ungdungdatvexemphim.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.ungdungdatvexemphim.Fragments.NowPlayingFragment;
import com.example.ungdungdatvexemphim.Fragments.UpComingFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    // Bộ chuyển đổi trang
    int count;
    public PagerAdapter(FragmentManager fm, int oount) {
        super(fm);
        this.count = oount;
    }

    // Hàm lấy giá trị chuyển vị trí
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
                return nowPlayingFragment;
            case 1:
                UpComingFragment upcomingFragment = new UpComingFragment();
                return upcomingFragment;
            default:
                nowPlayingFragment = new NowPlayingFragment();
                return nowPlayingFragment;
        }
    }

    @Override
    public int getCount() {
        return count;
    }
}
