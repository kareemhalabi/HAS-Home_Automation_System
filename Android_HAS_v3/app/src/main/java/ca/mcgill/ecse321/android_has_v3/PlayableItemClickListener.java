package ca.mcgill.ecse321.android_has_v3;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;


import ca.mcgill.ecse321.HAS.model.Playable;

public class PlayableItemClickListener implements AdapterView.OnItemClickListener{

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(parent.getContext(), SendPlayable.class);
        SendPlayable.setSelectedPlayable((Playable) parent.getAdapter().getItem(position));

        TextView topLeft = (TextView) view.findViewById(R.id.top_left_TextView);
        TextView topRight = (TextView) view.findViewById(R.id.top_right_TextView);
        TextView bottomLeft = (TextView) view.findViewById(R.id.bottom_left_TextView);
        TextView bottomRight = (TextView) view.findViewById(R.id.bottom_right_TextView);

        intent.putExtra("TOP_LEFT", topLeft.getText());
        intent.putExtra("TOP_RIGHT", topRight.getText());
        intent.putExtra("BOTTOM_LEFT", bottomLeft.getText());
        intent.putExtra("BOTTOM_RIGHT", bottomRight.getText());

        parent.getContext().startActivity(intent);
    }
}
