package com.ssafy.ssafit.user.dto;

public class LoginRequestDto {
	private String id;
	private String password;

	// Getter/Setter
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
