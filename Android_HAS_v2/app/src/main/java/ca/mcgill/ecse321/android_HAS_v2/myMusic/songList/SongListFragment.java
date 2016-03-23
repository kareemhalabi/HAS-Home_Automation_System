package ca.mcgill.ecse321.android_HAS_v2.myMusic.songList;


import android.app.ListFragment;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.mcgill.ecse321.HAS.model.HAS;
import ca.mcgill.ecse321.HAS.persistence.PersistenceHAS;

public class SongListFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);

        PersistenceHAS.setFileName(
                Environment.getExternalStorageDirectory().getPath() + "/HAS.xml");
        PersistenceHAS.loadHASModel();

        HAS h = HAS.getInstance();
        setListAdapter(new SongAdapter(getActivity(), h.getSongs()));
        return v;
    }
}
