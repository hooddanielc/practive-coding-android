package com.example.CodingChallenge;

import java.net.URL;
import java.net.URISyntaxException;
import java.lang.StringBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.*;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.os.AsyncTask;

import android.util.Log;

public class MainActivity extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
  }

  public void downloadJsonPlease(View view) {
    try {
      new AsyncTask<URL, Integer, String>() {
        protected String doInBackground(URL... urls) {
          if (urls.length != 1) {
            return "error";
          }

          String result = "";

          try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(urls[0].toURI());
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
            return result;
          } catch (Exception exception) {
            return "{ \"error\": \"There was a problem getting data from server\" }";
          }
        }

        protected void onProgressUpdate(Integer... progress) {
          Log.v("SHCE", "Download Progress Changed");
        }

        protected void onPostExecute(String result) {
          try {
            JSONObject object = new JSONObject(result);
            JSONArray caseStudies = object.getJSONArray("casestudies");
          } catch (Exception e) {
            Log.v("SHCE", "Could not parse json, it is malformed");
          }
        }
      }.execute(new URL("https://api.myjson.com/bins/2ukm9"));
    } catch (Exception e) {
      Log.v("SHCE", "Could not form url :-(");
    }
  }
}
