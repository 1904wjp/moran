package com.moon.joyce.listeners;

import com.moon.joyce.example.functionality.entity.doma.MySessionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 监听器来监控session的创建销毁
 */

@Configuration
@WebListener
public class SessionListener implements HttpSessionListener {

    private MySessionContext myc = MySessionContext.getInstance();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        System.out.println(">>>>>>>>>>>>我执行了..");
        myc.AddSession(httpSessionEvent.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        System.out.println(">>>>>>>>>>>>我执行了...");
        myc.DelSession(session);
    }
}
