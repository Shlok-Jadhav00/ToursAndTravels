package com.example.toursandtravels.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.toursandtravels.DBClass;
import com.example.toursandtravels.R;
import com.example.toursandtravels.SplashScreenActivity;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {
    CardView cardView1, cardView2, cardView3, cardView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        setTitle("Admin Home");
        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.city_mumbai_gateway, "Mumbai • Gateway & Marine Drive", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.city_jaipur_hawamahal, "Jaipur • Hawa Mahal & Pink City", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.city_goa_beach, "Goa • Sun, Sand & Sunset", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.city_udaipur_lake, "Udaipur • City Palace & Lake Pichola", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.city_agra_taj, "Agra • Taj Mahal Sunrise", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);

        cardView1 = findViewById(R.id.card_view1);
        cardView2 = findViewById(R.id.card_view2);
        cardView3 = findViewById(R.id.card_view3);
        cardView4 = findViewById(R.id.card_view4);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(AdminHomeActivity.this, AddPackagesActivity.class);
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, ViewAllBookings.class);
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AppUsersActivity.class);
                startActivity(intent);
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = "DELETE FROM Configuration";
                DBClass.execNonQuery(query);
                Intent intent = new Intent(AdminHomeActivity.this, SplashScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}