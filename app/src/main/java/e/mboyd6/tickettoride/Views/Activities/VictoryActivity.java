package e.mboyd6.tickettoride.Views.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import e.mboyd6.tickettoride.R;
import e.mboyd6.tickettoride.Views.Interfaces.IVictoryActivity;


public class VictoryActivity extends AppCompatActivity implements IVictoryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victory);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }
}
