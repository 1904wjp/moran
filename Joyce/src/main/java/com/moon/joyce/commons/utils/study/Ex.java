package com.moon.joyce.commons.utils.study;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: XingDaoRong
 * @Date: 2022/3/23
 */
public class Ex {
    @Test
    public void insertSort(){
        int[] array = getArray(6);
        System.out.println("初始:"+Arrays.toString(array));
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int tempi= i;
            while (tempi>0&&temp<array[tempi-1]){
                  array[tempi]=array[tempi-1];
                  tempi=tempi-1;
            }
             array[tempi] = temp;
            System.out.println("过程:"+Arrays.toString(array));
        }
        System.out.println("结束:"+Arrays.toString(array));
    }
    public static int[] getArray(int len){
        int[] array = new int[len];
        for (int i = 0; i < len; i++) {
            array[i] = (int)(Math.random()*100);
        }
        return array;
    }
    @Test
   public void test01(){

        int[] nums = {1,2,7,8,10};
       int target = 9;
       Map<Integer, Integer> map = new HashMap<>();
       map.put(nums[0],0);

       int[] ints = new int[2];
       for (int i = 1; i < nums.length; i++) {
             if (map.containsKey(target-nums[i])){
                 ints[1] = i;
                 ints[0] = map.get(target-nums[i]);
                 break;
             }else {
                 map.put(nums[i],i);
             }
       }
       System.out.println(Arrays.toString(ints));
   }

}
