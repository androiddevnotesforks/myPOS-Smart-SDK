package com.mypos.appmanagment;

import android.os.Parcel;
import android.os.Parcelable;

public class WriteEntity implements Parcelable {
    private CardTypeEnum cardType;
    private int zone;
    private int address;
    private byte[] writeData;
    private int writeLen;

    public WriteEntity() {
    }

    public WriteEntity(CardTypeEnum cardType, int zone, int address, byte[] writeData, int writeLen) {
        this.cardType = cardType;
        this.zone = zone;
        this.address = address;
        this.writeData = writeData;
        this.writeLen = writeLen;
    }

    protected WriteEntity(Parcel in) {
        cardType = in.readParcelable(CardTypeEnum.class.getClassLoader());
        zone = in.readInt();
        address = in.readInt();
        writeData = in.createByteArray();
        writeLen = in.readInt();
    }

    public static final Creator<WriteEntity> CREATOR = new Creator<WriteEntity>() {
        @Override
        public WriteEntity createFromParcel(Parcel in) {
            return new WriteEntity(in);
        }

        @Override
        public WriteEntity[] newArray(int size) {
            return new WriteEntity[size];
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

    public byte[] getWriteData() {
        return this.writeData;
    }

    public void setWriteData(byte[] writeData) {
        this.writeData = writeData;
    }

    public int getWriteLen() {
        return this.writeLen;
    }

    public void setWriteLen(int writeLen) {
        this.writeLen = writeLen;
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
        parcel.writeByteArray(writeData);
        parcel.writeInt(writeLen);
    }
}
