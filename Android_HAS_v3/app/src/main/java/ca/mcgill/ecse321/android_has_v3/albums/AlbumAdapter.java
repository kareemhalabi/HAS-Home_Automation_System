package ca.mcgill.ecse321.android_has_v3.albums;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.android_has_v3.R;


public class AlbumAdapter extends BaseAdapter{

    Context context;
    List<Album> albums;
    private static LayoutInflater inflater = null;

    public AlbumAdapter(Context context, List<Album> albums) {
        this.context = context;
        this.albums = albums;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { return albums.size(); }

    @Override
    public Object getItem(int position) { return albums.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        if(vi == null)
            vi = inflater.inflate(R.layout.listview_item, null);

        Album album = albums.get(position);

        TextView albumName = (TextView) vi.findViewById(R.id.top_left_TextView);
        albumName.setText(album.getName());

        //Formats date as "DD-MM-YYYY"
        TextView releaseDate = (TextView) vi.findViewById(R.id.top_right_TextView);
        Calendar c = Calendar.getInstance();
        c.setTime(album.getReleaseDate());
        releaseDate.setText(String.format("%02d-%02d-%04d", c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR)));

        TextView mainArtist = (TextView) vi.findViewById(R.id.bottom_left_TextView);
        mainArtist.setText(album.getMainArtist().getName());

        TextView genre = (TextView) vi.findViewById(R.id.bottom_right_TextView);
        genre.setText(album.getGenre());

        return vi;
    }
}
