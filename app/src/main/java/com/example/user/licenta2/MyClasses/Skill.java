package com.example.user.licenta2.MyClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Skill implements Parcelable {
    private String nume;

    public Skill(String _nume)
    {
        nume = _nume;
    }

    public Skill() {}

    protected Skill(Parcel in) {
        nume = in.readString();
    }

    public static final Creator<Skill> CREATOR = new Creator<Skill>() {
        @Override
        public Skill createFromParcel(Parcel in) {
            return new Skill(in);
        }

        @Override
        public Skill[] newArray(int size) {
            return new Skill[size];
        }
    };

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
    }

    @Override
    public String toString() {
        return this.getNume();
    }
}
