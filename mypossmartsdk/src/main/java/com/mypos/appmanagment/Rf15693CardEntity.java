package com.mypos.appmanagment;

import android.os.Parcel;
import android.os.Parcelable;

public class Rf15693CardEntity implements Parcelable {
    private byte infoFlag;
    private byte[] cardUid;
    private byte dsfid;
    private byte afi;
    private int totalBlockNum;
    private int singleBlockSize;
    private byte icReference;

    protected Rf15693CardEntity(Parcel in) {
        infoFlag = in.readByte();
        cardUid = in.createByteArray();
        dsfid = in.readByte();
        afi = in.readByte();
        totalBlockNum = in.readInt();
        singleBlockSize = in.readInt();
        icReference = in.readByte();
    }

    public static final Creator<Rf15693CardEntity> CREATOR = new Creator<Rf15693CardEntity>() {
        @Override
        public Rf15693CardEntity createFromParcel(Parcel in) {
            return new Rf15693CardEntity(in);
        }

        @Override
        public Rf15693CardEntity[] newArray(int size) {
            return new Rf15693CardEntity[size];
        }
    };

    public byte getInfoFlag() {
        return infoFlag;
    }

    public void setInfoFlag(byte infoFlag) {
        this.infoFlag = infoFlag;
    }

    public byte[] getCardUid() {
        return cardUid;
    }

    public void setCardUid(byte[] cardUid) {
        this.cardUid = cardUid;
    }

    public byte getDsfid() {
        return dsfid;
    }

    public void setDsfid(byte dsfid) {
        this.dsfid = dsfid;
    }

    public byte getAfi() {
        return afi;
    }

    public void setAfi(byte afi) {
        this.afi = afi;
    }

    public int getTotalBlockNum() {
        return totalBlockNum;
    }

    public void setTotalBlockNum(int totalBlockNum) {
        this.totalBlockNum = totalBlockNum;
    }

    public int getSingleBlockSize() {
        return singleBlockSize;
    }

    public void setSingleBlockSize(int singleBlockSize) {
        this.singleBlockSize = singleBlockSize;
    }

    public byte getIcReference() {
        return icReference;
    }

    public void setIcReference(byte icReference) {
        this.icReference = icReference;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(infoFlag);
        parcel.writeByteArray(cardUid);
        parcel.writeByte(dsfid);
        parcel.writeByte(afi);
        parcel.writeInt(totalBlockNum);
        parcel.writeInt(singleBlockSize);
        parcel.writeByte(icReference);
    }
}
