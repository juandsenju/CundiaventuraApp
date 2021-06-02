package com.example.cundiaventuraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private TextView regi;
    private String email, password;
    private  String URL = "http://192.168.0.4/Android_Cundiaventura/login.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        email = password = "";
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        regi = findViewById(R.id.regi);



    }

    public void login(View view) {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (!email.equals("") && !password.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                        Intent intent = new Intent(MainActivity.this, Verificado.class);
                        startActivity(intent);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error en el login Usuario/Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", email);
                    data.put("password", password);

                    return data;
                }
            };


            requestQueue.add(stringRequest);
        }  else if(!email.contains("@")) {
            Toast.makeText(this, "El Email debe incluir un @", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Los campos NO pueden estar vacios", Toast.LENGTH_SHORT).show();
        }
    }

    public void register(View v) {
        int id = v.getId();
        if(id == R.id.regi) {
            Intent intent = new Intent(MainActivity.this, Registro.class);
            startActivity(intent);

        }
    }
}