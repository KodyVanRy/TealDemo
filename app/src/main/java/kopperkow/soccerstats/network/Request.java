package kopperkow.soccerstats.network;

import android.os.Handler;
import android.util.Log;

import com.towdvi.towdvi.MyApplication;
import com.towdvi.towdvi.utils.PreferenceUtil;
import com.towdvi.towdvi.utils.Util;
import com.towdvi.towdvi.view.GetImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dakota Van Ry on 10/18/2016. Use it
 */

public class Request {
    public static final int ADD_SERVICE = 0;
    public static final int ADD_PRETRIP_INSPECTION = 1;
    public static final int END_SHIFT = 2;
    public static final int GET_CALL_TYPES = 3;
    public static final int DROP_OFF_VEHICLE = 4;
    public static final int LOAD_VEHICLE = 5;
    public static final int LOGIN = 6;
    public static final int GET_DRIVER = 7;
    public static final int GET_PTI_ITEMS = 8;
    public static final int CREATE_PTI = 9;
    public static final int UPDATE_PTI = 10;
    public static final int CREATE_TOW = 11;
    public static final int CREATE_SERVICE = 12;
    public static final int LOAD_TOW = 13;
    public static final int AFTER_SERVICE = 14;
    public static final int DROP_OFF_TOW = 15;
    public static final int GET_PTI_HISTORY = 16;
    public static final int GET_TRUCK_NUMBERS = 17;
    private static final String _log = "Request";
    boolean converted;
    private String url, photosString;
    private JSONObject json;
    private ArrayList<GetImageView> imageViews;
    private int requestType;
    private Handler onFinishHandler;
    private Handler errorHandler;

    public Request(String url, JSONObject jsonObject, String photosString, ArrayList<GetImageView> imageViews, int requestType) {
        this.url = url;
        this.photosString = photosString;
        this.imageViews = imageViews;
        this.json = jsonObject;
        this.requestType = requestType;
        this.converted = false;
    }

    public Request(String url, JSONObject jsonObject, String photosString, ArrayList<GetImageView> imageViews, int requestType, Handler onFinishHandler) {
        this.url = url;
        this.photosString = photosString;
        this.imageViews = imageViews;
        this.json = jsonObject;
        this.requestType = requestType;
        this.converted = false;
        this.onFinishHandler = onFinishHandler;
    }

    public Request(String url, JSONObject jsonObject, String photosString, ArrayList<GetImageView> imageViews, int requestType, Handler onFinishHandler, Handler errorHandler) {
        this.url = url;
        this.photosString = photosString;
        this.imageViews = imageViews;
        this.json = jsonObject;
        this.requestType = requestType;
        this.converted = false;
        this.onFinishHandler = onFinishHandler;
        this.errorHandler = errorHandler;
    }

    public Request(JSONObject jsonObject) throws JSONException {
        if (jsonObject.has("url") && !jsonObject.isNull("url"))
            this.url = jsonObject.getString("url");
        if (jsonObject.has("request_type") && !jsonObject.isNull("request_type"))
            this.requestType = jsonObject.getInt("request_type");
        if (jsonObject.has("json") && !jsonObject.isNull("json"))
            this.json = jsonObject.getJSONObject("json");
        this.converted = true;
    }

    public String getCallNumber() {
        try {
            return json.getString("call_number");
        } catch (Exception e) {
            return "";
        }
    }

    String getUrl() {
        if (requestType == Request.UPDATE_PTI)
            return url + PreferenceUtil.getPtiId();
        if (requestType == Request.LOAD_TOW || requestType == Request.AFTER_SERVICE)
            return url + PreferenceUtil.getPendingLoadId();
        return url;
    }

    public void setUrl(String url) {
        Log.d(_log, "Before - " + this.url);
        this.url = url;
        Log.d(_log, "After - " + this.url);
    }

    Handler getOnFinishHandler() {
        return onFinishHandler;
    }

    Handler getErrorHandler() {
        return errorHandler;
    }

    void convert() {
        if (requestType == CREATE_TOW ||
                requestType == AFTER_SERVICE ||
                requestType == CREATE_SERVICE ||
                requestType == LOAD_TOW ||
                requestType == DROP_OFF_TOW) {
            try {
                json.put(photosString, getPhotosJson());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        converted = true;

    }

    public JSONObject getJson() {
        if (!converted) {
            convert();
        }
        if (json == null) {
            return new JSONObject();
        }
        return json;
    }

    private JSONObject getPhotosJson() {
        JSONObject j = new JSONObject();
        try {
            if (imageViews != null) {
                for (GetImageView getImageView : imageViews) {
                    if (getImageView.getSavedBitmapLocation() != null) {
                        Log.d(_log, "Adding " + getPrefix(getImageView) + getImageView.getJsonTitle() + " to the JSON Object");
                        j.put(getPrefix(getImageView) + getImageView.getJsonTitle(), Util.buildPhotoData(getImageView.getSavedBitmap(MyApplication.getContext())));
                        Util.deleteFile(getImageView.getSavedBitmapLocation());
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }

    private String getPrefix(GetImageView imageView) {
        if (requestType == CREATE_TOW && !imageView.getJsonTitle().startsWith("id")) {
            return "pickup-";
        } else if (requestType == LOAD_TOW && !imageView.getJsonTitle().startsWith("id")) {
            return "load-";
        } else if (requestType == DROP_OFF_TOW && !imageView.getJsonTitle().startsWith("id")) {
            return "drop-off-";
        }
        return "";
    }

    public JSONObject toJson() {
        JSONObject returnJson = new JSONObject();
        try {
            returnJson.put("url", url);
            returnJson.put("request_type", requestType);
            if (json != null)
                returnJson.put("json", json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnJson;
    }

    int getRequestType() {
        return requestType;
    }
}
