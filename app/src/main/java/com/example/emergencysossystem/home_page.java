package com.example.emergencysossystem;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
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
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class home_page extends AppCompatActivity implements LocationListener {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 0;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    LocationManager locationManager;
    TextView textView;
    String CurrentLocation;
    ImageView QuickAlert, StartRide, Support, Help, RefreshLocation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        getGeoLocation();


        textView = findViewById(R.id.textView3);
        QuickAlert = findViewById(R.id.imageView12);
        StartRide = findViewById(R.id.imageView10);
        Support = findViewById(R.id.imageView9);
        Help = findViewById((R.id.imageView8));
        RefreshLocation = findViewById(R.id.refrshicon);
        textView.setText("Fetching Location......");

        //-----------------------------------Fetching SMS Permission Check ->>>
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.SEND_SMS)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQUEST_CODE);
            }
        }
        //-----------------------------------Fetching SMS Permission Check ->>>


        //-----------------------------------Fetching Location Permission Check ->>>
//        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
//        {
//            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION))
//            {}
//            else{
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);}
//        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

            getGeoLocation();


        }

        //-----------------------------------Fetching Location Permission Check ->>>


        //-----------------------------------On Click Listeners->>>
        QuickAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTextMessage();
            }
        });

        StartRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, RideActivity.class);
                startActivity(intent);
            }
        });
        Support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://t.me/ShivamTheSkywalker"));
                startActivity(intent);
            }
        });
        Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home_page.this, Help_page.class);
                startActivity(intent);
            }
        });
        RefreshLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getGeoLocation();
                Toast.makeText(home_page.this, "Refreshing Location.", Toast.LENGTH_SHORT).show();


            }
        });
        //-----------------------------------On Click Listeners->>>
    }  //End of OnCreate Method.

    private void sendTextMessage() {
        SharedPreferences sp = getSharedPreferences("EmergencyContactsStorage", Context.MODE_PRIVATE);
        String Contact1 = sp.getString("CONTACT1", "eMPTY");
        String Contact2 = sp.getString("CONTACT2", "eMPTY");
        String Contact3 = sp.getString("CONTACT3", "eMPTY");
        SmsManager sms = SmsManager.getDefault();
        if(CurrentLocation != null)
        {
        sms.sendTextMessage(Contact1, null,"URGENT! I needs immediate Help. My location:-", null, null);
            sms.sendTextMessage(Contact1, null,CurrentLocation, null, null);
        sms.sendTextMessage(Contact2, null, "URGENT! I needs immediate Help. My location:-", null, null);
            sms.sendTextMessage(Contact2, null,CurrentLocation, null, null);
        sms.sendTextMessage(Contact3, null, "URGENT! I needs immediate Help. My location:-", null, null);
            sms.sendTextMessage(Contact3, null,CurrentLocation, null, null);
        Toast.makeText(getApplicationContext(), "SOS sent with location.",
                Toast.LENGTH_LONG).show();}
        else {
            sms.sendTextMessage(Contact1, null, "URGENT! I needs immediate Help. Please contact them or dispatch help to their location as soon as possible.\n Unable to fetch location.", null, null);
            sms.sendTextMessage(Contact2, null, "URGENT! I needs immediate Help. Please contact them or dispatch help to their location as soon as possible.\n Unable to fetch location.", null, null);
            sms.sendTextMessage(Contact3, null, "URGENT! I needs immediate Help. Please contact them or dispatch help to their location as soon as possible.\n Unable to fetch location.", null, null);
            Toast.makeText(getApplicationContext(), "SOS sent without location.",
                    Toast.LENGTH_LONG).show();}

        }



    //Method to request permission...
    public void onRequestPermissionResult(int requestCode, String permission[], int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "no permission", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            // Check if permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can proceed with location-related tasks
                // For example, start fetching location updates
            } else {
                // Permission denied, inform the user or handle it gracefully
            }
        }
    }
    //-----------------------------------Fetching Location ->>>
    public void getGeoLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
//           locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50000, 0, this);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    @Override
    public void onLocationChanged(Location location) {
        try {
            Geocoder geocoder = new Geocoder(home_page.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);
            String Longlett= location.getLongitude()+" " + location.getLatitude();
            textView.setText(address +" /n "+ Longlett);
            CurrentLocation=address;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //-----------------------------------Fetching Location <<<-
}