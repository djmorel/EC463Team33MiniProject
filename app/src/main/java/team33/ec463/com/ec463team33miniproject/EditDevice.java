package team33.ec463.com.ec463team33miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EditDevice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device);

        // Create references for all of the layout elements
        Button removeDevice_Button = (Button) findViewById(R.id.removeDevice_Button);
        Button doneDevice_Button = (Button) findViewById(R.id.doneDevice_Button);
        final EditText deviceNickname_text = (EditText) findViewById(R.id.deviceNickname_text);
        TextView deviceNickname_textview = (TextView) findViewById(R.id.deviceNickname_textview);
        final TextView errorDeviceName_textview = (TextView) findViewById(R.id.errorDeviceName_textview);
        TextView deviceType_textview = (TextView) findViewById(R.id.deviceType_textview);
        TextView deviceSensorType_textview = (TextView) findViewById(R.id.deviceSensorType_textview);
        TextView deviceIDLabel_textview = (TextView) findViewById(R.id.deviceIDLabel_textview);
        TextView devicePrintedID_textview = (TextView) findViewById(R.id.devicePrintedID_textview);
        TextView assignedRoom_textview = (TextView) findViewById(R.id.assignedRoom_textview);
        Spinner assignedRoom_Spinner = (Spinner) findViewById(R.id.assignedRoom_Spinner);

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



    }
}
