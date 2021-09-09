package com.example.kanyequote;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private final String URL = "https://api.kanye.rest/";
    private final String ERROR_MESSAGE = "Oooops...Can't get any Kanye quotes at the moment!";

    //add a tag for request cancellation purposes (when activity is stopped)
    public static final String TAG = "RequestTag";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void quoteButtonPress(View view) {

        //Get text view
        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);

        //Get request queue
        requestQueue = Volley.newRequestQueue(this);

        //Get JSON object
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Display quote in text view
                        String quote = response.optString("quote", ERROR_MESSAGE);
                        textView.setText(quote);
//                        System.out.println(quote);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        textView.setText(ERROR_MESSAGE);
                        System.out.println(ERROR_MESSAGE);
                    }
                });
        //Set request tag
        jsonObjectRequest.setTag(TAG);
        //Add to request queue
        requestQueue.add(jsonObjectRequest);


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