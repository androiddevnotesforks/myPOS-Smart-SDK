// IRf15693CardAidlInterface.aidl
package com.mypos.appmanagment;

// Declare any non-default types here with import statements
import com.mypos.appmanagment.Rf15693CardEntity;

parcelable Rf15693CardEntity;

interface IRf15693CardAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    boolean open();
    void close();
    boolean isCardExist();
    Rf15693CardEntity getCardInfo();
    byte[] getCardSecInfo(in byte var1, in byte var2);
    int writeSingleBlock(in byte var1, in byte var2, out byte[] var3);
    byte[] readSingleBlock(in byte var1, in byte var2);
    int writeMultiBlock(in byte var1, in byte var2, in byte var3, out byte[] var4);
    byte[] readMultiBlock(in byte var1, in byte var2, in byte var3);
    int selectTag();
    int keepCalm();
    int resetReady();
    int lockBlock(in byte var1);
    int writeAfi(in byte var1);
    int lockAfi();
    int writeDsfid(in byte var1);
    int lockDsfid();
}