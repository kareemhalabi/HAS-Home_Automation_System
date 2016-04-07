package ca.mcgill.ecse321.android_has_v3.artists;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.android_has_v3.R;

public class AddArtistActivity extends AppCompatActivity {

    //data elements
    private String error = null;
    private TextView artistName = null;
    private TextView errorMessage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_artist);
        setTitle("Add an Artist");

        errorMessage = (TextView) findViewById(R.id.add_artist_errorMessage);

        // Initialize artist name text field
        artistName = (TextView) findViewById(R.id.newartist_name);
        artistName.setText("");
    }

    public void refreshError() {
        errorMessage.setText(error);
    }

    public void addArtist(View v) {

        error = null;
        HASController hc = new HASController();
        try {
            hc.createArtist(artistName.getText().toString());
        } catch (InvalidInputException e) {
            error = e.getMessage();
        }

        //confirms the addition of an artist and closes the activity
        if(error == null || error.length() == 0) {
            String confirmationMessage = "Added artist: " + artistName.getText();
            Toast confirmation = Toast.makeText(getApplicationContext(),
                    confirmationMessage, Toast.LENGTH_SHORT);
            confirmation.show();
            finish();
        }
        else
            refreshError();
    }
}
