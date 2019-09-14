package team33.ec463.com.ec463team33miniproject;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Collection;

public class RoomDetails extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Instantiate the layout types
    LineGraphSeries<DataPoint> series;
    TextView targetRoomName;
    Button deleteRoom_button;
    final TextView roomNickname_text = (TextView) findViewById(R.id.targetRoomName_textview);
    private CollectionReference roomColRef = FirebaseFirestore.getInstance().collection("Rooms");
    private static final String RTAG = "Rooms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Data values for the plot
        double x = 1.0;
        double y;
        double[] temp = new double[]{349, 344, 339, 334, 331, 327, 325, 323, 321, 338, 340, 341, 346, 342, 344};

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for(int i = 0; i < 15; i++)
        {
            x++;
            y = temp[i] / 10.0;
            series.appendData(new DataPoint(x,y), true, 100);
        }
        graph.addSeries(series);

        // Custom label formatter to show am/pm and F
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // Show normal x values
                    return super.formatLabel(value, isValueX) + ":00 am";
                } else {
                    // Show F for y values
                    return super.formatLabel(value, isValueX) + " F";
                }
            }
        });

        // Connect the layout elements to variables
        targetRoomName = (TextView) findViewById(R.id.targetRoomName_textview);
        deleteRoom_button = (Button) findViewById(R.id.deleteRoom_Button);

        // Get Room Name
        Bundle roomBundle = getIntent().getExtras();
        if(roomBundle != null)
        {
            targetRoomName.setText(roomBundle.getString("RoomName"));
        }

        // Implement the deleteRoom_button
        deleteRoom_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String room_delete = targetRoomName.getText().toString();

                // Search the user's list of rooms for the specified room

                // Remove the current room from the list
                //deleteRoom();

                // Return to the Rooms page
                Intent roomsIntent = new Intent(getApplicationContext(), Rooms.class);
                startActivity(roomsIntent);
            }
        });
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
        getMenuInflater().inflate(R.menu.room_details, menu);
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

    public void deleteRoom() {
        String name = roomNickname_text.getText().toString();
        //delete room from specified rooms collection
        final DocumentReference deviceRef = roomColRef.document(name);
        deviceRef
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(RTAG, "Device was deleted");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(RTAG, "Device was not deleted");
                    }
                });
    }
}