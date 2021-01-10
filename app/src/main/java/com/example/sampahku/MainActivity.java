package com.example.sampahku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity {

    private int id;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("username");
        id = getIntent().getIntExtra("id", 0);

        System.out.println("id user " + id);

        ambilDataTabungan(id);
        ambilDataUser(id);
    }


    protected void setHomeFromTabungan(String saldo, String sampah) {
        TextView saldoTV = (TextView)findViewById(R.id.saldo);
        TextView sampahTV = (TextView)findViewById(R.id.total_sampah);

        saldoTV.setText("Rp. " + saldo);
        sampahTV.setText(sampah + " KG");
    }
    
    protected void setHomeFromUser(String nama) {
        TextView userTV = (TextView)findViewById(R.id.nama_user);
        
        userTV.setText(nama);
    }

    protected void ambilDataTabungan(int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.futsaloka.my.id/webapipmobile/index.php/tabunganuser";
        JSONObject postData = new JSONObject();

        System.out.println("url tabungan " + id);

        try {
            postData.put("id_user", String.valueOf(id).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                String respon_str = response.toString();
                int total_saldo = 0;
                int total_sampah = 0;

                try {
                    JSONObject jsonObject = new JSONObject(respon_str);
                    Boolean status = jsonObject.getBoolean("status");

                    if(status == true) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for(int i=0; i<jsonArray.length(); i++) {
                            JSONObject tabungan = jsonArray.getJSONObject(i);

                            String jumlah_txt = tabungan.getString("jumlah").toString();
                            total_sampah += Integer.valueOf(jumlah_txt);

                            String saldo_txt = tabungan.getString("total_tabungan").toString();
                            total_saldo += Integer.valueOf(saldo_txt);
                        }

                        System.out.println("Jumlah saldo : " + total_saldo);
                        System.out.println("Jumlah sampah : " + total_sampah);
                    } else {
                        System.out.println("salaaahhhhhh");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                setHomeFromTabungan(String.valueOf(total_saldo).toString(), String.valueOf(total_sampah).toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    protected void ambilDataUser(int id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.futsaloka.my.id/webapipmobile/index.php/userid";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", String.valueOf(id).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                String respon_str = response.toString();
                String nama = null;

                try {
                    JSONObject jsonObject = new JSONObject(respon_str);
                    Boolean status = jsonObject.getBoolean("status");

                    if(status == true) {
                        String data = jsonObject.getString("data").toString();
                        JSONObject dt = new JSONObject(data);

                        nama = dt.getString("name").toString();

                        System.out.println("Nama : " + nama);

                    } else {
                        System.out.println("salah bro");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                setHomeFromUser(nama);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    public void go_bank_sampah(View view) {
        Intent intent = new Intent(this, BankSampahMaps.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    public void go_timbang_sampah(View view) {
        Intent intent = new Intent(this, TimbangSampah.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    public void go_riwayat(View view) {
        Intent intent = new Intent(this, Riwayat.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    public void go_user_porofile(View view) {
        Intent intent = new Intent(this, UserProfile.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }
}