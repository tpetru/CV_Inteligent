package com.example.user.licenta2.MyClasses;

public class Skill {
    private String nume;

    public Skill(String _nume)
    {
        nume = _nume;
    }

    public Skill() {}

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
