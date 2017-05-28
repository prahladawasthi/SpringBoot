package com.service;

import com.model.User;
import com.model.UserRoleEnum;

import java.util.List;

public interface UserService {

	List<User> find(String test);

	User findByID(String id);

	void saveOrUpdateUser(User user);	

	User deleteUserById(String id);

	List<User> findAllUsers();

	boolean isUserExist(User user);

	public User findByEmail(String email);

	String getUserRole(String email);

	List<UserRoleEnum> findUserRoles();

}
