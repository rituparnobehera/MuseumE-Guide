package com.example.abhishek.tikshnayodha;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //Global variables declaration
    //work for empty user id and password

    EditText etname, etpass;
    ImageButton btlogin;
    View ttsignup;
    Spinner spinnerLanguage;
    ArrayAdapter<CharSequence> adapterLanguage;
    public static String language="English";
    public static boolean PARSE_FLAG=false, ENGLISH_FLAG=false, HINDI_FLAG=false, POLISH_FLAG=false;

    MySQLiteQuery mySQLiteQuery;
    int a_id;
    String a_name,a_desc,gal_id,m_id;
    String[] column = {"artifact_id", "gallery_id", "artifact_name", "artifact_description", "media_id"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        etname = (EditText) findViewById(R.id.input_email);
        etpass = (EditText) findViewById(R.id.input_password);
        btlogin = (ImageButton) findViewById(R.id.login);
        ttsignup = (TextView) findViewById(R.id.registerHere);
        btlogin.setOnClickListener(this);
        mySQLiteQuery=new MySQLiteQuery(this);
        spinnerLanguage = (Spinner) findViewById(R.id.spinner_lang);
        //category_names is array define strings.xml
        adapterLanguage = ArrayAdapter.createFromResource(this, R.array.array_language_name, android.R.layout.simple_spinner_item);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        try {
            spinnerLanguage.setAdapter(adapterLanguage);  //setting the array adapter to sppiner
            spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    language = parent.getItemAtPosition(position).toString();
                    Toasty.info(getApplicationContext(), "You have Selected "+language+" Language", Toast.LENGTH_SHORT, true).show();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        catch (Exception e) {
        }

    }


    @Override
    public void onClick(View v) {

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait Login....");
        progressDialog.show();

        if(etname.getText().toString().equals("")){
            progressDialog.hide();
            etname.setError("Enter your registered EmailId");
        }
        else if(etpass.getText().toString().equals("")) {
            progressDialog.hide();
            etpass.setError("Enter your Password");
        }
        else {

            final RequestQueue rq;
            rq = Volley.newRequestQueue(getApplicationContext());
            String url = MainActivity.IPV4_URL + "/index.php?vemail=" + etname.getText() + "&vpassword=" + etpass.getText();
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString("check").equals("True")) {
                            progressDialog.hide();
                            Toasty.success(getApplicationContext(), "You have Login Successfully", Toast.LENGTH_SHORT, true).show();

                            if (language.equalsIgnoreCase("English")) {

                                ENGLISH_FLAG = true;
                                POLISH_FLAG = false;
                                HINDI_FLAG = false;
                                jsonDecoder(MainActivity.IPV4_URL + "/artical_data_english.php", "eng_artifact_details", "English");

                            } else if (language.equalsIgnoreCase("Polish")) {

                                ENGLISH_FLAG = false;
                                POLISH_FLAG = true;
                                HINDI_FLAG = false;
                                jsonDecoder(MainActivity.IPV4_URL + "/artical_data_polish.php", "polish_artifact_details", "Polish");

                            } else if (language.equalsIgnoreCase("Hindi")) {

                                ENGLISH_FLAG = false;
                                POLISH_FLAG = false;
                                HINDI_FLAG = true;
                                jsonDecoder(MainActivity.IPV4_URL + "/artical_data_hindi.php", "hindi_artifact_details", "Hindi");

                            }
                            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                            startActivity(intent);
                        } else {
                            progressDialog.hide();
                            Toasty.error(getApplicationContext(), "Email or Password is wrong!!... ", Toast.LENGTH_SHORT, true).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.hide();
                    Toasty.error(getApplicationContext(), "Need Internet" + error.toString(), Toast.LENGTH_SHORT, true).show();
                }
            });
            rq.add(jor);
        }
    }

    public void registerHere(View view) {

        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        intent.putExtra("txt","REGISTER HERE...");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Pair<View, String> pair1 = Pair.create(ttsignup, "register");
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1);
            startActivity(intent, options.toBundle());
        }else
            startActivity(intent);
    }


    //JsonDecoder is used to parse the json file from server

    public void jsonDecoder(String phpFile, final String arrayName,final String language){

        final RequestQueue rque;
        rque = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET, phpFile , null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
                    SQLiteDatabase writeDatabase = mySQLiteQuery.getWritableDatabase();
                    ContentValues cntval = new ContentValues();
                    JSONArray ja = response.getJSONArray(arrayName);
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        a_id = jo.getInt("artifact_id");
                        gal_id = jo.getString("gallery_id");
                        a_name = jo.getString("artifact_name");
                        a_desc = jo.getString("artifact_description");
                        m_id = jo.getString("media_id");
                        cntval.put("artifact_id", a_id);
                        cntval.put("gallery_id", gal_id);
                        cntval.put("artifact_name", a_name);
                        cntval.put("artifact_description", a_desc);
                        cntval.put("media_id", m_id);


                        if(language.equalsIgnoreCase("English")){

                            ENGLISH_FLAG=true;
                            POLISH_FLAG=false;
                            HINDI_FLAG=false;
                            writeDatabase.insert("eng_artifact_table", null, cntval);
                            //Toast.makeText(getApplicationContext(),"inserted",Toast.LENGTH_SHORT).show();

                        }else if(language.equalsIgnoreCase("Polish")){

                            ENGLISH_FLAG=false;
                            POLISH_FLAG=true;
                            HINDI_FLAG=false;
                            writeDatabase.insert("polish_artifact_table", null, cntval);

                        } else if(language.equalsIgnoreCase("Hindi")){

                            ENGLISH_FLAG=false;
                            POLISH_FLAG=false;
                            HINDI_FLAG=true;
                            writeDatabase.insert("hindi_artifact_table", null, cntval);
                        }

                    }
                    writeDatabase.close();

                } catch (JSONException e) {
                    //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                //Toasty.error(getApplicationContext(), "Error in Server Connection ", Toast.LENGTH_SHORT, true).show();
            }
        }
        );rque.add(jsonObjRequest);
    }

}