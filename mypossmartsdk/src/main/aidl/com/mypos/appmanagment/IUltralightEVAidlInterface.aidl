// IUltralightAidlInterface.aidl
package com.mypos.appmanagment;

// Declare any non-default types here with import statements

interface IUltralightEVAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void open();
    void close();
    byte[] getCardVersion();
    int authority(in byte[] var1);
    byte[] read(byte var1);
    byte[] fastRead(byte var1, byte var2);
    int write(byte var1, in byte[] var2, boolean var3);
    byte[] readCNT(byte var1);
    int increaseCNT(byte var1, out byte[] var2);
    int checkTearingEvent(byte var1);
    byte[] exchangeCmd(in byte[] var1);
}
