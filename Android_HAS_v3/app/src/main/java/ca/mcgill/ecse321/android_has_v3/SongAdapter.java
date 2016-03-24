package ca.mcgill.ecse321.android_has_v3;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse321.HAS.model.Song;

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

        TextView songName = (TextView) vi.findViewById(R.id.song_nameTextView);
        songName.setText(songs.get(position).getName());

        TextView songDuration = (TextView) vi.findViewById(R.id.song_duratonTextView);
        songDuration.setText(Integer.toString(songs.get(position).getDuration()));

        TextView songArtist = (TextView) vi.findViewById(R.id.song_artistsTextView);
        songArtist.setText(songs.get(position).getAlbum().getArtist().getName());

        TextView songAlbum = (TextView) vi.findViewById(R.id.song_albumTextView);
        songAlbum.setText(songs.get(position).getAlbum().getName());

        return vi;
    }
}
