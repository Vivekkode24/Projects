package com.example.layoutpractise3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SocialHandle extends AppCompatActivity {

    int Value;
    private ImageButton instagram;
    private ImageButton facebook;
    private ImageButton twitter;
    private ImageButton website;
    String insta="https://instagram.com/ ",
            fb="https://www.facebook.com/ ",
            twit="https://twitter.com/ ",
            web="http://www.nmcollege.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_handle);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Value = bundle.getInt("value");
        }


        instagram=(ImageButton)findViewById(R.id.instagram);
        facebook=(ImageButton)findViewById(R.id.facebook);
        twitter=(ImageButton)findViewById(R.id.x);
        website=(ImageButton)findViewById(R.id.web);

        switch(Value)
        {
            case 11:   
                        insta="https://instagram.com/nmcomputersociety/?hl=en";
                        fb="https://www.facebook.com";
                twit="https://www.twitter.com";
                web="http://www.nmcollege.com/";
                break;
            case 12:  
                insta="https://instagram.com/nm_techfest/?hl=en";
                fb="https://www.facebook.com";
                twit="https://www.twitter.com";
                web="https://nmcollege.in";
                break;
            case 21: 
                insta="https://www.instagram.com/nmculturalsociety/?hl=en";
                fb="https://www.facebook.com";
                twit="https://www.twitter.com";
                web="https://nmcollege.in";//YOUTUBE CHANNEl
                break;
            case 31: 
                insta="https://instagram.com/nmculturalsociety/?hl=en";
                fb="https://www.facebook.com/";
                twit="https://www.twitter.com";
                web="https://nmcollege.in";
                break;
            case 41: 
                insta="https://instagram.com/nm_gymkhana/?hl=en";
                fb="https://www.facebook.com/";
                twit="https://www.twitter.com";
                web="https://nmcollege.in";
                break;
            case 51:
                insta="https://instagram.com/nmarithmos/?hl=en";
                fb="https://www.facebook.com/";
                twit="https://www.twitter.com";
                web="https://nmcollege.in";
                break;
            case 61: 
                insta="https://instagram.com/dls.nmcce/?hl=en";
                fb="https://www.facebook.com";
                twit="https://www.twitter.com";
                web="https://nmcollege.in";
                break;
        }

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(insta));
                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(fb));
                startActivity(intent);
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(web));
                startActivity(intent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(twit));
                startActivity(intent);
            }
        });

    }
}
