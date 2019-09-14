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
import java.util.List;

public class EditDevice extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button removeDevice_Button;
    private Button doneDevice_Button;
    private EditText deviceNickname_text;
    private TextView deviceNickname_textview;
    private TextView errorDeviceName_textview;
    private TextView deviceType_textview;
    private TextView deviceSensorType_textview;
    private TextView deviceIDLabel_textview;
    private TextView devicePrintedID_textview;
    private TextView assignedRoom_textview;
    private Spinner assignedRoom_Spinner;
    private FirebaseFirestore datab = FirebaseFirestore.getInstance();
    private static final String RTAG = "Rooms";
    private static final String DTAG = "Devices";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device);

        removeDevice_Button = (Button) findViewById(R.id.removeDevice_Button);
        doneDevice_Button = (Button) findViewById(R.id.doneDevice_Button);
        deviceNickname_text = (EditText) findViewById(R.id.deviceNickname_text);
        deviceNickname_textview = (TextView) findViewById(R.id.deviceNickname_textview);
        errorDeviceName_textview = (TextView) findViewById(R.id.errorDeviceName_textview);
        deviceType_textview = (TextView) findViewById(R.id.deviceType_textview);
        deviceSensorType_textview = (TextView) findViewById(R.id.deviceSensorType_textview);
        deviceIDLabel_textview = (TextView) findViewById(R.id.deviceIDLabel_textview);
        devicePrintedID_textview = (TextView) findViewById(R.id.devicePrintedID_textview);
        assignedRoom_textview = (TextView) findViewById(R.id.assignedRoom_textview);
        assignedRoom_Spinner = (Spinner) findViewById(R.id.assignedRoom_Spinner);

        // Set errorDeviceName_textview to invisible by default
        errorDeviceName_textview.setVisibility(View.INVISIBLE);

        // Pull name of the device from the user's account
        // TODO

        // Make the Remove Device functional
        removeDevice_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO
                // Search the user's account for the device, then remove it
                String deviceRoom = assignedRoom_Spinner.getOnItemSelectedListener().toString();
                String deviceName = deviceNickname_text.getText().toString();
                deleteDevice(deviceRoom, deviceName);

                // Return to the Devices Activity
                Intent devicesIntent = new Intent(getApplicationContext(), Devices.class);
                startActivity(devicesIntent);
            }
        });

        // Make the Done Button functional
        doneDevice_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(deviceNickname_text.getText().toString().trim().length() != 0)
                {
                    // Remove the name error message (if even present)
                    errorDeviceName_textview.setVisibility(View.INVISIBLE);

                    // TODO
                    // Save the settings to the user's account
                    editDevice();

                    // Return to the Devices Activity
                    Intent devicesIntent = new Intent(getApplicationContext(), Devices.class);
                    startActivity(devicesIntent);
                }
                else  // Display name error message
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


        final List<String> roomList = new ArrayList<>(Arrays.asList(sampleRooms));  // Switch sampleRooms with database

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(EditDevice.this, android.R.layout.simple_spinner_item,
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
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        assignedRoom_Spinner.setAdapter(adapter);
        assignedRoom_Spinner.setOnItemSelectedListener(this);

    }

    // Override methods to support the Adapter's OnItemSelectedListener
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Ignore
    }

    private void editDevice(){
        deviceNickname_text = (EditText) findViewById(R.id.deviceNickname_text);
        assignedRoom_Spinner = (Spinner) findViewById(R.id.assignedRoom_Spinner);
        devicePrintedID_textview = (TextView) findViewById(R.id.devicePrintedID_textview);
        String devName = deviceNickname_text.getText().toString();
        String devRoom = assignedRoom_Spinner.getOnItemSelectedListener().toString();
        String devID = devicePrintedID_textview.getText().toString();

        DocumentReference device = datab.collection("New Devices").document(devID);
        device.update("Name", devName);
        device.update("Assigned Room", devRoom)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(DTAG, "Device successfully updated");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(DTAG, "Could not update device", e);
            }
        });

        datab.collection("rooms").document(devRoom).collection("Devices")
                .add(device)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(DTAG, "Device successfully added to assigned room");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(DTAG, "Could not add device to assigned room", e);
                    }
                });
    }

    private void deleteDevice(String devRoom, String devName){
        /*deviceNickname_text = (EditText) findViewById(R.id.deviceNickname_text);
        assignedRoom_Spinner = (Spinner) findViewById(R.id.assignedRoom_Spinner);
        String devRoom = assignedRoom_Spinner.getOnItemSelectedListener().toString();
        String devName = deviceNickname_text.getText().toString();*/

        DocumentReference device = datab.collection("rooms").document(devRoom).collection("Devices").document(devName);
        device
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(DTAG, "Deleted device");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(DTAG, "Could not delete device", e);
                    }
                });
    }
}
