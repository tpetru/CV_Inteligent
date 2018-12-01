package com.example.user.licenta2.MyClasses;

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
        this.end_date = _data_sfarsit;
    }

    public Experience(String _nume, String _pozitie, String _data_inceput)
    {
        this.name = _nume;
        this.position = _pozitie;
        this.start_date = _data_inceput;
        this.end_date = "Prezent";
    }

    public Experience () {}

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
}
