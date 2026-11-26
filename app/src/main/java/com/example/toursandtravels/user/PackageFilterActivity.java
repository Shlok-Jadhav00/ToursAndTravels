package com.example.toursandtravels.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.toursandtravels.R;

public class PackageFilterActivity extends AppCompatActivity {

    EditText etxtLocation;
    String location = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_filter);
        etxtLocation = findViewById(R.id.etxtLocation);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Explore Cities");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnSearchClick(View view) {
        location = etxtLocation.getText().toString().trim();
        if (location.isEmpty()) {
            location = "All";
        }
        Intent intent = new Intent(this, FindPackagesActivity.class);
        intent.putExtra("location", location);
        startActivity(intent);
    }

    public void btnBrowseAllClick(View view) {
        Intent intent = new Intent(this, FindPackagesActivity.class);
        intent.putExtra("location", "All");
        startActivity(intent);
    }
}
