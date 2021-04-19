package com.practica.userJava.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practica.userJava.dao.UserDAO;
import com.practica.userJava.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserDAO userDAO;
	
	@Override
	public List<User> findAll() {
        List<User> listUsers= userDAO.findAll();
        return listUsers;
	}

	@Override
	public User findById(int id) {
		return userDAO.findById(id);
	}

	@Override
	public void save(User user) {		
        userDAO.save(user);		
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findByEmail(String email) {
		return userDAO.findByEmail(email);
	}

	@Override
	public boolean existEmail(String email) {
		// TODO Auto-generated method stub
		return userDAO.existEmail(email);
	}

}
