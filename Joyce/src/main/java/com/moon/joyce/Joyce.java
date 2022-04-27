package com.moon.joyce;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moon.joyce.commons.utils.FileUtils;
import com.moon.joyce.commons.utils.RedisUtils;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.entity.Source;
import com.moon.joyce.example.service.SourceService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/17
 */
public class Joyce {
    @Autowired
    private SourceService sourceService;
    @Value("${file.upload.path}")
    String filePath;
    public static void main(String[] args) {


    }

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

        String code = "451dasd";
        Jedis instance = RedisUtils.getInstance();
        String currentCode = "current_code_user";
        instance.setex(currentCode, 10, code);
        int i = 0;
        while (i < 5) {
            String user = RedisUtils.getVerifyCode(instance, currentCode, "333", 3, 60 * 20);
            System.out.println(user);
            i++;
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        instance.close();
    }
    @org.junit.Test
    public void test0101(){
        print(-5);
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
}
