package com.coverteam.pta.data.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.coverteam.pta.data.repositorys.Identifiable;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.type.Date;


public class Users implements Parcelable {
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


    private String signature ;
    private String signatureSVG ;

    public  Users(){

    }

    public Users(String role, String nama, String nip, String foto, String noHandphone, String password, String username, String golongan, String jabatan, Integer masaKerja, Integer jumlahMaximalCutiPertahun, String atasan, Timestamp timeStamp, String signature) {
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
        this.timeStamp = timeStamp;
        this.signature = signature;
    }


    public String getSignatureSVG() {
        return signatureSVG;
    }

    public void setSignatureSVG(String signatureSVG) {
        this.signatureSVG = signatureSVG;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.role);
        dest.writeString(this.nama);
        dest.writeString(this.nip);
        dest.writeString(this.foto);
        dest.writeString(this.noHandphone);
        dest.writeString(this.password);
        dest.writeString(this.username);
        dest.writeString(this.golongan);
        dest.writeString(this.jabatan);
        dest.writeValue(this.masaKerja);
        dest.writeValue(this.jumlahMaximalCutiPertahun);
        dest.writeString(this.atasan);
        dest.writeParcelable(this.timeStamp, flags);
        dest.writeString(this.signature);
    }

    public void readFromParcel(Parcel source) {
        this.role = source.readString();
        this.nama = source.readString();
        this.nip = source.readString();
        this.foto = source.readString();
        this.noHandphone = source.readString();
        this.password = source.readString();
        this.username = source.readString();
        this.golongan = source.readString();
        this.jabatan = source.readString();
        this.masaKerja = (Integer) source.readValue(Integer.class.getClassLoader());
        this.jumlahMaximalCutiPertahun = (Integer) source.readValue(Integer.class.getClassLoader());
        this.atasan = source.readString();
        this.timeStamp = source.readParcelable(Timestamp.class.getClassLoader());
        this.signature = source.readString();
    }

    protected Users(Parcel in) {
        this.role = in.readString();
        this.nama = in.readString();
        this.nip = in.readString();
        this.foto = in.readString();
        this.noHandphone = in.readString();
        this.password = in.readString();
        this.username = in.readString();
        this.golongan = in.readString();
        this.jabatan = in.readString();
        this.masaKerja = (Integer) in.readValue(Integer.class.getClassLoader());
        this.jumlahMaximalCutiPertahun = (Integer) in.readValue(Integer.class.getClassLoader());
        this.atasan = in.readString();
        this.timeStamp = in.readParcelable(Timestamp.class.getClassLoader());
        this.signature = in.readString();
    }

    public static final Parcelable.Creator<Users> CREATOR = new Parcelable.Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel source) {
            return new Users(source);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };
}
