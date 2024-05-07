package com.micro.ui.model;

import java.io.Serializable;

public class FriendDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long phoneNumber;
	private Long friendNumber;
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Long getFriendNumber() {
		return friendNumber;
	}
	public void setFriendNumber(Long friendNumber) {
		this.friendNumber = friendNumber;
	}
	
}
