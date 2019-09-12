package team33.ec463.com.ec463team33miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddDevice extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    final Device newDev = new Device();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        final EditText deviceNickname_text = (EditText) findViewById(R.id.deviceNickname_text);
        TextView deviceNickname_textview = (TextView) findViewById(R.id.deviceNickname_textview);
        final TextView errorDeviceName_textview = (TextView) findViewById(R.id.errorDeviceName_textview);
        TextView assignedRoom_textview = (TextView) findViewById(R.id.assignedRoom_textview);
        final Spinner assignedRoom_Spinner = (Spinner) findViewById(R.id.assignedRoom_Spinner);
        TextView deviceType_textview = (TextView) findViewById(R.id.deviceType_textview);
        final Spinner deviceType_Spinner = (Spinner) findViewById(R.id.deviceType_Spinner);

        // Create a reference to the deviceID_textview
        TextView deviceID_textview = (TextView) findViewById(R.id.deviceID_textview);

        // Create a reference to the deviceID_text field
        final EditText deviceID_text = (EditText) findViewById(R.id.deviceID_text);

        // Create a reference to the Invalid ID error TextView
        final TextView invalidID_textview = (TextView) findViewById(R.id.invalidID_textview);
        invalidID_textview.setVisibility(View.INVISIBLE);

        // Create an reference to the Cancel button, and make it functional
        Button cancel_adddev_Button = (Button) findViewById(R.id.cancel_adddev_Button);
        cancel_adddev_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Return to the Devices Activity without adding a new device
                Intent devicesIntent = new Intent(getApplicationContext(), Devices.class);
                startActivity(devicesIntent);
            }
        });

        // Create a reference to the Done button, and make it functional
        Button done_adddev_Button = (Button) findViewById(R.id.done_adddev_Button);
        done_adddev_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Record the Device ID text
                String deviceID = deviceID_text.getText().toString();

                // Make sure the Device ID field isn't empty
                if(deviceID_text.getText().toString().trim().length() != 0)
                {
                    // Check to see if the ID is valid against Firebase
                    boolean validID = false;

                    if( deviceID.equals("0123456789ab") )  // A test device ID
                    {
                        validID = true;

                        // Record the sensor in the user's account on Firebase
                        newDev.setIdVal(deviceID);
                    }

                    // Handle valid and invalid device IDs
                    if(validID)
                    {
                        // Turn off Invalid ID error visibility
                        invalidID_textview.setVisibility(View.INVISIBLE);

                        // Go to the Edit Device Activity
                        //Intent editdeviceIntent = new Intent(getApplicationContext(), EditDevice.class);
                        //startActivity(editdeviceIntent);
                    }
                    else
                    {
                        // Display an invalid ID message
                        invalidID_textview.setVisibility(View.VISIBLE);
                    }

                }
                else  // User didn't enter an ID so display invalid ID message
                {
                    invalidID_textview.setVisibility(View.VISIBLE);
                }

                // Record the Device Nickname text
                String nickname = deviceNickname_text.getText().toString();

                // Make sure the room name isn't empty
                if(deviceNickname_text.getText().toString().trim().length() != 0)
                {
                    // Turn off name error
                    errorDeviceName_textview.setVisibility(View.INVISIBLE);

                    //set device name
                    newDev.setName(nickname);
                }
                else
                {
                    errorDeviceName_textview.setVisibility(View.VISIBLE);
                }

            }
        });
        // Pull the list of devices from the user's account
        // TODO
        // For now, default to a simple list
        String[] sampleRooms = new String[] {
                "Select a room",
                "Kitchen",
                "Bedroom",
                "Garage"
        };

        String[] deviceTypes = new String[]{
                "Thermometer",
                "Hygrometer"
        };

        final List<String> roomList = new ArrayList<>(Arrays.asList(sampleRooms));  // Switch sampleRooms with database
        final List<String> typeList = new ArrayList<>(Arrays.asList(deviceTypes));  // Switch sampleRooms with database
        ArrayAdapter<String> roomAdapter =  new ArrayAdapter<String>(AddDevice.this, android.R.layout.simple_spinner_item,
                roomList){
            // Disables hint
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable first item (hint) from spinner)
                    return false;
                } else
                {
                    return true;
                }
            }

            // Handles option color
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0)
                {
                    // Set hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else
                {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedRoom_Spinner.setAdapter(roomAdapter);
        assignedRoom_Spinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> typeAdapter =  new ArrayAdapter<String>(AddDevice.this, android.R.layout.simple_spinner_item,
                typeList){
            // Disables hint
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable first item (hint) from spinner)
                    return false;
                } else
                {
                    return true;
                }
            }

            // Handles option color
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0)
                {
                    // Set hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else
                {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deviceType_Spinner.setAdapter(typeAdapter);
        deviceType_Spinner.setOnItemSelectedListener(this);
        Rooms.AppDB.addNewDevice(newDev);
    }

    // Override methods to support the Adapter's OnItemSelectedListener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if(parent.getId() == R.id.deviceType_Spinner)
            newDev.setType(text);
        else if(parent.getId() == R.id.assignedRoom_Spinner)
            newDev.setRoom(text);
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Ignore
    }

}