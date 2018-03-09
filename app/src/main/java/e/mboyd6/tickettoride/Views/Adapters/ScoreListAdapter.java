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
 * Created by hunte on 2/8/2018.
 */

public class ScoreListAdapter extends ArrayAdapter<Player> {
    private final Context context;
    private final ScoreFragment scoreFragment;
    private int position;
    private View convertView;
    private ViewGroup parent;

    public ScoreListAdapter(Context context, ArrayList<Player> players, ScoreFragment scoreFragment) {
        super(context, 0, players);
        this.context = context;
        this.scoreFragment = scoreFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        this.convertView = convertView;
        this.parent = parent;

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