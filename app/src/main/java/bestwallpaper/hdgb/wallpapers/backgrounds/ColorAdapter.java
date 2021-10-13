package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {

    public static ArrayList<Integer> colorList;
    Context context;
    ViewHolder viewHolder;
    View itemLayoutView;
    private ColorClickListener mClickListener = null;
    private ShapeColorClickListener mShapeClickListener = null;

    public ColorAdapter(ColorClickListener listener, ShapeColorClickListener listener1, ArrayList<Integer> colorList, Context context) {
        ColorAdapter.colorList = colorList;
        this.context = context;
        this.mClickListener = listener;
        this.mShapeClickListener = listener1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_item, parent, false);

        viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder1, int position) {

        try {
            viewHolder1.imgViewIcon.setBackgroundColor(context.getResources().getColor(colorList.get(position)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewHolder1.imgViewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ViewActivity.shapeColorBoolean) {
                    if (mShapeClickListener != null)
                        mShapeClickListener.onColorClick(position);
                } else {
                    if (mClickListener != null)
                        mClickListener.onColorClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return colorList.size();
    }

    public interface ColorClickListener {
        void onColorClick(int position);
    }

    public interface ShapeColorClickListener {
        void onColorClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            imgViewIcon = itemLayoutView.findViewById(R.id.item_image);

        }
    }


}

