package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.CollectionList.ResultsItem;

public class SearchCollectionAdapter extends RecyclerView.Adapter<SearchCollectionAdapter.ViewHolder> {

    public static ArrayList<ResultsItem> collectionsModels;
    Context context;
    ViewHolder viewHolder;
    View itemLayoutView;
    private FirebaseAnalytics mFirebaseAnalytics;

    public SearchCollectionAdapter(Context contexts, ArrayList<ResultsItem> itemsData) {
        collectionsModels = itemsData;
        this.context = contexts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_collection_item, parent, false);

        viewHolder = new ViewHolder(itemLayoutView);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder1, int position) {

        ResultsItem model = collectionsModels.get(position);

        try {
            Picasso.with(context).load(model.getCoverPhoto().getUrls().getSmall()).into(viewHolder1.imgViewIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        viewHolder1.total.setText(model.getTotalPhotos() + " Photos");
        viewHolder1.title.setText(model.getTitle());
        viewHolder1.bdName.setText(model.getUser().getName());

        viewHolder1.imgViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("Search Collection Activity", "Wallpaper", "View Wallpaper");
                Intent intent = new Intent(context, CollectionWallpapersActivity.class);
                intent.putExtra("Collection_Result", model);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return collectionsModels.size();
    }

    public void addAll(List<ResultsItem> model) {
        collectionsModels.addAll(model);
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

        public ImageView imgViewIcon;
        public TextView total, title, bdName;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            imgViewIcon = itemLayoutView.findViewById(R.id.item_image);
            total = itemLayoutView.findViewById(R.id.total);
            title = itemLayoutView.findViewById(R.id.title);
            bdName = itemLayoutView.findViewById(R.id.bdName);
        }
    }


}

