package com.edencoding.models;

import java.util.Date;

public class Student {

    private long DB_ID;
    private String name;
    private String vorname;
    private Date geburtsdatum;
    private String adresse;
    private Studiengruppe gruppe;

    public Student(long DB_ID, String name, String vorname, Date geburtsdatum, String adresse, Studiengruppe gruppe) {
        this.DB_ID = DB_ID;
        this.name = name;
        this.vorname = vorname;
        this.geburtsdatum = geburtsdatum;
        this.adresse = adresse;
        this.gruppe = gruppe;
    }

    public long getDB_ID() {
        return DB_ID;
    }

    public void setDB_ID(long DB_ID) {
        this.DB_ID = DB_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Studiengruppe getGruppe() {
        return gruppe;
    }

    public void setGruppe(Studiengruppe gruppe) {
        this.gruppe = gruppe;
    }
}
