package ca.mcgill.ecse321.android_has_v3.artists;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.android_has_v3.R;

public class ArtistNavFragment extends Fragment {

    public ArtistNavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_artist_nav, container, false);

        ListView listView = (ListView) v.findViewById(R.id.artist_list_view);

        HAS h = HAS.getInstance();

        ArtistAdapter adapter = new ArtistAdapter(getActivity().getApplicationContext(), h.getArtists());
        listView.setAdapter(adapter);

        return v;
    }


}
