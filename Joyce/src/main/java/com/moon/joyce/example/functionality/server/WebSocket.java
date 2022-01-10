package com.moon.joyce.example.functionality.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moon.joyce.example.entity.ChatRecord;
import com.moon.joyce.example.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/26-- 23:13
 * @describe:
 */
// websocket连接点映射.
@ServerEndpoint(value="/websocket/{id}")
@Component
public class WebSocket {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int onlineNumber = 0;
    private static CopyOnWriteArraySet<WebSocket> clients = new CopyOnWriteArraySet<>();
    private static Map<Long,Session> map = new HashMap<>();
    //当前会话的session.
    private Session session;
    //用户id.
    private Long id;

    //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
    /**
     * 成功建立连接调用的方法.
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("id") Long id){
        
        this.session = session;
        this.id = id;
        map.put(id,session);
     /*   sendMessageAll(JSON.toJSONString(map),id);*/
        clients.add(this);

    }


    /**
     * 连接关闭调用的方法.
     */
    @OnClose
    public void onClose(Session session){
        onlineNumber --;
        clients.remove(id);
        //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
        Map<String,Object> map1 = new HashMap<>();
        map1.put("messageType",2);
        map1.put("onlineUsers",clients);
        map1.put("id",id);
        /*sendMessageAll(JSON.toJSONString(map1),id);*/
        logger.info("有连接关闭！ 当前在线人数" + clients.size());
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 收到客户端消息后调用的方法.
     */
    @OnMessage
    public void onMessage(String message,Session session){

        try {
            logger.info("来自客户端消息：" + message+"客户端的id是："+session.getId());
            JSONObject jsonObject = JSON.parseObject(message);
            String textMessage = jsonObject.getString("message");
            String fileUrl = jsonObject.getString("fileUrl");
            Long fromuserId = Long.valueOf(jsonObject.getString("id"));
            Long touserId = Long.valueOf(jsonObject.getString("to"));
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息

            if (!touserId.equals("All")){

                Session fromWS = map.get(fromuserId);
                Session toWS = map.get(touserId);
                ChatRecord chatRecord = new ChatRecord();
                chatRecord.setContent(textMessage);
                chatRecord.setUserAId(fromuserId);
                chatRecord.setAFileUrl(fileUrl);
                fromWS.getAsyncRemote().sendText(JSON.toJSONString(chatRecord));
                if (toWS!=null){
                    toWS.getAsyncRemote().sendText(JSON.toJSONString(chatRecord));
                }
            }else {
                sendMessageAll(message,fromuserId);
            }

        }
        catch (Exception e){
            e.printStackTrace();
            logger.info("发生了错误了");
        }

    }

    /**
     * 发生错误时调用.
     */
    public void onError(Throwable error){
       logger.info("发生错误"+error.getMessage());
       // error.printStackTrace();
    }

    public void sendMessage(String message){
        this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 公告.
     * @param message
     * @param id
     */
   private void sendMessageAll(String message, Long id) {
        for (WebSocket item : clients) {
            item.session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 群发.
     * @param textMessage
     * @param fileUrl
     * @param fromuserId
     * @param ids
     */
    private void sendMessages(String textMessage,String fileUrl,Long fromuserId,List<Long> ids) {
        Session fromWS = map.get(fromuserId);
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setContent(textMessage);
        chatRecord.setUserAId(fromuserId);
        chatRecord.setAFileUrl(fileUrl);
        fromWS.getAsyncRemote().sendText(JSON.toJSONString(chatRecord));
        for (Long touserId : ids) {
            Session toWS = map.get(touserId);
            if (toWS!=null){
                toWS.getAsyncRemote().sendText(JSON.toJSONString(chatRecord));
            }
        }
    }

    /**
     * 获得在线人数
     * @return
     */
    public  static synchronized  int getOnlineNumber(){
        return onlineNumber;
    }

    /**
     * 在线人员
     * @return
     */
  /*  public  synchronized Map<Long, WebSocket>  getOnlineUsers(){
       return clients;
    }*/

}
