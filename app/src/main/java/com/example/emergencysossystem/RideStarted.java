package com.example.emergencysossystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.adservices.adselection.AddAdSelectionOverrideRequest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import android.os.Handler;
import android.os.Looper;


public class RideStarted extends AppCompatActivity implements LocationListener {

    TextView Locationview;
    ImageView Cancel , support,help;
    String Lattlong;
    String currentaddress , priviousaddress;

    LocationManager locationManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_started);
        Locationview = findViewById(R.id.textView11);
        support=findViewById(R.id.imageView9);
        help=findViewById(R.id.imageView8);
        Cancel= findViewById(R.id.cancelbut);
        if(ContextCompat.checkSelfPermission(RideStarted.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(RideStarted.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://t.me/ShivamTheSkywalker"));
                startActivity(intent);
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RideStarted.this, Help_page.class);
                startActivity(intent);
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                finish(); //end the page
            }
        });
        currentaddress = "";
        priviousaddress = "";
        startLocationUpdates();

    }
    private void startLocationUpdates() {
        // Fetch location every 30 seconds
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Fetch current location
//                Toast.makeText(RideStarted.this, "Run function running", Toast.LENGTH_SHORT).show();
                GetGeoLocation();
                comparelocation();
                handler.postDelayed(this, 900000); // 15 minutes
            }
        }, 0); // Start immediately

    }

    private void comparelocation() {
                currentaddress=Lattlong;
                if(currentaddress != priviousaddress)
                {
                    priviousaddress = currentaddress;
                }
                else
                {
                    SharedPreferences sp2 = getSharedPreferences("LocationStorage", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp2.edit();
                    editor.putString("Current address", currentaddress);
                    Intent intent = new Intent(RideStarted.this, response.class);
                    startActivity(intent);

                }


    }

    @SuppressLint("MissingPermission")
    private void GetGeoLocation()
    {
        Toast.makeText(this, "Fetching location", Toast.LENGTH_SHORT).show();
        try{
        locationManager=(LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,0,RideStarted.this);
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, RideStarted.this, null);

        }
        catch(Exception e){
            e.printStackTrace();
    }}
    @Override
    public void onLocationChanged(Location location) {
        //
        // Toast.makeText(this, "" + location.getLatitude() + "" + location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(RideStarted.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
            Locationview.setText(address);
            Lattlong=(address);

        } catch (Exception e) {

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop location updates
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

}