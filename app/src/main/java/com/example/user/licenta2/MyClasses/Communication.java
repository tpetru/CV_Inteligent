package com.example.user.licenta2.MyClasses;

public class Communication {
    private String name;
    private String level;

    public Communication(String _nume, String _nivel)
    {
        this.name = _nume;
        this.level = _nivel;
    }

    public Communication()
    {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
