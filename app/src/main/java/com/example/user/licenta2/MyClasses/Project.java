package com.example.user.licenta2.MyClasses;

import android.os.Parcel;
import android.os.Parcelable;

public class Project implements Parcelable {

    private String name;
    private String rezumat;
    private String description;

    public Project(String _nume, String _rezumat, String _descriere)
    {
        this.name = _nume;
        this.rezumat = _rezumat;
        this.description = _descriere;
    }

    public Project(String _name, String _description)
    {
        this.name = _name;
        this.description = _description;
    }

    public Project()
    {

    }


    protected Project(Parcel in) {
        name = in.readString();
        rezumat = in.readString();
        description = in.readString();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRezumat() {
        return rezumat;
    }

    public void setRezumat(String rezumat) {
        this.rezumat = rezumat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(rezumat);
        dest.writeString(description);
    }
}
