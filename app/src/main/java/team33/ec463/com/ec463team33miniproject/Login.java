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

                // Go to the Rooms Activity as the default homepage
                Intent homeIntent = new Intent(getApplicationContext(), Rooms.class);
                startActivity(homeIntent);

                // Pass Google Username to be used in drawer menu

            }
        });
    }
}
