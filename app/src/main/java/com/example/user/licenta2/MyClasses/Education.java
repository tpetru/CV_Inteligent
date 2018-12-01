package com.example.user.licenta2.MyClasses;

public class Education {
    private String type;
    private String nume;
    private String data_inceput;
    private String data_sfarsit;
    private String specializare;

    public Education(String _type, String _name, String _data_inceput, String _data_sfarsit, String _specialziare)
    {
        this.type = _type;
        this.nume = _name;
        this.data_inceput = _data_inceput;
        this.data_sfarsit = _data_sfarsit;
        this.specializare = _specialziare;
    }

    public Education()
    {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getData_inceput() {
        return data_inceput;
    }

    public void setData_inceput(String data_inceput) {
        this.data_inceput = data_inceput;
    }

    public String getData_sfarsit() {
        return data_sfarsit;
    }

    public void setData_sfarsit(String data_sfarsit) {
        this.data_sfarsit = data_sfarsit;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }
}
