package com.example.abhishek.tikshnayodha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.common.collect.Range;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, age, country, address, email, mobileno, password, confirmPassword;
    String vname,vgender,vage,vaddress,vcountry,vemail,vmobileno,vpassword;
    private ImageButton btnSignUp;
    private AwesomeValidation awesomeValidation;
    private static final String REGISTER_URL = MainActivity.IPV4_URL+"/visitor_registration_details.php";
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "Register_Data" ;
    public static final String Name = "nameKey";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        TextView txt=(TextView)findViewById(R.id.reg);
        Intent i=getIntent();
        txt.setText(i.getStringExtra("txt"));

        vgender="";
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        name = (EditText) findViewById(R.id.visitorName);
        age = (EditText) findViewById(R.id.age);
        country = (EditText) findViewById(R.id.country);
        address = (EditText) findViewById(R.id.address);
        email = (EditText) findViewById(R.id.email);
        mobileno = (EditText) findViewById(R.id.mobileNo);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);

        awesomeValidation.addValidation(this, R.id.visitorName, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.age, Range.closed(1, 110), R.string.ageerror);
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.mobileNo, "^[2-9]{2}[0-9]{8}$", R.string.mobileerror);

        btnSignUp = (ImageButton) findViewById(R.id.signup);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                formValidation();

            }
        });
    }

    public void onRadioButtonClicked(View v){

        boolean checked = ((RadioButton) v).isChecked();
        switch(v.getId()) {
            case R.id.male:
                if (checked) {
                    vgender = "Male";
                    //Toast.makeText(getApplicationContext(), vgender, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.female:
                if (checked) {
                    vgender = "Female";
                    //Toast.makeText(getApplicationContext(), vgender, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void formValidation() {

        //first validate the form before submit
        if (awesomeValidation.validate()) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())){
                if (password.getText().toString().length()>=8) {
                    if (!country.getText().toString().equals("")) {
                        if (!address.getText().toString().equals("")) {
                            if(vgender.equals("Male") || vgender.equals("Female")){

                                //Validation Successful
                                Toasty.success(getApplicationContext(), "Register Successful", Toast.LENGTH_SHORT, true).show();
                                vname=name.getText().toString();
                                vage= age.getText().toString();
                                vcountry=country.getText().toString();
                                vaddress=address.getText().toString();
                                vemail=email.getText().toString();
                                vmobileno=mobileno.getText().toString();
                                vpassword=password.getText().toString();
                                register(vname,vgender,vage,vaddress,vcountry,vemail,vmobileno,vpassword);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Name, vname);
                                editor.putString(Email, vemail);
                                editor.putString(Phone, vmobileno);
                                editor.putString(Password, vpassword);
                                editor.commit();

                                Intent intent_Login = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent_Login);

                            }else
                                Toast.makeText(getApplicationContext(),"You have not chosen Your Gender",Toast.LENGTH_LONG).show();
                        } else
                            address.setError("Enter your Address");
                    } else
                        country.setError("Enter yor Country");
                } else
                    password.setError("Password length must be 8 character");
            }else
                confirmPassword.setError("Password Mismatch");
        }
    }

    //Post the data entered in the form to the Server vname,vgender,vage,vaddress,vcountry,vemail,vmobileno,vpassword;
    private void register(String name, String gender, String age, String address, String country, String email, String mobileno, String pass){

        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog dialog = new ProgressDialog(RegisterActivity.this);
            RegisterUserClass ruc = new RegisterUserClass();

            @Override
            protected void onPreExecute() {
                dialog.setMessage("Please Wait....");
                dialog.show();
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {

                if (dialog.isShowing())
                    dialog.dismiss();
                //Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... params) {
                //Insertion of Data into SQL database
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("vname",params[0]);
                data.put("vgender",params[1]);
                data.put("vage",params[2]);
                data.put("vaddress",params[3]);
                data.put("vcountry",params[4]);
                data.put("vemail",params[5]);
                data.put("vmobileno",params[6]);
                data.put("vpassword",params[7]);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                return  result;
            }
        }
        RegisterUser ru = new RegisterUser();
        ru.execute(name,gender,age,address,country,email,mobileno,pass);
    }

}
