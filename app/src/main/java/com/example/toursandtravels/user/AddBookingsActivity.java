package com.example.toursandtravels.user;

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
import com.example.toursandtravels.DBClass;
import com.example.toursandtravels.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddBookingsActivity extends AppCompatActivity {

    EditText etxtUserName, etxtAddress , etxtMobileno;

    String id="", mobileno="", address="", username="";
    ProgressDialog pDialog;
    JSONArray jsonData = null;
    String packageid, packagename,packagelocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bookings);
        etxtAddress = findViewById(R.id.etxtAddress);
        etxtUserName = findViewById(R.id.etxtUserName);
        etxtMobileno = findViewById(R.id.etxtMobileno);

        packageid = getIntent().getStringExtra("id");
        packagename = getIntent().getStringExtra("name");
        packagelocation = getIntent().getStringExtra("location");



    }

    public void btnBookingClick(View view) {
        username = etxtUserName.getText().toString();
        mobileno = etxtMobileno.getText().toString();
        if (username.equals("")) {
            etxtUserName.setError("Please Enter User Name.");
            etxtUserName.requestFocus();
            return;
        }
        if (mobileno.equals("")) {
            etxtMobileno.setError("Please Enter  Mobileno.");
            etxtMobileno.requestFocus();
            return;
        }
        address = etxtAddress.getText().toString();
        username = etxtUserName.getText().toString();
        if (address.equals("")) {
            etxtAddress.setError("Please Enter  Your Address.");
            etxtAddress.requestFocus();
            return;
        }
        pDialog = new ProgressDialog(AddBookingsActivity.this);
        pDialog.setMessage("validating your details, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                boolean success = DBClass.addBooking(packagename, packagelocation, username, mobileno, address);
                if (success) {
                    Intent intent = new Intent(getApplicationContext(), MyBookings.class);
                    intent.putExtra("id", id);
                    intent.putExtra("username", username);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Booking Success...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Booking Failed...", Toast.LENGTH_LONG).show();
                }
            }
        }, 500);

    }

}