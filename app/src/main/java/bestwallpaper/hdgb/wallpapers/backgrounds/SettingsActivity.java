package bestwallpaper.hdgb.wallpapers.backgrounds;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.google.firebase.analytics.FirebaseAnalytics;

import bestwallpaper.hdgb.wallpapers.backgrounds.Service.MyService;

public class SettingsActivity extends AppCompatActivity {
    ImageView imageView;
    LinearLayout interval, screen, source, theme;
    LinearLayout recommend, rate, feedback;
    TextView home, lock, both;
    TextView light, dark, system;
    String ApplyTo = "Both";
    Switch autoSwitch;
    int REQUEST_PERMISSION = 786;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView favriteSource, randomSource;
    String sourceOf = "Random";
    Boolean setAutoWallpaper = false;
    TextView sourceTextview, screenTextview, themeTextview, intervalTextview;
    CheckBox wifi, charging;
    Boolean Wifi = true, Charging = true;
    String currentTheme = "Light";
    RadioButton radio, min15, min30, h1, h3, h6, h12, h24, h48;
    RadioGroup radio_group;
    int intervalTime = 15;
    Intent serviceIntent;
    PendingIntent pendingIntent;
    TextView mVersion;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.getSupportActionBar().hide();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getBaseContext());
        imageView = findViewById(R.id.back1);
        interval = findViewById(R.id.l1);
        screen = findViewById(R.id.l2);
        source = findViewById(R.id.l3);
        theme = findViewById(R.id.l4);
        autoSwitch = findViewById(R.id.wSwitch);
        sourceTextview = findViewById(R.id.t17);
        screenTextview = findViewById(R.id.t15);
        themeTextview = findViewById(R.id.t19);
        wifi = findViewById(R.id.wifi);
        charging = findViewById(R.id.charging);
        recommend = findViewById(R.id.l5);
        rate = findViewById(R.id.l6);
        feedback = findViewById(R.id.l7);
        intervalTextview = findViewById(R.id.t13);
        mVersion = findViewById(R.id.t31);
        mVersion.setText(BuildConfig.VERSION_NAME);

        serviceIntent = new Intent(this, MyService.class);
        sharedPreferences = getSharedPreferences("Auto_pref", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        setAutoWallpaper = sharedPreferences.getBoolean("Auto_Set", false);

        autoSwitch.setChecked(setAutoWallpaper);

        setData();
        Intent myIntent = new Intent(SettingsActivity.this, MyService.class);
        pendingIntent = PendingIntent.getService(SettingsActivity.this, 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        autoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission ", "Already Granted");
                } else {
                    requestPermission();
                }
                if (isChecked) {
                    FirebaseAnalytics("SettingsActivity", "Set", "Auto Wallpaper on");
                    editor.putBoolean("Auto_Set", true);
                    editor.putString("SourceOf", sourceOf);
                    editor.putString("ApplyTo", ApplyTo);
                    editor.putBoolean("Wifi", Wifi);
                    editor.putBoolean("Charging", Charging);
                    editor.putInt("Interval", intervalTime);
                    editor.apply();
                    startService();
                } else {
                    FirebaseAnalytics("SettingsActivity", "Set", "Auto Wallpaper off");
                    editor.putBoolean("Auto_Set", false);
                    editor.apply();
                    stopService();
                }
            }
        });

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("SettingsActivity", "Set", "wifi");
                Wifi = wifi.isChecked();
                editor.putBoolean("Wifi", Wifi);
                editor.apply();
                stopService();
                startService();
            }
        });

        charging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("SettingsActivity", "Set", "charging");
                Charging = charging.isChecked();
                editor.putBoolean("Charging", Charging);
                editor.apply();
                stopService();
                startService();
            }
        });

        interval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intervalDialog();
            }
        });

        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wallpaperDialog();
            }
        });

        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sourceDialog();
            }
        });

        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeDialog();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("SettingsActivity", "Share", "Recommend App");
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAnalytics("SettingsActivity", "Rate", "Rate App");
                try {
                    Intent rateIntent = rateIntentForUrl("market://details");
                    startActivity(rateIntent);
                } catch (ActivityNotFoundException e) {
                    Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
                    startActivity(rateIntent);
                }
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });
    }

    public void sendFeedback() {
        FirebaseAnalytics("SettingsActivity", "Feedback", "Give Feedback");
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.parse("mailto:" + "admin@hotmail.com"));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email via..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(),
                    "There are no email clients installed.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.SET_WALLPAPER)
                    == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                requestPermissions(new String[]{android.Manifest.permission.SET_WALLPAPER, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }
    }

    public void startService() {
        Log.e("SettingsActivity :", "startService called");
        if (!MyService.isServiceRunning) {
            ContextCompat.startForegroundService(this, serviceIntent);
            startService(serviceIntent);
        }

    }

    public void stopService() {
        Log.e("SettingsActivity :", "stopService called");

        if (MyService.isServiceRunning) {
            stopService(serviceIntent);
        }
    }


    public void intervalDialog() {
        FirebaseAnalytics("SettingsActivity", "Set", "Interval");
        AlertDialog.Builder alertadd = new AlertDialog.Builder(SettingsActivity.this);
        LayoutInflater factory = LayoutInflater.from(SettingsActivity.this);
        final View view = factory.inflate(R.layout.interval_popup, null);
        alertadd.setView(view);
        radio_group = view.findViewById(R.id.radio_group);
        min15 = view.findViewById(R.id.min15);
        min30 = view.findViewById(R.id.min30);
        h1 = view.findViewById(R.id.h1);
        h3 = view.findViewById(R.id.h3);
        h6 = view.findViewById(R.id.h6);
        h12 = view.findViewById(R.id.h12);
        h24 = view.findViewById(R.id.h24);
        h48 = view.findViewById(R.id.h48);
        intervalTime = sharedPreferences.getInt("Interval", intervalTime);
        switch (intervalTime) {
            case 16:
                min15.setChecked(true);
                break;
            case 30:
                min30.setChecked(true);
                break;
            case 1:
                h1.setChecked(true);
                break;
            case 3:
                h3.setChecked(true);
                break;
            case 6:
                h6.setChecked(true);
                break;
            case 12:
                h12.setChecked(true);
                break;
            case 24:
                h24.setChecked(true);
                break;
            case 48:
                h48.setChecked(true);
                break;
            default:
                min15.setChecked(true);
                break;
        }
        alertadd.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedId = radio_group.getCheckedRadioButtonId();
                radio = view.findViewById(selectedId);
                if (selectedId == -1) {
                    Toast.makeText(SettingsActivity.this, "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    if (radio.getId() == R.id.min15) intervalTime = 15;
                    if (radio.getId() == R.id.min30) intervalTime = 30;
                    if (radio.getId() == R.id.h1) intervalTime = 1;
                    if (radio.getId() == R.id.h3) intervalTime = 3;
                    if (radio.getId() == R.id.h6) intervalTime = 6;
                    if (radio.getId() == R.id.h12) intervalTime = 12;
                    if (radio.getId() == R.id.h24) intervalTime = 24;
                    if (radio.getId() == R.id.h48) intervalTime = 48;

                }
                editor.putInt("Interval", intervalTime);
                editor.apply();
                stopService();
                startService();
                setData();
                dialog.dismiss();
            }
        });

        alertadd.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertadd.show();
    }

    public void setData() {

        sourceOf = sharedPreferences.getString("SourceOf", "Random");
        if (sourceOf.equals("Favourite")) {
            sourceTextview.setText("Favourite Wallpapers");
        } else {
            sourceTextview.setText("Random Wallpapers");
        }

        ApplyTo = sharedPreferences.getString("ApplyTo", "Both");
        if (ApplyTo.equals("Home")) {
            screenTextview.setText("Home Screen");
        } else if (ApplyTo.equals("Lock")) {
            screenTextview.setText("Lock Screen");
        } else {
            screenTextview.setText("Home and Lock Screen");
        }

        Wifi = sharedPreferences.getBoolean("Wifi", true);
        wifi.setChecked(Wifi);
        Charging = sharedPreferences.getBoolean("Charging", true);
        charging.setChecked(Charging);

        currentTheme = sharedPreferences.getString("Theme", "Light");
        if (currentTheme.equals("Light")) {
            themeTextview.setText("Light");
        } else if (currentTheme.equals("Dark")) {
            themeTextview.setText("Dark");
        } else {
            themeTextview.setText("System");
        }
        if (currentTheme.equals("Light")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentTheme.equals("Dark")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }

        intervalTime = sharedPreferences.getInt("Interval", intervalTime);
        switch (intervalTime) {
            case 15:
                intervalTextview.setText("Each Wallpaper will last a minimum of 15 minutes.");
                break;
            case 30:
                intervalTextview.setText("Each Wallpaper will last a minimum of 30 minutes.");
                break;
            case 1:
                intervalTextview.setText("Each Wallpaper will last a minimum of 1 hours.");
                break;
            case 3:
                intervalTextview.setText("Each Wallpaper will last a minimum of 3 hours.");
                break;
            case 6:
                intervalTextview.setText("Each Wallpaper will last a minimum of 6 hours.");
                break;
            case 12:
                intervalTextview.setText("Each Wallpaper will last a minimum of 12 hours.");
                break;
            case 24:
                intervalTextview.setText("Each Wallpaper will last a minimum of 24 hours.");
                break;
            case 48:
                intervalTextview.setText("Each Wallpaper will last a minimum of 48 hours.");
                break;
            default:
                intervalTextview.setText("Each Wallpaper will last a minimum of 15 minutes.");
                break;
        }
    }

    public void themeDialog() {
        FirebaseAnalytics("SettingsActivity", "Set", "Set theme");
        AlertDialog.Builder alertadd = new AlertDialog.Builder(SettingsActivity.this);
        LayoutInflater factory = LayoutInflater.from(SettingsActivity.this);
        final View view = factory.inflate(R.layout.theme_popup, null);
        alertadd.setView(view);

        light = view.findViewById(R.id.light);
        dark = view.findViewById(R.id.dark);
        system = view.findViewById(R.id.system);
        currentTheme = sharedPreferences.getString("Theme", "Light");
        if (currentTheme.equals("Light")) {
            light.setTextColor(getResources().getColor(R.color.teal_700));
            dark.setTextColor(getResources().getColor(R.color.black));
            system.setTextColor(getResources().getColor(R.color.black));
        } else if (currentTheme.equals("Dark")) {
            light.setTextColor(getResources().getColor(R.color.black));
            dark.setTextColor(getResources().getColor(R.color.teal_700));
            system.setTextColor(getResources().getColor(R.color.black));
        } else {
            light.setTextColor(getResources().getColor(R.color.black));
            dark.setTextColor(getResources().getColor(R.color.black));
            system.setTextColor(getResources().getColor(R.color.teal_700));
        }
        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light.setTextColor(getResources().getColor(R.color.teal_700));
                dark.setTextColor(getResources().getColor(R.color.black));
                system.setTextColor(getResources().getColor(R.color.black));
                currentTheme = "Light";

            }
        });
        dark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dark.setTextColor(getResources().getColor(R.color.teal_700));
                light.setTextColor(getResources().getColor(R.color.black));
                system.setTextColor(getResources().getColor(R.color.black));
                currentTheme = "Dark";

            }
        });
        system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                system.setTextColor(getResources().getColor(R.color.teal_700));
                dark.setTextColor(getResources().getColor(R.color.black));
                light.setTextColor(getResources().getColor(R.color.black));
                currentTheme = "System";
            }
        });

        alertadd.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putString("Theme", currentTheme);
                editor.apply();
                setData();

                dialog.dismiss();
            }
        });
        alertadd.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertadd.show();

    }

    public void sourceDialog() {
        FirebaseAnalytics("SettingsActivity", "Set", "Set source");
        AlertDialog.Builder alertadd = new AlertDialog.Builder(SettingsActivity.this);
        LayoutInflater factory = LayoutInflater.from(SettingsActivity.this);
        final View view = factory.inflate(R.layout.source_popup, null);
        alertadd.setView(view);
        favriteSource = view.findViewById(R.id.favourite_source);
        randomSource = view.findViewById(R.id.random_source);

        sourceOf = sharedPreferences.getString("SourceOf", "Favourite");
        if (sourceOf.equals("Favourite")) {
            favriteSource.setTextColor(getResources().getColor(R.color.teal_700));
            randomSource.setTextColor(getResources().getColor(R.color.black));

        } else {
            randomSource.setTextColor(getResources().getColor(R.color.teal_700));
            favriteSource.setTextColor(getResources().getColor(R.color.black));
        }
        favriteSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favriteSource.setTextColor(getResources().getColor(R.color.teal_700));
                randomSource.setTextColor(getResources().getColor(R.color.black));
                sourceOf = "Favourite";
            }
        });
        randomSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomSource.setTextColor(getResources().getColor(R.color.teal_700));
                favriteSource.setTextColor(getResources().getColor(R.color.black));
                sourceOf = "Random";
            }
        });

        alertadd.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putString("SourceOf", sourceOf);
                editor.apply();
                stopService();
                startService();
                setData();
                dialog.dismiss();
            }
        });
        alertadd.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertadd.show();
    }

    public void wallpaperDialog() {
        FirebaseAnalytics("SettingsActivity", "Set", "Apply Wallpaper on");
        AlertDialog.Builder alertadd = new AlertDialog.Builder(SettingsActivity.this);
        LayoutInflater factory = LayoutInflater.from(SettingsActivity.this);
        final View view = factory.inflate(R.layout.wallpaper_popup, null);
        alertadd.setView(view);

        home = view.findViewById(R.id.home);
        lock = view.findViewById(R.id.lock);
        both = view.findViewById(R.id.both);

        ApplyTo = sharedPreferences.getString("ApplyTo", "Both");
        if (ApplyTo.equals("Home")) {
            home.setTextColor(getResources().getColor(R.color.teal_700));
            lock.setTextColor(getResources().getColor(R.color.black));
            both.setTextColor(getResources().getColor(R.color.black));
        } else if (ApplyTo.equals("Lock")) {
            home.setTextColor(getResources().getColor(R.color.black));
            lock.setTextColor(getResources().getColor(R.color.teal_700));
            both.setTextColor(getResources().getColor(R.color.black));
        } else {
            home.setTextColor(getResources().getColor(R.color.black));
            lock.setTextColor(getResources().getColor(R.color.black));
            both.setTextColor(getResources().getColor(R.color.teal_700));
        }
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyTo = "Home";
                home.setTextColor(getResources().getColor(R.color.teal_700));
                lock.setTextColor(getResources().getColor(R.color.black));
                both.setTextColor(getResources().getColor(R.color.black));
            }
        });

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyTo = "Lock";
                home.setTextColor(getResources().getColor(R.color.black));
                lock.setTextColor(getResources().getColor(R.color.teal_700));
                both.setTextColor(getResources().getColor(R.color.black));
            }
        });

        both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyTo = "Both";
                home.setTextColor(getResources().getColor(R.color.black));
                lock.setTextColor(getResources().getColor(R.color.black));
                both.setTextColor(getResources().getColor(R.color.teal_700));
            }
        });
        alertadd.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.putString("ApplyTo", ApplyTo);
                editor.apply();
                stopService();
                startService();
                setData();
                dialog.dismiss();

            }
        });
        alertadd.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertadd.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.overridePendingTransition(R.anim.animation_enter,
                R.anim.animation_leave);

        Intent in = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(in);
    }

    public void FirebaseAnalytics(String s1, String s2, String s3) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, s1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, s2);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, s3);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        Log.e("Favourite Activity", "Analitics stored");
    }


}