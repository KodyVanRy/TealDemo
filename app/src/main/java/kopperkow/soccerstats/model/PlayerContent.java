package kopperkow.soccerstats.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kopperkow.soccerstats.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class PlayerContent {

    /**
     * An array of sample Player items.
     */
    public static final List<Player> ITEMS = new ArrayList<Player>();

    /**
     * A map of sample Player items, by ID.
     */
    public static final Map<String, Player> ITEM_MAP = new HashMap<String, Player>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyPlayer(i));
        }
    }

    public static void addItem(Player item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Player createDummyPlayer(int position) {
        String randomJersey = ((int) (Math.random() * 100)) + "";
        return new Player(randomJersey, getRandomName(), makeDetails(position, randomJersey), getRandomColor());
    }

    public static int getRandomColor() {
        double rand = Math.random();
        if (rand <= 0.1) {
            return R.color.colorBlack;
        } else if (rand <= 0.2) {
            return R.color.colorGrey;
        } else if (rand <= 0.3) {
            return R.color.colorPink;
        } else if (rand <= 0.4) {
            return R.color.colorBlue;
        } else if (rand <= 0.5) {
            return R.color.colorRed;
        } else if (rand <= 0.6) {
            return R.color.colorPurple;
        } else if (rand <= 0.7) {
            return R.color.colorYello;
        } else if (rand <= 0.8) {
            return R.color.colorOrange;
        } else if (rand <= 0.9) {
            return R.color.colorGreen;
        } else {
            return R.color.colorTeal;
        }
    }

    private static String getRandomName() {
        double rand = Math.random();
        if (rand <= 0.1) {
            return "Jake Sully";
        } else if (rand <= 0.2) {
            return "Neytiri";
        } else if (rand <= 0.3) {
            return "Tsu'Tey";
        } else if (rand <= 0.4) {
            return "Grace";
        } else if (rand <= 0.5) {
            return "Mo'at";
        } else if (rand <= 0.6) {
            return "Eytukan";
        } else if (rand <= 0.7) {
            return "Norm Spellman";
        } else if (rand <= 0.8) {
            return "Na'vi";
        } else if (rand <= 0.9) {
            return "James Cameron";
        } else {
            return "Sam Worthington";
        }
    }

    public static String makeDetails(int position, String jerseyNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("Jersey Number - ").append(jerseyNumber).append("\n\n");
        for (int i = 0; i < position; i++) {
            builder.append(getRandomDetail());
            builder.append("\n");
        }
        return builder.toString();
    }

    private static String getRandomDetail() {
        double rand = Math.random();
        if (rand <= 0.1) {
            return "Has a height of 6'12\"\n";
        } else if (rand <= 0.2) {
            return "Is named after their mom\n";
        } else if (rand <= 0.3) {
            return "Is a cat in hiding\n";
        } else if (rand <= 0.4) {
            return "Is the real cat in the hat\n";
        } else if (rand <= 0.5) {
            return "Is true royalty\n";
        } else if (rand <= 0.6) {
            return "Has an obsession with pizza\n";
        } else if (rand <= 0.7) {
            return "Doesn't know how to spell\n";
        } else if (rand <= 0.8) {
            return "Is really just the favorite of the Na'vi\n";
        } else if (rand <= 0.9) {
            return "Landed the first ROFL copter\n";
        } else {
            return "Still hasn't stopped laughing since milk came out their nose 2 weeks ago.\n";
        }
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Player {
        public final String id;
        public final String content;
        public final String details;
        public final int favColor;
        public final int textColor;

        public Player(String id, String content, String details, int favColor) {
            this.id = id;
            this.content = content;
            this.details = details;
            this.favColor = favColor;
            this.textColor = getTextColorFromFavColor(favColor);
        }

        private static int getTextColorFromFavColor(int favColor) {
            switch (favColor) {
                case R.color.colorPink:
                case R.color.colorOrange:
                case R.color.colorPurple:
                case R.color.colorBlue:
                case R.color.colorRed:
                case R.color.colorGrey:
                case R.color.colorBlack:
                    return R.color.white;
                case R.color.colorGreen:
                case R.color.colorYello:
                case R.color.colorTeal:
                    return R.color.black;
            }
            return R.color.black;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
