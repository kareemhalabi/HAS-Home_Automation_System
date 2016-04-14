package ca.mcgill.ecse321.android_has.songs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.android_has.PlayableItemClickListener;
import ca.mcgill.ecse321.android_has.R;

public class SongNavFragment extends Fragment {

    //required to update sorted song list
    private SongAdapter adapter;
    private static List<Song> songs;

    public void setSongs(List<Song> newSongs) {
        songs = newSongs;
        adapter.setSongs(songs);
    }

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

        View v = inflater.inflate(R.layout.fragment_nav, container, false);

        Button addSongBtn = (Button) v.findViewById(R.id.add_button);
        addSongBtn.setText(getResources().getString(R.string.addSong_button));
        addSongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSong();
            }
        });

        LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.fragment_nav_buttons);

        //Inserts buttons specific to Song Nav
        Button albumButton = new Button(getActivity().getApplicationContext());
        albumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByAlbum();
            }
        });
        albumButton.setText(getResources().getString(R.string.sortByAlbum_button));
        albumButton.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        albumButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        linearLayout.addView(albumButton, 1);

        Button artistButton = new Button(getActivity().getApplicationContext());
        artistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByArtist();
            }
        });
        artistButton.setText(getResources().getString(R.string.sortByArtist_button));
        artistButton.setLayoutParams(new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        artistButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        linearLayout.addView(artistButton, 2);


        ListView listView = (ListView) v.findViewById(R.id.list_view);
        listView.setOnItemClickListener(new PlayableItemClickListener());

        HAS h = HAS.getInstance();

        if(songs == null)
            songs = h.getSongs();

        adapter = new SongAdapter(getActivity().getApplicationContext(), songs);
        listView.setAdapter(adapter);

        return v;
    }

    public void addSong() {
        Intent intent = new Intent(getActivity().getApplicationContext(),
                AddSongActivity.class);
        startActivity(intent);
    }

    public void sortByAlbum() {
        HASController hc = new HASController();
        HAS h = HAS.getInstance();
        hc.sortAlbums();
        List<Song> songsSortedByAlbum = new ArrayList<Song>();

        for(Album a: h.getAlbums()) {
            songsSortedByAlbum.addAll(a.getSongs());
        }
        setSongs(songsSortedByAlbum);
    }

    public void sortByArtist() {
        HASController hc = new HASController();
        HAS h = HAS.getInstance();
        hc.sortArtists();
        List<Song> songsSortedByArtist = new ArrayList<Song>();
        for(Artist ar : h.getArtists()) {
            for(Album al: ar.getAlbums()) {
                songsSortedByArtist.addAll(al.getSongs());
            }
        }
        setSongs(songsSortedByArtist);
    }
}