package com.example.toursandtravels.user;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toursandtravels.DBClass;
import com.example.toursandtravels.R;
import com.example.toursandtravels.adapters.Booking;
import com.example.toursandtravels.adapters.BookingAdapter;

import java.util.ArrayList;

public class MyBookings extends AppCompatActivity {

    private ArrayList<Booking> bookingArrayList;
    private BookingAdapter bookingAdapter;
    private RecyclerView rview;
    private TextView txtEmpty;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_bookings);

        rview = findViewById(R.id.rview);
        txtEmpty = findViewById(R.id.txtEmpty);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("My Bookings");

        listBookings();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    private void listBookings() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading bookings, please wait...");
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

                    if (bookingArrayList.isEmpty()) {
                        txtEmpty.setVisibility(View.VISIBLE);
                        rview.setVisibility(View.GONE);
                    } else {
                        txtEmpty.setVisibility(View.GONE);
                        rview.setVisibility(View.VISIBLE);
                    }

                    bookingAdapter = new BookingAdapter(MyBookings.this, bookingArrayList);
                    rview.setLayoutManager(new LinearLayoutManager(MyBookings.this));
                    rview.setAdapter(bookingAdapter);
                } catch (Exception e) {
                    Log.e("Exception", ">> " + e.getMessage());
                }
            }
        }, 250);
    }
}
