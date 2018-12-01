package com.example.user.licenta2.MyClasses;

public class Project {

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
}
