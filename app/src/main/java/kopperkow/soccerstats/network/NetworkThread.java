package kopperkow.soccerstats.network;

import android.util.Log;

import com.towdvi.towdvi.jsonobjects.PendingHistoryLog;
import com.towdvi.towdvi.jsonobjects.PendingNeededDropOff;
import com.towdvi.towdvi.utils.PreferenceUtil;
import com.towdvi.towdvi.utils.ThreadUtil;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Dakota Van Ry on 10/18/2016. Use it
 */

public class NetworkThread implements Runnable {

    private static final String _log = "NetworkThread";
    AtomicBoolean running = new AtomicBoolean(true);

    @Override
    public void run() {
        while (running.get()) {
            if (ThreadUtil.getPendingHistoryLogs().size() > 0) {
                for (PendingHistoryLog pendingHistoryLog : ThreadUtil.getPendingHistoryLogs()) {
                    pendingHistoryLog.save();
                }
                ThreadUtil.getPendingHistoryLogs().clear();
            }
            if (ThreadUtil.getPendingNeededDropOffs().size() > 0) {
                for (PendingNeededDropOff pendingNeededDropOff : ThreadUtil.getPendingNeededDropOffs()) {
                    pendingNeededDropOff.save();
                }
                ThreadUtil.getPendingNeededDropOffs().clear();
            }
            if (ThreadUtil.pendingRequests == null) {
                ThreadUtil.loadPendingRequests();
            } else if (ThreadUtil.pendingRequests.size() >= 1) {
                for (Request request : ThreadUtil.pendingRequests) {
                    if (!request.converted) {
                        request.convert();
                        PreferenceUtil.savePendingRequests(ThreadUtil.pendingRequests);
                    }
                }
                Request pendingRequest = ThreadUtil.pendingRequests.get(0);
                int result = 0;
                if (pendingRequest.getRequestType() == Request.ADD_SERVICE ||
                        pendingRequest.getRequestType() == Request.ADD_PRETRIP_INSPECTION ||
                        pendingRequest.getRequestType() == Request.LOGIN ||
                        pendingRequest.getRequestType() == Request.CREATE_PTI ||
                        pendingRequest.getRequestType() == Request.CREATE_TOW ||
                        pendingRequest.getRequestType() == Request.CREATE_SERVICE) {
                    result = JSONRequest.post(pendingRequest);
                } else if (pendingRequest.getRequestType() == Request.LOAD_VEHICLE ||
                        pendingRequest.getRequestType() == Request.DROP_OFF_VEHICLE ||
                        pendingRequest.getRequestType() == Request.END_SHIFT) {
                    result = JSONRequest.patch(pendingRequest);
                } else if (pendingRequest.getRequestType() == Request.GET_CALL_TYPES ||
                        pendingRequest.getRequestType() == Request.GET_DRIVER ||
                        pendingRequest.getRequestType() == Request.GET_TRUCK_NUMBERS ||
                        pendingRequest.getRequestType() == Request.GET_PTI_ITEMS ||
                        pendingRequest.getRequestType() == Request.GET_PTI_HISTORY) {
                    result = JSONRequest.get(pendingRequest);
                } else if (pendingRequest.getRequestType() == Request.UPDATE_PTI ||
                        pendingRequest.getRequestType() == Request.LOAD_TOW ||
                        pendingRequest.getRequestType() == Request.AFTER_SERVICE ||
                        pendingRequest.getRequestType() == Request.DROP_OFF_TOW) {
                    result = JSONRequest.put(pendingRequest);
                }
                if (result == JSONRequest.OK || result == JSONRequest.BAD_REQUEST || pendingRequest.getErrorHandler() != null) {
                    ThreadUtil.pendingRequests.remove(0);
                    PreferenceUtil.savePendingRequests(ThreadUtil.pendingRequests);
                } else if (result == JSONRequest.NO_INTERNET) {
                    sleep(5000);
                } else {
                    ThreadUtil.pendingRequests.remove(0);
                    PreferenceUtil.savePendingRequests(ThreadUtil.pendingRequests);
                }
                Log.d(_log, "Result = " + result);
            } else {
                sleep(2000);
            }
        }
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
