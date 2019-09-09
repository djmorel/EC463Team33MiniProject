package team33.ec463.com.ec463team33miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class RoomDetails extends AppCompatActivity {

    TextView targetRoomName;
    Button deleteRoom_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        // Connect the layout elements to variables
        targetRoomName = (TextView) findViewById(R.id.targetRoomName_textview);
        deleteRoom_button = (Button) findViewById(R.id.deleteRoom_button);

        // Get Room Name
        Bundle roomBundle = getIntent().getExtras();
        if(roomBundle != null)
        {
            targetRoomName.setText(roomBundle.getString("RoomName"));
        }
    }
}
