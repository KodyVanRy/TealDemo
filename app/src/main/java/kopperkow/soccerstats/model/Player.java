package kopperkow.soccerstats.model;

import android.graphics.Color;

/**
 * Created by Dakota Van Ry on 11/15/2016. Use it
 */

public class Player {

    private String name;
    private int number;
    private Color favoriteColor;

    public Player(String name, int number, Color favoriteColor) {
        this.name = name;
        this.number = number;
        this.favoriteColor = favoriteColor;
    }

    public Color getFavoriteColor() {
        return favoriteColor;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}
