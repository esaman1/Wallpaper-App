package bestwallpaper.hdgb.wallpapers.backgrounds.Service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class MyService extends Service {

    public static boolean isServiceRunning;
    private final ScreenLockReceiver screenLockReceiver;
    int intervalTime = 16;
    Handler handler;

    public MyService() {
        Log.e("MyService", "constructor called");
        isServiceRunning = false;
        screenLockReceiver = new ScreenLockReceiver();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyService", "onCreate called");
        isServiceRunning = true;

        // register receiver to listen for screen on events
        IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
        registerReceiver(screenLockReceiver, filter);

        SharedPreferences sharedPreferences1 = getSharedPreferences("Auto_pref", MODE_PRIVATE);
        intervalTime = sharedPreferences1.getInt("Interval", intervalTime);
        Log.e("MyService", "Interval time  : " + intervalTime);
        handler = new Handler(Looper.getMainLooper());

        if (intervalTime == 15 || intervalTime == 30) {
            handler.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    handler.postDelayed(this, intervalTime * 60 * 1000);
                    Log.e("My service", "onReceive called:interval time " + intervalTime + " Min");
                    new Util().setRandomWallpaper(MyService.this);
                }
            }, 0);
        } else {
            handler.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    handler.postDelayed(this, intervalTime * 3600 * 1000);
                    Log.e("My service", "onReceive called:interval time " + intervalTime + " Hour");
                    new Util().setRandomWallpaper(MyService.this);
                }
            }, 0);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyService", "onStartCommand called");

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("MyService", "onDestroy called");
        isServiceRunning = false;
        stopForeground(true);

        // unregister receiver
        unregisterReceiver(screenLockReceiver);
        // call MyReceiver which will restart this service via a worker
        Intent broadcastIntent = new Intent(this, MyReceiver.class);
        sendBroadcast(broadcastIntent);

        super.onDestroy();
    }

    // Not getting called on Xiaomi Redmi Note 7S even when autostart permission is granted
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("MyService", "onTaskRemoved called");
        super.onTaskRemoved(rootIntent);
    }
}
