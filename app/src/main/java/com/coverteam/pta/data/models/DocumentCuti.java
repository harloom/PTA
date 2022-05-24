package com.coverteam.pta.data.models;


import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class DocumentCuti {
    @DocumentId
    private  String idDoc;


    private  String namaPengaju ;
    private String alamat;
    private String alasan;
    private  String typeAlasan;


    private String nipPengaju;
    private  String noHp;

    private Integer jumlahCutiHari;
    private String status;

    private String validasiAtasan;
    private String validasiKepagawaian;
    private String validasiPejabat;

    private List<Date> listTgl;

    private String validasiNipAtasan;
    private String validasiNipKepagawaian;
    private String validasiNipPejabat;

    private  String penolakanMessage;

    private String tahun;
    private String bulan;
    private Boolean urgent;

    private Timestamp createdAt;
    @ServerTimestamp
    private Timestamp updatedAt;

    public DocumentCuti() {
        this.createdAt = Timestamp.now();
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getTypeAlasan() {
        return typeAlasan;
    }

    public void setTypeAlasan(String typeAlasan) {
        this.typeAlasan = typeAlasan;
    }

    public String getNamaPengaju() {
        return namaPengaju;
    }

    public void setNamaPengaju(String namaPengaju) {
        this.namaPengaju = namaPengaju;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() { return alamat; }
    public void setAlamat(String value) { this.alamat = value; }

    public String getAlasan() { return alasan; }
    public void setAlasan(String value) { this.alasan = value; }

    public String getNipPengaju() { return nipPengaju; }
    public void setNipPengaju(String value) { this.nipPengaju = value; }

    public Integer getJumlahCutiHari() { return jumlahCutiHari; }
    public void setJumlahCutiHari(Integer value) { this.jumlahCutiHari = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public String getValidasiAtasan() { return validasiAtasan; }
    public void setValidasiAtasan(String value) { this.validasiAtasan = value; }

    public String getValidasiKepagawaian() { return validasiKepagawaian; }
    public void setValidasiKepagawaian(String value) { this.validasiKepagawaian = value; }

    public String getValidasiPejabat() { return validasiPejabat; }
    public void setValidasiPejabat(String value) { this.validasiPejabat = value; }



    public List<Date> getListTgl() { return listTgl; }
    public void setListTgl(List<Date> value) { this.listTgl = value; }

    public String getValidasiNipAtasan() { return validasiNipAtasan; }
    public void setValidasiNipAtasan(String value) { this.validasiNipAtasan = value; }

    public String getTahun() { return tahun; }
    public void setTahun(String value) { this.tahun = value; }

    public String getBulan() { return bulan; }
    public void setBulan(String value) { this.bulan = value; }

    public Boolean getUrgent() { return urgent; }
    public void setUrgent(Boolean value) { this.urgent = value; }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getValidasiNipKepagawaian() {
        return validasiNipKepagawaian;
    }

    public void setValidasiNipKepagawaian(String validasiNipKepagawaian) {
        this.validasiNipKepagawaian = validasiNipKepagawaian;
    }

    public String getValidasiNipPejabat() {
        return validasiNipPejabat;
    }

    public void setValidasiNipPejabat(String validasiNipPejabat) {
        this.validasiNipPejabat = validasiNipPejabat;
    }

    public String getIdDoc() {
        return idDoc;
    }



    public  static  String TERIMA = "TERIMA";
    public  static  String TOLAK = "TOLAK";

    public  static  String colorTerima = "#5ABD8C";
    public  static  String colorTOLAK= "#d1206b";
    public  static  String colorMenunggu = "#000000";

    public  static String MESSAGE_TOLAK_PEGAWAIAN = "DATA DI TOLAK OLEH KEPEGAWAIAN";
    public  static String MESSAGE_TERIMA_PEGAWAIAN = "DATA DI TERIMA OLEH KEPEGAWAIAN";
    public  static String MESSAGE_MENUNGGU_PEGAWAIAN = "DATA SEDANG DI CEK OLEH KEPEGAWAIAN";

    public static  String MESSAGE_MENUNGGU_PERSETUJUAN = "MENUNGGU PERSETUJUAN";

    public  static String MESSAGE_TOLAK_ATASAN= "DATA DI TOLAK OLEH ATASAN";
    public  static String MESSAGE_TERIMA_ATASAN = "DATA DI TERIMA OLEH ATASAN";

    public  static String MESSAGE_TOLAK_PEJABAT = "DATA DI TOLAK OLEH PEJABAT";
    public  static String MESSAGE_TERIMA_PEJABAT= "DATA DI TERIMA OLEH PEJABAT";

    public String getPenolakanMessage() {
        return penolakanMessage;
    }

    public void setPenolakanMessage(String penolakanMessage) {
        this.penolakanMessage = penolakanMessage;
    }
}
