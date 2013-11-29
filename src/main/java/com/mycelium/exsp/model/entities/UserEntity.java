package com.mycelium.exsp.model.entities;

public class UserEntity implements Entity {

	private Long _id;
	private String _login;
	private String _email;

	@Override
	public Long getId() {
		return _id;
	}

	@Override
	public void setId(Long id) {
		_id = id;
	}

	public String getLogin() {
		return _login;
	}

	public void setLogin(String login) {
		_login = login;
	}

	public String getEmail() {
		return _email;
	}

	public void setEmail(String email) {
		_email = email;
	}

}
