package com.example.layoutpractise3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Events extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    RecyclerView recyclerView;
    String s1[],s2[];
    int Value=1121;
    int image[]={};
    int commEventImage[]={
            R.drawable.event1, R.drawable.event2, R.drawable.event3,R.drawable.event4,
            R.drawable.event5, R.drawable.event6, R.drawable.event7, R.drawable.event8,
            R.drawable.event9,R.drawable.event10
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        mDrawerLayout=findViewById(R.id.drawer);
        mToggle= new ActionBarDrawerToggle(Events.this, mDrawerLayout,R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.navigview);
        navigationView.setNavigationItemSelectedListener(this);




        s1=getResources().getStringArray(R.array.Comm_EVENT_TITLE);
        s2=getResources().getStringArray(R.array.COMM_Event_Day);
        recyclerView=findViewById(R.id.evt_recycleview);
       image=commEventImage;
        Comm_UpEvent_Adapter UpEventAdapter = new Comm_UpEvent_Adapter(this, s1, s2, image, Value);
        recyclerView.setAdapter(UpEventAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mToggle.onOptionsItemSelected(item))
        {
            return  true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id)
        {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new society()).commit();

                Toast.makeText(this, "Welcome To Committees' Page", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:  Toast.makeText(this, "Events Page", Toast.LENGTH_SHORT).show();
                Intent i =new Intent(Events.this, Events.class);
                startActivity(i);
                // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new events()).commit();
                break;

            case  R.id.logout: Toast.makeText(this, "About Us!!", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new aboutus()).commit();
                break;

            case R.id.vote:
                // Replace the URL with your desired link
                String voteUrl = "https://94b8-103-115-21-50.ngrok-free.app";

                // Create an intent to open the link
                Intent intentVote = new Intent(Intent.ACTION_VIEW, Uri.parse(voteUrl));
                startActivity(intentVote);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return false;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
