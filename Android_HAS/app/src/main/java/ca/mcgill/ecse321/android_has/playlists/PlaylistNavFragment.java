package ca.mcgill.ecse321.android_has.playlists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.android_has.PlayableItemClickListener;
import ca.mcgill.ecse321.android_has.R;

public class PlaylistNavFragment extends Fragment {



    public PlaylistNavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_playlist_nav, container, false);

        ListView listView = (ListView) v.findViewById(R.id.playlist_list_view);
        listView.setOnItemClickListener(new PlayableItemClickListener());

        HAS h = HAS.getInstance();

        PlaylistAdapter adapter = new PlaylistAdapter(getActivity().getApplicationContext(), h.getPlaylists());
        adapter.notifyDataSetChanged(); //sometimes doesn't update automatically
        listView.setAdapter(adapter);

        return v;
    }


}
