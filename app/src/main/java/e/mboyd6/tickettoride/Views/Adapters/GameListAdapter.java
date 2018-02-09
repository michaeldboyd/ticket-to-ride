package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sharedcode.model.Game;

import java.util.ArrayList;

import e.mboyd6.tickettoride.R;

/**
 * Created by hunte on 2/8/2018.
 */

public class GameListAdapter extends ArrayAdapter<Game> {
    private int position;
    private View convertView;
    private ViewGroup parent;

    public GameListAdapter(Context context, ArrayList<Game> games) {
        super(context, 0, games);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        this.convertView = convertView;
        this.parent = parent;
        // Get the data item for this position
        Game game = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lobby_item, parent, false);
        }
        //Swirly Photoshop magic. This may release some serious cacodemons
        String gameCreatorData = (game.getPlayerIDs().size() > 0 ? game.getPlayerIDs().get(0) : "NULL") + "\'s game";
        TextView gameCreatorField = convertView.findViewById(R.id.lobby_item_game_creator);
        gameCreatorField.setText(gameCreatorData);

        String playerCountData = game.getPlayerIDs().size() + "/5";
        TextView playerCountField = convertView.findViewById(R.id.lobby_item_player_count);
        playerCountField.setText(playerCountData);
        return convertView;
    }
}