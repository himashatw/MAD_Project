package com.example.shoppingez;

import android.os.Parcel;
import android.os.Parcelable;

public class Payment implements Parcelable {

    private String address;
    private int Total;
    private String pid;

    public Payment() {
    }

    protected Payment(Parcel in) {
        address = in.readString();
        Total = in.readInt();
        pid = in.readString();
    }

    public static final Creator<Payment> CREATOR = new Creator<Payment>() {
        @Override
        public Payment createFromParcel(Parcel in) {
            return new Payment(in);
        }

        @Override
        public Payment[] newArray(int size) {
            return new Payment[size];
        }
    };

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAddress() {
        return address;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeInt(Total);
        parcel.writeString(pid);
    }
}
