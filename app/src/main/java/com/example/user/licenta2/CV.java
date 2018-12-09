package com.example.user.licenta2;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.user.licenta2.MyClasses.Skill;
import com.example.user.licenta2.MyClasses.Communication;
import com.example.user.licenta2.MyClasses.Education;
import com.example.user.licenta2.MyClasses.Experience;
import com.example.user.licenta2.MyClasses.Project;

import java.util.ArrayList;
import java.util.List;

public class CV implements Parcelable {
    private String cvName;
    private String firstName, secondName, middleName;
    private String country, city, cod_postal;
    private String phoneNumber, email;
    private ArrayList<Skill> skills;
    private List<Education> education;
    private List<Experience> experiences;
    private List<Project> projects;
    private List<Communication> communications;



    public CV(String _cvName, String _firstName, String _secondName, String _middleName, String _phoneNumber, String _email)
    {
        this.cvName = _cvName;

        this.firstName = _firstName + " ";

        this.secondName = _secondName;

        if(_middleName.length() > 1)
            _middleName += " ";
        this.middleName = _middleName;

        this.phoneNumber = _phoneNumber;

        if(_email.length() > 1)
            _email = "Email: " + _email;
        this.email = _email;
    }

    public CV()
    {
        skills = new ArrayList<Skill>();
        education = new ArrayList<Education>();
        experiences = new ArrayList<Experience>();
        projects = new ArrayList<Project>();
        communications = new ArrayList<Communication>();
    }

    public CV(Parcel input) {
        this.cvName = input.readString();
        this.firstName = input.readString();
        this.secondName = input.readString();
        this.middleName = input.readString();
        this.country = input.readString();
        this.city = input.readString();
        this.country = input.readString();
        this.phoneNumber = input.readString();
        this.email = input.readString();

        this.skills = new ArrayList<Skill>();
        input.readTypedList(this.skills, Skill.CREATOR);


    }

    public static final Creator<CV> CREATOR = new Creator<CV>() {
        @Override
        public CV createFromParcel(Parcel in) {
            return new CV(in);
        }

        @Override
        public CV[] newArray(int size) {
            return new CV[size];
        }
    };

    public String getCvName() {
        return cvName;
    }

    public void setCvName(String cvName) {
        this.cvName = cvName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public List<Education> getEducation() {
        return education;
    }

    public void setEducation(List<Education> education) {
        this.education = education;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Communication> getCommunications() {
        return communications;
    }

    public void setCommunications(List<Communication> communications) {
        this.communications = communications;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cvName);
        dest.writeString(firstName);
        dest.writeString(secondName);
        dest.writeString(middleName);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(cod_postal);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeTypedList(skills);
    }
}
