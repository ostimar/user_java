package com.practica.userJava.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practica.userJava.app.Utilities;
import com.practica.userJava.entity.User;
import com.practica.userJava.model.ResponseUsers;
import com.practica.userJava.service.UserService;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
@RequestMapping("/api") 
public class UserRestController 
{
    private static final Logger log = LoggerFactory.getLogger(UserRestController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    private final HttpServletResponse response;

    
    @Autowired
    private UserService userService;
    
    @Autowired
    public UserRestController(ObjectMapper objectMapper, HttpServletRequest request,  HttpServletResponse response) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.response = response;
    }
	@GetMapping("/v1/echo")
    @ResponseBody
    public ResponseEntity<String> hola() {
 
        return ResponseEntity.status(HttpStatus.OK).body("REST User V1.0");
 
    }
	
		
    @RequestMapping(value = "/v1/user",
            consumes = { "application/json" }, 
            method = RequestMethod.POST)
    public ResponseEntity userPost( @Valid @RequestBody User user) {
    	try {
    		if(userService.existEmail(user.getEmail()))
    			 return ResponseEntity.status(HttpStatus.CONFLICT).body("{\r\n" + 
    	            		"	\"error\": \"El Email existe\"\r\n" + 
    	            		"}");
	        userService.save(user);	        
	        return new ResponseEntity<Void>(HttpStatus.OK);
    	}catch (ConstraintViolationException cvie) {
            log.error("Error en userPost", cvie);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\r\n" + 
            		"	\"error\": \"" + Utilities.ConstraintViolationToHuman(cvie.getConstraintViolations()) + "\"\r\n" + 
            		"}");    	        
    	}catch (DataIntegrityViolationException deie) {
            log.error("Error en userPost", deie);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\r\n" + 
            		"	\"error\": \"" + deie.getMostSpecificCause().getMessage() + "\"\r\n" + 
            		"}");
    	}catch (Exception e) {
            log.error("Error en userPost", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\r\n" + 
            		"	\"error\": \"" + e.getCause() + "\"\r\n" + 
            		"}");
        }
    }
        
    
    @RequestMapping(value = "/v1/userList",
            produces = { "application/json" }, 
            method = RequestMethod.GET)
    public ResponseEntity<ResponseUsers> userList() {
            try {
            	List<User> lUser = userService.findAll();
            	if(lUser.size() == 0)
                    return new ResponseEntity<ResponseUsers>(HttpStatus.NOT_FOUND);
            	ResponseUsers responseUser = new ResponseUsers();
            	responseUser.setUsers(lUser);
                return ResponseEntity.ok().body(responseUser);            	
            } catch (Exception e) {
                log.error("Error en userList", e);
                return new ResponseEntity<ResponseUsers>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }    
	
}
