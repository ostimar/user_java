package com.practica.userJava.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practica.userJava.entity.User;
import com.practica.userJava.model.ResponseLogin;
import com.practica.userJava.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("/api") 

public class AdminRestController 
{
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);
   
    @Autowired
    private UserService userService;
          
    @RequestMapping(value = "/v1/login",
    produces = { "application/json" }, 
    method = RequestMethod.GET)
    public  ResponseEntity<ResponseLogin> loginGet(
    		@NotNull @Valid	@RequestParam(value = "email", required = true) String email,
    		@NotNull @Valid @RequestParam(value = "password", required = true) String password) {
		try {
			
			User user = userService.findByEmail(email);
			if(!password.equals(user.getPassword()))
				return new ResponseEntity<ResponseLogin>(HttpStatus.FORBIDDEN);
			ResponseLogin responseLogin = new ResponseLogin();
			responseLogin.setUser(user);
			responseLogin.setAuthToken(getJWTToken(user.getEmail()));
            return ResponseEntity.ok().body(responseLogin); 
		} catch (NoResultException | EmptyResultDataAccessException nree) {
	            log.error("Usuario no encontrao", nree);
	            return new ResponseEntity<ResponseLogin>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error en login", e);
            return new ResponseEntity<ResponseLogin>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
 
    }
    
      private String getJWTToken(String email) {
		String secretKey = "9'5\"N])q<YfEh*O";
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
					.commaSeparatedStringToAuthorityList("ROLE_USER");
		String token = Jwts
				.builder()
				.setId("practica_user")
				.setSubject(email)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1440000000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();
		

		return "Bearer " + token;
	}
}
