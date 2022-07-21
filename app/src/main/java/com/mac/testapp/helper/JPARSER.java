package com.mac.testapp.helper;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;


public class JPARSER {

    static InputStream IS = null;
    static JSONObject Jobj = null;
    static String Json="";
    public static String serveradr = "http://bnbapps.in/RPS/";

    //CONSTRUCTOR
    public JPARSER()
    {

    }
    //POST AND GET METHOD
    public static JSONObject makeRequest(String url, String method, List<NameValuePair> params) {
        IS = null;
        Jobj = null;
        Json = null;
        HttpParams httpParams = new BasicHttpParams();
        int timeoutConnection = 5000;
        HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
        // Set the default socket timeout (SO_TIMEOUT)
        // in milliseconds which is the timeout for waiting for data.
        int timeoutSocket = 5000;
        HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
        DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);
        try {
            if (method == "POST") {

                //HttpPost httpPost = new HttpPost("http://bits-nbytes.in/rsp/scripts/" + url);
                HttpPost httpPost = new HttpPost(serveradr +"scripts/" + url);
                httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
                HttpResponse httpResponse = httpclient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                Log.e("Connected:","Successfully");
                IS = httpEntity.getContent();
            } else if (method == "GET") {
                //DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                HttpResponse httpResponse = httpclient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                IS = httpEntity.getContent();
                Log.e("Connected:","Successfully");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("Error:", e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.e("Error:", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error:", e.toString());
        }
        catch(Exception e)
        {
            Log.e("Error:", e.toString());
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"), 8);
            StringBuilder SB = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                SB.append(line + "\n");
            }
            IS.close();
            Json = SB.toString();
            Log.e("jdata:", Json.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("Buffer Error", "Error converting response" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            Log.e("Buffer error:", e.toString());
        }
        try
        {
            Jobj = new JSONObject(Json);
            //Log.e("data :" ,  Jobj.getString("result") );

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON Parser", "Error parsing data " + e.toString());

        }catch (NullPointerException npe)
        {

        }
        return Jobj;
    }


}
