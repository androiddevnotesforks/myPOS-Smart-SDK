package com.mypos.appmanagment;

import android.os.Parcel;
import android.os.Parcelable;

public class ReadECEntity implements Parcelable {
    private CardTypeEnum cardType;
    private int mode;
    private int zone;

    public ReadECEntity() {
    }

    public ReadECEntity(CardTypeEnum cardType, int mode, int zone) {
        this.cardType = cardType;
        this.mode = mode;
        this.zone = zone;
    }

    protected ReadECEntity(Parcel in) {
        cardType = in.readParcelable(CardTypeEnum.class.getClassLoader());
        mode = in.readInt();
        zone = in.readInt();
    }

    public static final Creator<ReadECEntity> CREATOR = new Creator<ReadECEntity>() {
        @Override
        public ReadECEntity createFromParcel(Parcel in) {
            return new ReadECEntity(in);
        }

        @Override
        public ReadECEntity[] newArray(int size) {
            return new ReadECEntity[size];
        }
    };

    public CardTypeEnum getCardType() {
        return this.cardType;
    }

    public void setCardType(CardTypeEnum cardType) {
        this.cardType = cardType;
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
        parcel.writeInt(mode);
        parcel.writeInt(zone);
    }
}
