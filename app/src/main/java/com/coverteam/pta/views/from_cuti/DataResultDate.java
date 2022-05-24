package com.coverteam.pta.views.from_cuti;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataResultDate implements Parcelable {

    String message;
    List<Date> listDate;

    public DataResultDate(String message, List<Date> listDate) {
        this.message = message;
        this.listDate = listDate;
    }

    public DataResultDate() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeList(this.listDate);
    }

    public void readFromParcel(Parcel source) {
        this.message = source.readString();
        this.listDate = new ArrayList<Date>();
        source.readList(this.listDate, Date.class.getClassLoader());
    }

    protected DataResultDate(Parcel in) {
        this.message = in.readString();
        this.listDate = new ArrayList<Date>();
        in.readList(this.listDate, Date.class.getClassLoader());
    }

    public static final Parcelable.Creator<DataResultDate> CREATOR = new Parcelable.Creator<DataResultDate>() {
        @Override
        public DataResultDate createFromParcel(Parcel source) {
            return new DataResultDate(source);
        }

        @Override
        public DataResultDate[] newArray(int size) {
            return new DataResultDate[size];
        }
    };
}
