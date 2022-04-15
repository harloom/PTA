package com.coverteam.pta.data.models;

public class InformationDate {
    private String id;
    private Long jumlahOrangYangCuti;
    private String bulan;
    private String tanggal;
    private String tahun;

    public String getID() { return id; }
    public void setID(String value) { this.id = value; }

    public Long getJumlahOrangYangCuti() { return jumlahOrangYangCuti; }
    public void setJumlahOrangYangCuti(Long value) { this.jumlahOrangYangCuti = value; }

    public String getBulan() { return bulan; }
    public void setBulan(String value) { this.bulan = value; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String value) { this.tanggal = value; }

    public String getTahun() { return tahun; }
    public void setTahun(String value) { this.tahun = value; }
}
