package com.android.internal.telephony;

/**
 * Created by Jelly on 2017-09-19.
 */

public interface ITelephony {

    boolean endCall();
    void answerRingingCall();
    void silenceRinger();

}
