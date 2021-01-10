package com.example.sampahku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BankSampahMaps extends AppCompatActivity {


    int id;

    public void ambilDataBankSampah() {
        System.out.println("Ambil Data Sampah");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.futsaloka.my.id/webapipmobile/index.php/bs";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String respon_str = response.toString();

                try {
                    JSONObject jsonObject = new JSONObject(respon_str);
                    Boolean status = jsonObject.getBoolean("status");

                    if(status == true) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        String arraytxt = jsonObject.getString("data").toString();


                    } else {
                        System.out.println("salaaahhhhhh");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_sampah_maps);

        ambilDataBankSampah();

        id = getIntent().getIntExtra("id", 0);

    }


    public void kembali(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }


    public void tuhutentrem(View view) {
        String URL = "https://www.google.com/maps/dir/?api=1&travelmode=driving&dir_action=navigate&destination=Bank+Sampah+\"Tresno+Tuhutentrem\"";
        Intent goMaps = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(goMaps);
    }

    public void gemahripah(View view) {
        String URL = "https://www.google.com/maps/dir/?api=1&travelmode=driving&dir_action=navigate&destination=Bank+Sampah+Gemah+Ripah+Bantul";
        Intent goMaps = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(goMaps);
    }

    public void sunten(View view) {
        String URL = "https://www.google.com/maps/dir/?api=1&travelmode=driving&dir_action=navigate&destination=Bank+Sampah+Sunten";
        Intent goMaps = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(goMaps);
    }

    public void mondoriko(View view) {
        String URL = "https://www.google.com/maps/dir/?api=1&travelmode=driving&dir_action=navigate&destination=Bank+Sampah+Mondoroko+RW+7";
        Intent goMaps = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(goMaps);
    }

    public void prenggan(View view) {
        String URL = "https://www.google.com/maps/dir/?api=1&travelmode=driving&dir_action=navigate&destination=Bank+Sampah+Barokah+Daleman+Prenggan";
        Intent goMaps = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
        startActivity(goMaps);
    }

    public void onBackPressed() {
        super.onBackPressed();

        this.kembali(null);
    }
}