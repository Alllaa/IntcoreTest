package com.example.intcoretest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText Email, Password, name, phone;
    Button btnRegister;
    String strEmail, strPassword, strName, strPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        Email = findViewById(R.id.ed_Email);
        Password = findViewById(R.id.ed_Password);
        name = findViewById(R.id.ed_name);
        phone = findViewById(R.id.ed_phone);

        btnRegister = findViewById(R.id.btn_login);

        btnRegister.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        strEmail = Email.getText().toString();
        strPassword = Password.getText().toString();
        strName = name.getText().toString();
        strPhone = phone.getText().toString();
        register();
    }
    void register()
    {
        String link = "https://internship-api-v0.7.intcore.net/api/v1/user/auth/signup";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response",response);
                String x = "{";
                if(response.substring(0,1).equals(x))
                {
                    Intent intent = new Intent(Register.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(Register.this, "The email has already been taken\nor\nThe phone has already been taken"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                Toast.makeText(Register.this, "There is error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String,String>();
                params.put("name", strName);
                params.put("phone", strPhone);
                params.put("password", strPassword);
                params.put("email", strEmail);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

}

