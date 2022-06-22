package com.moon.joyce.example.functionality.service.serviceImpl;

import com.moon.joyce.commons.annotation.Column;
import com.moon.joyce.commons.annotation.Ids;
import com.moon.joyce.commons.annotation.NotExist;
import com.moon.joyce.commons.annotation.Table;
import com.moon.joyce.commons.factory.entity.ColumnEntity;
import com.moon.joyce.commons.factory.entity.TableEntity;
import com.moon.joyce.commons.factory.init.AutoCreateTableInit;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.example.functionality.entity.JoyceException;

import com.moon.joyce.example.functionality.service.TableFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/05/13-- 21:48
 * @describe: 自动创建表工厂_扫描所有相关类
 */
@Service
public class AutoCreateTableFactory implements TableFactory {

    //日志
    private  Logger logger = LoggerFactory.getLogger(this.getClass());
    //sql容器
    private static Map<TableEntity, List<ColumnEntity>> map = null;
    //计数标志
    private static int idValue = 0;
    //检测子文件容器
    private static Set<String> set = null;
    //sql容器
    private static List<String> sqls = null;
    //工厂对象
    private static AutoCreateTableFactory autoCreateTableFactory = null;
    //配置信息的容器
    private static Map<String,ColumnEntity> defMap = null;

    /**
     * 获取工厂实例
     * @param ps
     * @return
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public  AutoCreateTableFactory init(String ps)  {
        if (Objects.isNull(autoCreateTableFactory)){
            autoCreateTableFactory = new AutoCreateTableFactory();
        }
        if (Objects.isNull(map)){
            //初始化
            map =  new ConcurrentHashMap<>();
            set = new HashSet<>();
            sqls = new ArrayList<>();
            try {
                //读取配置
                InputStream in  = getClassLoader().getResourceAsStream("joyce.properties");
               defMap =  AutoCreateTableInit.readDefConfig(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //扫描包
            init(ps).scannerPackage(ps);
            //填充属性
            init(ps).fillSqls(null);
        }
        return autoCreateTableFactory;
    }

    /**
     * 扫描指定文件
     * @param packagePath
     * @throws ClassNotFoundException
     */
    private void scannerPackage(String packagePath) {
        String[] packages;
        if (packagePath.contains(";")){
            packages = StringUtils.split(packagePath, ";");
        }else {
            packages = new String[]{packagePath};
        }
        //检测文件是否有包含关系
        checkIsParentFile(packages);
        for (String aPackage : packages) {
            try {
                scanner(aPackage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检测里面是否有子文件
     * @param packages
     */
    private void checkIsParentFile(String[] packages) {
        Set<String> set = Arrays.stream(packages).collect(Collectors.toSet());
        if (set.contains(null) || set.contains("")){
            throw new NullPointerException("配置包不可含有null或者''");
        }
        if (packages.length<2){
            return;
        }
        for (int i = 0; i < packages.length; i++) {
            File f1 = getFile(packages[i]);
            for (int j = i+1; j < packages.length; j++) {
                File f2 = getFile(packages[j]);
                if (f1.equals(f2.getParentFile()) || f2.equals(f1.getParentFile())){
                    throw new JoyceException(packages[i]+"与"+packages[j]+"是相互包含的关系，必须选择一个");
                }
            }
        }
    }

    /**
     * 扫描指定文件并且填充属性
     * @param entityClassPackage
     * @throws ClassNotFoundException
     */
    private void scanner(String entityClassPackage)  {
        File dir = getFile(entityClassPackage);
        ClassLoader classLoader = getClassLoader();
        try {
            fillMap(dir,classLoader);
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 填充属性
     * @param dir
     * @param classLoader
     * @throws ClassNotFoundException
     */
    private  void fillMap(File dir,ClassLoader classLoader) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()){
                String filePath = file.getAbsolutePath();
                if (!filePath.endsWith(".class")){
                    continue;
                }
                String classPath = filePath.substring(filePath.indexOf("com"), filePath.indexOf(".class"));
                classPath = classPath.replace("\\", ".");
                Class<?> loadClass = classLoader.loadClass(classPath);
                if (loadClass.isAnnotationPresent(Table.class)){
                    //类注解获取并且填充
                    Table table = loadClass.getAnnotation(Table.class);
                    if (table.isParent()){
                        logger.warn(loadClass+"为被继承类，无法扫描到map容器中");
                        continue;
                    }
                    TableEntity tableEntity = new TableEntity();
                    tableEntity.setName(table.name());
                    tableEntity.setContent(table.content());
                    tableEntity.setStrategy(table.strategy().getCode().toString());
                    //检查是否有重复注解
                    check(tableEntity.getName());
                    //属性注解获取并且填充
                    Field[] fields = getFields(loadClass);
                    //储存属性的容器
                    List<ColumnEntity> list = new ArrayList<>();
                    List<Field> idsField = new ArrayList<>();
                    for (Field field : fields) {
                        //检测上是否有无关数据库的属性字段注解
                        NotExist notExist = field.getAnnotation(NotExist.class);
                        if (Objects.nonNull(notExist)){
                            if (notExist.require()){
                                continue;
                            }
                        }
                        //检测列属性字段注解并且填充
                        Column column = field.getAnnotation(Column.class);
                        if (Objects.nonNull(column)){
                            ColumnEntity columnEntity = new ColumnEntity();
                            columnEntity = createColumnByColumnAn(columnEntity, column);
                            list.add(columnEntity);
                        }else {
                            idsField.add(field);
                        }
                    }
                    //检测是否有ids注解是否存在
                    boolean rs = isHaveIds(fields);
                    if (rs){
                      List<ColumnEntity> tableEntityList = getTableEntitySuperList(fields,idsField);
                      list.addAll(tableEntityList);
                    }
                    List<ColumnEntity> newList = new ArrayList<>();
                    for (ColumnEntity columnEntity : list) {
                        if (Objects.isNull(columnEntity)){
                            continue;
                        }
                        ColumnEntity newColumnEntity = (ColumnEntity) BeanUtils.cloneBean(columnEntity);
                        newList.add(newColumnEntity);
                    }
                   if (Objects.nonNull(checkRepeatTableEntities(newList))){
                       throw new JoyceException("存在如下相同属性，无法创建:"+ checkRepeatTableEntities(newList));
                   }
                    map.put(tableEntity,newList);
                }
            }
            if (file.isDirectory()){
                fillMap(file,classLoader);
            }
        }
    }

    /**
     * 获取父类属性实体类集合
     * @param fields
     * @param idsField
     * @return
     */
    private List<ColumnEntity> getTableEntitySuperList(Field[] fields,List<Field> idsField) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        ++idValue;
        List<ColumnEntity> columnEntities = new ArrayList<>();
        Set<String> keySet = checkKey(fields);
        for (Field field : idsField) {
            ColumnEntity columnEntity = createColumnByIdsAn(field);
            if (Objects.nonNull(columnEntity)){
                ColumnEntity newColumnEntity = (ColumnEntity) BeanUtils.cloneBean(columnEntity);
                if (keySet.contains(columnEntity.getName())){
                    newColumnEntity.setComment("主键");
                    newColumnEntity.setKey(true);
                    newColumnEntity.setNotNull(true);
                }
                  newColumnEntity.setIdValue(idValue);
                  columnEntities.add(newColumnEntity);
            }
        }
        return columnEntities;
    }

    /**
     * 返回主键集合
     * @param fields
     * @return
     */
    private Set<String> checkKey(Field[] fields) {
        Set<String> keyList = new HashSet<>();
        String[] keys = new String[0];
        //Ids注解计数器
        for (Field field : fields) {
            Ids ids = field.getAnnotation(Ids.class);
            if (Objects.nonNull(ids)){
                keys = ids.values();
            }
            if (keys.length!=0){
                break;
            }
        }
        if (keys.length==0){
            return keyList;
        }
        //检测Ids中的属性是否有重复元素，有的话也会报错
        if (Arrays.stream(keys).collect(Collectors.toSet()).size()!=keys.length){
            throw new JoyceException("Ids注解中存在相同属性");
        }
        for (String key : keys) {
            for (Field field : fields) {
                if (key.equals(field.getName())){
                    keyList.add(key);
                }
            }
        }
        return keyList;
    }

    /**
     * 检测list中重复元素
     * @param list
     * @return
     */
    private List<ColumnEntity> checkRepeatTableEntities(List<ColumnEntity> list){
        if (Objects.isNull(list)){
            return null;
        }
        if (list.isEmpty()){
            return null;
        }
        if (list.stream().collect(Collectors.toSet()).size() == list.size()){
            return null;
        }
        List<ColumnEntity> columnEntities = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
           int  index = i+1;
           while (index < list.size()){
               if (list.get(i).equals(list.get(index))){
                   columnEntities.add(list.get(i));
                   index++;
               }
           }
        }
        if (!columnEntities.isEmpty()){
           return columnEntities;
        }
        return null;
    }

    /**
     * 获取类所有属性
     * @param loadClass
     * @return
     */
    private Field[] getFields(Class<?> loadClass) {
        Table table = loadClass.getAnnotation(Table.class);
        Field[] fields = loadClass.getDeclaredFields();
        Class<?> superclass = loadClass.getSuperclass();
        while (Objects.nonNull(superclass) ){
            if (Objects.nonNull(superclass.getAnnotation(Table.class))){
                Field[] superclassFields = superclass.getDeclaredFields();
                fields = addFields(fields, superclassFields);
            }
            superclass = superclass.getSuperclass();
        }
        return fields;
    }

    /**
     * 计算总属性集合
     * @param fs1
     * @param fs2
     * @return
     */
    private Field[] addFields(Field[] fs1,Field[] fs2)  {
        if (Objects.isNull(fs1)||fs1.length==0){
            return fs2;
        }
        if (Objects.isNull(fs2)||fs2.length==0){
            return fs1;
        }
        Field[] fields = new Field[fs1.length + fs2.length];
        int index = 0;
        for (int i = 0; i < fields.length; i++) {
            if (i < fs1.length){
                fields[i] = fs1[i];
            }else {
                fields[i] = fs2[index++];
            }
        }
        return fields;
    }

    /**
     * 根据column注解创建实体类
     * @param columnEntity
     * @param column
     */
    private ColumnEntity createColumnByColumnAn(ColumnEntity columnEntity, Column column){
        columnEntity.setAuto(column.auto());
        columnEntity.setComment(column.comment());
        columnEntity.setKey(column.isKey());
        columnEntity.setName(column.name());
        columnEntity.setLength(Long.valueOf(column.length()));
        columnEntity.setType(column.type().getStr());
        columnEntity.setNotNull(column.isNotNull());
        columnEntity.setDefaultValue(column.defaultValue());
        if (columnEntity.getAuto()){
            columnEntity.setKey(true);
        }
        if (column.isKey()){
            columnEntity.setNotNull(true);
        }
        return columnEntity;
    }

    /**
     * 根据ids注解创建实体类
     * @param field
     * @return
     */
    private ColumnEntity createColumnByIdsAn(Field field) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        String name = StringsUtils.toUnderScoreCase(field.getName());
        String type = field.getType().toString();
        type = type.substring(type.lastIndexOf(".") + 1);
        boolean rs = defMap.containsKey(type);
        if (rs){
            ColumnEntity columnEntity = (ColumnEntity) BeanUtils.cloneBean(defMap.get(type));
            columnEntity.setName(name);
            return  (ColumnEntity) BeanUtils.cloneBean(columnEntity);
        }
        return null;
    }

    /**
     * 判断是否有ids
     * @param fields
     * @return
     */
    private boolean isHaveIds(Field[] fields){
        for (Field field : fields) {
            Ids ids = field.getAnnotation(Ids.class);
            if (Objects.nonNull(ids)){
                return true;
            }
        }
        return false;
    }

    /**
     * 检测是否已存在
     * @param key
     * @return
     */
    private void check(String key){
       if (set.contains(key)){
           throw new JoyceException("实体类表名'"+key+"'已注册，无法重复创建");
       }
        set.add(key);
    }

    /**
     * 根据路径获取对应文件
     * @param packagePath
     * @return
     */
    private File getFile(String packagePath){
        ClassLoader classLoader = getClassLoader();
        String rePackageName = packagePath.replace(".", "/");
        URL resource = classLoader.getResource(rePackageName);
        return new File(resource.getFile());
    }

    /**
     * 根据路径获取加载
     */
    private ClassLoader getClassLoader(){
        return AutoCreateTableFactory.class.getClassLoader();
    }

    /**
     * 填充sqls
     */
    private void fillSqls( List<com.moon.joyce.example.functionality.entity.Column> existColumns){
        for (Map.Entry<TableEntity, List<ColumnEntity>> entry : map.entrySet()) {
            String[] sqlStrArr = selectCreateSql(entry.getKey(), entry.getValue(),existColumns);
            if (Objects.isNull(sqlStrArr)) {
                continue;
            }
            if (sqlStrArr.length==1){
                sqls.add(sqlStrArr[0]);
            }
            if (sqlStrArr.length==2){
                sqls.add(sqlStrArr[0]);
                sqls.add(sqlStrArr[1]);
            }
        }
    }

    /**
     * 自动选取策略创建
     * @param tableEntity
     * @param columnEntities
     * @return
     */
    private String[] selectCreateSql(TableEntity tableEntity, List<ColumnEntity> columnEntities, List<com.moon.joyce.example.functionality.entity.Column> existColumns){
        String strategy = tableEntity.getStrategy();
        switch (strategy){
            case "1":
                return new String[]{alterTable(tableEntity, columnEntities,existColumns)};
            case "2":
                return new String[]{createTableSql(tableEntity,columnEntities)};
            case "3":
                return new String[]{deleteTable(tableEntity.getName()),createTableSql(tableEntity, columnEntities)};
            default: return null;
        }
    }

    /**
     * 创建表的sql
     * @param tableEntity
     * @param columnEntities
     * @return
     */
    private String createTableSql(TableEntity tableEntity, List<ColumnEntity> columnEntities){
        if (Objects.isNull(columnEntities)||columnEntities.isEmpty()){
            throw  new JoyceException("在创建"+tableEntity.getName()+"的时候，该类无属性类，因此无法创建");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(" if not exists ");
        sb.append(tableEntity.getName())
                .append("(");
        for (int i = 0; i < columnEntities.size(); i++) {
            if (Objects.isNull(columnEntities.get(i))){
                continue;
            }
            if (Objects.isNull(columnEntities.get(i).getName())){
             continue;
            }
            ColumnEntity column = columnEntities.get(i);
            if (Objects.isNull(column)){continue;}
            sb.append("`")
                    .append(StringsUtils.camelToUnderline(column.getName()).toLowerCase())
                    .append("` ")
                    .append(column.getType())
                    .append("(")
                    .append(column.getLength())
                    .append(") ");
            if (column.getNotNull()){
                sb.append(" not null ");
            }else {
                if (!column.getKey()){
                    sb.append(" default "+AutoCreateTableInit.defValConvert(column.getDefaultValue(),column.getType()));
                }
            }
            if (column.getAuto()){
                sb.append(" auto_increment ");
            }
            if (column.getKey()){
                sb.append(" primary key ");
            }
            if (StringUtils.isNoneBlank(column.getComment())){
                sb.append(" comment '")
                        .append(column.getComment())
                        .append("'");
            }
             sb.append(",");

        }
        StringBuilder newSb = new StringBuilder();
        newSb.append( sb.substring(0, sb.length()-1));
        newSb.append(")ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci");
        if (Objects.nonNull(tableEntity.getContent())){
            newSb.append(" COMMENT = '").append(tableEntity.getContent()).append("'");
        }
        newSb.append(" ROW_FORMAT = Dynamic");
        return  newSb.toString();
    }

    /**
     * 给表添加字段
     * @param tableEntity
     * @param columnEntities
     * @return
     */
    private String alterTable(TableEntity tableEntity, List<ColumnEntity> columnEntities, List<com.moon.joyce.example.functionality.entity.Column> existColumns){
        List<String> list = existColumns.stream().map(com.moon.joyce.example.functionality.entity.Column::getColumnName).collect(Collectors.toList());
        List<ColumnEntity> differentList = getDifferentList(list, columnEntities);
        if (!differentList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            sb.append("alter table ").append(tableEntity.getName()).append(" add (");
            for (int i = 0; i < differentList.size(); i++) {
                ColumnEntity columnEntity = differentList.get(i);
                sb.append(columnEntity.getName()).append(" ").append(columnEntity.getType()).append("(").append(columnEntity.getLength()).append(") ");
                if (columnEntity.getNotNull()){
                    sb.append(" not null ");
                }else {
                    sb.append(" default null");
                }
                if (columnEntity.getAuto()){
                    sb.append(" auto_increment ");
                }
                if (columnEntity.getKey()){
                    sb.append(" primary key ");
                }
                if (StringUtils.isNoneBlank(columnEntity.getComment())){
                    sb.append(" comment '")
                            .append(columnEntity.getComment())
                            .append("'");
                }
                if (i!=(columnEntities.size()-1)){
                    sb.append(",");
                }
            }
           return sb.append(")").toString();
        }
        return null;
    }

    /**
     * 删除表
     * @param tableName
     * @return
     */
    private String deleteTable(String tableName){
        return "DROP TABLE IF EXISTS `"+tableName+"`";
    }

    /**
     * 获取工程名称
     * @return
     */
    @Override
    public String getTableFactoryName() {
        return "自动创建sql表工厂";
    }

    /**
     * 检测是否有多出的字段
     * @param strs
     * @param list
     * @return
     */
    private List<ColumnEntity> getDifferentList(List<String> strs,List<ColumnEntity> list){
        List<ColumnEntity> columnEntities = new ArrayList<>();
        for (ColumnEntity columnEntity : list) {
            boolean rs = StringsUtils.listIsContainsStr(columnEntity.getName(), strs);
            if (!rs){
                columnEntities.add(columnEntity);
            }
        }
        return columnEntities;
    }

    /**
     * 获取执行sql集合
     * @param ps
     * @return
     */
    @Override
    public List<String> getSqls(String ps){
        if (StringUtils.isBlank(ps)){
            throw new JoyceException("扫描包路径格式不合法");
        }
        try {
            init(ps);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sqls.forEach(sql->logger.info("生成sql:------->"+sql));
        return sqls;
    }


}
