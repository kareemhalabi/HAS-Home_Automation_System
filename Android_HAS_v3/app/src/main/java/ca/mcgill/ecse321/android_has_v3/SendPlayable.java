package ca.mcgill.ecse321.android_has_v3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.model.Playable;
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.RoomGroup;
import ca.mcgill.ecse321.android_has_v3.roomgroups.RoomGroupAdapter;
import ca.mcgill.ecse321.android_has_v3.rooms.RoomAdapter;

public class SendPlayable extends AppCompatActivity {

    private static Playable selectedPlayable;

    public static void setSelectedPlayable(Playable playable) {
        selectedPlayable = playable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_playable);
        setTitle("Where do you want to play:");

        //Recreate playable item view
        TextView topLeft = (TextView) findViewById(R.id.top_left_TextView);
        topLeft.setTextColor(0xFF868686);
        TextView topRight = (TextView) findViewById(R.id.top_right_TextView);
        topRight.setTextColor(0xFF868686);
        TextView bottomLeft = (TextView) findViewById(R.id.bottom_left_TextView);
        bottomLeft.setTextColor(0xFF868686);
        TextView bottomRight = (TextView) findViewById(R.id.bottom_right_TextView);
        bottomRight.setTextColor(0xFF868686);

        Intent intent = getIntent();

        topLeft.setText(intent.getStringExtra("TOP_LEFT"));
        topRight.setText(intent.getStringExtra("TOP_RIGHT"));
        bottomLeft.setText(intent.getStringExtra("BOTTOM_LEFT"));
        bottomRight.setText(intent.getStringExtra("BOTTOM_RIGHT"));

        HAS h = HAS.getInstance();

        ListView roomListView = (ListView) findViewById(R.id.room_list_view);
        roomListView.setAdapter(new RoomAdapter(getApplicationContext(), h.getRooms()));
        roomListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Room selectedRoom = (Room) parent.getAdapter().getItem(position);
                    selectedRoom.setPlayable(selectedPlayable);

                    String confirmationMessage = "Playing: " + selectedPlayable.getName() + " in " +
                            selectedRoom.getName();
                    Toast confirmation = Toast.makeText(getApplicationContext(),
                            confirmationMessage, Toast.LENGTH_SHORT);
                    confirmation.show();
                    finish();
                }
            }
        );

        ListView roomGroupListView = (ListView) findViewById(R.id.room_group_list_view);
        roomGroupListView.setAdapter(new RoomGroupAdapter(getApplicationContext(), h.getRoomGroups()));
        roomGroupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RoomGroup selectedRoomGroup = (RoomGroup) parent.getAdapter().getItem(position);
                selectedRoomGroup.setPlayable(selectedPlayable);

                String confirmationMessage = "Playing: " + selectedPlayable.getName() + " in " +
                        selectedRoomGroup.getName();
                Toast confirmation = Toast.makeText(getApplicationContext(),
                        confirmationMessage, Toast.LENGTH_SHORT);
                confirmation.show();
                finish();
            }
        });
    }
}
