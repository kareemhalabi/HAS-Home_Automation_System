package ca.mcgill.ecse321.android_has.playlists;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.model.Song;
import ca.mcgill.ecse321.android_has.R;

public class AddPlaylistActivity extends AppCompatActivity {

    //data elements
    private String error = null;
    private ArrayList<Song> allSongs;
    private ArrayList<Song> playlistSongs;
    private Spinner songSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);
        setTitle("Add a Playlist");

        HAS h = HAS.getInstance();

        //Initialize playlist name text field
        TextView playlistName = (TextView) findViewById(R.id.newplaylist_name);
        playlistName.setText("");

        playlistSongs = new ArrayList<Song>();

        //Initially populate songs from HAS
        allSongs = new ArrayList<Song>(h.getSongs());
        songSpinner = (Spinner) findViewById(R.id.playlist_song_spinner);
        refreshSongs();
    }

    public void refreshSongs() {
        ArrayAdapter<CharSequence> songAdapter = new
                ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
        songAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        //Populate spinner
        for(int i = 0; i < allSongs.size(); i++) {
            songAdapter.add(allSongs.get(i).getName());
        }
        songSpinner.setAdapter(songAdapter);
    }

    public void refreshError() {
        TextView errorMessage = (TextView) findViewById(R.id.add_playlist_errorMessage);
        errorMessage.setText(error);
    }

    public void addSongToPlaylist(View v) {
        //Prevent adding the same song twice
        int selectedSong = songSpinner.getSelectedItemPosition();
        if(selectedSong >= 0) {
            Song aSong = allSongs.get(selectedSong);
            playlistSongs.add(aSong);
            allSongs.remove(aSong);
            refreshSongs();
        }
        else {
            error = "No songs available!";
            refreshError();
        }
    }

    public void addPlaylist(View v) {
        error = null;
        HASController hc = new HASController();

        TextView playListName = (TextView) findViewById(R.id.newplaylist_name);
        try {
            hc.createPlaylist(playListName.getText().toString(), playlistSongs);
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }

        //confirms the addition of a playlist and closes the activity
        if(error == null || error.length() == 0) {
            String confirmationMessage = "Added playlist: " + playListName.getText();
            Toast confirmation = Toast.makeText(getApplicationContext(),
                    confirmationMessage, Toast.LENGTH_SHORT);
            confirmation.show();
            finish();
        }
        else
            refreshError();
    }
}
