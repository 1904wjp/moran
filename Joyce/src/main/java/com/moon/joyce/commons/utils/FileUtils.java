package com.moon.joyce.commons.utils;


import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.functionality.entity.JoyceException;
import com.moon.joyce.example.functionality.entity.PageComponent;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 17:53
 * @desc 文件工具类
 */
public class FileUtils implements Serializable {
    private static final long serialVersionUID = 6813374997135795261L;
    private FileUtils() throws JoyceException {
        throw JoyceExceptionUtils.exception("工具类无法实例化");
    }
    public static List<File> fileList =new ArrayList<>();
    /**
     * 文件上传工具类
     *
     * @param file
     * @return
     */
    public static String fileUpLoad(MultipartFile file, String sysPath, String access) {
        if (Objects.isNull(file) || StringUtils.isBlank(sysPath) || StringUtils.isBlank(access)) {
            return null;
        }
        //uuid生成的唯一前缀 + 上传文件名 构成唯一的新文件名
        String fileName = UUIDUtils.getUUID(6)  + "_" + StringsUtils.substringFileName(file.getOriginalFilename());
        //文件保存路径
        String now = DateUtils.dateForMat("dv", new Date());
        String path = sysPath +"1"+access + now;
        //新建文件filepath
        File filepath = new File(path, fileName);
        //判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            //将上传的文件file写入文件filepath
            file.transferTo(new File(path + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将请求文件的相对路径返回
        return "/static/1" + access +now + "/" + fileName;
    }

    /**
     * xml配置的读取
     * @param filePathName
     * @return
     */
    public static Map<String, List<PageComponent>> readXmlConfig(String filePathName) {
        File file = new File(filePathName);
        if (!file.exists()) {
            return null;
        }
        Map<String, List<PageComponent>> map = new HashMap<>();
        List<PageComponent> pageComponents = new ArrayList<>();
        String id = null;
        try {
            SAXReader saxReader = new SAXReader();
            FileInputStream fis = new FileInputStream(filePathName);
            Document document = saxReader.read(fis);
            Element rootElement = document.getRootElement();
            Iterator<Element> rootIterator = rootElement.elementIterator();
            while (rootIterator.hasNext()) {
                Element setting = rootIterator.next();
                id = setting.attributeValue("id");
                Iterator<Element> settingIterator = setting.elementIterator();
                while (settingIterator.hasNext()) {
                    PageComponent pageComponent = new PageComponent();
                    Element object = settingIterator.next();
                    pageComponent.setName(object.attributeValue("name"));
                    Iterator<Element> objectIterator = object.elementIterator();
                    while (objectIterator.hasNext()) {
                        Element field = objectIterator.next();
                        if (field.attributeValue("name").equals("backgroundUrl")) {
                            pageComponent.setBackgroundUrl(field.attributeValue("value"));
                        }
                        if (field.attributeValue("name").equals("backgroundColor")) {
                            pageComponent.setBackgroundColor(field.attributeValue("value"));
                        }
                        if (field.attributeValue("name").equals("backgroundType")) {
                            pageComponent.setBackgroundType(field.attributeValue("value"));
                        }
                        if (field.attributeValue("name").equals("params")) {
                            Iterator<Element> fieldIterator = field.elementIterator();
                            Map<String, String> fieldMap = new HashMap<>();
                            while (fieldIterator.hasNext()) {
                                Element listField = fieldIterator.next();
                                if (Objects.nonNull(listField)) {
                                    fieldMap.put(listField.attributeValue("name"), listField.attributeValue("value"));
                                }
                            }
                            pageComponent.setParams(fieldMap);
                        }
                    }
                    pageComponents.add(pageComponent);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        map.put(id, pageComponents);
        return map;
    }

    /**
     * xml配置的初始化和更改
     * @param filePathName
     * @return
     */
    public static void writeConfigXml(String filePathName, Map<String, List<PageComponent>> map) {
        Document document = createXmlFactory(map);
        try {
            XMLWriter xmlWriter2 = new XMLWriter();
            xmlWriter2.write(document);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            XMLWriter xmlwriter = new XMLWriter(new FileOutputStream(filePathName), format);
            xmlwriter.write(document);
            xmlwriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建xml节点工厂
     *
     * @param map
     * @return
     */
    private static Document createXmlFactory(Map<String, List<PageComponent>> map) {
        //创建文档并设置文档的根元素节点
        Element root = DocumentHelper.createElement("settings");
        Document document = DocumentHelper.createDocument(root);
        //根节点
        root.addAttribute("name", "settings");
        //创建子节点
        for (Map.Entry<String, List<PageComponent>> entry : map.entrySet()) {
            Element element = root.addElement("setting");
            if (StringUtils.isNotEmpty(entry.getKey())) {
                element.addAttribute("id", entry.getKey());
            }
            for (int i = 0; i < entry.getValue().size(); i++) {
                Element obj = element.addElement("object");
                obj.addAttribute("name", entry.getValue().get(i).getName());
                Element oElement = obj.addElement("obj_map");
                oElement.addAttribute("name", "params");
                for (Map.Entry<String, String> fEntry : entry.getValue().get(i).getParams().entrySet()) {
                    Element fsElement = oElement.addElement("map_field");
                    if (StringUtils.isNotEmpty(fEntry.getKey())) {
                        fsElement.addAttribute("name", fEntry.getKey());
                    }
                    fsElement.addAttribute("value", fEntry.getValue());
                }

                if (StringUtils.isNotEmpty(entry.getValue().get(i).getBackgroundUrl())) {
                    Element backgroundUrl = obj.addElement("obj_field");
                    backgroundUrl.addAttribute("name", "backgroundUrl");
                    backgroundUrl.addAttribute("value", entry.getValue().get(i).getBackgroundUrl());
                }

                if (StringUtils.isNotEmpty(entry.getValue().get(i).getBackgroundColor())) {
                    Element backgroundUrl = obj.addElement("obj_field");
                    backgroundUrl.addAttribute("name", "backgroundColor");
                    backgroundUrl.addAttribute("value", entry.getValue().get(i).getBackgroundColor());
                }

                if (StringUtils.isNotEmpty(entry.getValue().get(i).getBackgroundType())) {
                    Element backgroundUrl = obj.addElement("obj_field");
                    backgroundUrl.addAttribute("name", "backgroundType");
                    backgroundUrl.addAttribute("value", entry.getValue().get(i).getBackgroundType());
                }
            }
        }
        return document;
    }

    /**
     * 写入文件
     * @param path
     * @param text
     */
    public static void writeFile(String path,String text){
        File file = new File(path);
        if (file.exists()){
            file.delete();
        }
        if (!(file.getParentFile()).exists()){
            file.getParentFile().mkdirs();
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doCompress(String srcFile, String zipFile) throws IOException {
        doCompress(new File(srcFile), new File(zipFile));
    }

    /**
     * 文件压缩
     * @param srcFile 目录或者单个文件
     * @param zipFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File zipFile) throws IOException {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipFile));
            doCompress(srcFile, out);
        } catch (Exception e) {
            throw e;
        } finally {
            out.close();//记得关闭资源
        }
    }

    public static void doCompress(String filelName, ZipOutputStream out) throws IOException{
        File file = new File(filelName);
        doCompress(file, out);
        if (file.exists()){
            file.delete();
        }
    }

    public static void doCompress(File file, ZipOutputStream out) throws IOException{
        doCompress(file, out, "");
    }

    public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
        if ( inFile.isDirectory() ) {
            File[] files = inFile.listFiles();
            if (files!=null && files.length>0) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir + "/" + name;
                    }
                    FileUtils.doCompress(file, out, name);
                }
            }
        } else {
            FileUtils.doZip(inFile, out, dir);
        }
    }

    public static void doZip(File inFile, ZipOutputStream out, String dir) throws IOException {
        String entryName = null;
        if (!"".equals(dir)) {
            entryName = dir + "/" + inFile.getName();
        } else {
            entryName = inFile.getName();
        }
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);

        int len = 0 ;
        byte[] buffer = new byte[1024];
        FileInputStream fis = new FileInputStream(inFile);
        while ((len = fis.read(buffer)) > 0) {
            out.write(buffer, 0, len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }

    /**
     * 删除文件夹下所有的文件，不删除文件夹
     * @param path
     * @return
     */
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        if (tempList.length==0){
            return false;
        }
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }

            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                delAllFile(path + "/" + tempList[i]);
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检测文件是否存在
     * @param filePath
     * @return
     */
    public static boolean fileIsExists(String filePath){
        File file = new File(filePath);
        if (file.exists()){
            return true;
        }
        return false;
    }


    /**
     * 导出
     * @param response 响应
     * @param fileName 文件名
     * @param columnList 每列的标题名
     * @param dataList 导出的数据
     */
    public static void exporExcel(HttpServletResponse response, String fileName, List<String> columnList,List<List<String>> dataList,String sheetName){
        //声明输出流
        OutputStream os = null;
        //设置响应头
        HttpUtils.setResponseHeader(response,fileName);
        try {
            //获取输出流
            os = response.getOutputStream();
            //内存中保留1000条数据，以免内存溢出，其余写入硬盘
            SXSSFWorkbook wb = new SXSSFWorkbook(1000);
            if (Objects.isNull(sheetName)){
                sheetName = "sheet1";
            }
            //获取该工作区的第一个sheet
            Sheet sheet1 = wb.createSheet(sheetName);
            int excelRow = 0;
            //创建标题行
            Row titleRow = sheet1.createRow(excelRow++);
            for(int i = 0;i<columnList.size();i++){
                //创建该行下的每一列，并写入标题数据
                Cell cell = titleRow.createCell(i);
                cell.setCellValue(columnList.get(i));
            }
            //设置内容行
            if(dataList!=null && dataList.size()>0){
                //序号是从1开始的
                int count = 1;
                //外层for循环创建行
                for(int i = 0;i<dataList.size();i++){
                    Row dataRow = sheet1.createRow(excelRow++);
                    //内层for循环创建每行对应的列，并赋值
                    for(int j = -1;j<dataList.get(0).size();j++){//由于多了一列序号列所以内层循环从-1开始
                        Cell cell = dataRow.createCell(j+1);
                       if(j==-1){//第一列是序号列，不是在数据库中读取的数据，因此手动递增赋值
                            cell.setCellValue(count++);
                        }else{//其余列是数据列，将数据库中读取到的数据依次赋值*/
                            cell.setCellValue(dataList.get(i).get(j));
                        }
                    }
                }
            }
            //将整理好的excel数据写入流中
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭输出流
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 导出
     * @param response 响应
     * @param fileName 文件名
     * @param columnList 每列的标题名
     * @param map 对应名字导出的数据
     */
    public static void exporExcel2(HttpServletResponse response, String fileName, List<String> columnList,Map<String,List<List<String>>> map ) {
        //声明输出流
        OutputStream os = null;
        //设置响应头
        HttpUtils.setResponseHeader(response, fileName);
        //获取输出流
        try {
            os = response.getOutputStream();
            //内存中保留1000条数据，以免内存溢出，其余写入硬盘
            SXSSFWorkbook wb = new SXSSFWorkbook(1000);
            for (Map.Entry<String, List<List<String>>> entry : map.entrySet()) {
                String sheetName = entry.getKey();
                List<List<String>> dataList = entry.getValue();
                //获取该工作区的第一个sheet
                Sheet sheet1 = wb.createSheet(sheetName);
                int excelRow = 0;
                //创建标题行
                Row titleRow = sheet1.createRow(excelRow++);
                for (int i = 0; i < columnList.size(); i++) {
                    //创建该行下的每一列，并写入标题数据
                    Cell cell = titleRow.createCell(i);
                    cell.setCellValue(columnList.get(i));
                }
                //设置内容行
                if (dataList != null && dataList.size() > 0) {
                    //序号是从1开始的
                    int count = 1;
                    //外层for循环创建行
                    for (int i = 0; i < dataList.size(); i++) {
                        Row dataRow = sheet1.createRow(excelRow++);
                        //内层for循环创建每行对应的列，并赋值
                        for (int j = -1; j < dataList.get(0).size(); j++) {//由于多了一列序号列所以内层循环从-1开始
                            Cell cell = dataRow.createCell(j + 1);
                            if (j == -1) {//第一列是序号列，不是在数据库中读取的数据，因此手动递增赋值
                                cell.setCellValue(count++);
                            } else {//其余列是数据列，将数据库中读取到的数据依次赋值*/
                                cell.setCellValue(dataList.get(i).get(j));
                            }
                        }
                    }
                }

            }
            //将整理好的excel数据写入流中
            wb.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭输出流
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 判断Excel的版本,获取Workbook
     * @param file
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) {
        Workbook wb = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            if(file.getName().endsWith(Constant.EXCEL_XLS)){
                wb = new HSSFWorkbook(in);
            }else if(file.getName().endsWith(Constant.EXCEL_XLSX)){
                wb = new XSSFWorkbook(in);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * XSSFCell转换成String
     */
    public static String getCellValue(Cell cell) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ret = "";
        if (cell == null) return ret;
        CellType type = cell.getCellTypeEnum();
        switch (type) {
            case BLANK:
                ret = "";
                break;
            case BOOLEAN:
                ret = String.valueOf(cell.getBooleanCellValue());
                break;
            case ERROR:
                ret = null;
                break;
            case FORMULA:
                Workbook wb = cell.getSheet().getWorkbook();
                CreationHelper crateHelper = wb.getCreationHelper();
                FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
                ret = getCellValue(evaluator.evaluateInCell(cell));
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date theDate = cell.getDateCellValue();
                    ret = simpleDateFormat.format(theDate);
                } else {
                    ret = NumberToTextConverter.toText(cell.getNumericCellValue());
                }
                break;
            case STRING:
                ret = cell.getRichStringCellValue().getString();
                break;
            default:
                ret = "";
        }

        return ret; // 有必要自行trim
    }

    /**
     * XSSFCell转换成String并且去空格
     */
    public static String getCellValueOfTrim(Cell cell) {
       return getCellValue(cell).trim();
    }


    public static BufferedReader getReadObj(String path ){
        System.out.println("我结束后需要关闭资源");
        BufferedReader bufferedReader = null;
        try {
           bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }
    public static BufferedReader getReadObj(File file ){
        System.out.println("我结束后需要关闭资源");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }

    /**
     * 上传视频（临时文件）
     * @param req
     * @param resp
     * @param fileUploadTempDir
     * @return
     */
   public static int uploadVideo(HttpServletRequest req, HttpServletResponse resp,String fileUploadTempDir,String fileUploadDir){
       resp.addHeader("Access-Control-Allow-Origin", "*");
       MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
// 获得文件分片数据
       MultipartFile file = multipartRequest.getFile("data");
// 分片第几片
       int index = Integer.parseInt(multipartRequest.getParameter("index"));
// 总片数
       int total = Integer.parseInt(multipartRequest.getParameter("total"));
// 获取文件名
       String fileName = multipartRequest.getParameter("name");
       String name = fileName.substring(0, fileName.lastIndexOf("."));

       String uuid = multipartRequest.getParameter("uuid");
       File uploadFile = new File(fileUploadTempDir + "/" + uuid, uuid + name + index + ".tem");
       if (!uploadFile.getParentFile().exists()) {
           uploadFile.getParentFile().mkdirs();
       }
       try {
           if (index<total){
               // 上传的文件分片名称
               file.transferTo(uploadFile);
               return 201;
           }else {
               file.transferTo(uploadFile);
               return 200;
           }
       } catch (IOException e) {
           e.printStackTrace();
           return 500;
       }
   }

    /**
     * 合并临时文件
     * @param fileUploadTempDir
     * @param access
     * @param uuid
     * @param newFileName
     * @return
     */
   public static String mergeTempFile(String fileUploadTempDir,String sysPath,String access,String uuid, String newFileName){
       String path = null;
       try {
           File dirFile = new File(fileUploadTempDir + "/" + uuid);
           if (!dirFile.exists()) {
               throw new JoyceException("文件不存在！");
           }
//分片上传的文件已经位于同一个文件夹下，方便寻找和遍历(当文件数大于十的时候记得排序用冒泡排序确保顺序是正确的)
           String[] fileNames = dirFile.list();
// 创建空的合并文件
         /*  File file = new File(sysPath);
           if (!file.exists()){
               file.mkdirs();
           }
*/
           String createFileName = UUIDUtils.getUUID(6)  + "_" + StringsUtils.substringFileName(newFileName);
           //文件保存路径
           String now = DateUtils.dateForMat("dv", new Date());
           String filePath = sysPath +"2"+access + now;
           File targetFile = new File(filePath, createFileName);
           if (!targetFile.getParentFile().exists()){
               targetFile.getParentFile().mkdirs();
           }
           RandomAccessFile writeFile = new RandomAccessFile(targetFile, "rw");
           int position = 0;
           for (String fileName : fileNames) {
               File sourceFile = new File(fileUploadTempDir + "/" + uuid, fileName);
               RandomAccessFile readFile = new RandomAccessFile(sourceFile, "rw");
               int chunksize = 1024 * 3;
               byte[] buf = new byte[chunksize];
               writeFile.seek(position);
               int byteCount = 0;
               while ((byteCount = readFile.read(buf)) != -1) {
                   if (byteCount != chunksize) {
                       byte[] tempBytes = new byte[byteCount];
                       System.arraycopy(buf, 0, tempBytes, 0, byteCount);
                       buf = tempBytes;
                   }
                   writeFile.write(buf);
                   position = position + byteCount;
               }
               readFile.close();
               //删除缓存的临时文件
               org.apache.commons.io.FileUtils.deleteQuietly(sourceFile);
           }
           writeFile.close();
           path= "/static/2" + access +now + "/" + createFileName;
       } catch (IOException e) {
           e.printStackTrace();
       }
       return path;
   }

    /**
     * 通过目录获取文件集合
     * @param path
     * @return
     */
   public static   List<File> getFilesByMkdirPath(String path){
       File file = new File(path);
       if (file.isDirectory()){
           File[] files = file.listFiles();
           for (File f : files) {
               getFilesByMkdirPath(f.getPath());
           }
       }
       if (file.isFile()){
           fileList.add(file);
       }
       return fileList;
   }
}

