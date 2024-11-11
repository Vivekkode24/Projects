package com.example.layoutpractise3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class tech extends AppCompatActivity {

    private TextView GenClubHeading;
  private Button comm, alias;
  int value, nvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);

        GenClubHeading=findViewById(R.id.genclubhead);
        comm=findViewById(R.id.comm_bttn);
        alias=findViewById(R.id.alias_btn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            value = bundle.getInt("value");
        }

        DISPLAY();

        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(tech.this, COMM.class);
                //int no=1;
               // int x=10*value+number;
                //value=COMM(no);
                nvalue=10*value+1;
                intent.putExtra("value",nvalue);
                startActivity(intent);
            }
        });

        alias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nvalue=10*value+2;
                Intent intent= new Intent(tech.this, COMM.class);
                intent.putExtra("value",nvalue);
                startActivity(intent);
            }
        });


    }

    void DISPLAY()
    {
        alias.setVisibility(View.GONE);
        switch(value)
        {
            case 1:  GenClubHeading.setText("TECHNICAL COMMITTEES");
                comm.setText("COMPUTER SOCIETY");
                alias.setVisibility(View.VISIBLE);
                alias.setText("TECHFEST");
                break;

            case 2:  GenClubHeading.setText("DANCE COMMITTEES");
                comm.setText("Cultural Society");
                break;
            case 3: GenClubHeading.setText("MUSIC COMMITTEES");
                comm.setText("CULTURAL SOCIETY");
                break;

            case 4:  GenClubHeading.setText("SPORTS COMMITTEES");
                comm.setText("GYMKHANA");
                 break;

            case 5: GenClubHeading.setText("STRATEGIC COMMITTEES");
                comm.setText("ARITHMOS");
                break;
            case 6:GenClubHeading.setText("LITERARY COMMITTEES");
                comm.setText("DLS");
                break;
        }

    }
}


//if(value==1)  GenClubHeading.setText("TECHNICAL "Committes"");
// if(value==4) GenClubHeading.setText("ART "Committes"");
         /*
        Intent i=getIntent();
        Bundle b=i.getExtras();
        if(b!=null)
        {
            value=(int) b.get("value");
        }
        if(value==1) GenClubHeading.setText("TECH "Committes"");
        else if (value==4) GenClubHeading.setText("ART "Committes"");*/

