package ca.mcgill.ecse321.android_has_v3.roomgroups;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.RoomGroup;
import ca.mcgill.ecse321.android_has_v3.R;

public class RoomGroupAdapter extends BaseAdapter{

    Context context;
    List<RoomGroup> roomGroups;
    private static LayoutInflater inflater = null;

    public RoomGroupAdapter(Context context, List<RoomGroup> roomGroups) {
        this.context = context;
        this.roomGroups = roomGroups;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return roomGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return roomGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(vi == null)
            vi = inflater.inflate(R.layout.listview_item_room_group, null);

        RoomGroup roomGroup = roomGroups.get(position);

        TextView roomGroupName = (TextView) vi.findViewById(R.id.room_group_nameTextView);
        roomGroupName.setText(roomGroup.getName());


        if(roomGroup.hasPlayable()) {
            TextView playableName = (TextView) vi.findViewById(R.id.room_group_playableNameTextView);
            String nowPlaying = "Playing: " + roomGroup.getPlayable().getName();
            playableName.setText(nowPlaying);
        }

        TextView numberOfRooms = (TextView) vi.findViewById(R.id.room_group_size_TextView);
        String numRooms = "" + roomGroup.numberOfRooms() + " Room";
        if(roomGroup.numberOfRooms() != 1)
            numRooms += "s";
        numberOfRooms.setText(numRooms);

        return vi;
    }
}
