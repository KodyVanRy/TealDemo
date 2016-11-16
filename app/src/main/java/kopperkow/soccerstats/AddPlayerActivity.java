package kopperkow.soccerstats;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import kopperkow.soccerstats.R;
import kopperkow.soccerstats.model.PlayerContent;

public class AddPlayerActivity extends AppCompatActivity {

    private EditText name, jerseyNum;
    private TextInputLayout nameLayout, jerseyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        name = (EditText) findViewById(R.id.eddittext_name);
        nameLayout = (TextInputLayout) findViewById(R.id.input_layout_name);
        jerseyNum = (EditText) findViewById(R.id.eddittext_player_number);
        jerseyLayout = (TextInputLayout) findViewById(R.id.input_layout_player_number);
    }

    public void onClickAddPlayer(View view) {
        if (name.getText().toString().length() <= 0) {
            nameLayout.setError("Must be filled");
            return;
        }
        if (jerseyNum.getText().toString().length() <= 0) {
            jerseyLayout.setError("Must be filled");
            return;
        }
        PlayerContent.addItem(new PlayerContent.Player(jerseyNum.getText().toString(), name.getText().toString(), PlayerContent.makeDetails(5, jerseyNum.getText().toString()), PlayerContent.getRandomColor()));
        Toast.makeText(this, name.getText().toString() + " succesfully added", Toast.LENGTH_SHORT).show();
        finish();
    }
}
