package com.example.abhishek.tikshnayodha;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class BeaconActivity extends AppCompatActivity implements BeaconConsumer, RangeNotifier {

    ImageView imageView;
    private BeaconManager mBeaconManager;
    TextView search;
    List<BeconRange> beconRange;
    //vsir_bobby_charlton  vwayne_rooney  uefa_champions_league fa_challenge_cup
    static String[] image_id={"wayne_rooney.jpg","fa_challenge_cup.jpg","uefa_winners_cup.jpg","sir_bobby_charlton.jpg","2009.jpg","1988.jpg"};
    static String[] beacon_id={"0x00000000000000000001","0x00000000000000000002","0x00000000000000000003","0x00000000000000000004","0x00000000000000000005","0x00000000000000000006"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);
        search=(TextView)findViewById(R.id.search);

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

        Intent i=getIntent();
        search.setText(i.getStringExtra("txt"));

        imageView=(ImageView) findViewById(R.id.imageView);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.anim);
        imageView.startAnimation(myFadeInAnimation);
    }

    @Override
    public void onBeaconServiceConnect() {
        Region region = new Region("all-beacons-region", null, null, null);
        try {
            mBeaconManager.startRangingBeaconsInRegion(region);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
        mBeaconManager.setRangeNotifier(this);
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {

        beconRange = new ArrayList<BeconRange>();
        for (final Beacon beacon : collection) {
            final Identifier namespaceId = beacon.getId1();
            final double dis = beacon.getDistance();
            beconRange.add(new BeconRange(dis, namespaceId.toString()));

        }
        try {
            //mBeaconManager.wait(1000);
            Collections.sort(beconRange, new Comparator<BeconRange>() {

                public int compare(BeconRange obj1, BeconRange obj2) {   // TODO Auto‐generated method stub
                    return (obj1.distance < obj2.distance) ? -1 : (obj1.distance > obj2.distance) ? 1 : 0;


                }
            });
            for(int i=0;i<beacon_id.length;i++){
                if(beacon_id[i].equals(beconRange.get(0).becon_id))
                {
                    Intent intent_qrActivity = new Intent(getApplicationContext(), QRActivity.class);
                    intent_qrActivity.putExtra("image_id",image_id[i]);
                    startActivity(intent_qrActivity);
                    this.finish();
                    break;
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Collections.sort " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mBeaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        // Detect the main Eddystone-UID frame:
        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19"));
        mBeaconManager.bind(this);
    }

    @Override
    public void onPause() {
        beconRange.clear();
        super.onPause();
        mBeaconManager.unbind(this);
    }

}





