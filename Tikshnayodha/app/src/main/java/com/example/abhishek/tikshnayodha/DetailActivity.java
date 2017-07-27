package com.example.abhishek.tikshnayodha;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;


public class DetailActivity extends FragmentActivity {

    ListView lvDetail;
    Context context = DetailActivity.this;
    ArrayList myList = new ArrayList();
    MySQLiteQuery mySQLiteQuery;
    String[] column = {"artifact_id", "gallery_id", "artifact_name", "artifact_description","media_id"};
    public final String[] relted_exibits={" RELATED EXHIBITS "," POWIĄZANE WYSTAWY "," संबंधित प्रदर्शन  "};
    public static final String EXTRA_IMAGE_URL = "detailImageUrl";
    public static final String IMAGE_TRANSITION_NAME = "transitionImage";
    public static final String ADDRESS4_TRANSITION_NAME = "address4";
    public static final String HEAD1_TRANSITION_NAME = "head1";
    public static final String HEAD2_TRANSITION_NAME = "head2";
    public static final String HEAD3_TRANSITION_NAME = "head3";
    public static final String HEAD4_TRANSITION_NAME = "head4";
    private TextView address4,name,related_exibit;//View
    private ImageView imageView;

    public static String[] a_id = new String[19];
    public static String[] a_name = new String[19];
    public static String[] a_desc = new String[19];
    public static String[] a_mid = new String[19];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        related_exibit=(TextView)findViewById(R.id.related_exibts);
        name=(TextView)findViewById(R.id.name);
        imageView = (ImageView)findViewById(R.id.image);
        address4 =(TextView)findViewById(R.id.address4);
        lvDetail=(ListView) findViewById(R.id.lvCustomList);
        mySQLiteQuery=new MySQLiteQuery(this);

        if(LoginActivity.language.equalsIgnoreCase("English"))
            related_exibit.setText(relted_exibits[0]);
        else if(LoginActivity.language.equalsIgnoreCase("Polish"))
            related_exibit.setText(relted_exibits[1]);
        else if(LoginActivity.language.equalsIgnoreCase("Hindi"))
            related_exibit.setText(relted_exibits[2]);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        getDataFromDataBase();
        name.setText(a_name[0]);
        address4.setText(a_desc[0]);

        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        ViewCompat.setTransitionName(imageView, IMAGE_TRANSITION_NAME);
        getDataInList();

        lvDetail.setAdapter(new MyBaseAdapter(context, myList));
        lvDetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int itemPosition = position;// ListView Clicked item index
                Intent i = new Intent(getApplicationContext(), QRActivity.class);
                i.putExtra("image_id",a_id[position+1]);
                //Toast.makeText(getApplicationContext(), "Position:- "+itemPosition, Toast.LENGTH_LONG).show();// Show Alert
                startActivity(i);
            }
        });
    }

    private void getDataInList() {

        SharedPreferences prefs = getSharedPreferences("MyPref", MODE_PRIVATE);
        String data_path = prefs.getString("path", null);
        String path=data_path+"museum_data/";

        Bitmap bmp;
        for (int i = 1; i<a_id.length; i++) {
            ListData ld = new ListData();       // Create a new object for each list item
            ld.setTitle(a_name[i]);
            bmp= BitmapFactory.decodeFile(path+a_id[i]);
            ld.setImgResId(bmp);
            myList.add(ld);     // Add this object into the ArrayList myList
        }
    }

    void getDataFromDataBase(){

        if(GalleryActivity.INDEX_NO==0) {
            a_id = new String[19];
            a_name = new String[19];
            a_desc = new String[19];
            a_mid = new String[19];
        }else if(GalleryActivity.INDEX_NO==1){
            a_id = new String[6];
            a_name = new String[6];
            a_desc = new String[6];
            a_mid = new String[6];
        }else if(GalleryActivity.INDEX_NO==2){
            a_id = new String[9];
            a_name = new String[9];
            a_desc = new String[9];
            a_mid = new String[9];
        }

        try {
            if(LoginActivity.ENGLISH_FLAG) {

                SQLiteDatabase writeDatabase = mySQLiteQuery.getReadableDatabase();
                String selectQuery = " select * from eng_artifact_table where artifact_id > " + ((GalleryActivity.INDEX_NO + 1) * 100 - 1) + " and artifact_id < " + (GalleryActivity.INDEX_NO + 2) * 100;
                Cursor cursor = writeDatabase.rawQuery(selectQuery, null);
                int i = 0;
                while (cursor.moveToNext()) {
                    a_id[i] = cursor.getString(1);
                    a_name[i] = cursor.getString(2);
                    a_desc[i] = cursor.getString(3);
                    a_mid[i] = cursor.getString(4);
                    i++;
                    //Toast.makeText(getApplicationContext(),"id= "+cursor.getString(0), Toast.LENGTH_LONG).show();
                }
                writeDatabase.close();
            }
            else if(LoginActivity.HINDI_FLAG)
            {
                SQLiteDatabase writeDatabase1 = mySQLiteQuery.getReadableDatabase();
                String selectQuery1 = " select * from hindi_artifact_table where artifact_id > " + ((GalleryActivity.INDEX_NO + 1) * 100 - 1) + " and artifact_id < " + (GalleryActivity.INDEX_NO + 2) * 100;
                Cursor cursor1 = writeDatabase1.rawQuery(selectQuery1, null);
                int i1 = 0;
                while (cursor1.moveToNext()) {
                    a_id[i1] = cursor1.getString(1);
                    a_name[i1] = cursor1.getString(2);
                    a_desc[i1] = cursor1.getString(3);
                    a_mid[i1] = cursor1.getString(4);
                    i1++;
                    //Toast.makeText(getApplicationContext(),"id= "+cursor.getString(0), Toast.LENGTH_LONG).show();
                }
                writeDatabase1.close();
            }
            else if(LoginActivity.POLISH_FLAG) {
                SQLiteDatabase writeDatabase2 = mySQLiteQuery.getReadableDatabase();
                String selectQuery2 = " select * from polish_artifact_table where artifact_id > " + ((GalleryActivity.INDEX_NO + 1) * 100 - 1) + " and artifact_id < " + (GalleryActivity.INDEX_NO + 2) * 100;
                Cursor cursor2 = writeDatabase2.rawQuery(selectQuery2, null);
                int i2 = 0;
                while (cursor2.moveToNext()) {
                    a_id[i2] = cursor2.getString(1);
                    a_name[i2] = cursor2.getString(2);
                    a_desc[i2] = cursor2.getString(3);
                    a_mid[i2] = cursor2.getString(4);
                    i2++;
                    //Toast.makeText(getApplicationContext(),"id= "+cursor.getString(0), Toast.LENGTH_LONG).show();
                }
                writeDatabase2.close();
            }
        }
        catch (Exception e) {
            //Toast.makeText(getApplicationContext(),"Some Error "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}