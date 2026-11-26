package com.example.toursandtravels.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toursandtravels.DBClass;
import com.example.toursandtravels.R;
import com.example.toursandtravels.adapters.Packages;
import com.example.toursandtravels.adapters.PackagesAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FindPackagesActivity extends AppCompatActivity {

    private static final String TAG = "FindPackagesActivity";
    private ArrayList<Packages> allPackagesList = new ArrayList<>();
    private ArrayList<Packages> displayedList = new ArrayList<>();
    private PackagesAdapter packagesAdapter;
    private RecyclerView rview;
    private TextView txtEmpty;
    private EditText etxtSearch;
    private Button btnFilterAll, btnFilterSaved, btnSortLowHigh, btnSortHighLow, btnSortTopRated;
    private ProgressDialog pDialog;
    private String location = "";
    private Double lat, lon;
    private String currentMode = "ALL"; // ALL, SAVED, LOW_HIGH, HIGH_LOW, TOP_RATED

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_packages);

        Intent intent = getIntent();
        if (intent.hasExtra("location")) {
            location = intent.getStringExtra("location");
        } else if (intent.hasExtra("lat")) {
            lat = intent.getDoubleExtra("lat", 18.9220);
            lon = intent.getDoubleExtra("lon", 72.8347);
        }

        rview = findViewById(R.id.rview);
        txtEmpty = findViewById(R.id.txtEmpty);
        etxtSearch = findViewById(R.id.etxtSearch);
        btnFilterAll = findViewById(R.id.btnFilterAll);
        btnFilterSaved = findViewById(R.id.btnFilterSaved);
        btnSortLowHigh = findViewById(R.id.btnSortLowHigh);
        btnSortHighLow = findViewById(R.id.btnSortHighLow);
        btnSortTopRated = findViewById(R.id.btnSortTopRated);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (location != null && !location.isEmpty() && !location.equalsIgnoreCase("All")) {
            setTitle("Places in " + location);
        } else {
            setTitle("Explore Tourist Places");
        }

        setupSearchAndChips();
        list();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (packagesAdapter != null) {
            applyFilterAndSort();
        }
    }

    private void setupSearchAndChips() {
        etxtSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilterAndSort();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnFilterAll.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { setMode("ALL", btnFilterAll); }
        });
        btnFilterSaved.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { setMode("SAVED", btnFilterSaved); }
        });
        btnSortLowHigh.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { setMode("LOW_HIGH", btnSortLowHigh); }
        });
        btnSortHighLow.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { setMode("HIGH_LOW", btnSortHighLow); }
        });
        btnSortTopRated.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) { setMode("TOP_RATED", btnSortTopRated); }
        });
    }

    private void setMode(String mode, Button activeBtn) {
        currentMode = mode;
        Button[] allBtns = {btnFilterAll, btnFilterSaved, btnSortLowHigh, btnSortHighLow, btnSortTopRated};
        int primary = ContextCompat.getColor(this, R.color.warm_primary);
        int surfaceAlt = ContextCompat.getColor(this, R.color.warm_surface_alt);
        int white = ContextCompat.getColor(this, R.color.white);

        for (Button b : allBtns) {
            boolean isActive = (b == activeBtn);
            b.setBackgroundTintList(ColorStateList.valueOf(isActive ? primary : surfaceAlt));
            b.setTextColor(isActive ? white : primary);
        }
        applyFilterAndSort();
    }

    private void applyFilterAndSort() {
        String query = etxtSearch.getText().toString().trim().toLowerCase();
        displayedList = new ArrayList<>();

        for (Packages p : allPackagesList) {
            if (currentMode.equals("SAVED") && !DBClass.isFavorite(p.id)) {
                continue;
            }
            if (!query.isEmpty()) {
                boolean matchesName = p.name != null && p.name.toLowerCase().contains(query);
                boolean matchesLoc = p.location != null && p.location.toLowerCase().contains(query);
                boolean matchesCat = p.category != null && p.category.toLowerCase().contains(query);
                if (!matchesName && !matchesLoc && !matchesCat) {
                    continue;
                }
            }
            displayedList.add(p);
        }

        if (currentMode.equals("LOW_HIGH")) {
            Collections.sort(displayedList, new Comparator<Packages>() {
                @Override public int compare(Packages a, Packages b) {
                    return Double.compare(parseDoubleSafe(a.price), parseDoubleSafe(b.price));
                }
            });
        } else if (currentMode.equals("HIGH_LOW")) {
            Collections.sort(displayedList, new Comparator<Packages>() {
                @Override public int compare(Packages a, Packages b) {
                    return Double.compare(parseDoubleSafe(b.price), parseDoubleSafe(a.price));
                }
            });
        } else if (currentMode.equals("TOP_RATED")) {
            Collections.sort(displayedList, new Comparator<Packages>() {
                @Override public int compare(Packages a, Packages b) {
                    return Double.compare(parseDoubleSafe(b.rating), parseDoubleSafe(a.rating));
                }
            });
        }

        if (displayedList.isEmpty()) {
            txtEmpty.setVisibility(View.VISIBLE);
            rview.setVisibility(View.GONE);
        } else {
            txtEmpty.setVisibility(View.GONE);
            rview.setVisibility(View.VISIBLE);
        }

        if (packagesAdapter != null) {
            packagesAdapter.updateList(displayedList);
        }
    }

    private double parseDoubleSafe(String val) {
        try {
            return val != null ? Double.parseDouble(val.trim()) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void list() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading places, please wait...");
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
                if (pDialog != null && pDialog.isShowing()) {
                    pDialog.dismiss();
                }
                try {
                    android.database.Cursor cursor = DBClass.findPackages(location, String.valueOf(lat), String.valueOf(lon));
                    allPackagesList = new ArrayList<>();
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
                            packages.imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_IMAGE));
                            packages.rating = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_RATING));
                            packages.category = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_CATEGORY));
                            packages.duration = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_DURATION));
                            packages.bestTime = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_BEST_TIME));
                            packages.highlights = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_PKG_HIGHLIGHTS));
                            allPackagesList.add(packages);
                        }
                        cursor.close();
                    }

                    packagesAdapter = new PackagesAdapter(FindPackagesActivity.this, displayedList);
                    rview.setLayoutManager(new LinearLayoutManager(FindPackagesActivity.this));
                    rview.setAdapter(packagesAdapter);
                    applyFilterAndSort();
                } catch (Exception e) {
                    Log.e(TAG, "Exception >> " + e);
                }
            }
        }, 200);
    }
}
