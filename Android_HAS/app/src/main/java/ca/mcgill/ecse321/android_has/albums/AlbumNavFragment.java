package ca.mcgill.ecse321.android_has.albums;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.android_has.PlayableItemClickListener;
import ca.mcgill.ecse321.android_has.R;

public class AlbumNavFragment extends Fragment {

    public AlbumNavFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_nav, container, false);

        Button addAlbumBtn = (Button) v.findViewById(R.id.add_button);
        addAlbumBtn.setText(getResources().getString(R.string.addAlbum_button));
        addAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAlbum();
            }
        });

        ListView listView = (ListView) v.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new PlayableItemClickListener());

        HAS h = HAS.getInstance();

        AlbumAdapter adapter = new AlbumAdapter(getActivity().getApplicationContext(),
                h.getAlbums());
        adapter.notifyDataSetChanged(); //sometimes doesn't update automatically
        listView.setAdapter(adapter);

        return v;
    }
    public void addAlbum() {
        Intent intent = new Intent(getActivity().getApplicationContext(),
                AddAlbumActivity.class);
        startActivity(intent);
    }
}