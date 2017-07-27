package com.example.abhishek.tikshnayodha;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton gallery, qrScanner, navigation, tourGuide, back, extra_btn;
    View becon;
    IntentIntegrator qrScan;
    MySQLiteQuery mySQLiteQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }

        gallery = (ImageButton) findViewById(R.id.img_btn_gallery);
        becon = (ImageButton) findViewById(R.id.img_btn_beacon);
        qrScanner = (ImageButton) findViewById(R.id.img_btn_qr);
        navigation = (ImageButton) findViewById(R.id.img_btn_navigation);
        tourGuide = (ImageButton) findViewById(R.id.img_btn_tour);
        extra_btn = (ImageButton) findViewById(R.id.img_btn_extra);

        if(LoginActivity.language.equalsIgnoreCase("hindi")){
            gallery.setBackgroundResource(R.drawable.hbtn_gallery);
            becon.setBackgroundResource(R.drawable.hbeac);
            qrScanner.setBackgroundResource(R.drawable.hbtn_qr);
            navigation.setBackgroundResource(R.drawable.hvr);
            tourGuide.setBackgroundResource(R.drawable.hbtn_guide);
        }

        gallery.setOnClickListener(this);
        becon.setOnClickListener(this);
        qrScanner.setOnClickListener(this);
        navigation.setOnClickListener(this);
        tourGuide.setOnClickListener(this);
        extra_btn.setOnClickListener(this);
        qrScan = new IntentIntegrator(this);

        mySQLiteQuery = new MySQLiteQuery(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.img_btn_gallery) {

            Intent i = new Intent(getApplicationContext(), GalleryActivity.class);
            startActivity(i);

        }
        else if (v.getId() == R.id.img_btn_beacon) {

            Intent intent = new Intent(getApplicationContext(), BeaconActivity.class);
            intent.putExtra("txt","SEARCHING FOR NEARBY BEACON......");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Pair<View, String> pair1 = Pair.create(becon, "beacon");
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1);
                startActivity(intent, options.toBundle());
            }else
                startActivity(intent);
            //Toast.makeText(MenuActivity.this, "You Are In Becon Mode", Toast.LENGTH_SHORT).show();
        }
        else if (v.getId() == R.id.img_btn_qr) {

            //Toast.makeText(MenuActivity.this, "You Are In QR Mode", Toast.LENGTH_SHORT).show();
            qrScan.initiateScan();

        }
        else if (v.getId() == R.id.img_btn_navigation) {

            Intent intent = new Intent(getApplicationContext(), VR_Tour.class);
            startActivity(intent);
            //Toast.makeText(MenuActivity.this, "VR_Tour... ", Toast.LENGTH_SHORT).show();
        }
        else if (v.getId() == R.id.img_btn_tour) {
            //Toast.makeText(MenuActivity.this, "TOUR GUIDE... ", Toast.LENGTH_SHORT).show();
            openApp(this, "com.webrtc.audioconference");
        }
        else if (v.getId() == R.id.img_btn_extra) {

            PopupMenu popup = new PopupMenu(MenuActivity.this, extra_btn);
            popup.getMenuInflater().inflate(R.menu.extra_menu_items, popup.getMenu());//registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                public boolean onMenuItemClick(MenuItem item) {

                    if (item.getItemId() == R.id.help_desk) {
                        Intent intent = new Intent(getApplicationContext(), HelpDesk.class);
                        startActivity(intent);
                        //Toast.makeText(MenuActivity.this, "HELP DESK...", Toast.LENGTH_SHORT).show();
                    }
                    else if (item.getItemId() == R.id.history) {
                        Intent intent = new Intent(getApplicationContext(), History.class);
                        startActivity(intent);
                        //Toast.makeText(MenuActivity.this, "HISTORY...", Toast.LENGTH_SHORT).show();
                    }
                    else if (item.getItemId() == R.id.about_us) {
                        Intent i = new Intent(getApplicationContext(), AboutUs.class);
                        startActivity(i);
                        //Toast.makeText(MenuActivity.this, "ABOUT US...", Toast.LENGTH_SHORT).show();
                    }
                    else if (item.getItemId() == R.id.setting) {
                        Intent intent = new Intent(getApplicationContext(), Setting.class);
                        startActivity(intent);
                        //Toast.makeText(MenuActivity.this, "SETTINGs...", Toast.LENGTH_SHORT).show();
                    }
                    else if (item.getItemId() == R.id.exit) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                        builder.setTitle("Do you Want to Exit");
                        builder.setMessage("");
                        builder.setIcon(R.drawable.exit_icon);
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getApplicationContext(), "System Close", Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                finishAffinity();
                                System.exit(0);
                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create();
                        builder.show();
                        //Toast.makeText(MenuActivity.this, "EXIT...", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

            });
            popup.show();
        }
    }

    //Getting the scan results from Qr Scanner
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String id = "";
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {         //if qrcode has nothing in it
                //Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {          //if qr contains data
                try {
                    JSONObject obj = new JSONObject(result.getContents());   //Decoding Qr code
                    id += obj.getString("image_id");      //artifact UID
                } catch (JSONException e) {
                    e.printStackTrace();
                    //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        Intent i = new Intent(getApplicationContext(), QRActivity.class);
        i.putExtra("image_id", id);
        startActivity(i);
    }

    public static boolean openApp(Context context, String pkg_name) {
        PackageManager manager = context.getPackageManager();
        Intent intent = manager.getLaunchIntentForPackage(pkg_name);
        if (intent == null) {
            return false;
        } else {
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(intent);
            return true;
        }

    }
}