package com.example.toursandtravels;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.toursandtravels.user.VehiclesActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    EditText etxtEmail, etxtPassword , etxtName, etxtMobileno;

    String id="", name="", mobileno="", email="", password="", usertype="";
    ProgressDialog pDialog;
    JSONArray jsonData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);
        etxtName = findViewById(R.id.etxtName);
        etxtMobileno = findViewById(R.id.etxtMobileno);
    }

    public void btnRegisterClick(View view) {
        name = etxtName.getText().toString();
        mobileno = etxtMobileno.getText().toString();
        if (name.equals("")) {
            etxtName.setError("Please Enter  Name.");
            etxtName.requestFocus();
            return;
        }
        if (mobileno.equals("")) {
            etxtMobileno.setError("Please Enter  Mobileno.");
            etxtMobileno.requestFocus();
            return;
        }
        email = etxtEmail.getText().toString();
        password = etxtPassword.getText().toString();
        if (email.equals("")) {
            etxtEmail.setError("Please Enter  Email.");
            etxtEmail.requestFocus();
            return;
        }
        if (password.equals("")) {
            etxtPassword.setError("Please Enter  Password.");
            etxtPassword.requestFocus();
            return;
        }

        pDialog = new ProgressDialog(RegistrationActivity.this);
        pDialog.setMessage("validating your details, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                boolean success = DBClass.registerUser(name, email, mobileno, password);
                if (success) {
                    Intent intent = new Intent(getApplicationContext(), com.example.toursandtravels.LoginActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Registration Success...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Registration Failed (Email may exist)...", Toast.LENGTH_LONG).show();
                }
            }
        }, 500);

    }

    public void btnLoginClick(View view) {
        Intent intent = new Intent(this, com.example.toursandtravels.LoginActivity.class);
        startActivity(intent);
    }
}