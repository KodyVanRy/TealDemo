package kopperkow.soccerstats.model;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Dakota Van Ry on 11/15/2016. Use it
 */

public class MyApplication extends Application {

    private ArrayList<Player> players;

    @Override
    public void onCreate() {
        super.onCreate();
        players = new ArrayList<>();
    }

    void addPlayer(Player player) {
        players.add(player);
    }

    ArrayList<Player> getPlayers() {
        return players;
    }
}
