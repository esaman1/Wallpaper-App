package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.PhotographerList.PhotographerModel;

public class SearchPhotographerAdapter extends RecyclerView.Adapter<SearchPhotographerAdapter.ViewHolder> {

    public static ArrayList<PhotographerModel> photographerList;
    Context context;
    ViewHolder viewHolder;
    View itemLayoutView;
    private FirebaseAnalytics mFirebaseAnalytics;

    public SearchPhotographerAdapter(Context contexts, ArrayList<PhotographerModel> itemsData) {
        photographerList = itemsData;
        this.context = contexts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_photographer_item, parent, false);

        viewHolder = new ViewHolder(itemLayoutView);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder1, int position) {

        PhotographerModel model = photographerList.get(position);

        try {
            Picasso.with(context).load(model.getProfileImage().getMedium()).into(viewHolder1.imgViewIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder1.userName.setText("@" + model.getUsername());
        viewHolder1.fullname.setText(model.getName());

        viewHolder1.rootRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("Search Photographer Activity", "View", "View Profile");
                Intent in = new Intent(context, ProfileActivity.class);
                in.putExtra("profile_from", "search");
                in.putExtra("PhotographerModel", model);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {

        return photographerList.size();
    }

    public void addAll(PhotographerModel model) {
        photographerList.add(model);
        notifyDataSetChanged();
    }

    public void FirebaseAnalytics(String s1, String s2, String s3) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, s1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, s2);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, s3);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Log.e("Search Photographer ", "Analitics stored");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgViewIcon;
        public TextView userName, fullname;
        RelativeLayout rootRelative;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            imgViewIcon = itemLayoutView.findViewById(R.id.item_image);
            userName = itemLayoutView.findViewById(R.id.userName);
            fullname = itemLayoutView.findViewById(R.id.name);
            rootRelative = itemLayoutView.findViewById(R.id.rootRelative);
        }
    }


}

