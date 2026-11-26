package com.example.toursandtravels.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.toursandtravels.DBClass;
import com.example.toursandtravels.R;
import com.example.toursandtravels.adapters.Packages;
import com.example.toursandtravels.adapters.PackagesAdapter;
import com.example.toursandtravels.adapters.PackagesAdapterr;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddPackagesActivity extends AppCompatActivity {

    private ArrayList<Packages> packagesArrayList;
    private PackagesAdapterr packagesAdapterr;
    RecyclerView rview;
    public ProgressDialog pDialog;
    Context context = null;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_packages);
        context = getApplicationContext();
        rview =  findViewById(R.id.rview);
        fab = findViewById(R.id.floatingActionButton);
        list();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AddPackagesActivity.this,AddPackageActivity.class);
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add Forts");
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
    public void list()
    {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("downloading, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        pDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                try {
                    android.database.Cursor cursor = DBClass.getPackages();
                    packagesArrayList = new ArrayList<>();
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            Packages packages = new Packages();
                            packages.id = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_ID));
                            packages.name = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_NAME));
                            packages.location = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_LOCATION));
                            packages.description = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_DESCRIPTION));
                            packages.price = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_PRICE));
                            packages.lattitude = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_LATITUDE));
                            packages.longitude = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_LONGITUDE));
                            packagesArrayList.add(packages);
                        }
                        cursor.close();
                    }
                    packagesAdapterr = new PackagesAdapterr(getApplicationContext(), packagesArrayList);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    rview.setLayoutManager(layoutManager);
                    rview.setAdapter(packagesAdapterr);
                } catch (Exception e) {
                    Log.e("Exception ", ">> " + e);
                }
            }
        }, 500);
    }
}