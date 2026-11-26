package com.example.toursandtravels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.toursandtravels.admin.AdminHomeActivity;
import com.example.toursandtravels.user.UserHomeActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        createDatabase();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);

                String query = "SELECT * FROM Configuration";
                if (DBClass.checkIfRecordExist(query)) {
                    query = "SELECT CValue FROM Configuration WHERE CName = 'usertype'";
                    String usertype = DBClass.getSingleValue(query);

                    if ("admin".equals(usertype)) {
                        intent = new Intent(SplashScreenActivity.this, AdminHomeActivity.class);
                    } else if ("user".equals(usertype)) {
                        intent = new Intent(SplashScreenActivity.this, UserHomeActivity.class);
                    } else {
                        intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    }
                }

                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    private void createDatabase() {
        DBClass databaseInstance = new DBClass(this); // Initialize DBClass instance
        String query = "CREATE TABLE IF NOT EXISTS Configuration (CName VARCHAR, CValue VARCHAR)";
        DBClass.execNonQuery(query); // Execute SQL query to create table if not exists
    }
}
