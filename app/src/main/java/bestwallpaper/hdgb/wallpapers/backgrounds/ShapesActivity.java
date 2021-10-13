package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.ShapeModel;

public class ShapesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    ArrayList<ShapeModel> shapeList = new ArrayList<>();
    ShapeAdapter shapeAdapter;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shapes);
        this.getSupportActionBar().hide();

        imageView = findViewById(R.id.back1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AddToList();

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView = findViewById(R.id.shapeRecycler);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        shapeAdapter = new ShapeAdapter(shapeList, ShapesActivity.this);
        recyclerView.setAdapter(shapeAdapter);

    }

    public void AddToList() {
        shapeList.clear();
        shapeList.add(new ShapeModel(R.drawable.ic_shape1, "Circle"));
        shapeList.add(new ShapeModel(R.drawable.ic_spiral, "Spiral"));
        shapeList.add(new ShapeModel(R.drawable.ic_leaf, "Leaf"));
        shapeList.add(new ShapeModel(R.drawable.ic_hearts, "Hearts"));
        shapeList.add(new ShapeModel(R.drawable.ic_sun, "Sun"));
        shapeList.add(new ShapeModel(R.drawable.ic_curve_diamond, "Curve Diamond"));
        shapeList.add(new ShapeModel(R.drawable.ic_diamond, "Diamond Shape"));
        shapeList.add(new ShapeModel(R.drawable.ic_love, "Love Hearts"));
        shapeList.add(new ShapeModel(R.drawable.ic_ellipse, "Ellipse"));
        shapeList.add(new ShapeModel(R.drawable.ic_triangle, "Triangle"));
        shapeList.add(new ShapeModel(R.drawable.ic_diamond1, "Diamond"));
        shapeList.add(new ShapeModel(R.drawable.ic_sun1, "Sun"));
        shapeList.add(new ShapeModel(R.drawable.ic_star_moon, "Star Moon"));
        shapeList.add(new ShapeModel(R.drawable.ic_tree, "Heart Tree"));
        shapeList.add(new ShapeModel(R.drawable.ic_heart_king, "Heart King"));
    }
}