package com.moon.joyce.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.moon.joyce.commons.utils.StringsUtils;
import com.moon.joyce.dataSource.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: XingDaoRong
 * @Date: 2021/11/24
 * 数据源配置
 */
@Configuration
@EnableTransactionManagement
public class JoyceDruidDBConfig {
    //默认数据源配置
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.druid.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.druid.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.druid.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.druid.maxWait}")
    private int maxWait;

    //mybatis-plus配置
    @Value("${mybatis-plus.type-aliases-package}")
    private String typeAliasesPackage;
    @Value("${mybatis-plus.mapper-locations}")
    private String mapperLocations;



    @Bean //声明其为Bean实例
    @Primary //在同样的DataSource中，首先使用被注入的DataSource
    @Qualifier("mainDataSource")
    public DataSource dataSource() throws SQLException {
        ArrayList<String> data = new ArrayList<>();
        DruidDataSource dataSource = new DruidDataSource();
        //基础连接信息
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        /*data.add(dataSource.getPassword());*/
        dataSource.setDriverClassName(driverClassName);
        //连接池信息
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        //data.add(String.valueOf(dataSource.getMaxWait()));
        //是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        //data.add(String.valueOf(dataSource.getMaxPoolPreparedStatementPerConnectionSize()));
        //  datasource.setConnectionProperties("oracle.net.CONNECT_TIMEOUT=6000;oracle.jdbc.ReadTimeout=60000");//对于耗时长的查询sql，会受限于ReadTimeout的控制，单位毫秒
        //对于耗时长的查询sql，会受限于ReadTimeout的控制，单位毫秒
        dataSource.setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");
        //申请连接时执行validationQuery检测连接是否有效，这里建议配置为TRUE，防止取到的连接不可用
        dataSource.setTestOnBorrow(true);
        //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        dataSource.setTestWhileIdle(true);
        String validationQuery = "select 1 from dual";
        //用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
        dataSource.setValidationQuery(validationQuery);
        //属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
        dataSource.setFilters("stat,wall");
        //配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        //配置一个连接在池中最小生存的时间，单位是毫秒，这里配置为3分钟180000
        dataSource.setMinEvictableIdleTimeMillis(180000);
        //打开druid.keepAlive之后，当连接池空闲时，池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作，即执行druid.validationQuery指定的查询SQL，一般为select * from dual，只要minEvictableIdleTimeMillis设置的小于防火墙切断连接时间，就可以保证当连接空闲时自动做保活检测，不会被防火墙切断
        dataSource.setKeepAlive(true);
        //是否移除泄露的连接/超过时间限制是否回收。
        dataSource.setRemoveAbandoned(true);
        //泄露连接的定义时间(要超过最大事务的处理时间)；单位为秒。这里配置为1小时
        dataSource.setRemoveAbandonedTimeout(3600);
        //移除泄露连接发生是是否记录日志
        dataSource.setLogAbandoned(true);
        data.add("数据url:"+dbUrl);
        data.add("用户名:"+username);
        data.add("密码:*******");
        data.add("驱动:"+driverClassName);
        data.add("初始容量:"+initialSize);
        data.add("最小连接数:"+minIdle);
        data.add("最大连接数:"+maxActive);
        data.add("最大等待时间:"+maxWait+"毫秒");
        logger.info(StringsUtils.appendStr("\n数据源配置:{\n",data,"}",";\n"));
        return dataSource;
    }

    /**
     * 注册一个:filterRegistrationBean   druid监控页面配置2-允许页面正常浏览
     * @return
     */
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean(name = "dynamicDataSource")
    @Qualifier("dynamicDataSource")
    public DynamicDataSource dynamicDataSource() throws SQLException {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDebug(false);
        //配置缺省的数据源
        // 默认数据源配置 DefaultTargetDataSource
        dynamicDataSource.setDefaultTargetDataSource(dataSource());
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //额外数据源配置 TargetDataSources
        targetDataSources.put("mainDataSource", dataSource());
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
       /* SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();*/
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource());
        //解决手动创建数据源后字段到bean属性名驼峰命名转换失效的问题
        bean.setConfiguration(configuration());
        // 设置mybatis的主配置文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // Resource mybatisConfigXml = resolver.getResource("classpath:mybatis/mybatis-config.xml");
        //  sqlSessionFactoryBean.setConfigLocation(mybatisConfigXml);
        // 设置别名包
        bean.setTypeAliasesPackage(typeAliasesPackage);
        //设置xml包
        //bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResource("classpath:mapper/*.xml"));
        //手动配置mybatis的mapper.xml资源路径,如果单纯使用注解方式,不需要配置该行
         bean.setMapperLocations(resolver.getResources(mapperLocations));
        bean.setPlugins(new PaginationInterceptor[]{new PaginationInterceptor()});
        return bean.getObject();
    }

    /**
     * 读取驼峰命名设置
     * @return
     */
  /*  @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration configuration() {
        return new org.apache.ibatis.session.Configuration();
    }
*/
    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public MybatisConfiguration configuration() {
        return new MybatisConfiguration();
    }
/*MybatisConfiguration*/

    /**
     * 实现的动态数据源加载类DynamicDataSource 添加到数据事务管理器
     * @param dynamicDataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }



}
