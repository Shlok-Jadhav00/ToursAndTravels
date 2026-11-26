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
import com.example.toursandtravels.adapters.Booking;
import com.example.toursandtravels.adapters.BookingAdapter;
import com.example.toursandtravels.adapters.User;
import com.example.toursandtravels.adapters.UserAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewAllBookings extends AppCompatActivity {

    private ArrayList<Booking> bookingArrayList;
    private BookingAdapter bookingAdapter;
    private RecyclerView rview;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_bookings);

        rview = findViewById(R.id.rview);

        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("All Bookings");

        listBookings();
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

    private void listBookings() {
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
                    android.database.Cursor cursor = DBClass.getBookings();
                    bookingArrayList = new ArrayList<>();
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            Booking booking = new Booking();
                            booking.id = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_BKG_ID));
                            booking.username = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_BKG_USERNAME));
                            booking.address = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_BKG_ADDRESS));
                            booking.mobileno = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_BKG_MOBILENO));
                            booking.fort = cursor.getString(cursor.getColumnIndexOrThrow(DBClass.COLUMN_BKG_FORT));
                            bookingArrayList.add(booking);
                        }
                        cursor.close();
                    }
                    bookingAdapter = new BookingAdapter(ViewAllBookings.this, bookingArrayList);
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    rview.setLayoutManager(layoutManager);
                    rview.setAdapter(bookingAdapter);
                } catch (Exception e) {
                    Log.e("Exception", ">> " + e.getMessage());
                }
            }
        }, 500);
    }
}
