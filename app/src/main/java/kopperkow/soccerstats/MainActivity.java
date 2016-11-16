package kopperkow.soccerstats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    static final String COTTONWOOD_HEIGHTS_REQUEST = "http://api.wunderground.com/api/b126e4e263fe3435/conditions/q/UT/Cottonwood_Heights.json";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupView();
        setupWeather();
    }

    private void setupView() {

    }

    private void setupWeather() {
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(new JsonObjectRequest(JsonObjectRequest.Method.GET, COTTONWOOD_HEIGHTS_REQUEST, null, this, this));
    }

    public void onClickAddPlayer(View view) {
        startActivity(new Intent(this, AddPlayerActivity.class));
    }

    public void onClickDrawPlays(View view) {
        startActivity(new Intent(this, DrawingActivity.class));
    }

    @Override
    public void onResponse(JSONObject response) {
        String city = "";
        String tempF = "";
        String weather = "";
        String weatherIconUrl = "";
        try {
            JSONObject currentObservation = getCurrentObservationFromJson(response);
            if (currentObservation != null) {
                city = currentObservation.getJSONObject("displayLocation").getString("city");
                tempF = currentObservation.getString("temp_f");
                weather = currentObservation.getString("weather");
                weatherIconUrl = currentObservation.getString("icon_url");


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getCurrentObservationFromJson(JSONObject j) {
        try {
            if (j.has("current_observation") && !j.isNull("current_observation")) {
                return j.getJSONObject("current_observation");
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    // http://api.wunderground.com/api/b126e4e263fe3435/conditions/q/UT/Cottonwood_Heights.json
}
