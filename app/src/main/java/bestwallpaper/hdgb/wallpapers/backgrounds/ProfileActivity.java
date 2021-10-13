package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList.LatestModel;
import bestwallpaper.hdgb.wallpapers.backgrounds.Model.PhotographerList.PhotographerModel;

public class ProfileActivity extends AppCompatActivity {

    public static RecyclerView recyclerView;
    ProfileAdapter adapter;
    TextView name, detail;
    LatestModel latestModel;
    PhotographerModel photosItem;
    ImageView profileView, profileImg, back;
    ArrayList<LatestModel> profileArrayList;
    String userName;
    int visibleItemCount, pastVisiblesItems, totalItemCount;
    int[] lastPositions;
    int pageCount = 1;
    NestedScrollView scrollView;
    ProgressBar progressBar;
    ImageView insta, fb, twitter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        this.getSupportActionBar().hide();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getBaseContext());
        init();

        latestModel = (LatestModel) getIntent().getSerializableExtra("Latest");
        if (latestModel == null) {
            latestModel = (LatestModel) getIntent().getSerializableExtra("Popular");
        }

        if (latestModel == null) {
            if (getIntent().getStringExtra("profile_from").equals("search")) {
                photosItem = (PhotographerModel) getIntent().getSerializableExtra("PhotographerModel");
                userName = photosItem.getUsername();
                name.setText(photosItem.getName());
                if (photosItem.getBio() != null) {
                    detail.setText(photosItem.getBio());
                } else {
                    detail.setVisibility(View.GONE);
                }
                try {
                    profileView.setVisibility(View.GONE);
                    Picasso.with(ProfileActivity.this).load(photosItem.getProfileImage().getMedium()).into(profileImg);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (photosItem.getInstagramUsername() == null) {
                    insta.setVisibility(View.GONE);
                } else {
                    insta.setVisibility(View.VISIBLE);
                }

                if (photosItem.getTwitterUsername() == null) {
                    twitter.setVisibility(View.GONE);
                } else {
                    twitter.setVisibility(View.VISIBLE);
                }
            }
        } else {
            userName = latestModel.getUser().getUsername();
            name.setText(latestModel.getUser().getName());
            if (latestModel.getUser().getBio() != null) {
                detail.setText(latestModel.getUser().getBio());
            } else {
                detail.setVisibility(View.GONE);
            }
            try {
                Picasso.with(ProfileActivity.this).load(latestModel.getUrls().getRegular()).into(profileView);
                Picasso.with(ProfileActivity.this).load(latestModel.getUser().getProfileImage().getMedium()).into(profileImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (latestModel.getUser().getInstagramUsername() == null) {
                insta.setVisibility(View.GONE);
            } else {
                insta.setVisibility(View.VISIBLE);
            }

            if (latestModel.getUser().getTwitterUsername() == null) {
                twitter.setVisibility(View.GONE);
            } else {
                twitter.setVisibility(View.VISIBLE);
            }
        }
        Log.e("Profile User name :", userName);

//        Log.e("Profile data :",latestModel.getUser().toString());

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ProfileActivity", "Redirect", "Open Instagram");
                String instaName = latestModel.getUser().getInstagramUsername();
                if (instaName == null) {
                    instaName = photosItem.getInstagramUsername();
                }
                Uri uri = Uri.parse("http://instagram.com/_u/" + instaName);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/_u/" + instaName)));
                }
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ProfileActivity", "Redirect", "Open Twitter");
                String twitterName = latestModel.getUser().getTwitterUsername();
                if (twitterName == null) {
                    twitterName = photosItem.getInstagramUsername();
                }
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitterName)));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + twitterName)));
                }
            }
        });

        profileArrayList = new ArrayList<>();
        profileArrayList.clear();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {

                        visibleItemCount = staggeredGridLayoutManager.getChildCount();
                        totalItemCount = staggeredGridLayoutManager.getItemCount();

                        if (lastPositions == null) {
                            lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                            lastPositions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPositions);
                            pastVisiblesItems = Math.max(lastPositions[0], lastPositions[1]);
                        }

//                        Log.e("LLLLL_count: ","Visible: "+visibleItemCount+" totalItem: "+totalItemCount+
//                                " pastVisible: "+pastVisiblesItems);


                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            if (pageCount != -1)
                                showProgressView();
                            getAllImageOfProfile();
                        }

                    }
                }
            }
        });

        getAllImageOfProfile();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.super.onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new ProfileAdapter(ProfileActivity.this, profileArrayList);
        recyclerView.setAdapter(adapter);
    }

    public void init() {
        name = findViewById(R.id.profileName);
        detail = findViewById(R.id.profileDetail);
        profileView = findViewById(R.id.profileImage);
        profileImg = findViewById(R.id.profileCard1);
        recyclerView = findViewById(R.id.profileRecycler);
        scrollView = findViewById(R.id.scrollView);
        progressBar = findViewById(R.id.progressBar);
        back = findViewById(R.id.back1);
        insta = findViewById(R.id.insta);
        fb = findViewById(R.id.fb);
        twitter = findViewById(R.id.twitter);

    }

    public void getAllImageOfProfile() {

        Log.e("Profile page count :- ", String.valueOf(pageCount));
        AndroidNetworking.get(" https://api.unsplash.com/users/" + userName + "/photos/")
                .addQueryParameter("client_id", "a82f6bf78409bb9e7f0921a410d9d693d06b98a2d5df9a9cdc8295ab3cb261c1")
                .addQueryParameter("page", "0")
                .addQueryParameter("page", String.valueOf(pageCount))
                .addQueryParameter("per_page", "20")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressView();
                            }
                        }, 2000);

                        if (response.toString().trim().equals("[]")) {
                            pageCount = -1;
                            Log.e("Profile response :- ", response.toString());
                            Toast.makeText(ProfileActivity.this, "No More Data Here!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            pageCount++;
                            Log.e("Profile response :- ", response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    latestModel = new Gson().fromJson(object.toString(), LatestModel.class);
                                    adapter.addAll(latestModel);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Profile error:- ", anError.getMessage());
                    }
                });
    }

    void showProgressView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressView() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void FirebaseAnalytics(String s1, String s2, String s3) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, s1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, s2);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, s3);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Log.e("Favourite Activity", "Analitics stored");
    }
}