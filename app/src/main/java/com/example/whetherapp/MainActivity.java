package com.example.whetherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


public class MainActivity extends AppCompatActivity {

  EditText et;
  Button btn;
  TextView temp,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        et = findViewById(R.id.cityname);
        btn = findViewById(R.id.button);
        temp = findViewById(R.id.temp);
        description = findViewById(R.id.description);

    }
    public void Call(View view) {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String city = et.getText().toString().trim();
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=0dc6598561c8b2865bd6ee8edb0209ac";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("main");
                    String tempj = jsonObject.getString("temp");
                    Double tempc = Double.parseDouble(tempj)-273.15;

                    JSONArray wjson = response.getJSONArray("weather");
                    JSONObject desobj = wjson.getJSONObject(0);
                    String des = desobj.getString("description");
                    String finaltemp = tempc.toString().trim().substring(0,5);


                   temp.setText(finaltemp);
                   description.setText(des);
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
