package com.ssafy.ssafit.user.dto;

public class UserSignUpRequestDto {
    private String id;
    private String password;
    private String name;
    private String email;
    private String nickName;
    private String phone;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "UserSignUpRequestDto [id=" + id + ", password=" + password + ", name=" + name + ", email=" + email
				+ ", nickName=" + nickName + ", phone=" + phone + "]";
	}

   
}
