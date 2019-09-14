package team33.ec463.com.ec463team33miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AddDevice extends AppCompatActivity {

    private TextView deviceID_textview;
    private EditText deviceID_text;
    private TextView invalidID_textview;
    private Button cancel_adddev_Button;
    private Button done_adddev_Button;
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

                // Make sure the Device ID field isn't empty
                if(deviceID_text.getText().toString().trim().length() != 0)
                {
                    // Check to see if the ID is valid against Firebase
                    boolean validID = false;

                    if( deviceID.equals("0123456789ab") )  // A test device ID
                    {
                        validID = true;

                        // Record the sensor in the user's account on Firebase
                        addNewDevice();
                    }

                    // Handle valid and invalid device IDs
                    if(validID)
                    {
                        // Turn off Invalid ID error visibility
                        invalidID_textview.setVisibility(View.INVISIBLE);

                        // Go to the Edit Device Activity
                        Intent editdeviceIntent = new Intent(getApplicationContext(), EditDevice.class);
                        startActivity(editdeviceIntent);
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
    }

    private void addNewDevice(){
        deviceID_text = (EditText) findViewById(R.id.deviceID_text);
        String devID = deviceID_text.getText().toString();
        Map<String, Object> newDev = new HashMap<>();
        newDev.put("Device ID", devID);
        newDev.put("Name", "");
        newDev.put("Assigned Room", "");
        newDev.put("Device Type", "");
        newDev.put("Data", 0);

        datab.collection("New Devices")
                .add(newDev)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
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