package com.mypos.smartsdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.mypos.appmanagment.IM1PlusAidlInterface;
import com.mypos.appmanagment.ISystemAidlInterface;

import java.net.BindException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class M1PlusManagement {

    private static final String SERVICE_ACTION = "com.mypos.service.SYSTEM";
    private static final String SAM_MODULE = "m1_plus";

    private IM1PlusAidlInterface m1ManagementService = null;

    private boolean isBound = false;
    private static M1PlusManagement instance;

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
                m1ManagementService = IM1PlusAidlInterface.Stub.asInterface(binder);

            isBound = true;

            if (mListener != null)
                mListener.onBindComplete();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            m1ManagementService = null;
            isBound = false;
        }
    };

    private M1PlusManagement(){

    }

    public static M1PlusManagement getInstance() {
        if (instance == null)
            instance = new M1PlusManagement();

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
            m1ManagementService = null;
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
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    this.m1ManagementService.open();
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
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    this.m1ManagementService.close();
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

    public boolean active(long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                boolean var6;
                try {
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    boolean active = this.m1ManagementService.active();
                    var6 = active;
                    return var6;
                } catch (IllegalStateException var19) {
                    continue;
                } catch (RemoteException var20) {
                    RemoteException e = var20;
                    e.printStackTrace();
                    var6 = false;
                } finally {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException var18) {
                        InterruptedException e = var18;
                        e.printStackTrace();
                    }

                }

                return var6;
            }

            throw new TimeoutException("Function did not return result in the required time period");
        }
    }

    public int MPInit(int var1, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                int var8;
                try {
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    int ret = this.m1ManagementService.MPInit(var1);
                    var8 = ret;
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

    public byte[] MPRead(int var1, int var2, int var3, int var4, int var5, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                byte[] var8;
                try {
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    byte[] ret = this.m1ManagementService.MPRead(var1, var2, var3, var4, var5);
                    var8 = ret;
                    return var8;
                } catch (IllegalStateException var21) {
                    continue;
                } catch (RemoteException var22) {
                    RemoteException e = var22;
                    e.printStackTrace();
                    var8 = null;
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

    public int MPWrite(int var1, int var2, int var3, int var4, byte[] result, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                int var8;
                try {
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    int writeBlock = this.m1ManagementService.MPWrite(var1, var2, var3, var4, result);
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

    public int MPIncValue(short var1, int var2, int var3, int var4, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                int var8;
                try {
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    int ret = this.m1ManagementService.MPIncValue(var1, var2, var3, var4);
                    var8 = ret;
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

    public int MPDecValue(short var1, int var2, int var3, int var4, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                int var8;
                try {
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    int writeBlock = this.m1ManagementService.MPDecValue(var1, var2, var3, var4);
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

    public int MPWriteValue(short var1, int var2, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                int var8;
                try {
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    int value = this.m1ManagementService.MPWriteValue(var1, var2);
                    var8 = value;
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


    public int MPTransfer(short var1, int var2, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                int var8;
                try {
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    int value = this.m1ManagementService.MPTransfer(var1, var2);
                    var8 = value;
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


    public int MPReadValue(short var1, long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                int var8;
                try {
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    int value = this.m1ManagementService.MPReadValue(var1);
                    var8 = value;
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


    public int MPDeselect(long timeOut) throws Exception {
        if (!this.isBound) {
            throw new BindException("call .bind(context) fist");
        } else {
            long startTime = System.currentTimeMillis();

            while(System.currentTimeMillis() - startTime < timeOut) {
                int var8;
                try {
                    if (this.m1ManagementService == null) {
                        continue;
                    }

                    int value = this.m1ManagementService.MPDeselect();
                    var8 = value;
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

    private boolean isServiceExist(Context context) {
        Intent intent = new Intent(SERVICE_ACTION, null);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        List<ResolveInfo> services = context.getPackageManager().queryIntentServices(intent, 0);
        return !services.isEmpty();
    }

    public boolean isSupported() {
        return m1ManagementService != null;
    }

}
