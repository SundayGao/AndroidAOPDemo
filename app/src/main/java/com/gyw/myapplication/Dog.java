package com.gyw.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Author: gaoyuanwu
 * @Date: 2019-04-09 11:14
 * @Description:
 */
public class Dog implements Parcelable {
    private String name;
    private int legs;

    protected Dog(Parcel in) {
        name = in.readString();
        legs = in.readInt();
    }

    public static final Creator<Dog> CREATOR = new Creator<Dog>() {
        @Override
        public Dog createFromParcel(Parcel in) {
            return new Dog(in);
        }

        @Override
        public Dog[] newArray(int size) {
            return new Dog[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(legs);
    }
}
