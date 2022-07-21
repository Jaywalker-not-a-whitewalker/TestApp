package com.mac.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import com.mac.testapp.helper.JPARSER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
//TODO Please Ignore the NameValuePar and BasicNameValuePair error, App should/will run
public class rc_Act extends AppCompatActivity {
    RecyclerView RV_list;
    RV_adapter rv_adapter;
    ArrayList<String> f_name = new ArrayList<>(),l_name = new ArrayList<>(),gender = new ArrayList<>(),
            Age = new ArrayList<>(),Addr = new ArrayList<>(),Mobile = new ArrayList<>(),Course = new ArrayList<>(),
            year = new ArrayList<>(),RegNo = new ArrayList<>();
    ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rc);
        RV_list = findViewById(R.id.RV_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rc_Act.this,LinearLayoutManager.VERTICAL,false);
        RV_list.setLayoutManager(layoutManager);
        rv_adapter = new RV_adapter(rc_Act.this,f_name,l_name,gender,Age,Addr,Mobile,Course,year,RegNo);
        RV_list.setAdapter(rv_adapter);
        new getInfoTask().execute();
    }


    public  class getInfoTask extends AsyncTask<String,Integer, JSONObject>{


        @Override
        protected JSONObject doInBackground(String... strings) {
            List<NameValuePair> params = new ArrayList();
          //TODO  Replace with your own Php file name
            return JPARSER.makeRequest("YOUR PHP FILE NAME","POST",params);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressDialog(rc_Act.this);
            progressBar.setCancelable(false);
            progressBar.setMessage("Loading");
            progressBar.show();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            progressBar.dismiss();
            try {

                String rescode = jsonObject.getString("rescode");
                if(rescode.equals("1"))
                {
                    JSONObject jArray = jsonObject.getJSONObject("result");
                    JSONArray F_nameJS = jArray.getJSONArray("F_name");
                    JSONArray L_nameJS = jArray.getJSONArray("L_name");
                    JSONArray GenderJS = jArray.getJSONArray("Gender");
                    JSONArray AgeJS = jArray.getJSONArray("Age");
                    JSONArray AddrJS = jArray.getJSONArray("Addr");
                    JSONArray MobileJS = jArray.getJSONArray("Mobile");
                    JSONArray CourseJS = jArray.getJSONArray("Course");
                    JSONArray yearJS = jArray.getJSONArray("year");
                    JSONArray RegNoJS = jArray.getJSONArray("RegNo");
                   for (int i=0;i<F_nameJS.length();i++)
                   {
                       f_name.add(F_nameJS.getString(i));
                       l_name.add(L_nameJS.getString(i));
                       gender.add(GenderJS.getString(i));
                       Age.add(AgeJS.getString(i));
                       Addr.add(AddrJS.getString(i));
                       Mobile.add(MobileJS.getString(i));
                       Course.add(CourseJS.getString(i));
                       year.add(yearJS.getString(i));
                       year.add(yearJS.getString(i));
                       RegNo.add(RegNoJS.getString(i));
                   }
                   rv_adapter.setData(f_name,l_name,gender,Age,Addr,Mobile,Course,year,RegNo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}