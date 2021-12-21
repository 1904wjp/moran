package com.moon.joyce.commons.utils;

import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.Result;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 9:06
 * @desc Result工具类
 */
public class ResultUtils implements Serializable {
    private static final long serialVersionUID = -5357414979443374259L;
    /**
     * 成功的方法
     */
    //分页
    public PageVo success(List<Object> rows, int total) {
        return new PageVo(rows,total);
    }
    public static Result success(String msg,Boolean rs,Object data){
        if (Objects.isNull(msg)){
            msg=Constant.RESULT_SUCCESS_MSG;
        }
        return new Result(Constant.SUCCESS_CODE,msg,rs,data);
    }
    public static Result success(String msg,Boolean rs){
        if (Objects.isNull(msg)){
            msg=Constant.RESULT_SUCCESS_MSG;
        }
        return new Result(Constant.SUCCESS_CODE,msg,rs);
    }
    public static Result success(String msg,Object data){
        if (Objects.isNull(msg)){
            msg=Constant.RESULT_SUCCESS_MSG;
        }
        return new Result(Constant.SUCCESS_CODE,msg,true,data);
    }
    public static Result success(String msg){
        if (Objects.isNull(msg)){
            msg=Constant.RESULT_SUCCESS_MSG;
        }
        return new Result(Constant.SUCCESS_CODE,msg,true);
    }
    public static Result success(Boolean rs){
        return new Result(Constant.SUCCESS_CODE,Constant.RESULT_SUCCESS_MSG,rs);
    }
    public static Result success(Object data){
        return new Result(Constant.SUCCESS_CODE,Constant.RESULT_SUCCESS_MSG,true,data);
    }
    public static Result success(Boolean rs,Object data){
        return new Result(Constant.SUCCESS_CODE,Constant.RESULT_SUCCESS_MSG,rs,data);
    }
    public static Result success(){
        return new Result(Constant.SUCCESS_CODE,Constant.RESULT_SUCCESS_MSG,true);
    }

    /**
     * 失败的方法
     */
    public static Result error(Integer code,String msg,Boolean rs,Object data){
        if (Objects.isNull(msg)){
            msg = getErrorMSG(code, msg);
        }
        return new Result(code,msg,rs,data);
    }
    public static Result error(Integer code,String msg,Object data){
        if (Objects.isNull(msg)){
            msg = getErrorMSG(code, msg);
        }
        return new Result(code,msg,false,data);
    }
    public static Result error(Integer code,String msg){
        if (Objects.isNull(msg)){
            msg = getErrorMSG(code, msg);
        }
        return new Result(code,msg,false);
    }
    public static Result error(Integer code,String msg,Boolean rs){
        if (Objects.isNull(msg)){
            msg = getErrorMSG(code, msg);
        }
        return new Result(code,msg,rs);
    }
    public static Result error(Integer code,Boolean rs,Object data){
        String msg =null;
        msg = getErrorMSG(code, msg);
        return new Result(code,msg,rs,data);
    }
    public static Result error(Integer code,Object data){
        String msg =null;
        msg = getErrorMSG(code, msg);
        return new Result(code,msg,false,data);
    }
    public static Result error(Integer code,Boolean rs){
        String msg =null;
        msg = getErrorMSG(code, msg);
        return new Result(code,msg,rs);
    }
    public static Result error(Integer code){
        String msg =null;
        msg = getErrorMSG(code, msg);
        return new Result(code,msg,false);
    }
    public static Result error(){
        return new Result(Constant.ERROR_CODE,Constant.CHINESE_ERROR_MESSAGE,false);
    }
    public static Result error(String msg){
        return new Result(Constant.ERROR_CODE,msg,false);
    }


    /**
     * 设置默认error情况下的msg绑定
     * @param code
     * Constant.ERROR_CODE 错误代码
     * Constant.NULL_CODE null代码
     * Constant.INACTIVE_CODE 未激活代码
     * Constant.FROZEN_CODE 冻结代码
     * @param msg
     * Constant.RESULT_ERROR_MSG 错误信息
     * Constant.RESULT_NULL_MSG 空值信息
     * Constant.CHINESE_INACTIVE_MESSAGE 未激活信息
     * Constant.CHINESE_FROZEN_MESSAGE 冻结信息
     * Constant.RESULT_FILL_ERROR_MSG 填写有误信息
     * @return
     */
    private  static String getErrorMSG(Integer code,String msg){
            /**
             *错误
            */
            if (Constant.ERROR_CODE==code){
                msg=Constant.RESULT_ERROR_MSG;
            }
            /**
             *空值
             */
            if (Constant.NULL_CODE==code){
                msg=Constant.RESULT_NULL_MSG;
            }
            /**
             *未激活
             */
            if (Constant.INACTIVE_CODE==code){
                msg=Constant.CHINESE_INACTIVE_MESSAGE;
            }
            /**
             *冻结
             */
            if (Constant.FROZEN_CODE==code){
                msg=Constant.CHINESE_FROZEN_MESSAGE;
            }
            /**
             * 信息不匹配
             */
            if (Constant.ERROR_FILL_ERROR_CODE==code){
                msg=Constant.RESULT_FILL_ERROR_MSG;
            }

        return msg;
    }
}
