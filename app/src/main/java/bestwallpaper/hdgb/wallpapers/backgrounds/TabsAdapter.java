package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class TabsAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mData = new ArrayList<>();
    private final Context mContext;

    private final int[] tabIcons = {
            R.drawable.ic_latest_red,
            R.drawable.ic_popular_black,
    };

    public TabsAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);

        mContext = context;
        initData();
    }

    public ArrayList<Fragment> getData() {
        return mData;
    }

    private void initData() {
        mData.add(new LatestFragment());
        mData.add(new PopularFragment());
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    public View getTabView(int position) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null);
        ImageView tabIcon = v.findViewById(R.id.tabIcon);

        if (position == 0) {
            tabIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_latest_red));
        }
        if (position == 1) {
            tabIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_popular_black));
        }

        return v;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Latest";
        } else {
            return "Popular";
        }
    }
}


