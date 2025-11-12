// IAppManagmentAidlInterface.aidl
package com.mypos.appmanagment;

import com.mypos.appmanagment.IOnCardEmulationListener;
import com.mypos.appmanagment.NdefUserMessageRecord;

interface ICardEmulationInterface {
    byte[] getNfcDataWithAmt(int tagType, String amt, String languageCodeAmt, String activityFullName, String activityData, String packageName);
    byte[] getNfcDataWithText(int tagType, String languageCode, String text);
    byte[] getNfcDataWithUri(int tagType, int uriProtocols, String uri);
    byte[] getNfcDataWithUserMessage(int tagType, in NdefUserMessageRecord ndefUserMessageRecord);
    int openNfc(int tagType, in byte[] nfcData, int timeout, IOnCardEmulationListener listener);
    void closeNfc();
    int setUid(int tagType, in byte[] uid);
}
