package kopperkow.soccerstats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kopperkow.soccerstats.view.SoccerPlayDrawingView;

public class DrawingActivity extends AppCompatActivity {

    SoccerPlayDrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        drawingView = (SoccerPlayDrawingView) findViewById(R.id.drawing_view);
    }

    public void onClickClear(View view) {
        drawingView.clear();
    }
}
