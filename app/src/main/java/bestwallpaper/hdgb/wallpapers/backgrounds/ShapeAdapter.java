package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.ShapeModel;

import static android.content.Context.MODE_PRIVATE;

public class ShapeAdapter extends RecyclerView.Adapter<ShapeAdapter.ViewHolder> {

    //    474343
    public static ArrayList<ShapeModel> shapeList;
    public static int currentShape;
    Context context;
    ViewHolder viewHolder;
    View itemLayoutView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private FirebaseAnalytics mFirebaseAnalytics;

    public ShapeAdapter(ArrayList<ShapeModel> shapeList, Context context) {
        ShapeAdapter.shapeList = shapeList;
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Shape_pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        currentShape = sharedPreferences.getInt("shapeID", R.drawable.ic_shape1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shape_item, parent, false);
        viewHolder = new ViewHolder(itemLayoutView);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder1, int position) {

        ShapeModel model = shapeList.get(position);
        currentShape = sharedPreferences.getInt("shapeID", R.drawable.ic_shape1);
        if (model.getShapeImage() == currentShape) {
            viewHolder1.shapeCard.setCardBackgroundColor(context.getResources().getColor(R.color.cardcolor));
        } else {
            viewHolder1.shapeCard.setCardBackgroundColor(context.getResources().getColor(R.color.white));
        }
        Glide.with(context)
                .load(model.getShapeImage())
                .into(viewHolder1.shapeImage);
        viewHolder1.shapeName.setText(model.getShapeName());

        viewHolder1.shapeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("ShapeActivity", "Shape", "Apply shape");
                editor.putInt("shapeID", model.getShapeImage());
                editor.apply();
//                Toast.makeText(context,"Shape Applied",Toast.LENGTH_LONG).show();
                ((ShapesActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return shapeList.size();
    }

    public void FirebaseAnalytics(String s1, String s2, String s3) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, s1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, s2);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, s3);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Log.e("Shape Activity", "Analitics stored");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView shapeImage;
        public TextView shapeName;
        CardView shapeCard;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            shapeImage = itemLayoutView.findViewById(R.id.shapeImage);
            shapeName = itemLayoutView.findViewById(R.id.shapeName);
            shapeCard = itemLayoutView.findViewById(R.id.shapeCard);
        }
    }

}