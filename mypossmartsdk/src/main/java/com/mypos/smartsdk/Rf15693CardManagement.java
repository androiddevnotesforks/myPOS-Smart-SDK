package com.mypos.smartsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.mypos.appmanagment.IRf15693CardAidlInterface;
import com.mypos.appmanagment.ISystemAidlInterface;
import com.mypos.appmanagment.ReadEntity;

import java.net.BindException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Rf15693CardManagement {

    private static final String SERVICE_ACTION = "com.mypos.service.SYSTEM";
    private static final String SAM_MODULE = "rf15693";

    private IRf15693CardAidlInterface rf15693ManagementService = null;

    private boolean isBound = false;
    private static Rf15693CardManagement instance;

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
                rf15693ManagementService = IRf15693CardAidlInterface.Stub.asInterface(binder);

            isBound = true;

            if (mListener != null)
                mListener.onBindComplete();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            rf15693ManagementService = null;
            isBound = false;
        }
    };

    private Rf15693CardManagement(){

    }

    public static Rf15693CardManagement getInstance() {
        if (instance == null)
            instance = new Rf15693CardManagement();

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
            rf15693ManagementService = null;
            isBound = false;
        }

        mListener = null;
    }

    public boolean open(long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                try {
                    if (this.rf15693ManagementService == null) {
                        continue;
                    }

                    return this.rf15693ManagementService.open();
                } catch (IllegalStateException var18) {
                    continue;
                } catch (RemoteException var19) {
                    RemoteException e = var19;
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var17) {
                        InterruptedException e = var17;
                        e.printStackTrace();
                    }

                }

                return false;
            }

            throw new TimeoutException("Function did not return result in the required time period");
        }
    }

    public void close(long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                try {
                    if (this.rf15693ManagementService == null) {
                        continue;
                    }

                    this.rf15693ManagementService.close();
                    return;
                } catch (IllegalStateException var18) {
                    continue;
                } catch (RemoteException var19) {
                    RemoteException e = var19;
                    e.printStackTrace();
                } finally {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var17) {
                        InterruptedException e = var17;
                        e.printStackTrace();
                    }

                }

                return;
            }

            throw new TimeoutException("Function did not return result in the required time period");
        }
    }

    public boolean isCardExist(ReadEntity readEntity) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            if (this.rf15693ManagementService == null) {
                return false;
            }

            return this.rf15693ManagementService.isCardExist();
        }
    }

    public byte[] getCardSecInfo(byte var1, byte var2) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.getCardSecInfo(var1, var2);
        }
    }

    public int writeSingleBlock(byte var1, byte var2, byte[] var3) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.writeSingleBlock(var1, var2, var3);
        }
    }

    public byte[] readSingleBlock(byte var1, byte var2) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.readSingleBlock(var1, var2);
        }
    }

    public int writeMultiBlock(byte var1, byte var2, byte var3, byte[] var4) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.writeMultiBlock(var1, var2, var3, var4);
        }
    }

    public byte[] readMultiBlock(byte var1, byte var2, byte var3) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.readMultiBlock(var1, var2, var3);
        }
    }

    public int selectTag() throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.selectTag();
        }
    }

    public int keepCalm() throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.keepCalm();
        }
    }

    public int resetReady() throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.resetReady();
        }
    }

    public int lockBlock(byte var1) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.lockBlock(var1);
        }
    }

    public int writeAfi(byte var1) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.writeAfi(var1);
        }
    }

    public int lockAfi() throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.lockAfi();
        }
    }

    public int writeDsfid(byte var1) throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.writeDsfid(var1);
        }
    }

    public int lockDsfid() throws Exception {
        if(!this.isBound) {
            throw new BindException("call .bind(context) first");
        } else {
            return this.rf15693ManagementService.lockDsfid();
        }
    }

    private boolean isServiceExist(Context context) {
        Intent intent = new Intent(SERVICE_ACTION, null);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        List<ResolveInfo> services = context.getPackageManager().queryIntentServices(intent, 0);
        return !services.isEmpty();
    }

    public boolean isSupported() {
        return rf15693ManagementService != null;
    }

}
