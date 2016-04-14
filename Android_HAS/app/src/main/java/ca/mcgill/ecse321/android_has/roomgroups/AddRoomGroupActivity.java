package ca.mcgill.ecse321.android_has.roomgroups;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.android_has.R;

public class AddRoomGroupActivity extends AppCompatActivity {

    //data elements
    private String error = null;
    private ArrayList<Room> allRooms;
    private ArrayList<Room> roomsToAdd;
    private TextView roomGroupName;
    private Spinner roomSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room_group);
        setTitle("Add a Room Group");

        HAS h = HAS.getInstance();

        //Initialize Room Group name text field
        roomGroupName = (TextView) findViewById(R.id.newroomgroup_name);
        roomGroupName.setText("");

        roomsToAdd = new ArrayList<Room>();

        //Initially populate rooms from HAS
        allRooms = new ArrayList<Room>(h.getRooms());
        roomSpinner= (Spinner) findViewById(R.id.room_spinner);
        refreshRooms();
    }

    public void refreshRooms() {
        ArrayAdapter<CharSequence> roomAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        roomAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        //Populate spinner
        for(int i = 0; i < allRooms.size(); i++) {
            roomAdapter.add(allRooms.get(i).getName());
        }
        roomSpinner.setAdapter(roomAdapter);
    }

    public void refreshError() {
        TextView errorMessage = (TextView) findViewById(R.id.add_room_group_errorMessage);
        errorMessage.setText(error);
    }

    public void addRoomToRoomGroup(View v) {
        //Prevent adding the same room twice
        int selectedRoom = roomSpinner.getSelectedItemPosition();
        if(selectedRoom >= 0) {
            Room aRoom = allRooms.get(selectedRoom);
            roomsToAdd.add(aRoom);
            allRooms.remove(aRoom);
            refreshRooms();
        }
        else {
            error = "No rooms available!";
            refreshError();
        }
    }

    public void addRoomGroup(View v) {
        error = null;
        HASController hc = new HASController();

        try {
            hc.createRoomGroup(roomGroupName.getText().toString(), roomsToAdd);
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }

        //confirms addition of roomGroup and closes the activity
        if(error == null || error.length() == 0) {
            String confirmationMessage = "Added room group: " + roomGroupName.getText();
            Toast confirmation = Toast.makeText(getApplicationContext(),
                    confirmationMessage, Toast.LENGTH_SHORT);
            confirmation.show();
            finish();
        }
        else
            refreshError();
    }
}
