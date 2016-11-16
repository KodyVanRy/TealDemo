package kopperkow.soccerstats.view;

import kopperkow.soccerstats.R;

/**
 * Created by Dakota Van Ry on 11/15/2016. Use it
 */

public class DrawableUtil {

    public static int getDrawableIdFromUrlString(String url) {
        if (url.contains("chanceflurries")) {
            return R.drawable.weather_snow;
        } else if (url.contains("snow")) {
            return R.drawable.weather_big_snow;
        } else if (url.contains("sleet")) {
            return R.drawable.weather_hail;
        } else if (url.contains("rain")) {
            return R.drawable.weather_rain_day;
        } else if (url.contains("tstorms")) {
            return R.drawable.weather_storm;
        } else if (url.contains("cloudy")) {
            return R.drawable.weather_clouds;
        } else if (url.contains("flurries")) {
            return R.drawable.weather_snow;
        } else if (url.contains("fog")) {
            return R.drawable.weather_haze;
        } else if (url.contains("hazy")) {
            return R.drawable.weather_haze;
        } else if (url.contains("sunny")) {
            return R.drawable.weather_clear;
        } else if (url.contains("clear")) {
            return R.drawable.weather_clear;
        }
        return R.drawable.weather_none_available;
    }
}
