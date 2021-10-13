package bestwallpaper.hdgb.wallpapers.backgrounds.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class ScreenLockReceiver extends BroadcastReceiver {


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_SCREEN_ON)) {
            Log.e("ScreenLockReceiver", "onReceive called: screen on");
//            new Util().setRandomWallpaper(context);
        } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
            Log.e("ScreenLockReceiver", "onReceive called: screen off");
        } else if (action.equals(Intent.ACTION_USER_PRESENT)) {
            Log.e("ScreenLockReceiver", "onReceive called: screen unlocked");

        }
    }
}
