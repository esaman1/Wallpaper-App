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

public class SearchTabAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> mData = new ArrayList<>();
    private final Context mContext;

    private final int[] tabIcons = {
            R.drawable.ic_wallpaper_red,
            R.drawable.ic_collections_black,
            R.drawable.ic_photographer_black,
    };

    public SearchTabAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        mContext = context;
        initData();
    }

    public ArrayList<Fragment> getData() {
        return mData;
    }

    private void initData() {
        mData.add(new SearchWallpaerFragment());
        mData.add(new SearchCollectionsFragment());
        mData.add(new SearchPhotographerFragment());
    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mData.get(position);
    }

    public View getTabView(int position) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.search_tab, null);
        ImageView tabIcon = v.findViewById(R.id.tabIcon);

        if (position == 0) {
            tabIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_wallpaper_red));

        }
        if (position == 1) {
            tabIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_collections_black));
        }

        if (position == 2) {
            tabIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_photographer_black));
        }

        return v;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Wallpapers";
        }
        if (position == 1) {
            return "Collections";
        } else {
            return "Photographers";
        }
    }
}


