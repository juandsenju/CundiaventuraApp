package com.example.cundiaventuraapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    EditText etName, etPassword, etEmail, etPhone, etPassword2;
    Button btnRegistro;


    RequestQueue requestQueue;

    private static final String URL1 = "http://192.168.0.4/Android_Cundiaventura/registro.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);


        requestQueue = Volley.newRequestQueue(this);

        // UI
        initUI();

    }

    private void initUI(){

        //EditText
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword2 = findViewById(R.id.etPassword2);

        //Button
        btnRegistro = findViewById(R.id.btnRegistro);

    }

    public void save(View v) {
        int id = v.getId();

        if (id == R.id.btnRegistro) {

            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String password2 = etPassword2.getText().toString().trim();

            if (!password.equals(password2)) {
                Toast.makeText(this, "Las contraseñas NO coinciden", Toast.LENGTH_SHORT).show();

            }
            else if(!email.contains("@")) {
                Toast.makeText(this, "El Email debe incluir un @", Toast.LENGTH_SHORT).show();
            }
            else if (!name.equals("") && !email.equals("") && !phone.equals("") && !password.equals("")) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL1, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            etName.setText("");
                            etEmail.setText("");
                            etPassword.setText("");
                            etPhone.setText("");
                            etPassword2.setText("");
                            Toast.makeText(Registro.this, "Registro creado Satisfactoriamente", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registro.this, "Error al crear el registro", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> data = new HashMap<>();
                        data.put("name", name);
                        data.put("email", email);
                        data.put("phone", phone);
                        data.put("password", password);

                        return data;
                    }
                };

                requestQueue.add(stringRequest);
            }else {
                Toast.makeText(this, "Los campos NO pueden estar vacios", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
