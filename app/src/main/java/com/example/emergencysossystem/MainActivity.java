package com.example.emergencysossystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Contact1,Contact2,Contact3;
    String ph1,ph2,ph3;
    Boolean varification;
    Button LoginButton;
    ImageView loginimage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Contact1=findViewById(R.id.PhoneNO1);
        Contact2=findViewById(R.id.PhoneNO2);
        Contact3=findViewById(R.id.PhoneNO3);
       loginimage=findViewById(R.id.imageView3);

        varification=false;
        SharedPreferences sp = getSharedPreferences("EmergencyContactsStorage", Context.MODE_PRIVATE);
        if(sp.getBoolean("VARIFICATION",false))
        {
            Intent intent = new Intent(MainActivity.this, home_page.class);
            startActivity(intent);
        }
        loginimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ph1=Contact1.getText().toString().trim();
                ph2=Contact2.getText().toString().trim();
                ph3=Contact3.getText().toString().trim();

                if(ph1.isEmpty() || ph2.isEmpty() || ph3.isEmpty())
                {
                        Toast.makeText(MainActivity.this, "Empty Feild", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(ph1.length()==10 & ph2.length()==10 & ph3.length()==10)
                    {
                        varification =true;
                        SharedPreferences sp = getSharedPreferences("EmergencyContactsStorage", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("CONTACT1", ph1);
                        editor.putString("CONTACT2", ph2);
                        editor.putString("CONTACT3", ph3);
                        varification=true;
                        editor.putBoolean("VARIFICATION",varification);
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, home_page.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });


    }
}