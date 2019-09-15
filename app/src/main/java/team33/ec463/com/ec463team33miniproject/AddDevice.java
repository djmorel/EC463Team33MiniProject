package team33.ec463.com.ec463team33miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddDevice extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private TextView deviceID_textview;
    private EditText deviceID_text;
    private TextView invalidID_textview;
    private Button cancel_adddev_Button;
    private Button done_adddev_Button;
    private EditText deviceNickname_text;
    private TextView deviceNickname_textview;
    private TextView errorDeviceName_textview;
    private TextView assignedRoom_textview;
    private Spinner assignedRoom_Spinner;
    private TextView deviceType_textview;
    private Spinner deviceType_Spinner;
    private FirebaseFirestore datab = FirebaseFirestore.getInstance();
    private static final String RTAG = "Rooms";
    private static final String DTAG = "Devices";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        deviceID_textview = (TextView) findViewById(R.id.deviceID_textview);
        deviceID_text = (EditText) findViewById(R.id.deviceID_text);
        invalidID_textview = (TextView) findViewById(R.id.invalidID_textview);
        cancel_adddev_Button = (Button) findViewById(R.id.cancel_adddev_Button);
        done_adddev_Button = (Button) findViewById(R.id.done_adddev_Button);
        deviceNickname_text = (EditText) findViewById(R.id.deviceNickname_text);
        deviceNickname_textview = (TextView) findViewById(R.id.deviceNickname_textview);
        errorDeviceName_textview = (TextView) findViewById(R.id.errorDeviceName_textview);
        assignedRoom_textview = (TextView) findViewById(R.id.assignedRoom_textview);
        assignedRoom_Spinner = (Spinner) findViewById(R.id.assignedRoom_Spinner);
        deviceType_textview = (TextView) findViewById(R.id.deviceType_textview);
        deviceType_Spinner = (Spinner) findViewById(R.id.deviceType_Spinner);


        invalidID_textview.setVisibility(View.INVISIBLE);
        cancel_adddev_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Return to the Devices Activity without adding a new device
                Intent devicesIntent = new Intent(getApplicationContext(), Devices.class);
                startActivity(devicesIntent);
            }
        });

        // Create a reference to the Done button, and make it functional
        done_adddev_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Record the Device ID text
                String deviceID = deviceID_text.getText().toString();
                String deviceName = deviceNickname_text.getText().toString();
                String deviceType = deviceType_Spinner.getSelectedItem().toString();
                String deviceRoom = assignedRoom_Spinner.getSelectedItem().toString();

                // Make sure the Device ID field isn't empty
                if(deviceID_text.getText().toString().trim().length() != 0)
                {
                    // Check to see if the ID is valid against Firebase
                    boolean validID = false;

                    if( deviceID.equals("0123456789ab") )  // A test device ID
                    {
                        validID = true;

                        // Record the sensor in the user's account on Firebase
                        addNewDevice(deviceID, deviceName, deviceType, deviceRoom);
                    }

                    // Handle valid and invalid device IDs
                    if(validID)
                    {
                        // Turn off Invalid ID error visibility
                        invalidID_textview.setVisibility(View.INVISIBLE);

                        // Go to the Edit Device Activity
                        Intent devicesIntent = new Intent(getApplicationContext(), Devices.class);
                        startActivity(devicesIntent);
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
            }
        });

        String[] sampleRooms = new String[] {
                "Select a room",
                "Kitchen",
                "Bedroom",
                "Garage"
        };

        String[] deviceTypes = new String[] {
                "Select type",
                "Thermometer",
                "Hygrometer"
        };

        final List<String> roomList = new ArrayList<>(Arrays.asList(sampleRooms));  // Switch sampleRooms with database
        final List<String> typeList = new ArrayList<>(Arrays.asList(deviceTypes));

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
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Ignore
    }

    private void addNewDevice(String devID, String devName, String devType, String devRoom){
        /*deviceID_text = (EditText) findViewById(R.id.deviceID_text);
        String devID = deviceID_text.getText().toString();*/
        Device newDevice = new Device(devName, devType, devID, devRoom);
        if(devType.equals("Thermometer")) {
            try {
                newDevice.generateTempData();
            } catch (InterruptedException e) {
                System.out.println("Could not generate temperature data");
            }
        } else if(devType.equals("Hygrometer")){
            try {
                newDevice.generateHumidData();
            } catch(InterruptedException e){
                System.out.println("Could not generate humidity data");
            }
        }

        Map<String, Object> newDev = new HashMap<>();
        newDev.put("Device ID", devID);
        newDev.put("Name", devName);
        newDev.put("Assigned Room", devRoom);
        newDev.put("Device Type", devType);
        newDev.put("Data", newDevice.data);

        datab.collection("Rooms").document(devRoom).collection("Devices").document(devName)
                .set(newDev)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(DTAG, "Created new device");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(DTAG, "Could not create new device");
                    }
                });
    }
}