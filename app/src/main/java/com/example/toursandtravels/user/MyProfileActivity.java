package com.example.toursandtravels.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.toursandtravels.DBClass;
import com.example.toursandtravels.R;
import com.example.toursandtravels.SplashScreenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyProfileActivity extends AppCompatActivity {

    EditText etxtName, etxtEmail, etxtMobileno, etxtPassword;
    Button btnSubmit, btnLogout;
    String id;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        // Initialize UI elements
        etxtName = findViewById(R.id.etxtName);
        etxtMobileno = findViewById(R.id.etxtMobileno);
        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnLogout = findViewById(R.id.btnLogout);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Profile");

        // Fetch user ID from local storage or configuration
        String query = "SELECT CValue FROM Configuration WHERE CName = 'id'";
        id = DBClass.getSingleValue(query);

        // If user ID is not found, display a toast and return
        if (id == null || id.isEmpty()) {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_LONG).show();
            return;
        }

        // Fetch and display user details
        userDetails();
    }

    // Method to fetch user details from server
    private void userDetails() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading data, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                android.database.Cursor cursor = DBClass.getProfile(id);
                if (cursor != null && cursor.moveToFirst()) {
                    etxtName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_NAME)));
                    etxtEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_EMAIL)));
                    etxtMobileno.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_MOBILENO)));
                    etxtPassword.setText(cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_PASSWORD)));
                    cursor.close();
                } else {
                    Toast.makeText(getApplicationContext(), "No Information Found...", Toast.LENGTH_LONG).show();
                }
            }
        }, 500);
    }

    // Method to handle JSON parsing error
    private void handleJsonParsingError(JSONException e) {
        Log.e("JSON Parsing Error", "Error parsing JSON response: " + e.getMessage());
        Toast.makeText(getApplicationContext(), "Error parsing JSON response. Please try again later.", Toast.LENGTH_LONG).show();
    }

    // Method to handle logout button click
    public void logOutClick(View view) {
        // Clear user session or configuration
        String query = "DELETE FROM Configuration";
        DBClass.execNonQuery(query);

        // Redirect to SplashScreenActivity
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }

    // Method to handle update profile button click
    public void updateProfileClick(View view) {
        // Fetch user input
        String name = etxtName.getText().toString().trim();
        String email = etxtEmail.getText().toString().trim();
        String mobileno = etxtMobileno.getText().toString().trim();
        String password = etxtPassword.getText().toString().trim();

        // Validate user input
        if (name.isEmpty() || email.isEmpty() || mobileno.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        // Display progress dialog while validating details
        pDialog = new ProgressDialog(MyProfileActivity.this);
        pDialog.setMessage("Updating your profile, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                boolean success = DBClass.updateProfile(id, name, email, mobileno, password);
                if (success) {
                    Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to update profile", Toast.LENGTH_LONG).show();
                }
            }
        }, 500);
    }

    // Handle back button press in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close current activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
