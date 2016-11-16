package kopperkow.soccerstats.network;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.towdvi.towdvi.jsonobjects.CallTypes;
import com.towdvi.towdvi.jsonobjects.Driver;
import com.towdvi.towdvi.jsonobjects.InspectionHistory;
import com.towdvi.towdvi.jsonobjects.NeededDropOff;
import com.towdvi.towdvi.utils.PreferenceUtil;
import com.towdvi.towdvi.utils.ThreadUtil;
import com.towdvi.towdvi.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by Dakota Van Ry on 10/18/2016. Use it
 */

public class JSONRequest {
    public static final int OK = 200;
    public static final int GOOD = 201;
    public static final int BAD_REQUEST = 400;
    public static final int NO_INTERNET = 503;
    public static final int SERVER_ERROR = 500;
    private static final String _log = "JSONRequest";

    static int get(Request request) {
        try {
            HttpURLConnection urlConn;
            urlConn = (HttpURLConnection) new URL(request.getUrl()).openConnection();
            urlConn.setDoInput(true);
            urlConn.setUseCaches(false); // Ensure we get fresh new data
            urlConn.setRequestMethod("GET");
            // Send POST output.

            int HttpResult = urlConn.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConn.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
                br.close();

                convertAndUseJson(sb.toString(), request);

            } else {
                System.out.println(urlConn.getResponseMessage());
            }
            return HttpResult;
        } catch (UnknownHostException e) {
            return NO_INTERNET;
        } catch (IOException e) {
            e.printStackTrace();
            return SERVER_ERROR;
        }
    }

    private static void convertAndUseJson(String s, Request request) {
    }
}
