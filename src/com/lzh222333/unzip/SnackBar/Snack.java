package com.lzh222333.unzip.SnackBar;

import android.content.res.ColorStateList;
import android.os.Parcel;
import android.os.Parcelable;


class Snack implements Parcelable {

    final String mMessage;

    final String mActionMessage;
	String mActionMessage2;
    final int mActionIcon;

    final Parcelable mToken;

    final short mDuration;

    final ColorStateList mBtnTextColor;

    Snack(String message, String actionMessage,String actionMessage2, int actionIcon,
                 Parcelable token, short duration, ColorStateList textColor) {
        mMessage = message;
        mActionMessage = actionMessage;
		mActionMessage2=actionMessage2;
		
        mActionIcon = actionIcon;
        mToken = token;
        mDuration = duration;
        mBtnTextColor = textColor;
    }
    // reads data from parcel
    Snack(Parcel p) {
        mMessage = p.readString();
        mActionMessage = p.readString();
		mActionMessage2=p.readString();
        mActionIcon = p.readInt();
        mToken = p.readParcelable(p.getClass().getClassLoader());
        mDuration = (short) p.readInt();
        mBtnTextColor = p.readParcelable(p.getClass().getClassLoader());
    }

    // writes data to parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mMessage);
        out.writeString(mActionMessage);
		out.writeString(mActionMessage2);
        out.writeInt(mActionIcon);
        out.writeParcelable(mToken, 0);
        out.writeInt((int) mDuration);
        out.writeParcelable(mBtnTextColor, 0);
    }

    public int describeContents() {
        return 0;
    }

    // creates snack array
    public static final Parcelable.Creator<Snack> CREATOR = new Parcelable.Creator<Snack>() {
        public Snack createFromParcel(Parcel in) {
            return new Snack(in);
        }

        public Snack[] newArray(int size) {
            return new Snack[size];
        }
    };
}
