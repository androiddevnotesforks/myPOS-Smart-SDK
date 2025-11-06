package com.mypos.appmanagment;

import android.os.Parcel;
import android.os.Parcelable;

public enum CardTypeEnum implements Parcelable {
    AT24C01,
    AT24C02,
    AT24C04,
    AT24C08,
    AT24C16,
    AT24C32,
    AT24C64,
    AT88SC101,
    AT88SC102,
    IS23SC1604,
    AT88SC153,
    AT88SC1608,
    SLE4442,
    SLE4428;

    CardTypeEnum() {
    }

    public static final Creator<CardTypeEnum> CREATOR = new Creator<CardTypeEnum>() {
        @Override
        public CardTypeEnum createFromParcel(Parcel in) {
            return CardTypeEnum.valueOf(in.readString());
        }

        @Override
        public CardTypeEnum[] newArray(int size) {
            return new CardTypeEnum[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
