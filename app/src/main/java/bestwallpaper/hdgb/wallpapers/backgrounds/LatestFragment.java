package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList.LatestModel;

import static android.content.Context.MODE_PRIVATE;

public class LatestFragment extends Fragment {

    public static ArrayList<LatestModel> randomList = new ArrayList<>();
    public static RecyclerView recyclerView;
    ArrayList<LatestModel> latestModels = new ArrayList<>();
    View view;
    LatestAdapter adapter;
    ImageView icon;
    NestedScrollView scrollView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LatestModel latestModel;
    int visibleItemCount, pastVisiblesItems, totalItemCount;
    int[] lastPositions;
    int pageCount = 1;
    ProgressBar progressBar;
    Gson gson = new Gson();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String json1;
    LinearLayout refreshLayout;
    ImageView refresh;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_latest, container, false);

        sharedPreferences = getActivity().getSharedPreferences("Auto_pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refresh = view.findViewById(R.id.refresh);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView = view.findViewById(R.id.latest_Recycler);
        scrollView = view.findViewById(R.id.scrollView);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        icon = view.findViewById(R.id.pen_icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("LatestActivity", "Open", "Blank View Activity");
                Intent in = new Intent(getActivity(), ViewActivity.class);
                in.putExtra("From", "Latest_new");
                getActivity().startActivity(in);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatestData();
            }
        });

        latestModels.clear();
        adapter = new LatestAdapter(latestModels, getActivity());
        recyclerView.setAdapter(adapter);
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
                            randomList.clear();
                            getLatestData();
                        }
                    }
                }
            }
        });
        getLatestData();

        return view;
    }

    public void getLatestData() {

        Log.e("Latest page count :- ", String.valueOf(pageCount));
        AndroidNetworking.get(" https://api.unsplash.com/photos/")
                .addQueryParameter("client_id", "a82f6bf78409bb9e7f0921a410d9d693d06b98a2d5df9a9cdc8295ab3cb261c1")
                .addQueryParameter("page", "0")
                .addQueryParameter("page", String.valueOf(pageCount))
                .addQueryParameter("order_by", "latest")
                .addQueryParameter("per_page", "20")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
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
                            Log.e("Latest response :- ", response.toString());
                            Toast.makeText(getActivity(), "No More Data Here!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            pageCount++;
                            Log.e("Latest response :- ", response.toString());
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject object = response.getJSONObject(i);
                                    latestModel = new Gson().fromJson(object.toString(), LatestModel.class);
                                    adapter.addAll(latestModel);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            randomList.addAll(latestModels);
                            Log.e("Size -> Randomlist :- ", String.valueOf(randomList.size()));
                            json1 = gson.toJson(randomList);
                            editor.putString("random_list", json1);
                            editor.apply();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        refreshLayout.setVisibility(View.VISIBLE);
                        Log.e("Latest error:- ", anError.getMessage());
                    }
                });

//
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