package ca.mcgill.ecse321.android_has;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.Location;
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.RoomGroup;
import ca.mcgill.ecse321.android_has.roomgroups.RoomGroupNavFragment;
import ca.mcgill.ecse321.android_has.rooms.RoomNavFragment;

public class LocationSettingsActivity extends AppCompatActivity {

    private static Location location;

    private CheckBox muteCheckBox;
    private SeekBar volumeSeekBar;

    public static void setLocation(Location alocation) {
        location = alocation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_settings);
        setTitle(location.getName());

        volumeSeekBar = (SeekBar) findViewById(R.id.volume_seekBar);
        volumeSeekBar.setProgress(location.getVolume());

        final TextView volumeTextView = (TextView) findViewById(R.id.volume_TextView);
        volumeTextView.setText("" + location.getVolume());

        muteCheckBox = (CheckBox) findViewById(R.id.mute_CheckBox);
        muteCheckBox.setChecked(location.getMute());

        assert volumeSeekBar != null;
        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int volume, boolean fromUser) {
                volumeTextView.setText("" + volume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                muteCheckBox.setChecked(seekBar.getProgress() == 0);
            }
        });
    }

    public void saveChanges(View v) {
        HASController hc = new HASController();

        //No controller methods for location object
        try {
            if(location instanceof Room) {
                hc.setRoomVolumeLevel((Room) location, volumeSeekBar.getProgress());
                hc.setRoomMute((Room) location, muteCheckBox.isChecked());
                RoomNavFragment.getAdapter().notifyDataSetChanged();
            }
            else if(location instanceof RoomGroup) {
                hc.setRoomGroupVolumeLevel((RoomGroup) location, volumeSeekBar.getProgress());
                hc.setRoomGroupMute((RoomGroup) location, muteCheckBox.isChecked());
                RoomGroupNavFragment.getAdapter().notifyDataSetChanged();
            }

        } catch (InvalidInputException e) {} //UI prevents invalid input


        String confirmationMessage = location.getName() + " - Volmue: " + volumeSeekBar.getProgress();
        if(muteCheckBox.isChecked())
            confirmationMessage += " (Muted)";

        Toast confirmation = Toast.makeText(getApplicationContext(),
                confirmationMessage, Toast.LENGTH_SHORT);
        confirmation.show();

        finish();
    }
}