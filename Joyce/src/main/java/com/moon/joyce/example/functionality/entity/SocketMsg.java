package com.moon.joyce.example.functionality.entity;

import com.moon.joyce.example.entity.base.entity.BaseEntity;

/**
 * @author Joyce
 */
public class SocketMsg extends BaseEntity {
	/**
	 * 聊天类型，0：群聊；1：单聊;
	 */
	private int type;
	/**
	 * 发送者.
	 */
	private String fromUser;
	/**
	 * 接受者. session.getId();
	 */
	private String toUser;
	/**
	 * 消息.
	 */
	private String msg;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
