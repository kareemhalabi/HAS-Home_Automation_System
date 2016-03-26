package ca.mcgill.ecse321.android_has_v3.songs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.Album;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.android_has_v3.R;

public class AddSongActivity extends AppCompatActivity {

    //data elements
    private String error = null;
    private HashMap<Integer, Album> albums;
    private ArrayList<Artist> artists;
    private ArrayList<Artist> ftArtists;
    private Spinner ftArtsitsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        setTitle("Add a Song");

        HAS h = HAS.getInstance();

        // Initialize the data in the album spinner
        Spinner albumSpinner = (Spinner) findViewById(R.id.albumspinner);
        ArrayAdapter<CharSequence> albumAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        albumAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        this.albums = new HashMap<Integer, Album>();

        int i = 0;
        for(Iterator<Album> albums = h.getAlbums().iterator();
            albums.hasNext(); i++) {
            Album al = albums.next();
            albumAdapter.add(al.getName());
            this.albums.put(i,al);
        }
        albumSpinner.setAdapter(albumAdapter);

        // Initialize song name text field
        TextView songName = (TextView) findViewById(R.id.newsong_name);
        songName.setText("");

        // Initialize song name text field
        TextView songDuration = (TextView) findViewById(R.id.newsong_duration);
        songDuration.setText("");

        // Initialize song name text field
        TextView songPosition = (TextView) findViewById(R.id.newsong_position);
        songPosition.setText("");

        ftArtists = new ArrayList<Artist>();

        //Initially populate artists from HAS
        artists = new ArrayList<Artist>(h.getArtists());
        ftArtsitsSpinner = (Spinner) findViewById(R.id.ftartistspinner);
        refreshftArtists();
    }

    public void refreshftArtists() {
        ArrayAdapter<CharSequence> ftArtistAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        ftArtistAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        //Populate spinner
        for(int i = 0; i < artists.size(); i++) {
            ftArtistAdapter.add(artists.get(i).getName());
        }
        ftArtsitsSpinner.setAdapter(ftArtistAdapter);
    }

    public void refreshError() {
        TextView errorMessage = (TextView) findViewById(R.id.add_song_errorMessage);
        errorMessage.setText(error);
    }

    public void addSong(View v) {

        error = null;
        HASController hc = new HASController();

        Spinner albumSpinner = (Spinner) findViewById(R.id.albumspinner);
        int selectedAlbum = albumSpinner.getSelectedItemPosition();

        TextView songName = (TextView) findViewById(R.id.newsong_name);

        TextView songDuration = (TextView) findViewById(R.id.newsong_duration);

        TextView songPosition = (TextView) findViewById(R.id.newsong_position);


        // if no text is in songDuration, the duration defaults to 0 so
        // that hc can print an error
        int duration = 0;
        try {
            duration = Integer.parseInt(songDuration.getText().toString());
        } catch (NumberFormatException e) {}

        // if no text is in songPosition, the position defaults to 0 so
        // that hc can print an error
        int position = 0;
        try {
            position = Integer.parseInt(songPosition.getText().toString());
        } catch (NumberFormatException e) {}

        try {
            hc.addSongtoAlbum(albums.get(selectedAlbum), songName.getText().toString(),
                    duration, position);
        } catch(InvalidInputException e) {
            error = e.getMessage();
        }

        //confirms the addition of a song and closes the activity
        if(error == null || error.length() == 0) {
            String confirmationMessage = "Added song: " + songName.getText();
            Toast confirmation = Toast.makeText(getApplicationContext(),
                    confirmationMessage, Toast.LENGTH_SHORT);
            confirmation.show();
            finish();
        }
        else
            refreshError();
    }

    public void addftArtist(View view) {
        //Prevent adding the same ftArtist
        int selectedftArtist = ftArtsitsSpinner.getSelectedItemPosition();
        if (selectedftArtist >= 0) {
            Artist aftArtist = artists.get(selectedftArtist);
            ftArtists.add(aftArtist);
            artists.remove(selectedftArtist);
            refreshftArtists();
        }
        else {
            error = "No ft Artists available!";
            refreshError();
        }

    }
}
