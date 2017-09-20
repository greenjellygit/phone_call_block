package com.greenjelly.call_block;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;

/**
 * Created by Jelly on 2017-09-19.
 */
public class IncomingCallEventHandler extends BroadcastReceiver {

    private LinkedHashSet<String> numberSet = new LinkedHashSet<>();

    @Override
    public void onReceive(Context context, Intent intent) {
        android.os.Debug.waitForDebugger();
        //Check if this is an incoming call event
        if (!intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            return;
        } else {
            //store incoming call number for sending text messages later
            numberSet.add(intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
            disconnectPhoneItelephony(context);
        }
    }

    //rejecting incoming call
    private void disconnectPhoneItelephony(Context context) {
        ITelephony telephonyService;
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c = Class.forName(telephony.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(telephony);
            telephonyService.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
