package com.moon.joyce.commons.utils;

import com.moon.joyce.example.functionality.entity.doma.JoyceException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Xing Dao Rong
 * @date 2021/9/7 10:02
 * @desc
 */
public class MD5Utils  implements Serializable {
    private static final long serialVersionUID = -8921608517556867794L;
    private MD5Utils() throws JoyceException {
        throw JoyceExceptionUtils.exception("工具类无法实例化");
    }
    public static String getMD5Str(String str) {
        //非空判断
        if (StringUtils.isBlank(str)){
            return null;
        }
      /*  byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest  = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        str=md5Str+"4165fa46ds4gaIKOPJOPU*(joyce";*/
        return str;
    }

}
