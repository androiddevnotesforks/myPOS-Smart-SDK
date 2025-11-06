package com.mypos.smartsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.mypos.appmanagment.INTagAidlInterface;
import com.mypos.appmanagment.ISystemAidlInterface;

import java.net.BindException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class NTagManagement {

    private static final String SERVICE_ACTION = "com.mypos.service.SYSTEM";
    private static final String SAM_MODULE = "ntag";

    private INTagAidlInterface ntagManagementService = null;

    private boolean isBound = false;
    private static NTagManagement instance;

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
                ntagManagementService = INTagAidlInterface.Stub.asInterface(binder);

            isBound = true;

            if (mListener != null)
                mListener.onBindComplete();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            ntagManagementService = null;
            isBound = false;
        }
    };

    private NTagManagement(){

    }

    public static NTagManagement getInstance() {
        if (instance == null)
            instance = new NTagManagement();

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
            ntagManagementService = null;
            isBound = false;
        }

        mListener = null;
    }


    public void open(long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                try {
                    if (this.ntagManagementService == null) {
                        continue;
                    }

                    this.ntagManagementService.open();
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

    public void close(long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                try {
                    if (this.ntagManagementService == null) {
                        continue;
                    }

                    this.ntagManagementService.close();
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

    public byte[] getCardVersion() throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            return this.ntagManagementService.getCardVersion();
        }
    }

    public byte[] exchangeCmd(byte[] data, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                Object var7;
                try {
                    if (this.ntagManagementService == null) {
                        continue;
                    }

                    byte[] exchangeCmd = this.ntagManagementService.exchangeCmd(data);
                    byte[] var24 = exchangeCmd;
                    return var24;
                } catch (IllegalStateException var20) {
                    continue;
                } catch (RemoteException var21) {
                    RemoteException e = var21;
                    e.printStackTrace();
                    var7 = null;
                } finally {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var19) {
                        InterruptedException e = var19;
                        e.printStackTrace();
                    }

                }

                return (byte[])var7;
            }

            throw new TimeoutException("Function did not return result in the required time period");
        }
    }

    public int authority(byte[] data, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                int var7;
                try {
                    if (this.ntagManagementService == null) {
                        continue;
                    }

                    int authority = this.ntagManagementService.authority(data);
                    var7 = authority;
                    return var7;
                } catch (IllegalStateException var20) {
                    continue;
                } catch (RemoteException var21) {
                    RemoteException e = var21;
                    e.printStackTrace();
                    var7 = -2;
                } finally {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var19) {
                        InterruptedException e = var19;
                        e.printStackTrace();
                    }

                }

                return var7;
            }

            throw new TimeoutException("Function did not return result in the required time period");
        }
    }

    public byte[] read(byte block, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                Object var7;
                try {
                    if (this.ntagManagementService == null) {
                        continue;
                    }

                    byte[] readBlock = this.ntagManagementService.read(block);
                    byte[] var24 = readBlock;
                    return var24;
                } catch (IllegalStateException var20) {
                    continue;
                } catch (RemoteException var21) {
                    RemoteException e = var21;
                    e.printStackTrace();
                    var7 = null;
                } finally {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var19) {
                        InterruptedException e = var19;
                        e.printStackTrace();
                    }

                }

                return (byte[])var7;
            }

            throw new TimeoutException("Function did not return result in the required time period");
        }
    }


    public byte[] fastRead(byte block, byte readTo, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                Object var7;
                try {
                    if (this.ntagManagementService == null) {
                        continue;
                    }

                    byte[] readBlock = this.ntagManagementService.fastRead(block, readTo);
                    byte[] var24 = readBlock;
                    return var24;
                } catch (IllegalStateException var20) {
                    continue;
                } catch (RemoteException var21) {
                    RemoteException e = var21;
                    e.printStackTrace();
                    var7 = null;
                } finally {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var19) {
                        InterruptedException e = var19;
                        e.printStackTrace();
                    }

                }

                return (byte[])var7;
            }

            throw new TimeoutException("Function did not return result in the required time period");
        }
    }

    public int write(byte block, byte[] data, boolean flag, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                int var8;
                try {
                    if (this.ntagManagementService == null) {
                        continue;
                    }

                    int writeBlock = this.ntagManagementService.write(block, data, flag);
                    var8 = writeBlock;
                    return var8;
                } catch (IllegalStateException var21) {
                    continue;
                } catch (RemoteException var22) {
                    RemoteException e = var22;
                    e.printStackTrace();
                    var8 = -2;
                } finally {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var20) {
                        InterruptedException e = var20;
                        e.printStackTrace();
                    }

                }

                return var8;
            }

            throw new TimeoutException("Function did not return result in the required time period");
        }
    }


    public byte[] readCNT(byte cnt, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                Object var7;
                try {
                    if (this.ntagManagementService == null) {
                        continue;
                    }

                    byte[] readBlock = this.ntagManagementService.readCNT(cnt);
                    byte[] var24 = readBlock;
                    return var24;
                } catch (IllegalStateException var20) {
                    continue;
                } catch (RemoteException var21) {
                    RemoteException e = var21;
                    e.printStackTrace();
                    var7 = null;
                } finally {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var19) {
                        InterruptedException e = var19;
                        e.printStackTrace();
                    }

                }

                return (byte[])var7;
            }

            throw new TimeoutException("Function did not return result in the required time period");
        }
    }


    public byte[] readSIG(long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                Object var7;
                try {
                    if (this.ntagManagementService == null) {
                        continue;
                    }

                    byte[] readBlock = this.ntagManagementService.readSIG();
                    byte[] var24 = readBlock;
                    return var24;
                } catch (IllegalStateException var20) {
                    continue;
                } catch (RemoteException var21) {
                    RemoteException e = var21;
                    e.printStackTrace();
                    var7 = null;
                } finally {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var19) {
                        InterruptedException e = var19;
                        e.printStackTrace();
                    }

                }

                return (byte[])var7;
            }

            throw new TimeoutException("Function did not return result in the required time period");
        }
    }

    private boolean isServiceExist(Context context) {
        Intent intent = new Intent(SERVICE_ACTION, null);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        List<ResolveInfo> services = context.getPackageManager().queryIntentServices(intent, 0);
        return !services.isEmpty();
    }

    public boolean isSupported() {
        return ntagManagementService != null;
    }

}
