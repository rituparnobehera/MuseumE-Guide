package com.example.abhishek.tikshnayodha;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class AboutUs extends AppCompatActivity {

    TextView organization,nist,parulHills;
    TextView developer,abhi,alokik,abhishek_mail,alokik_mail;
    TextView designer,ritu,ram,ritu_mail,ram_mail;
    ImageView abhishek_pic,alokik_pic,ritu_pic,ram_pic;
    Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        display=getWindowManager().getDefaultDisplay();

        int height;
        int width;
        width=display.getWidth();
        height=display.getHeight();
        organization=(TextView)findViewById(R.id.organization);
        nist=(TextView)findViewById(R.id.nist);
        parulHills=(TextView)findViewById(R.id.palurHills);
        developer=(TextView)findViewById(R.id.developer);
        abhi=(TextView)findViewById(R.id.abhishek);
        alokik=(TextView)findViewById(R.id.alokik);
        abhishek_mail=(TextView)findViewById(R.id.abhishek_mail);
        alokik_mail=(TextView)findViewById(R.id.alokik_mail);
        designer=(TextView)findViewById(R.id.designer);
        ritu=(TextView)findViewById(R.id.ritu);
        ram=(TextView)findViewById(R.id.ram);
        ritu_mail=(TextView)findViewById(R.id.ritu_mail);
        ram_mail=(TextView)findViewById(R.id.ram_mail);

        abhishek_pic=(ImageView)findViewById(R.id.abhishek_pic);
        alokik_pic=(ImageView)findViewById(R.id.alokik_pic);
        ritu_pic=(ImageView)findViewById(R.id.ritu_pic);
        ram_pic=(ImageView)findViewById(R.id.ram_pic);

        mAnimation2 = new TranslateAnimation(0f, 0f, 500, -500);
        mAnimation2.setDuration(20000);
        mAnimation2.setStartOffset(000);
        mAnimation2.setRepeatMode(Animation.RESTART);
        mAnimation2.setRepeatCount(Animation.INFINITE);
        mAnimation2.setFillAfter(true);

        organization.setAnimation(mAnimation2);
        nist.setAnimation(mAnimation2);
        parulHills.setAnimation(mAnimation2);
        developer.setAnimation(mAnimation2);
        abhishek_pic.setAnimation(mAnimation2);
        alokik_pic.setAnimation(mAnimation2);
        abhi.setAnimation(mAnimation2);
        alokik.setAnimation(mAnimation2);
        abhishek_mail.setAnimation(mAnimation2);
        alokik_mail.setAnimation(mAnimation2);
        designer.setAnimation(mAnimation2);
        ritu_pic.setAnimation(mAnimation2);
        ram_pic.setAnimation(mAnimation2);
        ritu.setAnimation(mAnimation2);
        ram.setAnimation(mAnimation2);
        ritu_mail.setAnimation(mAnimation2);
        ram_mail.setAnimation(mAnimation2);

        organization.setOnTouchListener(onTouch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.innermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case (R.id.setting):
                Intent i = new Intent(getApplicationContext(), Setting.class);
                startActivity(i);
                return true;

            case (R.id.exit):
                final AlertDialog.Builder builder = new AlertDialog.Builder(AboutUs.this);
                builder.setTitle("Do you Want to Exit");
                builder.setMessage("");
                builder.setIcon(R.drawable.exit_icon);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "System Close", Toast.LENGTH_LONG).show();
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
                return true;

            case (R.id.help_desk):
                Intent intent = new Intent(getApplicationContext(), HelpDesk.class);
                startActivity(intent);
                return true;

            case (R.id.back):
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    TranslateAnimation mAnimation2;

    View.OnTouchListener onTouch = new View.OnTouchListener(){

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                organization.setSelected(false);
                organization.setMovementMethod(new ScrollingMovementMethod());
            }
            if (event.getAction() == MotionEvent.ACTION_UP){
                organization.setSelected(true);
            }
            return false;
        }
    };

}