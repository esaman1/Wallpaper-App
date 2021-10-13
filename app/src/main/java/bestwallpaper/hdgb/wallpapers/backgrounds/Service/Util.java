package bestwallpaper.hdgb.wallpapers.backgrounds.Service;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

import bestwallpaper.hdgb.wallpapers.backgrounds.Model.LatestList.LatestModel;

import static android.content.Context.MODE_PRIVATE;

public class Util {

    public static ArrayList<LatestModel> wallpaperList;
    String json1;
    String SourceOf = "Random";
    String ApplyTo = "Both";
    Boolean Wifi = true, Charging = true;
    Context context;
    Boolean setAutoWallpaper = true;
    int intervalTime = 15;

    public static int getRandomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static boolean hasPermission(Context context, String permission) {
        int result = ContextCompat.checkSelfPermission(context, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static long getFileSizeInKb(File file) {
        return file.length() / 1024;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setRandomWallpaper(Context context) {
        this.context = context;
        // check for permission
        if (!hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Log.e("Util", "App does not have WRITE_EXTERNAL_STORAGE permission");
            return;
        }

//        *****************************
        new UtilAsync().execute();

//        *****************************
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void changeWallpaper(Bitmap bitmap) {
        WallpaperManager manager = WallpaperManager.getInstance(context);
        try {
            if (ApplyTo.equals("Home")) {
                manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
            } else if (ApplyTo.equals("Lock")) {
                manager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK);
            } else {
                manager.setBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            Bitmap bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new java.net.URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            BatteryManager myBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            if (Wifi == true && Charging == false) {
                if (wifi.isWifiEnabled()) {
                    changeWallpaper(bitmap);
                }
            } else if (Charging == true && Wifi == false) {
                if (myBatteryManager.isCharging()) {
                    changeWallpaper(bitmap);
                }
            } else if (Charging == true && Wifi == true) {
                if (myBatteryManager.isCharging() && wifi.isWifiEnabled()) {
                    changeWallpaper(bitmap);
                }
            } else {
                changeWallpaper(bitmap);
            }
        }
    }

    public class UtilAsync extends AsyncTask<Void, Void, Void> {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected Void doInBackground(Void... voids) {
            Bitmap bitmap = null;
            InputStream inputStream;
            SharedPreferences sharedPreferences1 = context.getSharedPreferences("Auto_pref", MODE_PRIVATE);
            setAutoWallpaper = sharedPreferences1.getBoolean("Auto_Set", false);

            if (setAutoWallpaper) {

                SourceOf = sharedPreferences1.getString("SourceOf", SourceOf);
                ApplyTo = sharedPreferences1.getString("ApplyTo", ApplyTo);
                Wifi = sharedPreferences1.getBoolean("Wifi", Wifi);
                Charging = sharedPreferences1.getBoolean("Charging", Charging);
                intervalTime = sharedPreferences1.getInt("Interval", intervalTime);

                Log.e("Util", "SourceOf : " + SourceOf + " | Apply on" + ApplyTo);
                Log.e("Util", "On Wifi  : " + Wifi + " | On Charging :" + Charging);

                if (SourceOf.equals("Favourite")) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Favourites_pref", MODE_PRIVATE);
                    Gson gson = new Gson();

                    json1 = sharedPreferences.getString("fav_list", "");
                    Type type1 = new TypeToken<ArrayList<LatestModel>>() {
                    }.getType();
                    wallpaperList = new ArrayList<>();
                    wallpaperList = gson.fromJson(json1, type1);
                    try {
                        if (wallpaperList != null && wallpaperList.size() > 0) {
                            Log.e("Util", "Size: " + wallpaperList.size());
                            int randomFilePathIndex = getRandomInt(0, wallpaperList.size() - 1);
                            LatestModel randomFile = wallpaperList.get(randomFilePathIndex);
                            String randomFilePath = randomFile.getUrls().getRegular();
                            new GetImageFromUrl().execute(randomFilePath);
                        } else {
                            Log.e("Util", "Favourite List is empty");
                        }
                    } catch (Exception e) {
                        Log.e("Util", e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("Auto_pref", MODE_PRIVATE);
                    Gson gson = new Gson();

                    json1 = sharedPreferences.getString("random_list", "");
                    Type type1 = new TypeToken<ArrayList<LatestModel>>() {
                    }.getType();
                    wallpaperList = new ArrayList<>();
                    wallpaperList = gson.fromJson(json1, type1);
                    try {
                        if (wallpaperList != null && wallpaperList.size() > 0) {
                            Log.e("Util1", "Size: " + wallpaperList.size());
                            int randomFilePathIndex = getRandomInt(0, wallpaperList.size() - 1);
                            LatestModel randomFile = wallpaperList.get(randomFilePathIndex);
                            String randomFilePath = randomFile.getUrls().getRegular();
                            new GetImageFromUrl().execute(randomFilePath);
                        } else {
                            Log.e("Util", "Random List is empty");
                        }
                    } catch (Exception e) {
                        Log.e("Util", e.getMessage());
                        e.printStackTrace();
                    }
                }

            }
            return null;
        }
    }
}
