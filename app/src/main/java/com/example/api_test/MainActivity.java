package com.example.api_test;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

//    RequestQueue mRequestQueue;
//    RequestQueue mRequestQueueArray;

    RequestQueue mSigletonRequestQueue;

    TextView textViewJsonObjectElement,textViewJsonArrayElement;
    Button btnNextJoke,btnNextJoke2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mRequestQueue = Volley.newRequestQueue(this);
//        mRequestQueueArray = Volley.newRequestQueue(this);

        mSigletonRequestQueue=VolleySingleton.getInstance().getRequestQueue();

        textViewJsonObjectElement = findViewById(R.id.textViewJsonObjectElement);
        btnNextJoke = findViewById(R.id.btnNextJoke);

        btnNextJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET
                        , "https://official-joke-api.appspot.com/random_joke"
                        , null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("RESPONSE", response + "");
                        //Getting values JSON Object Element
                        try {
                            int id;
                            String type, setup, punchline;
                            id = response.getInt("id");
                            type = response.getString("type");
                            setup = response.getString("setup");
                            punchline = response.getString("punchline");
                            textViewJsonObjectElement.setText("id :" + id + "\n" + "type :" + type + "\n" + "setup :" + setup + "\n" + "puchline :" + punchline);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        Log.e("RESPONSE ERROR", error.getMessage());
                    }
                });

                mSigletonRequestQueue.add(jsonObjectRequest);
            }
        });

        textViewJsonArrayElement=findViewById(R.id.textViewJsonArrayElement);
        btnNextJoke2=findViewById(R.id.btnNextJoke2);

        btnNextJoke2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                        , "https://official-joke-api.appspot.com/random_ten"
                        , null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Getting values JSON Object Array
                        Log.i("ArrayRESPONSE", response + "");
                        for (int index = 0; index < response.length(); index++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(index);
                                //textViewJsonArrayElement.setText(jsonObject.getString("setup")+"\n");
                                Log.i("TAGG",jsonObject.getString("setup")+"\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ArrayRESPONSEError", error.getMessage());

                    }
                });


                mSigletonRequestQueue.add(jsonArrayRequest);

            }
        });




    }
}