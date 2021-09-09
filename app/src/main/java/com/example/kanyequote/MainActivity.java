package com.example.kanyequote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class MainActivity extends AppCompatActivity {
    private final String URL = "https://api.kanye.rest/";

    //add a tag for request cancellation purposes (when activity is stopped)
    public static final String TAG = "RequestTag";

    RequestQueue requestQueue;
    StringRequest stringRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void quoteButtonPress(View view) {
        //Get text view
        final TextView textView = (TextView) findViewById(R.id.textView);

        //Get request queue
        requestQueue = Volley.newRequestQueue(this);

        //Request a string response from the provided URL.
        stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        textView.setText(response);
                    }
                }, error -> textView.setText("Ooops! Failed to load JSON data"));

        //Add tag to String request
        stringRequest.setTag(TAG);
        //add string to request queue
        requestQueue.add(stringRequest);

    }

    //This method is called when this activity is stopped to halt any http requests coming from this class
    @Override
    protected void onStop() {
        super.onStop();
        //Cancel all requests if not empty
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }
}