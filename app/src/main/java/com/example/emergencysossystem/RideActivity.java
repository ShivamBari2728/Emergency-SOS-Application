package com.example.emergencysossystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RideActivity extends AppCompatActivity {
    ImageView StartRide , support ,help;
    public EditText Source,Destination;
//    String Startlocation,DestinationLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
        StartRide=findViewById(R.id.Srartridelogo);
        Source=findViewById(R.id.locationfrom);
        support=findViewById(R.id.imageView9);
        help=findViewById(R.id.imageView8);
        Destination=findViewById(R.id.locationto);

//        SharedPreferences sp2 = getSharedPreferences("LocationStorage", Context.MODE_PRIVATE);
//
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
                Intent intent = new Intent(RideActivity.this, Help_page.class);
                startActivity(intent);
            }
        });
        StartRide.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                 String StartLocation = Source.getText().toString().trim();
                 String DestinationLocation=Source.getText().toString().trim();
                if(StartLocation.isEmpty() || DestinationLocation.isEmpty())
                {
                    Toast.makeText(RideActivity.this, "Empty fiels.", Toast.LENGTH_SHORT).show();
                }
                else{
                    SharedPreferences sp2 = getSharedPreferences("LocationStorage", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp2.edit();
                    editor.putString("SourceLocation", StartLocation);
                    editor.putString("DestinationLocation", DestinationLocation);
                    Intent intent = new Intent(RideActivity.this, RideStarted.class);
                    startActivity(intent);
                }

            }
        });

    }
}