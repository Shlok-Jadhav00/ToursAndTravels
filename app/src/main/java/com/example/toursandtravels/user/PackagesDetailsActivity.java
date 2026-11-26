package com.example.toursandtravels.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toursandtravels.DBClass;
import com.example.toursandtravels.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mappls.sdk.maps.MapplsMap;
import com.mappls.sdk.maps.OnMapReadyCallback;
import com.mappls.sdk.maps.geometry.LatLng;

public class PackagesDetailsActivity extends AppCompatActivity implements OnMapReadyCallback, MapplsMap.OnMapLongClickListener {

    TextView txtName, txtMeta, txtDetails, txtRating, txtHighlights, txtBestTime;
    ImageView imgHero, imgFavorite;
    String id = "", name = "", description = "", location = "", price = "";
    String rating = "4.8", category = "City Tour", duration = "1 Day", bestTime = "Oct – Mar", highlights = "Guided Tour • Entry Pass";
    int imageResId = R.drawable.img_4;
    Double s_lat = 0.0, s_lon = 0.0, e_lat = 18.9220, e_lon = 72.8347;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages_details);

        txtName = findViewById(R.id.txtName);
        txtMeta = findViewById(R.id.txtMeta);
        txtDetails = findViewById(R.id.txtDetails);
        txtRating = findViewById(R.id.txtRating);
        txtHighlights = findViewById(R.id.txtHighlights);
        txtBestTime = findViewById(R.id.txtBestTime);
        imgHero = findViewById(R.id.imgHero);
        imgFavorite = findViewById(R.id.imgFavorite);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        location = intent.getStringExtra("location");
        description = intent.getStringExtra("description");
        price = intent.getStringExtra("price");
        imageResId = intent.getIntExtra("imageResId", R.drawable.img_4);
        if (intent.hasExtra("rating") && intent.getStringExtra("rating") != null) rating = intent.getStringExtra("rating");
        if (intent.hasExtra("category") && intent.getStringExtra("category") != null) category = intent.getStringExtra("category");
        if (intent.hasExtra("duration") && intent.getStringExtra("duration") != null) duration = intent.getStringExtra("duration");
        if (intent.hasExtra("bestTime") && intent.getStringExtra("bestTime") != null) bestTime = intent.getStringExtra("bestTime");
        if (intent.hasExtra("highlights") && intent.getStringExtra("highlights") != null) highlights = intent.getStringExtra("highlights");

        String lat = intent.getStringExtra("lattitude");
        String lon = intent.getStringExtra("longitude");
        try {
            e_lat = (lat != null && !lat.isEmpty()) ? Double.parseDouble(lat) : 18.9220;
            e_lon = (lon != null && !lon.isEmpty()) ? Double.parseDouble(lon) : 72.8347;
        } catch (NumberFormatException ex) {
            e_lat = 18.9220;
            e_lon = 72.8347;
        }

        imgHero.setImageResource(imageResId);
        txtName.setText(name);
        txtRating.setText(rating);
        txtMeta.setText(location + "  •  " + category + " (" + duration + ")  •  ₹" + price);
        txtDetails.setText(description != null ? description : "");
        txtHighlights.setText(highlights.replace(",", "  • "));
        txtBestTime.setText("Best Time to Visit: " + bestTime);

        updateFavoriteIcon();
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean nowFav = DBClass.toggleFavorite(id);
                updateFavoriteIcon();
                Toast.makeText(PackagesDetailsActivity.this, nowFav ? "Saved to Wishlist" : "Removed from Wishlist", Toast.LENGTH_SHORT).show();
            }
        });

        webView = findViewById(R.id.webView);
        initWebViewSettings();
        loadDestinationMap(e_lat, e_lon);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1001);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Place Details");
    }

    private void updateFavoriteIcon() {
        boolean isFav = DBClass.isFavorite(id);
        imgFavorite.setImageResource(isFav ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline);
    }

    public void btnBooknowClick(View view) {
        Intent intent = new Intent(this, AddBookingsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("location", location);
        startActivity(intent);
    }

    public void btnDirectionsClick(View view) {
        String embedUrl = "https://maps.google.com/maps?saddr=" + s_lat + "," + s_lon +
                "&daddr=" + e_lat + "," + e_lon + "&output=embed";
        if (s_lat == 0.0 && s_lon == 0.0) {
            embedUrl = "https://maps.google.com/maps?q=" + e_lat + "," + e_lon + "&z=15&output=embed";
        }
        loadMapUrlInIframe(embedUrl);

        String externalUrl = "https://www.google.com/maps/dir/?api=1&destination=" + e_lat + "," + e_lon + "&travelmode=driving";
        try {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(externalUrl));
            startActivity(mapIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Showing route map below", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return false;
                }
                // Block intent://, geo:, etc. so WebView never shows ERR_UNKNOWN_URL_SCHEME
                return true;
            }
        });
    }

    private void loadMapUrlInIframe(String embedUrl) {
        String html = "<!DOCTYPE html><html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><style>body,html{margin:0;padding:0;height:100%;width:100%;}iframe{border:0;width:100%;height:100%;}</style></head><body><iframe src=\"" + embedUrl + "\" allowfullscreen></iframe></body></html>";
        webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    }

    private void loadDestinationMap(double destLat, double destLng) {
        String url = "https://maps.google.com/maps?q=" + destLat + "," + destLng + "&z=15&output=embed";
        loadMapUrlInIframe(url);
    }

    private void loadDirections(double startLat, double startLng, double endLat, double endLng) {
        String url = "https://maps.google.com/maps?saddr=" + startLat + "," + startLng +
                "&daddr=" + endLat + "," + endLng + "&output=embed";
        loadMapUrlInIframe(url);
    }

    private void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location loc) {
                            if (loc != null) {
                                s_lat = loc.getLatitude();
                                s_lon = loc.getLongitude();
                                loadDirections(s_lat, s_lon, e_lat, e_lon);
                            }
                        }
                    });
        } catch (SecurityException e) {
            Log.e("Location", "Permission error", e);
        }
    }

    @Override
    public boolean onMapLongClick(@NonNull LatLng latLng) {
        return false;
    }

    @Override
    public void onMapReady(@NonNull MapplsMap mapplsMap) {
    }

    @Override
    public void onMapError(int i, String s) {
    }
}
