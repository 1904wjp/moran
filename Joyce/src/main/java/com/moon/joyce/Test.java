package com.moon.joyce;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/17
 */
public class Test {
    public static void main(String[] args) {

    }

    public static void arrayFunction(int[] arr){
        int eor = 0;
        for (int a : arr) {
            eor ^=a;
        }
        System.out.println(eor);
        int rightOne = eor & (~eor+1);
        System.out.println(rightOne);
        int onlyOne = 0;
        for (int a : arr) {
            if ((a & rightOne) ==0){
                onlyOne ^= a;
            }
        }
        System.out.println(onlyOne +":::"+(eor^onlyOne));
    }
}
