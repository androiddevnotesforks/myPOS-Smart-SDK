package com.mypos.appmanagment;

import android.os.Parcel;
import android.os.Parcelable;

public class NdefUserMessageRecord implements Parcelable {

    private NdefTextRtd[] textArray;
    private NdefUriRtd[] uriArray;
    private NdefAarRtd[] AarArray;
    private NdefCustomRecord[] customArray;

    public NdefUserMessageRecord() {
    }

    protected NdefUserMessageRecord(Parcel in) {
        textArray = in.createTypedArray(NdefTextRtd.CREATOR);
        uriArray = in.createTypedArray(NdefUriRtd.CREATOR);
        AarArray = in.createTypedArray(NdefAarRtd.CREATOR);
        customArray = in.createTypedArray(NdefCustomRecord.CREATOR);
    }

    public static final Creator<NdefUserMessageRecord> CREATOR = new Creator<NdefUserMessageRecord>() {
        @Override
        public NdefUserMessageRecord createFromParcel(Parcel in) {
            return new NdefUserMessageRecord(in);
        }

        @Override
        public NdefUserMessageRecord[] newArray(int size) {
            return new NdefUserMessageRecord[size];
        }
    };

    public NdefTextRtd[] getTextArray() {
        return this.textArray;
    }

    public void setTextArray(NdefTextRtd[] var1) {
        this.textArray = var1;
    }

    public NdefUriRtd[] getUriArray() {
        return this.uriArray;
    }

    public void setUriArray(NdefUriRtd[] var1) {
        this.uriArray = var1;
    }

    public NdefAarRtd[] getAarArray() {
        return this.AarArray;
    }

    public void setAarArray(NdefAarRtd[] var1) {
        this.AarArray = var1;
    }

    public NdefCustomRecord[] getCustomArray() {
        return this.customArray;
    }

    public void setCustomArray(NdefCustomRecord[] var1) {
        this.customArray = var1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public void readFromParcel(Parcel in) {

    }

    public static class NdefTextRtd implements Parcelable {
        private String text;
        private String languageCode;
        private int encode;

        public NdefTextRtd(Parcel in) {
            this.text = in.readString();
            this.languageCode = in.readString();
            this.encode = in.readInt();
        }

        public String getText() {
            return this.text;
        }

        public void setText(String var1) {
            this.text = var1;
        }

        public String getLanguageCode() {
            return this.languageCode;
        }

        public void setLanguageCode(String var1) {
            this.languageCode = var1;
        }

        public int getEncode() {
            return this.encode;
        }

        public void setEncode(int var1) {
            this.encode = var1;
        }

        public static final Creator<NdefTextRtd> CREATOR = new Creator<NdefTextRtd>() {
            @Override
            public NdefTextRtd createFromParcel(Parcel in) {
                return new NdefTextRtd(in);
            }

            @Override
            public NdefTextRtd[] newArray(int size) {
                return new NdefTextRtd[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.text);
            parcel.writeString(this.languageCode);
            parcel.writeInt(this.encode);

        }
    }

    public static class NdefUriRtd implements Parcelable {
        private String uri;
        private int uiProtocol;

        public NdefUriRtd(Parcel in) {
            this.uri = in.readString();
            this.uiProtocol = in.readInt();
        }

        public String getUri() {
            return this.uri;
        }

        public void setUri(String var1) {
            this.uri = var1;
        }

        public int getUiProtocol() {
            return this.uiProtocol;
        }

        public void setUiProtocol(int var1) {
            this.uiProtocol = var1;
        }

        public static final Creator<NdefUriRtd> CREATOR = new Creator<NdefUriRtd>() {
            @Override
            public NdefUriRtd createFromParcel(Parcel in) {
                return new NdefUriRtd(in);
            }

            @Override
            public NdefUriRtd[] newArray(int size) {
                return new NdefUriRtd[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.uri);
            parcel.writeInt(this.uiProtocol);
        }
    }

    public static class NdefAarRtd implements Parcelable {
        private String packageName;

        public NdefAarRtd(Parcel in) {
            this.packageName = in.readString();
        }

        public String getPackageName() {
            return this.packageName;
        }

        public void setPackageName(String var1) {
            this.packageName = var1;
        }

        public static final Creator<NdefAarRtd> CREATOR = new Creator<NdefAarRtd>() {
            @Override
            public NdefAarRtd createFromParcel(Parcel in) {
                return new NdefAarRtd(in);
            }

            @Override
            public NdefAarRtd[] newArray(int size) {
                return new NdefAarRtd[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.packageName);
        }
    }

    public static class NdefCustomRecord implements Parcelable {
        private int tnf;
        private byte[] type;
        private byte[] id;
        private byte[] payLoad;

        public NdefCustomRecord(Parcel in) {
            this.tnf = in.readInt();
            this.type = in.createByteArray();
            this.id = in.createByteArray();
            this.payLoad = in.createByteArray();
        }

        public int getTnf() {
            return this.tnf;
        }

        public void setTnf(int var1) {
            this.tnf = var1;
        }

        public byte[] getType() {
            return this.type;
        }

        public void setType(byte[] var1) {
            this.type = var1;
        }

        public byte[] getId() {
            return this.id;
        }

        public void setId(byte[] var1) {
            this.id = var1;
        }

        public byte[] getPayLoad() {
            return this.payLoad;
        }

        public void setPayLoad(byte[] var1) {
            this.payLoad = var1;
        }

        public static final Creator<NdefCustomRecord> CREATOR = new Creator<NdefCustomRecord>() {
            @Override
            public NdefCustomRecord createFromParcel(Parcel in) {
                return new NdefCustomRecord(in);
            }

            @Override
            public NdefCustomRecord[] newArray(int size) {
                return new NdefCustomRecord[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.tnf);
            parcel.writeByteArray(this.type);
            parcel.writeByteArray(this.id);
            parcel.writeByteArray(this.payLoad);
        }
    }
}