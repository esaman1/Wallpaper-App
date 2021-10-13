package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    LinearLayout searchLayout;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    SearchTabAdapter tabsAdapter;
    SearchView search;
    ImageView back;
    SearchWallpaperAdapter adapter1;
    SearchListner mClickListener;
    CollectionListner collectionListner;
    PhotographerListner photographerListner;
    private FirebaseAnalytics mFirebaseAnalytics;

    public void updateApi(SearchListner listener) {
        mClickListener = listener;
    }

    public void updateApi1(CollectionListner listener) {
        collectionListner = listener;
    }

    public void updateApi2(PhotographerListner listener) {
        photographerListner = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.getSupportActionBar().hide();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getBaseContext());
        searchLayout = findViewById(R.id.searchLayout);
        search = findViewById(R.id.search_bar1);
        SearchView.SearchAutoComplete searchAutoComplete = search.findViewById(R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.GRAY);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        adapter1 = new SearchWallpaperAdapter(SearchActivity.this);
        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        tabsAdapter = new SearchTabAdapter(SearchActivity.this, getSupportFragmentManager());
        mViewPager.setAdapter(tabsAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            Objects.requireNonNull(tab).setCustomView(tabsAdapter.getTabView(i));
        }
        Objects.requireNonNull(mTabLayout.getTabAt(0)).select();
        mTabLayout.setTabIndicatorFullWidth(false);
        mTabLayout.selectTab(mTabLayout.getTabAt(0));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    FirebaseAnalytics("SearchActivity", "Search in", "View Wallpaper tab");
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_wallpaper_red));
                }
                if (tab.getPosition() == 1) {
                    FirebaseAnalytics("SearchActivity", "Search in", "View Collection tab");
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_collections_red));
                }
                if (tab.getPosition() == 2) {
                    FirebaseAnalytics("SearchActivity", "Search in", "View Photographer tab");
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_photographer_red));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_wallpaper_black));
                }
                if (tab.getPosition() == 1) {
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_collections_black));
                }
                if (tab.getPosition() == 2) {
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_photographer_black));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.setOffscreenPageLimit(3);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (mClickListener != null)
                    mClickListener.onBtnClick(query);
                if (collectionListner != null)
                    collectionListner.onBtnClick(query);
                if (photographerListner != null)
                    photographerListner.onBtnClick(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void FirebaseAnalytics(String s1, String s2, String s3) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, s1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, s2);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, s3);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Log.e("Search Activity", "Analitics stored");
    }

}