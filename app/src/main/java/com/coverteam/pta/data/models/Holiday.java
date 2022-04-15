package com.coverteam.pta.data.models;


public class Holiday {
    private String tgl;
    private String bulan;
    private String tahun;
    private String alasan;

    public String getTgl() { return tgl; }
    public void setTgl(String value) { this.tgl = value; }

    public String getBulan() { return bulan; }
    public void setBulan(String value) { this.bulan = value; }

    public String getTahun() { return tahun; }
    public void setTahun(String value) { this.tahun = value; }

    public String getAlasan() { return alasan; }
    public void setAlasan(String value) { this.alasan = value; }
}
