package com.mypos.appmanagment;

import android.os.Parcel;
import android.os.Parcelable;

public class EraseEntity implements Parcelable {
    private CardTypeEnum cardType;
    private int address;
    private int eraseLen;
    private int zone;

    public EraseEntity() {
    }

    public EraseEntity(CardTypeEnum cardType, int address, int eraseLen, int zone) {
        this.cardType = cardType;
        this.address = address;
        this.eraseLen = eraseLen;
        this.zone = zone;
    }

    protected EraseEntity(Parcel in) {
        cardType = in.readParcelable(CardTypeEnum.class.getClassLoader());
        address = in.readInt();
        eraseLen = in.readInt();
        zone = in.readInt();
    }

    public static final Creator<EraseEntity> CREATOR = new Creator<EraseEntity>() {
        @Override
        public EraseEntity createFromParcel(Parcel in) {
            return new EraseEntity(in);
        }

        @Override
        public EraseEntity[] newArray(int size) {
            return new EraseEntity[size];
        }
    };

    public CardTypeEnum getCardType() {
        return this.cardType;
    }

    public void setCardType(CardTypeEnum cardType) {
        this.cardType = cardType;
    }

    public int getAddress() {
        return this.address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getEraseLen() {
        return this.eraseLen;
    }

    public void setEraseLen(int eraseLen) {
        this.eraseLen = eraseLen;
    }

    public int getZone() {
        return this.zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(cardType, i);
        parcel.writeInt(address);
        parcel.writeInt(eraseLen);
        parcel.writeInt(zone);
    }
}
