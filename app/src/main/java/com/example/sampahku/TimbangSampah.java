package com.example.sampahku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class TimbangSampah extends AppCompatActivity {

    double beratSampah;
    double saldoSampah;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timbang_sampah);

        id = getIntent().getIntExtra("id", 0);
    }


    public void cekberatsampah(View view) {
        EditText berat_sampah = (EditText)findViewById(R.id.beratsampah);
        RadioButton anorganik = (RadioButton)findViewById(R.id.sampah_anorganik);
        RadioButton organik = (RadioButton)findViewById(R.id.sampah_organik);
        RadioButton b3 = (RadioButton)findViewById(R.id.sampah_b3);
        TextView total_sampah = (TextView)findViewById(R.id.jumlahsaldosampah);

        String beratsmph = berat_sampah.getText().toString();
        double beratmph_d = Double.parseDouble(beratsmph);

        int tarif = 0;

        if(anorganik.isChecked()) {
            tarif = 2000;
        } else if(organik.isChecked()) {
            tarif = 4000;
        } else if(b3.isChecked()) {
            tarif = 5000;
        }

        double tarifFix = beratmph_d * tarif;

        total_sampah.setText(String.valueOf(tarifFix));

        beratSampah = beratmph_d;
        saldoSampah = tarifFix;
    }

    public void timbangsampah(View view) {

        EditText berat_sampah = (EditText)findViewById(R.id.beratsampah);

        RadioButton anorganik = (RadioButton)findViewById(R.id.sampah_anorganik);
        RadioButton organik = (RadioButton)findViewById(R.id.sampah_organik);
        RadioButton b3 = (RadioButton)findViewById(R.id.sampah_b3);

        TextView total_sampah = (TextView)findViewById(R.id.jumlahsaldosampah);



        String beratsmph = berat_sampah.getText().toString();
        double beratmph_d = Double.parseDouble(beratsmph);

        int tarif = 0;

        if(anorganik.isChecked()) {
            tarif = 2000;
        } else if(organik.isChecked()) {
            tarif = 4000;
        } else if(b3.isChecked()) {
            tarif = 5000;
        }

        double tarifFix = beratmph_d * tarif;

        Toast.makeText(this, beratmph_d + " " + tarif + " " + tarifFix, Toast.LENGTH_SHORT).show();

    }

    public void tabungsampah(View view) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.futsaloka.my.id/webapipmobile/index.php/tabungan";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id_user", id);
            postData.put("id_bs", 1);
            postData.put("jumlah", beratSampah);
            postData.put("date", 0);
            postData.put("total_tabungan", saldoSampah);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(postData);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);

                String respon_str = response.toString();

                try {
                    JSONObject jsonObject = new JSONObject(respon_str);
                    Boolean status = jsonObject.getBoolean("status");

                    if(status == true) {
                        Toast.makeText(TimbangSampah.this, "Berhasil Menabung", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TimbangSampah.this, "Gagal Menabung!", Toast.LENGTH_SHORT).show();
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

    public void kembali(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        super.onBackPressed();

        this.kembali(null);
    }
}