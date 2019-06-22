package com.example.user.licenta2.MyClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Experience {
    private String name;
    private String position;
    private String start_date;
    private String end_date;

    public Experience(String _nume, String _pozitie, String _data_inceput, String _data_sfarsit)
    {
        this.name = _nume;
        this.position = _pozitie;
        this.start_date = _data_inceput;
        if(_data_sfarsit.equals("..")) {
            this.end_date = "Prezent";
        }
        else {
            this.end_date = _data_sfarsit;
        }
    }

    public Experience(String _nume, String _pozitie, String _data_inceput)
    {
        this.name = _nume;
        this.position = _pozitie;
        this.start_date = _data_inceput;
        this.end_date = "Prezent";
    }

    public Experience(String _description) {
        this.name = _description;
        this.position = "";
        this.start_date = "..";
        this.end_date = "..";
    }

    public Experience ()
    {

    }

    protected Experience(Parcel in) {
        name = in.readString();
        position = in.readString();
        start_date = in.readString();
        end_date = in.readString();
    }

    public static final Parcelable.Creator<Experience> CREATOR = new Parcelable.Creator<Experience>() {
        @Override
        public Experience createFromParcel(Parcel in) {
            return new Experience(in);
        }

        @Override
        public Experience[] newArray(int size) {
            return new Experience[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(name);
//        dest.writeString(position);
//        dest.writeString(start_date);
//        dest.writeString(end_date);
//    }

    @Override
    public String toString() {
        return this.getName();
    }
}
