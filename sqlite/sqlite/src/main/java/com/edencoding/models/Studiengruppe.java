package com.edencoding.models;

public class Studiengruppe {

    private long DB_ID;
    private String name;
    private String studienort;

    public Studiengruppe(long DB_ID, String name, String studienort) {
        this.DB_ID = DB_ID;
        this.name = name;
        this.studienort = studienort;
    }

    public long getDB_ID() {
        return DB_ID;
    }

    public String getName() {
        return name;
    }

    public String getStudienort() {
        return studienort;
    }

    public void setDB_ID(long DB_ID) {
        this.DB_ID = DB_ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudienort(String studienort) {
        this.studienort = studienort;
    }
}
