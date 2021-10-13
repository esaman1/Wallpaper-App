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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.PhotographerList.PhotographerModel;

import static bestwallpaper.hdgb.wallpapers.backgrounds.SearchPhotographerAdapter.photographerList;

public class SearchPhotographerFragment extends Fragment {

    ArrayList<PhotographerModel> photographerList1 = new ArrayList<>();
    RecyclerView recyclerView;
    SearchPhotographerAdapter adapter;
    NestedScrollView scrollView;
    int visibleItemCount, pastVisiblesItems, totalItemCount;
    int pageCount = 1;
    ProgressBar progressBar;
    View view;
    String query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_photographer, container, false);
        recyclerView = view.findViewById(R.id.collection_Recycler);
        scrollView = view.findViewById(R.id.scrollView);
        progressBar = view.findViewById(R.id.progressBar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        photographerList1.clear();
        adapter = new SearchPhotographerAdapter(getActivity(), photographerList1);
        recyclerView.setAdapter(adapter);
        query = "surface";
        ((SearchActivity) getActivity()).updateApi2(new PhotographerListner() {
            @Override
            public void onBtnClick(String str) {
                photographerList.clear();
                query = str;
                Log.e("Search string in :", str);
                getPhotographerData(query);
            }
        });
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
                            getPhotographerData(query);
                        }

                    }
                }
            }
        });
        getPhotographerData(query);


        return view;
    }

    public void getPhotographerData(String str) {

        AndroidNetworking.get("https://api.unsplash.com/search/users/")
                .addQueryParameter("client_id", "a82f6bf78409bb9e7f0921a410d9d693d06b98a2d5df9a9cdc8295ab3cb261c1")
                .addQueryParameter("query", str)
                .addQueryParameter("page", String.valueOf(pageCount))
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
                            Log.e("Photographer response :- ", response.toString());
                            Toast.makeText(getActivity(), "No More Data Here!!!", Toast.LENGTH_SHORT).show();
                        } else {
                            pageCount++;
                            Log.e("Photographer response :- ", response.toString());
                            JSONObject jsonObj = null;
                            try {
                                jsonObj = new JSONObject(response.toString());
                                JSONArray ja_data = jsonObj.getJSONArray("results");
                                int length = ja_data.length();
                                for (int i = 0; i < length; i++) {
                                    JSONObject jsonObj1 = ja_data.getJSONObject(i);
                                    PhotographerModel photographerModel = new Gson().fromJson(jsonObj1.toString(), PhotographerModel.class);
                                    adapter.addAll(photographerModel);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Photographer error:- ", anError.getMessage());
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