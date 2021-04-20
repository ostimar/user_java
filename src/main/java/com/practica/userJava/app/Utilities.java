package com.practica.userJava.app;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class Utilities 
{
	public static String ConstraintViolationToHuman(Set<ConstraintViolation<?>> setCv) 
	{
		String sError = "";
		 for (ConstraintViolation cv : setCv)
         {
         	sError += cv.getPropertyPath() + " " + cv.getMessage() + " ";
         }
		 return sError;
	}

}
