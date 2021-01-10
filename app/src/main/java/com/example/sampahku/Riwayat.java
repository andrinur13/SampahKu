package com.example.sampahku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Riwayat extends AppCompatActivity {

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        id = getIntent().getIntExtra("id", 0);

        System.out.println("id user riwayat " + id);

        isiData();
    }


    public void kembali(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    protected void isiData() {

        final TableLayout tableLayout = (TableLayout)this.findViewById(R.id.table_layout);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.futsaloka.my.id/webapipmobile/index.php/tabunganuser";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id_user", String.valueOf(id).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("response " + response);

                String respon_str = response.toString();

                try {
                    JSONObject jsonObject = new JSONObject(respon_str);
                    Boolean status = jsonObject.getBoolean("status");

                    if(status == true) {
                        JSONArray dataRiwayat = jsonObject.getJSONArray("data");


                        for(int i=0; i<dataRiwayat.length(); i++) {
                            JSONObject riwayat = dataRiwayat.getJSONObject(i);

                            TableRow row = (TableRow)getLayoutInflater().inflate(R.layout.list_riwayat, null);

                            TextView tv_total_sampah = (TextView)findViewById(R.id.riwayat_total_sampah);
                            TextView tv_total_saldo = (TextView)findViewById(R.id.riwayat_total_saldo);

                            System.out.println(riwayat);

                            String total_tabungan = riwayat.getString("total_tabungan").toString();
                            String jumlah = riwayat.getString("jumlah").toString();

                            System.out.println("Total tabungan : " + total_tabungan);
                            System.out.println("Jumlah Sampah : " + jumlah);

                            int count = i+1;

                            String count_txt = String.valueOf(count).toString();

                            ((TextView)row.findViewById(R.id.no)).setText(count_txt);
                            ((TextView)row.findViewById(R.id.riwayat_total_sampah)).setText(jumlah + " KG");
                            ((TextView)row.findViewById(R.id.riwayat_total_saldo)).setText("Rp. " + total_tabungan);

                            tableLayout.addView(row);

                        }
                    }

                } catch (JSONException e) {
                    System.out.println("errorrrr broooo");
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


    public void onBackPressed() {
        super.onBackPressed();

        this.kembali(null);
    }

}