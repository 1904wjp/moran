package com.moon.joyce.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.druid.stat.DruidDataSourceStatManager;
import com.moon.joyce.commons.utils.JoyceExceptionUtils;
import com.moon.joyce.example.functionality.entity.doma.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.DriverManager;
import java.util.Map;
import java.util.Set;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 */

public class DynamicDataSource extends AbstractRoutingDataSource {
    private boolean debug = true;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private Map<Object, Object> dynamicTargetDataSources;
    private Object dynamicDefaultTargetDataSource;


    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DbContextHolder.getDataSource();
        if (!StringUtils.isEmpty(datasource)) {
            Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
            if (dynamicTargetDataSources2.containsKey(datasource)) {
                log.info("---当前数据源：" + datasource + "---");
            } else {
                log.warn("无法寻找到该数据源：");
                return null;
//       throw new ADIException("不存在的数据源："+datasource,500);
            }
        } else {
            log.info("---当前数据源：默认数据源---");
        }
        return datasource;
    }

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
        this.dynamicTargetDataSources = targetDataSources;
    }

    // 创建数据源
    public boolean createDataSource(String key, String driveClass, String url, String username, String password, String databaseType) {
        try {
            try { // 排除连接不上的错误
                Class.forName(driveClass);
                DriverManager.getConnection(url, username, password);// 相当于连接数据库
            } catch (Exception e) {
                logger.error("数据库信息填写有误");
                return false;
            }
//            HikariDataSource druidDataSource = new HikariDataSource();
            DruidDataSource druidDataSource = new DruidDataSource();
            druidDataSource.setName(key);
            druidDataSource.setDriverClassName(driveClass);
            druidDataSource.setUrl(url);
            druidDataSource.setUsername(username);
            druidDataSource.setPassword(password);
            druidDataSource.setInitialSize(1); //初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
            druidDataSource.setMaxActive(20); //最大连接池数量
            druidDataSource.setMaxWait(60000); //获取连接时最大等待时间，单位毫秒。当链接数已经达到了最大链接数的时候，应用如果还要获取链接就会出现等待的现象，等待链接释放并回到链接池，如果等待的时间过长就应该踢掉这个等待，不然应用很可能出现雪崩现象
            druidDataSource.setMinIdle(5); //最小连接池数量
            String validationQuery = "select 1 from dual";
    /*        if("mysql".equalsIgnoreCase(databasetype)) {
                driveClass = DBUtil.mysqldriver;
                validationQuery = "select 1";
            } else if("oracle".equalsIgnoreCase(databasetype)){
                driveClass = DBUtil.oracledriver;
                druidDataSource.setPoolPreparedStatements(true); //是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
                druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(50);
                int sqlQueryTimeout = ADIPropUtil.sqlQueryTimeOut();
                druidDataSource.setConnectionProperties("oracle.net.CONNECT_TIMEOUT=6000;oracle.jdbc.ReadTimeout="+sqlQueryTimeout);//对于耗时长的查询sql，会受限于ReadTimeout的控制，单位毫秒
            } else if("sqlserver2000".equalsIgnoreCase(databasetype)){
                driveClass = DBUtil.sql2000driver;
                validationQuery = "select 1";
            } else if("sqlserver".equalsIgnoreCase(databasetype)){
                driveClass = DBUtil.sql2005driver;
                validationQuery = "select 1";
            }*/
            druidDataSource.setTestOnBorrow(true); //申请连接时执行validationQuery检测连接是否有效，这里建议配置为TRUE，防止取到的连接不可用
            druidDataSource.setTestWhileIdle(true);//建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
            druidDataSource.setValidationQuery(validationQuery); //用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
            druidDataSource.setFilters("stat");//属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
            druidDataSource.setTimeBetweenEvictionRunsMillis(60000); //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
            druidDataSource.setMinEvictableIdleTimeMillis(180000); //配置一个连接在池中最小生存的时间，单位是毫秒，这里配置为3分钟180000
            druidDataSource.setKeepAlive(true); //打开druid.keepAlive之后，当连接池空闲时，池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作，即执行druid.validationQuery指定的查询SQL，一般为select * from dual，只要minEvictableIdleTimeMillis设置的小于防火墙切断连接时间，就可以保证当连接空闲时自动做保活检测，不会被防火墙切断
            druidDataSource.setRemoveAbandoned(true); //是否移除泄露的连接/超过时间限制是否回收。
            druidDataSource.setRemoveAbandonedTimeout(3600); //泄露连接的定义时间(要超过最大事务的处理时间)；单位为秒。这里配置为1小时
            druidDataSource.setLogAbandoned(true); //移除泄露连接发生是是否记录日志
            druidDataSource.init();
            this.dynamicTargetDataSources.put(key, druidDataSource);
            setTargetDataSources(this.dynamicTargetDataSources);// 将map赋值给父类的TargetDataSources
            super.afterPropertiesSet();// 将TargetDataSources中的连接信息放入resolvedDataSources管理
            log.info(key+"数据源初始化成功");
            //log.info(key+"数据源的概况："+druidDataSource.dump());
            return true;
        } catch (Exception e) {
            log.error(e + "");
            return false;
        }
    }

    // 创建数据源
    public boolean createDataSourceBySource(DataSource dataSource) {
        return createDataSource(dataSource.getDataSourceName(),dataSource.getDriver(),dataSource.getUrl(),dataSource.getUserName(),dataSource.getPassWord(),dataSource.getDatabaseType());
    }

    // 删除数据源
    public boolean delDatasources(String datasourceName) {
        Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
        if (dynamicTargetDataSources2.containsKey(datasourceName)) {
            Set<DruidDataSource> druidDataSourceInstances = DruidDataSourceStatManager.getDruidDataSourceInstances();
            for (DruidDataSource druidDataSource : druidDataSourceInstances) {
                if (datasourceName.equals(druidDataSource.getName())) {
                    dynamicTargetDataSources2.remove(datasourceName);
                    DruidDataSourceStatManager.removeDataSource(druidDataSource);
                    setTargetDataSources(dynamicTargetDataSources2);// 将map赋值给父类的TargetDataSources
                    super.afterPropertiesSet();// 将TargetDataSources中的连接信息放入resolvedDataSources管理
                    logger.warn(datasourceName+"数据源删除成功");
                    return true;
                }
            }
            logger.warn(datasourceName+"数据源删除失败");
            return false;
        }
            logger.warn(datasourceName+"数据源删除失败");
            return false;
    }

    // 测试数据源连接是否有效
    public boolean testDatasourceByDataSource(DataSource dataSource) {
       return testDatasource(dataSource.getDataSourceName(),dataSource.getDriver(), dataSource.getUrl(), dataSource.getUserName(),dataSource.getPassWord());
    }


    // 测试数据源连接是否有效
    public boolean testDatasource(String dataSourceName, String driveClass, String url, String username, String password) {
        try {
            Class.forName(driveClass);
            DriverManager.getConnection(url, username, password);
            logger.info("该数据源:"+dataSourceName+"可正常运行");
            return true;
        } catch (Exception e) {
            logger.error("该数据源:"+dataSourceName+"无法正常运行,Exception:"+e);
            return false;
        }
    }

    /**
     * 设置默认数据库
     * @param defaultTargetDataSource
     */
    @Override
    public void setDefaultTargetDataSource(Object defaultTargetDataSource) {
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        this.dynamicDefaultTargetDataSource = defaultTargetDataSource;
    }

    /**
     * @param debug
     *            the debug to set
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /*     * @return the debug
     */
    public boolean isDebug() {
        return debug;
    }

    /*     * @return the dynamicTargetDataSources
     */
    public Map<Object, Object> getDynamicTargetDataSources() {
        return dynamicTargetDataSources;
    }

    /**
     * @param dynamicTargetDataSources
     * the dynamicTargetDataSources to set
     */
    public void setDynamicTargetDataSources(Map<Object, Object> dynamicTargetDataSources) {
        this.dynamicTargetDataSources = dynamicTargetDataSources;
    }

    /*     * @return the dynamicDefaultTargetDataSource
     */
    public Object getDynamicDefaultTargetDataSource() {
        return dynamicDefaultTargetDataSource;
    }

    /**
     * @param dynamicDefaultTargetDataSource
     *            the dynamicDefaultTargetDataSource to set
     */
    public void setDynamicDefaultTargetDataSource(Object dynamicDefaultTargetDataSource) {
        this.dynamicDefaultTargetDataSource = dynamicDefaultTargetDataSource;
    }

    /**
     * 创建数据源
     * @param dataSource
     * @throws Exception
     */
    public void createDataSourceWithCheck(DataSource dataSource) throws Exception {
        String datasourceId = dataSource.getDataSourceName();
        log.info("正在检查数据源："+datasourceId);
        Map<Object, Object> dynamicTargetDataSources2 = this.dynamicTargetDataSources;
        if (dynamicTargetDataSources2.containsKey(datasourceId)) {
            log.warn("数据源"+datasourceId+"之前已经创建，准备测试数据源是否正常...");
            //DataSource druidDataSource = (DataSource) dynamicTargetDataSources2.get(datasourceId);
            DruidDataSource druidDataSource = (DruidDataSource) dynamicTargetDataSources2.get(datasourceId);
            boolean rightFlag = true;
            DruidPooledConnection connection = null;
            try {
                log.info(datasourceId+"数据源的概况->当前闲置连接数："+druidDataSource.getPoolingCount());
                long activeCount = druidDataSource.getActiveCount();
                log.info(datasourceId+"数据源的概况->当前活动连接数："+activeCount);
                if(activeCount > 0) {
                    log.info(datasourceId+"数据源的概况->活跃连接堆栈信息："+druidDataSource.getActiveConnectionStackTrace());
                }
                log.info("准备获取数据库连接...");
                connection = druidDataSource.getConnection();
                log.info("数据源"+datasourceId+"正常");
            } catch (Exception e) {
                log.error(e.getMessage(),e); //把异常信息打印到日志文件
                rightFlag = false;
                log.warn("缓存数据源"+datasourceId+"已失效，准备删除...");
                if(delDatasources(datasourceId)) {
                    log.warn("缓存数据源删除成功");
                } else {
                    log.warn("缓存数据源删除失败");
                }
            } finally {
                if(null != connection) {
                    connection.close();
                }
            }
            if(rightFlag) {
                log.warn("不需要重新创建数据源");
                return;
            } else {
                log.warn("准备重新创建数据源...");
                createDataSource(dataSource);
                log.info("重新创建数据源完成");
            }
        } else {
            createDataSource(dataSource);
        }
    }

    /**
     * 创建数据源
     * @param dataSource
     */
    private  boolean createDataSource(DataSource dataSource) {
        log.info("准备创建数据源"+dataSource.getDataSourceName());
        if(testDatasourceByDataSource(dataSource)) {
            boolean result = this.createDataSourceBySource(dataSource);
            if(!result) {
             throw JoyceExceptionUtils.exception("数据源"+dataSource.getDataSourceName()+"配置正确，但是创建失败");
            }else {
                return true;
            }
        } else {
            throw JoyceExceptionUtils.exception("数据源配置有错误");
        }
    }

}
