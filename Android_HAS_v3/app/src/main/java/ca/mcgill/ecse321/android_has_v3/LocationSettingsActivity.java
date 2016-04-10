package ca.mcgill.ecse321.android_has_v3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.model.Location;

public class LocationSettingsActivity extends AppCompatActivity {

    private static Location location;

    public int volumeSliderValue;
    private boolean muteChecked;

    public static void setLocation(Location alocation) {
        location = alocation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_settings);
        setTitle(location.getName());

        final SeekBar volumeSeekBar = (SeekBar) findViewById(R.id.volume_seekBar);
        volumeSeekBar.setProgress(location.getVolume());

        final TextView volumeTextView = (TextView) findViewById(R.id.volume_TextView);
        volumeTextView.setText(""+location.getVolume());

        final CheckBox muteCheckBox = (CheckBox) findViewById(R.id.mute_CheckBox);
        muteCheckBox.setChecked(location.getMute());

        assert volumeSeekBar != null;
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int volume, boolean fromUser) {
                volumeSliderValue = volume;
                volumeTextView.setText("" + volumeSliderValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(volumeSliderValue == 0)
                    muteChecked = true;
                else
                    muteChecked = false;

                muteCheckBox.setChecked(muteChecked);
            }
        });
    }

    public void saveChanges(View v) {
        HASController hc = new HASController();

        location.setVolume(volumeSliderValue);
        location.setMute(muteChecked);

        String confirmationMessage = location.getName() + " volmue: " + volumeSliderValue;
        if(muteChecked)
            confirmationMessage += " (Muted)";

        Toast confirmation = Toast.makeText(getApplicationContext(),
                confirmationMessage, Toast.LENGTH_SHORT);
        confirmation.show();
        finish();
    }
}
