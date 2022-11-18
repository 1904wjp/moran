package com.moon.joyce.commons.joyce;


import com.moon.joyce.example.controller.TestController;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/10/14-- 22:53
 * @describe:
 */
public class Joyce {
    static String str = " yytjC003, yytjC003, 13WGM01, 13ZSadmin, 13ryy, CSsmkTJ01, CSsmkTJ02, CSsmkTJ03, KF02200000, KF0220gnty, KF02210019, KF02210020, KF02210021, KF02210024, KF02211004, KF02211010, KF02211011, KF02211024, KF02211027, KF02211030, KF02211062, KF02211080, KF02211081, KF02211082, KF02211083, KF02211084, KF02211085, KF02211087, KF02211088, KF02211089, KF02211090, KF02211091, KF02211092, KF02211093, KF02211094, KF02211095, KF02211096, KF02211097, KF02211099, KF02211113, KF02212000, KF02213066, KF02230052, KF022NK001, KF022NK002, KF022NK003, KF022NK005, KFtest, KFtest124, Mark13, 641593e858ac41d098bb38b925b8461f, 0a216a9568bf4a80a42d971f4967b214, defe5407117e44bf858fa7070838d77e, bfe92da4a3e5417fac29b421def91119, 082c317f17c4483ba4082aa7c9bbae68, 3a4b73ee43f04fc0953ebf973c4eaf0c, 37ebe4bc06db4213ab8a86cb9bf3c8c7, 7c31b7a9a38542ba84567c60642de3de, a6e21498e8f54be3b319386e916e43fc, 773166165e8d4ea18721325f87814590";
   public static void main(String[] args) {
       String str2 = "kg,pdj,hm,old,nma,go,n,s,a,f,s,i,o,n,alMF[JONDFMGSLNGIJDFNAF;,F";
       char[] chars = str2.toCharArray();
       ArrayList<Integer> demos = new ArrayList<>();
       for (int i = 0; i < chars.length; i++) {
           if (String.valueOf(chars[i]).equals(",")){
               demos.add(i);
           }
       }
       System.out.println(demos.toString());

   }

    public static String str2(String str){
        return str.replaceAll("[^(\\一-\\龥)]", "").replace("\uE67C","").trim();
    }

}
