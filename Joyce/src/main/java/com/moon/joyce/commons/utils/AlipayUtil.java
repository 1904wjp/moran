package com.moon.joyce.commons.utils;


import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.moon.joyce.commons.utils.CommonUtils;
import com.moon.joyce.example.functionality.entity.doma.ZFBFaceToFaceModel;

/**
 * 支付宝面对面付款
 * 20201217 毛毛哇
 */
public class AlipayUtil {
    /**
     * 支付宝预下单
     * @param zfbFaceToFaceModel
     * @return
     */
    public static String ZFBPreorder(ZFBFaceToFaceModel zfbFaceToFaceModel,String dev) {
        try {
            CommonUtils commonUtils = new CommonUtils();
            /** 支付宝网关 **/
            String URL = commonUtils.getZFBinfoValue("open_api_domain"+dev);

            /** 应用id，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/190/201602493024 **/
            String APP_ID = commonUtils.getZFBinfoValue("appid"+dev);

            /** 应用私钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602469554 **/
            String APP_PRIVATE_KEY = commonUtils.getZFBinfoValue("private_key");

            /** 支付宝公钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602487431 **/
            String ALIPAY_PUBLIC_KEY = commonUtils.getZFBinfoValue("alipay_public_key"+dev);

            /** 初始化 **/
            AlipayClient alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");

            /** 实例化具体API对应的request类，类名称和接口名称对应,当前调用接口名称：alipay.trade.precreate（统一收单线下交易预创建（扫码支付）） **/
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

            /** 设置业务参数  **/
            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();

            /** 商户订单号，商户自定义，需保证在商户端不重复，如：20200612000001 **/
            model.setOutTradeNo(zfbFaceToFaceModel.getOutTradeNo());

            /**订单标题 **/
            model.setSubject(zfbFaceToFaceModel.getSubject());

            /** 订单金额，精确到小数点后两位 **/
            model.setTotalAmount(zfbFaceToFaceModel.getTotalAmount());

            /** 订单描述 **/
            model.setBody(zfbFaceToFaceModel.getBody());

            /** 业务扩展参数 **/
            //ExtendParams extendParams = new ExtendParams();

            /** 系统商编号，填写服务商的PID用于获取返佣，返佣参数传值前提：传值账号需要签约返佣协议，用于isv商户。 **/
            //extendParams.setSysServiceProviderId("2088511****07846");

            /** 花呗分期参数传值前提：必须有该接口花呗收款准入条件，且需签约花呗分期 **/
            /** 指定可选期数，只支持3/6/12期，还款期数越长手续费越高 **/
            // extendParams.setHbFqNum("3");

            /** 指定花呗分期手续费承担方式，手续费可以由用户全承担（该值为0），也可以商户全承担（该值为100），但不可以共同承担，即不可取0和100外的其他值。 **/
            //extendParams.setHbFqSellerPercent("0");

            //model.setExtendParams(extendParams);

            /** 将业务参数传至request中 **/
            request.setBizModel(model);

            /** 异步通知地址，以http或者https开头的，商户外网可以post访问的异步地址，用于接收支付宝返回的支付结果，如果未收到该通知可参考该文档进行确认：https://opensupport.alipay.com/support/helpcenter/193/201602475759 **/
            request.setNotifyUrl(zfbFaceToFaceModel.getNotifyUrl());

            /**第三方调用（服务商模式），传值app_auth_token后，会收款至授权app_auth_token对应商家账号，如何获传值app_auth_token请参考文档：https://opensupport.alipay.com/support/helpcenter/79/201602494631 **/
            //request.putOtherTextParam("app_auth_token", "传入获取到的app_auth_token值");

            /** 通过alipayClient调用API，获得对应的response类  **/
            AlipayTradePrecreateResponse response = alipayClient.execute(request);

            /** 获取接口调用结果，如果调用失败，可根据返回错误信息到该文档寻找排查方案：https://opensupport.alipay.com/support/helpcenter/101 **/
            System.out.println(response.getBody());
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String findZFB_trade(ZFBFaceToFaceModel zfbFaceToFaceModel) throws Exception{
        CommonUtils commonUtils = new CommonUtils();
        /** 支付宝网关 **/
        String URL = commonUtils.getZFBinfoValue("open_api_domain");

        /** 应用id，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/190/201602493024 **/
        String APP_ID = commonUtils.getZFBinfoValue("appid");

        /** 应用私钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602469554 **/
        String APP_PRIVATE_KEY = commonUtils.getZFBinfoValue("private_key");

        /** 支付宝公钥，如何获取请参考：https://opensupport.alipay.com/support/helpcenter/207/201602487431 **/
        String ALIPAY_PUBLIC_KEY = commonUtils.getZFBinfoValue("alipay_public_key");

        /** 初始化 **/
        AlipayClient alipayClient = new DefaultAlipayClient(URL,APP_ID,APP_PRIVATE_KEY,"json","UTF-8",ALIPAY_PUBLIC_KEY,"RSA2");

        /** 实例化具体API对应的request类，类名称和接口名称对应,当前调用接口名称：alipay.trade.query（统一收单线下交易查询） **/
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();

        /** 设置业务参数 **/
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();

        /** 注：交易号（TradeNo）与订单号（OutTradeNo）二选一传入即可，如果2个同时传入，则以交易号为准 **/
        /** 支付接口传入的商户订单号。如：2020061601290011200000140004 **/
        model.setOutTradeNo(zfbFaceToFaceModel.getOutTradeNo());

        /** 异步通知/查询接口返回的支付宝交易号，如：2020061622001473951448314322 **/
        //model.setTradeNo("2020061622001473951448314322");

        /** 将业务参数传至request中 **/
        request.setBizModel(model);

        /** 第三方调用（服务商模式），必须传值与支付接口相同的app_auth_token **/
        //request.putOtherTextParam("app_auth_token", "传入获取到的app_auth_token值");

        /** 通过alipayClient调用API，获得对应的response类  **/
        AlipayTradeQueryResponse response = alipayClient.execute(request);

        /** 获取接口调用结果，如果调用失败，可根据返回错误信息到该文档寻找排查方案：https://opensupport.alipay.com/support/helpcenter/101 **/
        return response.getBody();
    }

}
