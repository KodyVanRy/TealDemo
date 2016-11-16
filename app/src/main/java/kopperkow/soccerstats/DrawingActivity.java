package kopperkow.soccerstats;

import android.app.Activity;
import android.os.Bundle;

import kopperkow.soccerstats.R;
import kopperkow.soccerstats.view.SoccerPlayDrawingView;

public class DrawingActivity extends Activity {

    SoccerPlayDrawingView drawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);
        drawingView = (SoccerPlayDrawingView) findViewById(R.id.drawing_view);
    }
}
