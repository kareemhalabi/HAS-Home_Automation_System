package ca.mcgill.ecse321.android_HAS_v2.myMusic.songList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ca.mcgill.ecse321.HAS.model.Song;

public class SongView extends RelativeLayout {
    private TextView sNameTextView;
    private TextView sDurationTextView;
    private TextView sArtistTextView;
    private TextView sAlbumTextView;

    public static SongView inflate(ViewGroup parent) {
        SongView songView = (SongView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.songs_view, parent, false);
        return songView;
    }

    public SongView (Context c) {
        this(c, null);
    }

    public SongView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SongView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.songs_view_children, this, true);
        setupChildren();
    }

    private void setupChildren() {
        sNameTextView = (TextView) findViewById(R.id.song_nameTextView);
        sDurationTextView = (TextView) findViewById(R.id.song_duratonTextView);
        sArtistTextView = (TextView) findViewById(R.id.song_artistsTextView);
        sAlbumTextView = (TextView) findViewById(R.id.song_albumTextView);
    }

    public void setSong(Song song) {
        sNameTextView.setText(song.getName());
        sDurationTextView.setText(Integer.toString(song.getDuration()));
        sArtistTextView.setText(song.getAlbum().getArtist().getName());
        sAlbumTextView.setText(song.getAlbum().getName());
    }
}
