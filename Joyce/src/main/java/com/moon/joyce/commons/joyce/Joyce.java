package com.moon.joyce.commons.joyce;


import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 晚上偷零食
 * @autograph: Logic is justice
 * @date: 2023/9/29-- 12:01
 * @describe:
 */
public class Joyce {
    public static void main(String[] args) {
      String [] titleArray = {"","param1","param2","param3","param4"};
      createExcelTable("C:\\Users\\...\\Desktop\\work.xlsx","Sheet1",Arrays.asList(titleArray),getLists(79,100,1000));
    }

    /**
     * 创建excel
     * @param filePath 文件路径（可以不存在）
     * @param sheetName sheet名字
     * @param titleRow 头部名称行
     * @param lists 内容数组
     */
    public static void createExcelTable(String filePath, String sheetName, List<String> titleRow,List<List<String>> lists){

        XSSFWorkbook xw = new XSSFWorkbook();
        FileOutputStream fileOutputStream = null;
        XSSFRow row = null;
        try {
            File file = new File(filePath);
            if (!file.exists()){
                file.createNewFile();
            }
        XSSFCellStyle cellStyle = xw.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
        XSSFSheet sheet = xw.createSheet(sheetName);
         row = xw.getSheet(sheetName).createRow(0);
        short height = 300;
        row.setHeight(height);
        for (int i = 0; i < titleRow.size(); i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(titleRow.get(i));
            cell.setCellStyle(cellStyle);
        }
        for (int i = 0; i < lists.size(); i++) {
            XSSFRow cont = null;
            cont = xw.getSheet(sheetName).createRow(i + 1);
            for (int j = 0; j < lists.get(i).size(); j++) {
                XSSFCell cell = cont.createCell(j);
                cell.setCellValue(lists.get(i).get(j));
                cell.setCellStyle(cellStyle);
            }
        }
            fileOutputStream = new FileOutputStream(filePath);
            xw.write(fileOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 自定义内容数组
     * @param min 最小数
     * @param max  最大数
     * @param defSize 多少条数据
     * @return
     */
    public static List<List<String>> getLists(int min,int max,int defSize){
          List<List<String>> lists = new ArrayList<>();
        for (int i = 0; i < defSize; i++) {
            double param0 = getRandNum(min, max);
            double param1 = getRandNum(min, max);
            double param2 = getRandNum(min, max);
            double param3 = (param1+param2+param0)/3;
            param3  = Double.parseDouble(String.format("%.1f", param3));
            List<String> integers = new ArrayList<>();
            integers.add(String.valueOf(i+1));
            integers.add(String.valueOf(param3));
            integers.add(String.valueOf(param2));
            integers.add(String.valueOf(param1));
            integers.add(String.valueOf(param0));
            lists.add(integers);
        }
        return lists;
    }

    /**
     * 获取min-max随机数
     * @param min
     * @param max
     * @return
     */
    public static double getRandNum(float min,float max){
        double num =   Math.random()*(max- min)+ min;
        String format = String.format("%.1f", num);
        return Double.parseDouble(format);
    }


}
