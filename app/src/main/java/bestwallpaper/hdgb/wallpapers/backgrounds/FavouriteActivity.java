package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList.LatestModel;

public class FavouriteActivity extends AppCompatActivity {

    public static ArrayList<LatestModel> favList = new ArrayList<>();
    public static TextView noText;
    RecyclerView recyclerView;
    FavouritesAdapter adapter;
    String json1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        this.getSupportActionBar().hide();

        SharedPreferences sharedPreferences = getSharedPreferences("Favourites_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        json1 = sharedPreferences.getString("fav_list", "");
        Type type1 = new TypeToken<ArrayList<LatestModel>>() {
        }.getType();

        favList = gson.fromJson(json1, type1);

        recyclerView = findViewById(R.id.favourite_Recycler);
        noText = findViewById(R.id.noText);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        if (favList != null) {
            noText.setVisibility(View.GONE);
            adapter = new FavouritesAdapter(favList, FavouriteActivity.this);
            recyclerView.setAdapter(adapter);
        } else {
            noText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.animation_enter,
                R.anim.animation_leave);
        Intent in = new Intent(FavouriteActivity.this, MainActivity.class);
        startActivity(in);
    }


}