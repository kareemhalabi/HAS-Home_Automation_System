package ca.mcgill.ecse321.android_has_v3.songs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.android_has_v3.R;

public class SongNavFragment extends Fragment {

    public SongNavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_song_nav, container, false);

        ListView listView = (ListView) v.findViewById(R.id.song_list_view);

        HAS h = HAS.getInstance();

        SongAdapter adapter = new SongAdapter(getActivity().getApplicationContext(), h.getSongs());
        listView.setAdapter(adapter);

        return v;
    }


}
