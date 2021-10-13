package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList.LatestModel;

import static bestwallpaper.hdgb.wallpapers.backgrounds.SearchWallpaperAdapter.wallpaperModels;

public class SearchWallpaerFragment extends Fragment {


    public static RecyclerView recyclerView;
    ArrayList<LatestModel> wallpaerModels1 = new ArrayList<>();
    View view;
    SearchWallpaperAdapter adapter;
    NestedScrollView scrollView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LatestModel wallpaerModel;
    int visibleItemCount, pastVisiblesItems, totalItemCount;
    int[] lastPositions;
    int pageCount = 1;
    ProgressBar progressBar;
    String query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_wallpaer, container, false);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView = view.findViewById(R.id.latest_Recycler);
        scrollView = view.findViewById(R.id.scrollView);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        wallpaerModels1 = new ArrayList<>();
        wallpaerModels1.clear();
        adapter = new SearchWallpaperAdapter(wallpaerModels1, getActivity());
        recyclerView.setAdapter(adapter);
        query = "wallpaper";
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
                            getWallpeparData(query);
                        }

                    }
                }
            }
        });
        getWallpeparData(query);

        ((SearchActivity) getActivity()).updateApi(new SearchListner() {
            @Override
            public void onBtnClick(String str) {
                wallpaperModels.clear();
                query = str;
                Log.e("Search string :", str);
                getWallpeparData(query);
            }
        });

        return view;
    }

    public void getWallpeparData(String str) {

        Log.e("Wallpaper page count :- ", String.valueOf(pageCount));
        AndroidNetworking.get("https://api.unsplash.com/search/photos/")
                .addQueryParameter("client_id", "a82f6bf78409bb9e7f0921a410d9d693d06b98a2d5df9a9cdc8295ab3cb261c1")
                .addQueryParameter("page", String.valueOf(pageCount))
                .addQueryParameter("query", str)
                .addQueryParameter("per_page", "20")
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressView();
                            }
                        }, 2000);

                        if (response.toString().trim().equals("[]")) {
                            pageCount = -1;
                            Log.e("Wallpaper response :- ", response.toString());
                            Toast.makeText(getActivity(), "No More Data Here!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            pageCount++;
                            Log.e("Wallpaper response :- ", response.toString());
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = new JSONObject(response.toString());
                                JSONArray ja_data = jsonObj.getJSONArray("results");
                                int length = ja_data.length();
                                for (int i = 0; i < length; i++) {
                                    JSONObject jsonObj1 = ja_data.getJSONObject(i);
                                    wallpaerModel = new Gson().fromJson(jsonObj1.toString(), LatestModel.class);
                                    adapter.addAll(wallpaerModel);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Wallpaper error:- ", anError.getMessage());
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


}