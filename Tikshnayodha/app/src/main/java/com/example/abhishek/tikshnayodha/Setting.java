package com.example.abhishek.tikshnayodha;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;


public class Setting extends AppCompatActivity implements View.OnClickListener{

    EditText data_path;
    Button set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        data_path=(EditText)findViewById(R.id.video_path);
        set=(Button)findViewById(R.id.set);
        set.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        SharedPreferences.Editor editor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
        editor.putString("path",data_path.getText().toString());
        editor.commit();
        Toasty.info(getApplicationContext(), data_path.getText().toString()+"museum_data/" , Toast.LENGTH_LONG, true).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.innermenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case (R.id.exit):
                final AlertDialog.Builder builder = new AlertDialog.Builder(Setting.this);
                builder.setTitle("Do you Want to Exit");
                builder.setMessage("");
                builder.setIcon(R.drawable.exit_icon);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "System Close", Toast.LENGTH_SHORT).show();
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

            case (R.id.aboutus):
                Intent i = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(i);
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
}
