package ca.mcgill.ecse321.android_has;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.Artist;
import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.persistence.PersistenceHAS;

public class MainActivity extends AppCompatActivity {

    // data elements
    private String error = null;
    private HashMap<Integer, Artist> artists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PersistenceHAS.setFileName(
                Environment.getExternalStorageDirectory().getPath() + "/HAS.xml");
        PersistenceHAS.loadHASModel();
        refreshData();
    }

    private void refreshData() {

        HAS h = HAS.getInstance();

        // error
        TextView errorMessage = (TextView) findViewById(R.id.errorMessage);
        errorMessage.setText(error);

        if(error == null || error.length() == 0) {

            // Initialize artist name text field
            TextView newArtist = (TextView) findViewById(R.id.newartist_name);
            newArtist.setText("");

            // Initialize album name text field
            TextView newAlbumName = (TextView) findViewById(R.id.newalbum_name);
            newAlbumName.setText("");

            // Initialize the data in the artist spinner
            Spinner artistSpinner = (Spinner) findViewById(R.id.artistspinner);
            ArrayAdapter<CharSequence> artistAdapter = new
                    ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item);
            artistAdapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item);
            this.artists = new HashMap<Integer, Artist>();

            int i = 0;
            for(Iterator<Artist> artists = h.getArtists().iterator();
                    artists.hasNext(); i++) {
                Artist ar = artists.next();
                artistAdapter.add(ar.getName());
                this.artists.put(i,ar);
            }
            artistSpinner.setAdapter(artistAdapter);
        }
    }

    public void addArtist(View v) {
        error = null;
        HASController hc = new HASController();
        TextView newArtist = (TextView) findViewById(R.id.newartist_name);
        try {
            hc.createArtist(newArtist.getText().toString());
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }

        refreshData();
    }
}
