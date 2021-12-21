package com.moon.joyce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 注入ServerEndpointExporter之后，
 * 
 * 这个bean会自动注册使用了@ServerEndpoint注解声明的websocket
 * 
 * @author Joyce
 * @version v.0.1
 * @date 2021/10/13
 */
@Configuration
public class WebSocketConfig {
	
	@Bean
	public ServerEndpointExporter serverEndpointExporter(){
		return new ServerEndpointExporter();
	}
	
}
