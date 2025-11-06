package com.mypos.appmanagment;

import android.os.Parcel;
import android.os.Parcelable;

public class VerifyEntity implements Parcelable {
    private CardTypeEnum cardType;
    private byte[] pwd;
    private int mode;
    private int zone;

    public VerifyEntity(CardTypeEnum cardType, byte[] pwd, int mode, int zone) {
        this.cardType = cardType;
        this.pwd = pwd;
        this.mode = mode;
        this.zone = zone;
    }

    public VerifyEntity() {
    }

    protected VerifyEntity(Parcel in) {
        cardType = in.readParcelable(CardTypeEnum.class.getClassLoader());
        pwd = in.createByteArray();
        mode = in.readInt();
        zone = in.readInt();
    }

    public static final Creator<VerifyEntity> CREATOR = new Creator<VerifyEntity>() {
        @Override
        public VerifyEntity createFromParcel(Parcel in) {
            return new VerifyEntity(in);
        }

        @Override
        public VerifyEntity[] newArray(int size) {
            return new VerifyEntity[size];
        }
    };

    public CardTypeEnum getCardType() {
        return this.cardType;
    }

    public void setCardType(CardTypeEnum cardType) {
        this.cardType = cardType;
    }

    public byte[] getPwd() {
        return this.pwd;
    }

    public void setPwd(byte[] pwd) {
        this.pwd = pwd;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
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
        parcel.writeByteArray(pwd);
        parcel.writeInt(mode);
        parcel.writeInt(zone);
    }
}
