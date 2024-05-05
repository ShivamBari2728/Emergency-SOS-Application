package com.example.emergencysossystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class response extends AppCompatActivity {
    TextView yesclick;
    String startdestination,currentlocat;
    boolean clickedornot;
    Handler handler;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        yesclick=findViewById(R.id.textView14);
        //audio player
        mediaPlayer = MediaPlayer.create(this, R.raw.alert);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        //
         handler = new Handler(Looper.getMainLooper());
        clickedornot=false;

        SharedPreferences sp2 = getSharedPreferences("LocationStorage", Context.MODE_PRIVATE);
        startdestination=" destined to go from "+sp2.getString("SourceLocation","")+" to "+sp2.getString("DestinationLocation","")+".";
        currentlocat="Current location is "+sp2.getString("Current address","")+".";
        yesclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                finish();
            }
        });
        checkStatus();


    }


    private void checkStatus() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!clickedornot)
                {
                    clickedornot=true;
                }
                else {
                    sendsos();
                    //finish();
                }

                handler.postDelayed(this, 30000); // 30 seconds
            }
        }, 0); // Start immediately
    }

    private void sendsos() {

        SharedPreferences sp = getSharedPreferences("EmergencyContactsStorage", Context.MODE_PRIVATE);
        String Contact1 = sp.getString("CONTACT1", "eMPTY");
        String Contact2 = sp.getString("CONTACT2", "eMPTY");
        String Contact3 = sp.getString("CONTACT3", "eMPTY");
        Toast.makeText(this, "hiii", Toast.LENGTH_SHORT).show();
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(Contact1, null,"I need urgent help contact me, my location is :- ", null, null);
        sms.sendTextMessage(Contact1, null, currentlocat, null, null);
        sms.sendTextMessage(Contact1, null, startdestination, null, null);


        sms.sendTextMessage(Contact2, null,"I need urgent help contact me, my location is :- ", null, null);
        sms.sendTextMessage(Contact2, null, currentlocat, null, null);
        sms.sendTextMessage(Contact2, null, startdestination, null, null);
        sms.sendTextMessage(Contact3, null,"I need urgent help contact me, my location is :- ", null, null);
        sms.sendTextMessage(Contact3, null, currentlocat, null, null);
        sms.sendTextMessage(Contact3, null, startdestination, null, null);
        Toast.makeText(getApplicationContext(), "SOS Sent successfully!",
                Toast.LENGTH_LONG).show();
        onDestroy();
        finish();


    }
    @Override
    protected void onPause() {
        super.onPause();
        // Pause the audio if the activity is paused
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove any pending callbacks from the handler
        handler.removeCallbacksAndMessages(null);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


}