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
@ServerEndpoint(value="/websocket/{username}")// websocket连接点映射.
@Component
public class WebSocket {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int onlineNumber = 0;
    //用来存储每个客户端对应的MyWebSocket对象.
  //  private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();
    private static Map<String,WebSocket> clients = new ConcurrentHashMap<String,WebSocket>();
    //用来记录sessionId和该session之间的绑定关系.
   // private static Map<String, Session> map = new HashMap<String,Session>();
    private Session session;//当前会话的session.
    private String username;//昵称.



    //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
    /**
     * 成功建立连接调用的方法.
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("username") String username){
        
        this.session = session;
        this.username = username;
        /*String sId = SessionUtils.getSessionUser().getWebsocketSessionId();
        map.put(sId, session);
        webSocketSet.add(this);//加入set中.*/
        Map<String, Object> map1 = new HashMap<>();
        map1.put("messageType",1);
        map1.put("username",username);
        sendMessageAll(JSON.toJSONString(map1),username);
        clients.put(username,this);
        logger.info("有连接关闭!当前在线人数:"+clients.size());
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("messageType",3);
        Set<String> set = clients.keySet();
        map2.put("onlionUsers",set);
        sendMessageTo(JSON.toJSONString(map2),username);
    }


    /**
     * 连接关闭调用的方法.
     */
    @OnClose
    public void onClose(Session session){
        onlineNumber --;
//        webSocketSet.remove(this);//从set中移除.
        clients.remove(username);
        //messageType 1代表上线 2代表下线 3代表在线名单  4代表普通消息
        Map<String,Object> map1 = new HashMap<>();
        map1.put("messageType",2);
        map1.put("onlineUsers",clients.keySet());
        map1.put("username",username);
        sendMessageAll(JSON.toJSONString(map1),username);
        //logger.info("有连接关闭！ 当前在线人数" + onlineNumber);
        logger.info("有连接关闭！ 当前在线人数" + clients.size());
    }

    /**
     * 收到客户端消息后调用的方法.
     */
    @OnMessage
    public void onMessage(String message,Session session,@PathParam("username") String username){

      /*  //message 不是普通的string ，而是我们定义的SocketMsg json字符串.
        try {
            SocketMsg socketMsg = new ObjectMapper().readValue(message, SocketMsg.class);
            //单聊.
            if(socketMsg.getType() == 1){
                //单聊：需要找到发送者和接受者即可.
                socketMsg.setFromUser(session.getId());//发送者.
                //socketMsg.setToUser(toUser);//这个是由客户端进行设置.
                Session fromSession = map.get(socketMsg.getFromUser());
                Session toSession = map.get(socketMsg.getToUser());
                if(toSession != null){
                    //发送消息.
                    fromSession.getAsyncRemote().sendText(username+"："+socketMsg.getMsg());
                    toSession.getAsyncRemote().sendText(username+"："+socketMsg.getMsg());
                }else{
                    fromSession.getAsyncRemote().sendText("系统消息：对方不在线或者您输入的频道号有误");
                }
            }else {
                //群发给每个客户端.
                broadcast(socketMsg,username);
            }
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        try {
            logger.info("来自客户端消息：" + message+"客户端的id是："+session.getId());
            System.out.println("------------  :"+message);
            JSONObject jsonObject = JSON.parseObject(message);
            String textMessage = jsonObject.getString("message");
            String fromuserId = jsonObject.getString("username");
            String touserId = jsonObject.getString("to");
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
                System.out.println("开始推送消息给"+touserId);
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
     * @param toUsername
     */
    public void sendMessageTo(String message, String toUsername){
        for (WebSocket item : clients.values()) {
            if (item.username.equals(toUsername)){
                item.session.getAsyncRemote().sendText(message);
            }
        }
    }
    /**
     * 群发的方法.
     * @param socketMsg
     * @param fromUsername
     */
    private void sendMessageAll(String message, String fromUsername) {
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
    public  synchronized Map<String, WebSocket>  getOnlineUsers(){
       return clients;
    }

}
