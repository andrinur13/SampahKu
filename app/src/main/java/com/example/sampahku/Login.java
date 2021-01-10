package com.example.sampahku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button lgnBtn = (Button)findViewById(R.id.btn_login);

        lgnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText username = (EditText)findViewById(R.id.username);
                EditText password = (EditText)findViewById(R.id.password);

                String username_text = username.getText().toString();
                String password_text = password.getText().toString();


                proseslogin(username_text, password_text);
            }
        });
    }


    protected void proseslogin(String username, String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://www.futsaloka.my.id/webapipmobile/index.php/user/login";
        JSONObject postData = new JSONObject();

        try {
            postData.put("username", username);
            postData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

                        int type = dt.getInt("type");

                        if(type == 1) {

                        } else if(type == 2) {
                            String username_txt = dt.getString("username");
                            int id = dt.getInt("id");

                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("username", username_txt);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }

                    } else {
                        Toast.makeText(Login.this, "Login Gagal!", Toast.LENGTH_SHORT).show();
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