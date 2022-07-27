package com.moon.joyce.commons.joyce;


import com.moon.joyce.commons.utils.*;
import com.moon.joyce.example.service.SourceService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/17
 */
public class Joyce {
    @Value("${file.upload.path}")
    String filePath;


    /**
     * 死循环
     * @return
     */
    public static int[][] addHeap()  {
        int[][] intervals = {{1,3},{2,6},{8,10},{15,18},{7,9}};
        int couti = 0;
        int count = 0;
        if (intervals.length<=1){
            return intervals;
        }
        //排序
       for (int i = 0; i < intervals.length-1; i++) {
            if(intervals[i][1]>intervals[i+1][1]){
                int temp [] = intervals[i];
                intervals[i]=intervals[i+1];
                intervals[i+1] = temp;
            }
        }

        for (int i = 0; i < intervals.length-1; i++) {
            if (intervals[i][1]>=intervals[i+1][0]){
                count++;
            }
        }

        int[][] merge =   new int[intervals.length-count][2];
        for (int i = 0; i < intervals.length-1; i++) {
            if (intervals[i][1]>=intervals[i+1][0]){
                merge[couti] = getMaxAndMinValue(intervals[i][0],intervals[i][1],intervals[i + 1][0],intervals[i + 1][1]);
                i++;
            }else{
                merge[couti] = new int[]{intervals[i][0], intervals[i][1]};
            }
            if(intervals[intervals.length-2][1]<intervals[intervals.length-1][0]){
                merge[intervals.length-count-1] = new int[]{intervals[intervals.length-1][0], intervals[intervals.length-1][1]};
            };
            couti++;
        }
        return merge;
    }
    public static int[] getMaxAndMinValue(int var1,int var2,int var3,int var4){
         int[] arry= { var1, var2, var3, var4};
         int max = var1;
         int min = var1;
        for (int i = 0; i < arry.length; i++) {
            if (arry[i]>max){
                max = arry[i];
            }
            if (arry[i]<min){
                min = arry[i];
            }
        }
        return new int[]{min,max};
    }

   public static  int[][] merge(){
       int[][] intervals = {{4,5},{2,4},{4,6},{3,4},{0,0},{1,1},{3,5},{2,2}};
       if (intervals.length<=1){
           return intervals;
       }

       //排序
       while (true){
           int tempi = 0;
       for (int i = 0; i < intervals.length-1; i++) {
           if(intervals[i][1]>intervals[i+1][1]){
               tempi ++;
               int temp [] = intervals[i];
               intervals[i]=intervals[i+1];
               intervals[i+1] = temp;
           }
       }
       if (tempi==0){
           break;
       }
       }

        for (int i = 0; i < intervals.length-1; i++) {
           if (intervals[i][1]>=intervals[i+1][0]){
           }
       }
       while (true){
           int tempCount = 0;
           for (int i = 0; i < intervals.length-1; i++) {
           if (intervals[i][1]>=intervals[i+1][0]){
                tempCount = tempCount+1;
               intervals[i]= getMaxAndMinValue(intervals[i][0],intervals[i][1],intervals[i + 1][0],intervals[i + 1][1]);
               System.arraycopy(intervals, i + 1 + 1, intervals, i + 1, intervals.length - 1 - (i + 1));
               intervals = rm(intervals, intervals.length - 1);
            }
           }

           if (tempCount==0){
               break;
           }
       }

       return intervals;
   }

    /**
     * 数组删除
     * @param objects
     * @param object
     * @return
     */
    public static <T> Object[] rm(T[] objects, T object)  {
        int count = 0;
        for (Object o : objects) {
            if (o == object) {
                count++;
            }
        }
        if (count == 0) {
            return objects;
        }

        int temp = count;
        while (count != 0) {
            for (int j = 0; j < objects.length - 1; j++) {
                if (objects[j] == object) {
                    if (objects.length - 1 - j >= 0) {
                        System.arraycopy(objects, j + 1, objects, j, objects.length - 1 - j);
                    }
                }
            }
            count--;
        }
        Object[] newObjs = new Object[objects.length - temp];
        System.arraycopy(objects, 0, newObjs, 0, newObjs.length);
        return newObjs;
    }

    public static int[][]  rm(int[][] arrays,int index){
        int[][] ints = new int[arrays.length - 1][];
        for (int i = 0; i < arrays.length; i++) {
            if (index==i) {
                continue;
            }
            ints[i] = arrays[i];
        }
        return ints;
    }
    public static void getContext() {
        File file = new File("E:\\我的绝色美女房客");
        if (!file.exists()) {
            file.mkdir();
        }
        String url = "http://www.biqu5200.net/41_41858/16122457.html";
        int i = 0;
        while (i < 1739) {
            Document document = getDoc(url);
            String title = document.title();
            String text = document.select("#content").text();
            Elements nextdiv = document.select(".bottem2");
            String next = nextdiv.toString();
            String[] nexturl = next.split("下一章");
            next = nexturl[0];
            nexturl = next.split("\u2192");
            next = nexturl[1];
            next = next.substring(12, next.length() - 2);

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter("E:\\寒门状元\\第" + (i + 1) + "章.txt");
                fileWriter.write(title);
                fileWriter.write(text);
                fileWriter.close();
                Thread.sleep(1000);
                System.out.println("第" + (i + 1) + "章");
            } catch (IOException | InterruptedException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            i++;
        }

    }

    /**
     * 获取链接的document对象
     *
     * @param url
     * @return document
     */
    public static Document getDoc(String url) {
        boolean flag = false;
        Document document = null;
        do {
            try {
                document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                        .timeout(5000)
                        .get();
                flag = false;
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
                flag = true;
            }
        } while (flag);
        return document;
    }


    /*public static void arrayFunction(int[] arr){
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
    }*/
    public static void task01() {
        String path = "E:\\Program Files\\project\\data_center\\dc-modules\\modules-system\\src\\main\\java\\com\\wr\\source\\entity";
        File file = new File(path);
        File[] files = file.listFiles();
        int i = 0;
        int max = 0;
        System.out.println(files[73].getName());
       /* while (true){
            max=i+19;
            System.out.println(i+1+":"+files[i+1].getName()+"::"+max+":"+files[max].getName());
            i+=19;
            if (i+19>=143){
                break;
            }
        }*/
    }

    public static void task02() {
        BufferedReader readObj = FileUtils.getReadObj("E:\\Program Files\\project\\data_center\\dc-modules\\modules-system\\src\\main\\java\\com\\wr\\source\\web\\SourceController.java");
        String text = null;
        List<String> list = new ArrayList<>();
        try {
            while ((text = readObj.readLine()) != null) {//使用readLine方法，一次读一行
                list.add(text);
            }
            readObj.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.contains("@GetMapping(\"/") && !list.get(i - 2).contains("/*")) {
                String url = s.substring(s.indexOf("/"), s.indexOf("\")"));
                String title = list.get(i - 1).substring(list.get(i - 1).indexOf("title = \"") + 9, list.get(i - 1).indexOf("\", "));
                url = "source" + url;
                System.out.println("list.add(new Source(" + index + "l,\"" + title + "\",\"" + url + "\"));");
                ++index;
            }
        }
    }

    public static void task03() {

        String code = StringsUtils.randNumber(6);
        Jedis instance = RedisUtils.getInstance();
        String currentCode = "current_code_user1";
        int i = 0;
        while (i < 100) {
            String rs = RedisUtils.getVerifyCode(instance, code, currentCode, 10, 5);
            System.out.println("====>"+rs);
            if ("0".equals(rs)){
               System.out.println("规定时间内只能获取100次");
            }
            if ("-1".equals(rs)||"-2".equals(rs)){
                rs = RedisUtils.getVerifyCode(instance, code, currentCode, 10, 5);
            }
            System.out.println(rs);
            i++;
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        instance.close();
    }

    public static void  print(int num){
        //打印32位2进制方法，负数第32位为1，范围（-(2^-31)~(2^(31)-1)）
        //负数需要取反+1
        String sum="";
        for(int i=31;i>=0;i--){
            sum = sum + (((num&(1<<i))==0)?0:1);
        }
        System.out.println(sum);
    }


    public static void  test112(int num){
        int month =  num*12;
        int fee = 0;
        if(month>5){
            fee = 5*9+(month-5)*29;
        }
        System.out.println(fee-270);
    }

    public static void  test113(int num){
        int month =  num*12;
        int fee = (month-1)*19;
        System.out.println(fee-40);
    }

    public static  void test114(){
        int[] arr1 = {1,2,3,4,5,6};
        int[] arr2 = {8,9,10,11,6};
        System.arraycopy(arr1,1,arr2,2,2);
        System.out.println(Arrays.toString(arr2));
    }

    @org.junit.Test
    public void test0101() throws ParseException {
       int s= -124654640;
        String s1 = String.valueOf(s);
        char[] chars = s1.toCharArray();
        for (int i = 0; i < chars.length/2 ;i++) {
            char temp = chars[s1.length()-i-1];
            chars[s1.length()-i-1] = chars[i];
            System.out.println(chars[s1.length()-i-1] +":"+ chars[i]);
            chars[i] = temp;
        }
        String sum= "";
        for (int i = 0; i < chars.length ;i++) {
            sum = sum +chars[i];
        }
        if (sum.contains("-")){
            sum = sum.substring(0,sum.indexOf("-"));
            sum = "-"+sum;
        }
        System.out.println(Integer.valueOf(sum));
    }

    public static long reverse(int x) {
        System.out.println(x);
        String s1 = String.valueOf(x);
        StringBuilder builder = new StringBuilder(s1).reverse();

        char[] chars = s1.toCharArray();
        for (int i = 0; i < chars.length/2 ;i++) {
            char temp = chars[s1.length()-i-1];
            chars[s1.length()-i-1] = chars[i];
            chars[i] = temp;
        }
        String sum= "";
        for (int i = 0; i < chars.length ;i++) {
            sum = sum +chars[i];
        }
        if (sum.contains("-")){
            sum = sum.substring(0,sum.indexOf("-"));
            sum = "-"+sum;
        }

        return  Long.parseLong(sum);
    }

    public int lengthOfLongestSubstring(String s) {
        int[] end = new int[128];
        for(int i = 0;i<end.length;i++){
            end[i] = -1;
        }
        int start = 0;
        int rs = 0;
        for(int i=0;i<s.length();i++ ){
            int index = s.charAt(i);
            if(start<end[index]+1){
                start = end[index]+1;
            }
            if(rs<i-start+1){
                rs = i-start+1;
            }
            end[index] = i;
        }
        System.out.println(rs);
        return rs;
    }

    public static double getMedian(int[] num1,int[] num2){
        int[] num ={};
        if (num1.length==0){
            num = num2;
        } else if (num2.length==0){
            num = num1;
        }else {
            num = getSumNum(num1, num2);
        }
        double median = 0.00;
        if (num.length%2==0){
            median= (num[num.length/2-1]+num[num.length/2]) /(double)2;
        }else {
            median = num[num.length/2];
        }
        return median;
    }
    public static int[] getSumNum(int[] num1,int[] num2){
        int[] num = new int[num1.length+num2.length];
        int index = 0;
        for (int i = 0; i < num.length; i++) {
            if (i<num1.length){
                num[i] = num1[i];
            }else {
                num[i] = num2[index++];
            }
        }
        Arrays.sort(num);
        return num;
    }

    public static   double findMedianSortedArrays(int[] nums1, int[] nums2) {
        double median1 = 0.00;
        double median2 = 0.00;
        if (nums1.length!=0 && nums2.length!=0){
            median1 = getChildMedian(nums1);
            median2 = getChildMedian(nums2);
            return (median1+median2)/2;
        }
        if (nums2.length==0 ){
            return getChildMedian(nums1);
        }
        if (nums1.length==0 ){
            return getChildMedian(nums2);
        }
        return 0.00;

    }
    public static double getChildMedian(int[] num){
        double median = 0.00;
        if (num.length%2==0){
            median = (num[num.length/2]+num[num.length/2-1])/(double)2;
        }else {
            median = num[num.length/2];
        }
        return median;
    }
    @Test
    public void test0110(){
        String strs = "f,g,s,d,h,b,d,f,f,s,a,f,g";
        String[] split = strs.split(",");
        List<String> collect = Arrays.stream(split).collect(Collectors.toList());
        String s = StringsUtils.listToStr(collect);
        System.out.println(s);
    }
}
