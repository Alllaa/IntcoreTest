package com.example.intcoretest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    EditText name, email, newPassword,oldPassword;
    ImageView image;
    Button uProfile, uPassword;
    String token;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PREMISSION_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.ed_nameProfile);
        email = findViewById(R.id.ed_emailProfile);
        newPassword = findViewById(R.id.ed_newPasswordProfile);
        oldPassword = findViewById(R.id.ed_oldPasswordProfile);
        image = findViewById(R.id.image);
        uPassword = findViewById(R.id.update_password);
        uProfile = findViewById(R.id.update_profile);

        token = getIntent().getExtras().getString("api_token");
        getProfile();

        uPassword.setOnClickListener(this);
        uProfile.setOnClickListener(this);
        image.setOnClickListener(this);

    }
    void getProfile()
    {
        String url = "https://internship-api-v0.7.intcore.net/api/v1/user/auth/get-profile?api_token="+token;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("response",response.toString());
                    JSONObject main_object = response.getJSONObject("user");
                    String namee = main_object.getString("name");
                    String emaill = main_object.getString("email");



                    name.setText(namee);
                    email.setText(emaill);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.update_profile:
                updateProfile();
                break;
            case R.id.update_password:
                updatePassword();
                break;
            case R.id.image:
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        String[]permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PREMISSION_CODE);
                    }else
                    {
                        pickImageFromGallery();
                    }
                }else
                {
                    pickImageFromGallery();
                }
                break;
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE)
        {
         image.setImageURI(data.getData());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PREMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    void updateProfile()
    {
        String link = "https://internship-api-v0.7.intcore.net/api/v1/user/auth/update-profile?api_token="+token+"&name="+name.getText().toString()+"&email="+email.getText().toString()+"&image=";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response update profile",response.toString());
                Toast.makeText(Profile.this, "profile is updated", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error update profile",error.toString());
                Toast.makeText(Profile.this, "error update profile", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    void updatePassword()
    {
        String link = "https://internship-api-v0.7.intcore.net/api/v1/user/auth/update-password?api_token="+token+"&new_password="+newPassword.getText().toString()+"&old_password="+oldPassword.getText().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("responsePassword",response.toString());
                Intent intent = new Intent(Profile.this,MainActivity.class);
                Toast.makeText(Profile.this, "login again please", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error update Password",error.toString());
                Toast.makeText(Profile.this, "error update Password", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }





}


//String link = "https://internship-api-v0.7.intcore.net/api/v1/user/auth/update-profile?api_token="+token+"&name="+name.getText().toString()+"&email="+email.getText().toString()+"&image=";