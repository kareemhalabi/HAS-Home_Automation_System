package ca.mcgill.ecse321.android_has_v3.playlists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse321.HAS.model.Playlist;
import ca.mcgill.ecse321.android_has_v3.R;


public class PlaylistAdapter extends BaseAdapter {

    Context context;
    List<Playlist> playlists;
    private static LayoutInflater inflater = null;

    public PlaylistAdapter(Context context, List<Playlist> playlists) {
        this.context = context;
        this.playlists = playlists;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return playlists.size(); }

    @Override
    public Object getItem(int position) { return playlists.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(vi == null)
            vi = inflater.inflate(R.layout.listview_item, null);

        Playlist playlist = playlists.get(position);
        TextView playlistName = (TextView) vi.findViewById(R.id.top_left_TextView);
        playlistName.setText(playlist.getName());

        TextView numberOfSongs = (TextView) vi.findViewById(R.id.bottom_left_TextView);

        String numSongs = "" + playlist.numberOfSongs() + " Song";
        if(playlist.numberOfSongs() != 1)
            numSongs += "s";
        numberOfSongs.setText(numSongs);

        return vi;
    }
}
