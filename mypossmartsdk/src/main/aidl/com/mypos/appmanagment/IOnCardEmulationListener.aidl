// IGpioAidlInterface.aidl
package com.mypos.appmanagment;

import com.mypos.appmanagment.IOnCardEmulationListener;

// Declare any non-default types here with import statements

interface IOnCardEmulationListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     void onProcessResult(int retCode, String msg);

}
