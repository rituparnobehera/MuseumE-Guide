package com.example.abhishek.tikshnayodha;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class QRActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    TextView name,desc;
    ImageView imageView;
    ImageButton video_play,audio_play,three_d_view;
    String MEDIA_PATH;
    MediaPlayer mp;
    MySQLiteQuery mySQLiteQuery;
    int MediaFlag=0;
    String[] columns = {"artifact_id", "gallery_id", "artifact_name", "artifact_description","media_id"};

    String[] english_audio = {"eng_barbarian_swords.mp3", "eng_indian_coin_in_british_rule.mp3", "eng_triabal_ornaments.mp3", "eng_tribal_weapon.mp3"};
    String[] hindi_audio = {"hindi_barbarian_swords.mp3", "hindi_indian_coin_in_british_rule.mp3", "hindi_tribal_ornaments.mp3", "hindi_tribal_weapon.mp3"};
    String[] japanese_audio = {"jpn_barbarian_swords.m4a", "jpn_indian_coin_in_british_rule.m4a", "jpn_triabal_ornaments.wma", "jpn_tribal_weapon.m4a"};

    String[] threeD_Model={"com.Tikshnayodha.hackSword","com.Tikshnayodha.hackCoin","com.Tikshnayodha.hackBangle","com.Tikshnayodha.hackAxe"};

    String str="";
    String m_id;
    int artical_id;

    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 4f;
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;
    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        name=(TextView)findViewById(R.id.name);
        desc=(TextView)findViewById(R.id.description);
        video_play=(ImageButton)findViewById(R.id.play_butn);
        video_play.setOnClickListener(this);
        audio_play=(ImageButton)findViewById(R.id.audio);
        audio_play.setOnClickListener(this);
        imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setOnTouchListener(this);
        three_d_view=(ImageButton)findViewById(R.id.three_d_btn);
        three_d_view.setOnClickListener(this);
        Intent i=getIntent();
        str+=i.getStringExtra("image_id");
        mySQLiteQuery=new MySQLiteQuery(this);
        SQLiteDatabase writeDatabase = mySQLiteQuery.getWritableDatabase();
        //SQLiteDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
        if(LoginActivity.ENGLISH_FLAG) {
            Cursor cursor = writeDatabase.query("eng_artifact_table", columns, "gallery_id= ?", new String[]{str}, null, null, null);//Searching image_id in artical_table
            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_SHORT).show();
            } else {
                cursor.moveToNext();
                artical_id = cursor.getInt(0);
                name.setText(cursor.getString(2));
                desc.setText(cursor.getString(3));
                m_id = cursor.getString(4);
            }
            writeDatabase.close();
        }
        if(LoginActivity.HINDI_FLAG) {
            Cursor cursor = writeDatabase.query("hindi_artifact_table", columns, "gallery_id= ?", new String[]{str}, null, null, null);//Searching image_id in artical_table
            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_SHORT).show();
            } else {
                cursor.moveToNext();
                artical_id = cursor.getInt(0);
                name.setText(cursor.getString(2));
                desc.setText(cursor.getString(3));
                m_id = cursor.getString(4);
            }
            writeDatabase.close();
        }
        if(LoginActivity.POLISH_FLAG) {
            Cursor cursor = writeDatabase.query("polish_artifact_table", columns, "gallery_id= ?", new String[]{str}, null, null, null);//Searching image_id in artical_table
            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_SHORT).show();
            } else {
                cursor.moveToNext();
                artical_id = cursor.getInt(0);
                name.setText(cursor.getString(2));
                desc.setText(cursor.getString(3));
                m_id = cursor.getString(4);
            }
            writeDatabase.close();
        }

        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        String data_path = prefs.getString("path", null);
        String path=data_path+"museum_data/"+str;


        Bitmap bmp = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(bmp);
    }

    //Method For Application Transition uisng the Context and Package name
    public static boolean openApp(Context context, String pakage_name) {

        PackageManager manager = context.getPackageManager();

        Intent i = manager.getLaunchIntentForPackage(pakage_name);

        if (i == null) {
            return false;
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case (R.id.play_butn):
                Intent intent_play_video = new Intent(getApplicationContext(), VideoActivity.class);
                intent_play_video.putExtra("media_id",m_id);
                startActivity(intent_play_video);
                break;
            case(R.id.audio):
                Toast.makeText(this,"No Audio Found", Toast.LENGTH_LONG).show();
                break;
            case(R.id.three_d_btn):
                    Toast.makeText(this,"No 3D Model Found", Toast.LENGTH_LONG).show();
                break;

        }
    }//Onclik

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
                Intent intent_help = new Intent(getApplicationContext(), HelpDesk.class);
                startActivity(intent_help);
                return true;

            case (R.id.history):
                Intent intent_history = new Intent(getApplicationContext(), History.class);
                startActivity(intent_history);
                return true;

            case (R.id.aboutus):
                Intent intent_aboutus = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(intent_aboutus);
                return true;

            case (R.id.setting):
                Intent intent_setting = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent_setting);
                return true;

            case (R.id.exit):
                final AlertDialog.Builder builder = new AlertDialog.Builder(QRActivity.this);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;
        //dumpEvent(event);
        // Handle touch events here...
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                //Toast.makeText(getApplicationContext(), TAG+ " mode=DRAG", Toast.LENGTH_SHORT).show();
                mode = DRAG;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                //Toast.makeText(getApplicationContext(), TAG+ " mode=NONE", Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                //Toast.makeText(getApplicationContext(), TAG+ " oldDist= " + oldDist, Toast.LENGTH_SHORT).show();
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    //Toast.makeText(getApplicationContext(), TAG+ " mode=ZOOM", Toast.LENGTH_SHORT).show();
                    // Log.d(TAG, "mode=");
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX()-start.x, event.getY()-start.y);           //create the transformation in the matrix of points
                } else if (mode == ZOOM) {      // pinch zooming
                    float newDist = spacing(event);
                    //Log.d(TAG, "newDist=" + newDist);
                    //Toast.makeText(getApplicationContext(), TAG+ " newDist= " + newDist, Toast.LENGTH_SHORT).show();
                    if (newDist > 5f) {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist;      //setting the scaling of the matrix...if scale > 1 means zoom in...if scale < 1 means zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }
        view.setImageMatrix(matrix); // display the transformation on screen
        return true;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((double)(x * x + y * y));
    }
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if(MediaFlag==1) {
                mp.stop();
                audio_play.setEnabled(true);
            }
        }catch(Exception e){

        }
    }
}
