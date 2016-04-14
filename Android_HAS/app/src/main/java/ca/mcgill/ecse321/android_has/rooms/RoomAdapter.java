package ca.mcgill.ecse321.android_has.rooms;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.android_has.R;

public class RoomAdapter extends BaseAdapter{

    private List<Room> rooms;
    private static LayoutInflater inflater = null;

    public RoomAdapter(Context context, List<Room> rooms) {
        this.rooms = rooms;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(vi == null)
            vi = inflater.inflate(R.layout.listview_item, null);

        Room room = rooms.get(position);

        TextView roomName = (TextView) vi.findViewById(R.id.top_left_TextView);
        roomName.setText(room.getName());

        TextView roomVolume = (TextView) vi.findViewById(R.id.top_right_TextView);
        int volumeAmount = room.getVolume();
        String volume = "Volume: " + volumeAmount;
        roomVolume.setText(volume);

        if(room.hasPlayable()) {
            TextView playableName = (TextView) vi.findViewById(R.id.bottom_left_TextView);
            String nowPlaying = "Playing: " + room.getPlayable().getName();
            playableName.setText(nowPlaying);
        }


        TextView muteStatus = (TextView) vi.findViewById(R.id.bottom_right_TextView);
        if(room.getMute()) {
            muteStatus.setText("Muted");
        }
        else muteStatus.setText("");

        return vi;
    }
}
