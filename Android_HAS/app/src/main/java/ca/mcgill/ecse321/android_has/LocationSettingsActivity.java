package ca.mcgill.ecse321.android_has;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import ca.mcgill.ecse321.HAS.controller.HASController;
import ca.mcgill.ecse321.HAS.controller.InvalidInputException;
import ca.mcgill.ecse321.HAS.model.Location;
import ca.mcgill.ecse321.HAS.model.Playable;
import ca.mcgill.ecse321.HAS.model.Room;
import ca.mcgill.ecse321.HAS.model.RoomGroup;
import ca.mcgill.ecse321.android_has.roomgroups.RoomGroupNavFragment;
import ca.mcgill.ecse321.android_has.rooms.RoomNavFragment;

public class LocationSettingsActivity extends AppCompatActivity {

    private static Location location;

    private CheckBox muteCheckBox;
    private SeekBar volumeSeekBar;
    private LinearLayout parentLayout;
    private RelativeLayout playableLayout;

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

        if(location instanceof Room) {
            if(((Room) location).hasPlayable()) {
                displayPlayableInfo(((Room) location).getPlayable());
            }
        }

        if(location instanceof RoomGroup) {
            if(((RoomGroup) location).hasPlayable()) {
                displayPlayableInfo(((RoomGroup) location).getPlayable());
            }
        }
    }

    //Creates layout for displaying playable
    private void displayPlayableInfo(final Playable playable) {
        parentLayout = (LinearLayout) findViewById(R.id.location_settings_parent_layout);

        playableLayout = new RelativeLayout(getBaseContext());
        playableLayout.setLayoutParams(new ViewGroup.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView playableName = new TextView(getBaseContext());
        playableName.setTextColor(0xFF868686);
        playableName.setText("Playing: " + playable.getName());

        RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        nameParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        nameParams.addRule(RelativeLayout.CENTER_VERTICAL);

        playableName.setLayoutParams(nameParams);
        playableLayout.addView(playableName);

        Button stopBtn = new Button(getBaseContext());
        stopBtn.setText("Stop");
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop(playable);
            }
        });

        RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        btnParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        stopBtn.setLayoutParams(btnParams);

        playableLayout.addView(stopBtn);

        parentLayout.addView(playableLayout);
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

        } catch (InvalidInputException e) {} //UI already prevents invalid input


        String confirmationMessage = location.getName() + " - Volmue: " + volumeSeekBar.getProgress();
        if(muteCheckBox.isChecked())
            confirmationMessage += " (Muted)";

        Toast confirmation = Toast.makeText(getApplicationContext(),
                confirmationMessage, Toast.LENGTH_SHORT);
        confirmation.show();

        finish();
    }

    public void stop(Playable playable) {

        HASController hc = new HASController();
        try{
            if(location instanceof Room) {
                if(((Room) location).hasPlayable()) {
                    hc.playSingleRoom(null, (Room) location);
                }
            }

            if(location instanceof RoomGroup) {
                if(((RoomGroup) location).hasPlayable()) {
                    hc.playRoomGroup(null, (RoomGroup) location);
                }
            }

            String confirmationMessage = "Stopped: " + playable.getName();

            Toast confirmation = Toast.makeText(getApplicationContext(),
                    confirmationMessage, Toast.LENGTH_SHORT);
            confirmation.show();

            parentLayout.removeView(playableLayout);

        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
    }
}