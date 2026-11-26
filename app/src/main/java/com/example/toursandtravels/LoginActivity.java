package com.example.toursandtravels;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.toursandtravels.admin.AdminHomeActivity;
import com.example.toursandtravels.user.UserHomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText etxtEmail, etxtPassword;
    String id = "", name = "", mobileno = "", email = "", password = "", usertype = "";
    ProgressDialog pDialog;
    boolean loginSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);
    }

    public void btnLoginClick(View view) {
        email = etxtEmail.getText().toString();
        password = etxtPassword.getText().toString();

        if (email.isEmpty()) {
            etxtEmail.setError("Please Enter Email.");
            etxtEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etxtPassword.setError("Please Enter Password.");
            etxtPassword.requestFocus();
            return;
        }

        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Validating your details, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                android.database.Cursor cursor = DBClass.loginUser(email, password);
                if (cursor != null && cursor.moveToFirst()) {
                    id = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_ID));
                    name = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_NAME));
                    email = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_EMAIL));
                    usertype = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_TYPE));
                    mobileno = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_MOBILENO));
                    loginSuccess = true;
                    cursor.close();
                }

                if (loginSuccess) {
                    saveUserConfiguration();

                    Intent intent;
                    if (usertype.equals("user")) {
                        intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                    } else {
                        intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                    }
                    startActivity(intent);
                    finish();

                    Toast.makeText(getApplicationContext(), "Login Successfully...", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Email not found...", Toast.LENGTH_LONG).show();
                }
            }
        }, 500);
    }

    private void saveUserConfiguration() {
        String query = "DELETE FROM Configuration";
        DBClass.execNonQuery(query);

        query = "INSERT INTO Configuration(CName, CValue) VALUES('usertype', '" + usertype.replace("'", "''") + "')";
        DBClass.execNonQuery(query);

        query = "INSERT INTO Configuration(CName, CValue) VALUES('id', '" + id.replace("'", "''") + "')";
        DBClass.execNonQuery(query);

        query = "INSERT INTO Configuration(CName, CValue) VALUES('name', '" + name.replace("'", "''") + "')";
        DBClass.execNonQuery(query);

        query = "INSERT INTO Configuration(CName, CValue) VALUES('email', '" + email.replace("'", "''") + "')";
        DBClass.execNonQuery(query);

        query = "INSERT INTO Configuration(CName, CValue) VALUES('mobileno', '" + mobileno.replace("'", "''") + "')";
        DBClass.execNonQuery(query);
    }

    public void btnRegistrationClick(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
