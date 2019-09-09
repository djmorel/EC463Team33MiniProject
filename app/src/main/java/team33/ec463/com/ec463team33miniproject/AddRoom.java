package team33.ec463.com.ec463team33miniproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddRoom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        // Create a reference to the roomNickname_text field
        final EditText roomNickname_text = (EditText) findViewById(R.id.roomNickname_text);

        // Create a reference to the Cancel button
        Button cancel_Button = (Button) findViewById(R.id.cancel_Button);
        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Return to the Rooms Activity without adding a new room
                Intent roomsIntent = new Intent(getApplicationContext(), Rooms.class);
                startActivity(roomsIntent);
            }
        });

        // Create a reference to the Done button
        Button done_Button = (Button) findViewById(R.id.done_Button);
        done_Button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Record the Room Nickname text
                String nickname = roomNickname_text.getText().toString();

                // Make sure the room name isn't empty
                if(roomNickname_text.getText().toString().trim().length() != 0)
                {
                    // Return to the Rooms Activity while passing the text for a new room
                    Intent roomsIntent = new Intent(getApplicationContext(), Rooms.class);
                    roomsIntent.putExtra("roomNickname", nickname);
                    startActivity(roomsIntent);
                }
            }


        }));
    }
}
