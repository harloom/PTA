package com.coverteam.pta.data.models;


import java.util.Date;

public class Holiday {
    private Date tgl;
    private String deskripsi;

    public Date getTgl() {
        return tgl;
    }

    public void setTgl(Date tgl) {
        this.tgl = tgl;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
