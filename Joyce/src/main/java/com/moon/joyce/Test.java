package com.moon.joyce;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: XingDaoRong
 * @Date: 2021/12/17
 */
public class Test {
    public static void main(String[] args) {
        String path = "E:\\Program Files\\project\\data_center\\dc-modules\\modules-system\\src\\main\\java\\com\\wr\\source\\entity";
        File file = new File(path);
        File[] files = file.listFiles();
        int i = 0;
        int max = 0;
        while (i+19<143){
            max=i+19;
            System.out.println(i+1+":"+files[i+1].getName()+"::"+max+":"+files[max].getName());
            i+=19;
        }
    }

    public static void getContext(){
        File file = new File("E:\\寒门状元");
        if (!file.exists()){
            file.mkdir();
        }
        String url = "http://www.biquge5200.com/31_31746/12331189.html";
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
                fileWriter = new FileWriter("E:\\寒门状元\\第"+(i+1)+"章.txt");
                fileWriter.write(title);
                fileWriter.write(text);
                fileWriter.close();
                Thread.sleep(1000);
                System.out.println("第"+(i+1)+"章");
            } catch (IOException | InterruptedException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            i++;
        }

    }
    /**
     * 获取链接的document对象
     * @param url
     * @return document
     */
    public static Document getDoc(String url) {
        boolean flag = false;
        Document document = null;
        do{
            try {
                document=Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                        .timeout(5000)
                        .get();
                flag = false;
            } catch (IOException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
                flag = true;
            }
        }while(flag);
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
}
