package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList.LatestModel;

import static android.content.Context.MODE_PRIVATE;
import static bestwallpaper.hdgb.wallpapers.backgrounds.CollectionWallpaperAdapter.fromCollection;
import static bestwallpaper.hdgb.wallpapers.backgrounds.SearchWallpaperAdapter.fromSearchWallpaper;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {


    public static ArrayList<LatestModel> favList;
    public static ArrayList<LatestModel> profileData;
    public static boolean fromProfile = false;
    Context context;
    ViewHolder viewHolder;
    View itemLayoutView;
    String json1;
    Boolean exist = false;
    int pos;
    private FirebaseAnalytics mFirebaseAnalytics;

    public ProfileAdapter(Context contexts) {
        this.context = contexts;
    }

    public ProfileAdapter(Context contexts, ArrayList<LatestModel> itemsData) {
        profileData = itemsData;
        this.context = contexts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_item, parent, false);

        viewHolder = new ViewHolder(itemLayoutView);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder1, int position) {
        LatestModel model = profileData.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("Favourites_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        favList = new ArrayList<>();
        json1 = sharedPreferences.getString("fav_list", "");
        Type type1 = new TypeToken<ArrayList<LatestModel>>() {
        }.getType();
        favList = gson.fromJson(json1, type1);

        if (favList == null) {
            favList = new ArrayList<>();
        }
        if (favList.size() > 0) {
            for (int i = 0; i < favList.size(); i++) {
                if (favList.get(i).getId().equals(model.getId())) {
                    viewHolder1.favIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fav_red));
                }
            }
        }

        try {
            Picasso.with(context).load(model.getUrls().getSmall()).into(viewHolder1.imgViewIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder1.imgViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ProfileActivity", "Wallpaper", "View Wallpaper");
                fromProfile = true;
                fromCollection = false;
                fromSearchWallpaper = false;
                Log.e("Model position:-", String.valueOf(position));
                Intent intent = new Intent(context, ViewActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("fav_pos", model);
                intent.putExtra("From", "Favourite");
                context.startActivity(intent);
            }
        });

        viewHolder1.favIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                json1 = sharedPreferences.getString("fav_list", "");
                Type type1 = new TypeToken<ArrayList<LatestModel>>() {
                }.getType();
                favList = gson.fromJson(json1, type1);

                if (favList == null) {
                    favList = new ArrayList<>();
                }

                if (favList.size() > 0) {
                    for (int i = 0; i < favList.size(); i++) {
                        if (favList.get(i).getId().equals(profileData.get(position).getId())) {
                            exist = true;
                            pos = i;
                            break;
                        } else {
                            exist = false;
                        }
                    }
                }
                if (!exist) {
                    FirebaseAnalytics("ProfileActivity", "Wallpaper", "Add Wallpaper to favourites");
                    favList.add(profileData.get(position));
                    viewHolder1.favIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fav_red));
                } else {
                    FirebaseAnalytics("ProfileActivity", "Wallpaper", "Remove Wallpaper to favourites");
                    favList.remove(pos);
                    viewHolder1.favIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_fav_black));
                    exist = false;
                }

                Log.e("Fav_list :", favList.toString());
                Log.e("Fav_list size :", String.valueOf(favList.size()));

                json1 = gson.toJson(favList);
                editor.putString("fav_list", json1);
                editor.apply();
            }
        });
    }

    @Override
    public int getItemCount() {
        return profileData.size();
    }

    public void addAll(LatestModel model) {
        profileData.add(model);
        notifyDataSetChanged();
    }

    public void FirebaseAnalytics(String s1, String s2, String s3) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, s1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, s2);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, s3);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Log.e("Favourite Activity", "Analitics stored");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgViewIcon, favIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            imgViewIcon = itemLayoutView.findViewById(R.id.item_image);
            favIcon = itemLayoutView.findViewById(R.id.fav);
        }
    }


}

