package team33.ec463.com.ec463team33miniproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Rooms extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Assign roomNames to whatever is in FireBase
    // For now, default it to an empty list
    // TODO
    ArrayList<String> roomNames = new ArrayList<>();  // As of now, this resets list when the page opens...

    ArrayAdapter<String> adapter;
    ListView roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //~~~~~~~~~~ Beginning of Navigation Drawer default setup ~~~~~~~~~~//
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_plus_sign);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to Add Room Activity
                Intent addroomIntent = new Intent(getApplicationContext(), AddRoom.class);
                startActivity(addroomIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //~~~~~~~~~~ End of Navigation Drawer default setup ~~~~~~~~~~//


        //~~~~~~~~~~ Beginning of Adjustable ListView setup ~~~~~~~~~~//

        // ListView setup
        roomList = (ListView) findViewById(R.id.roomList);

        // Setup the Array Adapter
        adapter = new ArrayAdapter<String>(Rooms.this, android.R.layout.simple_list_item_1,
                roomNames);

        // Add Room Name to the ListView
        String nickname = getIntent().getStringExtra("roomNickname");
        if(nickname != null)
        {
            roomNames.add(nickname);
            adapter.notifyDataSetChanged();
        }

        // Make the list view interactive
        roomList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent roomDetailsIntent = new Intent(Rooms.this, RoomDetails.class);
                roomDetailsIntent.putExtra("RoomName", roomList.getItemAtPosition(position).toString());
                startActivity(roomDetailsIntent);
            }
        });
        roomList.setAdapter(adapter);

        //~~~~~~~~~~ End of Adjustable ListView setup ~~~~~~~~~~//

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rooms, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rooms)
        {
            // Go to the Rooms page
            Intent roomsIntent = new Intent(getApplicationContext(), Rooms.class);
            startActivity(roomsIntent);
        }
        else if (id == R.id.nav_devices)
        {
            // Go to the Devices page
            Intent devicesIntent = new Intent(getApplicationContext(), Devices.class);
            startActivity(devicesIntent);
        }
        else if (id == R.id.nav_signout)
        {
            // Go to Login page
            Intent signoutIntent = new Intent(getApplicationContext(), Login.class);
            startActivity(signoutIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}