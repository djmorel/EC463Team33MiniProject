package team33.ec463.com.ec463team33miniproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AddRoom extends AppCompatActivity {

    TextView roomName_textview = (TextView) findViewById(R.id.roomName_textview);
    final EditText roomNickname_text = (EditText) findViewById(R.id.roomNickname_text);
    final TextView errorName_textview = (TextView) findViewById(R.id.nameError_textview);

    // Create a reference to the Cancel button
    Button cancel_Button = (Button) findViewById(R.id.cancel_Button);
    private CollectionReference roomColRef = FirebaseFirestore.getInstance().collection("Rooms");
    private static final String RTAG = "Rooms";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        errorName_textview.setVisibility(View.INVISIBLE);
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
                    // Turn off name error
                    errorName_textview.setVisibility(View.INVISIBLE);

                    // Save the Room to the user's account
                    //addRoom();

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

    public void addRoom(){
        String name = roomNickname_text.getText().toString();

        Map<String, Object> newRoom = new HashMap<>();
        newRoom.put("Name", name);

        roomColRef.add(newRoom)
               .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                   @Override
                   public void onSuccess(DocumentReference documentReference) {
                       Log.d(RTAG, "Added new room");
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