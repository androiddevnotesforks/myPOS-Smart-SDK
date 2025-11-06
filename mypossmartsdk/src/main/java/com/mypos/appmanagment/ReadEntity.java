package com.mypos.appmanagment;

import android.os.Parcel;
import android.os.Parcelable;

public class ReadEntity implements Parcelable {
    private CardTypeEnum cardType;
    private int zone;
    private int address;
    private int readLen;

    public ReadEntity() {
    }

    public ReadEntity(CardTypeEnum cardType, int zone, int address, int readLen) {
        this.cardType = cardType;
        this.zone = zone;
        this.address = address;
        this.readLen = readLen;
    }

    protected ReadEntity(Parcel in) {
        cardType = in.readParcelable(CardTypeEnum.class.getClassLoader());
        zone = in.readInt();
        address = in.readInt();
        readLen = in.readInt();
    }

    public static final Creator<ReadEntity> CREATOR = new Creator<ReadEntity>() {
        @Override
        public ReadEntity createFromParcel(Parcel in) {
            return new ReadEntity(in);
        }

        @Override
        public ReadEntity[] newArray(int size) {
            return new ReadEntity[size];
        }
    };

    public CardTypeEnum getCardType() {
        return this.cardType;
    }

    public void setCardType(CardTypeEnum cardType) {
        this.cardType = cardType;
    }

    public int getZone() {
        return this.zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public int getAddress() {
        return this.address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getReadLen() {
        return this.readLen;
    }

    public void setReadLen(int readLen) {
        this.readLen = readLen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(cardType, i);
        parcel.writeInt(zone);
        parcel.writeInt(address);
        parcel.writeInt(readLen);
    }
}
