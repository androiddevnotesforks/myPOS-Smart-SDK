// IUltralightAidlInterface.aidl
package com.mypos.appmanagment;

// Declare any non-default types here with import statements

interface INTagAidlInterface {
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
    byte[] readSIG();
    byte[] exchangeCmd(in byte[] var1);
}
