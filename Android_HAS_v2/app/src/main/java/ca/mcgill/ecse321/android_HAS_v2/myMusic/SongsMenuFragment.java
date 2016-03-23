package ca.mcgill.ecse321.android_HAS_v2.myMusic;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.mcgill.ecse321.android_HAS_v2.R;

/**
 * Created by Kareem Halabi on 2016-03-22.
 */
public class SongsMenuFragment extends Fragment {

    public SongsMenuFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_songs_list, container, false);
        return rootView;
    }

}
