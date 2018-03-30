package e.mboyd6.tickettoride.Views.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharedcode.model.Player;

import org.w3c.dom.Text;

import java.util.ArrayList;

import e.mboyd6.tickettoride.Presenters.VictoryPresenter;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Interfaces.IVictoryActivity;


public class VictoryActivity extends AppCompatActivity implements IVictoryActivity {

    VictoryPresenter mVictoryPresenter = new VictoryPresenter(this);
    ArrayList<Player> playerListByScore;

    View victoryCard1;
    View victoryCard2;
    View victoryCard3;
    View victoryCard4;
    View victoryCard5;

    Button backToLobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        playerListByScore = (ArrayList<Player>) mVictoryPresenter.getPlayerListByScore();

        victoryCard1 = findViewById(R.id.victory_card_1);
        victoryCard2 = findViewById(R.id.victory_card_2);
        victoryCard3 = findViewById(R.id.victory_card_3);
        victoryCard4 = findViewById(R.id.victory_card_4);
        victoryCard5 = findViewById(R.id.victory_card_5);

        int count = 1;
        for(Player player : playerListByScore) {
            View victoryCard = count == 1 ? victoryCard1 : count == 2 ? victoryCard2 : count == 3 ? victoryCard3 : count == 4 ? victoryCard4 : count == 5 ? victoryCard5 : null;
            inflatePlayer(count, player, victoryCard);
            count++;
        }

        backToLobby = findViewById(R.id.victory_fragment_back_to_lobby_button);
        backToLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionToLobby();
            }
        });
    }

    protected void inflatePlayer(int rank_num, Player player, View victoryCard) {
        if (victoryCard == null)
            return;

        victoryCard.setVisibility(View.VISIBLE);

        int route_points = player.getScore().getPoints();
        int destination_points = player.getScore().destCardPoints;
        int destination_deductions = player.getScore().destCardDeductions;
        boolean has_longest_route = player.hasLongestPath();
        int total_points = player.getScore().getTotalPoints();

        TextView rank = victoryCard.findViewById(R.id.victory_card_rank);
        TextView name = victoryCard.findViewById(R.id.victory_card_name);
        TextView routes = victoryCard.findViewById(R.id.victory_card_routes);
        TextView destinations = victoryCard.findViewById(R.id.victory_card_destinations);
        TextView destinations_failed = victoryCard.findViewById(R.id.victory_card_destinations_failed);
        TextView longest_route = victoryCard.findViewById(R.id.victory_card_longest_route);
        TextView total = victoryCard.findViewById(R.id.victory_card_total);

        String rank_text = "#" + rank_num;
        String name_text = player.getName();
        String routes_text = getString(R.string.victory_routes) + " " + route_points;
        String destinations_text = getString(R.string.victory_destinations) + " " + destination_points;
        String destinations_failed_text = getString(R.string.victory_destinations_failed) + " -" + destination_deductions;
        String longest_route_text = has_longest_route ? getString(R.string.victory_longest_route) + " " + player.getLongestPath() + " trains": "";
        String total_points_text = getString(R.string.victory_total) + " " + total_points;

        rank.setText(rank_text);
        name.setText(name_text);
        routes.setText(routes_text);
        destinations.setText(destinations_text);
        destinations_failed.setText(destinations_failed_text);
        longest_route.setText(longest_route_text);
        total.setText(total_points_text);
    }

    private void transitionToLobby() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("STRAIGHT_TO_LOBBY", true);
        startActivity(intent);
        mVictoryPresenter.returnToLobby();
    }
}
