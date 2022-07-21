package com.mac.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mac.testapp.helper.JPARSER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class MainActivity extends AppCompatActivity {
    private EditText fname,lname,gen,Age,Adr,mob,cr,yr,Regno;
    private Button btn;
    private TextView TV_viewdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        gen = findViewById(R.id.gen);
        Age = findViewById(R.id.Age);
        Adr = findViewById(R.id.Adr);
        mob = findViewById(R.id.mob);
        cr = findViewById(R.id.cr);
        yr = findViewById(R.id.yr);
        Regno = findViewById(R.id.Regno);
        btn = findViewById(R.id.btn);
        TV_viewdata = findViewById(R.id.TV_viewdata);
        //Listners
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new registrationTask().execute();
            }
        });
        TV_viewdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //To navigate or to start a new Activity
                // * Create an Intent object, The intent will accept two parameters.
                // ----First paremeter -  the context of the activity from where another activity will be started
                //---- Second parameter - the class of the activity which u want ot start
                Intent intent = new Intent(MainActivity.this,rc_Act.class);
                //Start the activity using the startActivity function which accepts the Intent as a parameter/
                startActivity(intent);
            }
        });
    }
    public class registrationTask extends AsyncTask<String,Integer, JSONObject>{
        List<NameValuePair> params = new ArrayList();
        ProgressDialog progressBar;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressDialog(MainActivity.this);
            progressBar.setCancelable(false);
            progressBar.setMessage("Loading");
            progressBar.show();
        }

        @Override
        protected JSONObject doInBackground(String... strings) {
            params.add(new BasicNameValuePair("fname",fname.getText().toString()));
            params.add(new BasicNameValuePair("lname",lname.getText().toString()));
            params.add(new BasicNameValuePair("Gender",gen.getText().toString()));
            params.add(new BasicNameValuePair("Age",Age.getText().toString()));
            params.add(new BasicNameValuePair("Addr",Adr.getText().toString()));
            params.add(new BasicNameValuePair("Mobile",mob.getText().toString()));
            params.add(new BasicNameValuePair("Course",cr.getText().toString()));
            params.add(new BasicNameValuePair("year",yr.getText().toString()));
            params.add(new BasicNameValuePair("RegNo",Regno.getText().toString()));
            //TODO  Replace with your own Php file name
            return JPARSER.makeRequest("YOUR PHP FILE NAME","POST",params);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressBar.dismiss();
            try {
                JSONArray jArry = new JSONArray();
                 jArry = jsonObject.getJSONArray("result");
                String resCode = jArry.getJSONObject(0).getString("rescode");
                String res_msg="Server unreachable";
                if(resCode.equals("1"))
                {
                    res_msg = jArry.getJSONObject(0).getString("res_msg");
                    //Start login screen
                }
                else
                {
                    res_msg = jArry.getJSONObject(0).getString("res_msg") + "Try again!!";
                    //Make the user try again
                }
                Toast.makeText(MainActivity.this,res_msg,Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}