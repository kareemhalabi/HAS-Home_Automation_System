package ca.mcgill.ecse321.android_has.rooms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.android_has.LocationItemClickListener;
import ca.mcgill.ecse321.android_has.R;

public class RoomNavFragment extends Fragment {

    private static RoomAdapter adapter;

    public static RoomAdapter getAdapter() {
        return adapter;
    }

    public RoomNavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_nav, container, false);

        Button addRoomBtn = (Button) v.findViewById(R.id.add_button);
        addRoomBtn.setText(getResources().getString(R.string.addRoom_button));
        addRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoom();
            }
        });

        ListView listView = (ListView) v.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new LocationItemClickListener());

        HAS h = HAS.getInstance();

        adapter = new RoomAdapter(getActivity().getApplicationContext(), h.getRooms());
        adapter.notifyDataSetChanged(); //sometimes doesn't update automatically
        listView.setAdapter(adapter);

        return v;
    }

    public void addRoom() {
        Intent intent = new Intent(getActivity().getApplicationContext(),
                AddRoomActivity.class);
        startActivity(intent);
    }
}
