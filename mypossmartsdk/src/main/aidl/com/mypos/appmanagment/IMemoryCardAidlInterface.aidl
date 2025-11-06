// IMemoryCardAidlInterface.aidl
package com.mypos.appmanagment;

// Declare any non-default types here with import statements

import com.mypos.appmanagment.CardTypeEnum;
import com.mypos.appmanagment.ReadEntity;
import com.mypos.appmanagment.WriteEntity;
import com.mypos.appmanagment.EraseEntity;
import com.mypos.appmanagment.VerifyEntity;
import com.mypos.appmanagment.ReadECEntity;
import com.mypos.appmanagment.UpdateECEntity;

parcelable CardTypeEnum;
parcelable ReadEntity;
parcelable WriteEntity;
parcelable EraseEntity;
parcelable VerifyEntity;
parcelable ReadECEntity;
parcelable UpdateECEntity;

interface IMemoryCardAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int reset(in CardTypeEnum cardType);
    void powerOff();
    byte[] read(in ReadEntity entity);
    int write(in WriteEntity entity);
    int erase(in EraseEntity entity);
    int verify(in VerifyEntity entity);
    int readEC(in ReadECEntity entity);
    int updateEC(in UpdateECEntity entity);
}
