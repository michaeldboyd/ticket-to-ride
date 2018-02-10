package e.mboyd6.tickettoride.Views.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharedcode.model.Game;

import java.util.ArrayList;

import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Interfaces.ILobbyFragment;

/**
 * Created by hunte on 2/8/2018.
 */

public class GameListAdapter extends ArrayAdapter<Game> {
    private int position;
    private View convertView;
    private ViewGroup parent;
    private View lobbyItemCard1;
    private View lobbyItemCard2;
    private View lobbyItemCard3;
    private View lobbyItemCard4;
    private View lobbyItemCard5;
    private Button joinButton;
    private Context context;

    public GameListAdapter(Context context, ArrayList<Game> games) {
        super(context, 0, games);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        this.position = position;
        this.convertView = convertView;
        this.parent = parent;
        // Get the data item for this position
        final Game game = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lobby_item, parent, false);
        }
        //Swirly Photoshop magic. This may release some serious cacodaemons
        String gameCreatorData = (game.getPlayerIDs().size() > 0 ? game.getPlayerIDs().get(0) : "NULL") + "\'s game";
        TextView gameCreatorField = convertView.findViewById(R.id.lobby_item_game_creator);
        // Set the game creator
        gameCreatorField.setText(gameCreatorData);
        // Set the colors accordingly
        lobbyItemCard1 = convertView.findViewById(R.id.lobby_item_card_1);
        lobbyItemCard2 = convertView.findViewById(R.id.lobby_item_card_2);
        lobbyItemCard3 = convertView.findViewById(R.id.lobby_item_card_3);
        lobbyItemCard4 = convertView.findViewById(R.id.lobby_item_card_4);
        lobbyItemCard5 = convertView.findViewById(R.id.lobby_item_card_5);
        int playerCount = game.getPlayerIDs().size();
        lobbyItemCard1.setBackgroundResource(playerCount > 4 ? R.drawable.color_red : R.drawable.color_invisible);
        lobbyItemCard2.setBackgroundResource(playerCount > 3 ? R.drawable.color_turquoise : R.drawable.color_invisible);
        lobbyItemCard3.setBackgroundResource(playerCount > 2 ? R.drawable.color_orange : R.drawable.color_invisible);
        lobbyItemCard4.setBackgroundResource(playerCount > 1 ? R.drawable.color_blue : R.drawable.color_invisible);
        lobbyItemCard5.setBackgroundResource(playerCount > 0 ? R.drawable.color_purple : R.drawable.color_invisible);
        // Set the player count
        String playerCountData = playerCount + "/5";
        TextView playerCountField = convertView.findViewById(R.id.lobby_item_player_count);
        playerCountField.setText(playerCountData);

        // Disable the join button if it's full
        joinButton = convertView.findViewById(R.id.lobby_item_game_join_button);
        if (playerCount > 4) {
            joinButton.setBackgroundResource(R.drawable.buttonshape_red_nocorners_bg);
            joinButton.setText(context.getString(R.string.full));
            joinButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        } else {
            joinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof ILobbyFragment)
                    {
                        ILobbyFragment lobbyFragment = (ILobbyFragment) context;
                        lobbyFragment.onLobbyFragmentJoinGameButton(game);
                    }
                }
            });
        }

        return convertView;
    }
}