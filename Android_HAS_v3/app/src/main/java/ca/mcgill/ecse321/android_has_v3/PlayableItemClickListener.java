package ca.mcgill.ecse321.android_has_v3;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import ca.mcgill.ecse321.HAS.model.Playable;

public class PlayableItemClickListener implements AdapterView.OnItemClickListener{

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(parent.getContext(), SendPlayable.class);
        SendPlayable.setSelectedPlayable((Playable) parent.getAdapter().getItem(position));
//        SendPlayable.setSelectedView(view);
        parent.getContext().startActivity(intent);
    }
}
