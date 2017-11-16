/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytel.util;

import java.math.BigDecimal;

/**
 *
 * @author andri
 */
public class FonctionUtils {
    
    public static String normaliseChiffre(BigDecimal nb) {
        String lettre = ""+nb, res = "";
        lettre = lettre.contains(".") ? lettre.split(".")[0] : lettre;
        for(int i = lettre.length()-1; i >= 0; i--) {
            switch(res.length()) {
                case 0: res = lettre.charAt(i)+".00";
                        break;
                case 6: res = lettre.charAt(i)+"."+res;
                        break;
                case 10: res = lettre.charAt(i)+"."+res;
                        break;
                case 14: res = lettre.charAt(i)+"."+res;
                        break;
                case 18: res = lettre.charAt(i)+"."+res;
                        break;
                case 22: res = lettre.charAt(i)+"."+res;
                        break;
                case 26: res = lettre.charAt(i)+"."+res;
                        break;
                default: res = lettre.charAt(i)+res;
                        break;
            }
        }
        return res;
    }
}
