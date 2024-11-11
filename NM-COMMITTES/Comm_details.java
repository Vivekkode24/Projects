package com.example.layoutpractise3;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

public class Comm_details extends AppCompatActivity {

    int Value;

    private TextView DetailsTitle;
    private Button socialhandle;
    private Button About;
    private Button members;
    private Button photobutton;
    private Button events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Value = bundle.getInt("value");
        }

        DetailsTitle=findViewById(R.id.details_title);
        socialhandle=(Button)findViewById(R.id.social_handle_button);
        About=(Button)findViewById(R.id.aboutuscomm);
        members=(Button)findViewById(R.id.members);
        events=(Button)findViewById(R.id.events);
        photobutton=findViewById(R.id.photobutton);

        switch(Value)
        {
            case 11: DetailsTitle.setText("COMPUTER SOCIETY DETAILS");
                break;
            case 12: DetailsTitle.setText("TECHFEST: DETAILS");
                break;
            case 21: DetailsTitle.setText("CULTURAL SOCIETY: DETAILS");
                break;
            case 31: DetailsTitle.setText("CULTURAL SOCIETY: DETAILS");
                break;
            case 41: DetailsTitle.setText("GYMKHANA: DETAILS");
                 break;
            case 51: DetailsTitle.setText("ARITHMOS: DETAILS");
                break;
            case 61: DetailsTitle.setText("DLS: DETAILS");
                break;
        }

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ia=new Intent(Comm_details.this, Aboutcomm.class);
                ia.putExtra("value",Value);
                startActivity(ia);
            }
        });
        socialhandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ish =new Intent(Comm_details.this, SocialHandle.class);
                ish.putExtra("value",Value);
                startActivity(ish);
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ie=new Intent(Comm_details.this, CommEvents.class);
                ie.putExtra("value",Value);
                startActivity(ie);
            }
        });

        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent im= new Intent(Comm_details.this, Comm_members.class);
                im.putExtra("value",Value);
                startActivity(im);
            }
        });

        photobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ip= new Intent(Comm_details.this, CommPhoto.class);
                ip.putExtra("value",Value);
                startActivity(ip);
            }
        });


    }
}