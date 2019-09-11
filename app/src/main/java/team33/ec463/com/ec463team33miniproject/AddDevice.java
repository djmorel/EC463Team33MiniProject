package team33.ec463.com.ec463team33miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AddDevice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

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
}
