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

import java.util.HashMap;
import java.util.Map;

public class AddRoom extends AppCompatActivity {

    private TextView roomName_textview;
    private EditText roomNickname_text;
    private TextView errorName_textview;
    private Button cancel_Button;
    private Button done_Button;
    private FirebaseFirestore datab = FirebaseFirestore.getInstance();
    private static final String RTAG = "Rooms";
    private static final String DTAG = "Devices";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        roomName_textview = (TextView) findViewById(R.id.roomName_textview);
        roomNickname_text = (EditText) findViewById(R.id.roomNickname_text);
        errorName_textview = (TextView) findViewById(R.id.nameError_textview);
        cancel_Button = (Button) findViewById(R.id.cancel_Button);
        done_Button = (Button) findViewById(R.id.done_Button);

        errorName_textview.setVisibility(View.INVISIBLE);
        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Return to the Rooms Activity without adding a new room
                Intent roomsIntent = new Intent(getApplicationContext(), Rooms.class);
                startActivity(roomsIntent);
            }
        });

        done_Button.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Record the Room Nickname text
                String nickname = roomNickname_text.getText().toString();

                // Make sure the room name isn't empty
                if(roomNickname_text.getText().toString().trim().length() != 0)
                {
                    // Turn off name error
                    errorName_textview.setVisibility(View.INVISIBLE);

                    // Save the Room to the user's account
                    addNewRoom();

                    // Return to the Rooms Activity while passing the text for a new room
                    Intent roomsIntent = new Intent(getApplicationContext(), Rooms.class);
                    roomsIntent.putExtra("roomNickname", nickname);  // Remove this after TODO
                    startActivity(roomsIntent);
                }
                else
                {
                    errorName_textview.setVisibility(View.VISIBLE);
                }
            }


        }));
    }

    private void addNewRoom(){
        roomNickname_text = (EditText) findViewById(R.id.roomNickname_text);
        String roomName = roomNickname_text.getText().toString();
        Map<String, Object> newRoom = new HashMap<>();
        newRoom.put("Name", roomName);

        datab.collection("rooms")
                .add(newRoom)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(RTAG,"Added new room");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(RTAG, "Could not add new room", e);
                    }
                });
    }
}