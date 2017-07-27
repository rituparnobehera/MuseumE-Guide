package com.example.abhishek.tikshnayodha;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by ABHISHEK on 7/19/2017.
 */
public class MySQLiteQuery extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "u120967034_mydb";
    public static final int DATABASE_VERSION = 1;
    Context context;

    public MySQLiteQuery(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //-----created three table to store local infirmations

        String query = "CREATE TABLE IF NOT EXISTS eng_artifact_table(artifact_id int primary key, gallery_id varchar(80), artifact_name varchar(80),artifact_description text, media_id varchar(80));";
        String query1 = "CREATE TABLE IF NOT EXISTS hindi_artifact_table(artifact_id int primary key, gallery_id varchar(80), artifact_name varchar(80),artifact_description text, media_id varchar(80));";
        String query2 = "CREATE TABLE IF NOT EXISTS polish_artifact_table(artifact_id int primary key, gallery_id varchar(80), artifact_name varchar(80),artifact_description text, media_id varchar(80));";
        try {
            db.execSQL(query);
            db.execSQL(query1);
            db.execSQL(query2);
            Toast.makeText(context.getApplicationContext(),"table created", Toast.LENGTH_LONG).show();
        }
        catch (SQLException e) {
            Toast.makeText(context.getApplicationContext(),"table not created "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("drop table if exist eng_artifact_table");
            db.execSQL("drop table if exist hindi_artifcat_table");
            db.execSQL("drop table if exist polish_artifact_table");
            onCreate(db);
        }
        catch (SQLException e) {
        }
    }

}

