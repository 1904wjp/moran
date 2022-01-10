package com.moon.joyce.example.functionality.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2021/09/26-- 23:13
 * @describe:
 */
@ServerEndpoint(value="/websocket/{id}")// websocket连接点映射.
@Component
public class WebSocket {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int onlineNumber = 0;
    //用来存储每个客户端对应的MyWebSocket对象.
  //  private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();
    private static Map<Long,WebSocket> clients = new ConcurrentHashMap<>();
    //用来记录sessionId和该session之间的绑定关系.
   // private static Map<String, Session> map = new HashMap<String,Session>();
    private Session session;//当前会话的session.
    private Long id;//昵称.



    //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
    /**
     * 成功建立连接调用的方法.
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("id") Long id){
        
        this.session = session;
        this.id = id;
        /*String sId = SessionUtils.getSessionUser().getWebsocketSessionId();
        map.put(sId, session);
        webSocketSet.add(this);//加入set中.*/
        Map<String, Object> map1 = new HashMap<>();
        map1.put("messageType",1);
        map1.put("id",id);
        sendMessageAll(JSON.toJSONString(map1),id);
        clients.put(id,this);
        logger.info("有连接关闭!当前在线人数:"+clients.size());
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("messageType",3);
        Set<Long> set = clients.keySet();
        map2.put("onlionUsers",set);
        sendMessageTo(JSON.toJSONString(map2),id);
    }


    /**
     * 连接关闭调用的方法.
     */
    @OnClose
    public void onClose(Session session){
        onlineNumber --;
//        webSocketSet.remove(this);//从set中移除.
        clients.remove(id);
        //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
        Map<String,Object> map1 = new HashMap<>();
        map1.put("messageType",2);
        map1.put("onlineUsers",clients.keySet());
        map1.put("id",id);
        sendMessageAll(JSON.toJSONString(map1),id);
        //logger.info("有连接关闭！ 当前在线人数" + onlineNumber);
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
    public void onMessage(String message,Session session,@PathParam("id") Long id){


        try {
            logger.info("来自客户端消息：" + message+"客户端的id是："+session.getId());
            System.out.println("------------  :"+message);
            JSONObject jsonObject = JSON.parseObject(message);
            String textMessage = jsonObject.getString("message");
            Long fromuserId = Long.valueOf(jsonObject.getString("id"));
            Long touserId = Long.valueOf(jsonObject.getString("to"));
            //如果不是发给所有，那么就发给某一个人
            //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
            Map<String,Object> map1 = new HashMap();
            map1.put("messageType",4);
            map1.put("textMessage",textMessage);
            map1.put("fromuserId",fromuserId);
            if(touserId.equals("All")){
                map1.put("touserId","所有人");
                sendMessageAll(JSON.toJSONString(map1),fromuserId);
            }
            else{
                map1.put("touserId",touserId);
                logger.info("开始推送消息给"+touserId);
                /*sendMessageTo(JSON.toJSONString(map1),zz);*/
                sendMessageTo(JSON.toJSONString(map1),touserId);
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
    public void onError(Session session,Throwable error){
       logger.info("发生错误"+error.getMessage());
       // error.printStackTrace();
    }

    /**
     * 单一发送消息
     * @param message
     * @param id
     */
    public void sendMessageTo(String message, Long id){
        for (WebSocket item : clients.values()) {
            if (item.id.equals(id)){
                Session session = item.session;
                synchronized (session){
                    session.getAsyncRemote().sendText(message);
                }
            }
        }
    }
    /**
     * 群发的方法.
     * @param message
     * @param id
     */
    private void sendMessageAll(String message, Long id) {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendText(message);
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
    public  synchronized Map<Long, WebSocket>  getOnlineUsers(){
       return clients;
    }

}
