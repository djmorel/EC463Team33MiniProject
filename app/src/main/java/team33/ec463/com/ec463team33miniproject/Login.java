package team33.ec463.com.ec463team33miniproject;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Create a reference to the Login Button
        Button GoogleLogin_Button = (Button) findViewById(R.id.GoogleLogin_Button);
        GoogleLogin_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Testing, using Devices NOT Rooms now...
                Intent homeIntent = new Intent(getApplicationContext(), Devices.class);

                // Pass Google Username

                startActivity(homeIntent);
            }
        });
    }
}
