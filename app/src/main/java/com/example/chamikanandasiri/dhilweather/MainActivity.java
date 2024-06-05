package com.example.chamikanandasiri.dhilweather;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button findButton;
    TextView detailsTextView;
    String API_KEY = "4f5bc5b658e443319b552841af0c0851";
    String LATITUDE = "6.821251797008117";
    String LONGITUDE = "79.91935729924717";
    String URL = "https://api.weatherbit.io/v2.0/current?lat=" + LATITUDE + "&lon=" + LONGITUDE + "&key=" + API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findButton = findViewById(R.id.fetchButton);
        detailsTextView = findViewById(R.id.detailsTextView);
        findButton.setOnClickListener(v -> {
            getWeatherDetails();
        });
    }

    private void getWeatherDetails() {

        RequestQueue rq = Volley.newRequestQueue(detailsTextView.getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null,
                        response -> {
                            try {
                                detailsTextView.setText(createWeatherDescription(response));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> detailsTextView.setText("Details fetch unsuccessful")
                );
        rq.add(jsonObjectRequest);
    }

    private String createWeatherDescription(JSONObject obj) throws JSONException {
        String temp = obj.getJSONArray("data").getJSONObject(0).getString("temp");
        String hum = obj.getJSONArray("data").getJSONObject(0).getString("rh");
        String des = obj.getJSONArray("data").getJSONObject(0).getJSONObject("weather").getString("description");
        return "Temperature: " + temp + "\nHumidity: " + hum + "%\nWeather: " + des;
    }
}