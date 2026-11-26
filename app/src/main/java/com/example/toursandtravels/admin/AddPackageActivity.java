package com.example.toursandtravels.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddPackageActivity extends AppCompatActivity {


    private EditText etxtLatitude, etxtLongitude, etxtName, etxtLocation, etxtDescription,etxtPrice;
    private Button buttonOpenMap, btnSubmit;

    String name="", location="", description="",price="", latitude="", longitude="";
    ProgressDialog pDialog;
    JSONArray jsonData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_package);
        etxtName = findViewById(R.id.etxtName);
        etxtLocation = findViewById(R.id.etxtLocation);
        etxtDescription = findViewById(R.id.etxtDescription);
        etxtPrice = findViewById(R.id.etxtPrice);

        etxtLatitude = findViewById(R.id.etxtLatitude);
        etxtLongitude = findViewById(R.id.etxtLongitude);
        buttonOpenMap = findViewById(R.id.buttonOpenMap);
        btnSubmit = findViewById(R.id.btnSubmit);

        Intent intent = getIntent();
        if (intent.hasExtra("latitude")) {
            Double lat = getIntent().getDoubleExtra("latitude", 16.7050);
            Double longi = getIntent().getDoubleExtra("longitude", 74.2433);
            etxtLatitude.setText(""+lat);
            etxtLongitude.setText(""+longi);
        }

        buttonOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // checkLocationPermission();
                Intent intent = new Intent(AddPackageActivity.this, MarkerDraggingActivity.class);
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Fort");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void submitData()
    {
        name = etxtName.getText().toString();
        if(name.equals("")) {
            etxtName.setError("Please enter Station Name");
            etxtName.requestFocus();
            return;
        }
        location = etxtLocation.getText().toString();
        if(location.equals("")) {
            etxtLocation.setError("Please enter location Name");
            etxtLocation.requestFocus();
            return;
        }
        price = etxtPrice.getText().toString();
        if(price.equals("")) {
            etxtPrice.setError("Please enter location Name");
            etxtPrice.requestFocus();
            return;
        }
        description = etxtDescription.getText().toString();
        if(description.equals("")) {
            etxtDescription.setError("Please enter Description.");
            etxtDescription.requestFocus();
            return;
        }
        latitude = etxtLatitude.getText().toString();
        if(latitude.equals("")) {
            etxtLatitude.setError("Latitude is empty");
            etxtLatitude.requestFocus();
            return;
        }
        longitude = etxtLongitude.getText().toString();
        if(longitude.equals("")) {
            etxtLongitude.setError("Longitude is empty");
            etxtLongitude.requestFocus();
            return;
        }
        pDialog = new ProgressDialog(AddPackageActivity.this);
        pDialog.setMessage("validating your details, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                boolean success = DBClass.addPackage(name, location, description, price, latitude, longitude);
                if (success) {
                    Intent intent = new Intent(getApplicationContext(), AddPackagesActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Fort Added..!!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Fort Not Added ...Failed...", Toast.LENGTH_LONG).show();
                }
            }
        }, 500);

    }


}