package com.example.abhishek.tikshnayodha;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Intent i=getIntent();
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(History.this);
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

            case (R.id.aboutus):
                Intent intent = new Intent(getApplicationContext(), AboutUs.class);
                startActivity(intent);
                return true;

            case (R.id.help_desk):
                Intent intent2 = new Intent(getApplicationContext(), HelpDesk.class);
                startActivity(intent2);
                return true;

            case (R.id.back):
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
