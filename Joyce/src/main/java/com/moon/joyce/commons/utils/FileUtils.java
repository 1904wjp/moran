package com.moon.joyce.commons.utils;


import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.functionality.entity.doma.JoyceException;
import com.moon.joyce.example.functionality.entity.doma.PageComponent;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 17:53
 * @desc 文件工具类
 */
public class FileUtils extends org.apache.commons.io.FileUtils implements Serializable {
    private static final long serialVersionUID = 6813374997135795261L;

    private FileUtils() throws JoyceException {
        throw JoyceExceptionUtils.exception("工具类无法实例化");
    }

    //新建文件
    private static File newFile;
    public static List<File> fileList = new ArrayList<>();
    private static int BUFFER = 1024;

    /**
     * 文件上传工具类
     * @param file
     * @return
     */
    public static Map<String,String> fileUpLoad(MultipartFile file, String sysPath, String access) {
        if (Objects.isNull(file) || StringUtils.isBlank(sysPath) || StringUtils.isBlank(access)) {
            return null;
        }
        //uuid生成的唯一前缀 + 上传文件名 构成唯一的新文件名
        String fileName = UUIDUtils.getUUID(6) + "_" + StringsUtils.substringFileName(Objects.requireNonNull(file.getOriginalFilename()));
        //文件保存路径
        String now = DateUtils.dateForMat("dv", new Date());
        String path = sysPath + "1" + access + now;
        //新建文件filepath
        File filepath = new File(path, fileName);
        //判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            //将上传的文件file写入文件filepath
            File f1 = new File(path + File.separator + fileName);
            file.transferTo(f1);
            HashMap<String, String> pathMap = new HashMap<>();
            pathMap.put("r",f1.getPath());
            pathMap.put("v",StringUtils.isNoneBlank(fileName) ? "/static/1" + access + now + "/" + fileName : null);
            return pathMap;
        } catch (IOException e) {
            e.printStackTrace();
            deleteFile(filepath);
        }
        return null;
    }

    /**
     * 文件上传工具类
     * @param file
     * @return
     */
    public static Map<String, String> fileUpLoadRealDummy(MultipartFile file, String sysPath, String access) {
        if (Objects.isNull(file) || StringUtils.isBlank(sysPath) || StringUtils.isBlank(access)) {
            return null;
        }
        Map<String, String> map = new HashMap<>();
        //uuid生成的唯一前缀 + 上传文件名 构成唯一的新文件名
        String fileName = UUIDUtils.getUUID(6) + "_" + StringsUtils.substringFileName(Objects.requireNonNull(file.getOriginalFilename()));
        //文件保存路径
        String now = DateUtils.dateForMat("dv", new Date());
        String path = sysPath + "1" + access + now;
        //新建文件filepath
        File filepath = new File(path, fileName);
        //判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        map.put("real", filepath.getPath());
        try {
            //将上传的文件file写入文件filepath
            file.transferTo(new File(path + File.separator + fileName));
        } catch (IOException e) {
            e.printStackTrace();
            deleteFile(filepath);
            map.put("dummy", null);
            return map;
        }
        //将请求文件的相对路径返回
        map.put("dummy", StringUtils.isNoneBlank(fileName) ? "/static/1" + access + now + "/" + fileName : null);
        return map;
    }

    /**
     * 上传图片
     * @param files
     * @param sysPath
     * @param access
     * @return
     */
    public static List<String> fileUpLoad(MultipartFile[] files, String sysPath, String access) {
        List<String> list = new ArrayList<>();
        Set<String> set = new HashSet<>();

        for (MultipartFile file : files) {
            Map<String, String> map = null;
            try {
                map = fileUpLoadRealDummy(file, sysPath, access);
            } catch (Exception e) {
                e.printStackTrace();
                deleteFile(set);
            }
            list.add(map.get("dummy"));
            set.add(map.get("real"));
        }
        return list;
    }

    /**
     * xml配置的读取
     * @param filePathName
     * @return
     */
    public static Map<String, List<PageComponent>> readXmlConfig(String filePathName) {
        File file = createFile(filePathName);
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
                        if ("backgroundUrl".equals(field.attributeValue("name"))) {
                            pageComponent.setBackgroundUrl(field.attributeValue("value"));
                        }
                        if ("backgroundColor".equals(field.attributeValue("name"))) {
                            pageComponent.setBackgroundColor(field.attributeValue("value"));
                        }
                        if ("backgroundType".equals(field.attributeValue("name"))) {
                            pageComponent.setBackgroundType(field.attributeValue("value"));
                        }
                        if ("params".equals(field.attributeValue("name"))) {
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
        } catch (FileNotFoundException | DocumentException e) {
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
                Map<String, String> params = entry.getValue().get(i).getParams();
                for (Map.Entry<String, String> fEntry : params.entrySet()) {
                    setElementField(oElement, "map_field", fEntry.getKey(), fEntry.getValue());
                }
                setElementField(obj, "obj_field", "backgroundUrl", entry.getValue().get(i).getBackgroundUrl());
                setElementField(obj, "obj_field", "backgroundColor", entry.getValue().get(i).getBackgroundColor());
                setElementField(obj, "obj_field", "getBackgroundType", entry.getValue().get(i).getBackgroundType());
            }
        }
        return document;
    }

    /**
     * 设置属性值
     * @param element
     * @param elValue
     * @param name
     * @param value
     */
    private static void setElementField(Element element, String elValue, String name, String value) {
        Element childElement = element.addElement(elValue);
        childElement.addAttribute("name", name);
        if (StringUtils.isNotEmpty(value)) {
            childElement.addAttribute("value", value);
        }
    }

    /**
     * 写入文件
     * @param path
     * @param text
     */
    public static void writeFile(String path, String text) {
        System.out.println("---------->" + path);
        File file = createFile(path);
        if (!(file.getParentFile()).exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedWriter fw = null;
        try {
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, false), "UTF-8"));
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     * @param path
     * @param text
     */
    public static void writeFile(String path, String text, boolean append, String charsetName) {
        System.out.println("---------->" + path);
        File file = createFile(path);
        if (!(file.getParentFile()).exists()) {
            file.getParentFile().mkdirs();
        }
        BufferedWriter fw = null;
        try {
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, append), charsetName));
            fw.write(text);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void doCompress(String srcFile, String zipFile) throws IOException {
        doCompress(createFile(srcFile), createFile(zipFile));
    }

    public static ZipOutputStream getZipObj(String zipName, HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + zipName);
        return new ZipOutputStream(response.getOutputStream());
    }

    /**
     * 直接下载文件
     * @param path
     * @param response
     * @return
     */
    public static HttpServletResponse download(String path, HttpServletResponse response) {

        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return response;
    }


    /**
     * 压缩文件并且下载
     * @param zipName
     * @param response
     * @param map
     * @throws IOException
     */
    public static void downloadZip(String zipName, HttpServletResponse response, Map<String, String> map) throws IOException {
        ZipOutputStream out = null;
        out = FileUtils.getZipObj(zipName, response);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            FileUtils.doCompress(entry.getValue(), out);
            response.flushBuffer();
        }
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

    public static void doCompress(String filelName, ZipOutputStream out) throws IOException {
        File file = createFile(filelName);
        doCompress(file, out);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void doCompress(File file, ZipOutputStream out) throws IOException {
        doCompress(file, out, "");
    }

    public static void doCompress(File inFile, ZipOutputStream out, String dir) throws IOException {
        if (inFile.isDirectory()) {
            File[] files = inFile.listFiles();
            if (files != null && files.length > 0) {
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

        int len = 0;
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
        File file = createFile(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        if (tempList.length == 0) {
            return false;
        }
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = createFile(path + tempList[i]);
            } else {
                temp = createFile(path + File.separator + tempList[i]);
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
    public static boolean fileIsExists(String filePath) {
        File file = createFile(filePath);
        if (file.exists()) {
            return true;
        }
        return false;
    }


    /**
     * 导出
     * @param response   响应
     * @param fileName   文件名
     * @param columnList 每列的标题名
     * @param dataList   导出的数据
     */
    public static void exporExcel(HttpServletResponse response, String fileName, List<String> columnList, List<List<String>> dataList, String sheetName) {
        //声明输出流
        OutputStream os = null;
        //设置响应头
        HttpUtils.setResponseHeader(response, fileName);
        try {
            //获取输出流
            os = response.getOutputStream();
            //内存中保留1000条数据，以免内存溢出，其余写入硬盘
            SXSSFWorkbook wb = new SXSSFWorkbook(1000);
            if (Objects.isNull(sheetName)) {
                sheetName = "sheet1";
            }
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
     * @param response   响应
     * @param fileName   文件名
     * @param columnList 每列的标题名
     * @param map        对应名字导出的数据
     */
    public static void exporExcel2(HttpServletResponse response, String fileName, List<String> columnList, Map<Date, List<List<String>>> map) {
        //声明输出流
        OutputStream os = null;
        //设置响应头
        HttpUtils.setResponseHeader(response, fileName);
        //获取输出流
        try {
            os = response.getOutputStream();
            //内存中保留1000条数据，以免内存溢出，其余写入硬盘
            SXSSFWorkbook wb = new SXSSFWorkbook(1000);
            for (Map.Entry<Date, List<List<String>>> entry : map.entrySet()) {
                String sheetName = entry.getKey().toString();
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
            if (file.getName().endsWith(Constant.EXCEL_XLS)) {
                wb = new HSSFWorkbook(in);
            } else if (file.getName().endsWith(Constant.EXCEL_XLSX)) {
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
      /*  if (cell == null) {
            return ret;
        }
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
        }*/

        return ret; // 有必要自行trim
    }

    /**
     * XSSFCell转换成String并且去空格
     * @param cell
     * @return
     */
    public static String getCellValueOfTrim(Cell cell) {
        return getCellValue(cell).trim();
    }

    /**
     * 读取资源
     * @param path
     * @param charset
     * @return
     */
    public static BufferedReader getReadObj(String path, Charset charset) {
        System.out.println("我结束后需要关闭资源");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), charset));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bufferedReader;
    }

    /**
     * 读取资源
     * @param file
     * @param charset
     * @return
     */
    public static BufferedReader getReadObj(File file,Charset charset) {
        System.out.println("我结束后需要关闭资源");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),charset));
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
    public static int uploadVideo(HttpServletRequest req, HttpServletResponse resp, String fileUploadTempDir, String fileUploadDir) {
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
            if (index < total) {
                // 上传的文件分片名称
                file.transferTo(uploadFile);
                return 201;
            } else {
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

    public static String vrp = "videoRealPath";
    public static String vap = "videoAccessPath";
    public static String vn = "videoName";
    public static String vp = "videoPicturePath";

    public static Map<String, Object> mergeTempFile(String fileUploadTempDir, String sysPath, String access, String uuid, String newFileName) {

        Map<String, Object> map = new HashMap<>();
        try {
            File dirFile = createFile(fileUploadTempDir + "/" + uuid);
            if (!dirFile.exists()) {
                throw new JoyceException("文件不存在！");
            }
            //分片上传的文件已经位于同一个文件夹下，方便寻找和遍历(当文件数大于十的时候记得排序用冒泡排序确保顺序是正确的)
            String[] fileNames = dirFile.list();
            String createFileName = UUIDUtils.getUUID(6) + "_" + StringsUtils.substringFileName(newFileName);
            //文件保存路径
            String now = DateUtils.dateForMat("dv", new Date());
            String filePath = sysPath + "2" + access + now;
            File targetFile = new File(filePath, createFileName);
            if (!targetFile.getParentFile().exists()) {
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
            String path = "/static/2" + access + now + "/" + createFileName;
            map.put(vrp, targetFile.getPath());
            map.put(vap, path);
            map.put(vn, newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 截取视频文件某一帧
     * @param videoRealPath
     * @param pictureRealPath
     * @return
     */
    public static File captureVideoFrames(String videoRealPath, String pictureRealPath) throws IOException {
        File videoFile = createFile(videoRealPath);
        File pictureFile = createFile(pictureRealPath);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoFile);
        ff.start();
        int length = ff.getLengthInAudioFrames();
        int i = 0;
        Frame f = null;
        while (i < length) {
            f = ff.grabFrame();
            if ((i > 21) && (Objects.nonNull(f.image))) {
                break;
            }
            i++;
        }
        int owidth = f.imageWidth;
        int oheight = f.imageHeight;
        // 对截取的帧进行等比例缩放
        int width = 800;
        int height = (int) (((double) width / owidth) * oheight);
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage fecthedImage = converter.getBufferedImage(f);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(fecthedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null);
        //ff.flush();
        ImageIO.write(bi, "jpg", pictureFile);
        ff.stop();
        return pictureFile;
    }


    /**
     * 通过目录获取文件集合
     * @param path
     * @return
     */
    public static List<File> getFilesByMkdirPath(String path) {
        File file = createFile(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files.length == 0){
                return fileList;
            }
            for (File f : files) {
                if (Objects.isNull(f)) {
                    continue;
                }
                getFilesByMkdirPath(f.getPath());
            }
        }
        if (file.isFile()) {
            fileList.add(file);
        }
        return fileList;
    }

    /**
     * 创建文件
     * @param path
     * @param bool 是否使用懒汉模式
     * @return
     */
    public static File createFile(String path, boolean bool) {
        if (!bool) {
            newFile = new File(path);
        }
        if (Objects.isNull(newFile) && bool) {
            synchronized (Objects.requireNonNull(newFile)) {
                if (Objects.isNull(newFile)) {
                    newFile = new File(path);
                }
            }
        }
        if (!newFile.getParentFile().exists()) {
            newFile.mkdirs();
        }
        return newFile;
    }

    /**
     * 获取文件全路径名称
     * @param filePath
     * @return
     */
    public static String getFilePathName(String filePath) {
        String suf = filePath.substring(filePath.indexOf("."));
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        StringBuilder builder = new StringBuilder();
        builder.append(filePath, 0, filePath.indexOf("."));
        int index = 0;
        while (file.exists()) {
            builder.append("(").append(++index).append(")");
            String tempPath = builder.toString();
            filePath = tempPath + suf;
            file = new File(filePath);
        }
        return filePath;
    }

    /**
     * 创建文件
     * @param path
     * @return
     */
    public static File createFile(String path) {
        return createFile(path, false);
    }

    /**
     * 创建新文件
     * @param path
     */
    public static File createNewFile(boolean isDel,String path, boolean isDir) {
        File file = createFile(path);
        if (isDel){
            deleteFile(file);
        }
        createNewFile(file, isDir);
        return file;
    }
    /**
     * 创建新文件
     * @param path
     */
    public static File createNewFile(String path, boolean isDir) {
        File file = createFile(path);
        createNewFile(file, isDir);
        return file;
    }

    /**
     * 创建新文件
     * @param path
     * @param bool
     */
    public static void createNewFile(String path, boolean bool, boolean isDir) {
        File file = createFile(path, bool);
        createNewFile(file, isDir);
    }

    /**
     * 创建新文件
     * @param file
     */
    public static void createNewFile(File file, boolean isDir) {
        if (!file.exists()) {
            if (isDir) {
                file.mkdirs();
            } else {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 更新文件
     * @param filePath
     * @param flag
     * @return
     */
    public static boolean updateFile(String filePath, boolean flag) {
        return updateFile(createFile(filePath), flag);
    }

    /**
     * 更新文件
     * @param file
     * @param flag
     * @return
     */
    public static boolean updateFile(File file, boolean flag) {
        String path = file.getPath();
        if (flag) {
            try {
                deleteFile(file);
                createNewFile(path,file.isDirectory());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 删除文件
     * @param paths
     * @return
     */
    public static boolean deleteFile(Set<String> paths) {
        boolean flag = true;
        for (String path : paths) {
            if (StringUtils.isNoneBlank(path)) {
                throw new JoyceException("该文件路径不能为空");
            }
            boolean rs = deleteFile(path);
            if (!rs) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 删除文件
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        return deleteFile(file);
    }

    /**
     * 删除文件
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 规定时间类检测文件是否完成
     * @param path
     * @param time
     * @return
     */
    public static boolean fileExist(String path, long time) {
        File file = createFile(path);
        return fileExist(file, time);
    }

    /**
     * 规定时间类检测文件是否完成
     * @param file
     * @param time
     * @return
     */
    public static boolean fileExist(File file, long time) {
        long t = 0L;
        boolean flag = false;
        while (t * 1000 <= time) {
            if (file.exists()) {
                flag = true;
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t++;
        }
        return flag;
    }

    /**
     * 解压zip文件
     * @param filePath
     * @param zipDir
     * @return
     */
    public static int unzip(String filePath, String zipDir) throws IOException {
        int tempCount = 0;
        String name = "";
        ZipEntry entry;
        BufferedInputStream is = null;
        BufferedOutputStream dest = null;
        ZipFile zipfile = new ZipFile(filePath);
        Enumeration<? extends ZipEntry> dir = zipfile.entries();
        while (dir.hasMoreElements()) {
            entry = (ZipEntry) dir.nextElement();
            if (entry.isDirectory()) {
                name = entry.getName();
                name = name.substring(0, name.length() - 1);
                File fileObject = new File(zipDir + name);
                fileObject.mkdir();
            }
        }

        Enumeration<? extends ZipEntry> e = zipfile.entries();
        while (e.hasMoreElements()) {
            entry = (ZipEntry) e.nextElement();
            if (entry.isDirectory()) {
                continue;
            } else {
                is = new BufferedInputStream(zipfile.getInputStream(entry));
                int count;
                byte[] dataByte = new byte[BUFFER];
                FileOutputStream fos = new FileOutputStream(zipDir + entry.getName());
                dest = new BufferedOutputStream(fos, BUFFER);
                while ((count = is.read(dataByte, 0, BUFFER)) != -1) {
                    dest.write(dataByte, 0, count);
                }
                tempCount++;
            }
        }
        dest.flush();
        dest.close();
        is.close();
        return tempCount;
    }

    /**
     * 执行py文件
     * @param execArgs
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static String execPythonFile(ExecArgs execArgs) throws IOException, InterruptedException {
        System.out.println("文件检测中。。。。");
        execArgs.setEnv("python");
        String pySuf = ".py";
        String startFileName = execArgs.getStartFileName();
        if (!startFileName.endsWith(pySuf)) {
            startFileName += pySuf;
        }
        File orFile = new File(execArgs.getFilePath());
        String pyFilePath = orFile.getParent() + "\\" + UUIDUtils.getUUIDName();
        createNewFile(pyFilePath, true);
        int fileCount = unzip(execArgs.getFilePath(), pyFilePath + "\\");
        List<File> dir = getFilesByMkdirPath(pyFilePath);
        while (dir.size() != fileCount) {
            Thread.sleep(1000);
            dir = getFilesByMkdirPath(pyFilePath);
        }
        boolean flag = false;
        for (File file : dir) {
            if (file.getName().equals(startFileName)) {
                execArgs.setFilePath(file.getPath());
                flag = true;
            }
        }
        if (!flag) {
            return "未找到启动文件";
        }
        return execPythonFile(execArgsToArray(execArgs));
    }

    /**
     * 执行py文件
     * @param args
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static String execPythonFile(String[] args) throws IOException, InterruptedException {
        Process exec = Runtime.getRuntime().exec(args);
        BufferedReader br = new BufferedReader(new InputStreamReader(exec.getInputStream()));
        String text = null;
        StringBuilder sum = new StringBuilder();
        while ((text = br.readLine()) != null) {
            sum.append(text);
        }
        br.close();
        exec.waitFor();
        System.out.println("py end");
        return sum.toString();
    }

    /**
     * 参数类
     */
    public static class ExecArgs {
        //文件环境
        private String env;
        //文件路径
        private String filePath;
        //参数数组
        private String[] args;
        //文件开启文件名
        private String startFileName;

        public String getEnv() {
            return env;
        }

        private void setEnv(String env) {
            this.env = env;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String[] getArgs() {
            return args;
        }

        public void setArgs(String[] args) {
            this.args = args;
        }

        public String getStartFileName() {
            return startFileName;
        }

        public void setStartFileName(String startFileName) {
            this.startFileName = startFileName;
        }
    }

    /**
     * 转化成字符数组
     * @param execArgs
     * @return
     */
    private static String[] execArgsToArray(ExecArgs execArgs) {
        System.out.println("py start。。。。");
        int len = 2;
        if (Objects.nonNull(execArgs.args)) {
            len = execArgs.args.length + 2;
        }
        String[] args = new String[len];
        args[0] = execArgs.getEnv();
        args[1] = execArgs.getFilePath();
        if (Objects.nonNull(execArgs.getArgs()) && args.length > 2) {
            for (int i = 2; i < args.length; i++) {
                args[i] = execArgs.getArgs()[i - 2];
            }
        }
        return args;
    }

    /**
     * 通过XWPFWordExtractor读取word(后缀名为docx的文件)
     * @return
     */
    public static String readWordDocx(String path){
        String buffer = "";
        try {
            if (path.endsWith(".doc")){
                InputStream is = new FileInputStream(path);
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
            }else if (path.endsWith(".docx")){
             // 如果poi版本在4.0.0及以上那么可以使用注释的方式
             	/*OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();*/
                FileInputStream fs = new FileInputStream(path);
                XWPFDocument xdoc = new XWPFDocument(fs);
                XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
                buffer = extractor.getText();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return buffer;
    }
    //使用MultipartFile 接收文件流
    public void importWord(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        String suff = fileName.substring(fileName.lastIndexOf(".") + 1);
        String content = "";
        if (suff.equals("docx")) {
            XWPFDocument xdoc = new XWPFDocument(inputStream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
            content = extractor.getText();
            System.out.println(content);
            extractor.close();
        } else if (suff.equals("doc")) {
            WordExtractor ex = new WordExtractor(inputStream);
            content = ex.getText();
            System.out.println(content);
            ex.close();
        } else {
            System.out.println("此文件不是word文件");
        }
    }


    /**
     * 读取pdf文件
     * @param file
     * @return
     */
   /* public static List<String> readPdf(File file){
        List<String> list = pdfDirToWord(file);
        List<String> textList = new ArrayList<>();
        for (String path : list) {
            String text = readWordDocx(path);
            if ("输入的不是pdf文件".equals(text)){
                text = path+text;
            }
            textList.add(text);
        }
        return textList;
    }
*/
    /**
     * pdf转word
     */
    // 涉及到的路径
    // 1、pdf所在的路径，真实测试种是从外部引入的

    // 2、如果是大文件，需要进行切分，保存的子pdf路径
    private final static String  splitPath = "./split/";

    // 3、如果是大文件，需要对子pdf文件一个一个进行转化
    private final static String docPath = "./doc/";

   /* public static List<String> pdfDirToWord(File file ){
        List<String> list = new ArrayList<>();
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if (files!=null){
                for (File f : files) {
                    pdfDirToWord(f);
                }
            }
        }else {
            String path = pdfToWord(file.getPath());
            list.add(path);
        }
        return list;
    }*/

    /*public static String pdfToWord(String srcPath) {
        // 4、最终生成的doc所在的目录，默认是和引入的一个地方，开源时对外提供下载的接口。
        String desPath = srcPath.substring(0, srcPath.length() - 4) + ".docx";
        boolean result = false;
        try {
            // 0、判断输入的是否是pdf文件
            //第一步：判断输入的是否合法
            boolean flag = isPDFFile(srcPath);
            //第二步：在输入的路径下新建文件夹
            boolean flag1 = create();

            if (flag && flag1) {
                // 1、加载pdf
                PdfDocument pdf = new PdfDocument();
                pdf.loadFromFile(srcPath);
                PdfPageCollection num = pdf.getPages();
                System.out.println("pdf分页数:"+num);
                // 2、如果pdf的页数小于11，那么直接进行转化
                if (num.getCount() <= 10) {
                    pdf.saveToFile(desPath, FileFormat.DOCX);
                }
                // 3、否则输入的页数比较多，就开始进行切分再转化
                else {
                    // 第一步：将其进行切分,每页一张pdf
                    pdf.split(splitPath + "test{0}.pdf", 0);

                    // 第二步：将切分的pdf，一个一个进行转换
                    File[] fs = getSplitFiles(splitPath);
                    for (int i = 0; i < fs.length; i++) {
                        PdfDocument sonpdf = new PdfDocument();
                        sonpdf.loadFromFile(fs[i].getAbsolutePath());
                        sonpdf.saveToFile(docPath + fs[i].getName().substring(0, fs[i].getName().length() - 4) + ".docx", FileFormat.DOCX);
                    }
                    //第三步：对转化的doc文档进行合并，合并成一个大的word
                    try {
                        result = mergeWord(docPath, desPath);
                        System.out.println(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("输入的不是pdf文件");
                return "输入的不是pdf文件";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4、把刚刚缓存的split和doc删除
            if (result == true) {
                clearFiles(splitPath);
                clearFiles(docPath);
            }
        }
        return desPath;
    }

*/
    private static boolean create() {
        File f = new File(splitPath);
        File f1 = new File(docPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        if (!f.exists()) {
            f1.mkdirs();
        }
        return true;
    }

    // 判断是否是pdf文件
    private static boolean isPDFFile(String srcPath2) {
        File file = new File(srcPath2);
        String filename = file.getName();
        if (filename.endsWith(".pdf")) {
            return true;
        }
        return false;
    }

    // 取得某一路径下所有的pdf
   /* private static File[] getSplitFiles(String path) {
        File f = new File(path);
        File[] fs = f.listFiles();
        if (fs == null) {
            return null;
        }
        return fs;
    }*/

    public static boolean mergeWord(String docPath, String desPath) {

        File[] fs = getSplitFiles(docPath);
        System.out.println(docPath);
       /* com.spire.doc.Document document = new Document(docPath + "test0.docx");

        for (int i = 1; i < fs.length; i++) {
            document.insertTextFromFile(docPath + "test" + i + ".docx", com.spire.doc.FileFormat.Docx_2013);
        }
        //第四步：对合并的doc进行保存2
        document.saveToFile(desPath);*/
        return true;
    }

    // 取得某一路径下所有的pdf
    private static File[] getSplitFiles(String path) {
        File f = new File(path);
        File[] fs = f.listFiles();
        if (fs == null) {
            return null;
        }
        return fs;
    }
    //删除文件和目录
    public static void clearFiles(String workspaceRootPath){
        File file = new File(workspaceRootPath);
        if(file.exists()){
            deleteFile2(file);
        }
    }
    public static void deleteFile2(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(int i=0; i<files.length; i++){
                deleteFile2(files[i]);
            }
        }
        file.delete();
    }

}

