package com.coverteam.pta.data.models;


import com.coverteam.pta.data.repositorys.Identifiable;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;


public class Users  {
    private String role;
    private String nama;
    private String nip;
    private String foto;
    private String noHandphone;
    private String password;
    private String username;
    private String golongan;
    private String jabatan;
    private Integer masaKerja;
    private Integer jumlahMaximalCutiPertahun;
    private String atasan;
    @ServerTimestamp
    private Timestamp timeStamp;


    public  Users(){

    }

    public Users(String role, String nama, String nip, String foto,
                 String noHandphone, String password, String username, String golongan, String jabatan, Integer masaKerja, Integer jumlahMaximalCutiPertahun, String atasan) {
        this.role = role;
        this.nama = nama;
        this.nip = nip;
        this.foto = foto;
        this.noHandphone = noHandphone;
        this.password = password;
        this.username = username;
        this.golongan = golongan;
        this.jabatan = jabatan;
        this.masaKerja = masaKerja;
        this.jumlahMaximalCutiPertahun = jumlahMaximalCutiPertahun;
        this.atasan = atasan;
    }

    public String getRole() { return role; }
    public void setRole(String value) { this.role = value; }

    public String getNama() { return nama; }
    public void setNama(String value) { this.nama = value; }

    public String getNip() { return nip; }
    public void setNip(String value) { this.nip = value; }

    public String getFoto() { return foto; }
    public void setFoto(String value) { this.foto = value; }

    public String getNoHandphone() { return noHandphone; }
    public void setNoHandphone(String value) { this.noHandphone = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public String getUsername() { return username; }
    public void setUsername(String value) { this.username = value; }

    public String getGolongan() { return golongan; }
    public void setGolongan(String value) { this.golongan = value; }

    public String getJabatan() { return jabatan; }
    public void setJabatan(String value) { this.jabatan = value; }

    public Integer getMasaKerja() { return masaKerja; }
    public void setMasaKerja(Integer value) { this.masaKerja = value; }

    public Integer getJumlahMaximalCutiPertahun() { return jumlahMaximalCutiPertahun; }
    public void setJumlahMaximalCutiPertahun(Integer value) { this.jumlahMaximalCutiPertahun = value; }

    public String getAtasan() { return atasan; }
    public void setAtasan(String value) { this.atasan = value; }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
}
