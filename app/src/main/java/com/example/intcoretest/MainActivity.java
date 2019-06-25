package com.example.intcoretest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, password ;
    Button signIn, signUp ;
    String strEmail,strPassword;
    TextView txRegister;
     boolean ok ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = findViewById(R.id.ed_email);
        password = findViewById(R.id.ed_password);
        signIn = findViewById(R.id.btn_signin);
        signUp = findViewById(R.id.btn_signup);
        txRegister = findViewById(R.id.tx_register);

      //  txRegister.setText(hello);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_signin:
               // Toast.makeText(this, "Sign In", Toast.LENGTH_SHORT).show();
                strEmail = email.getText().toString();
                strPassword = password.getText().toString();
                logIn();
                break;
            case R.id.btn_signup:
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
                break;
        }
    }

    void logIn()
    {
        String url  = "https://internship-api-v0.7.intcore.net/api/v1/user/auth/signin";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response",response);
                int z =  response.indexOf("api_token");
                String apiToken = response.substring(z+12,z+12+32);

                Intent intent = new Intent(MainActivity.this,Profile.class);
                intent.putExtra("api_token",apiToken);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                ok = false;
                Toast.makeText(MainActivity.this, "These credentials do not match our records", Toast.LENGTH_SHORT).show();

            }
        }) {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", strEmail);
            params.put("password", strPassword);
            return params;
        }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

}

/*
* void logIn()
    {
        String url  = "https://internship-api-v0.7.intcore.net/api/v1/user/auth/signin";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response",response);
                int z =  response.indexOf("api_token");
                String apiToken = response.substring(z+12,z+12+32);

                Intent intent = new Intent(MainActivity.this,Profile.class);
                intent.putExtra("api_token",apiToken);
                   startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                ok = false;
                Toast.makeText(MainActivity.this, "These credentials do not match our records", Toast.LENGTH_SHORT).show();

            }
        }) {@Override
        protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("name", strEmail);
            params.put("password", strPassword);
            return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }*/
