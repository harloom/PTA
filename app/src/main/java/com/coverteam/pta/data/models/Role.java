package com.coverteam.pta.data.models;

import java.util.ArrayList;

public  class Role {

    public static  String ADMIN = "Admin";
    public static  String PEGAWAI = "Pegawai";
    public static  String PEJABAT = "Pejabat";
    public  static  String KEPEGAWAIAN = "Kepegawaian";


    public  static ArrayList<String> getList(){
        ArrayList<String> listRole = new ArrayList<String>();
        listRole.add(Role.ADMIN);
        listRole.add(Role.KEPEGAWAIAN);
        listRole.add(Role.PEGAWAI);
        listRole.add(Role.PEJABAT);
        return  listRole;
    }

}
