package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.CollectionList.CollectionsModel;
import bestwallpaper.hdgb.wallpapers.backgrounds.Model.CollectionList.ResultsItem;

public class CollectionActivity extends AppCompatActivity {

    ArrayList<ResultsItem> collectionsModels = new ArrayList<>();
    RecyclerView recyclerView;
    CollectionsFolderAdapter adapter;
    NestedScrollView scrollView;
    int visibleItemCount, pastVisiblesItems, totalItemCount;
    int pageCount = 1;
    ProgressBar progressBar;
    LinearLayout refreshLayout;
    ImageView refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        this.getSupportActionBar().hide();
        refreshLayout = findViewById(R.id.refresh_layout);
        refresh = findViewById(R.id.refresh);
        recyclerView = findViewById(R.id.collection_Recycler);
        scrollView = findViewById(R.id.scrollView);
        progressBar = findViewById(R.id.progressBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(CollectionActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        collectionsModels.clear();
        getCollectionData();
        adapter = new CollectionsFolderAdapter(CollectionActivity.this, collectionsModels);
        recyclerView.setAdapter(adapter);
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {

                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

//                        Log.e("LLLLL_count: ","Visible: "+visibleItemCount+" totalItem: "+totalItemCount+
//                                " pastVisible: "+pastVisiblesItems);

                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            if (pageCount != -1)
                                showProgressView();
                            getCollectionData();
                        }
                    }
                }
            }
        });
    }

    public void getCollectionData() {

        AndroidNetworking.get("https://api.unsplash.com/search/collections/")
                .addQueryParameter("client_id", "a82f6bf78409bb9e7f0921a410d9d693d06b98a2d5df9a9cdc8295ab3cb261c1")
                .addQueryParameter("query", "backdrops")
                .addQueryParameter("page", String.valueOf(pageCount))
                .addQueryParameter("per_page", "20")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        refreshLayout.setVisibility(View.GONE);
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressView();
                            }
                        }, 2000);
                        if (response.toString().trim().equals("[]")) {
                            pageCount = -1;
                            Log.e("Collection response :- ", response.toString());
                            Toast.makeText(CollectionActivity.this, "No More Data Here!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            pageCount++;
                            Log.e("Collection response :- ", response.toString());
                            CollectionsModel resultsItem = new Gson().fromJson(response.toString(), CollectionsModel.class);
                            List<ResultsItem> resultsItem1 = resultsItem.getResults();
                            adapter.addAll(resultsItem1);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        refreshLayout.setVisibility(View.VISIBLE);
                        Log.e("Collection error:- ", anError.getMessage());
                    }
                });

    }

    void showProgressView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    void hideProgressView() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.overridePendingTransition(R.anim.animation_enter,
                R.anim.animation_leave);
        Intent in = new Intent(CollectionActivity.this, MainActivity.class);
        startActivity(in);
    }
}