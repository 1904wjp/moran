package com.moon.joyce.example.functionality.entity.doma;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * 自定义获取指定sessionId的session
 */
public class MySessionContext {
    private static MySessionContext instance;
    private HashMap mymap;

    private MySessionContext() {
        mymap = new HashMap();
    }

    /**
     * 创建MySessionContext的对象
     * @return
     */
    public static MySessionContext getInstance() {
        if (instance == null) {
            instance = new MySessionContext();
        }
        return instance;
    }

    /**
     * 添加session
     * @param session
     */
    public synchronized void AddSession(HttpSession session) {
        if (session != null) {
            mymap.put(session.getId(), session);
        }
    }

    /**
     * 删除session
     * @param session
     */
    public synchronized void DelSession(HttpSession session) {
        if (session != null) {
            mymap.remove(session.getId());
        }
    }

    /**
     * 查询session
     * @param session_id
     * @return
     */
    public synchronized HttpSession getSession(String session_id) {
        if (session_id == null) {
            return null;
        }
        return (HttpSession) mymap.get(session_id);
    }

}
