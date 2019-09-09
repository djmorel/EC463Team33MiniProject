package team33.ec463.com.ec463team33miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static final String TAG = "Rooms";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /////////////////////////////////////////////////////////
    /////////////DATABASE CODE///////////////////////////////
    /////////////////////////////////////////////////////////
    FirebaseFirestore datab = FirebaseFirestore.getInstance();
    public void addNewRoom(String name) {
        //create new room with a name
        Map<String, Object> room = new HashMap<>();
        room.put("Name", name);

        // Add new room to collection with a new ID
        datab.collection("rooms")
                .add(room)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Added new room with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Could not add new room", e);
                    }
                });
    }

    public void addNewDevice(String name, String type, int idVal, String room){
        //create new room with a name
        Map<String, Object> device = new HashMap<>();
        device.put("Name", name);
        device.put("Type", type);
        device.put("ID", idVal);

        // Add new room to collection with a new ID
        datab.collection("rooms").document(room).collection("devices")
                .add(device)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Added new device with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Could not add new device", e);
                    }
                });
    }

    public void deleteDevice(String name, String room) {
        datab.collection("rooms").document(room).collection("devices").document(name)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Device was deleted");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Device was not deleted");
                    }
                });
    }

    public void deleteRoom(String room) {
        datab.collection("rooms").document(room)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Room was deleted");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Room was not deleted");
                    }
                });
    }
}
