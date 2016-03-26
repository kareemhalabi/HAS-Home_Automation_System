package ca.mcgill.ecse321.android_has_v3.songs;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.android_has_v3.R;

public class SongAdapter extends BaseAdapter{

    Context context;
    List<Song> songs;
    private static LayoutInflater inflater = null;

    public SongAdapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(vi == null)
            vi = inflater.inflate(R.layout.songs_listview_item, null);

        Song song = songs.get(position);

        TextView songName = (TextView) vi.findViewById(R.id.song_nameTextView);
        songName.setText(song.getName());

        TextView songDuration = (TextView) vi.findViewById(R.id.song_duratonTextView);
        songDuration.setText(Integer.toString(song.getDuration()));

        TextView songArtist = (TextView) vi.findViewById(R.id.song_artistsTextView);
        songArtist.setText(song.getAlbum().getMainArtist().getName());

        TextView songAlbum = (TextView) vi.findViewById(R.id.song_albumTextView);
        songAlbum.setText(song.getAlbum().getName());

        return vi;
    }
}
