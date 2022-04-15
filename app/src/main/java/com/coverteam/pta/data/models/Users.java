package com.coverteam.pta.data.models;


public class Users {
    private String role;
    private String nama;
    private String nip;
    private String foto;
    private String noHandphone;
    private String password;
    private String username;
    private String golongan;
    private String jabatan;
    private Long masaKerja;
    private Long jumlahMaximalCutiPertahun;
    private String atasan;

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

    public Long getMasaKerja() { return masaKerja; }
    public void setMasaKerja(Long value) { this.masaKerja = value; }

    public Long getJumlahMaximalCutiPertahun() { return jumlahMaximalCutiPertahun; }
    public void setJumlahMaximalCutiPertahun(Long value) { this.jumlahMaximalCutiPertahun = value; }

    public String getAtasan() { return atasan; }
    public void setAtasan(String value) { this.atasan = value; }
}
