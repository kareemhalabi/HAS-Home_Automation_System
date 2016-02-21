package ca.mcgill.ecse321.android_has;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

            // Initialize album genre text field
            TextView newAlbumGenre = (TextView) findViewById(R.id.newalbum_genre);
            newAlbumGenre.setText("");

            // Initialize album release date to today
            Calendar c = Calendar.getInstance();
            setDate(R.id.albumReleasedate, c.get(Calendar.DAY_OF_MONTH),
                    c.get(Calendar.MONTH), c.get(Calendar.YEAR));
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

    public void addAlbum(View v) {

        error = null;
        HASController hc = new HASController();

        TextView albumName = (TextView) findViewById(R.id.newalbum_name);

        Spinner artistSpinner = (Spinner) findViewById(R.id.artistspinner);
        int selectedArtist = artistSpinner.getSelectedItemPosition();

        TextView albumGenre = (TextView) findViewById(R.id.newalbum_genre);

        TextView releaseDate = (TextView) findViewById(R.id.albumReleasedate);
        DateFormat dft = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = new Date(dft.parse(releaseDate.getText().toString()).getTime());
        } catch (ParseException e) {
            error = "Date not formatted correclty!";
        }

        try {
            hc.createAlbum(albumName.getText().toString(),
                    albumGenre.getText().toString(),
                    date,
                    artists.get(selectedArtist));
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }

        refreshData();
    }

    public void showDatePickerDialog(View v) {
        TextView tf = (TextView) v;
        Bundle args = getDateFromLabel(tf.getText());
        args.putInt("id", v.getId());

        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(args);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private Bundle getDateFromLabel(CharSequence text) {
        Bundle rtn = new Bundle();
        String comps[] = text.toString().split("-");
        int day =1;
        int month=1;
        int year = 1;

        if (comps.length == 3) {
            day = Integer.parseInt(comps[0]);
            month = Integer.parseInt(comps[1]);
            year = Integer.parseInt(comps[2]);
        }

        rtn.putInt("day", day);
        rtn.putInt("month", month - 1);
        rtn.putInt("year", year);

        return rtn;
    }

    public void setDate(int id, int day, int month, int year) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(String.format("%02d-%02d-%04d", day, month + 1, year));
    }
}
