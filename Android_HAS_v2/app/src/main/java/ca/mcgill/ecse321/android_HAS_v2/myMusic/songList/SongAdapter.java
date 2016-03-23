package ca.mcgill.ecse321.android_HAS_v2.myMusic.songList;

import android.content.Context;
import android.widget.ArrayAdapter;


import java.util.List;

import android.view.View;
import android.view.ViewGroup;

import ca.mcgill.ecse321.HAS.model.Song;

public class SongAdapter extends ArrayAdapter<Song> {

    public SongAdapter(Context c, List<Song> songs) {
        super(c, 0, songs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SongView songView = (SongView)convertView;
        if (null == songView)
            songView = SongView.inflate(parent);
        songView.setSong(getItem(position));
        return songView;
    }

}