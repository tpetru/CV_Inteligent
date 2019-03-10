package com.example.user.licenta2.MyClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Education implements Parcelable {
    private String type;
    private String name;
    private String startDates;
    private String endDates;
    private String profile;

    public Education(String _type, String _name, String _profile, String _data_inceput, String _data_sfarsit)
    {
        this.type = _type;
        this.name = _name;
        this.startDates = _data_inceput;
        this.endDates = _data_sfarsit;
        this.profile = _profile;
    }

    public Education(String _type, String _name, String _profile, String _data_inceput)
    {
        this.type = _type;
        this.name = _name;
        this.startDates = _data_inceput;
        this.endDates = "Prezent";
        this.profile = _profile;
    }

    public Education()
    {

    }

    protected Education(Parcel in) {
        type = in.readString();
        name = in.readString();
        startDates = in.readString();
        endDates = in.readString();
        profile = in.readString();
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDates() {
        return startDates;
    }

    public void setStartDates(String startDates) {
        this.startDates = startDates;
    }

    public String getEndDates() {
        return endDates;
    }

    public void setEndDates(String endDates) {
        this.endDates = endDates;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(name);
        dest.writeString(startDates);
        dest.writeString(endDates);
        dest.writeString(profile);
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
