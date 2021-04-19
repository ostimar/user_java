package com.practica.userJava.dao;

import java.util.List;

import com.practica.userJava.entity.User;

public interface UserDAO 
{
    public List<User> findAll();

    public User findById(int id);
    public User findByEmail(String email);
    
    public boolean existEmail(String email);

	public void save(User user);

	public void deleteById(int id);	

}
