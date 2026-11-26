package com.example.toursandtravels.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.toursandtravels.DBClass;
import com.example.toursandtravels.R;
import com.example.toursandtravels.SplashScreenActivity;

import java.util.ArrayList;

public class UserHomeActivity extends AppCompatActivity {

    CardView cardView1, cardView2, cardView3, cardView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

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
                Intent intent = new Intent(UserHomeActivity.this, PackageFilterActivity.class);
                startActivity(intent);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomeActivity.this, MyBookings.class);
                startActivity(intent);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomeActivity.this, MyProfileActivity.class);
                startActivity(intent);
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = "DELETE FROM Configuration";
                DBClass.execNonQuery(query);
                Intent intent = new Intent(UserHomeActivity.this, SplashScreenActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Popular city quick-filter buttons
        setupCityButton(R.id.btnCityAll, "All");
        setupCityButton(R.id.btnCityMumbai, "Mumbai");
        setupCityButton(R.id.btnCityJaipur, "Jaipur");
        setupCityButton(R.id.btnCityGoa, "Goa");
        setupCityButton(R.id.btnCityPune, "Pune");
        setupCityButton(R.id.btnCityUdaipur, "Udaipur");
    }

    private void setupCityButton(int buttonId, final String cityName) {
        Button btn = findViewById(buttonId);
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserHomeActivity.this, FindPackagesActivity.class);
                    intent.putExtra("location", cityName);
                    startActivity(intent);
                }
            });
        }
    }
}
