package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sharedcode.model.Player;

import java.util.ArrayList;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Fragments.BoardFragment;
import e.mboyd6.tickettoride.Views.Fragments.ScoreFragment;

/**
 * Class which inflates the instances of the Score object
 */

public class ScoreListAdapter extends ArrayAdapter<Player> {

    /**
     * @pre context != null
     * @pre players != null
     * @pre scoreFragment != null
     * @param context The gameActivity that gets passed in through the fragment
     * @param players A list of the players whose scores we wish to display
     * @param scoreFragment The score fragment that initialized the list
     */
    public ScoreListAdapter(Context context, ArrayList<Player> players, ScoreFragment scoreFragment) {
        super(context, 0, players);
        /*
      The context which is passed in from the Fragment. Should be the GameActivity.
     */
        Context context1 = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int position1 = position;
        View convertView1 = convertView;
        ViewGroup parent1 = parent;

        // Get the data item for this position
        final Player player = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_score, parent, false);
        }
        TextView name = convertView.findViewById(R.id.name);
        TextView points = convertView.findViewById(R.id.points);
        TextView trains = convertView.findViewById(R.id.trains);
        TextView cards = convertView.findViewById(R.id.cards);
        TextView routes = convertView.findViewById(R.id.routes);

        name.setText(player.getName());
        points.setText(String.valueOf(player.getScore().getPoints()));
        trains.setText(String.valueOf(player.getScore().getTrains()));
        cards.setText(String.valueOf(player.getScore().getCards()));
        routes.setText(String.valueOf(player.getScore().getRoutes()));

        return convertView;
    }
}