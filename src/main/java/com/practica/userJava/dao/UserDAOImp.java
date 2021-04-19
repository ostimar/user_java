package com.practica.userJava.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.practica.userJava.entity.User;

@Repository
public class UserDAOImp implements UserDAO{

    @Autowired
    private EntityManager entityManager;
    
	@Override
	public List<User> findAll() {  
		return entityManager.createQuery(" from User" , User.class).getResultList();
	}

	@Override
	public User findById(int id) {
		 Session currentSession = entityManager.unwrap(Session.class);
	       return currentSession.get(User.class, id);
	 }

	@Override
	public void save(User user) {
		Session currentSession = entityManager.unwrap(Session.class);
        currentSession.save(user); 		
	}

	@Override
	public void deleteById(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existEmail(String email) {
		 CriteriaBuilder cb=entityManager.getCriteriaBuilder();
		  CriteriaQuery<User> criteria=cb.createQuery(User.class);
		  Root<User> user=criteria.from(User.class);
		  criteria.select(user).where(cb.equal(cb.upper(user.get("email")),email.toUpperCase()));
		  return entityManager.createQuery(criteria).getResultList().size() > 0;
			}

	@Override
	public User findByEmail(String email) {
		 CriteriaBuilder cb=entityManager.getCriteriaBuilder();
		  CriteriaQuery<User> criteria=cb.createQuery(User.class);
		  Root<User> user=criteria.from(User.class);
		  criteria.select(user).where(cb.equal(cb.upper(user.get("email")),email.toUpperCase()));
		  return entityManager.createQuery(criteria).getSingleResult();
	}

}
