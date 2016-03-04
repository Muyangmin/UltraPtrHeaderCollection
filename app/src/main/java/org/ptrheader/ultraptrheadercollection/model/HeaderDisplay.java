package org.ptrheader.ultraptrheadercollection.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;

public class HeaderDisplay implements Parcelable {

    public final String displayName;
    public final int type;
    @LayoutRes
    public final int sampleUsage;

    public HeaderDisplay(String displayName, int type, @LayoutRes int sampleUsage) {
        this.displayName = displayName;
        this.type = type;
        this.sampleUsage = sampleUsage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.displayName);
        dest.writeInt(this.type);
        dest.writeInt(this.sampleUsage);
    }

    protected HeaderDisplay(Parcel in) {
        this.displayName = in.readString();
        this.type = in.readInt();
        this.sampleUsage = in.readInt();
    }

    public static final Creator<HeaderDisplay> CREATOR = new Creator<HeaderDisplay>() {
        public HeaderDisplay createFromParcel(Parcel source) {
            return new HeaderDisplay(source);
        }

        public HeaderDisplay[] newArray(int size) {
            return new HeaderDisplay[size];
        }
    };
}