package com.mypos.smartsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.mypos.appmanagment.CardTypeEnum;
import com.mypos.appmanagment.EraseEntity;
import com.mypos.appmanagment.IMemoryCardAidlInterface;
import com.mypos.appmanagment.ISystemAidlInterface;
import com.mypos.appmanagment.ReadECEntity;
import com.mypos.appmanagment.ReadEntity;
import com.mypos.appmanagment.UpdateECEntity;
import com.mypos.appmanagment.VerifyEntity;
import com.mypos.appmanagment.WriteEntity;

import java.net.BindException;
import java.util.List;

public class MemoryCardManagement {

    private static final String SERVICE_ACTION = "com.mypos.service.SYSTEM";
    private static final String SAM_MODULE = "memory_card";

    private IMemoryCardAidlInterface memoryCardManagementService = null;

    private boolean isBound = false;
    private static MemoryCardManagement instance;

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
                memoryCardManagementService = IMemoryCardAidlInterface.Stub.asInterface(binder);

            isBound = true;

            if (mListener != null)
                mListener.onBindComplete();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            memoryCardManagementService = null;
            isBound = false;
        }
    };

    private MemoryCardManagement(){

    }

    public static MemoryCardManagement getInstance() {
        if (instance == null)
            instance = new MemoryCardManagement();

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
            memoryCardManagementService = null;
            isBound = false;
        }

        mListener = null;
    }


    public int reset(CardTypeEnum cardType) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            if (this.memoryCardManagementService == null) {
                return -1;
            }

            return this.memoryCardManagementService.reset(cardType);
        }
    }

    public void powerOff() throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            if (this.memoryCardManagementService == null) {
                return;
            }

            this.memoryCardManagementService.powerOff();
        }
    }

    public byte[] read(ReadEntity readEntity) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            if (this.memoryCardManagementService == null) {
                return null;
            }

            return this.memoryCardManagementService.read(readEntity);
        }
    }

    public int write(WriteEntity writeEntity) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            if (this.memoryCardManagementService == null) {
                return -1;
            }

            return this.memoryCardManagementService.write(writeEntity);
        }
    }

    public int erase(EraseEntity eraseEntity) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            if (this.memoryCardManagementService == null) {
                return -1;
            }

            return this.memoryCardManagementService.erase(eraseEntity);
        }
    }

    public int erase(VerifyEntity verifyEntity) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            if (this.memoryCardManagementService == null) {
                return -1;
            }

            return this.memoryCardManagementService.verify(verifyEntity);
        }
    }

    public int readEC(ReadECEntity readECEntity) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            if (this.memoryCardManagementService == null) {
                return -1;
            }

            return this.memoryCardManagementService.readEC(readECEntity);
        }
    }


    public int updateEC(UpdateECEntity updateECEntity) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            if (this.memoryCardManagementService == null) {
                return -1;
            }

            return this.memoryCardManagementService.updateEC(updateECEntity);
        }
    }

    private boolean isServiceExist(Context context) {
        Intent intent = new Intent(SERVICE_ACTION, null);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        List<ResolveInfo> services = context.getPackageManager().queryIntentServices(intent, 0);
        return !services.isEmpty();
    }

    public boolean isSupported() {
        return memoryCardManagementService != null;
    }

}
