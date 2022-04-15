package com.coverteam.pta.data.models;


import java.util.List;

public class DocumentCuti {
    private String alamat;
    private String alasan;
    private String nipPengaju;
    private Long jumlahCutiHari;
    private String status;
    private Boolean validasiAtasan;
    private Boolean validasiKepagawaian;
    private Boolean validasiPejabat;
    private String alasanStatusValidasi;
    private List<String> listTgl;
    private String nipProsesPerubahan;
    private String tahun;
    private String bulan;
    private Boolean urgent;

    public String getAlamat() { return alamat; }
    public void setAlamat(String value) { this.alamat = value; }

    public String getAlasan() { return alasan; }
    public void setAlasan(String value) { this.alasan = value; }

    public String getNipPengaju() { return nipPengaju; }
    public void setNipPengaju(String value) { this.nipPengaju = value; }

    public Long getJumlahCutiHari() { return jumlahCutiHari; }
    public void setJumlahCutiHari(Long value) { this.jumlahCutiHari = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public Boolean getValidasiAtasan() { return validasiAtasan; }
    public void setValidasiAtasan(Boolean value) { this.validasiAtasan = value; }

    public Boolean getValidasiKepagawaian() { return validasiKepagawaian; }
    public void setValidasiKepagawaian(Boolean value) { this.validasiKepagawaian = value; }

    public Boolean getValidasiPejabat() { return validasiPejabat; }
    public void setValidasiPejabat(Boolean value) { this.validasiPejabat = value; }

    public String getAlasanStatusValidasi() { return alasanStatusValidasi; }
    public void setAlasanStatusValidasi(String value) { this.alasanStatusValidasi = value; }

    public List<String> getListTgl() { return listTgl; }
    public void setListTgl(List<String> value) { this.listTgl = value; }

    public String getNipProsesPerubahan() { return nipProsesPerubahan; }
    public void setNipProsesPerubahan(String value) { this.nipProsesPerubahan = value; }

    public String getTahun() { return tahun; }
    public void setTahun(String value) { this.tahun = value; }

    public String getBulan() { return bulan; }
    public void setBulan(String value) { this.bulan = value; }

    public Boolean getUrgent() { return urgent; }
    public void setUrgent(Boolean value) { this.urgent = value; }
}
