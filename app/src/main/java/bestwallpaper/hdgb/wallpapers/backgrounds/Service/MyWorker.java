package bestwallpaper.hdgb.wallpapers.backgrounds.Service;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    private final Context context;


    public MyWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
//        Log.e("MyWorker","doWork called for: " + this.getId());
//        Log.e("MyWorker","Service Running: " + MyService.isServiceRunning);
//        if(!MyService.isServiceRunning){
//            Log.e("MyWorker","starting service from doWork");
//            Intent intent = new Intent(this.context,MyService.class);
//
//            /*
//             * startForegroundService is similar to startService but with an implicit promise
//             * that the service will call startForeground once it begins running.
//             * The service is given an amount of time comparable to the ANR interval to do this,
//             * otherwise the system will automatically stop the service and declare the app ANR.
//             */
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//                ContextCompat.startForegroundService(context,intent);
//            }else{
//                context.startService(intent);
//            }
//        }
        return Result.success();
    }

    @Override
    public void onStopped() {
        Log.e("MyWorker", "onStopped called for: " + this.getId());
        super.onStopped();
    }
}
