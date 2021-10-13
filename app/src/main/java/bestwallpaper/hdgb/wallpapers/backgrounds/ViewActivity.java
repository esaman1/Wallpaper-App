package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubfilter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList.LatestModel;

import static bestwallpaper.hdgb.wallpapers.backgrounds.CollectionWallpaperAdapter.fromCollection;
import static bestwallpaper.hdgb.wallpapers.backgrounds.ProfileAdapter.fromProfile;
import static bestwallpaper.hdgb.wallpapers.backgrounds.SearchWallpaperAdapter.fromSearchWallpaper;

public class ViewActivity extends AppCompatActivity {

    private static final double[] DELTA_INDEX = {
            0, 0.01, 0.02, 0.04, 0.05, 0.06, 0.07, 0.08, 0.1, 0.11,
            0.12, 0.14, 0.15, 0.16, 0.17, 0.18, 0.20, 0.21, 0.22, 0.24,
            0.25, 0.27, 0.28, 0.30, 0.32, 0.34, 0.36, 0.38, 0.40, 0.42,
            0.44, 0.46, 0.48, 0.5, 0.53, 0.56, 0.59, 0.62, 0.65, 0.68,
            0.71, 0.74, 0.77, 0.80, 0.83, 0.86, 0.89, 0.92, 0.95, 0.98,
            1.0, 1.06, 1.12, 1.18, 1.24, 1.30, 1.36, 1.42, 1.48, 1.54,
            1.60, 1.66, 1.72, 1.78, 1.84, 1.90, 1.96, 2.0, 2.12, 2.25,
            2.37, 2.50, 2.62, 2.75, 2.87, 3.0, 3.2, 3.4, 3.6, 3.8,
            4.0, 4.3, 4.7, 4.9, 5.0, 5.5, 6.0, 6.5, 6.8, 7.0,
            7.3, 7.5, 7.8, 8.0, 8.4, 8.7, 9.0, 9.4, 9.6, 9.8,
            10.0
    };
    public static Boolean shapeColorBoolean;
    public static ArrayList<LatestModel> favList;
    public static int currentShape;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    private final float mScaleFactor = 1.0f;
    LatestAdapter latestAdapter;
    LatestFragment latestFragment;
    PopularAdapter popularAdapter;
    PopularFragment popularFragment;
    SearchWallpaperAdapter searchWallpaperAdapter;
    SearchWallpaerFragment searchWallpaerFragment;
    ProfileAdapter profileAdapter;
    ProfileActivity profileActivity;
    ImageView setWallpaper;
    TextView home, lock, both;
    ImageView vector15, vector14, vector13, vector12, vector11;
    CardView cardView1, cardView2, cardView3;
    ImageView origininalIMG;
    ImageView resize, rotate, opacity, color, shape, delete;
    RecyclerView colorRecycler, colorRecycler1;
    LinearLayout layout;
    int position = 0;
    LatestModel latestModel;
    TextView profileName, site;
    ImageView back, profileImage;
    CardView cardImg;
    LinearLayoutManager layoutManager;
    ColorAdapter colorAdapter;
    ArrayList<Integer> colorList;
    Boolean dayNightBoolean, blurBoolean, vintageBoolean, colorBoolean, contrastBoolean;
    int dayNightProgress, blurProgress, vintageProgress, colorProgress, contrastProgress;
    Bitmap constantBitmap, shapeBitmap;
    SeekBar seekBarBrightness, seekBarBlur, seekbarVignette, seekbarColor, seekbarContrast;
    SeekBar seekBarSize, seekbarRotate, seekbarOpacity;
    Boolean resizeBoolean, rotateBoolean, opacityBoolean;
    int resizeProgress, rotateProgress, opacityProgress;
    ImageView shapeImgView;
    ImageView vector4, vector3, vector2, vector1, gallery;
    RelativeLayout rootRL, rootLayout, blankLayout;
    HorizontalScrollView scrollView;
    int shapeCount = 0;
    int currentShapeID;
    int REQUEST_PERMISSION = 786;
    Boolean from = false;
    LatestModel popularModel;
    Gson gson = new Gson();
    String json1;
    Boolean exist = false;
    int pos;
    String ImageFrom, ApplyTo;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences1;
    LinearLayout newLayout;
    private float xCoOrdinate, yCoOrdinate;
    private AdView adView;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ScaleGestureDetector scaleGestureDetector;


    public static PorterDuffColorFilter setBrightness(int progress) {
//        Log.e("Brightness Progress :",String.valueOf(progress));
        if (progress >= 50) {
            int value = (progress - 50) * 255 / 70;
            return new PorterDuffColorFilter(Color.argb(value, 255, 255, 255), PorterDuff.Mode.OVERLAY);
        } else {
            int value = (100 - progress) * 255 / 130;
            return new PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.OVERLAY);
        }
    }

    public static ColorFilter adjustContrast(int value) {
        ColorMatrix cm = new ColorMatrix();

        adjustContrast(cm, value);

        return new ColorMatrixColorFilter(cm);
    }

    public static void adjustContrast(ColorMatrix cm, int value) {
        value = (int) cleanValue(value, 100);
        if (value == 0) {
            return;
        }
        float x;
        if (value < 0) {
            x = 127 + value / 100 * 127;
        } else {
            x = value % 1;
            if (x == 0) {
                x = (float) DELTA_INDEX[value];
            } else {
                //x = DELTA_INDEX[(p_val<<0)]; // this is how the IDE does it.
                x = (float) DELTA_INDEX[(value << 0)] * (1 - x) + (float) DELTA_INDEX[(value << 0) + 1] * x; // use linear interpolation for more granularity.
            }
            x = x * 127 + 127;
        }

        float[] mat = new float[]
                {
                        x / 127, 0, 0, 0, 0.5f * (127 - x),
                        0, x / 127, 0, 0, 0.5f * (127 - x),
                        0, 0, x / 127, 0, 0.5f * (127 - x),
                        0, 0, 0, 1, 0,
                        0, 0, 0, 0, 1
                };
        cm.postConcat(new ColorMatrix(mat));

    }

    protected static float cleanValue(float p_val, float p_limit) {
        return Math.min(p_limit, Math.max(-p_limit, p_val));
    }

    public void wallpaperDialog() {

        AlertDialog.Builder alertadd = new AlertDialog.Builder(ViewActivity.this);
        LayoutInflater factory = LayoutInflater.from(ViewActivity.this);
        final View view = factory.inflate(R.layout.wallpaper_popup, null);
        alertadd.setView(view);

        home = view.findViewById(R.id.home);
        lock = view.findViewById(R.id.lock);
        both = view.findViewById(R.id.both);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Set", "Set Wallpaper to home");
                ApplyTo = "Home";
                home.setTextColor(getResources().getColor(R.color.teal_700));
                lock.setTextColor(getResources().getColor(R.color.black));
                both.setTextColor(getResources().getColor(R.color.black));
            }
        });

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Set", "Set Wallpaper to lock");
                ApplyTo = "Lock";
                home.setTextColor(getResources().getColor(R.color.black));
                lock.setTextColor(getResources().getColor(R.color.teal_700));
                both.setTextColor(getResources().getColor(R.color.black));
            }
        });

        both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Set", "Set Wallpaper to Both");
                ApplyTo = "Both";
                home.setTextColor(getResources().getColor(R.color.black));
                lock.setTextColor(getResources().getColor(R.color.black));
                both.setTextColor(getResources().getColor(R.color.teal_700));
            }
        });
        alertadd.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                setWallpaper(ApplyTo);
            }
        });
        alertadd.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertadd.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        this.getSupportActionBar().hide();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getBaseContext());
        init();
//        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
//        origininalIMG.setOnTouchListener(onImageTouched());
        Resources res = getResources();
        adView = new com.facebook.ads.AdView(this, res.getString(R.string.facebook_banner_ad), AdSize.BANNER_HEIGHT_50);

        LinearLayout adContainer = findViewById(R.id.banner_container);
        adContainer.addView(adView);


        loadAd();

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        colorRecycler.setLayoutManager(layoutManager);
        colorList = initColorList();
        setDefaultShape();
        ColorAdapter.ColorClickListener listener = new ColorAdapter.ColorClickListener() {
            @Override
            public void onColorClick(int position) {
                FirebaseAnalytics("ViewActivity", "Set", "Set color to Wallpaper");
//                Log.e("Position 1:" + String.valueOf(position),"Color : " + colorList.get(position).toString());
                blankLayout.setBackgroundColor(getResources().getColor(colorList.get(position)));

            }
        };

        ColorAdapter.ShapeColorClickListener listener1 = new ColorAdapter.ShapeColorClickListener() {
            @Override
            public void onColorClick(int position) {
                FirebaseAnalytics("ViewActivity", "Set", "Set color to shape");
//                Log.e("Position 2:" + String.valueOf(position),"Color : " + colorList.get(position).toString());
                shapeImgView = findViewById(currentShapeID);
                storeShape();
                shapeImgView.setColorFilter(ContextCompat.getColor(getApplicationContext(), colorList.get(position)));
            }
        };
        colorAdapter = new ColorAdapter(listener, listener1, colorList, ViewActivity.this);
        colorRecycler.setAdapter(colorAdapter);
        colorRecycler1.setAdapter(colorAdapter);


        if (getIntent().getStringExtra("From").equals("Latest")) {
            setDefault();
            position = getIntent().getIntExtra("Latest_pos", position);
            latestModel = LatestAdapter.latestModels.get(position);
            profileName.setText(latestModel.getUser().getName());
            try {
//                Picasso.with(ViewActivity.this).load(latestModel.getUrls().getRegular()).into(origininalIMG);
                Picasso.Builder builder = new Picasso.Builder(this);
                builder.listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        cardView1.setVisibility(View.GONE);
                        cardView2.setVisibility(View.GONE);
                        cardView3.setVisibility(View.GONE);
                        setWallpaper.setVisibility(View.GONE);
                        Toast.makeText(ViewActivity.this, "Ooops !!! Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.build().load(latestModel.getUrls().getRegular()).into(origininalIMG);
                ImageFrom = "Latest";
                Picasso.with(ViewActivity.this).load(latestModel.getUser().getProfileImage().getMedium()).into(profileImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (getIntent().getStringExtra("From").equals("Popular")) {
            setDefault();
            position = getIntent().getIntExtra("Popular_pos", position);
            popularModel = PopularAdapter.popularModels.get(position);
            profileName.setText(popularModel.getUser().getName());
            try {
//                Picasso.with(ViewActivity.this).load(popularModel.getUrls().getRegular()).into(origininalIMG);
                Picasso.Builder builder = new Picasso.Builder(this);
                builder.listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        cardView1.setVisibility(View.GONE);
                        cardView2.setVisibility(View.GONE);
                        cardView3.setVisibility(View.GONE);
                        setWallpaper.setVisibility(View.GONE);
                        Toast.makeText(ViewActivity.this, "Ooops !!! Something went wrong.", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.build().load(popularModel.getUrls().getRegular()).into(origininalIMG);
                ImageFrom = "Popular";
                Picasso.with(ViewActivity.this).load(popularModel.getUser().getProfileImage().getMedium()).into(profileImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (getIntent().getStringExtra("From").equals("Favourite")) {
            setDefault();
            popularModel = (LatestModel) getIntent().getSerializableExtra("fav_pos");
            profileName.setText(popularModel.getUser().getName());
            position = getIntent().getIntExtra("position", position);
            try {
//                Picasso.with(ViewActivity.this).load(popularModel.getUrls().getRegular()).into(origininalIMG);
                Picasso.Builder builder = new Picasso.Builder(this);
                builder.listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        cardView1.setVisibility(View.GONE);
                        cardView2.setVisibility(View.GONE);
                        cardView3.setVisibility(View.GONE);
                        setWallpaper.setVisibility(View.GONE);
                        Toast.makeText(ViewActivity.this, "Ooops !!! Something went wrong.", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.build().load(popularModel.getUrls().getRegular()).into(origininalIMG);
                ImageFrom = "Popular";
                Picasso.with(ViewActivity.this).load(popularModel.getUser().getProfileImage().getMedium()).into(profileImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (getIntent().getStringExtra("From").equals("Popular_new") || getIntent().getStringExtra("From").equals("Latest_new")) {
            from = true;
            rootLayout.setVisibility(View.GONE);
            blankLayout.setVisibility(View.VISIBLE);
            shapeColorBoolean = false;
            scrollView.setEnabled(false);
            blankLayout.setBackgroundColor(getResources().getColor(R.color.black));
            cardImg.setVisibility(View.GONE);
            profileName.setVisibility(View.GONE);
            site.setVisibility(View.GONE);
            colorRecycler.setVisibility(View.VISIBLE);
            vector4.setVisibility(View.GONE);

            vector1.setVisibility(View.GONE);
            newLayout.setWeightSum(3);
            gallery.setVisibility(View.VISIBLE);
        }


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Open gallery", "Open gallery");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Open Profile", "Open Profile");
                Intent in = new Intent(ViewActivity.this, ProfileActivity.class);
                if (getIntent().getStringExtra("From").equals("Latest")) {
                    in.putExtra("Latest", latestModel);
                } else {
                    in.putExtra("Popular", popularModel);
                }
                startActivity(in);
            }
        });

        vector1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                json1 = sharedPreferences.getString("fav_list", "");
                Type type1 = new TypeToken<ArrayList<LatestModel>>() {
                }.getType();
                favList = gson.fromJson(json1, type1);

                if (favList == null) {
                    favList = new ArrayList<>();
                }

                if (ImageFrom.equals("Latest")) {
                    if (favList.size() > 0) {
                        for (int i = 0; i < favList.size(); i++) {
                            if (favList.get(i).getId().equals(LatestAdapter.latestModels.get(position).getId())) {
                                exist = true;
                                pos = i;
                                break;
                            } else {
                                exist = false;
                            }
                        }
                    }
                    if (!exist) {
                        FirebaseAnalytics("ViewActivity", "Wallpaper", "Add Wallpaper to favourites");
                        favList.add(LatestAdapter.latestModels.get(position));
                        vector1.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector__12_));
                    } else {
                        FirebaseAnalytics("ViewActivity", "Wallpaper", "Remove Wallpaper to favourites");
                        favList.remove(pos);
                        vector1.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_1));
                        exist = false;
                    }
                } else {
                    if (fromCollection) {
                        if (favList.size() > 0) {
                            for (int i = 0; i < favList.size(); i++) {
                                if (favList.get(i).getId().equals(CollectionWallpaperAdapter.resultData.get(position).getId())) {
                                    exist = true;
                                    pos = i;
                                    break;
                                } else {
                                    exist = false;
                                }
                            }
                        }
                        if (!exist) {
                            FirebaseAnalytics("ViewActivity", "Wallpaper", "Add Wallpaper to favourites");
                            favList.add(CollectionWallpaperAdapter.resultData.get(position));
                            vector1.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector__12_));
                        } else {
                            FirebaseAnalytics("ViewActivity", "Wallpaper", "Remove Wallpaper to favourites");
                            favList.remove(pos);
                            vector1.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_1));
                            exist = false;
                        }
                        fromCollection = false;
                    } else if (fromProfile) {
                        if (favList.size() > 0) {
                            for (int i = 0; i < favList.size(); i++) {
                                if (favList.get(i).getId().equals(ProfileAdapter.profileData.get(position).getId())) {
                                    exist = true;
                                    pos = i;
                                    break;
                                } else {
                                    exist = false;
                                }
                            }
                        }
                        if (!exist) {
                            FirebaseAnalytics("ViewActivity", "Wallpaper", "Add Wallpaper to favourites");
                            favList.add(ProfileAdapter.profileData.get(position));
                            vector1.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector__12_));
                        } else {
                            FirebaseAnalytics("ViewActivity", "Wallpaper", "Remove Wallpaper to favourites");
                            favList.remove(pos);
                            vector1.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_1));
                            exist = false;
                        }
                        fromProfile = false;
                    } else if (fromSearchWallpaper) {
                        if (favList.size() > 0) {
                            for (int i = 0; i < favList.size(); i++) {
                                if (favList.get(i).getId().equals(SearchWallpaperAdapter.wallpaperModels.get(position).getId())) {
                                    exist = true;
                                    pos = i;
                                    break;
                                } else {
                                    exist = false;
                                }
                            }
                        }
                        if (!exist) {
                            FirebaseAnalytics("ViewActivity", "Wallpaper", "Add Wallpaper to favourites");
                            favList.add(SearchWallpaperAdapter.wallpaperModels.get(position));
                            vector1.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector__12_));
                        } else {
                            FirebaseAnalytics("ViewActivity", "Wallpaper", "remove Wallpaper to favourites");
                            favList.remove(pos);
                            vector1.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_1));
                            exist = false;
                        }
                        fromSearchWallpaper = false;
                    } else {
                        if (favList.size() > 0) {
                            for (int i = 0; i < favList.size(); i++) {
                                if (favList.get(i).getId().equals(PopularAdapter.popularModels.get(position).getId())) {
                                    exist = true;
                                    pos = i;
                                    break;
                                } else {
                                    exist = false;
                                }
                            }
                        }
                        if (!exist) {
                            FirebaseAnalytics("ViewActivity", "Wallpaper", "Add Wallpaper to favourites");
                            favList.add(PopularAdapter.popularModels.get(position));
                            vector1.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector__12_));
                        } else {
                            FirebaseAnalytics("ViewActivity", "Wallpaper", "Remove Wallpaper to favourites");
                            favList.remove(pos);
                            vector1.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_1));
                            exist = false;
                        }
                    }
                }

                Log.e("Fav_list :", favList.toString());
                Log.e("Fav_list size :", String.valueOf(favList.size()));

                json1 = gson.toJson(favList);
                editor.putString("fav_list", json1);
                editor.apply();
                latestFragment = new LatestFragment();
                latestAdapter = new LatestAdapter(ViewActivity.this);
                popularFragment = new PopularFragment();
                popularAdapter = new PopularAdapter(ViewActivity.this);
                searchWallpaerFragment = new SearchWallpaerFragment();
                searchWallpaperAdapter = new SearchWallpaperAdapter(ViewActivity.this);
                profileActivity = new ProfileActivity();
                profileAdapter = new ProfileAdapter(ViewActivity.this);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        latestAdapter.notifyDataSetChanged();
                        LatestFragment.recyclerView.setAdapter(latestAdapter);
                        popularAdapter.notifyDataSetChanged();
                        PopularFragment.recyclerView.setAdapter(popularAdapter);
                        if (SearchWallpaerFragment.recyclerView != null) {
                            searchWallpaperAdapter.notifyDataSetChanged();
                            SearchWallpaerFragment.recyclerView.setAdapter(searchWallpaperAdapter);
                        }
                        if (ProfileActivity.recyclerView != null) {
                            profileAdapter.notifyDataSetChanged();
                            ProfileActivity.recyclerView.setAdapter(profileAdapter);
                        }
                    }
                });

//                profileActivity=new ProfileActivity();
//                profileAdapter=new ProfileAdapter(ViewActivity.this);
//                profileAdapter.notifyDataSetChanged();
//                profileActivity.recyclerView.setAdapter(profileAdapter);

            }
        });

        vector2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission ", "Already Granted");
                    FirebaseAnalytics("ViewActivity", "Download", "Download Wallpaper ");
                    downloadWallpaper();
                } else {
                    requestPermission();
                }
            }
        });

        vector3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Shape", "Add shape to Wallpaper ");
                cardView1.setVisibility(View.GONE);
                cardView2.setVisibility(View.GONE);
                cardView3.setVisibility(View.VISIBLE);
                ImageView imageView = new ImageView(ViewActivity.this);
                imageView.setId(shapeCount);
                imageView.setImageResource(currentShape);
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT));
                if (from) {
                    blankLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));
                    blankLayout.addView(imageView);
                } else {
                    rootLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT));
                    rootLayout.addView(imageView);
                }
                setContentView(rootRL);
                shapeCount++;
                imageView.getLayoutParams().width = 500;
                imageView.getLayoutParams().height = 500;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500, 500);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                imageView.setLayoutParams(params);
//                Log.e("ID :",String.valueOf(imageView.getId()));
                imageView.setOnTouchListener(onShapeTouched());
                imageView.setOnClickListener(onShapeClicked());
//                Log.e("Alpha :",String.valueOf(imageView.getImageAlpha()));
            }
        });

        vector4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView1.setVisibility(View.GONE);
                cardView2.setVisibility(View.VISIBLE);
                cardView3.setVisibility(View.GONE);
            }
        });

        vector11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Set", "Brightness");
                //Day Night Brightness Effect
                vector11();
                storeOriginal();
                seekBarBrightness.setMax(100);
                seekBarBrightness.setProgress(dayNightProgress);
            }
        });

        vector12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Set", "Blur effect");
                vector12();
                storeOriginal();
                seekBarBlur.setMax(25);
                seekBarBlur.setProgress(blurProgress);
            }
        });

        vector13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Set", "Vintage effect");
                vector13();
                storeOriginal();
                seekbarVignette.setMax(255);
                seekbarVignette.setProgress(vintageProgress);
            }
        });

        vector14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Set", "Color effect");
                vector14();
                storeOriginal();
                seekbarColor.setMax(255);
                seekbarColor.setProgress(colorProgress);
            }
        });

        vector15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Set", "Contrast effect");
                vector15();
                storeOriginal();
                seekbarContrast.setMax(100);
                seekbarContrast.setProgress(contrastProgress);
            }
        });


        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallpaperDialog();
            }
        });

        resize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Adjust", "Shape Size");
                resizeTheme();
            }
        });

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Shape", "Shape rotation");
                rotateTheme();
            }
        });

        opacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Shape", "Shape opacity");
                opacityTheme();
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "Shape", "Shape color");
                colorTheme();
                storeShape();
            }
        });

        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ViewActivity", "View", "All Shapes");
                shapeTheme();
                storeShape();
                Intent in = new Intent(ViewActivity.this, ShapesActivity.class);
                startActivity(in);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ViewActivity.this, "Log press to delete.", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FirebaseAnalytics("ViewActivity", "Shape", "Shape delete");
//                Log.e("Count :" ,String.valueOf(shapeCount));

                shapeImgView = findViewById(currentShapeID);
                if (shapeImgView != null) {
                    rootLayout.removeView(shapeImgView);
                    setContentView(rootRL);
                    shapeCount--;
                    cardView1.setVisibility(View.VISIBLE);
                    cardView2.setVisibility(View.GONE);
                    cardView3.setVisibility(View.GONE);
                }
//                Log.e("Count :" ,String.valueOf(shapeCount));
                if (shapeCount == 0) {
                    cardView1.setVisibility(View.VISIBLE);
                    cardView2.setVisibility(View.GONE);
                    cardView3.setVisibility(View.GONE);
                }
                return true;
            }
        });

        blankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shapeColorBoolean = false;
            }
        });

        seekBarBrightness.setOnSeekBarChangeListener(onSeekBarChanged());
        seekBarBlur.setOnSeekBarChangeListener(onSeekBarChanged());
        seekbarVignette.setOnSeekBarChangeListener(onSeekBarChanged());
        seekbarColor.setOnSeekBarChangeListener(onSeekBarChanged());
        seekbarContrast.setOnSeekBarChangeListener(onSeekBarChanged());

        seekBarSize.setOnSeekBarChangeListener(onShapeSeekBarChanged());
        seekbarRotate.setOnSeekBarChangeListener(onShapeSeekBarChanged());
        seekbarOpacity.setOnSeekBarChangeListener(onShapeSeekBarChanged());

    }

    public void loadAd() {
        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("Error :", adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };

        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setWallpaper(String applyTo) {
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        Bitmap bitmap;
        if (applyTo.equals("Lock")) {
            if (from) {
                blankLayout.setDrawingCacheEnabled(true);
                bitmap = Bitmap.createBitmap(blankLayout.getDrawingCache());
                blankLayout.setDrawingCacheEnabled(false);
                try {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                rootLayout.setDrawingCacheEnabled(true);
                bitmap = Bitmap.createBitmap(rootLayout.getDrawingCache());
                rootLayout.setDrawingCacheEnabled(false);
                try {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (applyTo.equals("Home")) {
            if (from) {
                blankLayout.setDrawingCacheEnabled(true);
                bitmap = Bitmap.createBitmap(blankLayout.getDrawingCache());
                blankLayout.setDrawingCacheEnabled(false);
                try {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                rootLayout.setDrawingCacheEnabled(true);
                bitmap = Bitmap.createBitmap(rootLayout.getDrawingCache());
                rootLayout.setDrawingCacheEnabled(false);
                try {
                    wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (from) {
                blankLayout.setDrawingCacheEnabled(true);
                bitmap = Bitmap.createBitmap(blankLayout.getDrawingCache());
                blankLayout.setDrawingCacheEnabled(false);
                try {
                    wallpaperManager.setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                rootLayout.setDrawingCacheEnabled(true);
                bitmap = Bitmap.createBitmap(rootLayout.getDrawingCache());
                rootLayout.setDrawingCacheEnabled(false);
                try {
                    wallpaperManager.setBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        onGalleryImageSelect(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(ViewActivity.this, "Nothing Selected.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onGalleryImageSelect(Bitmap bitmap) {
        FirebaseAnalytics("ViewActivity", "Gallery", "On image Select");
        blankLayout.setVisibility(View.GONE);
        rootLayout.setVisibility(View.VISIBLE);
        origininalIMG.setImageBitmap(bitmap);
        storeOriginal();

        setDefault();
    }

    private void downloadWallpaper() {

        try {
            File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/Wallpapers/");
            if (!wallpaperDirectory.exists()) {
                wallpaperDirectory.mkdirs();
            }
            Bitmap bitmap;

            if (from) {
                blankLayout.setDrawingCacheEnabled(true);
                bitmap = Bitmap.createBitmap(blankLayout.getDrawingCache());
                blankLayout.setDrawingCacheEnabled(false);
            } else {
                rootLayout.setDrawingCacheEnabled(true);
                bitmap = Bitmap.createBitmap(rootLayout.getDrawingCache());
                rootLayout.setDrawingCacheEnabled(false);
            }

            File imageFile = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(ViewActivity.this, "Wallpaper Downloaded !!!", Toast.LENGTH_LONG).show();
            openScreenshot(imageFile);
        } catch (Throwable e) {
            e.printStackTrace();
        }


    }

    private void openScreenshot(File imageFile) {
        FirebaseAnalytics("ViewActivity", "Open", "Screenshot");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri photoURI = FileProvider.getUriForFile(ViewActivity.this, getApplicationContext().getPackageName() + ".provider", imageFile);
        intent.setDataAndType(photoURI, "image/*");
        startActivity(intent);
    }

    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //This checks both permission status and the Don't ask again check box
//            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE )
//                        == PackageManager.PERMISSION_DENIED  && ContextCompat.checkSelfPermission(this,Manifest.permission.SET_WALLPAPER )
//                        == PackageManager.PERMISSION_DENIED && ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE )
//                        == PackageManager.PERMISSION_DENIED){
//                    requestPermissions(new String[]{ android.Manifest.permission.SET_WALLPAPER,android.Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE },REQUEST_PERMISSION);
//                }else{
//                    Uri packageUri = Uri.fromParts("package",getApplicationContext().getPackageName(),null);
//
//                    Intent applicationDetailsSettingsIntent = new Intent();
//
//                    applicationDetailsSettingsIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    applicationDetailsSettingsIntent.setData(packageUri);
//                    applicationDetailsSettingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                    getApplicationContext().startActivity(applicationDetailsSettingsIntent);
//                }
//            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.SET_WALLPAPER)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                requestPermissions(new String[]{android.Manifest.permission.SET_WALLPAPER, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }

    }


    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(this, Manifest.permission.SET_WALLPAPER) == PackageManager.PERMISSION_GRANTED) {

                } else {
                    requestPermission();
                }
            } else {
                requestPermission();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences = getSharedPreferences("Favourites_pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        json1 = sharedPreferences.getString("fav_list", "");
        Type type1 = new TypeToken<ArrayList<LatestModel>>() {
        }.getType();
        favList = gson.fromJson(json1, type1);

        if (favList == null) {
            favList = new ArrayList<>();
        }
        if (favList.size() > 0) {
            for (int i = 0; i < favList.size(); i++) {
                if (favList.get(i).getId().equals(popularModel.getId()) || favList.get(i).getId().equals(latestModel.getId())) {
                    vector1.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector__12_));
                }
            }
        }

        sharedPreferences1 = getSharedPreferences("Shape_pref", MODE_PRIVATE);
        currentShape = sharedPreferences1.getInt("shapeID", R.drawable.ic_shape1);
        cardView1.setVisibility(View.VISIBLE);
        cardView2.setVisibility(View.GONE);
        cardView3.setVisibility(View.GONE);
    }

    private View.OnClickListener onShapeClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shapeColorBoolean = true;
                currentShapeID = view.getId();
                cardView1.setVisibility(View.GONE);
                cardView2.setVisibility(View.GONE);
                cardView3.setVisibility(View.VISIBLE);
            }
        };
    }

    private View.OnTouchListener onShapeTouched() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                shapeColorBoolean = true;
                currentShapeID = view.getId();
                cardView1.setVisibility(View.GONE);
                cardView2.setVisibility(View.GONE);
                cardView3.setVisibility(View.VISIBLE);
//                Log.e("Current shape moving :",String.valueOf(currentShapeID));
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:

                        xCoOrdinate = view.getX() - event.getRawX();
                        yCoOrdinate = view.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        };
    }


    private SeekBar.OnSeekBarChangeListener onShapeSeekBarChanged() {
        return new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shapeImgView = findViewById(currentShapeID);
                storeShape();
                if (resizeBoolean) {
                    resizeProgress = progress;
//                    Log.e("Current shape scale :",String.valueOf(currentShapeID));
//                    Log.e("Width is :" + shapeImgView.getLayoutParams().width,"Height is :" + shapeImgView.getLayoutParams().height);
                    shapeImgView.getLayoutParams().width = progress;
                    shapeImgView.getLayoutParams().height = progress;
                    shapeImgView.requestLayout();
                } else if (rotateBoolean) {
//                    Log.e("Current shape rotate :",String.valueOf(currentShapeID) + " & Progress :" + progress);
                    rotateProgress = progress;
                    shapeImgView.animate().rotation(progress).start();
                } else if (opacityBoolean) {
                    opacityProgress = progress;
//                    Log.e("Alpha :",String.valueOf(shapeImgView.getImageAlpha()));
                    shapeImgView.setImageAlpha(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                FirebaseAnalytics("ViewActivity", "Shape", "Shape seekbar progress changed");
            }
        };
    }

    public void storeShape() {
        try {
            shapeImgView = findViewById(currentShapeID);
            Bitmap bitmap;

            if (shapeImgView != null) {
                bitmap = Bitmap.createBitmap(shapeImgView.getDrawable().getIntrinsicWidth(), shapeImgView.getDrawable().getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(bitmap);
                shapeImgView.getDrawable().setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                shapeImgView.getDrawable().draw(canvas);
                shapeBitmap = bitmap;
            }
        } catch (OutOfMemoryError e) {

        }
    }

    public void setDefault() {
        from = false;
        dayNightBoolean = true;
        dayNightProgress = 50;
        blurBoolean = false;
        blurProgress = 1;
        vintageBoolean = false;
        vintageProgress = 1;
        colorBoolean = false;
        colorProgress = 1;
        contrastBoolean = false;
        contrastProgress = 1;
        seekBarBrightness.setProgress(dayNightProgress);
        seekBarBlur.setProgress(blurProgress);
        seekbarVignette.setProgress(vintageProgress);
        seekbarColor.setProgress(colorProgress);
        seekbarContrast.setProgress(contrastProgress);
        colorRecycler.setVisibility(View.GONE);
        vector4.setVisibility(View.VISIBLE);
        vector1.setVisibility(View.VISIBLE);
        newLayout.setWeightSum(4);
        gallery.setVisibility(View.GONE);
    }

    public void setDefaultShape() {

//        Log.e("Max is :",String.valueOf(origininalIMG.getMeasuredWidth()));
        currentShape = getIntent().getIntExtra("shapeID", R.drawable.ic_shape1);
        seekBarSize.setMax(2048);
        seekbarRotate.setMax(360);
        seekbarOpacity.setMax(255);
        resizeBoolean = false;
        resizeProgress = 300;
        rotateBoolean = false;
        rotateProgress = 0;
        opacityBoolean = false;
        opacityProgress = 255;
        shapeColorBoolean = true;
        seekBarSize.setProgress(resizeProgress);
        seekbarRotate.setProgress(rotateProgress);
        seekbarOpacity.setProgress(opacityProgress);
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChanged() {
        return new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (dayNightBoolean) {
                    origininalIMG.setColorFilter(setBrightness(progress));
                    dayNightProgress = progress;
                } else if (blurBoolean) {
                    blurProgress = progress;
                    if (progress == 1)
                        origininalIMG.setImageBitmap(constantBitmap);
                    else {
                        Bitmap resultBmp = BlurBuilder.blur(ViewActivity.this, constantBitmap, progress);
                        origininalIMG.setImageBitmap(resultBmp);
                    }
                } else if (vintageBoolean) {
                    vintageProgress = progress;
                    Filter imageFilter = new Filter();
                    imageFilter.addSubFilter(new VignetteSubfilter(ViewActivity.this, progress));
                    Bitmap mutableBitmap = constantBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Bitmap outputImage = imageFilter.processFilter(mutableBitmap);
                    origininalIMG.setImageBitmap(outputImage);
                } else if (colorBoolean) {
                    colorProgress = progress;
                    if (progress == 1 || progress == 255) {

                        origininalIMG.setImageBitmap(constantBitmap);
                    } else {
                        Filter imageFilter = new Filter();
                        Bitmap mutableBitmap = constantBitmap.copy(Bitmap.Config.ARGB_8888, true);
                        Bitmap outputImage = imageFilter.processFilter(mutableBitmap);
                        Canvas canvas = new Canvas(outputImage);

                        Paint paint = new Paint();
                        paint.setColorFilter(ColorFilterGenerator.adjustHue(progress));
                        canvas.drawBitmap(outputImage, 0, 0, paint);
                        origininalIMG.setImageBitmap(outputImage);

                    }
                } else if (contrastBoolean) {
                    contrastProgress = progress;
                    origininalIMG.setColorFilter(adjustContrast(progress));
                } else {
                    origininalIMG.setImageBitmap(constantBitmap);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                FirebaseAnalytics("ViewActivity", "Wallpaper", "Wallpaper seekbar progress changed");
            }
        };
    }

    public void storeOriginal() {
        BitmapDrawable drawable = (BitmapDrawable) origininalIMG.getDrawable();
        constantBitmap = drawable.getBitmap();
    }

    @Override
    public void onBackPressed() {
        if (cardView1.getVisibility() == View.VISIBLE) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.GONE);
            cardView3.setVisibility(View.GONE);
            super.onBackPressed();
        } else if (cardView2.getVisibility() == View.VISIBLE) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.GONE);
            cardView3.setVisibility(View.GONE);
        } else if (cardView3.getVisibility() == View.VISIBLE) {
            cardView1.setVisibility(View.VISIBLE);
            cardView2.setVisibility(View.GONE);
            cardView3.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    public void init() {
        origininalIMG = findViewById(R.id.image);
        setWallpaper = findViewById(R.id.setWallpaper);
        vector1 = findViewById(R.id.vector1);
        vector2 = findViewById(R.id.vector2);
        vector3 = findViewById(R.id.vector3);
        vector4 = findViewById(R.id.vector4);
        gallery = findViewById(R.id.gallery);
        vector11 = findViewById(R.id.dayNight);
        vector12 = findViewById(R.id.blur);
        vector13 = findViewById(R.id.vignette);
        vector14 = findViewById(R.id.colorVector);
        vector15 = findViewById(R.id.contrast);
        cardView1 = findViewById(R.id.card1);
        cardView2 = findViewById(R.id.card2);
        cardView3 = findViewById(R.id.card3);
        cardImg = findViewById(R.id.cardImg);
        seekBarBrightness = findViewById(R.id.seekbarBrightness);
        seekBarBlur = findViewById(R.id.seekbarBlur);
        seekbarVignette = findViewById(R.id.seekbarVignette);
        seekbarColor = findViewById(R.id.seekbarColor);
        seekbarContrast = findViewById(R.id.seekbarContrast);
        seekBarBrightness.setMax(100);
        seekBarBlur.setMax(25);
        seekbarVignette.setMax(255);
        seekbarColor.setMax(255);
        seekbarContrast.setMax(100);
        seekBarSize = findViewById(R.id.seekbarSize);
        seekbarRotate = findViewById(R.id.seekbarRotate);
        seekbarOpacity = findViewById(R.id.seekbarOpacity);

        colorRecycler = findViewById(R.id.colorRecycler);
        colorRecycler1 = findViewById(R.id.colorRecycler1);
        layout = findViewById(R.id.title);

        resize = findViewById(R.id.resize);
        rotate = findViewById(R.id.rotate);
        opacity = findViewById(R.id.opacity);
        color = findViewById(R.id.color);
        shape = findViewById(R.id.shape);
        delete = findViewById(R.id.delete);
        latestModel = new LatestModel();
        popularModel = new LatestModel();

        profileName = findViewById(R.id.profileName);
        back = findViewById(R.id.back1);
        profileImage = findViewById(R.id.profileImage);
        site = findViewById(R.id.site);

        rootRL = findViewById(R.id.rootRL);
        rootLayout = findViewById(R.id.rootLayout);
        blankLayout = findViewById(R.id.blankLayout);
        scrollView = findViewById(R.id.scrollView);
        scrollView.setKeepScreenOn(true);
        newLayout = findViewById(R.id.newLayout);


        ViewTreeObserver vto = scrollView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                scrollView.scrollTo(scrollView.getRight() / 2, 0);
            }
        });
    }


    public void vector11() {
        seekBarBrightness.setVisibility(View.VISIBLE);
        seekBarBlur.setVisibility(View.GONE);
        seekbarVignette.setVisibility(View.GONE);
        seekbarColor.setVisibility(View.GONE);
        seekbarContrast.setVisibility(View.GONE);
        dayNightBoolean = true;
        blurBoolean = false;
        vintageBoolean = false;
        colorBoolean = false;
        contrastBoolean = false;
        vector11.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector11_black));
        vector12.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector12_grey));
        vector13.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector13_grey));
        vector14.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector14_grey));
        vector15.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector15_grey));
    }

    public void vector12() {
        seekBarBrightness.setVisibility(View.GONE);
        seekBarBlur.setVisibility(View.VISIBLE);
        seekbarVignette.setVisibility(View.GONE);
        seekbarColor.setVisibility(View.GONE);
        seekbarContrast.setVisibility(View.GONE);
        dayNightBoolean = false;
        blurBoolean = true;
        vintageBoolean = false;
        colorBoolean = false;
        contrastBoolean = false;
        vector11.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector11_grey));
        vector12.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector12_black));
        vector13.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector13_grey));
        vector14.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector14_grey));
        vector15.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector15_grey));
    }

    public void vector13() {
        seekBarBrightness.setVisibility(View.GONE);
        seekBarBlur.setVisibility(View.GONE);
        seekbarVignette.setVisibility(View.VISIBLE);
        seekbarColor.setVisibility(View.GONE);
        seekbarContrast.setVisibility(View.GONE);
        dayNightBoolean = false;
        blurBoolean = false;
        vintageBoolean = true;
        colorBoolean = false;
        contrastBoolean = false;
        vector11.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector11_grey));
        vector12.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector12_grey));
        vector13.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector13_black));
        vector14.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector14_grey));
        vector15.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector15_grey));
    }

    public void vector14() {
        seekBarBrightness.setVisibility(View.GONE);
        seekBarBlur.setVisibility(View.GONE);
        seekbarVignette.setVisibility(View.GONE);
        seekbarColor.setVisibility(View.VISIBLE);
        seekbarContrast.setVisibility(View.GONE);
        dayNightBoolean = false;
        blurBoolean = false;
        vintageBoolean = false;
        colorBoolean = true;
        contrastBoolean = false;
        vector11.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector11_grey));
        vector12.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector12_grey));
        vector13.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector13_grey));
        vector14.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector14_black));
        vector15.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector15_grey));
    }

    public void vector15() {
        seekBarBrightness.setVisibility(View.GONE);
        seekBarBlur.setVisibility(View.GONE);
        seekbarVignette.setVisibility(View.GONE);
        seekbarColor.setVisibility(View.GONE);
        seekbarContrast.setVisibility(View.VISIBLE);
        dayNightBoolean = false;
        blurBoolean = false;
        vintageBoolean = false;
        colorBoolean = false;
        contrastBoolean = true;
        vector11.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector11_grey));
        vector12.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector12_grey));
        vector13.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector13_grey));
        vector14.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector14_grey));
        vector15.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector15_black));
    }

    public void resizeTheme() {
        resizeBoolean = true;
        rotateBoolean = false;
        colorBoolean = false;
        opacityBoolean = false;
        seekBarSize.setVisibility(View.VISIBLE);
        seekbarRotate.setVisibility(View.GONE);
        seekbarOpacity.setVisibility(View.GONE);
        colorRecycler1.setVisibility(View.GONE);
        resize.setImageDrawable(getResources().getDrawable(R.drawable.ic_resize_black));
        rotate.setImageDrawable(getResources().getDrawable(R.drawable.ic_rotate_grey));
        opacity.setImageDrawable(getResources().getDrawable(R.drawable.ic_opacity_grey));
        color.setImageDrawable(getResources().getDrawable(R.drawable.ic_color_grey));
        shape.setImageDrawable(getResources().getDrawable(R.drawable.ic_shape_grey));
    }

    public void rotateTheme() {
        resizeBoolean = false;
        rotateBoolean = true;
        colorBoolean = false;
        opacityBoolean = false;
        seekBarSize.setVisibility(View.GONE);
        seekbarRotate.setVisibility(View.VISIBLE);
        seekbarOpacity.setVisibility(View.GONE);
        colorRecycler1.setVisibility(View.GONE);
        resize.setImageDrawable(getResources().getDrawable(R.drawable.ic_resize_grey));
        rotate.setImageDrawable(getResources().getDrawable(R.drawable.ic_rotate_black));
        opacity.setImageDrawable(getResources().getDrawable(R.drawable.ic_opacity_grey));
        color.setImageDrawable(getResources().getDrawable(R.drawable.ic_color_grey));
        shape.setImageDrawable(getResources().getDrawable(R.drawable.ic_shape_grey));
    }

    public void opacityTheme() {
        resizeBoolean = false;
        rotateBoolean = false;
        opacityBoolean = true;
        colorBoolean = false;
        seekBarSize.setVisibility(View.GONE);
        seekbarRotate.setVisibility(View.GONE);
        seekbarOpacity.setVisibility(View.VISIBLE);
        colorRecycler1.setVisibility(View.GONE);
        resize.setImageDrawable(getResources().getDrawable(R.drawable.ic_resize_grey));
        rotate.setImageDrawable(getResources().getDrawable(R.drawable.ic_rotate_grey));
        opacity.setImageDrawable(getResources().getDrawable(R.drawable.ic_opacity_black));
        color.setImageDrawable(getResources().getDrawable(R.drawable.ic_color_grey));
        shape.setImageDrawable(getResources().getDrawable(R.drawable.ic_shape_grey));
    }

    public void colorTheme() {
        resizeBoolean = false;
        rotateBoolean = false;
        opacityBoolean = false;
        colorBoolean = true;
        seekBarSize.setVisibility(View.GONE);
        seekbarRotate.setVisibility(View.GONE);
        seekbarOpacity.setVisibility(View.GONE);
        colorRecycler1.setVisibility(View.VISIBLE);
        resize.setImageDrawable(getResources().getDrawable(R.drawable.ic_resize_grey));
        rotate.setImageDrawable(getResources().getDrawable(R.drawable.ic_rotate_grey));
        opacity.setImageDrawable(getResources().getDrawable(R.drawable.ic_opacity_grey));
        color.setImageDrawable(getResources().getDrawable(R.drawable.ic_color_black));
        shape.setImageDrawable(getResources().getDrawable(R.drawable.ic_shape_grey));
    }

    public void shapeTheme() {
        resizeBoolean = false;
        rotateBoolean = false;
        opacityBoolean = false;
        colorBoolean = true;
        seekBarSize.setVisibility(View.GONE);
        seekbarRotate.setVisibility(View.GONE);
        seekbarOpacity.setVisibility(View.GONE);
        colorRecycler1.setVisibility(View.VISIBLE);
        resize.setImageDrawable(getResources().getDrawable(R.drawable.ic_resize_grey));
        rotate.setImageDrawable(getResources().getDrawable(R.drawable.ic_rotate_grey));
        opacity.setImageDrawable(getResources().getDrawable(R.drawable.ic_opacity_grey));
        color.setImageDrawable(getResources().getDrawable(R.drawable.ic_color_grey));
        shape.setImageDrawable(getResources().getDrawable(R.drawable.ic_shape_black));
    }

    private ArrayList<Integer> initColorList() {

        ArrayList<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.md_amber_50);
        colorList.add(R.color.md_amber_100);
        colorList.add(R.color.md_amber_200);
        colorList.add(R.color.md_amber_300);
        colorList.add(R.color.md_amber_400);
        colorList.add(R.color.md_amber_500);
        colorList.add(R.color.md_amber_600);
        colorList.add(R.color.md_amber_700);
        colorList.add(R.color.md_amber_800);
        colorList.add(R.color.md_amber_900);
        colorList.add(R.color.md_amber_A100);
        colorList.add(R.color.md_amber_A200);
        colorList.add(R.color.md_amber_A400);
        colorList.add(R.color.md_amber_A700);


        colorList.add(R.color.md_blue_50);
        colorList.add(R.color.md_blue_100);
        colorList.add(R.color.md_blue_200);
        colorList.add(R.color.md_blue_300);
        colorList.add(R.color.md_blue_400);
        colorList.add(R.color.md_blue_500);
        colorList.add(R.color.md_blue_600);
        colorList.add(R.color.md_blue_700);
        colorList.add(R.color.md_blue_800);
        colorList.add(R.color.md_blue_900);
        colorList.add(R.color.md_blue_A100);
        colorList.add(R.color.md_blue_A200);
        colorList.add(R.color.md_blue_A400);
        colorList.add(R.color.md_blue_A700);


        colorList.add(R.color.md_blue_grey_50);
        colorList.add(R.color.md_blue_grey_100);
        colorList.add(R.color.md_blue_grey_200);
        colorList.add(R.color.md_blue_grey_300);
        colorList.add(R.color.md_blue_grey_400);
        colorList.add(R.color.md_blue_grey_500);
        colorList.add(R.color.md_blue_grey_600);
        colorList.add(R.color.md_blue_grey_700);
        colorList.add(R.color.md_blue_grey_800);
        colorList.add(R.color.md_blue_grey_900);

        colorList.add(R.color.md_brown_50);
        colorList.add(R.color.md_brown_100);
        colorList.add(R.color.md_brown_200);
        colorList.add(R.color.md_brown_300);
        colorList.add(R.color.md_brown_400);
        colorList.add(R.color.md_brown_500);
        colorList.add(R.color.md_brown_600);
        colorList.add(R.color.md_brown_700);
        colorList.add(R.color.md_brown_800);
        colorList.add(R.color.md_brown_900);

        colorList.add(R.color.md_cyan_50);
        colorList.add(R.color.md_cyan_100);
        colorList.add(R.color.md_cyan_200);
        colorList.add(R.color.md_cyan_300);
        colorList.add(R.color.md_cyan_400);
        colorList.add(R.color.md_cyan_500);
        colorList.add(R.color.md_cyan_600);
        colorList.add(R.color.md_cyan_700);
        colorList.add(R.color.md_cyan_800);
        colorList.add(R.color.md_cyan_900);
        colorList.add(R.color.md_cyan_A100);
        colorList.add(R.color.md_cyan_A200);
        colorList.add(R.color.md_cyan_A400);
        colorList.add(R.color.md_cyan_A700);


        colorList.add(R.color.md_deep_orange_50);
        colorList.add(R.color.md_deep_orange_100);
        colorList.add(R.color.md_deep_orange_200);
        colorList.add(R.color.md_deep_orange_300);
        colorList.add(R.color.md_deep_orange_400);
        colorList.add(R.color.md_deep_orange_500);
        colorList.add(R.color.md_deep_orange_600);
        colorList.add(R.color.md_deep_orange_700);
        colorList.add(R.color.md_deep_orange_800);
        colorList.add(R.color.md_deep_orange_900);
        colorList.add(R.color.md_deep_orange_A100);
        colorList.add(R.color.md_deep_orange_A200);
        colorList.add(R.color.md_deep_orange_A400);
        colorList.add(R.color.md_deep_orange_A700);

        colorList.add(R.color.md_deep_purple_50);
        colorList.add(R.color.md_deep_purple_100);
        colorList.add(R.color.md_deep_purple_200);
        colorList.add(R.color.md_deep_purple_300);
        colorList.add(R.color.md_deep_purple_400);
        colorList.add(R.color.md_deep_purple_500);
        colorList.add(R.color.md_deep_purple_600);
        colorList.add(R.color.md_deep_purple_700);
        colorList.add(R.color.md_deep_purple_800);
        colorList.add(R.color.md_deep_purple_900);
        colorList.add(R.color.md_deep_purple_A100);
        colorList.add(R.color.md_deep_purple_A200);
        colorList.add(R.color.md_deep_purple_A400);
        colorList.add(R.color.md_deep_purple_A700);

        colorList.add(R.color.md_green_50);
        colorList.add(R.color.md_green_100);
        colorList.add(R.color.md_green_200);
        colorList.add(R.color.md_green_300);
        colorList.add(R.color.md_green_400);
        colorList.add(R.color.md_green_500);
        colorList.add(R.color.md_green_600);
        colorList.add(R.color.md_green_700);
        colorList.add(R.color.md_green_800);
        colorList.add(R.color.md_green_900);
        colorList.add(R.color.md_green_A100);
        colorList.add(R.color.md_green_A200);
        colorList.add(R.color.md_green_A400);
        colorList.add(R.color.md_green_A700);

        colorList.add(R.color.md_grey_50);
        colorList.add(R.color.md_grey_100);
        colorList.add(R.color.md_grey_200);
        colorList.add(R.color.md_grey_300);
        colorList.add(R.color.md_grey_400);
        colorList.add(R.color.md_grey_500);
        colorList.add(R.color.md_grey_600);
        colorList.add(R.color.md_grey_700);
        colorList.add(R.color.md_grey_800);
        colorList.add(R.color.md_grey_900);

        colorList.add(R.color.md_indigo_50);
        colorList.add(R.color.md_indigo_100);
        colorList.add(R.color.md_indigo_200);
        colorList.add(R.color.md_indigo_300);
        colorList.add(R.color.md_indigo_400);
        colorList.add(R.color.md_indigo_500);
        colorList.add(R.color.md_indigo_600);
        colorList.add(R.color.md_indigo_700);
        colorList.add(R.color.md_indigo_800);
        colorList.add(R.color.md_indigo_900);
        colorList.add(R.color.md_indigo_A100);
        colorList.add(R.color.md_indigo_A200);
        colorList.add(R.color.md_indigo_A400);
        colorList.add(R.color.md_indigo_A700);

        colorList.add(R.color.md_light_blue_50);
        colorList.add(R.color.md_light_blue_100);
        colorList.add(R.color.md_light_blue_200);
        colorList.add(R.color.md_light_blue_300);
        colorList.add(R.color.md_light_blue_400);
        colorList.add(R.color.md_light_blue_500);
        colorList.add(R.color.md_light_blue_600);
        colorList.add(R.color.md_light_blue_700);
        colorList.add(R.color.md_light_blue_800);
        colorList.add(R.color.md_light_blue_900);
        colorList.add(R.color.md_light_blue_A100);
        colorList.add(R.color.md_light_blue_A200);
        colorList.add(R.color.md_light_blue_A400);
        colorList.add(R.color.md_light_blue_A700);

        colorList.add(R.color.md_light_green_50);
        colorList.add(R.color.md_light_green_100);
        colorList.add(R.color.md_light_green_200);
        colorList.add(R.color.md_light_green_300);
        colorList.add(R.color.md_light_green_400);
        colorList.add(R.color.md_light_green_500);
        colorList.add(R.color.md_light_green_600);
        colorList.add(R.color.md_light_green_700);
        colorList.add(R.color.md_light_green_800);
        colorList.add(R.color.md_light_green_900);
        colorList.add(R.color.md_light_green_A100);
        colorList.add(R.color.md_light_green_A200);
        colorList.add(R.color.md_light_green_A400);
        colorList.add(R.color.md_light_green_A700);

        colorList.add(R.color.md_lime_50);
        colorList.add(R.color.md_lime_100);
        colorList.add(R.color.md_lime_200);
        colorList.add(R.color.md_lime_300);
        colorList.add(R.color.md_lime_400);
        colorList.add(R.color.md_lime_500);
        colorList.add(R.color.md_lime_600);
        colorList.add(R.color.md_lime_700);
        colorList.add(R.color.md_lime_800);
        colorList.add(R.color.md_lime_900);
        colorList.add(R.color.md_lime_A100);
        colorList.add(R.color.md_lime_A200);
        colorList.add(R.color.md_lime_A400);
        colorList.add(R.color.md_lime_A700);

        colorList.add(R.color.md_orange_50);
        colorList.add(R.color.md_orange_100);
        colorList.add(R.color.md_orange_200);
        colorList.add(R.color.md_orange_300);
        colorList.add(R.color.md_orange_400);
        colorList.add(R.color.md_orange_500);
        colorList.add(R.color.md_orange_600);
        colorList.add(R.color.md_orange_700);
        colorList.add(R.color.md_orange_800);
        colorList.add(R.color.md_orange_900);
        colorList.add(R.color.md_orange_A100);
        colorList.add(R.color.md_orange_A200);
        colorList.add(R.color.md_orange_A400);
        colorList.add(R.color.md_orange_A700);

        colorList.add(R.color.md_pink_50);
        colorList.add(R.color.md_pink_100);
        colorList.add(R.color.md_pink_200);
        colorList.add(R.color.md_pink_300);
        colorList.add(R.color.md_pink_400);
        colorList.add(R.color.md_pink_500);
        colorList.add(R.color.md_pink_600);
        colorList.add(R.color.md_pink_700);
        colorList.add(R.color.md_pink_800);
        colorList.add(R.color.md_pink_900);
        colorList.add(R.color.md_pink_A100);
        colorList.add(R.color.md_pink_A200);
        colorList.add(R.color.md_pink_A400);
        colorList.add(R.color.md_pink_A700);

        colorList.add(R.color.md_purple_50);
        colorList.add(R.color.md_purple_100);
        colorList.add(R.color.md_purple_200);
        colorList.add(R.color.md_purple_300);
        colorList.add(R.color.md_purple_400);
        colorList.add(R.color.md_purple_500);
        colorList.add(R.color.md_purple_600);
        colorList.add(R.color.md_purple_700);
        colorList.add(R.color.md_purple_800);
        colorList.add(R.color.md_purple_900);
        colorList.add(R.color.md_purple_A100);
        colorList.add(R.color.md_purple_A200);
        colorList.add(R.color.md_purple_A400);
        colorList.add(R.color.md_purple_A700);

        colorList.add(R.color.md_red_50);
        colorList.add(R.color.md_red_100);
        colorList.add(R.color.md_red_200);
        colorList.add(R.color.md_red_300);
        colorList.add(R.color.md_red_400);
        colorList.add(R.color.md_red_500);
        colorList.add(R.color.md_red_600);
        colorList.add(R.color.md_red_700);
        colorList.add(R.color.md_red_800);
        colorList.add(R.color.md_red_900);
        colorList.add(R.color.md_red_A100);
        colorList.add(R.color.md_red_A200);
        colorList.add(R.color.md_red_A400);
        colorList.add(R.color.md_red_A700);

        colorList.add(R.color.md_teal_50);
        colorList.add(R.color.md_teal_100);
        colorList.add(R.color.md_teal_200);
        colorList.add(R.color.md_teal_300);
        colorList.add(R.color.md_teal_400);
        colorList.add(R.color.md_teal_500);
        colorList.add(R.color.md_teal_600);
        colorList.add(R.color.md_teal_700);
        colorList.add(R.color.md_teal_800);
        colorList.add(R.color.md_teal_900);
        colorList.add(R.color.md_teal_A100);
        colorList.add(R.color.md_teal_A200);
        colorList.add(R.color.md_teal_A400);
        colorList.add(R.color.md_teal_A700);


        return colorList;
    }

    public void FirebaseAnalytics(String s1, String s2, String s3) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, s1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, s2);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, s3);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Log.e("View Activity", "Analitics stored");
    }

    public static class ColorFilterGenerator {

        public static ColorFilter adjustHue(float value) {
            ColorMatrix cm = new ColorMatrix();

            adjustHue(cm, value);

            return new ColorMatrixColorFilter(cm);
        }


        public static void adjustHue(ColorMatrix cm, float value) {
            value = (value % 360.0f) * (float) Math.PI / 180.0f;

            if (value == 0) {
                return;
            }
            float cosVal = (float) Math.cos(value);
            float sinVal = (float) Math.sin(value);
            float lumR = 0.213f;
            float lumG = 0.715f;
            float lumB = 0.072f;
            float[] mat = new float[]
                    {
                            lumR + cosVal * (1 - lumR) + sinVal * (-lumR), lumG + cosVal * (-lumG) + sinVal * (-lumG), lumB + cosVal * (-lumB) + sinVal * (1 - lumB), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (0.143f), lumG + cosVal * (1 - lumG) + sinVal * (0.140f), lumB + cosVal * (-lumB) + sinVal * (-0.283f), 0, 0,
                            lumR + cosVal * (-lumR) + sinVal * (-(1 - lumR)), lumG + cosVal * (-lumG) + sinVal * (lumG), lumB + cosVal * (1 - lumB) + sinVal * (lumB), 0, 0,
                            0f, 0f, 0f, 1f, 0f,
                            0f, 0f, 0f, 0f, 1f};
            cm.postConcat(new ColorMatrix(mat));
        }
    }

//    ************************************
//    New changes


//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
//            origininalIMG.setScaleX(mScaleFactor);
//            origininalIMG.setScaleY(mScaleFactor);
//            return true;
//        }
//    }
//
//    private View.OnTouchListener onImageTouched(){
//        return new View.OnTouchListener(){
//            @Override
//            public boolean onTouch(View view,MotionEvent event){
//                scaleGestureDetector.onTouchEvent(event);
//                switch (event.getActionMasked()) {
//                    case MotionEvent.ACTION_DOWN:
//
//                        xCoOrdinate = view.getX() - event.getRawX();
//                        yCoOrdinate = view.getY() - event.getRawY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
//                        break;
//                    default:
//                        return false;
//                }
//                return true;
//            }
//        };
//    }
}