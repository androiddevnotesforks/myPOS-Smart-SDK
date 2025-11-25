package com.mypos.smartsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.mypos.appmanagment.ICardEmulationInterface;
import com.mypos.appmanagment.IOnCardEmulationListener;
import com.mypos.appmanagment.ISystemAidlInterface;
import com.mypos.appmanagment.NdefUserMessageRecord;
import com.mypos.appmanagment.NfcTagTypeEnum;

import java.net.BindException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class CardEmulationManagement {

    private static final String SERVICE_ACTION = "com.mypos.service.SYSTEM";
    private static final String SAM_MODULE = "card_emulation";
    private static final long DEFAULT_TIMEOUT = 5000L;

    public static final int NDEF_URI_NONE = 0;
    public static final int NDEF_URI_HTTP_WWW = 1;
    public static final int NDEF_URI_HTTPS_WWW = 2;
    public static final int NDEF_URI_HTTP = 3;
    public static final int NDEF_URI_HTTPS = 4;
    public static final int NDEF_URI_TEL = 5;
    public static final int NDEF_URI_MAILTO = 6;
    public static final int NDEF_URI_FTP_ANONYMOUS = 7;
    public static final int NDEF_URI_FTP_FTP = 8;
    public static final int NDEF_URI_FTPS = 9;
    public static final int NDEF_URI_SFTP = 10;
    public static final int NDEF_URI_SMB = 11;
    public static final int NDEF_URI_NFS = 12;
    public static final int NDEF_URI_FTP = 13;
    public static final int NDEF_URI_DAV = 14;
    public static final int NDEF_URI_NEWS = 15;
    public static final int NDEF_URI_TELNET = 16;
    public static final int NDEF_URI_IMAP = 17;
    public static final int NDEF_URI_RTSP = 18;
    public static final int NDEF_URI_URN = 19;
    public static final int NDEF_URI_POP = 20;
    public static final int NDEF_URI_SIP = 21;
    public static final int NDEF_URI_SIPS = 22;
    public static final int NDEF_URI_TFTP = 23;
    public static final int NDEF_URI_BTSPP = 24;
    public static final int NDEF_URI_BTL2CAP = 25;
    public static final int NDEF_URI_BTGOEP = 26;
    public static final int NDEF_URI_TCPOBEX = 27;
    public static final int NDEF_URI_IRDAOBEX = 28;
    public static final int NDEF_URI_FILE = 29;
    public static final int NDEF_URI_URN_EPC_ID = 30;
    public static final int NDEF_URI_URN_EPC_TAG = 31;
    public static final int NDEF_URI_URN_EPC_PAT = 32;
    public static final int NDEF_URI_URN_EPC_RAW = 33;
    public static final int NDEF_URI_URN_EPC = 34;
    public static final int NDEF_URI_URN_NFC = 35;
    public static final int NDEF_URI_AUTODETECT = 36;

    private ICardEmulationInterface cardEmulationManagementService = null;

    private boolean isBound = false;
    private static CardEmulationManagement instance;

    private OnBindListener mListener = null;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ISystemAidlInterface systemService = ISystemAidlInterface.Stub.asInterface(iBinder);

            IBinder binder = null;
            try {
                binder = systemService.getManager(SAM_MODULE);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            if (binder != null)
                cardEmulationManagementService = ICardEmulationInterface.Stub.asInterface(binder);

            isBound = true;

            if (mListener != null)
                mListener.onBindComplete();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            cardEmulationManagementService = null;
            isBound = false;
        }
    };

    private CardEmulationManagement(){

    }

    public static CardEmulationManagement getInstance() {
        if (instance == null)
            instance = new CardEmulationManagement();

        return instance;
    }

    public void bind(Context context, OnBindListener listener) throws Exception {
        if (!isServiceExist(context))
            throw new Exception("Functionality not supported (probably old version of myPOS OS)");

        if (isBound)
            return;

        mListener = listener;

        Intent intent = new Intent(SERVICE_ACTION);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setPackage("com.mypos");

        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbind(Context context) {
        if (isBound) {
            context.unbindService(serviceConnection);
            cardEmulationManagementService = null;
            isBound = false;
        }

        mListener = null;
    }

    public byte[] getNfcDataWithAmt(NfcTagTypeEnum tagType, String amt, String languageCodeAmt, String activityFullName, String activityData, String packageName) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) first");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < DEFAULT_TIMEOUT)) {
            try {
                if (cardEmulationManagementService != null) {
                    return cardEmulationManagementService.getNfcDataWithAmt(tagType.getValue(), amt, languageCodeAmt, activityFullName, activityData, packageName);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }

    public byte[] getNfcDataWithText(NfcTagTypeEnum tagType, String languageCode, String text) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) first");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < DEFAULT_TIMEOUT)) {
            try {
                if (cardEmulationManagementService != null) {
                    return cardEmulationManagementService.getNfcDataWithText(tagType.getValue(), languageCode, text);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }

    public byte[] getNfcDataWithUri(NfcTagTypeEnum tagType, int uriProtocols, String uri) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) first");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < DEFAULT_TIMEOUT)) {
            try {
                if (cardEmulationManagementService != null) {
                    return cardEmulationManagementService.getNfcDataWithUri(tagType.getValue(), uriProtocols, uri);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }

    public byte[] getNfcDataWithUserMessage(NfcTagTypeEnum tagType, NdefUserMessageRecord ndefUserMessageRecord) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) first");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < DEFAULT_TIMEOUT)) {
            try {
                if (cardEmulationManagementService != null) {
                    return cardEmulationManagementService.getNfcDataWithUserMessage(tagType.getValue(), ndefUserMessageRecord);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }

    public int openNfc(NfcTagTypeEnum tagType, byte[] nfcData, int timeout, OnCardEmulationListener listener) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) first");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < DEFAULT_TIMEOUT)) {
            try {
                if (cardEmulationManagementService != null) {
                    return cardEmulationManagementService.openNfc(tagType.getValue(), nfcData, timeout, new IOnCardEmulationListener.Stub() {
                        @Override
                        public void onProcessResult(int retCode, String msg) throws RemoteException {
                            if (listener != null) {
                                listener.onProcessResult(retCode, msg);
                            }
                        }
                    });
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }

    public void closeNfc() throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) first");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < DEFAULT_TIMEOUT)) {
            try {
                if (cardEmulationManagementService != null) {
                    cardEmulationManagementService.closeNfc();
                    return;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }

    public int setUid(NfcTagTypeEnum tagType, byte[] uid) throws Exception {
        if (!isBound) {
            throw new BindException("call .bind(context) first");
        }

        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime < DEFAULT_TIMEOUT)) {
            try {
                if (cardEmulationManagementService != null) {
                    return cardEmulationManagementService.setUid(tagType.getValue(), uid);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        throw new TimeoutException("Function did not return result in the required time period");
    }

    private boolean isServiceExist(Context context) {
        Intent intent = new Intent(SERVICE_ACTION, null);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        List<ResolveInfo> services = context.getPackageManager().queryIntentServices(intent, 0);
        return !services.isEmpty();
    }

    public boolean isSupported() {
        return cardEmulationManagementService != null;
    }

    public interface OnCardEmulationListener {
        void onProcessResult(int retCode, String msg);
    }
}
