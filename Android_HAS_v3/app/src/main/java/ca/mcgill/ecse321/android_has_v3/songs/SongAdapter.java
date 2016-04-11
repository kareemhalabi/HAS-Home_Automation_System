package ca.mcgill.ecse321.android_has_v3.songs;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.android_has_v3.R;

public class SongAdapter extends BaseAdapter{

    Context context;

    private List<Song> songs;

    public void setSongs(List<Song> songs) {
        this.songs = songs;
        this.notifyDataSetChanged();
    }

    public List<Song> getSongs() {
        return songs;
    }
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
            vi = inflater.inflate(R.layout.listview_item, null);

        Song song = songs.get(position);

        TextView songName = (TextView) vi.findViewById(R.id.top_left_TextView);
        songName.setText(song.getName());

        // duration appears as "m...mm:ss"
        TextView songDuration = (TextView) vi.findViewById(R.id.top_right_TextView);
        int duration = song.getDuration();
        String formattedDuration = String.format("%d:%02d", (duration/60), (duration%60));
        songDuration.setText(formattedDuration);

        // artist appears as "By: MainArtist ft FtArtist1, FtArtist2, ... , FtArtistN"
        TextView songArtist = (TextView) vi.findViewById(R.id.bottom_left_TextView);
        String formattedArtist = "By: " + song.getAlbum().getMainArtist().getName();
        if(song.hasFtArtists()) {
            formattedArtist += " ft ";
            List<Artist> ftArtistNames = song.getFtArtists();
            for (int i = 0; i < ftArtistNames.size() - 1; i++){
                formattedArtist += ftArtistNames.get(i).getName() + ", ";
            }
            formattedArtist += ftArtistNames.get(ftArtistNames.size()-1).getName();
        }
        songArtist.setText(formattedArtist);

        // album appears as "Album:position"
        TextView songAlbum = (TextView) vi.findViewById(R.id.bottom_right_TextView);
        String formattedAlbum = song.getAlbum().getName() + ": " + song.getPosition();
        songAlbum.setText(formattedAlbum);

        return vi;
    }
}
