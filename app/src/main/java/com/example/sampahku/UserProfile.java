package com.example.sampahku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserProfile extends AppCompatActivity {

    int id;
    String passwordFix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        id = getIntent().getIntExtra("id", 0);

        System.out.println("id user riwayat " + id);

        ambilData();
    }


    public void isiNilai(String username, String name, String password, String alamat, String type) {
        EditText nameET = (EditText)findViewById(R.id.nama);
        EditText usernameET = (EditText)findViewById(R.id.username);
        EditText alamatET = (EditText)findViewById(R.id.alamat);
        EditText passwordET = (EditText)findViewById(R.id.password);

        nameET.setText(name);
        usernameET.setText(username);
        alamatET.setText(alamat);

        System.out.println("Data yang mau diedit" + name + username + alamat + password);
    }


    public void logout(View view) {
        finish();
    }


    public void ambilData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.futsaloka.my.id/webapipmobile/index.php/userid";
        JSONObject postData = new JSONObject();

        try {
            postData.put("id", id);
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
                        String data = jsonObject.getString("data").toString();
                        JSONObject dt = new JSONObject(data);

                        String username = dt.getString("username").toString();
                        String name = dt.getString("name").toString();
                        String alamat = dt.getString("alamat").toString();
                        String password = dt.getString("password").toString();
                        String type = dt.getString("type").toString();

                        passwordFix = password;

                        System.out.println("dalam json : " + dt.getString("username").toString());

                        isiNilai(username, name, password, alamat, type);

                    } else {
                        Toast.makeText(UserProfile.this, "Gagal fetch!", Toast.LENGTH_SHORT).show();
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


    public void updateData(View view) {

        EditText nameET = (EditText)findViewById(R.id.nama);
        EditText usernameET = (EditText)findViewById(R.id.username);
        EditText alamatET = (EditText)findViewById(R.id.alamat);
        EditText passwordET = (EditText)findViewById(R.id.password);

        String name_str = nameET.getText().toString();
        String username_str = usernameET.getText().toString();
        String alamat_str = alamatET.getText().toString();
        String password_str = passwordET.getText().toString();

        System.out.println(name_str + username_str + alamat_str + password_str);
        System.out.println("Password " + passwordFix);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.futsaloka.my.id/webapipmobile/index.php/user";
        JSONObject postData = new JSONObject();

        String passwd;

        if(password_str.equals("")) {
            passwd = passwordFix;
        } else {
            passwd = password_str;
        }

        try {
            postData.put("id", id);
            postData.put("username", username_str);
            postData.put("name", name_str);
            postData.put("password", 0);
//            postData.put("total_tabungan", saldoSampah);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(postData);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);

                String respon_str = response.toString();

                try {
                    JSONObject jsonObject = new JSONObject(respon_str);
                    Boolean status = jsonObject.getBoolean("status");

                    if(status == true) {
                        Toast.makeText(UserProfile.this, "Berhasil Menabung", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserProfile.this, "Gagal Menabung!", Toast.LENGTH_SHORT).show();
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
}