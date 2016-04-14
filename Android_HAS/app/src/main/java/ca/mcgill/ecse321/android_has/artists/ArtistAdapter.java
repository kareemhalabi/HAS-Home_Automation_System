package ca.mcgill.ecse321.android_has.artists;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.android_has.R;

public class ArtistAdapter extends BaseAdapter {

    Context context;
    List<Artist> artists;
    private static LayoutInflater inflater = null;

    public ArtistAdapter(Context context, List<Artist> artists) {
        this.context = context;
        this.artists = artists;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return artists.size(); }

    @Override
    public Object getItem(int position) { return artists.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(vi == null)
            vi = inflater.inflate(R.layout.listview_item, null);

        Artist artist = artists.get(position);
        TextView artistName = (TextView) vi.findViewById(R.id.top_left_TextView);
        artistName.setText(artists.get(position).getName());

        TextView numberOfAlbums = (TextView) vi.findViewById(R.id.bottom_left_TextView);

        String numAlbums = "" + artist.numberOfAlbums() + " Album";
        if(artist.numberOfAlbums() != 1)
            numAlbums += "s";
        numberOfAlbums.setText(numAlbums);

        TextView numberOfFtSongs = (TextView) vi.findViewById(R.id.bottom_right_TextView);
        if(artist.numberOfSongs() !=0) {
            String numFtSongs = "Featured in " + artist.numberOfSongs() + " song";
            if (artist.numberOfSongs() != 1)
                numFtSongs += "s";
            numberOfFtSongs.setText(numFtSongs);
        } else {
            numberOfFtSongs.setText("");
        }

        return vi;
    }
}
