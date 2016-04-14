package ca.mcgill.ecse321.android_has.rooms;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.android_has.R;

public class AddRoomActivity extends AppCompatActivity {

    //data elements
    private String error = null;
    private TextView roomName = null;
    private TextView errorMessage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        setTitle("Add a Room");

        errorMessage = (TextView) findViewById(R.id.add_room_errorMessage);

        //Initialize artist name text field
        roomName = (TextView) findViewById(R.id.newroom_name);
        roomName.setText("");
    }

    public void refreshError() {
        errorMessage.setText(error);
    }

    public void addRoom(View v) {
        error = null;
        HASController hc = new HASController();
        try {
            hc.createRoom(roomName.getText().toString());
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }

        //confirms addition of room and closes activity
        if(error == null || error.length() == 0) {
            String confirmationMessage = "Added room: " + roomName.getText();
            Toast confirmation = Toast.makeText(getApplicationContext(),
                    confirmationMessage, Toast.LENGTH_SHORT);
            confirmation.show();
            finish();
        }
        else
            refreshError();
    }
}
