package com.moon.joyce.example.functionality.entity.doma;

import com.alipay.api.domain.GoodsDetail;

import java.util.List;

/**
 * 支付宝当面付实体类
 * @author 20201217 sqy
 *
 */
public class ZFBFaceToFaceModel {
    private String outTradeNo;// (必填) 商户网站订单系统中唯一订单号 ,64个字符以内，只能包含字母、数字、下划线， 需保证商户系统端不能重复，建议通过数据库sequence生成
    private String subject; // (必填) 订单标题,粗略描述用户的支付目的。如“喜士多（浦东店）消费”
    private String totalAmount;// (必填) 订单总金额单位为元，不能超过1亿元 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
    private String undiscountableAmount;// (可选) 订单不可打折金额,可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
    private String sellerId;// 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)// 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
    private String body;// // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
    private String operatorId; // 商户操作员编号，添加此参数可以为商户操作员做销售统计
    private String storeId; // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
    private String timeoutExpress;//支付超时如：“120m”，定义为120分钟
    private List<GoodsDetail> goodsDetailList; //商品明细列表，需填写购买商品详细信息，
    private String NotifyUrl;// 支付成功之后 支付宝异步调用的接口地址；
    private String MoblieReturnUrl;//手机支付同步通知页面地址；
    /**
     * (必填) 商户网站订单系统中唯一订单号 ,64个字符以内，只能包含字母、数字、下划线， 需保证商户系统端不能重复，建议通过数据库sequence生成
     * @return
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }
    /**
     * (必填) 商户网站订单系统中唯一订单号 ,64个字符以内，只能包含字母、数字、下划线， 需保证商户系统端不能重复，建议通过数据库sequence生成
     * @param outTradeNo
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
    /**
     * (必填) 订单标题,粗略描述用户的支付目的。如“喜士多（浦东店）消费”
     * @return
     */
    public String getSubject() {
        return subject;
    }
    /**
     * (必填) 订单标题,粗略描述用户的支付目的。如“喜士多（浦东店）消费”
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    /**
     *  (必填) 订单总金额单位为元，不能超过1亿元 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
     * @return
     */
    public String getTotalAmount() {
        return totalAmount;
    }
    /**
     *  (必填) 订单总金额单位为元，不能超过1亿元 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
     * @param totalAmount
     */
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
    /**
     * (可选) 订单不可打折金额,可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
     * @return
     */
    public String getUndiscountableAmount() {
        return undiscountableAmount;
    }
    /**
     * (可选) 订单不可打折金额,可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
     * @param undiscountableAmount
     */
    public void setUndiscountableAmount(String undiscountableAmount) {
        this.undiscountableAmount = undiscountableAmount;
    }
    /**
     * // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)// 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
     * @return
     */
    public String getSellerId() {
        return sellerId;
    }
    /**
     * // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)// 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
     * @param sellerId
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    /**
     * 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
     * @return
     */
    public String getBody() {
        return body;
    }
    /**
     * 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }
    /**
     *商户操作员编号，添加此参数可以为商户操作员做销售统计
     * @return
     */
    public String getOperatorId() {
        return operatorId;
    }
    /**
     *商户操作员编号，添加此参数可以为商户操作员做销售统计
     * @param operatorId
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
    /**
     * (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
     * @return
     */
    public String getStoreId() {
        return storeId;
    }
    /**
     * (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
     * @param storeId
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
    /**
     *支付超时如：“120m”，定义为120分钟
     * @return
     */
    public String getTimeoutExpress() {
        return timeoutExpress;
    }
    /**
     *支付超时如：“120m”，定义为120分钟
     * @param timeoutExpress
     */
    public void setTimeoutExpress(String timeoutExpress) {
        this.timeoutExpress = timeoutExpress;
    }
    /**
     *商品明细列表，需填写购买商品详细信息，
     * @return
     */
    public List<GoodsDetail> getGoodsDetailList() {
        return goodsDetailList;
    }
    /**
     *商品明细列表，需填写购买商品详细信息，
     * @param goodsDetailList
     */
    public void setGoodsDetailList(List<GoodsDetail> goodsDetailList) {
        this.goodsDetailList = goodsDetailList;
    }
    /**
     *支付成功之后 支付宝异步调用的接口地址；
     * @return
     */
    public String getNotifyUrl() {
        return NotifyUrl;
    }
    /**
     *支付成功之后 支付宝异步调用的接口地址；
     * @param notifyUrl
     */
    public void setNotifyUrl(String notifyUrl) {
        NotifyUrl = notifyUrl;
    }
    /**
     * 手机支付后跳转的页面地址
     * @return
     */
    public String getMoblieReturnUrl() {
        return MoblieReturnUrl;
    }
    /**
     * 手机支付后跳转的页面地址
     * @return
     */
    public void setMoblieReturnUrl(String moblieReturnUrl) {
        MoblieReturnUrl = moblieReturnUrl;
    }
}
