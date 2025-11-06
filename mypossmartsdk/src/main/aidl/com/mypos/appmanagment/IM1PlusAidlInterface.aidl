// IM1AidlInterface.aidl
package com.mypos.appmanagment;

import com.mypos.appmanagment.AuthEntity;

// Declare any non-default types here with import statements

interface IM1PlusAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void open();
    void close();
    boolean active();
    int MPInit(int var1);
    byte[] MPGetVersion();
    byte[] MPGetOriginalitySig();
    int MPAuthFirst(int var1, in byte[] var2, int var3);
    int MPAuthNonFirst(int var1, in byte[] var2);
    int MPResetAuth();
    int MPWritePersonal(int var1, in byte[] var2);
    int MPCommitPersonal(int var1);
    byte[] MPRead(int var1, int var2, int var3, int var4, int var5);
    int MPWrite(int var1, int var2, int var3, int var4,out byte[] var5);
    int MPIncValue(int var1, int var2, int var3, int var4);
    int MPDecValue(int var1, int var2, int var3, int var4);
    int MPTransfer(int var1, int var2);
    int MPWriteValue(int var1, int var2);
    int MPReadValue(int var1);
    int MPVcSupportLast(in byte[] var1, in byte[] var2, char var3, in byte[] var4, in byte[] var5, in byte[] var6);
    int MPDeselect();
}
