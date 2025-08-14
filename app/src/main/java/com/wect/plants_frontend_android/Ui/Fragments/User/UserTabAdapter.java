package com.wect.plants_frontend_android.Ui.Fragments.User;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.wect.plants_frontend_android.Ui.Fragments.User.Fragments.CollectFragment;
import com.wect.plants_frontend_android.Ui.Fragments.User.Fragments.LikeFragment;
import com.wect.plants_frontend_android.Ui.Fragments.User.Fragments.RecommendFragment;
import com.wect.plants_frontend_android.Ui.Fragments.User.Fragments.WorkFragment;

public class UserTabAdapter extends FragmentStateAdapter {
    public UserTabAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new WorkFragment();
            case 1: return new RecommendFragment();
            case 2: return new CollectFragment();
            case 3: return new LikeFragment();
            default: return new WorkFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4; // tab数量
    }
}
