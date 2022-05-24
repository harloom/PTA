package com.coverteam.pta.tools;

import android.text.SpannableString;

import com.azimolabs.maskformatter.MaskFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomMask {

    private static final Integer NIP_LENGTH = 18;




    public  static String  formatNIP(String text){
        String tmp = null;
        int length = text.length();
        System.out.println("Lenth : " + length);
        if(text.length() >= NIP_LENGTH){
            tmp = "";
            for(int i =0 ; i < text.length(); i++) {
                if(i == 8 || i == 14 ){
                    System.out.println("index : " + i);
                    tmp += " " + text.charAt(i);
                }else{
                    System.out.println("indexAss : " + i);
                    tmp += text.charAt(i);
                }

            }
        }else{
            tmp = text;
        }
            
        
        

        return tmp;
    }
}
