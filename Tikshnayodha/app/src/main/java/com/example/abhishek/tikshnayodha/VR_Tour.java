package com.example.abhishek.tikshnayodha;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class VR_Tour extends AppCompatActivity implements View.OnClickListener{

    ImageButton vr_stadium,vr_museum,ar_cup1,ar_cup2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr__tour);


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

        vr_stadium=(ImageButton)findViewById(R.id.stadium_vr);
        vr_museum=(ImageButton)findViewById(R.id.museum_vr);
        ar_cup1=(ImageButton)findViewById(R.id.cup1_ar);
        ar_cup2=(ImageButton)findViewById(R.id.cup2_ar);
        vr_stadium.setOnClickListener(this);
        vr_museum.setOnClickListener(this);
        ar_cup1.setOnClickListener(this);
        ar_cup2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.stadium_vr){
            openApp(this, "com.Tik.stadium");
        }
        else if(view.getId()==R.id.museum_vr){
            openApp(this, "com.Tik.museum");
        }
        else if(view.getId()==R.id.cup1_ar){
            openApp(this, "com.tikshnayodha.fa_cup");
        }
        else if(view.getId()==R.id.cup2_ar){

        }
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
