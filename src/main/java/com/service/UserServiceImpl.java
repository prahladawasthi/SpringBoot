package com.service;

import com.model.User;
import com.model.UserRoleEnum;
import com.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void saveOrUpdateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public User deleteUserById(String id) {
		return mongoTemplate.findAndRemove(new Query().addCriteria(Criteria.where("_id").is(id)), User.class);
	}

	@Override
	public List<User> findAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	@Override
	public boolean isUserExist(User user) {
		return ((mongoTemplate.find(
				Query.query(new Criteria().orOperator(Criteria.where("email").regex(user.getEmail(), "i"))),
				User.class)).size() > 0) ? true : false;
	}

	@Override
	public List<User> find(String text) {
		return mongoTemplate.find(Query.query(new Criteria().orOperator(Criteria.where("email").regex(text, "i"),
				Criteria.where("password").regex(text, "i"), Criteria.where("firstName").regex(text, "i"),
				Criteria.where("lastName").regex(text, "i"))), User.class);
	}

	@Override
	public User findByID(String id) {
		return mongoTemplate.findById(id, User.class);
	}

	@Override
	public User findByEmail(String email) {
		return mongoTemplate.findOne(new Query().addCriteria(Criteria.where("email").is(email)), User.class);
	}

	@Override
	public String getUserRole(String email) {
		return mongoTemplate.findOne(new Query().addCriteria(Criteria.where("email").is(email)), User.class)
				.getUserRole();
	}

	@Override
	public List<UserRoleEnum> findUserRoles() {
		return Arrays.asList(UserRoleEnum.values());

	}

}
