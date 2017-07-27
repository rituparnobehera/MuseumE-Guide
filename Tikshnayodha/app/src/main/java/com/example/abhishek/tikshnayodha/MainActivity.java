package com.example.abhishek.tikshnayodha;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.daimajia.androidanimations.library.Techniques;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //for run time users permission
    int PERMISSION_ALL=1;
    String[] PERMISSIONS={
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    //for View Animation
    final public static Techniques ANIMATION_TECH[]={Techniques.Flash, Techniques.Pulse, Techniques.RubberBand, Techniques.Shake,
                        Techniques.Swing, Techniques.Wobble, Techniques.Bounce, Techniques.Tada, Techniques.StandUp, Techniques.Wave};

    /*YoYo.with(tq[position]) .duration(700) .repeat(5) .playOn(findViewById(R.id.textView));*/

    //Global Variable Decleration
    ImageButton frontImage_butn;
    //ipv4 for local server connection 172.26.21.22    192.168.43.186
    public static final String IPV4_URL="http://abhidev.pe.hu/old_trafford_museum";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!hasPermissions(this,PERMISSIONS)){
            ActivityCompat.requestPermissions(this,PERMISSIONS,PERMISSION_ALL);
        }

        frontImage_butn=(ImageButton)findViewById(R.id.img_btn_museum_blink);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.anim);
        frontImage_butn.startAnimation(myFadeInAnimation);
        frontImage_butn.setOnClickListener(this);//set onclicklistener to image button

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            } else {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions){

        if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.M && context!=null && permissions!=null){
            for(String permission:permissions){
                if(ActivityCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.img_btn_museum_blink);
        {
            //Transition  to LoginActivity Page
            Intent intent_login=new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent_login);

        }
    }
}

/*Toasty.error(getApplicationContext(), "This is an error toast.", Toast.LENGTH_SHORT, true).show();
Toasty.success(getApplicationContext(), "Success!", Toast.LENGTH_SHORT, true).show();
Toasty.info(getApplicationContext(), "Here is some info for you.", Toast.LENGTH_SHORT, true).show();
Toasty.warning(getApplicationContext(), "Beware of the dog.", Toast.LENGTH_SHORT, true).show();*/
