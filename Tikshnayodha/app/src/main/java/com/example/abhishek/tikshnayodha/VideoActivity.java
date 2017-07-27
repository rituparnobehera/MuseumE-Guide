package com.example.abhishek.tikshnayodha;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;


public class VideoActivity extends AppCompatActivity {

    VideoView videoView;
    String uri;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent i=getIntent();
        String m_id=i.getStringExtra("media_id");       //Getting media_id
        videoView=(VideoView)findViewById(R.id.videoView);
        mediaController = new MediaController(this);

        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        String data_path = prefs.getString("path", null);
        String path=data_path+"museum_data/"+m_id;

        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse(path));
        videoView.requestFocus();
        videoView.start();
        mediaController.setAnchorView(videoView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.innermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case (R.id.back):
                finish();
                return true;

            case (R.id.help_desk):
                /*Intent intent_help = new Intent(getApplicationContext(), Help.class);
                startActivity(intent_help);*/
                return true;

            case (R.id.history):
                /*Intent intent_history = new Intent(getApplicationContext(), History.class);
                startActivity(intent_history);*/
                return true;

            case (R.id.aboutus):
                /*Intent intent_aboutus = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(intent_aboutus);*/
                return true;

            case (R.id.setting):
                /*Intent intent_setting = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent_setting);*/
                return true;

            case (R.id.exit):
                final AlertDialog.Builder builder = new AlertDialog.Builder(VideoActivity.this);
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

//Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");
//videoView.setVideoURI(path);
//uri = "android.resource://" + getPackageName() + "/" + R.raw.archelogy1;
// specify the location of media file
//Setting MediaController and URI, then starting the videoView