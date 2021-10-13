package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.onesignal.OneSignal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import bestwallpaper.hdgb.wallpapers.backgrounds.Service.MyService;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String ONESIGNAL_APP_ID = "8f5eca34-9600-4f5f-8fa9-f5d60c198013";
    private final ArrayList<String> menuItems = new ArrayList<>();
    private final ArrayList<Integer> menuimages = new ArrayList<>();
    public LinearLayout adViewLinear;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    TabsAdapter tabsAdapter;
    ImageView search;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean setAutoWallpaper = false;
    String currentTheme = "Light";
    Intent serviceIntent;
    ConnectionDetector cd;
    boolean isInternetPresent;
    int intentcase;
    AlertDialog.Builder alertadd;
    View nativeView;
    private ImageView img_menu;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ListViewUtil nav_list;
    private AdView adView;
    private InterstitialAd interstitialAd;
    private NativeAd nativeAd;
    private NativeAdLayout nativeAdLayout;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        AndroidNetworking.initialize(MainActivity.this);
        getDeviceID();
        cd = new ConnectionDetector(MainActivity.this);
        adViewLinear = findViewById(R.id.ad_viewLinear);

        adViewLinear.setVisibility(View.VISIBLE);
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    adView = new AdView(MainActivity.this);
                    adView.setAdUnitId(getString(R.string.banner_ad_unit_id));
                    LoadAdaptiveBanner(MainActivity.this, adView);
                    adViewLinear.addView(adView);
                }
            }, 2000);
        }

        loadAd();


        sharedPreferences = getSharedPreferences("Auto_pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        currentTheme = sharedPreferences.getString("Theme", "Light");
        if (currentTheme.equals("Light")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentTheme.equals("Dark")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }

//
        initNavigationView();

        search = findViewById(R.id.search_bar1);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("MainActivity", "Search", "View Search Page");
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        tabsAdapter = new TabsAdapter(MainActivity.this, getSupportFragmentManager());
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
                    FirebaseAnalytics("MainActivity", "View", "Latest Tab");
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_latest_red));
                }
                if (tab.getPosition() == 1) {
                    FirebaseAnalytics("MainActivity", "View", "Popular Tab");
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_popular_red));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_latest_black));
                }
                if (tab.getPosition() == 1) {
                    ImageView tv = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.tabIcon);
                    tv.setImageDrawable(getResources().getDrawable(R.drawable.ic_popular_black));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.setOffscreenPageLimit(0);

        setAutoWallpaper = sharedPreferences.getBoolean("Auto_Set", false);

        Log.e("MainActivity Auto_Set:", String.valueOf(setAutoWallpaper));
        serviceIntent = new Intent(this, MyService.class);
        sharedPreferences = getSharedPreferences("Auto_pref", MODE_PRIVATE);
        if (setAutoWallpaper) {
            startService();
        } else {
            stopService();
        }

//        throw new RuntimeException("Test Crash"); // Force a crash
    }

    private void getDeviceID() {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String token = null;
                AdvertisingIdClient.Info adInfo = null;
                try {
                    adInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                } catch (IOException e) {
// ...
                } catch (GooglePlayServicesRepairableException e) {
// ...
                } catch (GooglePlayServicesNotAvailableException e) {
// ...
                }
                String android_id = adInfo.getId();
                Log.d("LLLLL_DeviceID", android_id);

                return android_id;
            }

            @Override
            protected void onPostExecute(String token) {
                Log.i("LLLLL_DeviceID", "DEVICE_ID Access token retrieved:" + token);
            }

        };
        task.execute();
    }


    private void loadNativeAd() {

        // initializing nativeAd object
        nativeAd = new NativeAd(this, "4285067941545449_4285071931545050");

        // creating  NativeAdListener
        NativeAdListener nativeAdListener = new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // showing Toast message
                Log.e("Native error:", adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };

        // Load an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL)
                        .build());
    }

    public void loadAd() {

        interstitialAd = new InterstitialAd(this, "4285067941545449_4285069684878608");
        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                FirebaseAnalytics("MainActivity", "Interstitial Ad", "Display");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

                traverse(intentcase);
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                FirebaseAnalytics("MainActivity", "Interstitial Ad", "Error");

                Log.e("Error", "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                FirebaseAnalytics("MainActivity", "Interstitial Ad", "Load");
                Log.d("TAG", "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad

            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    public void LoadAdaptiveBanner(Activity context, final AdView adView) {
        AdRequest adRequest = new AdRequest.Builder().build();

        AdSize adSize = getAdSize(context);
// Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize);

        adView.loadAd(adRequest);

        adView.setVisibility(View.GONE);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                adView.setVisibility(View.VISIBLE);
            }
        });
    }

    private AdSize getAdSize(Activity context) {
// Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

// Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }


    @Override
    protected void onDestroy() {
        Log.e("MainActivity :", "onDestroy called");
//        stopService();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadNativeAd();
    }

    public void startService() {
        Log.e("SettingsActivity :", "startService called");
        if (!MyService.isServiceRunning) {
            ContextCompat.startForegroundService(this, serviceIntent);
            startService(serviceIntent);
        }

    }

    public void stopService() {
        Log.e("MainActivity :", "stopService called");

        if (MyService.isServiceRunning) {
            stopService(serviceIntent);
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            alertadd = new AlertDialog.Builder(MainActivity.this);
            LayoutInflater factory = LayoutInflater.from(MainActivity.this);
            nativeView = factory.inflate(R.layout.exit_popup, null);

            alertadd.setView(nativeView);
            alertadd.setMessage("Are you sure you want to exit?");
            alertadd.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finishAffinity();
                }
            });
            alertadd.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            if (nativeAd != null && nativeAd.isAdLoaded())
                inflateAd(nativeAd);
            alertadd.show();
        }
    }

    void inflateAd(NativeAd nativeAd) {

        // unregister the native Ad View
        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = nativeView.findViewById(R.id.native_ad_container);

        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

        // Inflate the Ad view.
        LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.ad_unified, nativeAdLayout, false);

        // adding view
        nativeAdLayout.addView(adView);

        // Add the AdOptionsView
        LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(MainActivity.this, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

        // Setting  the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and  button to listen for clicks.
        nativeAd.registerViewForInteraction(adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    private void initNavigationView() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        nav_list = findViewById(R.id.nav_list);


        img_menu = findViewById(R.id.navigate);
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        menuItems.add("Wallpapers");
        menuItems.add("Collections");
        menuItems.add("Favourites");
        menuItems.add("Settings");

        menuimages.add(R.drawable.ic_menu1_red);
        menuimages.add(R.drawable.ic_menu2_grey);
        menuimages.add(R.drawable.ic_menu3_grey);
        menuimages.add(R.drawable.ic_menu4_grey);

        nav_list.setAdapter(new DrawerAdapter(this, menuItems, menuimages));
        nav_list.setOnItemClickListener((parent, view, position, id) -> {
            drawerLayout.closeDrawers();

            if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
                traverse(position);
                return;
            }
            // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
            if (interstitialAd.isAdInvalidated()) {
                traverse(position);
                return;
            }
            // Show the ad
            interstitialAd.show();

            switch (position) {
                case 0:
                    intentcase = 0;
                    break;
                case 1:
                    intentcase = 1;
                    break;
                case 2:
                    intentcase = 2;
                    break;
                case 3:
                    intentcase = 3;
                    break;
            }


            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    public void FirebaseAnalytics(String s1, String s2, String s3) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, s1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, s2);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, s3);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Log.e("Main Activity", "Analitics stored");
    }

    public void traverse(int pos) {
        switch (pos) {
            case 0:
                FirebaseAnalytics("MainActivity", "View", "Main page");
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case 1:
                FirebaseAnalytics("MainActivity", "View", "Collections page");
                Intent videoIntent = new Intent(MainActivity.this, CollectionActivity.class);
                startActivity(videoIntent);
                break;

            case 2:
                FirebaseAnalytics("MainActivity", "View", "Favourites page");
                Intent audioIntent = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(audioIntent);
                break;
            case 3:
                FirebaseAnalytics("MainActivity", "View", "Settings page");
                Intent documentIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(documentIntent);
                break;
        }
    }

}