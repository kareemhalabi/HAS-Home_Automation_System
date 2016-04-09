package ca.mcgill.ecse321.android_has_v3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ca.mcgill.ecse321.HAS.model.Playable;

public class SendPlayable extends AppCompatActivity {

    private static Playable selectedPlayable;

    public static void setSelectedPlayable(Playable playable) {
        selectedPlayable = playable;
    }

//    private static View selectedView;
//
//    public static void setSelectedView(View view) {
//        selectedView = view;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_playable);
        setTitle(selectedPlayable.getName());
    }
}
