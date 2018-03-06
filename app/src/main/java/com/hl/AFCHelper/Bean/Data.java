package com.hl.AFCHelper.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huanglei on 2018/1/17.
 */

public class Data implements Parcelable {
        private int mId;
        private String new_title;
        private String new_content;

        public Data(int mId, String new_title, String new_content) {
            this.new_title = new_title;
            this.new_content = new_content;
            this.mId = mId;
        }

    public String getNew_title() {
            return new_title;
        }

    public int getId() {
        return this.mId;
    }


        public String getNew_content() {
            return new_content;
        }

        public void setNew_title(String new_title) {
            this.new_title = new_title;
        }

        public void setNew_content(String new_content) {
            this.new_content = new_content;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt (this.mId);
        dest.writeString (this.new_title);
        dest.writeString (this.new_content);
    }

    protected Data(Parcel in) {
        this.mId = in.readInt ();
        this.new_title = in.readString ();
        this.new_content = in.readString ();
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data> () {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data (source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}
