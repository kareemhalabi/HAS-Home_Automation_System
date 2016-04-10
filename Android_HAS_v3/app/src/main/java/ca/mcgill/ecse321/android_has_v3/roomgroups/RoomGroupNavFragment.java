package ca.mcgill.ecse321.android_has_v3.roomgroups;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.android_has_v3.LocationItemClickListener;
import ca.mcgill.ecse321.android_has_v3.R;


public class RoomGroupNavFragment extends Fragment {


    public RoomGroupNavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_room_group_nav, container, false);

        ListView listView = (ListView) v.findViewById(R.id.room_group_list_view);
        listView.setOnItemClickListener(new LocationItemClickListener());

        HAS h = HAS.getInstance();

        RoomGroupAdapter adapter = new RoomGroupAdapter(getActivity().getApplicationContext(), h.getRoomGroups());
        adapter.notifyDataSetChanged(); //sometimes doesn't update automatically
        listView.setAdapter(adapter);

        return v;
    }

}
