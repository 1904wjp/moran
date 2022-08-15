package com.moon.joyce.commons.utils;

import com.moon.joyce.commons.constants.Constant;
import com.moon.joyce.commons.enums.RE;
import com.moon.joyce.example.entity.base.entity.BaseEntity;
import com.moon.joyce.example.entity.vo.PageVo;
import com.moon.joyce.example.functionality.entity.Result;

import java.util.List;
import java.util.Objects;

/**
 * @author Xing Dao Rong
 * @date 2021/9/3 9:06
 * @desc Result工具
 */
public class R  {


    /**
     * 返回结果
     * @param rsi
     * @param reCode
     * @return
     */
    public static Result dataResult(int rsi, int reCode, BaseEntity entity){
        if (reCode!=RE.ADDORUPDATE.getCode()){
            return dataResult(rsi,reCode);
        }
        if (Objects.isNull(entity.getId())){
            return dataResult(rsi,RE.ADD.getCode());
        }
        return dataResult(rsi,RE.UPDATE.getCode());
    }

    /**
     * 返回结果
     * @param rsi
     * @param reCode
     * @return
     */
    public static Result dataResult(int rsi,int reCode){
        return dataResult(rsi,reCode,null);
    }
    /**
     * 返回结果
     * @param rsi
     * @param reCode
     * @param data
     * @return
     */
    public static Result dataResult(int rsi,int reCode,Object data){
        String MSG = "";
        if (reCode== RE.ADD.getCode()){
            MSG = "添加";
        }
        if (reCode== RE.DELETE.getCode()){
            MSG = "删除";
        }
        if (reCode== RE.UPDATE.getCode()){
            MSG = "修改";
        }
        if (reCode== RE.SELECT.getCode()){
            MSG = "查询";
        }
        if (rsi!=0){
            if ("".equals(MSG)){
                return success(data);
            }
            return R.success(MSG+"成功",data);
        }
        if (!MSG.equals("")){
            return R.success(MSG+"失败");
        }
        return R.error();
    }
    /**
     * 返回结果
     * @param rsi
     * @return
     */
    public static Result dataResult(int rsi){
        if (rsi!=0){
            return R.success();
        }
        return R.error();
    }
    /**
     * 返回结果
     * @param rsi
     * @return
     */
    public static Result dataResult(int rsi,Result errorRs ,Result successRs){
        if (rsi!=0){
            return successRs;
        }
        return errorRs;
    }
    /**
     * 返回结果
     * @param rsi
     * @param data
     * @return
     */
    public static Result dataResult(int rsi,Object data){
       if (rsi!=0){
           return R.success(data);
       }
        return R.error();
    }


    /**
     * 返回结果
     * @param rsi
     * @return
     */
    public static Result dataResult(int rsi,Integer code){
        if (rsi!=0){
            return success();
        }
        return error(code);
    }
    /**
     * 返回结果
     * @param rsi
     * @return
     */
    public static Result dataResult(int rsi,String msg){
        if (rsi!=0){
            return success(msg);
        }
        return error(msg);
    }
    /**
     * 返回结果
     * @param rsi
     * @return
     */
    public static Result dataResult(int rsi,Integer code,String msg){
        if (rsi!=0){
            return success(msg);
        }
        return error(code,msg);
    }
    /**
     * 返回结果
     * @param rsi
     * @return
     */
    public static Result dataResult(int rsi,Integer code,Object data){
        if (rsi!=0){
            return success(data);
        }
        return error(code);
    }
    /**
     * 返回结果
     * @param rsi
     * @return
     */
    public static Result dataResult(int rsi,Integer code,String msg,Object data){
        if (rsi!=0){
            return success(msg,data);
        }
        return error(code,msg,false);
    }
   
    /**
     * 返回结果
     * @param rsi
     * @return
     */
    public static Result dataResult(int rsi,String errorMsg,String successMsg){
        if (rsi!=0){
            return success(successMsg);
        }
        return error(errorMsg);
    }
    /**
     * 返回结果
     * @param rsi
     * @return
     */
    public static Result dataResult(int rsi,String errorMsg,String successMsg,Object data){
        if (rsi!=0){
            return success(successMsg,data);
        }
        return error(errorMsg);
    }

    /**
     * 返回结果
     * @param rsi
     * @return
     */
    public static Result dataResult(int rsi,String errorMsg,Object data){
        if (rsi!=0){
            return success(data);
        }
        return error(errorMsg);
    }

    /**
     * 返回结果
     * @param rsi
     * @return
     */
    public static Result dataResult(int rsi,Integer code,String errorMsg,String successMsg,Object data){
        if (rsi!=0){
            return success(successMsg,data);
        }
        return error(code,errorMsg,false);
    }
    /**
     * 返回结果
     * @param rs
     * @return
     */
    public static Result dataResult(boolean rs){
        if (rs){
            return success();
        }
        return error();
    }

    /**
     * 返回结果
     * @param rs
     * @param reCode
     * @param entity
     * @return
     */
    public static Result dataResult(boolean rs, int reCode, BaseEntity entity){
        if (!rs){
            return dataResult(0,reCode,entity);
        }
        return dataResult(1,reCode,entity);
    }
    /**
     * 返回结果
     * @param rs
     * @param errorRs
     * @param successRs
     * @return
     */
    public static Result dataResult(boolean rs,Result errorRs ,Result successRs){
        if (rs){
            return successRs;
        }
        return errorRs;
    }
    /**
     * 返回结果
     * @param rs
     * @param reCode
     * @return
     */
    public static Result dataResult(boolean rs,int reCode){
        if (!rs){
            return dataResult(0,reCode,null);
        }
        return dataResult(1,reCode,null);
    }
    /**
     * 返回结果
     * @param rs
     * @param reCode
     * @param data
     * @return
     */
    public static Result dataResult(boolean rs,int reCode,Object data){
        if (!rs){
            return dataResult(0,reCode,data);
        }
        return dataResult(1,reCode,data);
    }
    /**
     * 返回结果
     * @param rs
     * @param data
     * @return
     */
    public static Result dataResult(boolean rs,Object data){
       if (rs){
           return success(data);
       }
        return error();
    }

    /**
     * 返回结果
     * @param rs
     * @return
     */
    public static Result dataResult(boolean rs,Integer code){
        if (rs){
            return success();
        }
        return error(code);
    }
    /**
     * 返回结果
     * @param rs
     * @return
     */
    public static Result dataResult(boolean rs,String msg){
        if (rs){
            return success(msg);
        }
        return error(msg);
    }
    /**
     * 返回结果
     * @param rs
     * @return
     */
    public static Result dataResult(boolean rs,Integer code,String msg){
        if (rs){
            return success(msg);
        }
        return error(code,msg);
    }
    /**
     * 返回结果
     * @param rs
     * @return
     */
    public static Result dataResult(boolean rs,Integer code,Object data){
        if (rs){
            return success(data);
        }
        return error(code);
    }
    /**
     * 返回结果
     * @param rs
     * @return
     */
    public static Result dataResult(boolean rs,Integer code,String msg,Object data){
        if (rs){
            return success(msg,data);
        }
        return error(code,msg,false);
    }
    /**
     * 返回结果
     * @param rs
     * @return
     */
    public static Result dataResult(boolean rs,String errorMsg,String successMsg){
        if (rs){
            return success(successMsg);
        }
        return error(errorMsg);
    }
    /**
     * 返回结果
     * @param rs
     * @return
     */
    public static Result dataResult(boolean rs,String errorMsg,Object data){
        if (rs){
            return success(data);
        }
        return error(errorMsg);
    }
    /**
     * 返回结果
     * @param rs
     * @return
     */
    public static Result dataResult(boolean rs,String errorMsg,String successMsg,Object data){
        if (rs){
            return success(successMsg,data);
        }
        return error(errorMsg);
    }
    /**
     * 返回结果
     * @param rs
     * @return
     */
    public static Result dataResult(boolean rs,Integer code,String errorMsg,String successMsg,Object data){
        if (rs){
            return success(successMsg,data);
        }
        return error(code,errorMsg,false);
    }
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
    public static Result success(Integer code,String msg){
        return new Result(code,msg,true);
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
    public static Result success(Integer code,String msg,Object data){
        return new Result(code,msg,true,data);
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
            if (Constant.ERROR_CODE.equals(code)){
                msg=Constant.RESULT_ERROR_MSG;
            }
            /**
             *空值
             */
            if (Constant.NULL_CODE.equals(code)){
                msg=Constant.RESULT_NULL_MSG;
            }
            /**
             *未激活
             */
            if (Constant.INACTIVE_CODE.equals(code)){
                msg=Constant.CHINESE_INACTIVE_MESSAGE;
            }
            /**
             *冻结
             */
            if (Constant.FROZEN_CODE.equals(code)){
                msg=Constant.CHINESE_FROZEN_MESSAGE;
            }
            /**
             * 信息不匹配
             */
            if (Constant.ERROR_FILL_ERROR_CODE.equals(code)){
                msg=Constant.RESULT_FILL_ERROR_MSG;
            }
        return msg;
    }
}
