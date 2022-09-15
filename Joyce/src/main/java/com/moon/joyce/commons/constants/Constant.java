package com.moon.joyce.commons.constants;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Xing Dao Rong
 * @date 2021/9/1 16:24
 * @desc 定量
 */
public class Constant {
    private Constant(){}

    /**
     * 定时参数配置示例
     */
//    每隔5秒执行一次：*/5 * * * * ?
//
//    每隔1分钟执行一次：0 */1 * * * ?
//
//    每天23点执行一次：0 0 23 * * ?
//
//    每天凌晨1点执行一次：0 0 1 * * ?
//
//    每月1号凌晨1点执行一次：0 0 1 1 * ?
//
//    每月最后一天23点执行一次：0 0 23 L * ?
//
//    每周星期天凌晨1点实行一次：0 0 1 ? * L
//
//    在20分、25分、30分执行一次：0 20,25,30 * * * ?

    //邮件密令
/*    @Value("${email.password}")
    public static   String password ;
    @Value("${email.emailPassword}")
    public static   String emailPassword ;
    @Value("${email.username}")
    public static   String email ;*/
    /**
     * 算法类型
     */
    //加
    public static final String ARITHMETIC_ADD = "type:arithmetic_add";
    //减
    public static final String ARITHMETIC_SUBTRACT = "type:arithmetic_subtract";
    //乘
    public static final String ARITHMETIC_MULTIPLY = "type:arithmetic_multiply";
    //除以
    public static final String ARITHMETIC_DIVIDE = "type:arithmetic_divide";

    /**
     * user拦截器
     */
    //当前时间
    public static final String SESSION_VERIFY_CODE = "session_verifyCode";
    //当前登录人
    public static final String SESSION_USER = "session_user";
    //登录路径
    public static final String USER_LOGIN_PATH = "/example/user/login";
    //注册路径
    public static final String USER_REGIST_PATH = "/example/user/regist";
    //app页面
    public static final String APP_INDEX_PATH = "/index";
    /**
     * 查询user条件
     */
    //登录修改查询user实体类
    public static final String USER_TYPE_LOGIN ="type:login";
    //验证用户名是否存在查询
    public static final String USER_TYPE_UNIQUE_USERNAME ="type:unique_username";
    //验证数据名是否存在查询
    public static final String DATA_TYPE_UNIQUE_NAME ="type:unique_name";
    //验证数据是否存在查询
    public static final String DATA_TYPE_UNIQUE ="type:unique_data";
    //验证手机是否存在查询
    public static final String USER_TYPE_UNIQUE_PHONE ="type:unique_phone";
    //验证邮件是否存在查询
    public static final String USER_TYPE_UNIQUE_EMAIL ="type:unique_email";
    //用户密码
    public static final String USER_TYPE_PASSWORD ="type:password";
    //验证用户名状态激活码是否存在查询
    public static final String USER_TYPE_UNIQUE_STATUS_CODE ="type:unique_status_code";
    //用户激活
    public static final String USER_TYPE_UP_VAILD_STATUS ="type:up_vaild_status";
    //用户升级vip
    public static final String USER_TYPE_UP_VIP_STATUS ="type:up_vip_status";
    //用户websocketSessionId
    public static final String USER_TYPE_WEBSOCKET_SESSION_ID ="type:websocket_session_id";
    //测试数据源
    public static final String TEST_DATASOURCE ="type:test_datasource";
    //创建数据源
    public static final String CREATE_DATASOURCE ="type:create_datasource";
    //移除数据源
    public static final String REMOVE_DATASOURCE ="type:remove_datasource";
    //当前应用数据源
    public static final String CURRENT_DATASOURCE ="current_datasource";
    //用户未激活状态
    public static final Integer USER_TYPE_INVAILD_STATUS =0;
    //用户有效状态
    public static final Integer USER_TYPE_VAILD_STATUS =1;
    //删除状态
    public static final Integer DELETE_STATUS =1;
    //未删除状态
    public static final Integer UNDELETE_STATUS =0;
    //应用状态
    public static final Integer APPLY_STATUS =1;
    //备用状态
    public static final Integer SPARE_STATUS =2;
    //启用状态
    public static final Integer START_STATUS =3;
    //会员用户
    public static final Integer USER_TYPE_VIP_STATUS =2;
    //冻结状态
    public static final Integer USER_TYPE_FROZEN_STATUS =10000;
    //int类型返回的未知结果
    public static final Integer RESULT_UNKNOWN_SQL_RESULT = -1;
    //int类型返回的没有结果
    public static final Integer RESULT_NO_SQL_RESULT = 0;
    //int类型返回的有一个结果
    public static final Integer RESULT_ONE_SUCCESS_SQL_RESULT = 1;
    /**
     * 异常类型
     */
    public static final String EXCEPTION_DATASOURCE = "EXCEPTION_DATASOURCE";
    /**
     * 正则一般需要用到的表达式
     */
    //手机号码验证
    public static final String PHONE_FORMULA = "^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
    //邮件验证
    public static final String EMAIL_FORMULA = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    //电话号码验证
    public static final String TELEPHONE_FORMULA = "(\\(\\d{3,4}\\)|\\d{3,4}-|\\s)?\\d{8}";
    //邮政编码验证
    public static final String POSTAL_CODE_FORMUL = "^\\d{6}$";
    //中文名验证
    public static final String CHINESE_NAME_FORMULA = "[a-zA-Z]{1,20}|[\\u4e00-\\u9fa5]{1,10}";
    //英文名验证
    public static final String ENGLISH_NAME_FORMULA = "[a-zA-Z]{1,20}";
    //网络地址验证
    public static final String INTENET_ADDRESS_FORMULA = "[a-zA-z]+://[^\\s]*";
    //ip地址验证
    public static final String IP_ADDRESS_FORMULA = "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$" ;
    //QQ验证
    public static final String QQ_FORMULA = "[1-9][0-9]{4,14}";
    //url验证
    public static final String URL_FORMULA = "^((https|http|ftp|rtsp|mms|ws)?://)"  //https、http、ftp、rtsp、mms、ws

            + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@

            + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 例如：199.194.52.184

            + "|" // 允许IP和DOMAIN（域名）

            + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.

            + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名

            + "[a-z]{2,6})" // first level domain- .com or .museum

            + "(:[0-9]{1,5})?" // 端口号最大为65535,5位数

            + "((/?)|" // a slash isn't required if there is no file name

            + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";

    /**
     * controller拼接
     */
    //重定向
    public static final String REDIRECT = "redirect:";

    /**
     * qq_email参数设置
     */
    //发送人的qq邮件
    public static final String SEND_EMAIL_CODE = "1154517070@qq.com";
    //发送人的qq密码
    public static final String SEND_EMAIL_PASSWORD = "cbeilolivceihghd";
    //发送邮件格式
    public static final String SEND_EMAIL_FORMATE = "text/html;charset=UTF-8";
    //发送人的授权码
    public static final String SEND_EMAIL_AUTH_CODE = "hj951206";
    //发送验证码的模板
    public static final String SEND_EMAIL_TEMPLATE = "来自Joyce网站的验证,您本次的验证码为:";
    //发送url激活模板
    public static final String SEND_EMAIL_TEMPLATE2 = "来自Joyce网站的验证,点击开始激活:";
    //发送验证码成功消息
    public static final String SEND_EMAIL_SEND_SUCCESS_MESSAGE = "验证码发送成功";
    //发送验证码在有效内消息
    public static final String SEND_EMAIL_SEND_VAILD_TIME_MESSAGE= "验证码60s发送一次";
    //发送邮件主题
    public static final String SEND_EMAIL_TITLE = "来自Joyce网站的激活邮件";


    /**
     * 返回的信息
     */
    public static final String CHINESE_COMMON_ERROR_MESSAGE = "操作失败";

    //填写错误的信息
    public static final String CHINESE_FILL_ERROR_MESSAGE = "填写有误，请填写正确信息";
    public static final String ERROR_FILL_MESSAGE = "Please fill in the correct information";
    //填写空白信息
    public static final String CHINESE_BLANK_MESSAGE= "请勿填写空白信息";
    public static final String BLANK_MESSAGE= "Do not fill in blank information";
    //查询的数据为空
    public static final String CHINESE_SELECT_BLANK_MESSAGE= "该数据不存在";
    public static final String SELECT_BLANK_MESSAGE= "The data does not exist";
    //查询的数据已存在
    public static final String CHINESE_SELECT_EXIST_MESSAGE= "该数据已存在";
    public static final String SELECT_EXIST_MESSAGE= "The data already exist";
    //查询的用户为空
    public static final String CHINESE_SELECT_BLANK_USERNAME_MESSAGE= "该用户不存在";
    public static final String SELECT_BLANK_USERNAME_MESSAGE= "The username does not exist";
    //查询的用户已存在
    public static final String CHINESE_SELECT_EXIST_USERNAME_MESSAGE= "该用户已存在";
    public static final String SELECT_EXIST_USERNAME_MESSAGE= "The username already exist";
    //查询的用户已存在
    public static final String CHINESE_SELECT_EXIST_EMAIL_MESSAGE= "该邮件已注册";
    public static final String SELECT_EXIST_EMAIL_MESSAGE= "The email already exist";
    //查询的用户已存在
    public static final String CHINESE_SELECT_EXIST_PHONE_MESSAGE= "该手机号码已存在";
    public static final String SELECT_EXIST_PHONE_MESSAGE= "The phone already exist";
    //未知错误
    public static final String CHINESE_ERROR_MESSAGE= "系统繁忙，请稍后再试！";
    public static final String ERROR_MESSAGE= "System busy, please try again later!";
    //用户未激活
    public static final String CHINESE_INACTIVE_MESSAGE= "用户未激活，请激活后再尝试！";
    public static final String INACTIVE_MESSAGE= "The user is not activated. Please activate it and try again!";
    //用户未激活
    public static final String CHINESE_FROZEN_MESSAGE= "用户已冻结，请查看详细信息！";
    public static final String FROZEN_MESSAGE= "The user has been frozen. Please view details!";
    //用户密码有误
    public static final String CHINESE_PASSWORD_ERROR_MESSAGE= "用户密码错误！";
    public static final String PASSWORD_ERROR_MESSAGE= "The password is error!";
    /**
     * Result 属性设置
     */
    //成功的编码
    public static final Integer SUCCESS_CODE = 200;
    public static final String RESULT_SUCCESS_MSG = "操作成功";
    //错误的编码
    public static final Integer ERROR_CODE = 500;
    public static final String RESULT_ERROR_MSG = CHINESE_ERROR_MESSAGE;
    //null的编码
    public static final Integer NULL_CODE = 404;
    public static final String RESULT_NULL_MSG = "未找到所属数据";
    //未激活的编码
    public static final Integer INACTIVE_CODE = 407;
    public static final String RESULT_INACTIVE_MSG = CHINESE_INACTIVE_MESSAGE;
    //冻结的编码
    public static final Integer FROZEN_CODE = 408;
    public static final String RESULT_FROZEN_MSG = CHINESE_FROZEN_MESSAGE;
    //填写有误的编码
    public static final Integer ERROR_FILL_ERROR_CODE = 409;
    public static final String RESULT_FILL_ERROR_MSG = CHINESE_FILL_ERROR_MESSAGE;
    //时间格式
    //日期(年)
    public static final String DATE_TIME_YEAR= "yyyy";
    //日期(月)
    public static final String DATE_TIME_MONTH= "yyyy-MM";
    //日期(天)
    public static final String DATE_TIME_DAY= "yyyy-MM-dd";
    //时间(时)
    public static final String DATE_TIME_HOUER= "yyyy-MM-dd hh";
    //时间(分)
    public static final String DATE_TIME_MINUTE= "yyyy-MM-dd hh:mm";
    //时间(秒)
    public static final String DATE_TIME_SECOND= "yyyy-MM-dd hh:mm:ss";

    /**
     * 文件相关配置
     */
    //默认头像
    public static final String FILE_DEFAULT_NAME= "/static/img/userImg/file0default0name.png";
    //默认封面视频
    public static final String VIDEO_DEFAULT_NAME= "/static/video/indexVideo/video0default0name.mp4";

    /**
     * PageComponent默认值
     */
    //fontName默认值
    public static final String FONTSIZE_DEFAULT_NAME= "d_f_n";
    //fontSize默认值
    public static final String FONTSIZE_DEFAULT_SIZE= "15px";
    //file默认值
    public static final String FILE_DEFAULT_SET_NAME= "d_fi_n";
    //fontSize默认值
    public static final String FILE_DEFAULT_URL= FILE_DEFAULT_NAME;
    //backgroundUrl默认值
    public static final String BACKGROUND_DEFAULT_URL= FILE_DEFAULT_NAME;
    //backgroundType默认值
    public static final String BACKGROUND_DEFAULT_TYPE= "img";
    //backgroundColor默认值
    public static final String BACKGROUND_DEFAULT_COLOR= "white";
    //默认配置文件
    public static final String DEFAULT_CONFIG_FILE = "dcfJoyce";
    /**
     * 数据库类
     */
    //用户主要数据
    public static final String USER_DATA_SOURCE =  "user_data_source";
    /**
     * 配置
     */
    //当前应用配置
    public static final String CURRENT_SETTING ="current_setting";

    /**
     * excel版本
     */
    //Excel&nbsp;2003
    public static final String EXCEL_XLS = "xls";
    // Excel 2007/2010
    public static final String EXCEL_XLSX = "xlsx";

    /**
     * 日志
     */
    //日志文件夹路径
    public static final String LOGGER_PATH = "D://Joyce/joyce.log";

    public static final String UNKNOW = "====================>";
    /**
     * redis总和map
     */
    public static final String redisMap = "redisMap";

    //appid
    public static final String APP_ID = "2021000117653093";
    //应用私钥
    public static final String APP_public_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAo6ZSLw9wGzYDghnHYSy2vkS4eHI9dGjn1CORqTbXcvnmeXFpC6/5BVQIogW35FjC5GcvqaeHXKIuMmuoDBNbLFiWpx8b43AIjnhGHP6s2LN2Xc/amV5sjsffiz1EfwEhcv1t+cOVRhL1sRpE8F43faa8RiPC13IVAruprz55ajahgcUZ+G74Q3eF3zBe6iWTyaSONStyvcV9jOOumgN9a00gqU9CjCuIa6NNtu1VFRxV/TJbzHogSNByoMLxi53EYzVoWdRKnMPiXgMOQTA8tOamwSAUvLxlP9cxGsIK+A4rt1mfDc21sAuf7+1AcQvxnZ7yLLZ/bz3h8+SdTiTSNwIDAQAB";
    public static final String CHARSET = "UTF-8";
    // 支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiamw+crJZvVbFFuJFavIj5Z+p3sGhYKAKuLgGFkMfnBt4h52caO7QLPuNwEc4R2MO8iziULhPK2Yif0oEpSzlCKMwsarq2kNPD5AcvhwEdPX8ZE5qnXt/wwtO3JgFXoZ/9byypyiWtMf9ULw8Y2GR7wh7U9yE85k3U5RNSlrO3LXUOpnXQSR4VUQX3WkgNfJDkz6cdVagab8zafrzm+l3gAKa9e3h9dkT7iwFo+OktYTqqv8e4vLMKx5zgGc7ghcmbD08X/XHifUQxKNT5puOPIo+AxO+KSmZVZkVlbDB0dqtF6MyDPxG5k9r9XF0veqXc+yJhzZKzm9tuEvYQiX9QIDAQAB";
    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    public static final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    public static final String FORMAT = "JSON";
    //签名方式
    public static final String SIGN_TYPE = "RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    public static final String NOTIFY_URL = "http://127.0.0.1/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    public static final String RETURN_URL = "http://localhost:8080/returnUrl";
}
