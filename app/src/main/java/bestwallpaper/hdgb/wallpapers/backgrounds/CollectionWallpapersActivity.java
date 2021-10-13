package bestwallpaper.hdgb.wallpapers.backgrounds;

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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.CollectionList.ResultsItem;
import bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList.LatestModel;

public class CollectionWallpapersActivity extends AppCompatActivity {

    ResultsItem resultsItem;
    String collectionID;
    ArrayList<LatestModel> resultArrayList;
    int visibleItemCount, pastVisiblesItems, totalItemCount;
    int[] lastPositions;
    int pageCount = 1;
    NestedScrollView scrollView;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LatestModel latestModel;
    ImageView back;
    CollectionWallpaperAdapter wallpaperAdapter;
    TextView collectionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_wallpapers);
        this.getSupportActionBar().hide();
        resultsItem = (ResultsItem) getIntent().getSerializableExtra("Collection_Result");
        collectionName = findViewById(R.id.cName);
        collectionName.setText(resultsItem.getTitle());
        collectionID = resultsItem.getId();

        init();

        resultArrayList = new ArrayList<>();
        resultArrayList.clear();
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
                            getAllImageOfCollection();
                        }

                    }
                }
            }
        });
        getAllImageOfCollection();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void init() {
        recyclerView = findViewById(R.id.profileRecycler);
        scrollView = findViewById(R.id.scrollView);
        progressBar = findViewById(R.id.progressBar);
        back = findViewById(R.id.back1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        wallpaperAdapter = new CollectionWallpaperAdapter(CollectionWallpapersActivity.this, resultArrayList);
        recyclerView.setAdapter(wallpaperAdapter);
    }

    public void getAllImageOfCollection() {

        Log.e("Profile page count :- ", String.valueOf(pageCount));
        AndroidNetworking.get(" https://api.unsplash.com/collections/" + collectionID + "/photos/")
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
                            Toast.makeText(CollectionWallpapersActivity.this, "No More Data Here!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            pageCount++;
                            Log.e("Profile response :- ", response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    latestModel = new Gson().fromJson(object.toString(), LatestModel.class);
                                    wallpaperAdapter.addAll(latestModel);

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
}