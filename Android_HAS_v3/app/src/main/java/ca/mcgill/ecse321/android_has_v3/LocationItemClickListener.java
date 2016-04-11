package ca.mcgill.ecse321.android_has_v3;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import ca.mcgill.ecse321.HAS.model.Location;

public class LocationItemClickListener implements AdapterView.OnItemClickListener{

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(parent.getContext(), LocationSettingsActivity.class);
        LocationSettingsActivity.setLocation((Location) parent.getAdapter().getItem(position));

        parent.getContext().startActivity(intent);
    }
}
