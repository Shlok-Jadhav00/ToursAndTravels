package com.example.toursandtravels.admin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.toursandtravels.DBClass;
import com.example.toursandtravels.R;
import com.example.toursandtravels.adapters.User;
import com.example.toursandtravels.adapters.UserAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppUsersActivity extends AppCompatActivity {

    private ArrayList<User> userArrayList;
    private UserAdapter userAdapter;
    private RecyclerView rview;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_users);

        rview = findViewById(R.id.rview);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("App Users");

        listUsers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Close activity on back button press
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the Volley request if activity is destroyed
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    private void listUsers() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Downloading, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish(); // Finish activity if dialog is canceled
            }
        });
        pDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                try {
                    android.database.Cursor cursor = DBClass.getAllUsers();
                    userArrayList = new ArrayList<>();
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            User user = new User();
                            user.id = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_ID));
                            user.name = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_NAME));
                            user.email = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_EMAIL));
                            user.mobileno = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_USER_MOBILENO));
                            userArrayList.add(user);
                        }
                        cursor.close();
                    }
                    userAdapter = new UserAdapter(AppUsersActivity.this, userArrayList);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    rview.setLayoutManager(layoutManager);
                    rview.setAdapter(userAdapter);
                } catch (Exception e) {
                    Log.e("Exception", ">> " + e.getMessage());
                }
            }
        }, 500);
    }
}
