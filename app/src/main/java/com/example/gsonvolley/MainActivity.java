package com.example.gsonvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    private Button buttonServer;
    private TextView messageText;
    private String serverUrl = "https://api.myjson.com/bins/ufy1x";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageText = findViewById(R.id.txt);
        buttonServer = findViewById(R.id.button);

        buttonServer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, serverUrl,
//                        new Response.Listener<String>()
//                        {
//                            @Override
//                            public void onResponse(String response)
//                            {
//                                messageText.setText(response);
//                                requestQueue.stop();
//                            }
//                        },
//                        new Response.ErrorListener()
//                        {
//                            @Override
//                            public void onErrorResponse(VolleyError error)
//                            {
//                                messageText.setText("Something went wrong...");
//                                error.printStackTrace();
//                                requestQueue.stop();
//                            }
//                        });
//                requestQueue.add(stringRequest);

                jsonParse();
            }
        });
    }

    private void jsonParse()
    {
        final JsonObjectRequest request =  new JsonObjectRequest(Request.Method.GET, serverUrl, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            JSONArray jsonArray = response.getJSONArray("Messages");

                            for (int i = 0 ; i < jsonArray.length(); i++)
                            {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String message = object.getString("message");
                                messageText.append(message);
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                });
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
